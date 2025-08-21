package org.example.dungeon.service;

import org.example.dungeon.dao.DungeonSolutionDao;
import org.example.dungeon.dao.ExposureDao;
import org.example.dungeon.dto.SolveRequest;
import org.example.dungeon.dto.SolveResponse;
import org.example.dungeon.experiment.ExperimentAssigner;
import org.example.dungeon.model.DungeonSolver;
import org.example.dungeon.model.Dp2dSolver;
import org.example.dungeon.model.Dp1dSolver;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class DungeonService {

    private final ExperimentAssigner assigner;
    private final DungeonSolutionDao solutionDao;
    private final ExposureDao exposureDao;
    private final MeterRegistry meterRegistry;

    private final DungeonSolver solverA = new Dp2dSolver();
    private final DungeonSolver solverB = new Dp1dSolver();

    public DungeonService(
            ExperimentAssigner assigner,
            DungeonSolutionDao solutionDao,
            ExposureDao exposureDao,
            MeterRegistry meterRegistry
    ) {
        this.assigner = assigner;
        this.solutionDao = solutionDao;
        this.exposureDao = exposureDao;
        this.meterRegistry = meterRegistry;
    }

    @Transactional
    public SolveResponse solve(String userIdHeader, String forcedVariant, SolveRequest request) {
        String userId = Optional.ofNullable(userIdHeader)
                .orElseGet(() -> Optional.ofNullable(request.userId()).orElse(UUID.randomUUID().toString()));

        String variant = (forcedVariant != null)
                ? forcedVariant
                : assigner.choose("dungeon-solver", userId);

        DungeonSolver solver = switch (variant) {
            case "DP_2D" -> solverA;
            case "DP_1D" -> solverB;
            default -> throw new IllegalArgumentException("Unknown variant: " + variant);
        };

        UUID requestId = UUID.randomUUID();
        exposureDao.recordExposure(requestId, "dungeon-solver", variant, userId, Instant.now());

        int[][] grid = request.toGrid();
        long start = System.nanoTime();
        int result = solver.minInitialHealth(grid);
        long durationMs = (System.nanoTime() - start) / 1_000_000;

        meterRegistry.timer("dungeon.solve", "variant", variant)
                .record(durationMs, java.util.concurrent.TimeUnit.MILLISECONDS);

        solutionDao.save(requestId, userId, request.grid(), result, variant, durationMs);

        return new SolveResponse(requestId, result, variant, durationMs);
    }

    public SolveResponse getById(UUID id) {
        var entity = solutionDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solution not found: " + id));
        return new SolveResponse(entity.getId(), entity.getResult(), entity.getVariant(), entity.getDurationMs());
    }
}

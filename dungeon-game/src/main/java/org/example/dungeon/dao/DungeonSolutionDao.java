package org.example.dungeon.dao;

import org.example.dungeon.model.DungeonSolutionEntity;
import org.example.dungeon.repository.DungeonSolutionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DungeonSolutionDao {

    private final DungeonSolutionRepository repository;

    public DungeonSolutionDao(DungeonSolutionRepository repository) {
        this.repository = repository;
    }

    public void save(UUID id, String userId, List<List<Integer>> grid, int result, String variant, long durationMs) {
        DungeonSolutionEntity entity = DungeonSolutionEntity.from(id, userId, grid, result, variant, durationMs);
        repository.save(entity);
    }

    public Optional<DungeonSolutionEntity> findById(UUID id) {
        return repository.findById(id);
    }
}

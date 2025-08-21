package org.example.dungeon.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dungeon_solutions")
public class DungeonSolutionEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String grid;

    private int result;
    private String variant;

    @Column(name = "duration_ms")
    private long durationMs;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected DungeonSolutionEntity() {}

    public static DungeonSolutionEntity from(UUID id, String userId, List<List<Integer>> grid, int result, String variant, long durationMs) {
        DungeonSolutionEntity e = new DungeonSolutionEntity();
        e.id = id;
        e.userId = userId;
        e.result = result;
        e.variant = variant;
        e.durationMs = durationMs;
        e.createdAt = Instant.now();

        try {
            e.grid = new ObjectMapper().writeValueAsString(grid);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to serialize grid", ex);
        }

        return e;
    }

    public UUID getId() { return id; }
    public int getResult() { return result; }
    public String getVariant() { return variant; }
    public long getDurationMs() { return durationMs; }
}

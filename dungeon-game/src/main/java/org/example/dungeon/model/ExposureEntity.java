package org.example.dungeon.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "experiment_exposures")
public class ExposureEntity {

    @Id
    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "experiment_key", nullable = false)
    private String experimentKey;

    private String variant;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    protected ExposureEntity() {}

    public ExposureEntity(UUID requestId, String experimentKey, String variant, String userId, Instant assignedAt) {
        this.requestId = requestId;
        this.experimentKey = experimentKey;
        this.variant = variant;
        this.userId = userId;
        this.assignedAt = assignedAt;
    }
}

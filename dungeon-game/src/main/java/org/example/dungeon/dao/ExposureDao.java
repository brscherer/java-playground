package org.example.dungeon.dao;

import org.example.dungeon.model.ExposureEntity;
import org.example.dungeon.repository.ExposureRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ExposureDao {

    private final ExposureRepository repository;

    public ExposureDao(ExposureRepository repository) {
        this.repository = repository;
    }

    public void recordExposure(UUID requestId, String experimentKey, String variant, String userId, Instant assignedAt) {
        ExposureEntity exposure = new ExposureEntity(requestId, experimentKey, variant, userId, assignedAt);
        repository.save(exposure);
    }
}

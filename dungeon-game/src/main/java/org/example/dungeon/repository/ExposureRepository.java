package org.example.dungeon.repository;

import org.example.dungeon.model.ExposureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExposureRepository extends JpaRepository<ExposureEntity, UUID> {
}

package org.example.dungeon.repository;

import org.example.dungeon.model.DungeonSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DungeonSolutionRepository extends JpaRepository<DungeonSolutionEntity, UUID> {
}

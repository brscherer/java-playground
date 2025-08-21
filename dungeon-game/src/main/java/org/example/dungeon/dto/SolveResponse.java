package org.example.dungeon.dto;

import java.util.UUID;

public record SolveResponse(
        UUID id,
        int minInitialHealth,
        String variant,
        long durationMs
) {
}

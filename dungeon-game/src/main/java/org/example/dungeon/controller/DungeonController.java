package org.example.dungeon.controller;

import org.example.dungeon.dto.SolveRequest;
import org.example.dungeon.dto.SolveResponse;
import org.example.dungeon.service.DungeonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dungeon")
public class DungeonController {

    private final DungeonService dungeonService;

    public DungeonController(DungeonService dungeonService) {
        this.dungeonService = dungeonService;
    }

    @PostMapping("/solve")
    public ResponseEntity<SolveResponse> solve(
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Exp-Variant", required = false) String forcedVariant,
            @Valid @RequestBody SolveRequest request
    ) {
        SolveResponse response = dungeonService.solve(userIdHeader, forcedVariant, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolveResponse> getById(@PathVariable UUID id) {
        SolveResponse response = dungeonService.getById(id);
        return ResponseEntity.ok(response);
    }
}

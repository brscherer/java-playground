package org.example.dungeon.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SolveRequest(
        String userId,
        @NotNull @Size(min = 1) List<@Size(min = 1) List<Integer>> grid
) {
    public int[][] toGrid() {
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (grid.get(i).size() != n) {
                throw new IllegalArgumentException("Jagged grid rows must have equal length");
            }
            for (int j = 0; j < n; j++) {
                arr[i][j] = grid.get(i).get(j);
            }
        }
        return arr;
    }
}

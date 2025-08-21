package org.example.dungeon.service;

import org.example.dungeon.solver.Dp1dSolver;
import org.example.dungeon.solver.Dp2dSolver;
import org.springframework.stereotype.Component;

@Component
public class DungeonSolver {

    private final Dp1dSolver dp1dSolver = new Dp1dSolver();
    private final Dp2dSolver dp2dSolver = new Dp2dSolver();

    public int solve(int[][] dungeon, String variant) {
        return switch (variant) {
            case "A" -> dp1dSolver.solve(flatten(dungeon));
            case "B", "C" -> dp2dSolver.solve(dungeon);
            default -> dp2dSolver.solve(dungeon);
        };
    }

    private int[] flatten(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[] flat = new int[m * n];
        int idx = 0;
        for (int[] row : dungeon)
            for (int cell : row)
                flat[idx++] = cell;
        return flat;
    }
}

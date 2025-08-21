package org.example.dungeon.model;

public sealed interface DungeonSolver permits Dp2dSolver, Dp1dSolver {
    int minInitialHealth(int[][] dungeon);
    String variant();
}

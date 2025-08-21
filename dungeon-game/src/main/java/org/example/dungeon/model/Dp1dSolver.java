package org.example.dungeon.model;

public final class Dp1dSolver implements DungeonSolver {

    @Override
    public String variant() {
        return "DP_1D";
    }

    @Override
    public int minInitialHealth(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[] dp = new int[n];
        dp[n - 1] = Math.max(1, 1 - dungeon[m - 1][n - 1]);

        for (int j = n - 2; j >= 0; j--) {
            dp[j] = Math.max(1, dp[j + 1] - dungeon[m - 1][j]);
        }

        for (int i = m - 2; i >= 0; i--) {
            dp[n - 1] = Math.max(1, dp[n - 1] - dungeon[i][n - 1]);
            for (int j = n - 2; j >= 0; j--) {
                int need = Math.min(dp[j], dp[j + 1]) - dungeon[i][j];
                dp[j] = Math.max(1, need);
            }
        }
        return dp[0];
    }
}

package org.example.dungeon.experiment;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExperimentAssigner {

    private final LinkedHashMap<String, Double> cumulative = new LinkedHashMap<>();

    public ExperimentAssigner(Map<String, Double> split) {
        double acc = 0.0;
        for (Map.Entry<String, Double> e : split.entrySet()) {
            acc += e.getValue();
            cumulative.put(e.getKey(), acc);
        }
        if (Math.abs(acc - 1.0) > 1e-9) {
            throw new IllegalArgumentException("Variant split must sum to 1.0");
        }
    }

    public String choose(String experimentKey, String unitId) {
        double u = uniform01(experimentKey + "|" + unitId);
        return cumulative.entrySet().stream()
                .filter(e -> u <= e.getValue())
                .findFirst()
                .orElseThrow()
                .getKey();
    }

    private static double uniform01(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(s.getBytes());
            long hi = ByteBuffer.wrap(digest, 0, 8).getLong();
            return (hi >>> 1) / (double) (1L << 63);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

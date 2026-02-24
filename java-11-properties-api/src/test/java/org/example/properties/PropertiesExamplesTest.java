package org.example.properties;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesExamplesTest {
    @Test
    void createStoreAndReadBack() throws IOException {
        Properties p = new Properties();
        p.setProperty("k1", "v1");
        p.setProperty("k2", "v2");

        Path tmp = Files.createTempFile("props-test-", ".properties");
        try (var out = Files.newOutputStream(tmp)) {
            p.store(out, "test");
        }

        Properties read = new Properties();
        try (var in = Files.newInputStream(tmp)) {
            read.load(in);
        }

        assertEquals("v1", read.getProperty("k1"));
        assertEquals("v2", read.getProperty("k2"));
    }
}


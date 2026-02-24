package org.example.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream in = ReadPropertiesExample.class.getResourceAsStream("/config.properties")) {
            if (in == null) {
                System.err.println("config.properties not found on classpath");
                return;
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("app.name: " + props.getProperty("app.name"));
        System.out.println("app.version: " + props.getProperty("app.version"));
        System.out.println("All entries:");
        props.forEach((k, v) -> System.out.println(k + " = " + v));
    }
}


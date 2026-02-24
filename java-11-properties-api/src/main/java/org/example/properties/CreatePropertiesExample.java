package org.example.properties;

import java.util.Properties;

public class CreatePropertiesExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("app.name", "FunApp");
        props.setProperty("app.env", "development");
        props.setProperty("app.users", "alice,bob,charlie");

        props.forEach((k, v) -> System.out.println(k + " = " + v));
    }
}


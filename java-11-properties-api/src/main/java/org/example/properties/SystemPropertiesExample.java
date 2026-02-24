package org.example.properties;

public class SystemPropertiesExample {
    public static void main(String[] args) {
        System.out.println("-- System properties (selected) --");
        System.out.println("user.name: " + System.getProperty("user.name"));
        System.out.println("user.home: " + System.getProperty("user.home"));

        System.out.println("\n-- Demonstrate setting a process-local system property --");
        System.out.println("before custom.prop: " + System.getProperty("custom.prop"));
        System.setProperty("custom.prop", "hello-world");
        System.out.println("after custom.prop: " + System.getProperty("custom.prop"));

        System.out.println("\nNote: System.setProperty changes are only visible within this JVM process.");
    }
}


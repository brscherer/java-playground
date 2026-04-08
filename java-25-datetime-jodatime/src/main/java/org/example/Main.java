package org.example;

import org.example.javatime.*;
import org.example.jodatime.*;

public class Main {
    public static void main(String[] args) {
        printBanner("JAVA DATE-TIME API");
        
        JavaTimeExample.runAll();
        JavaTimeTimezoneExample.runAll();
        JavaTimeMeetingScheduler.runAll();
        
        printBanner("JODA TIME");
        
        JodaTimeExample.runAll();
        JodaTimeTimezoneExample.runAll();
        JodaTimeMeetingScheduler.runAll();
    }
    
    private static void printBanner(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }
}

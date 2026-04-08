package org.example.javatime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JavaTimeExample {

    public static void runBasicUsage() {
        System.out.println("\n========== Java Date-Time API: Basic Usage ==========");
        
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        
        LocalTime now = LocalTime.now();
        System.out.println("Current time: " + now);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current date-time: " + currentDateTime);
        
        LocalDate specificDate = LocalDate.of(2026, 4, 7);
        LocalDateTime specificDateTime = LocalDateTime.of(2026, 4, 7, 14, 30, 45);
        System.out.println("Specific date: " + specificDate);
        System.out.println("Specific date-time: " + specificDateTime);
    }

    public static void runFormatting() {
        System.out.println("\n========== Java Date-Time API: Formatting ==========");
        
        LocalDateTime now = LocalDateTime.now();
        
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Pattern 1: " + now.format(formatter1));
        
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        System.out.println("Pattern 2: " + now.format(formatter2));
        
        LocalDate date = LocalDate.now();
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE;
        System.out.println("ISO format: " + date.format(isoFormatter));
    }

    public static void runParsing() {
        System.out.println("\n========== Java Date-Time API: Parsing ==========");
        
        String dateString = "2026-12-25";
        LocalDate parsedDate = LocalDate.parse(dateString);
        System.out.println("Parsed date: " + parsedDate);
        
        String dateTimeString = "2026-04-07T14:30:45";
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString);
        System.out.println("Parsed date-time: " + parsedDateTime);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String customString = "25/12/2026 18:00";
        LocalDateTime customDateTime = LocalDateTime.parse(customString, formatter);
        System.out.println("Custom pattern parsed: " + customDateTime);
    }

    public static void runDateArithmetic() {
        System.out.println("\n========== Java Date-Time API: Date Arithmetic ==========");
        
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        LocalDate nextMonth = today.plusMonths(1);
        LocalDate nextYear = today.plusYears(1);
        
        System.out.println("Today: " + today);
        System.out.println("Next week: " + nextWeek);
        System.out.println("Next month: " + nextMonth);
        System.out.println("Next year: " + nextYear);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureTime = now.plusHours(5).plusMinutes(30);
        System.out.println("\nCurrent: " + now);
        System.out.println("After 5.5 hours: " + futureTime);
    }

    public static void runComparisons() {
        System.out.println("\n========== Java Date-Time API: Comparisons ==========");
        
        LocalDate date1 = LocalDate.of(2026, 4, 7);
        LocalDate date2 = LocalDate.of(2026, 12, 25);
        
        System.out.println("Date 1: " + date1);
        System.out.println("Date 2: " + date2);
        System.out.println("Is date1 before date2? " + date1.isBefore(date2));
        System.out.println("Is date1 after date2? " + date1.isAfter(date2));
        System.out.println("Are they equal? " + date1.isEqual(date2));
        
        LocalDateTime dateTime1 = LocalDateTime.of(2026, 4, 7, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2026, 4, 7, 15, 30);
        
        System.out.println("\nDateTime 1: " + dateTime1);
        System.out.println("DateTime 2: " + dateTime2);
        System.out.println("Difference in hours: " + 
            java.time.temporal.ChronoUnit.HOURS.between(dateTime1, dateTime2));
    }

    public static void runAll() {
        runBasicUsage();
        runFormatting();
        runParsing();
        runDateArithmetic();
        runComparisons();
    }
}


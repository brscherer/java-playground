package org.example.jodatime;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeExample {

    public static void runBasicUsage() {
        System.out.println("\n========== JodaTime: Basic Usage ==========");
        
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        
        LocalTime now = LocalTime.now();
        System.out.println("Current time: " + now);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current date-time: " + currentDateTime);
        
        LocalDate specificDate = new LocalDate(2026, 4, 7);
        LocalDateTime specificDateTime = new LocalDateTime(2026, 4, 7, 14, 30, 45);
        System.out.println("Specific date: " + specificDate);
        System.out.println("Specific date-time: " + specificDateTime);
    }

    public static void runFormatting() {
        System.out.println("\n========== JodaTime: Formatting ==========");
        
        DateTime now = DateTime.now();
        
        DateTimeFormatter formatter1 = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Pattern 1: " + formatter1.print(now));
        
        DateTimeFormatter formatter2 = DateTimeFormat.forPattern("EEEE, MMMM dd, yyyy");
        System.out.println("Pattern 2: " + formatter2.print(now));
        
        DateTimeFormatter isoFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        System.out.println("ISO-like format: " + isoFormatter.print(now));
    }

    public static void runParsing() {
        System.out.println("\n========== JodaTime: Parsing ==========");
        
        String dateString = "2026-12-25";
        LocalDate parsedDate = LocalDate.parse(dateString);
        System.out.println("Parsed date: " + parsedDate);
        
        String dateTimeString = "2026-04-07T14:30:45";
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString);
        System.out.println("Parsed date-time: " + parsedDateTime);
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
        String customString = "25/12/2026 18:00";
        LocalDateTime customDateTime = formatter.parseLocalDateTime(customString);
        System.out.println("Custom pattern parsed: " + customDateTime);
    }

    public static void runDateArithmetic() {
        System.out.println("\n========== JodaTime: Date Arithmetic ==========");
        
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
        System.out.println("\n========== JodaTime: Comparisons ==========");
        
        LocalDate date1 = new LocalDate(2026, 4, 7);
        LocalDate date2 = new LocalDate(2026, 12, 25);
        
        System.out.println("Date 1: " + date1);
        System.out.println("Date 2: " + date2);
        System.out.println("Is date1 before date2? " + date1.isBefore(date2));
        System.out.println("Is date1 after date2? " + date1.isAfter(date2));
        System.out.println("Are they equal? " + date1.isEqual(date2));
        
        LocalDateTime dateTime1 = new LocalDateTime(2026, 4, 7, 10, 0);
        LocalDateTime dateTime2 = new LocalDateTime(2026, 4, 7, 15, 30);
        
        System.out.println("\nDateTime 1: " + dateTime1);
        System.out.println("DateTime 2: " + dateTime2);
        System.out.println("Difference in hours: " + 
            org.joda.time.Hours.hoursBetween(dateTime1, dateTime2).getHours());
    }

    public static void runAll() {
        runBasicUsage();
        runFormatting();
        runParsing();
        runDateArithmetic();
        runComparisons();
    }
}


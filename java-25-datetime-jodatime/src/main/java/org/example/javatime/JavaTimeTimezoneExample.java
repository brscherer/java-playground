package org.example.javatime;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JavaTimeTimezoneExample {

    public static void runBasicTimezoneHandling() {
        System.out.println("\n========== Java Date-Time API: Timezone Handling ==========");
        
        ZonedDateTime nowUTC = ZonedDateTime.now(ZoneId.of("UTC"));
        System.out.println("Current UTC: " + nowUTC);
        
        ZonedDateTime nowNY = ZonedDateTime.now(ZoneId.of("America/New_York"));
        System.out.println("Current in NY: " + nowNY);
        
        ZonedDateTime nowLondon = ZonedDateTime.now(ZoneId.of("Europe/London"));
        System.out.println("Current in London: " + nowLondon);
        
        ZonedDateTime nowTokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.println("Current in Tokyo: " + nowTokyo);
    }

    public static void runMeetingScheduling() {
        System.out.println("\n========== Java Date-Time API: Meeting Scheduling Across Timezones ==========");
        
        LocalDateTime meetingTime = LocalDateTime.of(2026, 5, 15, 10, 0, 0);
        ZoneId companyZone = ZoneId.of("America/New_York");
        
        ZonedDateTime meeting = ZonedDateTime.of(meetingTime, companyZone);
        System.out.println("Company meeting (NY): " + meeting);
        
        ZonedDateTime londonTime = meeting.withZoneSameInstant(ZoneId.of("Europe/London"));
        System.out.println("London team time: " + londonTime);
        
        ZonedDateTime tokyoTime = meeting.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        System.out.println("Tokyo team time: " + tokyoTime);
        
        ZonedDateTime mumbaiTime = meeting.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
        System.out.println("Mumbai team time: " + mumbaiTime);
    }

    public static void runInstantComparison() {
        System.out.println("\n========== Java Date-Time API: Comparing Instants Across Timezones ==========");
        
        ZonedDateTime eventNY = ZonedDateTime.of(
            LocalDateTime.of(2026, 6, 1, 18, 0, 0),
            ZoneId.of("America/New_York")
        );
        
        ZonedDateTime eventTokyo = ZonedDateTime.of(
            LocalDateTime.of(2026, 6, 2, 6, 0, 0),
            ZoneId.of("Asia/Tokyo")
        );
        
        Instant instantNY = eventNY.toInstant();
        Instant instantTokyo = eventTokyo.toInstant();
        
        System.out.println("Event NY instant: " + instantNY);
        System.out.println("Event Tokyo instant: " + instantTokyo);
        System.out.println("Same instant in time? " + instantNY.equals(instantTokyo));
        
        long secondsBetween = ChronoUnit.SECONDS.between(instantNY, instantTokyo);
        System.out.println("Seconds between: " + secondsBetween);
    }

    public static void runOffsetHandling() {
        System.out.println("\n========== Java Date-Time API: Offset Handling ==========");
        
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("Current timezone offset: " + now.getOffset());
        
        ZonedDateTime daylightTime = ZonedDateTime.of(
            LocalDateTime.of(2026, 7, 15, 14, 0, 0),
            ZoneId.of("America/New_York")
        );
        System.out.println("July (summer) offset (NY): " + daylightTime.getOffset());
        
        ZonedDateTime winterTime = ZonedDateTime.of(
            LocalDateTime.of(2026, 1, 15, 14, 0, 0),
            ZoneId.of("America/New_York")
        );
        System.out.println("January (winter) offset (NY): " + winterTime.getOffset());
    }

    public static void runAll() {
        runBasicTimezoneHandling();
        runMeetingScheduling();
        runInstantComparison();
        runOffsetHandling();
    }
}


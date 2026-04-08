package org.example.jodatime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.Days;

public class JodaTimeTimezoneExample {

    public static void runBasicTimezoneHandling() {
        System.out.println("\n========== JodaTime: Timezone Handling ==========");
        
        DateTime nowUTC = DateTime.now(DateTimeZone.UTC);
        System.out.println("Current UTC: " + nowUTC);
        
        DateTime nowNY = DateTime.now(DateTimeZone.forID("America/New_York"));
        System.out.println("Current in NY: " + nowNY);
        
        DateTime nowLondon = DateTime.now(DateTimeZone.forID("Europe/London"));
        System.out.println("Current in London: " + nowLondon);
        
        DateTime nowTokyo = DateTime.now(DateTimeZone.forID("Asia/Tokyo"));
        System.out.println("Current in Tokyo: " + nowTokyo);
    }

    public static void runMeetingScheduling() {
        System.out.println("\n========== JodaTime: Meeting Scheduling Across Timezones ==========");
        
        LocalDateTime meetingTime = new LocalDateTime(2026, 5, 15, 10, 0, 0);
        DateTimeZone companyZone = DateTimeZone.forID("America/New_York");
        
        DateTime meeting = meetingTime.toDateTime(companyZone);
        System.out.println("Company meeting (NY): " + meeting);
        
        DateTime londonTime = meeting.withZone(DateTimeZone.forID("Europe/London"));
        System.out.println("London team time: " + londonTime);
        
        DateTime tokyoTime = meeting.withZone(DateTimeZone.forID("Asia/Tokyo"));
        System.out.println("Tokyo team time: " + tokyoTime);
        
        DateTime mumbaiTime = meeting.withZone(DateTimeZone.forID("Asia/Kolkata"));
        System.out.println("Mumbai team time: " + mumbaiTime);
    }

    public static void runMillisComparison() {
        System.out.println("\n========== JodaTime: Comparing Instants Across Timezones ==========");
        
        DateTime eventNY = new DateTime(
            2026, 6, 1, 18, 0, 0,
            DateTimeZone.forID("America/New_York")
        );
        
        DateTime eventTokyo = new DateTime(
            2026, 6, 2, 6, 0, 0,
            DateTimeZone.forID("Asia/Tokyo")
        );
        
        long millisNY = eventNY.getMillis();
        long millisTokyo = eventTokyo.getMillis();
        
        System.out.println("Event NY millis: " + millisNY);
        System.out.println("Event Tokyo millis: " + millisTokyo);
        System.out.println("Same instant in time? " + (millisNY == millisTokyo));
        
        long secondsBetween = (millisTokyo - millisNY) / 1000;
        System.out.println("Seconds between: " + secondsBetween);
    }

    public static void runOffsetHandling() {
        System.out.println("\n========== JodaTime: Offset Handling ==========");
        
        DateTime now = DateTime.now();
        DateTimeZone zone = now.getZone();
        System.out.println("Current timezone offset: " + zone.getOffset(now.getMillis()) + " ms");
        
        DateTime daylightTime = new DateTime(
            2026, 7, 15, 14, 0, 0,
            DateTimeZone.forID("America/New_York")
        );
        int offsetJuly = daylightTime.getZone().getOffset(daylightTime.getMillis());
        System.out.println("July (summer) offset (NY): " + offsetJuly + " ms (EDT)");
        
        DateTime winterTime = new DateTime(
            2026, 1, 15, 14, 0, 0,
            DateTimeZone.forID("America/New_York")
        );
        int offsetJan = winterTime.getZone().getOffset(winterTime.getMillis());
        System.out.println("January (winter) offset (NY): " + offsetJan + " ms (EST)");
    }

    public static void runAll() {
        runBasicTimezoneHandling();
        runMeetingScheduling();
        runMillisComparison();
        runOffsetHandling();
    }
}


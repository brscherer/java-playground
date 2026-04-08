package org.example.javatime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class JavaTimeMeetingScheduler {

    static class MeetingSlot {
        final String title;
        final ZonedDateTime startTime;
        final int durationMinutes;
        final List<String> attendeeTimezones;

        MeetingSlot(String title, ZonedDateTime startTime, int durationMinutes, List<String> timezones) {
            this.title = title;
            this.startTime = startTime;
            this.durationMinutes = durationMinutes;
            this.attendeeTimezones = timezones;
        }

        ZonedDateTime getEndTime() {
            return startTime.plusMinutes(durationMinutes);
        }

        void printSchedule() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");
            System.out.println("\n📅 Meeting: " + title);
            System.out.println("   Duration: " + durationMinutes + " minutes");
            
            for (String timezone : attendeeTimezones) {
                ZoneId zoneId = ZoneId.of(timezone);
                ZonedDateTime localStart = startTime.withZoneSameInstant(zoneId);
                ZonedDateTime localEnd = getEndTime().withZoneSameInstant(zoneId);
                System.out.println("   " + timezone + ": " + localStart.format(formatter) + " - " + 
                                 localEnd.format(DateTimeFormatter.ofPattern("HH:mm z")));
            }
        }

        boolean isFeasible() {
            return attendeeTimezones.stream()
                .allMatch(this::isReasonableTime);
        }

        private boolean isReasonableTime(String timezone) {
            ZoneId zoneId = ZoneId.of(timezone);
            ZonedDateTime localTime = startTime.withZoneSameInstant(zoneId);
            int hour = localTime.getHour();
            return hour >= 9 && hour < 18;
        }
    }

    public static void runSchedulerDemo() {
        System.out.println("\n========== Java Date-Time API: Meeting Scheduler Demo ==========");
        
        List<String> globalTeam = List.of("America/New_York", "Europe/London", "Asia/Tokyo");
        
        LocalDateTime proposedTime = LocalDateTime.of(2026, 5, 20, 14, 0, 0);
        ZoneId baseZone = ZoneId.of("America/New_York");
        ZonedDateTime baseMeeting = ZonedDateTime.of(proposedTime, baseZone);
        
        MeetingSlot meeting1 = new MeetingSlot("Sprint Planning", baseMeeting, 60, globalTeam);
        
        System.out.println("\n" + (meeting1.isFeasible() ? "✅ FEASIBLE" : "❌ NOT FEASIBLE"));
        meeting1.printSchedule();
        
        ZonedDateTime adjustedTime = baseMeeting.plusHours(7);
        MeetingSlot meeting2 = new MeetingSlot("Standup", adjustedTime, 30, globalTeam);
        
        System.out.println("\n" + (meeting2.isFeasible() ? "✅ FEASIBLE" : "❌ NOT FEASIBLE"));
        meeting2.printSchedule();
    }

    public static void runRecurringMeetings() {
        System.out.println("\n========== Java Date-Time API: Recurring Meetings ==========");
        
        LocalDateTime weeklyStart = LocalDateTime.of(2026, 5, 1, 13, 0, 0);
        ZoneId zone = ZoneId.of("America/New_York");
        
        System.out.println("Weekly meetings for May 2026:");
        for (int week = 0; week < 5; week++) {
            ZonedDateTime meetingTime = ZonedDateTime.of(weeklyStart.plusWeeks(week), zone);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm z");
            System.out.println("  Week " + (week + 1) + ": " + meetingTime.format(formatter));
        }
    }

    public static void runAvailabilityCheck() {
        System.out.println("\n========== Java Date-Time API: Availability Overlap Check ==========");
        
        ZoneId nyZone = ZoneId.of("America/New_York");
        ZoneId londonZone = ZoneId.of("Europe/London");
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        
        System.out.println("Finding 1-hour overlap for NY, London, Tokyo teams...");
        System.out.println("Working hours: 09:00 - 18:00 local time\n");
        
        for (int hour = 8; hour < 24; hour++) {
            ZonedDateTime testTime = ZonedDateTime.of(
                LocalDateTime.of(2026, 5, 20, hour, 0, 0), 
                nyZone
            );
            
            int londonHour = testTime.withZoneSameInstant(londonZone).getHour();
            int tokyoHour = testTime.withZoneSameInstant(tokyoZone).getHour();
            
            boolean nyOk = hour >= 9 && hour < 18;
            boolean londonOk = londonHour >= 9 && londonHour < 18;
            boolean tokyoOk = tokyoHour >= 9 && tokyoHour < 18;
            
            if (nyOk && londonOk && tokyoOk) {
                System.out.println("✅ Found overlap:");
                System.out.println("   NY: " + testTime.format(DateTimeFormatter.ofPattern("HH:mm z")));
                System.out.println("   London: " + testTime.withZoneSameInstant(londonZone)
                    .format(DateTimeFormatter.ofPattern("HH:mm z")));
                System.out.println("   Tokyo: " + testTime.withZoneSameInstant(tokyoZone)
                    .format(DateTimeFormatter.ofPattern("HH:mm z")));
                break;
            }
        }
    }

    public static void runAll() {
        runSchedulerDemo();
        runRecurringMeetings();
        runAvailabilityCheck();
    }
}


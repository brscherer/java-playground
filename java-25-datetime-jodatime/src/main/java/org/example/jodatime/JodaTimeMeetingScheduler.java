package org.example.jodatime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

public class JodaTimeMeetingScheduler {

    static class MeetingSlot {
        final String title;
        final DateTime startTime;
        final int durationMinutes;
        final List<String> attendeeTimezones;

        MeetingSlot(String title, DateTime startTime, int durationMinutes, List<String> timezones) {
            this.title = title;
            this.startTime = startTime;
            this.durationMinutes = durationMinutes;
            this.attendeeTimezones = timezones;
        }

        DateTime getEndTime() {
            return startTime.plusMinutes(durationMinutes);
        }

        void printSchedule() {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm z");
            System.out.println("\n📅 Meeting: " + title);
            System.out.println("   Duration: " + durationMinutes + " minutes");
            
            for (String timezone : attendeeTimezones) {
                DateTimeZone zoneId = DateTimeZone.forID(timezone);
                DateTime localStart = startTime.withZone(zoneId);
                DateTime localEnd = getEndTime().withZone(zoneId);
                System.out.println("   " + timezone + ": " + localStart.toString(formatter) + " - " + 
                                 localEnd.toString(DateTimeFormat.forPattern("HH:mm z")));
            }
        }

        boolean isFeasible() {
            return attendeeTimezones.stream()
                .allMatch(this::isReasonableTime);
        }

        private boolean isReasonableTime(String timezone) {
            DateTimeZone zone = DateTimeZone.forID(timezone);
            DateTime localTime = startTime.withZone(zone);
            int hour = localTime.getHourOfDay();
            return hour >= 9 && hour < 18;
        }
    }

    public static void runSchedulerDemo() {
        System.out.println("\n========== JodaTime: Meeting Scheduler Demo ==========");
        
        List<String> globalTeam = List.of("America/New_York", "Europe/London", "Asia/Tokyo");
        
        LocalDateTime proposedTime = new LocalDateTime(2026, 5, 20, 14, 0, 0);
        DateTimeZone baseZone = DateTimeZone.forID("America/New_York");
        DateTime baseMeeting = proposedTime.toDateTime(baseZone);
        
        MeetingSlot meeting1 = new MeetingSlot("Sprint Planning", baseMeeting, 60, globalTeam);
        
        System.out.println("\n" + (meeting1.isFeasible() ? "✅ FEASIBLE" : "❌ NOT FEASIBLE"));
        meeting1.printSchedule();
        
        DateTime adjustedTime = baseMeeting.plusHours(7);
        MeetingSlot meeting2 = new MeetingSlot("Standup", adjustedTime, 30, globalTeam);
        
        System.out.println("\n" + (meeting2.isFeasible() ? "✅ FEASIBLE" : "❌ NOT FEASIBLE"));
        meeting2.printSchedule();
    }

    public static void runRecurringMeetings() {
        System.out.println("\n========== JodaTime: Recurring Meetings ==========");
        
        LocalDateTime weeklyStart = new LocalDateTime(2026, 5, 1, 13, 0, 0);
        DateTimeZone zone = DateTimeZone.forID("America/New_York");
        
        System.out.println("Weekly meetings for May 2026:");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM dd, yyyy HH:mm z");
        for (int week = 0; week < 5; week++) {
            DateTime meetingTime = weeklyStart.plusWeeks(week).toDateTime(zone);
            System.out.println("  Week " + (week + 1) + ": " + meetingTime.toString(formatter));
        }
    }

    public static void runAvailabilityCheck() {
        System.out.println("\n========== JodaTime: Availability Overlap Check ==========");
        
        DateTimeZone nyZone = DateTimeZone.forID("America/New_York");
        DateTimeZone londonZone = DateTimeZone.forID("Europe/London");
        DateTimeZone tokyoZone = DateTimeZone.forID("Asia/Tokyo");
        
        System.out.println("Finding 1-hour overlap for NY, London, Tokyo teams...");
        System.out.println("Working hours: 09:00 - 18:00 local time\n");
        
        for (int hour = 8; hour < 24; hour++) {
            DateTime testTime = new DateTime(2026, 5, 20, hour, 0, 0, nyZone);
            
            int londonHour = testTime.withZone(londonZone).getHourOfDay();
            int tokyoHour = testTime.withZone(tokyoZone).getHourOfDay();
            
            boolean nyOk = hour >= 9 && hour < 18;
            boolean londonOk = londonHour >= 9 && londonHour < 18;
            boolean tokyoOk = tokyoHour >= 9 && tokyoHour < 18;
            
            if (nyOk && londonOk && tokyoOk) {
                DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm z");
                System.out.println("✅ Found overlap:");
                System.out.println("   NY: " + testTime.toString(fmt));
                System.out.println("   London: " + testTime.withZone(londonZone).toString(fmt));
                System.out.println("   Tokyo: " + testTime.withZone(tokyoZone).toString(fmt));
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


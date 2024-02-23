package com.sulsul.suldaksuldak.tool;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

@Slf4j
public class ScheduleTool {
    public static String generateCronStringByLocalDateTime(
            LocalDateTime currentDateTime
    ) {
//        LocalDateTime nextDateTime = currentDateTime.plusMinutes(1);
        int currentSec = currentDateTime.getSecond();
        int currentMinutes = (currentDateTime.getMinute()) % 60;
        int currentHour = currentDateTime.getHour();
        int currentDay = currentDateTime.getDayOfMonth();
        int currentMonth = currentDateTime.getMonthValue();
        int currentYear = currentDateTime.getYear();

        StringBuilder cron = new StringBuilder();
        cron.append(currentSec).append(" "); // Seconds (every second)

        int minutes = currentMinutes % 60;
        int hours = (currentMinutes / 60 + currentHour) % 24;
        int days = (currentMinutes / (60 * 24) + currentDay - 1) % currentDateTime.toLocalDate().lengthOfMonth() + 1;
        int months = currentMonth;
        int years = currentYear;

        if (minutes < currentMinutes) {
            hours++;
            if (hours >= 24) {
                hours = 0;
                days++;
                if (days > currentDateTime.toLocalDate().lengthOfMonth()) {
                    days = 1;
                    months++;
                    if (months > 12) {
                        months = 1;
                        years++;
                    }
                }
            }
        }

        cron.append(minutes).append(" "); // Minutes
        cron.append(hours).append(" "); // Hours
        cron.append(days).append(" "); // Days of the month
        cron.append(months).append(" "); // Months
        cron.append("? "); // Days of the week
        cron.append(years); // Years

        return cron.toString();
    }

    public static String generateCronString(int index) {
        // Get the current date and time in the specified time zone
        LocalDateTime currentDateTime = LocalDateTime.now(
                ZoneId.of("Asia/Seoul")
        );

        return generateCronStringByLocalDateTime(
                currentDateTime.plusMinutes(index)
        );
    }

    public static LocalDateTime convertToTime(long timeInMillis) {
        Instant instant = Instant.ofEpochMilli(timeInMillis);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String cronStrToRealCronStr(
            String cronStr
    ) {
        int hour = Integer.parseInt(cronStr.split(" ")[0]);
        int minute = Integer.parseInt(cronStr.split(" ")[1]);
        LocalDateTime reqDateTime = LocalDateTime.now().plusHours(hour).plusSeconds(minute * 60L);
        return ScheduleTool.generateCronStringByLocalDateTime(reqDateTime);
    }

    public static String cronStrToRealCronStrSec(
            Long sec
    ) {
        LocalDateTime reqDateTime = LocalDateTime.now().plusSeconds(sec);
        return ScheduleTool.generateCronStringByLocalDateTime(reqDateTime);
    }

    public static LocalDateTime cronToLocalDateTime(String cronExpression) {
        String[] cronFields = cronExpression.split(" ");
        int second = Integer.parseInt(cronFields[0]);
        int minute = Integer.parseInt(cronFields[1]);
        int hour = Integer.parseInt(cronFields[2]);
        int dayOfMonth = Integer.parseInt(cronFields[3]);
        int month = Integer.parseInt(cronFields[4]);
        int year = Integer.parseInt(cronFields[6]);

        return LocalDateTime.of(year, Month.of(month), dayOfMonth, hour, minute, second);
    }
}

package com.fromLab.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author wsh
 * @date 2019-12-27
 * The static utility class for the date translation and date calculation
 */
public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Convert the date to String
     *
     * @param date
     * @return String
     */
    public static String date2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    /**
     * Convert the string to Date
     *
     * @param dateString
     * @return date
     */
    public static Date string2Date(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Covert the date to localDateTime (jdk 1.8 support)
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Convert the localDateTime to date (jdk 1.8 support)
     *
     * @param localDateTime
     * @return date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * Output the day after date (jdk 1.8 support)
     *
     * @param date
     * @return date
     */
    public static Date plusOneDay(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        LocalDateTime result = localDateTime.plusDays(1);
        Date resultDate = localDateTime2Date(result);
        return resultDate;
    }

    /**
     * Convert the string to localDateTime (jdk 1.8 support)
     *
     * @param dataTime
     * @return localDateTime
     */
    public static LocalDateTime string2LocalDateTime(String dataTime) {
        Date date = string2Date(dataTime);
        return date2LocalDateTime(date);
    }

    /**
     * Convert the localDateTime to string (jdk 1.8 support)
     *
     * @param localDateTime
     * @return string
     */
    public static String localDateTime2String(LocalDateTime localDateTime) {
        Date date = localDateTime2Date(localDateTime);
        return date2String(date);
    }

}

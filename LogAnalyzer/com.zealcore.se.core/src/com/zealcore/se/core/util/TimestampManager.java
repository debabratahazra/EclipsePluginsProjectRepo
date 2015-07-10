package com.zealcore.se.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Utility class for Time related tasks
 * 
 * @author cafa
 * 
 */
public class TimestampManager {

    private static final String ON_PATTERN = " on pattern ";

    private static final String KK_MM_SS_PATTERN = "kk:mm:ss";

    // regular expressions
    private static final String STOPTIME_REGEXP = "StopTime:";

    private static final String USEC_REGEXP = "[ \\t]*?([0-9]{4}-[0-9]{2}-[0-9]{2})?[ \\t]?([0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{0,6})";

    private static final String NSEC_REGEXP = "[ \\t]*?([0-9]{4}-[0-9]{2}-[0-9]{2})?[ \\t]?([0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{0,9})";

    private static final String STOPTIME_USEC_REGEXP = TimestampManager.STOPTIME_REGEXP
            + TimestampManager.USEC_REGEXP;

    private static final String STOPTIME_NSEC_REGEXP = TimestampManager.STOPTIME_REGEXP
            + TimestampManager.NSEC_REGEXP;

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final long NANOS_PER_MILLI = 1000000L;

    public static final long NANOS_PER_HOUR = 3600000000000L;

    public static final long MILLIS_PER_HOUR = 3600000L;

    public static final long MILLIS_PER_SECOND = 1000L;

    public static final long MICROS_PER_MILLI = 1000L;

    public static final long MICROS_PER_SECOND = 1000000L;

    public static final long MICROS_PER_HOUR = 3600000000L;

    public static final long MICROS_PER_DAY = 86400000000L;

    public static final long NANOS_PER_NANO = 1;

    public static final long NANOS_PER_MICRO = 1000L;

    public static final long NANOS_PER_SECOND = 1000000000L;

    public static final long NANOS_PER_MINUTE = 60000000000L;

    public static final long NANOS_PER_DAY = 86400000000000L;

    protected static final int UTC_TIME_MICRO_START_IDX = 9;

    protected TimestampManager() {}

    /**
     * Returns a date represented as a String in long with nano second
     * resolution.
     * 
     * @param date
     * @return @ throws ImportException
     */
    public static long fromDateToLong(final String date)
    /*
     * throws ImportException
     */{
        final SimpleDateFormat df = new SimpleDateFormat();
        df.setTimeZone(TimestampUtil.TIMEZONE);
        df.applyPattern(TimestampManager.YYYY_MM_DD);
        final Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(df.parse(date));
        } catch (final ParseException e) {
            throw new RuntimeException(
                    "Error parsing date: " + date + TimestampManager.ON_PATTERN
                            + TimestampManager.YYYY_MM_DD, e);
        }
        return calendar.getTimeInMillis() * TimestampManager.NANOS_PER_MILLI;
    }

    public static long fromTimeToLong(final String time)
    /*
     * throws ImportException
     */{
        final SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern(TimestampManager.KK_MM_SS_PATTERN);
        df.setTimeZone(TimestampUtil.TIMEZONE);
        final Calendar calendar = new GregorianCalendar(TimestampUtil.TIMEZONE);

        // String timePart = time.substring(0, UTC_TIME_MICRO_START_IDX -
        // 1);
        try {
            calendar.setTime(df.parse(time));
        } catch (final ParseException e) {
            throw new RuntimeException("Failed to parse time:" + time
                    + " on pattern:" + TimestampManager.KK_MM_SS_PATTERN, e);
        }

        return calendar.getTimeInMillis() * TimestampManager.NANOS_PER_MILLI;
    }

    public static String getRegexpForMicroSec() {
        return TimestampManager.USEC_REGEXP;
    }

    public static String getRegexpForNanoSec() {
        return TimestampManager.NSEC_REGEXP;
    }

    public static String getRegexpForStoptimeMicroSec() {
        return TimestampManager.STOPTIME_USEC_REGEXP;
    }

    public static String getRegexpForStoptimeNanoSec() {
        return TimestampManager.STOPTIME_NSEC_REGEXP;
    }

    public static boolean verifyAllIsParsed(final String ts, final String regExp) {
        if (RegExpUtil.scanRegExp(ts, regExp).start() > 0
                || RegExpUtil.scanRegExp(ts, regExp).end() < ts.length()) {
            return false;
        }
        return true;
    }
}

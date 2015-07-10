/**
 * 
 */
package com.zealcore.se.iw.types.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.MatchResult;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.RegExpUtil;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * @author cafa
 * 
 */
public class TimestampTypeTimeMicrosec implements ITimestampType {
    private static final String USING_REGULAR_EXPRESSION = " using regular expression ";

    private static final String ON_PATTERN = " on pattern ";

    private static final String ERROR_PARSING_TIME = "Error parsing time: ";

    private static final int USEC_GROUP = 4;

    private static final int SECOND_GROUP = 3;

    private static final int MINUTE_GROUP = 2;

    private static final int HOUR_GROUP = 1;

    private static final String INTERNAL_KK_MM_SS = "kk:mm:ss";

    private static final String KK_MM_SS = "kk:mm:ss:uuuuuu";

    private static final String TIMESTAMP_HMS_US = "TIMESTAMP_HMS_US";

    private static final String USEC_REGEXP = "([0-9]{2}):([0-9]{2}):([0-9]{2}):([0-9]{1,6})";

    public static final long MILLIS_PER_HOUR = 3600000L;

    public static final long MICROS_PER_MILLI = 1000L;

    public static final long NANOS_PER_MICRO = 1000L;

    public static final long NANOS_PER_MILLI = 1000000L;

    protected static final int UTC_TIME_MICRO_START_IDX = 9;

    public String getLabel() {
        return "Timestamp (H:M:S:us)";
    }

    public Long valueOf(final String text) /* throws ImportException */{
        return TimestampTypeTimeMicrosec.fromTimeToLong(text);
    }

    /**
     * Returns the time stamp (as nano seconds) found in the String argument ts.
     * 
     * @param ts
     * @return time stamp in nano-seconds @ throws ImportException
     */
    public static long getTs(final String ts) /* throws ImportException */{
        final MatchResult result = RegExpUtil.scanRegExp(ts,
                TimestampTypeTimeMicrosec.USEC_REGEXP);
        if (result == null) {
            return 0;
        }
        return TimestampTypeTimeMicrosec.fromTimeToLong(result.group(1));
    }

    /**
     * "Converts" String to long. Returns time represented as a String as a long
     * with given resolution.
     * 
     * @param time
     * @return @ throws ImportException
     */
    public static long fromTimeToLong(final String time)
    /*
     * throws ImportException
     */{
        final SimpleDateFormat df = new SimpleDateFormat();
        df.setTimeZone(TimestampUtil.TIMEZONE);
        df.applyPattern(TimestampTypeTimeMicrosec.INTERNAL_KK_MM_SS);
        final Calendar calendar = new GregorianCalendar(TimeZone
                .getTimeZone("GMT"));
        try {
            final String timePart = time.substring(0,
                    TimestampTypeTimeMicrosec.UTC_TIME_MICRO_START_IDX - 1);
            calendar.setTime(df.parse(timePart));
        } catch (final ParseException e) {
            throw new RuntimeException(
                    TimestampTypeTimeMicrosec.ERROR_PARSING_TIME
                            + time
                            + TimestampTypeTimeMicrosec.ON_PATTERN
                            + TimestampTypeTimeMicrosec.KK_MM_SS
                            + TimestampTypeTimeMicrosec.USING_REGULAR_EXPRESSION
                            + TimestampTypeTimeMicrosec.USEC_REGEXP, e);
        }

        long base = calendar.getTimeInMillis();

        final long lo = Integer.parseInt(time
                .substring(TimestampTypeTimeMicrosec.UTC_TIME_MICRO_START_IDX));
        base *= TimestampTypeTimeMicrosec.NANOS_PER_MILLI;
        base += lo * TimestampTypeTimeMicrosec.NANOS_PER_MICRO;
        return base;
    }

    public String getId() {
        return TimestampTypeTimeMicrosec.TIMESTAMP_HMS_US;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String ts) {
        if (canMatch(ts)) {
            timestamp.setHour(RegExpUtil.scanRegExp(ts,
                    TimestampTypeTimeMicrosec.USEC_REGEXP).group(
                    TimestampTypeTimeMicrosec.HOUR_GROUP));
            timestamp.setMinute(RegExpUtil.scanRegExp(ts,
                    TimestampTypeTimeMicrosec.USEC_REGEXP).group(
                    TimestampTypeTimeMicrosec.MINUTE_GROUP));
            timestamp.setSecond(RegExpUtil.scanRegExp(ts,
                    TimestampTypeTimeMicrosec.USEC_REGEXP).group(
                    TimestampTypeTimeMicrosec.SECOND_GROUP));
            timestamp.setNs(RegExpUtil.scanRegExp(ts,
                    TimestampTypeTimeMicrosec.USEC_REGEXP).group(
                    TimestampTypeTimeMicrosec.USEC_GROUP)
                    + "000");
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException(
                    TimestampTypeTimeMicrosec.ERROR_PARSING_TIME
                            + ts
                            + TimestampTypeTimeMicrosec.ON_PATTERN
                            + TimestampTypeTimeMicrosec.KK_MM_SS
                            + TimestampTypeTimeMicrosec.USING_REGULAR_EXPRESSION
                            + TimestampTypeTimeMicrosec.USEC_REGEXP);
        }
    }

    public boolean canMatch(final String proposal) {
        final Scanner s = new Scanner(proposal);
        s.findInLine(TimestampTypeTimeMicrosec.USEC_REGEXP);
        try {
            s.match();
        } catch (final IllegalStateException e) {
            return false;
        }
        return TimestampManager.verifyAllIsParsed(proposal,
                TimestampTypeTimeMicrosec.USEC_REGEXP);
    }
}

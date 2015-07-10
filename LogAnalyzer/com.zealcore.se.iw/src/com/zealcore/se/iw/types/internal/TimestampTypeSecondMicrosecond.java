/**
 * 
 */
package com.zealcore.se.iw.types.internal;

import java.util.Scanner;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.RegExpUtil;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * @author cafa
 * 
 */
public final class TimestampTypeSecondMicrosecond implements ITimestampType {
    private static final int SECOND_GROUP = 1;

    private static final int USEC_GROUP = 2;

    private static final String USING_REGULAR_EXPRESSION = " using regular expression ";

    private static final String ON_PATTERN = " on pattern ";

    private static final String ERROR_PARSING_TIME = "Error parsing time: ";

    private static final String ID_TYPE_TIMESTAMP_YMD = "TYPE_TIMESTAMP_SECOND_MICROSECOND";

    private static final String KK_MM_SS = "s.uuuuuu";

    private static final String SECOND_USEC_REGEXP = "([0-9]{1,}).([0-9]{6})";

    public static final long NANOS_PER_MICRO = 1000L;

    private static final long SECONDS_PER_MICRO = 1000000L;

    public String getLabel() {
        return "Timestamp ([s.us])";
    }

    public Long valueOf(final String text) /* throws ImportException */{
        return TimestampTypeSecondMicrosecond.getTs(text);
    }

    /**
     * Returns the time stamp (as nano seconds) found in the String argument ts.
     * 
     * @param ts
     * @return time stamp in nano-seconds @ throws ImportException
     */
    public static long getTs(final String ts) /* throws ImportException */{
        return TimestampTypeSecondMicrosecond.fromTsLong(
                RegExpUtil.scanRegExp(ts,
                        TimestampTypeSecondMicrosecond.SECOND_USEC_REGEXP)
                        .group(1), RegExpUtil.scanRegExp(ts,
                        TimestampTypeSecondMicrosecond.SECOND_USEC_REGEXP)
                        .group(1 + 1));
    }

    /**
     * Returns a timestamp [seconds.microseconds] represented as strings in long
     * with nano second resolution.
     * 
     * @param date
     * @return -
     */
    public static long fromTsLong(final String sec, final String usec) {
        return (Long.parseLong(sec)
                * TimestampTypeSecondMicrosecond.SECONDS_PER_MICRO + Long
                .parseLong(usec))
                * TimestampTypeSecondMicrosecond.NANOS_PER_MICRO;
    }

    public String getId() {
        return TimestampTypeSecondMicrosecond.ID_TYPE_TIMESTAMP_YMD;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String ts) {
        if (canMatch(ts)) {
            timestamp.setSecond(RegExpUtil.scanRegExp(ts,
                    TimestampTypeSecondMicrosecond.SECOND_USEC_REGEXP).group(
                    TimestampTypeSecondMicrosecond.SECOND_GROUP));
            timestamp.setNs(RegExpUtil.scanRegExp(ts,
                    TimestampTypeSecondMicrosecond.SECOND_USEC_REGEXP).group(
                    TimestampTypeSecondMicrosecond.USEC_GROUP)
                    + "000");
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException(
                    TimestampTypeSecondMicrosecond.ERROR_PARSING_TIME
                            + ts
                            + TimestampTypeSecondMicrosecond.ON_PATTERN
                            + TimestampTypeSecondMicrosecond.KK_MM_SS
                            + TimestampTypeSecondMicrosecond.USING_REGULAR_EXPRESSION
                            + TimestampTypeSecondMicrosecond.SECOND_USEC_REGEXP);
        }
    }

    public boolean canMatch(final String proposal) {
        final Scanner s = new Scanner(proposal);
        s.findInLine(TimestampTypeSecondMicrosecond.SECOND_USEC_REGEXP);
        try {
            s.match();

        } catch (final IllegalStateException e) {
            return false;
        }
        return TimestampManager.verifyAllIsParsed(proposal,
                TimestampTypeSecondMicrosecond.SECOND_USEC_REGEXP);
    }
}

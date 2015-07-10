package com.zealcore.se.iw.types.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.RegExpUtil;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class TimestampTypeYMD implements ITimestampType {
    private static final String USING_REGULAR_EXPRESSION = " using regular expression ";

    private static final int YEAR_GROUP = 1;

    private static final int DAY_GROUP = 3;

    private static final int MONTH_GROUP = 2;

    private static final String USEC_REGEXP = "([0-9]{2,4})-([0-9]{2})-([0-9]{2})";

    private static final String ERROR_PARSING_DATE = "Error parsing date: ";

    private static final String ON_PATTERN = " on pattern ";

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String ts) {
        if (canMatch(ts)) {
            timestamp.setYear(RegExpUtil.scanRegExp(ts,
                    TimestampTypeYMD.USEC_REGEXP).group(
                    TimestampTypeYMD.YEAR_GROUP));
            timestamp.setMonth(RegExpUtil.scanRegExp(ts,
                    TimestampTypeYMD.USEC_REGEXP).group(
                    TimestampTypeYMD.MONTH_GROUP));
            timestamp.setDay(RegExpUtil.scanRegExp(ts,
                    TimestampTypeYMD.USEC_REGEXP).group(
                    TimestampTypeYMD.DAY_GROUP));
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException(TimestampTypeYMD.ERROR_PARSING_DATE + ts
                    + TimestampTypeYMD.ON_PATTERN + TimestampManager.YYYY_MM_DD
                    + TimestampTypeYMD.USING_REGULAR_EXPRESSION
                    + TimestampTypeYMD.USEC_REGEXP);
        }
    }

    private static final String ID_TYPE_TIMESTAMP_YMD = "TYPE_TIMESTAMP_YMD";

    public void modify(final GenericLogEvent event, final String value)
    /*
     * throws ImportException
     */{
        event.setTs(valueOf(value) + event.getTs());
    }

    public String getLabel() {
        return "Timestamp (Y-M-D)";
    }

    public Long valueOf(final String text) /* throws ImportException */{
        return TimestampTypeYMD.fromDateToLong(text);
    }

    /**
     * Returns the time stamp (as nano seconds) found in the String argument ts.
     * 
     * @param ts
     * @return time stamp in nano-seconds @ throws ImportException
     */
    public static long getTs(final String ts) /* throws ImportException */{
        return TimestampTypeYMD.fromDateToLong(RegExpUtil.scanRegExp(ts,
                TimestampTypeYMD.USEC_REGEXP).group(1));
    }

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
            throw new RuntimeException(TimestampTypeYMD.ERROR_PARSING_DATE
                    + date + TimestampTypeYMD.ON_PATTERN
                    + TimestampManager.YYYY_MM_DD
                    + TimestampTypeYMD.USING_REGULAR_EXPRESSION
                    + TimestampTypeYMD.USEC_REGEXP, e);
        }
        return calendar.getTimeInMillis() * TimestampManager.NANOS_PER_MILLI;
    }

    public String getId() {
        return TimestampTypeYMD.ID_TYPE_TIMESTAMP_YMD;
    }

    public boolean canMatch(final String proposal) {
        final Scanner s = new Scanner(proposal);
        s.findInLine(TimestampTypeYMD.USEC_REGEXP);
        try {
            s.match();
        } catch (final IllegalStateException e) {
            return false;
        }
        return TimestampManager.verifyAllIsParsed(proposal,
                TimestampTypeYMD.USEC_REGEXP);
    }
}

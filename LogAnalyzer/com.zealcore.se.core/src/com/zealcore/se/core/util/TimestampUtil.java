/**
 * 
 */
package com.zealcore.se.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Utility class for Time related tasks
 * 
 * @author cafa
 */
public class TimestampUtil {

    private static final String ON_PATTERN = " on pattern:";

    public static final long NANOS_PER_MILLI = 1000000L;

    public static final long NANOS_PER_HOUR = 3600000000000L;

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final int HUNDRED_YEARS = 100;

    private static final int TEN_YEARS = 50;

    private static final int FIFTY_YEARS = 50;

    private static final String COLON = ":";

    private static final String ZERO_ZERO = "00";

    private static final String ZERO_ONE = "01";

    private static final String LINE = "-";

    private String year = "1970";

    private String month = TimestampUtil.ZERO_ONE;

    private String day = TimestampUtil.ZERO_ONE;

    private String hour = TimestampUtil.ZERO_ZERO;

    private String minute = TimestampUtil.ZERO_ZERO;

    private String second = TimestampUtil.ZERO_ZERO;

    private String ns = TimestampUtil.ZEROSTRING;

    private static final long MILLION = 1000000L;

    private static final long BILLION = 1000000000L;

    private static final String ZEROSTRING = "0";

    public static final TimeZone TIMEZONE = TimeZone.getTimeZone("GMT");

    public TimestampUtil() {}

    /**
     * @return the time stamp in nano seconds.
     */
    public long getTs() /* throws ImportException */{
        final Calendar calendar = new GregorianCalendar(TIMEZONE);

        final String dateStr = this.year + TimestampUtil.LINE + this.month
                + TimestampUtil.LINE + this.day;

        final SimpleDateFormat df = new SimpleDateFormat();
        df.setTimeZone(TIMEZONE);
        df.applyPattern(TimestampUtil.YYYY_MM_DD);

        try {
            Date date = df.parse(dateStr);
            calendar.setTime(date);
        } catch (final ParseException e) {
            throw new RuntimeException("Failed to parse date:" + dateStr
                    + ON_PATTERN + TimestampUtil.YYYY_MM_DD, e);
        }

        if (calendar.getTimeInMillis() < 0L) {
            throw new RuntimeException("Negative timestamp when parsing date:"
                    + dateStr + ON_PATTERN + TimestampUtil.YYYY_MM_DD
                    + " Timestamp is:" + calendar.getTimeInMillis());
        }

        final String time = this.hour + TimestampUtil.COLON + this.minute
                + TimestampUtil.COLON + this.second;

        long fromTimeToLong = TimestampManager.fromTimeToLong(time);
        return calendar.getTimeInMillis() * TimestampManager.NANOS_PER_MILLI
                + fromTimeToLong + Long.parseLong(this.ns);
    }

    static String getAsNineChars(final Long val) {
        if (val < 0L) {
            throw new RuntimeException(
                    "Negative timestamp found on an event, this should not occur! Timstamp is "
                            + val);
        }
        final String zeros = "000000000";
        return zeros.substring(val.toString().length()) + val.toString();
    }

    public static String tsToDate(final long ts) {

        final Calendar d = new GregorianCalendar(TimestampUtil.TIMEZONE);
        d.setTimeInMillis(0);
        d.setTimeInMillis(ts / TimestampUtil.MILLION);
        final String date =

        d.get(Calendar.YEAR) + TimestampUtil.LINE
                + getAsTwoChars(d.get(Calendar.MONTH) + 1) + TimestampUtil.LINE
                + getAsTwoChars(d.get(Calendar.DATE)) + " "
                + getAsTwoChars(d.get(Calendar.HOUR_OF_DAY))
                + TimestampUtil.COLON + getAsTwoChars(d.get(Calendar.MINUTE))
                + TimestampUtil.COLON + getAsTwoChars(d.get(Calendar.SECOND))
                + TimestampUtil.COLON
                + TimestampUtil.getAsNineChars((ts % TimestampUtil.BILLION));

        return date;
    }

    public final String getDay() {
        return this.day;
    }

    public final void setDay(final String day) {
        this.day = day;
    }

    public final String getMonth() {
        return this.month;
    }

    public final void setMonth(final String month) {
        this.month = month;
    }

    public final String getYear() {
        return this.year;
    }

    public static String getAsTwoChars(final Integer val) {
        if (val < 10) {
            return TimestampUtil.ZEROSTRING + val.toString();
        }
        return val.toString();
    }

    /**
     * Sets the year part of the timestamp. If the value is less than 50, it is
     * assumed that is 2000 century, else, if the value is less than 100 it is
     * assumed that it is 1900 century, else it is assumed that it is a complete
     * 4 digit value.
     * 
     * @param year
     */
    public final void setYear(final String year) {
        final int y = Integer.parseInt(year);
        if (y < TimestampUtil.TEN_YEARS) {
            this.year = "200" + Integer.toString(y);
        } else if (y < TimestampUtil.FIFTY_YEARS) {
            this.year = "20" + Integer.toString(y);
        } else if (y < TimestampUtil.HUNDRED_YEARS) {
            this.year = "19" + Integer.toString(y);
        } else {
            this.year = year;
        }
    }

    public final String getHour() {
        return this.hour;
    }

    public final void setHour(final String hour) {
        this.hour = hour;
    }

    public final String getMinute() {
        return this.minute;
    }

    public final void setMinute(final String minute) {
        this.minute = minute;
    }

    public final String getNs() {
        return this.ns;
    }

    public final void setNs(final String ns) {
        this.ns = ns;
    }

    public final String getSecond() {
        return this.second;
    }

    public final void setSecond(final String second) {
        this.second = second;
    }
}

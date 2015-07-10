/**
 * 
 */
package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public final class TimeFormat {
    private static final String NS = "ns";

    private static final int THOUSAND = 1000;

    private static final int THREE_LETTERS = 3;

    private static final int NINE_LETTERS = 9;

    private static final int TWO_LETTERS = 2;

    /** The a value representing s to ns factor (10^9) */
    static final long S_TO_NS_FACTOR = 1000000000;

    /** The a value representing s to us factor (10^6) */
    static final long S_TO_US_FACTOR = 1000000;

    private final long timeInNanos;

    private String toString;

    private Calendar calendar;

    private static final long NS_PER_DAY = TimeFormat.S_TO_NS_FACTOR * 60 * 60 * 24;

    private static final long NS_PER_HOUR = TimeFormat.S_TO_NS_FACTOR * 60 * 60;

    private static final long NS_PER_MINUT = TimeFormat.S_TO_NS_FACTOR * 60;

    private static final String YEAR = "Y";

    private static final String MONTH = "M";

    private static final String DAY = "D";

    private static final String HOUR = "h";

    private static final String MINUTE = "m";

    private static final String SECOND = "s";

    private static final String MS = "ms";

    private static final String US = "us";

    private static final String COLON = ":";

    private static final String SLASH = "/";

    private static final String DASH = "-";

    private static final String SPACE = " ";

    private static final String DOT = ".";

    private TimeFormat(final long timeInNanos) {
        this.timeInNanos = timeInNanos;
    }

    /**
     * Formats the time in nanoseconds according to the format string. Special
     * tokens are Y M D h m s ms us ns If these are separated by non letters as
     * in "Y-M-D" they will expand to numeric values. In the case of "ms" it
     * will not expand
     * 
     * @param timeInNs
     *            the time in ns
     * @param format
     *            the format
     */

    public static String format(final String format, final long timeInNs) {
        final List<String> tokens = filter(TimeFormat.tokenize(format));
        return TimeFormat.format(new TimeFormat(timeInNs), tokens);
    }

    /**
     * 
     * Formats a timestamp in a d h:m:s:ns format. The timestamp is non-relative
     * and will display number of days > 365.
     * 
     * This is useful for displaying distances between two timestamps.
     * 
     * 
     * for instance TimeFormat.format( 10^9 * 60 * 60 * 24)
     * 
     * 1 days 00:00:00:000000000
     * 
     * @param relative
     * @return -
     */
    public static String format(final long relative) {

        long delta = relative;

        final long days = delta / TimeFormat.NS_PER_DAY;
        delta -= days * TimeFormat.NS_PER_DAY;

        final int hours = (int) (delta / TimeFormat.NS_PER_HOUR);
        delta -= hours * TimeFormat.NS_PER_HOUR;

        final int minutes = (int) (delta / TimeFormat.NS_PER_MINUT);
        delta -= minutes * TimeFormat.NS_PER_MINUT;

        final int seconds = (int) (delta / TimeFormat.S_TO_NS_FACTOR);
        delta -= seconds * TimeFormat.S_TO_NS_FACTOR;

        // delta is now ns
        final int ns = (int) delta;

        final StringBuilder bld = new StringBuilder();
        final int twoDigits = 2;

        if (days > 0) {
            bld.append(days);
            bld.append(" days, ");
        }

        if (bld.length() > 0 || hours > 0) {
            bld.append(TimeFormat.numberFormat(hours, twoDigits));
            bld.append(" h, ");
        }

        if (bld.length() > 0 || minutes > 0) {
            bld.append(TimeFormat.numberFormat(minutes, twoDigits));
            bld.append(" min, ");
        }
        if (bld.length() > 0 || seconds > 0) {
            bld.append(TimeFormat.numberFormat(seconds, twoDigits));
            bld.append(" sec, ");
        }

        bld.append(TimeFormat.numberFormat(ns, 9));
        bld.append(SPACE + NS);

        final String value = bld.toString();
        return value;
    }

    private static List<String> tokenize(final String format) {
        StringBuilder tokenizer = new StringBuilder();
        final List<String> tokens = new ArrayList<String>();
        final int max = format.length();
        for (int i = 0; i < max; ++i) {
            final char letter = format.charAt(i);
            if (Character.isLetter(letter)) {
                tokenizer.append(letter);
            } else {
                tokens.add(tokenizer.toString());
                tokens.add(Character.toString(letter));
                tokenizer = new StringBuilder();
            }
        }
        if (tokenizer.length() > 0) {
            tokens.add(tokenizer.toString());
        }
        return tokens;
    }

    private static String format(final TimeFormat ts, final List<String> tokens) {
        final StringBuilder builder = new StringBuilder();
        for (final String token : tokens) {
            if (token.startsWith(YEAR)) {
                ts.appendYear(builder);
            } else if (token.startsWith(MONTH)) {
                ts.appendMonth(builder);
            } else if (token.startsWith(DAY)) {
                ts.appendDay(builder);
            } else if (token.startsWith(HOUR)) {
                ts.appendHour(builder);
            } else if (token.startsWith(MS)) {
                ts.appendMs(builder);
            } else if (token.startsWith(MINUTE)) {
                ts.appendMinute(builder);
            } else if (token.startsWith(SECOND)) {
                ts.appendSecond(builder);
            } else if (token.startsWith(US)) {
                ts.appendUs(builder);
            } else if (token.startsWith(NS)) {
                ts.appendNs(builder);
            } else {
                builder.append(token);
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        if (this.toString != null) {
            return this.toString;
        }

        final StringBuilder builder = new StringBuilder();
        appendDate(builder);
        builder.append(' ');
        appendClock(builder);
        builder.append(' ');

        appendResolution(builder);
        this.toString = builder.toString();
        return this.toString;
    }

    private void appendResolution(final StringBuilder builder) {
        appendNs(builder);
    }

    private void appendClock(final StringBuilder builder) {
        appendHour(builder);
        builder.append(':');
        appendMinute(builder);
        builder.append(':');
        appendSecond(builder);
    }

    private void appendSecond(final StringBuilder builder) {
        builder.append(TimeFormat.numberFormat(getCalendar().get(
                Calendar.SECOND), TimeFormat.TWO_LETTERS));
    }

    private void appendMinute(final StringBuilder builder) {
        builder.append(TimeFormat.numberFormat(getCalendar().get(
                Calendar.MINUTE), TimeFormat.TWO_LETTERS));
    }

    private void appendHour(final StringBuilder builder) {
        builder.append(TimeFormat.numberFormat(getCalendar().get(
                Calendar.HOUR_OF_DAY), TimeFormat.TWO_LETTERS));
    }

    private void appendDate(final StringBuilder builder) {
        appendYear(builder);
        builder.append('-');
        appendMonth(builder);
        builder.append('-');
        appendDay(builder);
    }

    private StringBuilder appendDay(final StringBuilder builder) {
        return builder.append(TimeFormat.numberFormat(getCalendar().get(
                Calendar.DAY_OF_MONTH), TimeFormat.TWO_LETTERS));
    }

    private void appendMonth(final StringBuilder builder) {
        final int month = getCalendar().get(Calendar.MONTH) + 1;
        builder.append(TimeFormat.numberFormat(month, TimeFormat.TWO_LETTERS));
    }

    private void appendYear(final StringBuilder builder) {
        final int year = getCalendar().get(Calendar.YEAR);
        builder.append(year);
    }

    private void appendNs(final StringBuilder builder) {
        builder.append(TimeFormat.numberFormat(this.timeInNanos
                % TimeFormat.S_TO_NS_FACTOR, TimeFormat.NINE_LETTERS));
    }

    private void appendUs(final StringBuilder builder) {
        appendMs(builder);
        builder.append(TimeFormat.numberFormat(this.timeInNanos
                % TimeFormat.S_TO_US_FACTOR / TimeFormat.THOUSAND,
                TimeFormat.THREE_LETTERS));
    }

    private void appendMs(final StringBuilder builder) {
        builder.append(TimeFormat.numberFormat(getCalendar().get(
                Calendar.MILLISECOND), TimeFormat.THREE_LETTERS));
    }

    private static String numberFormat(final long num, final int length) {
        final StringBuilder b = new StringBuilder();
        b.insert(0, num);
        while (b.length() < length) {
            b.insert(0, '0');
        }
        return b.toString();
    }

    private Calendar getCalendar() {
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
            getCalendar().setTimeInMillis(
                    this.timeInNanos / TimeFormat.S_TO_US_FACTOR);
        }
        return this.calendar;
    }

    private static List<String> filter(final List<String> tokens) {
        List<String> filterTokens = new ArrayList<String>();
        for (Iterator<String> iterator = tokens.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            if ((string.startsWith(YEAR)) || (string.startsWith(MONTH))
                    || (string.startsWith(DAY)) || (string.startsWith(HOUR))
                    || (string.startsWith(MS)) || (string.startsWith(MINUTE))
                    || (string.startsWith(SECOND)) || (string.startsWith(US))
                    || (string.startsWith(NS))) {
                filterTokens.add(string);
            } else {
                try {
                    Integer.parseInt(string);
                } catch (NumberFormatException nfe) {
                    if (string.startsWith(COLON)) {
                        filterTokens.add(COLON);
                    } else if (string.startsWith(SLASH)) {
                        filterTokens.add(SLASH);
                    } else if (string.startsWith(DASH)) {
                        filterTokens.add(DASH);
                    } else if (string.equals(SPACE)) {
                        filterTokens.add(SPACE);
                    } else if (string.equals(DOT)) {
                        filterTokens.add(DOT);
                    }
                }
            }
        }
        return filterTokens;
    }
}

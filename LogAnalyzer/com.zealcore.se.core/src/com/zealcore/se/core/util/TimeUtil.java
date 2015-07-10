/*
 * 
 */
package com.zealcore.se.core.util;

/**
 * Utility class for Time related tasks
 */
public final class TimeUtil {

    private static final int TEN = 10;

    private static final int THREE_ZEROES = 3;

    private static final int SIX_ZEROS = 6;

    private TimeUtil() {}

    public enum BASE {
        MILLI(3, "m"), MICRO(6, "\u00B5"), NANO(9, "n");

        private final int exp;

        private String abbr;

        BASE(final int exp, final String abbr) {

            this.exp = exp;
            this.abbr = abbr;

        }

        public int getExp() {
            return exp;
        }

        @Override
        public String toString() {
            return abbr;
        }
    }

    /**
     * Returns the lowest base for a timestamp value.
     * 
     * @param value
     *                the value
     * 
     * @return the lowst BASE
     */
    public static BASE lowestBase(final long value) {
        final String str = Long.toString(value);
        int numZeros = 0;
        int i = 0;
        for (i = str.length() - 1; i >= 0; --i) {
            if (str.charAt(i) != '0') {
                break;
            }
            ++numZeros;
        }

        if (numZeros >= TimeUtil.SIX_ZEROS) {
            return BASE.MILLI;
        } else if (numZeros >= TimeUtil.THREE_ZEROES) {
            return BASE.MICRO;
        }

        return BASE.NANO;
    }

    /**
     * Returns the ratio a value has relative a min and max timestamp.
     * 
     * @param value
     *                the value
     * @param min
     *                the min ts
     * @param max
     *                the max ts
     * 
     * @return the ratio
     */
    public static double ratio(final long value, final long min, final long max) {
        return (double) (value - min) / (double) (max - min);
    }

    /**
     * Returns the ratio a value has relative a Span instance.
     * 
     * @param value
     *                the value
     * @param span
     *                the span
     * 
     * @return the ratio
     */
    public static double ratio(final long value, final Span span) {
        return TimeUtil.ratio(value, span.getStart(), span.getEnd());
    }

    /**
     * Calculates a coordinate by ratio from a given Span. The method is passed
     * a span which contains a start and end coordinate, the ratio is used to
     * derive where on this span the coordiate resides.
     * 
     * @param ratio
     *                the ratio
     * @param span
     *                the span
     * 
     * @return the coordinate
     */
    public static double coordinateByRatio(final double ratio, final Span span) {
        return ratio * (span.getEnd() - span.getStart()) + span.getStart();
    }

    /**
     * 
     * @param value
     *                a value in nanos to convert
     * @param base
     *                the base to convert to
     * @return a value with trailing zeros removed
     */
    public static long convertTo(final long value, final BASE base) {
        final BASE lowest = TimeUtil.lowestBase(value);
        if (base.compareTo(lowest) < 0) {
            throw new NumberFormatException("Cannot convert " + value + " to "
                    + base + " lowest possible base is " + lowest);
        }

        final int removeTrailing = BASE.NANO.getExp() - base.getExp();

        if (removeTrailing > 0) {
            final String strValue = Long.toString(value);
            final int cut = strValue.length() - removeTrailing;
            return Long.valueOf(strValue.substring(0, cut));
        }

        return value;
    }

    /**
     * Returns a new long value with a specified granularity. Ie the value 1234
     * with a granularity of 2 will return 1200, 1234 with granularity 3 returns
     * 1230 and so on.
     * 
     * @param value
     *                the value
     * @param granul
     *                the granularity
     * 
     * @return the new long value using the specified granularity
     */
    public static long granularity(final long value, final int granul) {
        final int numDigits = Long.toString(value).length();
        final long d = (int) Math.pow(TimeUtil.TEN, numDigits - granul);
        if (d < 1) {
            return value;
        }
        final long temp = value / d;
        return temp * d;
    }

}

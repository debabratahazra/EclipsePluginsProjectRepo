/*
 * 
 */
package com.zealcore.se.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ITimed;

/**
 * The Span is a lightweight span useful in cojunction with {@link TimeUtil}.
 * 
 * @see TimeUtil
 */
public final class Span {

    private static final int PRIME = 31;

    private static final int INT_BITS = 32;

    private final long start;

    private final long end;

    @Override
    public int hashCode() {
        final int prime = Span.PRIME;
        int result = 1;
        result = prime * result + (int) (this.end ^ this.end >>> Span.INT_BITS);
        result = prime * result
                + (int) (this.start ^ this.start >>> Span.INT_BITS);
        return result;
    }

    /**
     * Checks if a value is contained within the Span
     * 
     * @param value
     *                the value to check
     * 
     * @return true, if value is contained within the span
     */
    public boolean contains(final long value) {
        if (value == this.start || value == this.end) {
            return true;
        }
        return value <= this.end && value >= this.start;
    }

    /**
     * Checks if value is contained exclusive within the Span. that is value <
     * end and value < start. This is different than {@link Span#contains(long)}
     * which is inclusive.
     * 
     * @param value
     *                the value
     * 
     * @return true, if value is contained exclusive
     */
    public boolean containsExclusive(final long value) {
        return value < this.end && value > this.start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Span other = (Span) obj;
        if (this.end != other.end) {
            return false;
        }
        if (this.start != other.start) {
            return false;
        }
        return true;
    }

    private Span(final long start, final long end) {
        this.start = start;
        this.end = end;

    }

    /**
     * Gets the end value.
     * 
     * @return the end
     */
    public long getEnd() {
        return this.end;
    }

    /**
     * Gets the start.
     * 
     * @return the start
     */
    public long getStart() {
        return this.start;
    }

    /**
     * Returns a new Span representing the specified start and end values.
     * 
     * @param start
     *                the start value
     * @param end
     *                the end value
     * 
     * @return a new Span
     */
    public static Span valueOf(final long start, final long end) {
        return new Span(start, end);
    }

    /**
     * Gets the width of the span. This is a convenience method for getEnd() -
     * getStart();
     * 
     * @return the width of the span
     */
    public double getWidth() {
        return getEnd() - getStart();
    }

    /**
     * Creates a new Span representing the specified duration value.
     * 
     * @param duration
     *                the duration
     * 
     * @return the span
     */
    public static Span valueOf(final IDuration duration) {

        return Span.valueOf(duration.getStartTime(), duration.getStartTime()
                + duration.getDurationTime());
    }

    /**
     * Creates spans which are equal to that of the durations
     * 
     * @param activities
     * @return -
     */
    public static Collection<Span> valueOfDurations(
            final Collection<? extends IDuration> activities) {

        final Collection<Span> spans = new ArrayList<Span>();

        long lastEnd = -1;
        for (final IDuration duration : activities) {
            if (lastEnd != -1) {
                if (duration.getStartTime() != lastEnd) {
                    spans.add(Span.valueOf(lastEnd, duration.getStartTime()));
                }
            }
            lastEnd = duration.getStartTime() + duration.getDurationTime();
            spans.add(Span.valueOf(duration));
        }

        return spans;
    }

    /**
     * Returns a new Collection of Span build from a list of ILogEvents.
     * 
     * @return Collecion of Span
     */
    public static Collection<Span> valueOf(
            final List<? extends ILogEvent> events) {
        return Span.valueOfTimed(events);
    }

    private static Collection<Span> valueOfTimed(
            final List<? extends ITimed> events) {
        if (events.size() < 1) {
            throw new IllegalArgumentException("The list of events " + events
                    + " does not have any elements " + Span.class.getName());
        }

        ITimed last = events.get(0);
        final Collection<Span> spans = new ArrayList<Span>();
        for (int i = 1; i < events.size(); i++) {
            spans.add(Span.valueOf(last.getTimeReference(), events.get(i)
                    .getTimeReference()));
            last = events.get(i);
        }

        return spans;
    }
}

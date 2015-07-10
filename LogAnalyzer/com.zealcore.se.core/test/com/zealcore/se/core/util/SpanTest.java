package com.zealcore.se.core.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import com.zealcore.se.core.model.AbstractDuration;
import com.zealcore.se.core.model.AbstractLogEvent;

public class SpanTest {

    @Test
    public void testSpanHashcodeEqual() {
        Span subject = Span.valueOf(0, 100);
        Span two = Span.valueOf(0, 100);
        assertTrue(subject.hashCode() > 0);
        assertTrue(subject.equals(two));
    }

    @Test
    public void testContains() {
        Span subject = Span.valueOf(0, 100);

        assertTrue(subject.contains(1));
        assertTrue(subject.contains(99));
        assertTrue(subject.contains(0));
        assertFalse(subject.contains(-1));
        assertTrue(subject.contains(100));
        assertFalse(subject.contains(101));
    }

    @Test
    public void testContainsExclusive() {
        Span subject = Span.valueOf(0, 100);

        assertTrue(subject.containsExclusive(1));
        assertTrue(subject.containsExclusive(99));
        assertFalse(subject.containsExclusive(0));
        assertFalse(subject.containsExclusive(-1));
        assertFalse(subject.containsExclusive(100));
        assertFalse(subject.containsExclusive(101));
    }

    @Test
    public void testValueOfDuration() {
        AbstractDuration data = new AbstractDuration() {};
        AbstractLogEvent zero = new AbstractLogEvent() {};
        AbstractLogEvent hundred = new AbstractLogEvent() {};
        zero.setTs(0);
        hundred.setTs(100);
        data.setStartEvent(zero);
        data.setStopEvent(hundred);
        Span subject = Span.valueOf(data);
        assertTrue(subject.equals(Span.valueOf(0, 100)));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfEmptyTimed() {
        Span.valueOf(Collections.EMPTY_LIST);
    }
}

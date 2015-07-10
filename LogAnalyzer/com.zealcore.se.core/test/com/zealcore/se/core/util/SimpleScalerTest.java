package com.zealcore.se.core.util;

import junit.framework.TestCase;

public class SimpleScalerTest extends TestCase {

    public void testToTimestampInt() {
        SimpleScaler subject = new SimpleScaler(Span.valueOf(0, 100), Span.valueOf(0, 1000));
        assertEquals(0, subject.toTimestamp(1));
        assertEquals(90, subject.toTimestamp(900));
        assertEquals(100, subject.toTimestamp(1000));
        assertEquals(100, subject.toTimestamp(10000));
        assertEquals(0, subject.toTimestamp(-1));
    }

    public void testToScreenLong() {
        SimpleScaler subject = new SimpleScaler(Span.valueOf(0, 100), Span.valueOf(0, 1000));
        assertEquals(10, subject.toScreen(1));
        assertEquals(200, subject.toScreen(20));
        assertEquals(1000, subject.toScreen(100));
        assertEquals(1000, subject.toScreen(1000));
        assertEquals(0, subject.toScreen(-1));
    }

    public void testToScreenLongDouble() {
        SimpleScaler subject = new SimpleScaler(Span.valueOf(0, 100), Span.valueOf(0, 1000));
        assertEquals(10.5, subject.toScreen(1L, 1.0));
        assertEquals(200.5, subject.toScreen(20L, 1.0));
        assertEquals(1000.5, subject.toScreen(100L, 1.0));
        assertEquals(10000.5, subject.toScreen(1000L, 1.0));
        assertEquals(-9.5, subject.toScreen(-1L, 1.0));
    }

    public void testToTimestampIntDouble() {
        SimpleScaler subject = new SimpleScaler(Span.valueOf(0, 100), Span.valueOf(0, 1000));
        assertEquals(0.6, subject.toTimestamp(1, 1.0));
        assertEquals(2.5, subject.toTimestamp(20, 1.0));
        assertEquals(10.5, subject.toTimestamp(100, 1.0));
    }
}

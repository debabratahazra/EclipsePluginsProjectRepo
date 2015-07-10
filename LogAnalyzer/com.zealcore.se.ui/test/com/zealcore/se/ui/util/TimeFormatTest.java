package com.zealcore.se.ui.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeFormatTest {

    @Test
    public void testFormatRelative() {

        final long nsTos = 1000000000;

        // final long start = System.currentTimeMillis();

        assertEquals("000000001 ns", TimeFormat.format(1));
        assertEquals("01 sec, 000000000 ns", TimeFormat.format(nsTos));
        assertEquals("01 min, 00 sec, 000000000 ns", TimeFormat.format(nsTos * 60));
        assertEquals("01 h, 00 min, 00 sec, 000000000 ns", TimeFormat.format(nsTos * 60 * 60));
        assertEquals("1 days, 00 h, 00 min, 00 sec, 000000000 ns", TimeFormat
                .format(nsTos * 60 * 60 * 24));

        assertEquals("000000002 ns", TimeFormat.format(1 + 1));
        assertEquals("01 sec, 000000001 ns", TimeFormat.format(nsTos + 1));
        assertEquals("01 min, 00 sec, 000000001 ns", TimeFormat.format(nsTos * 60 + 1));
        assertEquals("01 h, 00 min, 00 sec, 000000001 ns", TimeFormat
                .format(nsTos * 60 * 60 + 1));
        assertEquals("2 days, 00 h, 00 min, 00 sec, 000000000 ns", TimeFormat.format(nsTos * 60
                * 60 * 24 * 2));

        // final long end = System.currentTimeMillis();
        // System.out.println("took: " + (end - start));
    }
}

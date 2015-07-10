package com.zealcore.se.core.util;

import junit.framework.TestCase;

public class TestTimestampManagerTest extends TestCase {

    public void testFromDateToLong() {
        long fromDateToLong = TimestampManager.fromDateToLong("1970-01-02");
        assertEquals(86400000000000L, fromDateToLong);
    }

    public void testFromDateToLongTrhowException() {
        boolean exceptionCaught = false;
        try {
            TimestampManager.fromDateToLong("1970:01:01");
        } catch (RuntimeException e) {
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

    public void testFromTimeToLong() {
        assertEquals(1000000000L, TimestampManager.fromTimeToLong("00:00:01"));
    }

    public void testFromTimeToLongThrowsException() {
        boolean exceptionCaught = false;
        try {
            TimestampManager.fromTimeToLong("00-00-01");
        } catch (RuntimeException e) {
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

    public void testVerifyAllIsParsed() {
        assertEquals(true, TimestampManager.verifyAllIsParsed(
                "1970-01-01 00:00:00:000000", TimestampManager
                        .getRegexpForMicroSec()));
    }

    public void testVerifyAllIsParsedTrowsException() {
        boolean exceptionCaught = false;
        try {
            assertEquals(true, TimestampManager.verifyAllIsParsed(
                    "1970-01-01 00:00:00.000000", TimestampManager
                            .getRegexpForMicroSec()));
        } catch (Exception e) {
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

}

package com.zealcore.se.core.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimestampUtilTest {

    private static final long NANOS = 1000000000L;

    @Test
    public void testGetTs() {
        long expected;
        TimestampUtil subject = new TimestampUtil();
        subject.setNs("1");
        expected = 1;
        assertEquals(expected, subject.getTs());

        subject.setSecond("02");
        expected += 2*NANOS;
        assertEquals(2000000001L, subject.getTs());
        
        subject.setMinute("3");        
        expected += 3*60*NANOS;        
        assertEquals(expected, subject.getTs());

        subject.setHour("4");        
        expected += 4*3600*NANOS;        
        assertEquals(expected, subject.getTs());
        
        subject.setDay("6");        
        expected += 5*24*3600*NANOS;
        assertEquals(expected, subject.getTs());
        
        subject.setMonth("2");        
        expected += 1*31*24*3600*NANOS;        
        assertEquals(expected, subject.getTs());
        
        subject.setYear("1971");        
        expected += 365*24*3600*NANOS;        
        assertEquals(expected, subject.getTs());
    }
    

    @Test
    public void testTsToDate() {
        assertEquals("1971-02-06 04:03:02:000000001", TimestampUtil.tsToDate(34660982000000001L));
    }

}

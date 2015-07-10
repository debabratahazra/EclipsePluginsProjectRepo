package com.zealcore.se.iw.types.internal;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampUtil;

public class TestInternalTypes {

    private static final String SIXTY = "60";

    private static final String FIFTYNINE = "59";

    private static final String DOUBLEZERO = "00";

    private static final String ONE = "1";

    private static final String ZERO = "0";

    private static final String MINUS1 = "-1";

    @Test
    public void testParseDateAndTime() /* throws ImportException */{
        final GenericLogEvent event = new GenericLogEvent();
        final TimestampUtil timestamp = new TimestampUtil();

        final TimestampTypeYear tsYear = new TimestampTypeYear();
        testThatValueFails(tsYear, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsYear, event, timestamp, "year");
        testThatValueFails(tsYear, event, timestamp, "10000");
        testThatValuePasses(tsYear, event, timestamp, TestInternalTypes.ZERO);
        testThatValuePasses(tsYear, event, timestamp, TestInternalTypes.ONE);
        testThatValuePasses(tsYear, event, timestamp, "9999");

        final TimestampTypeMonth tsMonth = new TimestampTypeMonth();
        testThatValueFails(tsMonth, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsMonth, event, timestamp, "month");
        testThatValueFails(tsMonth, event, timestamp, TestInternalTypes.ZERO);
        testThatValueFails(tsMonth, event, timestamp, "13");
        testThatValuePasses(tsMonth, event, timestamp, TestInternalTypes.ONE);
        testThatValuePasses(tsMonth, event, timestamp, "12");

        final TimestampTypeDay tsDay = new TimestampTypeDay();
        testThatValueFails(tsDay, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsDay, event, timestamp, "day");
        testThatValueFails(tsDay, event, timestamp, TestInternalTypes.ZERO);
        testThatValueFails(tsDay, event, timestamp, "32");
        testThatValueFails(tsDay, event, timestamp, "12321");
        testThatValuePasses(tsDay, event, timestamp, TestInternalTypes.ONE);
        testThatValuePasses(tsDay, event, timestamp, "31");

        final TimestampTypeHour tsHour = new TimestampTypeHour();
        testThatValueFails(tsHour, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsHour, event, timestamp, "hour");
        testThatValueFails(tsHour, event, timestamp, "24");
        testThatValueFails(tsHour, event, timestamp, "123");
        testThatValuePasses(tsHour, event, timestamp, TestInternalTypes.ZERO);
        testThatValuePasses(tsHour, event, timestamp,
                TestInternalTypes.DOUBLEZERO);
        testThatValuePasses(tsHour, event, timestamp, "23");

        final TimestampTypeMinute tsMinute = new TimestampTypeMinute();
        testThatValueFails(tsMinute, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsMinute, event, timestamp, TestInternalTypes.SIXTY);
        testThatValuePasses(tsMinute, event, timestamp, TestInternalTypes.ZERO);
        testThatValuePasses(tsMinute, event, timestamp,
                TestInternalTypes.DOUBLEZERO);
        testThatValuePasses(tsMinute, event, timestamp,
                TestInternalTypes.FIFTYNINE);

        final TimestampTypeSecond tsSecond = new TimestampTypeSecond();
        testThatValueFails(tsSecond, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsSecond, event, timestamp, TestInternalTypes.SIXTY);
        testThatValuePasses(tsSecond, event, timestamp, TestInternalTypes.ZERO);
        testThatValuePasses(tsSecond, event, timestamp,
                TestInternalTypes.DOUBLEZERO);
        testThatValuePasses(tsSecond, event, timestamp,
                TestInternalTypes.FIFTYNINE);

        final TimestampTypeMillisecond tsms = new TimestampTypeMillisecond();
        testThatValueFails(tsms, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsms, event, timestamp, "1000");
        testThatValuePasses(tsms, event, timestamp, TestInternalTypes.ZERO);
        testThatValuePasses(tsms, event, timestamp, TestInternalTypes.ONE);
        testThatValuePasses(tsms, event, timestamp, "999");

        final TimestampTypeMicrosecond tsus = new TimestampTypeMicrosecond();
        testThatValueFails(tsus, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsus, event, timestamp, "1000000");
        testThatValuePasses(tsus, event, timestamp, TestInternalTypes.ZERO);
        testThatValuePasses(tsus, event, timestamp, TestInternalTypes.ONE);
        testThatValuePasses(tsus, event, timestamp, "999999");

        final TimestampTypeNanosecond tsns = new TimestampTypeNanosecond();
        testThatValueFails(tsns, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsns, event, timestamp, "1000000000");
        testThatValuePasses(tsns, event, timestamp, TestInternalTypes.ZERO);
        testThatValuePasses(tsns, event, timestamp, TestInternalTypes.ONE);
        testThatValuePasses(tsns, event, timestamp, "999999999");

        final TimestampTypeYMD tsYMD = new TimestampTypeYMD();
        testThatValueFails(tsYMD, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsYMD, event, timestamp, "01+01-01");
        testThatValueFails(tsYMD, event, timestamp, "1-01-01");
        testThatValueFails(tsYMD, event, timestamp, "01-1-01");
        testThatValueFails(tsYMD, event, timestamp, "01-01-1");
        testThatValueFails(tsYMD, event, timestamp, "12001-01-01");
        testThatValueFails(tsYMD, event, timestamp, "01-01-a01");
        testThatValueFails(tsYMD, event, timestamp, "01-01");
        testThatValuePasses(tsYMD, event, timestamp, "01-01-01");
        testThatValuePasses(tsYMD, event, timestamp, "2001-01-01");

        final TimestampTypeTimeMicrosec tsTimeUS = new TimestampTypeTimeMicrosec();
        testThatValueFails(tsTimeUS, event, timestamp, TestInternalTypes.MINUS1);
        testThatValueFails(tsTimeUS, event, timestamp, "01:01:01:1234567");
        testThatValueFails(tsTimeUS, event, timestamp, "01-01:01:123456");
        testThatValueFails(tsTimeUS, event, timestamp, "01:01:01-123456");
        testThatValueFails(tsTimeUS, event, timestamp, "01:01:01:");
        testThatValueFails(tsTimeUS, event, timestamp, "1:01:01:123456");
        testThatValueFails(tsTimeUS, event, timestamp, "01:1:01:123456");
        testThatValueFails(tsTimeUS, event, timestamp, "01:01:1:123456");
        testThatValueFails(tsTimeUS, event, timestamp, "01:01:01:123asdf456");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:1");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:12");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:123");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:1234");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:12345");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:123456");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:000006");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:000056");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:000456");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:003456");
        testThatValuePasses(tsTimeUS, event, timestamp, "01:01:01:023456");
    }

    private void testThatValueFails(final Object tsDay,
            final GenericLogEvent event, final TimestampUtil timestamp,
            final String day) {
        boolean exeptionCatched = false;
        try {
            ((ITimestampType) tsDay).modify(event, timestamp, day);
        } catch (final Exception e) {
            exeptionCatched = true;
        }
        Assert.assertTrue(exeptionCatched);
    }

    private void testThatValuePasses(final Object tsDay,
            final GenericLogEvent event, final TimestampUtil timestamp,
            final String day) {
        boolean exeptionCatched = false;
        try {
            ((ITimestampType) tsDay).modify(event, timestamp, day);
        } catch (final Exception e) {
            exeptionCatched = true;
        }
        Assert.assertFalse(exeptionCatched);
    }
}

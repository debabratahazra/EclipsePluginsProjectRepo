package com.zealcore.se.iw.types.internal;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class FieldTypeFactoryTest {

    private static final String LINE = "-";

    private static final String YEAR1970 = "1970";

    private static final String YEAR2004 = "2004";

    private static final String MONTH01 = "01";

    private static final String MONTH12 = "12";

    private static final String DAY01 = FieldTypeFactoryTest.MONTH01;

    private static final String DAY31 = "31";

    private static final String DATE_2005_01_02 = "2005-01-02";

    private static final String DATE_05_01_02 = "05-01-02";

    private static final String DATE_1970_01_02 = "1970-01-02";

    private static final String DATE_70_01_02 = "70-01-02";

    private static final String ZERO_TIME = " 00:00:00:000000000";

    private static final String ZERO_ONE = "0" + "1";

    private static final String DUMMY = "Dummy";

    private static final String A_CONSTANT_STRING = "ABC123";

    private static final long ONE_HOUR_MINUTE_SECOND_MICROSEC = 3661000001000L;

    private static final long NANOS_PER_DAY = 86400000000000L;

    private static final int HUNDRED_INT = 100;

    private static final long HUNDRED_LONG = 100L;

    private static final float HUNDRED_FLOAT = 100.0f;

    private static final double HUNDRED_DOUBLE = 100;

    private static final String HUNDRED = "100";

    @Test
    public void testTypes() {
        for (final IFieldType type : FieldTypeFactory.getInstance()
                .getRegisteredFieldTypes()) {
            if (!type.getClass().equals(TimestampTypeYMD.class)
                    && !type.getClass().equals(TimestampTypeTimeMicrosec.class)
                    && !type.getClass().equals(
                            TimestampTypeSecondMicrosecond.class)) {
                if (!type.getClass().equals(IgnoreType.class)) {
                    final Object obj = type
                            .valueOf(FieldTypeFactoryTest.HUNDRED);
                    obj.getClass();
                }
            }
        }
        final IgnoreType it = new IgnoreType();
        Assert.assertEquals(null, it.valueOf(FieldTypeFactoryTest.HUNDRED));
        Assert.assertEquals("Ignore field", it.getLabel());
        NumberType p = new NumberType(Integer.class);
        Assert.assertEquals(FieldTypeFactoryTest.HUNDRED_INT, p
                .valueOf(FieldTypeFactoryTest.HUNDRED));
        p = new NumberType(Long.class);
        Assert.assertEquals(FieldTypeFactoryTest.HUNDRED_LONG, p
                .valueOf(FieldTypeFactoryTest.HUNDRED));
        p = new NumberType(Float.class);
        Assert.assertEquals(FieldTypeFactoryTest.HUNDRED_FLOAT, p
                .valueOf(FieldTypeFactoryTest.HUNDRED));
        p = new NumberType(Double.class);
        Assert.assertEquals(FieldTypeFactoryTest.HUNDRED_DOUBLE, p
                .valueOf(FieldTypeFactoryTest.HUNDRED));
        final StringType s = new StringType();
        Assert.assertEquals(FieldTypeFactoryTest.A_CONSTANT_STRING, s
                .valueOf(FieldTypeFactoryTest.A_CONSTANT_STRING));
        final GenericLogEvent event = new GenericLogEvent();
        final TimestampUtil timestamp = new TimestampUtil();
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR1970
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH01
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY01), timestamp.getTs());
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR1970
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH01
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY01), event.getTs());
        final TimestampTypeYMD t = new TimestampTypeYMD();
        Assert.assertEquals(FieldTypeFactoryTest.NANOS_PER_DAY, (long) t
                .valueOf(FieldTypeFactoryTest.DATE_1970_01_02));
        t.modify(event, timestamp, FieldTypeFactoryTest.DATE_1970_01_02);
        Assert.assertEquals(FieldTypeFactoryTest.NANOS_PER_DAY, event.getTs());
        timestamp.setDay(FieldTypeFactoryTest.DAY01);
        t.modify(event, timestamp, FieldTypeFactoryTest.DATE_70_01_02);
        Assert.assertEquals(FieldTypeFactoryTest.DATE_1970_01_02
                + FieldTypeFactoryTest.ZERO_TIME, event.getDate());
        Assert.assertEquals(FieldTypeFactoryTest.DATE_1970_01_02
                + FieldTypeFactoryTest.ZERO_TIME, event.getDate());
        t.modify(event, timestamp, FieldTypeFactoryTest.DATE_2005_01_02);
        Assert.assertEquals(FieldTypeFactoryTest.DATE_2005_01_02
                + FieldTypeFactoryTest.ZERO_TIME, event.getDate());
        t.modify(event, timestamp, FieldTypeFactoryTest.DATE_05_01_02);
        Assert.assertEquals(FieldTypeFactoryTest.DATE_2005_01_02
                + FieldTypeFactoryTest.ZERO_TIME, event.getDate());
        timestamp.setYear(FieldTypeFactoryTest.YEAR1970);
        timestamp.setDay(FieldTypeFactoryTest.DAY01);
        final TimestampTypeTimeMicrosec tm = new TimestampTypeTimeMicrosec();
        Assert.assertEquals(
                FieldTypeFactoryTest.ONE_HOUR_MINUTE_SECOND_MICROSEC, (long) tm
                        .valueOf("01:01:01:000001"));
        final TimestampTypeYear year = new TimestampTypeYear();
        year.modify(event, timestamp, FieldTypeFactoryTest.YEAR2004);
        Assert.assertEquals(FieldTypeFactoryTest.DUMMY, year
                .valueOf(FieldTypeFactoryTest.DUMMY));
        Assert.assertEquals("Timestamp (Y - Year)", year.getLabel());
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR2004
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH01
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY01), timestamp.getTs());
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR2004
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH01
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY01), event.getTs());
        final TimestampTypeMonth month = new TimestampTypeMonth();
        month.modify(event, timestamp, FieldTypeFactoryTest.MONTH12);
        Assert.assertEquals(FieldTypeFactoryTest.DUMMY, month
                .valueOf(FieldTypeFactoryTest.DUMMY));
        Assert.assertEquals("Timestamp (M - Month)", month.getLabel());
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR2004
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH12
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY01), timestamp.getTs());
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR2004
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH12
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY01), event.getTs());
        final TimestampTypeDay day = new TimestampTypeDay();
        day.modify(event, timestamp, FieldTypeFactoryTest.DAY31);
        Assert.assertEquals(FieldTypeFactoryTest.DUMMY, month
                .valueOf(FieldTypeFactoryTest.DUMMY));
        Assert.assertEquals("Timestamp (D - Day)", day.getLabel());
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR2004
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH12
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY31), timestamp.getTs());
        Assert.assertEquals(TimestampManager
                .fromDateToLong(FieldTypeFactoryTest.YEAR2004
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.MONTH12
                        + FieldTypeFactoryTest.LINE
                        + FieldTypeFactoryTest.DAY31), event.getTs());
        final TimestampTypeHour h = new TimestampTypeHour();
        Assert.assertEquals(TimestampManager.NANOS_PER_HOUR, (long) h
                .valueOf(FieldTypeFactoryTest.ZERO_ONE));
        Assert.assertEquals("Timestamp (h - Hour)", h.getLabel());
        final TimestampTypeMinute m = new TimestampTypeMinute();
        Assert.assertEquals(TimestampManager.NANOS_PER_MINUTE, (long) m
                .valueOf(FieldTypeFactoryTest.ZERO_ONE));
        Assert.assertEquals("Timestamp (m - Minute)", m.getLabel());
        final TimestampTypeSecond sec = new TimestampTypeSecond();
        Assert.assertEquals(TimestampManager.NANOS_PER_SECOND, (long) sec
                .valueOf(FieldTypeFactoryTest.ZERO_ONE));
        Assert.assertEquals("Timestamp (s - Second)", sec.getLabel());
        final TimestampTypeMillisecond ms = new TimestampTypeMillisecond();
        Assert.assertEquals(TimestampManager.NANOS_PER_MILLI, (long) ms
                .valueOf("001"));
        Assert.assertEquals("Timestamp (ms - Millisecond)", ms.getLabel());
        final TimestampTypeMicrosecond us = new TimestampTypeMicrosecond();
        Assert.assertEquals(TimestampManager.NANOS_PER_MICRO, (long) us
                .valueOf("000001"));
        Assert.assertEquals("Timestamp (us - Microsecond)", us.getLabel());
        final TimestampTypeNanosecond ns = new TimestampTypeNanosecond();
        Assert.assertEquals(TimestampManager.NANOS_PER_NANO, (long) ns
                .valueOf("000000001"));
        Assert.assertEquals("Timestamp (ns - Nanosecond)", ns.getLabel());
        final NameType nt = new NameType();
        Assert.assertEquals(FieldTypeFactoryTest.DUMMY, nt
                .valueOf(FieldTypeFactoryTest.DUMMY));
        nt.modify(event, FieldTypeFactoryTest.DUMMY);
        Assert.assertEquals(FieldTypeFactoryTest.DUMMY, event.getTypeName());
    }
}

package com.zealcore.se.core.util;

import org.junit.Assert;
import org.junit.Test;

import com.zealcore.se.core.util.TimeUtil.BASE;

public class TimeUtilTest {

    private static final String S = "s";

    private static final long TEST_1234 = 1234;

    private static final long TEN_BASE = 10;

    private static final long HUNDRED = 100L;

    private static final long THOUSAND = 1000;

    private static final long TEN_THOUSAND = 10000;

    private static final long HUNDRED_THOUSAND = 100000;

    private static final long MILLION = 1000000;

    @Test
    public void testLowestBase() {

        long subject = 1;
        Assert.assertEquals(BASE.NANO, TimeUtil.lowestBase(subject));

        subject = TimeUtilTest.TEN_BASE;
        Assert.assertEquals(BASE.NANO, TimeUtil.lowestBase(subject));

        subject = TimeUtilTest.HUNDRED;
        Assert.assertEquals(BASE.NANO, TimeUtil.lowestBase(subject));

        // ---------------------------------------------------
        subject = TimeUtilTest.THOUSAND;
        Assert.assertEquals(BASE.MICRO, TimeUtil.lowestBase(subject));

        subject = TimeUtilTest.TEN_THOUSAND;
        Assert.assertEquals(BASE.MICRO, TimeUtil.lowestBase(subject));

        subject = TimeUtilTest.HUNDRED_THOUSAND;
        Assert.assertEquals(BASE.MICRO, TimeUtil.lowestBase(subject));

        // ---------------------------------------------------
        subject = TimeUtilTest.MILLION;
        Assert.assertEquals(BASE.MILLI, TimeUtil.lowestBase(subject));

    }

    @Test
    public void testConvertTo() {
        long subject = 1;
        Assert.assertEquals(1L, TimeUtil.convertTo(subject, BASE.NANO));

        subject = TimeUtilTest.TEN_BASE;
        Assert.assertEquals(TimeUtilTest.TEN_BASE, TimeUtil.convertTo(subject,
                BASE.NANO));

        subject = TimeUtilTest.HUNDRED;
        Assert.assertEquals(TimeUtilTest.HUNDRED, TimeUtil.convertTo(subject,
                BASE.NANO));

        // ---------------------------------------------------
        subject = TimeUtilTest.THOUSAND;
        Assert.assertEquals(1L, TimeUtil.convertTo(subject, BASE.MICRO));

        subject = TimeUtilTest.TEN_THOUSAND;
        Assert.assertEquals(TimeUtilTest.TEN_BASE, TimeUtil.convertTo(subject,
                BASE.MICRO));

        subject = TimeUtilTest.HUNDRED_THOUSAND;
        Assert.assertEquals(TimeUtilTest.HUNDRED, TimeUtil.convertTo(subject,
                BASE.MICRO));

        subject = TimeUtilTest.MILLION;
        Assert.assertEquals(TimeUtilTest.THOUSAND, TimeUtil.convertTo(subject,
                BASE.MICRO));

        subject = TimeUtilTest.MILLION;
        Assert.assertEquals(1L, TimeUtil.convertTo(subject, BASE.MILLI));

    }

    @Test
    public void testLowestBaseFormat() {
        check(1, "1 ns");
        check(10, "10 ns");
        check(100, "100 ns");
        check(1000, "1 " + BASE.MICRO.toString() + S);
        check(10000, "10 " + BASE.MICRO.toString() + S);
        check(100000, "100 " + BASE.MICRO.toString() + S);
        check(1000000, "1 ms");
        check(10000000, "10 ms");
        check(100000000, "100 ms");
    }

    private void check(final long value, final String format) {
        final BASE base = TimeUtil.lowestBase(value);
        Assert.assertEquals(format, TimeUtil.convertTo(value, base) + " "
                + base + S);
    }

    @Test
    public void testGranularity() {
        Assert.assertEquals(Long.parseLong("0000"), TimeUtil.granularity(
                TimeUtilTest.TEST_1234, 0));
        Assert.assertEquals(Long.parseLong("1000"), TimeUtil.granularity(
                TimeUtilTest.TEST_1234, 1));
        Assert.assertEquals(Long.parseLong("1200"), TimeUtil.granularity(
                TimeUtilTest.TEST_1234, 1 + 1));
        Assert.assertEquals(Long.parseLong("1230"), TimeUtil.granularity(
                TimeUtilTest.TEST_1234, 1 + 1 + 1));

        Assert.assertEquals(TimeUtilTest.TEST_1234, TimeUtil.granularity(
                TimeUtilTest.TEST_1234, 1 + 1 + 1 + 1));
        Assert.assertEquals(TimeUtilTest.TEST_1234, TimeUtil.granularity(
                TimeUtilTest.TEST_1234, 1 + 1 + 1 + 1 + 1));

    }

}

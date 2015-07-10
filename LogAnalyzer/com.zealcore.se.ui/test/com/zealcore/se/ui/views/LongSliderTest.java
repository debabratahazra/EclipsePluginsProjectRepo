package com.zealcore.se.ui.views;

import org.junit.After;
import org.junit.Before;

public class LongSliderTest {

    private static final int TEST_MIN = 10000;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    public static void main(final String[] args) {

        final long subject = Long.MAX_VALUE / (1 + 1);
        System.out.println(subject);

        final int fx = LongSliderTest.f(subject, LongSliderTest.TEST_MIN,
                Long.MAX_VALUE);
        System.out.println("F(x) = " + fx);

        final long gy = LongSliderTest.g(fx, LongSliderTest.TEST_MIN,
                Long.MAX_VALUE);
        System.out.println("G(f(x)) = " + gy);

        System.out.println("Fault: " + Math.abs(subject - gy));

        if (Long.MAX_VALUE != LongSliderTest.g(Integer.MAX_VALUE,
                LongSliderTest.TEST_MIN, Long.MAX_VALUE)) {
            System.out.println("fail 1");
        }

        final int fmin = LongSliderTest.f(LongSliderTest.TEST_MIN,
                LongSliderTest.TEST_MIN, Long.MAX_VALUE);
        if (0 != fmin) {
            System.out.println("fail 2: expected " + LongSliderTest.TEST_MIN
                    + " was " + fmin);
        }

        final long gfmin = LongSliderTest.g(fmin, LongSliderTest.TEST_MIN,
                Long.MAX_VALUE);

        if (LongSliderTest.TEST_MIN != gfmin) {
            System.out.println("Fai3 expected TEST_MIN == g(f(min)) but was "
                    + LongSliderTest.TEST_MIN);
        }
    }

    public static int f(final long x, final long min, final long max) {

        final double r = (double) (x - min) / (double) (max - min);

        return (int) (r * Integer.MAX_VALUE);
    }

    public static long g(final int x, final long min, final long max) {
        final double r = (double) x / (double) Integer.MAX_VALUE;

        final long y = (long) (min + r * (max - min));
        return y;
    }

}

package util;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.ui.util.TimeFormat;

public final class TimeStampTest {

    private static final String FULL_NS = "Y-M-D h:m:s ns";

    private static final String FULL_US = "Y-M-D h:m:s us";

    private static final String FULL_MS = "Y-M-D h:m:s ms";

    @Test
    public void testFormater() {

        String str = TimeFormat
                .format(TimeStampTest.FULL_MS, Integer.MAX_VALUE);

        Assert.assertEquals("1970-01-01 00:00:02 147", str);
        str = TimeFormat.format(TimeStampTest.FULL_US, Integer.MAX_VALUE);
        Assert.assertEquals("1970-01-01 00:00:02 147483", str);
        str = TimeFormat.format(TimeStampTest.FULL_NS, Integer.MAX_VALUE);
        Assert.assertEquals("1970-01-01 00:00:02 147483647", str);

        str = TimeFormat.format(TimeStampTest.FULL_MS, Long.MAX_VALUE);
        Assert.assertEquals("2262-04-11 23:47:16 854", str);
        str = TimeFormat.format(TimeStampTest.FULL_US, Long.MAX_VALUE);
        Assert.assertEquals("2262-04-11 23:47:16 854775", str);
        str = TimeFormat.format(TimeStampTest.FULL_NS, Long.MAX_VALUE);
        Assert.assertEquals("2262-04-11 23:47:16 854775807", str);

    }

    public static void main(final String[] args) {

        System.out.println(TimeFormat.format("ms", Long.MAX_VALUE));
        System.out.println(TimeFormat.format("us", Long.MAX_VALUE));
        System.out.println(TimeFormat.format("ns", Long.MAX_VALUE));

        System.out.println(TimeFormat.format(TimeStampTest.FULL_MS,
                Long.MAX_VALUE));
        System.out.println(TimeFormat.format(TimeStampTest.FULL_US,
                Long.MAX_VALUE));
        System.out.println(TimeFormat.format(TimeStampTest.FULL_NS,
                Long.MAX_VALUE));

        System.out.println(TimeFormat.format("h:m:s ms", Long.MAX_VALUE));
        System.out.println(TimeFormat.format("h:m:s us", Long.MAX_VALUE));
        System.out.println(TimeFormat.format("h:m:s ns", Long.MAX_VALUE));

        System.out.println(TimeFormat.format("m:s ms", Long.MAX_VALUE));
        System.out.println(TimeFormat.format("s us", Long.MAX_VALUE));
        System.out.println(TimeFormat.format(" us", Long.MAX_VALUE));

    }
}

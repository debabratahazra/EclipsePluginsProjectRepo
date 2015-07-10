package com.zealcore.sd.ose.importers;

import static org.junit.Assert.assertEquals;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.ImportException;

public class EventBBBinWriterTest {

    private static final String GET_TS_FROM_PARAMS = "getTsFromParams didn't throw ImportException when expected";
    private EventBBBinWriter eventBBBinWriter;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testGetTsFromParams() throws IOException {
        DataOutputStream dos = new DataOutputStream(System.out);
        eventBBBinWriter = new EventBBBinWriter(dos, true);
        testGetTsFromParamsTickLength();
        eventBBBinWriter.close();
        eventBBBinWriter.setOutputStream(dos);
        testGetTsFromParamsTicknTick();
        eventBBBinWriter.close();
        eventBBBinWriter.setOutputStream(dos);
        testGetTsFromParamsSeconds();
    }

    private void testGetTsFromParamsSeconds() {
        long microTickFrequency = 1000000;
        long tickLength = 1000;
        long reference = 0;
        boolean isLossEvent = false;
        long tick = 2;
        long nTick = 1;
        long seconds;
        long secondsTick = 0;

        GregorianCalendar cal = new GregorianCalendar(1970,
                GregorianCalendar.JANUARY, 1, 1, 0, 0);
        cal.setTimeZone(TimeZone.getTimeZone("GMT+1"));		// Sweden Standard Time Zone
        long expected = cal.getTimeInMillis();
        seconds = expected / 1000;

        long calculatedTs = eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference);
        assertEquals(2001000L, calculatedTs);
        
        cal = new GregorianCalendar(2000,
                GregorianCalendar.JANUARY, 1, 1, 0, 0);
        cal.setTimeZone(TimeZone.getTimeZone("GMT+1"));		// Sweden Standard Time Zone
        expected = cal.getTimeInMillis();
        seconds = expected / 1000;

        assertEquals(946684800002001000L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));
    }

    private void testGetTsFromParamsTicknTick() {
        long microTickFrequency = 0;
        long tickLength = 1;
        long reference = 0;
        boolean isLossEvent = false;
        long tick = 0;
        long nTick = -1;
        long seconds = 0;
        long secondsTick = 0;

        /*
         * tick = 0 and nTick = 0 is not a timestamp
         */
        try {
            eventBBBinWriter.getTsFromParams(microTickFrequency, tickLength,
                    tick, nTick, seconds, secondsTick, isLossEvent, reference);
            Assert
                    .fail(GET_TS_FROM_PARAMS);
        } catch (ImportException expectedException) {}

        /*
         * tick = -1 and nTick = 0 is not a timestamp
         */
        tick = -1;
        try {
            eventBBBinWriter.getTsFromParams(microTickFrequency, tickLength,
                    tick, nTick, seconds, secondsTick, isLossEvent, reference);
            Assert
                    .fail(GET_TS_FROM_PARAMS);
        } catch (ImportException expectedException) {}

        /*
         * tick = -1 and nTick = -1 is not a timestamp
         */
        nTick = -1;
        try {
            eventBBBinWriter.getTsFromParams(microTickFrequency, tickLength,
                    tick, nTick, seconds, secondsTick, isLossEvent, reference);
            Assert
                    .fail(GET_TS_FROM_PARAMS);
        } catch (ImportException expectedException) {}

        /*
         * tick = 0 and nTick = -1 is not a timestamp
         */
        tick = 0;
        try {
            eventBBBinWriter.getTsFromParams(microTickFrequency, tickLength,
                    tick, nTick, seconds, secondsTick, isLossEvent, reference);
            Assert
                    .fail(GET_TS_FROM_PARAMS);
        } catch (ImportException expectedException) {}

        /*
         * tick = 100 and nTick = -1 is not a timestamp
         */
        tick = 100;
        try {
            eventBBBinWriter.getTsFromParams(microTickFrequency, tickLength,
                    tick, nTick, seconds, secondsTick, isLossEvent, reference);
            Assert
                    .fail(GET_TS_FROM_PARAMS);
        } catch (ImportException expectedException) {}

        /*
         * nTick is ignored because microTickFrequency = 0
         */
        tick = 1;
        nTick = 1;
        assertEquals(1000L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));

        /*
         * nTick is ignored because microTickFrequency = 0
         */
        tick = 1000000;
        nTick = 1;
        assertEquals(1000000000L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));

        tick = 1000000;
        nTick = 1;
        microTickFrequency = 1000000000;
        assertEquals(1000000001L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));

        /*
         * isLossEvent == true, use previous timestamp
         */
        isLossEvent = false;
        assertEquals(1000000001L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));

        tick = 4294967294L;
        nTick = 0;
        assertEquals(4294967294000L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));
        
        /*
         * tick overflow, assumed as bug in RMM => use previous ts
         */
        tick = 500000L;
        nTick = 0;
        assertEquals(4294967294000L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));
        
        
        /*
         * A correct tick overflow
         */
        tick = 1L;
        assertEquals(4294967296000L, eventBBBinWriter.getTsFromParams(
                microTickFrequency, tickLength, tick, nTick, seconds,
                secondsTick, isLossEvent, reference));
    }

    /*
     * Test with tickLengt == 0 -> should always use the internal counter and
     * increase by 1 for each call to getTsFromParams
     */
    private void testGetTsFromParamsTickLength() {
        long microTickFrequency = 0;
        long tickLength = 0;
        long reference = 0;
        boolean isLossEvent = false;
        long tick = 0;
        long nTick = 0;
        long seconds = 0;
        long secondsTick = 0;

        assertEquals(0, eventBBBinWriter.getTsFromParams(microTickFrequency,
                tickLength, tick, nTick, seconds, secondsTick, isLossEvent,
                reference));
        tick = 10;
        nTick = 1000;
        assertEquals(1, eventBBBinWriter.getTsFromParams(microTickFrequency,
                tickLength, tick, nTick, seconds, secondsTick, isLossEvent,
                reference));
        isLossEvent = true;
        assertEquals(2, eventBBBinWriter.getTsFromParams(microTickFrequency,
                tickLength, tick, nTick, seconds, secondsTick, isLossEvent,
                reference));
        microTickFrequency = 1000000000;
        assertEquals(3, eventBBBinWriter.getTsFromParams(microTickFrequency,
                tickLength, tick, nTick, seconds, secondsTick, isLossEvent,
                reference));

        /*
         * test with negative tickLength
         */
        tickLength = -1;
        isLossEvent = false;
        assertEquals(4, eventBBBinWriter.getTsFromParams(microTickFrequency,
                tickLength, tick, nTick, seconds, secondsTick, isLossEvent,
                reference));
    }
}

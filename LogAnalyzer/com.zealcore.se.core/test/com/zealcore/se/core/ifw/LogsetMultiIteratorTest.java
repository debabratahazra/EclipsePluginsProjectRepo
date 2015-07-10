package com.zealcore.se.core.ifw;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.zealcore.se.core.ifw.Logset.MultiIterator;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.ILogEvent;

public class LogsetMultiIteratorTest {

    @Test()
    public void testJoinIterator() throws Exception {

        final Set<ILogAdapter> data = new LinkedHashSet<ILogAdapter>();
        data.add(create(2, 4, 6));
        data.add(create(1, 3, 5));
        data.add(create(7, 8, 9, 10, 11));

        final MultiIterator subject = new Logset.MultiIterator(data);
        for (int i = 1; i < 12; i++) {
            assertTrue(subject.hasNext());
            final ILogEvent item = subject.next();
            assertNotNull(item);
            assertEquals(i, item.getTs());
        }
        assertFalse(subject.hasNext());
    }

    @Test
    public void testJoinIteratorMultiSameElements() throws Exception {

        final Set<ILogAdapter> data = new LinkedHashSet<ILogAdapter>();
        data.add(create(2, 4, 6));
        data.add(create(1, 3, 5));
        data.add(create(7, 8, 9, 10, 11));
        data.add(create(2, 4, 6));
        data.add(create(1, 3, 5));
        data.add(create(7, 8, 9, 10, 11));

        final MultiIterator subject = new Logset.MultiIterator(data);
        for (int i = 1; i < 12; i++) {
            assertTrue(subject.hasNext());
            ILogEvent item = subject.next();
            assertNotNull(item);
            assertEquals(i, item.getTs());

            assertTrue(subject.hasNext());
            item = subject.next();
            assertNotNull(item);
            assertEquals(i, item.getTs());

        }
        assertFalse(subject.hasNext());

        final Set<ILogAdapter> data2 = new LinkedHashSet<ILogAdapter>();
        data2.add(create(0, 1, 2, 3, 4));
        data2.add(create(5, 6, 7, 8, 9));

        final MultiIterator subject2 = new Logset.MultiIterator(data2);
        for (int i = 0; i < 10; i++) {
            assertTrue(subject2.hasNext());
            ILogEvent item = subject2.next();
            assertNotNull(item);
            assertEquals(i, item.getTs());
        }
        assertFalse(subject.hasNext());
    }

    @Test
    public void testPerformance() throws Exception {
        final int numEvents = 50000;
        final int numAdapters = 10;

        final Set<ILogAdapter> data = new LinkedHashSet<ILogAdapter>();

        for (int i = 0; i < numAdapters; i++) {
            long ts = (long) (Math.random() * numAdapters);

            final List<ILogEvent> events = new ArrayList<ILogEvent>();
            for (int e = 0; e < numEvents; e++) {
                final AbstractLogEvent event = new AbstractLogEvent() {};
                event.setTs(ts);
                events.add(event);
                ts = (long) (ts + Math.random() * numAdapters + 2);
            }
            data.add(new TestAdapter(events));

        }

        final long start = System.currentTimeMillis();
        final MultiIterator subject = new Logset.MultiIterator(data);
        int count = 0;
        while (subject.hasNext()) {
            assertTrue(subject.hasNext());
            subject.next();
            count++;
        }

        final long end = System.currentTimeMillis();

        System.out.println("Took: " + (end - start) + " ms to do " + count
                + " events distributed on " + numAdapters + " adapters");
    }

    TestAdapter create(final int... values) {
        final List<ILogEvent> events = new ArrayList<ILogEvent>();
        for (final int i : values) {
            final AbstractLogEvent event = new AbstractLogEvent() {};
            event.setTs(i);
            events.add(event);
        }
        return new TestAdapter(events);
    }
}

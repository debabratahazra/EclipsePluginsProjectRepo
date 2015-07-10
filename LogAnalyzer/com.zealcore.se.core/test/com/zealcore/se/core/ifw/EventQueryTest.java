package com.zealcore.se.core.ifw;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.ILogEvent;

public class EventQueryTest extends AbstractQueryTest {

    private EventQuery subject;

    private long currentTime = 100;

    @Before
    public void setUp() {
    	Logset  logset = Logset.valueOf(UUID.randomUUID());
        try {
			logset.addLog(new TestAdapter((ILogEvent)null));
		} catch (ImportOperationCancelledException e) {
			e.printStackTrace();
		}		
        setRefreshCalled(false);
        subject = new EventQuery(this);

        subject.setLogset(logset);
        subject.setCacheSize(50);
        subject.initialize(this);
        populate(Reason.FILE_ADDED);
    }

    @Test
    public void testVisit() {

        final List<ILogEvent> result = subject.getEvents(10, 10);
        assertEquals(20, result.size());
        long ts = 90;
        for (final ILogEvent event : result) {
            assertEquals(ts++, event.getTs());
        }
    }

    @Test
    public void testRequestLargerThanCachesize() {
        int big = (subject.getCacheSize() / 2) + 2;
        subject.getEvents(1, big);
        subject.getEvents(big, 1);

    }

    @Test
    public void testRequestEmpty() {
        subject = new EventQuery(this);
        subject.setLogset(Logset.valueOf(UUID.randomUUID()));
        subject.initialize(this);
        subject.visitBegin(Reason.FILE_ADDED);
        subject.visitEnd(true);
        final List<ILogEvent> result = subject.getEvents(10, 10);
        assertEquals(0, result.size());
        assertFalse(getRefreshCalled());
    }

    @Test
    public void testVisitCacheMissBeforeAtBegin() {

        subject.initialize(this);
        currentTime = 2;
        final List<ILogEvent> result = subject.getEvents(10, 10);
        assertEquals(12, result.size());
        long ts = 0;
        for (final ILogEvent event : result) {
            assertEquals(ts++, event.getTs());
        }
        assertTrue(getRefreshCalled());
    }

    @Test
    public void testVisitCacheMissAfterAtEnd() {

        subject.initialize(this);
        currentTime = 198;
        final List<ILogEvent> result = subject.getEvents(10, 10);
        assertEquals(12, result.size());
        long ts = 188;
        for (final ILogEvent event : result) {
            assertEquals(ts++, event.getTs());
        }
        assertTrue(getRefreshCalled());
    }

    @Test
    public void testVisitCacheMissBeforeMiddle() {

        subject.initialize(this);
        currentTime = 50;
        final List<ILogEvent> result = subject.getEvents(10, 10);
        assertEquals(20, result.size());
        long ts = 40;
        for (final ILogEvent event : result) {
            assertEquals(ts++, event.getTs());
        }
        assertTrue(getRefreshCalled());
    }

    @Test
    public void testVisitCacheMissAfterMiddle() {

        subject.initialize(this);
        currentTime = 150;
        final List<ILogEvent> result = subject.getEvents(10, 10);
        assertEquals(20, result.size());
        long ts = 140;
        for (final ILogEvent event : result) {
            assertEquals(ts++, event.getTs());
        }
        assertTrue(getRefreshCalled());
    }

    @Test
    public void testGetLargerThanCacheSizeNoMiss() {

        subject.initialize(this);

        final List<ILogEvent> result = subject.getEvents(
                subject.getCacheSize(), subject.getCacheSize());
        assertEquals(subject.getCacheSize(), result.size());
        assertFalse(getRefreshCalled());
    }

    @Test
    public void testGetLargerThanCacheSizeWithMiss() {

        subject.initialize(this);
        currentTime = 50;
        final List<ILogEvent> result = subject.getEvents(
                subject.getCacheSize(), subject.getCacheSize());
        assertEquals(subject.getCacheSize(), result.size());
        assertTrue(getRefreshCalled());
    }

    @Test
    public void testRunAllOver() {
        subject.initialize(this);
        for (int i = -100; i < 300; i++) {
            currentTime = i;
            final List<ILogEvent> events = subject.getEvents(10, 10);
            assertTrue(events.size() >= 10);
        }
    }

    public long getCurrentTime() {
        return currentTime;
    }

    @Override
    IQuery getQuery() {
        return subject;
    }

    @Override
    protected void populate(final Reason r) {
        subject.visitBegin(r);
        boolean visitEnd = true;
        for (int i = 0; i < 200; i++) {
            final AbstractLogEvent item = new AbstractLogEvent() {};
            item.setTs(i);
            if (!subject.visit(item)) {
                visitEnd = false;
                break;
            }
        }
        subject.visitEnd(visitEnd);
    }

    @Test()
    public void testNoInitialize() {
        EventQuery query = new EventQuery(this);
        query.setLogset(Logset.valueOf(UUID.randomUUID()));
        /*try {
            query.getEvents(10, 10);
            fail("Expected Illegal State Exception");
        } catch (IllegalStateException ex) {
            query.getEvents(10, 10);
        }*/
        assertEquals(Collections.emptyList(),query.getEvents(10, 10));
    }
}

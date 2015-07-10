package com.zealcore.se.core.ifw;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ITimed;

public class TimeCacheTest implements ITimeProvider {

    @Test
    public void testPutInCache() {
        TimeCache<ITimed> subject = new TimeCache<ITimed>(this, 100);
        subject.clear();
        int where = 0;
        for (int i = 0; i < 300; i++) {
            if (!subject.putInCache(ModelFactory.newEvent(i))) {
                where = i;
                break;
            }
        }
        assertEquals(249, where);
    }

    public long getCurrentTime() {
        return 200;
    }

    @Test
    public void testGetLast() {
        TimeCache<ITimed> subject = new TimeCache<ITimed>(this, 100);
        subject.clear();
        for (int i = 0; i < 300; i++) {
            if (!subject.putInCache(ModelFactory.newEvent(i))) {
                break;
            }
        }

        ILogEvent last = (ILogEvent) subject.getLast();
        assertEquals(249, last.getTs());
    }

}

package com.zealcore.se.ui.editors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.swt.graphics.Rectangle;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.internal.IDistanceTransformer;
import com.zealcore.se.ui.internal.LogaritmicDistanceTransformer;

public class FixedMidPointRulerTest {

    private static final class LogEvent extends AbstractLogEvent {

        public LogEvent(final long l) {
            setTs(l);
        }

    }

    @Before
    public void print() {
    // System.out.println("####### NEW TEST ########");
    }

    @Test
    public void testToScreen() {

        final Rectangle screen = new Rectangle(0, 0, 100, 1);

        final List<Span> spans = new ArrayList<Span>();
        for (int i = 0; i < 10; i++) {
            spans.add(Span.valueOf(i * 10, i * 10 + 10));
        }

        final FixedMidPointRuler subject = new FixedMidPointRuler(screen.x,
                screen.width, new NoTransform(), 50, spans);

        for (int i = 0; i < 100; i++) {
            Assert.assertEquals("1:1 transform", i, subject.toScreen(i));
        }

    }

    @Test
    public void testToScreenOutside() {

        final List<Span> spans = new ArrayList<Span>();
        spans.add(Span.valueOf(0, 100));

        final FixedMidPointRuler subject = new FixedMidPointRuler(0, 100,
                new NoTransform(), 50, spans);
        Assert.assertTrue(subject.toScreen(101) > 100);
        Assert.assertTrue(subject.toScreen(50) == 50);
        Assert.assertTrue(subject.toScreen(0) == 0);
        Assert.assertTrue(subject.toScreen(-1) < 0);

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testToScreenLog() {
        final Rectangle screen = new Rectangle(0, 0, 100, 1);

        final List<ILogEvent> events = new ArrayList<ILogEvent>();

        for (int i = 0; i < 100; i++) {
            final long ts = (long) (i * 10 + Math.random() * 10000);
            // System.out.println("Generated ts " + ts);
            events.add(new LogEvent(ts));

        }
        Collections.sort(events);
        final Collection<Span> spans = Span.valueOf(events);

        final FixedMidPointRuler subject = new FixedMidPointRuler(screen.x,
                screen.width, new LogaritmicDistanceTransformer(2, 1), 500,
                spans);

        assertNotNull(subject);
        // for (int i = 0; i < 1000; i++) {
        // System.out.println(i + " => " + subject.toScreen(i));
        // }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testToScreenLogSameDistanceRightNLeft() {
        final Rectangle screen = new Rectangle(0, 0, 100, 1);

        final List<ILogEvent> events = new ArrayList<ILogEvent>();

        final int step = 10;
        for (int i = 0; i < 1000; i += step) {
            final long ts = (long) (i * step + Math.random());
            events.add(new LogEvent(ts));

        }
        Collections.sort(events);
        final Collection<Span> spans = Span.valueOf(events);

        final FixedMidPointRuler subject = new FixedMidPointRuler(screen.x,
                screen.width, new LogaritmicDistanceTransformer(2, 1), 500,
                spans);

        final int firstScreen = subject.toScreen(step);
        int distance = subject.toScreen(20) - firstScreen;

        assertEquals(0, distance);

        // FIXME Convert to assertions!
        // for (int i = 0; i < 1000; i++) {
        // System.out.println(i + " => " + subject.toScreen(i));
        // }
        //
        // for (int i = step; i < 1000; i += step) {
        // final int ts = subject.toScreen(i);
        // distance = ts - subject.toScreen(i - step);
        // System.out.println(i + " => " + ts);
        // System.out.println("Distance: " + distance);
        // System.out.println();
        //
        // }
    }

    private static class NoTransform implements IDistanceTransformer {

        public double transform(final double delta) {
            return delta;
        }
    }

}

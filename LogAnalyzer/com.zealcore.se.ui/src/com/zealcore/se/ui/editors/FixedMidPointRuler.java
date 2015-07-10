package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;

import com.zealcore.se.core.util.Span;
import com.zealcore.se.core.util.TimeUtil;
import com.zealcore.se.ui.internal.IDistanceTransformer;

final class FixedMidPointRuler extends AbstractRuler {

    private static final long MISSING_DELTA = 10;

    private static final int OUTSIDE_OFFSET = 1;

    private final List<ScreenChunk> screenChunks = new ArrayList<ScreenChunk>();

    private final int min;

    private final int max;

    private long minValue = Long.MAX_VALUE;

    private long maxValue = Long.MIN_VALUE;

    FixedMidPointRuler(final int start, final int length,
            final IDistanceTransformer transform,
            final long forcedMidPointValue, final Collection<Span> spans) {
        this(new Rectangle(start, 0, length, 0), transform,
                forcedMidPointValue, spans);
    }

    /**
     * Deprecated Use
     * {@link #FixedMidPointRuler(int, int, IDistanceTransformer, long, Collection)}
     * start, length,
     * 
     * @param screen
     * @param transform
     * @param forcedMidPointValue
     * @param spans
     */
    @Deprecated
    FixedMidPointRuler(final Rectangle screen,
            final IDistanceTransformer transform,
            final long forcedMidPointValue, final Collection<Span> spans) {
        super(null, forcedMidPointValue);
        min = screen.x;
        max = screen.x + screen.width;

        final int screenMiddle = screen.x + screen.width / 2;
        final List<Span> leftHand = new ArrayList<Span>();
        final List<Span> rightHand = new ArrayList<Span>();

        final Rectangle leftScreen = new Rectangle(screen.x, screen.y,
                screen.width / 2, screen.height);
        final Rectangle rightScreen = new Rectangle(screenMiddle, screen.y,
                screen.width / 2, screen.height);

        final Span middle = sortSpans(forcedMidPointValue, spans, leftHand,
                rightHand);

        if (middle != null) {

            /*
             * 1 weigh the percentage left and right 2 use these weighs to
             * create screenspans and valuespans 3 decfeate left and right
             * screen based on (2)
             */

            final double leftPercentage = TimeUtil.ratio(forcedMidPointValue,
                    middle);
            final double rightPercentage = 1.0 - leftPercentage;

            final double transformedLength = transform.transform(middle
                    .getWidth());

            final double screenLeftPixels = transformedLength * leftPercentage;
            final double screenRightPixels = transformedLength
                    * rightPercentage;

            final long leftValueEnd = (long) TimeUtil.coordinateByRatio(
                    leftPercentage, middle);

            final Span leftValueSpan = Span.valueOf(middle.getStart(),
                    leftValueEnd);
            final Span rightValueSpan = Span.valueOf(leftValueEnd, middle
                    .getEnd());

            final Span leftScreenSpan = Span.valueOf(
                    (long) (screenMiddle - screenLeftPixels), screenMiddle);
            final Span rightScreenSpan = Span.valueOf(screenMiddle,
                    (long) (screenMiddle + screenRightPixels));
            screenChunks.add(new ScreenChunk(leftValueSpan, leftScreenSpan));
            screenChunks.add(new ScreenChunk(rightValueSpan, rightScreenSpan));

            leftScreen.width -= screenLeftPixels;
            rightScreen.x += screenRightPixels;
            rightScreen.width -= screenRightPixels;

        }

        createLeftScreenChunks(leftScreen, transform, leftHand);

        createRightChunks(rightScreen, transform, rightHand);

        Collections.sort(screenChunks, new Comparator<ScreenChunk>() {

            public int compare(final ScreenChunk o1, final ScreenChunk o2) {
                return (int) (o1.getScreenSpan().getStart() - o2
                        .getScreenSpan().getStart());
            }

        });

        if (screenChunks.size() > 0) {
            minValue = Long.MAX_VALUE;
            maxValue = Long.MIN_VALUE;
            for (ScreenChunk s : screenChunks) {
                if (minValue > s.valueSpan.getStart()) {
                    minValue = s.valueSpan.getStart();
                }
                if (maxValue < s.valueSpan.getEnd()) {
                    maxValue = s.valueSpan.getEnd();
                }
            }
        }
    }

    private void createRightChunks(final Rectangle screen,
            final IDistanceTransformer transform, final List<Span> spans) {
        double screenSizeLeft = screen.width;
        double cunkScreenXStart = screen.x;
        for (final Span span : spans) {
            final double screenSlotUptake = transform
                    .transform(span.getWidth());
            screenSizeLeft -= screenSlotUptake;

            final ScreenChunk screenChunk = new ScreenChunk(span, Span.valueOf(
                    (long) (0.5 + cunkScreenXStart),
                    (long) (cunkScreenXStart + screenSlotUptake)));
            screenChunks.add(screenChunk);
            cunkScreenXStart += screenSlotUptake;
            if (screenSizeLeft < 0) {
                break;
            }

        }
    }

    private void createLeftScreenChunks(final Rectangle screen,
            final IDistanceTransformer transform, final List<Span> spans) {
        double chunkScreenXStart = screen.x + screen.width;
        Collections.reverse(spans);

        double screenSizeLeft = screen.width;
        for (final Span span : spans) {
            final double screenSlotUptake = transform
                    .transform(span.getWidth());
            screenSizeLeft -= screenSlotUptake;

            chunkScreenXStart -= screenSlotUptake;

            final ScreenChunk screenChunk = new ScreenChunk(span, Span.valueOf(
                    (long) (0.5 + chunkScreenXStart),
                    (long) (chunkScreenXStart + screenSlotUptake)));
            screenChunks.add(screenChunk);
            if (screenSizeLeft < 0) {
                break;
            }
        }
    }

    /**
     * Separates the spans in a left hand and a right hand list based on the
     * forcedMidPointValue. this method will also return a span which spans over
     * the mid point, or null, if no such span exists.
     * 
     * @param forcedMidPointValue
     * @param spans
     * @param leftHand
     * @param rightHand
     * @return
     */
    private Span sortSpans(final long forcedMidPointValue,
            final Collection<Span> spans, final List<Span> leftHand,
            final List<Span> rightHand) {
        Span middle = null;
        for (final Span span : spans) {
            if (span.getWidth() == 0.0) {} else if (span.getStart() < forcedMidPointValue
                    && span.getEnd() > forcedMidPointValue) {
                middle = span;
            } else if (span.getStart() < forcedMidPointValue) {
                leftHand.add(span);
            } else {
                rightHand.add(span);
            }
        }

        if (middle == null) {
            if (leftHand.size() < 1) {
                leftHand.add(Span
                        .valueOf(forcedMidPointValue
                                - FixedMidPointRuler.MISSING_DELTA,
                                forcedMidPointValue));
            }
            if (rightHand.size() < 1) {
                rightHand
                        .add(Span.valueOf(forcedMidPointValue,
                                forcedMidPointValue
                                        + FixedMidPointRuler.MISSING_DELTA));
            }
        }
        return middle;
    }

    /**
     * Transforms the value x to a screen coordinate. If the x value falls
     * outside the known spans provided to the {@link FixedMidPointRuler}, a
     * negative coordinate will be returned. This is also the case if the
     * coordinate should actually be to the right of the screen.
     * 
     * 
     * 
     */
    @Override
    public int toScreen(final long x) {
        if (x > maxValue) {
            return max + 1;
        }
        if (x < minValue) {
            return -1;
        }

        int screenCoordinate = -FixedMidPointRuler.OUTSIDE_OFFSET;
        for (final ScreenChunk c : screenChunks) {
            if (c.valueSpan.contains(x)) {
                final double ratio = TimeUtil.ratio(x, c.valueSpan);
                screenCoordinate = (int) (0.5 + TimeUtil.coordinateByRatio(
                        ratio, c.screenSpan));
            }
        }

        return screenCoordinate;
    }
    
    public long toTimestamp(int x) {
        
        long ts = -1;
        for (final ScreenChunk c : screenChunks) {
            if (c.screenSpan.contains(x)) {
                final double ratio = TimeUtil.ratio(x, c.screenSpan);
                ts = (int) (0.5 + TimeUtil.coordinateByRatio(
                        ratio, c.valueSpan));
                
                if (ts > c.valueSpan.getEnd()) {
                    return c.valueSpan.getEnd();
                }
                if (ts < c.valueSpan.getStart()) {
                    return c.valueSpan.getStart();
                }
            }
        }

        return ts;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    @Override
    public String toString() {
        String str = "min = " + min + ", max = " + max + ", minValue = "
                + minValue + ", maxValue = " + maxValue
                + ", screenChunks.size = " + screenChunks.size();
        return str;
    }

    private static class ScreenChunk {

        private final Span valueSpan;

        private final Span screenSpan;

        public ScreenChunk(final Span span, final Span screenSpan) {
            valueSpan = span;
            this.screenSpan = screenSpan;
        }

        public Span getValueSpan() {
            return valueSpan;
        }

        public Span getScreenSpan() {
            return screenSpan;
        }

    }
}

package com.zealcore.se.ui.editors;

import com.zealcore.se.core.util.Span;
import com.zealcore.se.core.util.TimeUtil;

public class SimpleLinearScaleRuler extends AbstractRuler {

    private Span valueSpan;

    private Span screenSpan;

    private int min;

    private int max;

    public SimpleLinearScaleRuler(final long forcedMidPointValue,
            final Span valueSpan, final Span screenSpan) {
        super(null, forcedMidPointValue);
        this.valueSpan = valueSpan;
        this.screenSpan = screenSpan;
        min = (int) screenSpan.getStart();
        max = (int) (screenSpan.getStart() + screenSpan.getEnd());
    }

    public int max() {
        return max;
    }

    public int min() {
        return min;
    }

    public int toScreen(long x) {
        double ratio = TimeUtil.ratio(x, valueSpan);
        int screenCoordinate = (int) (0.5 + TimeUtil.coordinateByRatio(ratio,
                screenSpan));
        if (screenCoordinate > screenSpan.getEnd()) {
            return (int) screenSpan.getEnd();
        }
        if (screenCoordinate < screenSpan.getStart()) {
            return (int) screenSpan.getStart();
        }
        return screenCoordinate;
    }

    public long toTimestamp(final int x) {
        // double ratio = roundTwoDecimals(TimeUtil.ratio(x, screenSpan));
        double ratio = TimeUtil.ratio(x, screenSpan);
        long ts = (long) (0.5 + TimeUtil.coordinateByRatio(ratio, valueSpan));
        if (ts > valueSpan.getEnd()) {
            return valueSpan.getEnd();
        }
        if (ts < valueSpan.getStart()) {
            return valueSpan.getStart();
        }
        return ts;
    }
}

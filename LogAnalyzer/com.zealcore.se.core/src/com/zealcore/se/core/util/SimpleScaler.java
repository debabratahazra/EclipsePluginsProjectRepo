package com.zealcore.se.core.util;

public class SimpleScaler {

    private Span valueSpan;

    private Span screenSpan;

    public SimpleScaler(final Span valueSpan, final Span screenSpan) {
        this.valueSpan = valueSpan;
        this.screenSpan = screenSpan;

    }

    public long toTimestamp(final int x) {
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

    public int toScreen(final long ts) {
        double ratio = TimeUtil.ratio(ts, valueSpan);
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

    public double toScreen(final long ts, final double imageScale) {
        double ratio = TimeUtil.ratio(ts, valueSpan);
        double screenCoordinate = 0.5 + TimeUtil.coordinateByRatio(ratio,
                screenSpan);
        screenCoordinate = screenCoordinate / imageScale;

        return screenCoordinate;
    }

    public double toTimestamp(final int x, final double imageScale) {
        double ratio = TimeUtil.ratio(x, screenSpan);
        ratio = ratio * imageScale;
        if (ratio > 0.99 && ratio < 1.0) {
            ratio = 1.0;
        }
        double ts = 0.5 + TimeUtil.coordinateByRatio(ratio, valueSpan);

        if (ts > valueSpan.getEnd()) {
            return valueSpan.getEnd();
        }
        if (ts < valueSpan.getStart()) {
            return valueSpan.getStart();
        }
        return ts;
    }
}

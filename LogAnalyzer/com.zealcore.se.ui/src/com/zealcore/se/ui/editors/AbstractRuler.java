package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.ui.graphics.Graphics;
import com.zealcore.se.ui.graphics.IGraphics;
import com.zealcore.se.ui.internal.SWTUtil;

abstract class AbstractRuler implements IDrawableRuler {

    private static final int PIN_MARGIN = 2;

    private static final int RULER_HEIGHT = 20 + ClassicGantRenderer.LEGEND_HEIGHT;

    private static final int HALF = 2;

    private static final int LABEL_MARGIN = 10;

    private final Scaler scaler;

    private final long reference;

    AbstractRuler(final Scaler delegate, final long reference) {
        this.scaler = delegate;
        this.reference = reference;
    }

    public int toScreen(final long x) {
        return (int) this.scaler.fromValueToX(x);
    }

    public void draw(final Iterable<? extends ITimed> timed, final GC graphics,
            final int xStartClearance, final int xEndClearance) {
        draw(timed, Graphics.valueOf(graphics), xStartClearance, xEndClearance);
    }

    private void draw(final List<Long> values, final IGraphics graphics,
            final int xStartClearance, final int xEndClearance) {
        if (values.size() < 1) {
            return;
        }

        final int textHeight = graphics.getFontMetrics().getHeight();
        int labelXEnd = Integer.MIN_VALUE;
        Point start = null;
        Point end = null;

        Collections.sort(values);

        for (final Long element : values) {
            final long value = element;
            final char sign = value < this.reference ? '-' : '+';
            final long delta = Math.abs(value - this.reference);
            final String valueString = sign + Long.toString(delta);
            int x = toScreen(value) + xStartClearance / 2;
            // Do not try to draw text outside the window
            if (x > xEndClearance * 2) {
                break;
            }
            final int textLength = SWTUtil.textLength(graphics, valueString);
            final int xOffset = -textLength / AbstractRuler.HALF;
            final int labelXStart = x + xOffset - 2;
            if (labelXStart - AbstractRuler.LABEL_MARGIN > labelXEnd) {
                if (labelXStart > xStartClearance && labelXEnd < xEndClearance) {
                    labelXEnd = labelXStart + textLength;
                    graphics.drawString(valueString, labelXStart,
                            ClassicGantRenderer.LEGEND_HEIGHT, true);
                    graphics.drawLine(x + ClassicGantRenderer.LEGEND_HEIGHT,
                            textHeight + AbstractRuler.PIN_MARGIN
                                    + ClassicGantRenderer.LEGEND_HEIGHT, x
                                    + ClassicGantRenderer.LEGEND_HEIGHT,
                            AbstractRuler.RULER_HEIGHT);
                }
                if (start == null) {
                    start = new Point(x + ClassicGantRenderer.LEGEND_HEIGHT,
                            AbstractRuler.RULER_HEIGHT);
                }
                end = new Point(x + ClassicGantRenderer.LEGEND_HEIGHT,
                        AbstractRuler.RULER_HEIGHT);
            }
        }
        if (start != null && end != null) {
            graphics.drawLine(start.x, start.y, end.x, end.y);
        }

    }

    public void draw(final Iterable<? extends ITimed> timed,
            final IGraphics graphics, final int xStartClearance,
            final int xEndClearance) {

        final List<Long> values = new ArrayList<Long>();
        for (final ITimed element : timed) {
            final long value = element.getTimeReference();
            values.add(value);
            // if (element instanceof IDuration) {
            // final IDuration d = (IDuration) element;
            // values.add(value + d.getDurationTime());
            // }
        }
        draw(values, graphics, xStartClearance, xEndClearance);
    }
}

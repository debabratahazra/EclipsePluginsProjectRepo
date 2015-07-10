package com.zealcore.se.ui.graphics.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class Horizon extends Figure {

    private static final int ZIGZAG_HEIGHT = 15;

    private final int style;

    /**
     * Instantiates a new horizon fiure.
     * 
     * @param rect
     *                the rect
     * @param style
     *                the style must be SWT.LEFT or SWT.RIGHT
     */
    public Horizon(final Rectangle rect, final int style) {
        setBounds(new org.eclipse.draw2d.geometry.Rectangle(rect));
        this.style = style;
        setBackgroundColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
        setOpaque(true);

    }

    @Override
    protected void paintFigure(final Graphics gc) {
        final int alpha = gc.getAlpha();
        gc.setAlpha(35);
        if (getBackgroundColor() != null) {
            gc.setBackgroundColor(getBackgroundColor());

        }
        if (style == SWT.LEFT) {
            gc.fillPolygon(createLeftPolygon(gc));
        } else {
            createRightPolygon(gc);
        }
        gc.setAlpha(alpha);

    }

    private void createRightPolygon(final Graphics gc) {
        final int zigZagWidth = Horizon.ZIGZAG_HEIGHT / 2;
        final int ystart = getBounds().y;
        final int ystop = ystart + getBounds().height;

        final PointList poly = new PointList();

        poly.addPoint(new Point(bounds.x + bounds.width, bounds.y));
        poly.addPoint(new Point(bounds.x, bounds.y));

        final int zigZagX = getBounds().x + zigZagWidth;
        for (int y = ystart; y < ystop; y += Horizon.ZIGZAG_HEIGHT) {
            final Point p1 = new Point(zigZagX, y);
            final Point p2 = new Point(zigZagX + zigZagWidth, Math.min(ystop, y
                    + zigZagWidth));
            final Point p3 = new Point(zigZagX + zigZagWidth, Math.min(ystop, y
                    + zigZagWidth));

            final Point p4 = new Point(zigZagX, Math.min(ystop, y
                    + Horizon.ZIGZAG_HEIGHT));
            poly.addPoint(p1);
            poly.addPoint(p2);
            poly.addPoint(p3);
            poly.addPoint(p4);
        }
        poly.addPoint(new Point(getBounds().x + bounds.width, getBounds().y
                + getBounds().height));
        final PointList right = poly;
        gc.fillPolygon(right);
    }

    private PointList createLeftPolygon(final Graphics gc) {
        final int zigZagWidth = Horizon.ZIGZAG_HEIGHT / 2;

        final int ystart = getBounds().y;
        final int ystop = ystart + getBounds().height;

        final PointList poly = new PointList();

        poly.addPoint(new Point(bounds.x, bounds.y));
        poly.addPoint(new Point(bounds.x + bounds.width, bounds.y));

        final int zigZagX = getBounds().x + getBounds().width - zigZagWidth;
        for (int y = ystart; y < ystop; y += Horizon.ZIGZAG_HEIGHT) {
            final Point p1 = new Point(zigZagX, y);
            final Point p2 = new Point(zigZagX + zigZagWidth, Math.min(ystop, y
                    + zigZagWidth));
            final Point p3 = new Point(zigZagX + zigZagWidth, Math.min(ystop, y
                    + zigZagWidth));

            final Point p4 = new Point(zigZagX, Math.min(ystop, y
                    + Horizon.ZIGZAG_HEIGHT));
            poly.addPoint(p1);
            poly.addPoint(p2);
            poly.addPoint(p3);
            poly.addPoint(p4);
        }
        poly.addPoint(new Point(getBounds().x, getBounds().y
                + getBounds().height));
        return poly;
    }
}

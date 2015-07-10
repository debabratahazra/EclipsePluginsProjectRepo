/*
 * 
 */
package com.zealcore.se.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public final class ColumnLayout extends Layout {

    private static final int DEFAULT_SPACING = 2;

    private static final int DEFAULT_MARGIN_TOP = 20;

    private static final int DEFAULT_MARGIN = 10;

    private static final int TWO = 2;

    private int margin = ColumnLayout.DEFAULT_MARGIN;

    private int marginTop = ColumnLayout.DEFAULT_MARGIN_TOP;

    private int spacing = ColumnLayout.DEFAULT_SPACING;

    public ColumnLayout() {}

    public ColumnLayout(final int margin, final int marginTop, final int spacing) {
        this.margin = margin;
        this.marginTop = marginTop;
        this.spacing = spacing;
    }

    public static Layout getInstance() {
        return new ColumnLayout();
    }

    // cache

    private Point[] sizes;

    private int maxWidth;

    private int totalHeight;

    @Override
    protected Point computeSize(final Composite composite, final int wHint,
            final int hHint, final boolean flushCache) {

        final Control[] children = composite.getChildren();

        if (flushCache || sizes == null || sizes.length != children.length) {

            initialize(children);

        }

        int width = wHint;
        int height = hHint;

        if (wHint == SWT.DEFAULT) {
            width = maxWidth;
        }

        if (hHint == SWT.DEFAULT) {
            height = totalHeight;
        }

        return new Point(width + ColumnLayout.TWO * margin, height
                + ColumnLayout.TWO * margin);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void layout(final Composite composite, final boolean flushCache) {

        final Control[] children = composite.getChildren();

        if (flushCache || sizes == null || sizes.length != children.length) {

            initialize(children);

        }

        final Rectangle rect = composite.getClientArea();

        final int x = margin;
        int y = marginTop;

        final int width = Math.max(rect.width - ColumnLayout.TWO * margin,
                maxWidth);

        for (int i = 0; i < children.length; i++) {

            if (children[i].getLayoutData() instanceof ColumnData) {
                final ColumnData data = (ColumnData) children[i]
                        .getLayoutData();
                y += data.getMarginTop();
            }
            final int height = sizes[i].y;

            children[i].setBounds(x, y, width, height);

            y += height + spacing;

        }

    }

    void initialize(final Control[] children) {

        maxWidth = 0;

        totalHeight = 0;

        sizes = new Point[children.length];

        for (int i = 0; i < children.length; i++) {

            sizes[i] = children[i].computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
            if (children[i].getLayoutData() instanceof ColumnData) {
                final ColumnData data = (ColumnData) children[i]
                        .getLayoutData();
                sizes[i].y = data.height >= 0 ? data.height : sizes[i].y;
                totalHeight += data.getMarginTop();

            }
            maxWidth = Math.max(maxWidth, sizes[i].x);

            totalHeight += sizes[i].y;

        }

        totalHeight += (children.length - 1) * spacing;

    }

    public static final class ColumnData {
        private int height = -1;

        private int marginTop;

        public ColumnData(final int height) {
            super();
            this.height = height;
        }

        public ColumnData() {}

        public ColumnData marginTop(final int marginTop) {
            this.marginTop = marginTop;
            return this;
        }

        public int getMarginTop() {
            return marginTop;
        }
    }
}

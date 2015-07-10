package com.zealcore.se.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * The PropertyLayout layouts its children .
 */
public class PropertyLayout extends Layout {
    private static final int DEFAULT_ITEM_SPACING = 6;

    private static final int DEFAULT_MARGIN = 10;

    private static final int TWO = 2;

    private final int itemSpacing = PropertyLayout.DEFAULT_ITEM_SPACING;

    private Point[] sizes;

    private int totalHeight;

    private Rectangle leftColumn;

    private Rectangle rightColumn;

    @Override
    protected Point computeSize(final Composite composite, final int wHint,
            final int hHint, final boolean flushCache) {

        final Control[] children = composite.getChildren();
        if (flushCache || this.sizes == null
                || this.sizes.length != children.length) {
            initialize(children);
        }

        int width = wHint;
        int height = hHint;

        final Rectangle clientArea = composite.getClientArea();
        if (wHint == SWT.DEFAULT) {
            width = Math.max(clientArea.width, this.leftColumn.width
                    + this.rightColumn.width);
        }
        if (hHint == SWT.DEFAULT) {
            height = clientArea.y + this.totalHeight;
        }
        return new Point(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void layout(final Composite composite, final boolean flushCache) {

        final Control[] children = composite.getChildren();

        if (flushCache || this.sizes == null
                || this.sizes.length != children.length) {
            initialize(children);
        }

        final Rectangle rect = composite.getClientArea();

        final int margin = PropertyLayout.DEFAULT_MARGIN;
        int y = rect.y + margin;

        final int rightWidth = Math.max(this.rightColumn.width, rect.width
                - this.leftColumn.width)
                - PropertyLayout.TWO * margin - margin;
        final int rightX = margin + this.leftColumn.width + margin;
        for (int i = 0; i < children.length; i += PropertyLayout.TWO) {
            final Control left = children[i];
            final Control right = children[i + 1];
            final int height = Math.max(this.sizes[i].y, this.sizes[i + 1].y);
            left.setBounds(margin, y, this.leftColumn.width, height);

            right.setBounds(rightX, y, rightWidth, height);
            y += height + this.itemSpacing;

        }
    }

    void initialize(final Control[] children) {

        this.totalHeight = 0;

        this.sizes = new Point[children.length];

        this.leftColumn = new Rectangle(0, 0, 0, 0);
        this.rightColumn = new Rectangle(0, 0, 0, 0);
        for (int i = 0; i < children.length; i++) {
            Rectangle column;
            this.sizes[i] = children[i].computeSize(SWT.DEFAULT, SWT.DEFAULT,
                    true);

            if (i % PropertyLayout.TWO == 0) {
                column = this.leftColumn;
            } else {
                column = this.rightColumn;
            }

            if (children[i].getLayoutData() instanceof GridData) {
                final GridData data = (GridData) children[i].getLayoutData();
                this.sizes[i].x = Math.max(data.widthHint, column.width);
                this.sizes[i].y = Math.max(data.heightHint, this.sizes[i].y);
            }

            column.width = Math.max(this.sizes[i].x, this.leftColumn.width);
            column.height += this.sizes[i].y;
        }
        this.totalHeight = Math.max(this.rightColumn.height,
                this.leftColumn.height);
        this.totalHeight += children.length / PropertyLayout.TWO
                * this.itemSpacing;
    }
}

package com.zealcore.se.ui.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;

import com.zealcore.se.core.model.IDuration;

public class TaskExecution extends ObjectFigure {

    private static final int DROP_SHADOW_OFFSET = 2;

    private boolean dropShadow;

    private boolean gradient;

    public TaskExecution(final IDuration duration,
            final Rectangle representation) {
        setData(duration);
        final org.eclipse.draw2d.geometry.Rectangle rect = new org.eclipse.draw2d.geometry.Rectangle(
                representation);
        setBounds(rect);
    }

    public TaskExecution(final IDuration duration,
            final org.eclipse.draw2d.geometry.Rectangle representation) {
        setData(duration);
        setBounds(representation);
    }

    public IDuration getDuration() {
        return (IDuration) getData();
    }

    private final org.eclipse.draw2d.geometry.Rectangle clip = new org.eclipse.draw2d.geometry.Rectangle();

    @Override
    protected void paintFigure(final Graphics graphics) {

        // Drop Shadow effect
        final org.eclipse.draw2d.geometry.Rectangle box = getBounds();
        if (box.width < 1) {
            return;
        }
        graphics.getClip(clip);
        if (!clip.intersects(box)) {
            return;
        }
        if (dropShadow) {
            paintDropShadow(graphics);
        }
        graphics.setBackgroundColor(getBackgroundColor());
        if (gradient && box.width > 2) {
            graphics.setForegroundColor(getDevice().getSystemColor(
                    SWT.COLOR_WHITE));
            graphics.fillGradient(box, true);
        } else {
            graphics.fillRectangle(box);
        }

        graphics
                .setForegroundColor(getDevice().getSystemColor(SWT.COLOR_BLACK));
        graphics.drawRectangle(box);
    }

    private void paintDropShadow(final Graphics graphics) {
        final Color darkGray = getDevice().getSystemColor(SWT.COLOR_DARK_GRAY);
        graphics.setBackgroundColor(darkGray);
        final org.eclipse.draw2d.geometry.Rectangle shadow = getBounds()
                .getTranslated(TaskExecution.DROP_SHADOW_OFFSET,
                        TaskExecution.DROP_SHADOW_OFFSET);
        graphics.fillRectangle(shadow);
    }

    /**
     * Sets to true to draw as a gradient.
     * 
     * @param gradient
     *                the gradient
     */
    public final void setGradient(final boolean gradient) {
        this.gradient = gradient;
    }

    /**
     * Set to true to enable drop shadow.
     * 
     * @param dropShadow
     *                the drop shadow
     */
    public final void setDropShadow(final boolean dropShadow) {
        this.dropShadow = dropShadow;
    }

    @Override
    public void setData(final Object data) {
        if (data instanceof IDuration) {
            super.setData(data);
        } else {
            throw new IllegalArgumentException(
                    "Data must be an AbstractDuration");
        }
    }
}

package com.zealcore.se.ui.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import com.zealcore.se.core.model.ILogEvent;

public class Event extends ObjectFigure {

    public Event(final ILogEvent e, final int x, final int y, final int w,
            final int h) {
        setData(e);
        setBounds(new Rectangle(x, y, w, h));
        setOpaque(true);
    }

    @Override
    public ILogEvent getData() {
        return (ILogEvent) super.getData();
    }

    @Override
    protected void paintFigure(final Graphics graphics) {
        Color bg = graphics.getBackgroundColor();
        if (getBackgroundColor() != null) {
            graphics.setBackgroundColor(getBackgroundColor());
        }
        graphics.fillRectangle(getBounds());
        graphics.setBackgroundColor(bg);

    }
}

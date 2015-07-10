package com.zealcore.se.ui.contributions;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.zealcore.se.core.model.AbstractTaskInstance;
import com.zealcore.se.ui.editors.IRuler;
import com.zealcore.se.ui.graphics.figures.ObjectFigure;

public class TaskInstanceFigure extends ObjectFigure {

    private IRuler ruler;

    private final int y;

    public TaskInstanceFigure(final IRuler ruler, final int y,
            final AbstractTaskInstance i) {
        this.ruler = ruler;
        this.y = y;
        setData(i);
        setZ(0);
    }

    @Override
    protected void paintFigure(final Graphics graphics) {
        Color bg = graphics.getBackgroundColor();

        graphics.setBackgroundColor(Display.getDefault().getSystemColor(
                SWT.COLOR_GRAY));
        AbstractTaskInstance taskInstance = getData();
        int x0 = ruler.toScreen(taskInstance.getReleaseTs());
        int x1 = ruler.toScreen(taskInstance.getDeadline());

        if (x0 < 0 || x1 < 0) {
            return;
        }
        int y0 = y - 3;
        int y1 = y + 9;

        Rectangle outer = new Rectangle(x0, y0, x1 - x0, y1 - y0);
        setBounds(outer);
        graphics.fillRectangle(outer);
        graphics.setBackgroundColor(bg);
        graphics.drawRectangle(outer);
    }

    @Override
    public AbstractTaskInstance getData() {
        return (AbstractTaskInstance) super.getData();
    }
}

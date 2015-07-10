package com.zealcore.se.ui.graphics.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

public class ObjectFigure extends Figure {

    public static final int Z0 = 100;

    private Object data;

    private int z = Z0;

    public void setZ(final int z) {
        this.z = z;
    }

    public int getZ() {
        return z;
    }

    /**
     * The Constructor makes this figure default opaque (visible).
     */
    public ObjectFigure() {
        setOpaque(true);
    }

    public Object getData() {
        return this.data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    /**
     * Boxes a SWT rectangle to a draw2d rectangle 
     */
    public final void setBounds(final org.eclipse.swt.graphics.Rectangle rect) {
        super.setBounds(new Rectangle(rect));
    }

    public final Device getDevice() {
        return Display.getDefault();
    }

}

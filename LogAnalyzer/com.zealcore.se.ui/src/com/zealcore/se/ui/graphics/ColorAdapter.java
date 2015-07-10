/*
 * 
 */
package com.zealcore.se.ui.graphics;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public final class ColorAdapter implements IColor {

    private Color proxy;

    private int r;

    private int g;

    private int b;

    /**
     * Returns a new IColor representing the specified r, g and b values.
     * 
     * @param r
     *                the red
     * @param g
     *                the green
     * @param b
     *                the blue
     * 
     * @return the EColor
     */
    public static IColor valueOf(final int r, final int g, final int b) {
        final ColorAdapter instance = new ColorAdapter();
        instance.r = r;
        instance.g = g;
        instance.b = b;
        return instance;
    }

    /**
     * Returns a new IColor representing the specified Color value.
     * 
     * @param color
     *                the color
     * 
     * @return the E color
     */
    public static IColor valueOf(final Color color) {
        final ColorAdapter instance = new ColorAdapter();
        instance.proxy = color;
        instance.r = color.getRed();
        instance.g = color.getGreen();
        instance.b = color.getBlue();
        return instance;
    }

    /**
     * Tries to create a new Color from the IColor implementation.
     * 
     * @return the color
     */
    public Color toColor() {

        if (this.proxy != null && !this.proxy.isDisposed()) {
            return this.proxy;
        }
        final Display device = Display.getDefault();
        this.proxy = new Color(device, this.r, this.g, this.b);
        return this.proxy;
    }

    /**
     * {@inheritDoc}
     */
    public int b() {
        return this.b;
    }

    /**
     * {@inheritDoc}
     */
    public int g() {
        return this.g;
    }

    /**
     * {@inheritDoc}
     */
    public int r() {
        return this.r;
    }

    public static IColor valueOf(final RGB color) {
        final ColorAdapter colorAdapter = new ColorAdapter();
        colorAdapter.r = color.red;
        colorAdapter.g = color.green;
        colorAdapter.b = color.blue;
        return colorAdapter;
    }

}

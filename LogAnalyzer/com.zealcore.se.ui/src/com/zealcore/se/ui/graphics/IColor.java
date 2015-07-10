/*
 * 
 */
package com.zealcore.se.ui.graphics;

import org.eclipse.swt.graphics.Color;

/**
 * Bridge interface for SWT colors
 */
public interface IColor {

    /**
     * The Red intensity. Values are from 0 to 255
     * 
     * @return the intensity
     */
    int r();

    /**
     * The Green intensity. Values are from 0 to 255
     * 
     * @return the intensity
     */
    int g();

    /**
     * The Blue intensity. Values are from 0 to 255
     * 
     * @return the intensity
     */
    int b();

    /**
     * Triest o create a SWT color instance.
     * 
     * @return the color
     */
    Color toColor();
}

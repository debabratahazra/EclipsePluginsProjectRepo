/*
 * 
 */
package com.zealcore.se.ui.core;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.ui.graphics.IColor;

public interface IEventColorProvider {

    /**
     * Clears the color.
     * 
     * @param type
     *                the type
     */
    void clearColor(IType type);

    /**
     * Clears all the colors.
     * 
     */
    void clearColors();

    /**
     * Sets the color. Returns the previous color set for the given type, or
     * null if no color were set.
     * 
     * @param color
     *                the color to set. If null, resets to default value
     * @param type
     *                the type to set the color for
     * 
     * @return the color previosly defined, or null
     */
    IColor setColor(IType type, IColor color);

    /**
     * Gets the color for a given type. Or null if no color is set for this
     * type.
     * 
     * @param type
     *                the type
     * 
     * @return the color defined or null if no color i set.
     */
    IColor getColor(IType type);

    /**
     * Adds the listener to the collection of listeners who will be notified
     * when the color provider is changed, by sending it one of the messages
     * defined in the <code>IChangeListener</code> interface.
     * 
     * @param listener
     *                the listener which should be notified
     */
    void addChangeListener(IChangeListener listener);

    /**
     * Removes the listener from the collection of listeners who will be
     * notified when the color provider is changed.
     * 
     * @param listener
     *                the listener which should no longer be notified
     */
    void removeChangeListener(IChangeListener listener);

}

/*
 * 
 */
package com.zealcore.se.ui.editors;

import com.zealcore.se.core.model.ILogEvent;

interface IEventTimeLineRenderer {

    /**
     * Gets the event at screen coordinate x y.
     * 
     * @param y
     *                the y coordinate
     * @param x
     *                the x coordinate
     * 
     * @return the event at the x,y coordinate, or null if none is found
     */
    ILogEvent getEventAt(int x, int y);

    ILogEvent getBoxedEventAt(int x, int y);

    void draw(EventTimeLineData data);

}

/*
 * 
 */
package com.zealcore.se.ui.internal;

import com.zealcore.se.ui.ITimeCluster;

/**
 * A event which indicates that a time action occured.
 * 
 * 
 * The Object that implements the <code>ISynchable</code> interface gets this
 * <code>TimeEvent</code>.
 */
public class TimeEvent {

    private final ITimeCluster source;

    private final long previous;

    public TimeEvent(final ITimeCluster source, final long previous) {
        super();
        this.source = source;
        this.previous = previous;
    }

    /**
     * Gets the source of the time event.
     * 
     * @return the source of the time event.
     */
    public ITimeCluster getSource() {
        return this.source;
    }

    /**
     * Gets the time previous to this time event. If the time has not changed,
     * the previous time will be the same as the current time of the source
     * 
     * @return the previous time, or equal to the current
     */
    public long getPrevious() {
        return this.previous;
    }

}

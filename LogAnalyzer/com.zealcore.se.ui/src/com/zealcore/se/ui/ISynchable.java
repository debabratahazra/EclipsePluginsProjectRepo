package com.zealcore.se.ui;

import com.zealcore.se.ui.internal.TimeEvent;

/**
 * The ISynchable interface provide clients the ability be be synchronzied
 * through TimeEvents
 * 
 * @see TimeEvent
 * @author stch
 * 
 */
public interface ISynchable {

    /**
     * This method is called when an observable has produced a TimeEvent
     * 
     * @param source
     *                the time event spawned
     */
    void synch(TimeEvent source);

}

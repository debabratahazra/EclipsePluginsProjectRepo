/*
 * 
 */
package com.zealcore.se.core;

public interface IChangable {

    /**
     * Adds the listener to the collection of listeners who will be notified
     * when the item is changed, by sending it one of the messages defined in
     * the <code>IChangeListener</code> interface.
     * 
     * @param listener
     *                the listener which should be notified
     */
    void addChangeListener(IChangeListener listener);

    /**
     * Removes the listener from the collection of listeners who will be
     * notified when the item is changed.
     * 
     * @param listener
     *                the listener which should no longer be notified
     */
    void removeChangeListener(IChangeListener listener);
}

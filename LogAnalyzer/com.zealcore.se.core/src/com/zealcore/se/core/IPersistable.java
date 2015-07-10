/*
 * 
 */
package com.zealcore.se.core;

import org.eclipse.ui.IMemento;

public interface IPersistable {

    /**
     * Saves the state to a memento
     * 
     * @param memento
     *                the memento
     */
    void saveState(IMemento memento);

    /**
     * Initializes the state from a previous saveState.
     * 
     * @param memento
     *                the memento
     */
    void init(IMemento memento);
}

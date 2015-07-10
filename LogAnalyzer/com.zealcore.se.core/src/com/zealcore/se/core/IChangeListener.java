package com.zealcore.se.core;

/**
 * Interface that can receive change notifiecations from a Model object
 */
public interface IChangeListener {
    /**
     * Called with false when the listener is first attached to the model, and
     * called with true every time the model's state changes.
     */
    void update(boolean changed);
}

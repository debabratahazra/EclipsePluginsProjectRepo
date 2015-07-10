/*
 * 
 */
package com.zealcore.se.ui.internal.assertions;

/**
 * The listener interface for receiving modify events. The class that is
 * interested in processing a modify event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addModifyListener<code> method.
 * 
 * When the Modify occurs, that object's <code>componentModified</code> method is invoked.
 * 
 * @see ModifyEvent
 */
public interface IModifyListener {

    /**
     * Invoked when the component is modified.
     * 
     * @param event
     *                the event
     */
    void componentModified(final ModifyEvent event);
}

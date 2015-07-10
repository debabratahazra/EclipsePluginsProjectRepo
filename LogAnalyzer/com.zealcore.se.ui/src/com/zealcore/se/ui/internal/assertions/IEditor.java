/*
 * 
 */
package com.zealcore.se.ui.internal.assertions;

import org.eclipse.swt.widgets.Composite;

public interface IEditor {

    /**
     * Adds the listener to the collection of listeners who will be notified
     * when the instance is modified, by sending it one of the messages defined
     * in the <code>IModifyListener</code> interface.
     * 
     * @param listener
     *                the listener which should be notified
     */
    void addModifyListener(IModifyListener listener);

    /**
     * Removes the listener from the collection of listeners who will be
     * notified when the instance is modified.
     * 
     * @param listener
     *                the listener which should no longer be notified
     */
    void removeModifyListener(IModifyListener listener);

    /**
     * Saves the state to the IEditor instance.
     */
    void saveChanges();

    /**
     * Creates the UI components needed.
     * 
     * @param parent
     *                the parent
     * 
     * @return the created composite widget (or a container of widgets)
     */
    Composite createContents(Composite parent);

}

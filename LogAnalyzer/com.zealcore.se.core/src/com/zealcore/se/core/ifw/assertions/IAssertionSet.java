/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.IQuery;

public interface IAssertionSet extends IQuery {

    /**
     * Gets the name.
     * 
     * @return the name
     */
    String getName();

    /**
     * Gets the description.
     * 
     * @return the description
     */
    String getDescription();

    /**
     * Gets the assertions.
     * 
     * @return the assertions
     */
    Iterable<IAssertion> getAssertions();

    /**
     * Adds the listener to the collection of listeners who will be notified
     * when the assertion set is changed, by sending it one of the messages
     * defined in the <code>IChangeListener</code> interface.
     * 
     * @param listener
     *                the listener which should be notified
     */
    void addChangeListener(IChangeListener listener);

    /**
     * Removes the listener from the collection of listeners who will be
     * notified when the assertion set is changed.
     * 
     * @param listener
     *                the listener which should no longer be notified
     */
    void removeChangeListener(IChangeListener listener);

    /**
     * Adds the assertion.
     * 
     * @param assertion
     *                the assertion to add
     */
    void addAssertion(IAssertion assertion);

    /**
     * Removes the assertion.
     * 
     * @param assertion
     *                the assertion to remove
     */
    void removeAssertion(IAssertion assertion);

    /**
     * Saves the sate to a memento
     * 
     * @param memento
     *                the memento
     */
    void saveSate(IMemento memento);

    /**
     * Configures the assertion with data previously stored to the memento.
     * 
     * @param memento
     *                the memento
     */
    void init(IMemento memento, final ITypeRegistry typeService);

    IAssertionSetResult getResult();

    int getHitCount();

    void resetHitCount();

    void setSaveResults(boolean save);
}

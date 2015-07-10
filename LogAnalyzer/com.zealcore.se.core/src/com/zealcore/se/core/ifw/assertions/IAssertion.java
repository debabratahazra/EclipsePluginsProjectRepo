/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.IQuery;

public interface IAssertion extends IQuery {

    /**
     * Gets the severity.
     * 
     * @return the severity
     */
    int getSeverity();

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
     * Saves the sate to a memento
     * 
     * @param memento
     *                the memento
     */
    void saveState(IMemento memento);

    /**
     * Configures the assertion with data previously stored to the memento.
     * 
     * @param memento
     *                the memento
     */
    void init(IMemento memento, final ITypeRegistry typeService);

    void setName(String name);

    void setSeverity(int severity);

    void setDescription(String description);

    String getCondition();

    IAssertionResult getResult();
    
    int getHitCount();
    
    void resetHitCount();
    
    void setSaveResults(boolean save);
}

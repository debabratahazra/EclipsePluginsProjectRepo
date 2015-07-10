/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import com.zealcore.se.core.model.IObject;

public interface IAssertionResult extends Iterable<IObject> {

    /**
     * Gets the underlying assertion.
     * 
     * @return the assertion
     */
    IAssertion getAssertion();

    /**
     * Returns true if the Assertion has failed, false otherwise
     * 
     * @return boolean
     */
    boolean hasFailed();
}

/*
 * 
 */
package com.zealcore.se.core.services;

import com.zealcore.se.core.ifw.assertions.IAssertionResult;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.model.IObject;

public interface IAssertionReport {

    /**
     * Get assertion set results.
     * 
     * @return the assertion set results
     */
    Iterable<IAssertionSetResult> getAssertionSetResults();

    /**
     * Checks if the object has failures.
     * 
     * @param object
     *                the object
     * 
     * @return true, if object has failed
     */
    boolean hasFailed(IObject object);

    /**
     * Gets the failures for an object.
     * 
     * @param object
     *                the object
     * 
     * @return the failures for the object
     */
    Iterable<IAssertionResult> getFailures(IObject object);
}

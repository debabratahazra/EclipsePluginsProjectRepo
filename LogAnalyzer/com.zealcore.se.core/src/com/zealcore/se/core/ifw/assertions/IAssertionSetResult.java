/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

public interface IAssertionSetResult extends Iterable<IAssertionResult> {

    /**
     * Gets the underlying assertion set.
     * 
     * @return the assertion set
     */
    IAssertionSet getAssertionSet();

    /**
     * Returns true if the AssertionSet has failed, false otherwise An
     * AssertionSet has failed if ay of the assertions within that AssertionSet
     * has failed.
     * 
     * @return boolean
     */
    boolean hasFailed();

}

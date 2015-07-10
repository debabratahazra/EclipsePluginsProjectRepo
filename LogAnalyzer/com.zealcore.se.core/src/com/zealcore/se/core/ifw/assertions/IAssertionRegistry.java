/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

public interface IAssertionRegistry {

    Iterable<IAssertionSet> getAssertionSets();

    /**
     * Adds the assertion set.
     * 
     * @param set
     *                the set to add
     */
    void addAssertionSet(IAssertionSet set);

    /**
     * Removes the assertion set.
     * 
     * @param set
     *                the set to remove
     */
    void removeAssertionSet(IAssertionSet set);

    void save();

}

/**
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.zealcore.se.core.model.IObject;

class AssertionResult implements IAssertionResult {

    /** The assertion. */
    private final IAssertion assertion;

    /** The failed objects. */
    private final Set<IObject> failedObjects = new LinkedHashSet<IObject>();

    /**
     * The Constructor.
     * 
     * @param assertion
     *                the assertion
     */
    AssertionResult(final IAssertion assertion) {
        this.assertion = assertion;
    }

    public void addFailed(final IObject item) {
        this.failedObjects.add(item);
    }

    /**
     * {@inheritDoc}
     */
    public IAssertion getAssertion() {
        return this.assertion;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<IObject> iterator() {
        return this.failedObjects.iterator();
    }

    @Override
    public String toString() {
        return getAssertion().getName() + ", Condition: "
                + getAssertion().getCondition() + " [" + failedObjects.size() + "]";
    }

    public boolean hasFailed() {
        return iterator().hasNext();
    }
}
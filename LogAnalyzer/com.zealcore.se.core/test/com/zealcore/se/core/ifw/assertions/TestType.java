/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractLogEvent;

public class TestType extends AbstractLogEvent {

    private final int i;

    public TestType() {
        i = 0;
    }

    public TestType(final int i) {
        this.i = i;
    }

    /**
     * Gets the test, will always return 0.
     * 
     * @return the test
     */
    @ZCProperty(name = "Test", searchable = true)
    public int getTest() {
        return i;
    }

}

/**
 * 
 */
package com.zealcore.se.core.model.generic;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author psam
 * 
 */
public class GenericTaskExecutionTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {}

    @Test
    public void getDurationTimeTest() {
        GenericTaskSwitchEvent start = new GenericTaskSwitchEvent(100L);
        GenericTaskSwitchEvent stop = new GenericTaskSwitchEvent(100000L);
        GenericTaskExecution exec = new GenericTaskExecution(start, stop);
        Assert.assertEquals(stop.getTs() - start.getTs(), exec
                .getDurationTime());
    }
}

package com.odcgroup.workbench.testframework;
import com.odcgroup.workbench.core.AbstractActivator;


public class TestFrameworkCore extends AbstractActivator {

	public TestFrameworkCore() {
	}

    public static TestFrameworkCore getDefault() {
    	return (TestFrameworkCore) getDefault(TestFrameworkCore.class);
    }

}

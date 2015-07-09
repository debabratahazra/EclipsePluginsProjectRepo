package com.odcgroup.workbench.testframework.headless;

import com.odcgroup.workbench.core.AbstractActivator;


public class TestFrameworkHeadlessCore extends AbstractActivator {

	public TestFrameworkHeadlessCore() {
	}

    public static TestFrameworkHeadlessCore getDefault() {
    	return (TestFrameworkHeadlessCore) getDefault(TestFrameworkHeadlessCore.class);
    }

}

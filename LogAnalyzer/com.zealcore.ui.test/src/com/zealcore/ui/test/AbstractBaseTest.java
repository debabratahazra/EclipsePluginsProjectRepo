package com.zealcore.ui.test;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.UITestCaseSWT;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.windowtester.runtime.swt.locator.eclipse.WorkbenchLocator;

public class AbstractBaseTest extends UITestCaseSWT {

	protected static String LOGFILE_PATH;
	protected String FILE_SEPARATOR;
	
	
	@Override
	protected void oneTimeSetup() throws Exception {
		
		super.oneTimeSetup();
		
		LOGFILE_PATH = Messages.getString("AbstractBaseTest.logfile"); //$NON-NLS-1$
		FILE_SEPARATOR = System.getProperty("file.separator");
		
		IUIContext ui = getUI();
		ui.ensureThat(new WorkbenchLocator().hasFocus());
		ui.ensureThat(ViewLocator.forName("Welcome").isClosed());
		//Open "Log Analyzer" Perspective.
		ui.ensureThat(PerspectiveLocator.forId("com.zealcore.se.ui.PerspectiveFactory")
				.isActive());
		
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}

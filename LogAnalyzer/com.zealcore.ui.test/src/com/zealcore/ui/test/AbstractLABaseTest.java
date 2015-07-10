package com.zealcore.ui.test;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.windowtester.runtime.swt.locator.eclipse.WorkbenchLocator;

public class AbstractLABaseTest extends AbstractBaseTest {
	
	@Override
	protected void oneTimeSetup() throws Exception {
		
		super.oneTimeSetup();
		
		//1.3 Install Optima signal & event databases
		IUIContext ui = getUI();
		ui.click(new MenuItemLocator("Window/Preferences"));
		ui.wait(new ShellShowingCondition("Preferences"));
		ui.click(new FilteredTreeItemLocator("OSE"));
		ui.click(new FilteredTreeItemLocator("OSE/Event Database"));
		ui.click(new ButtonLocator("Add..."));
		ui.enterText(LOGFILE_PATH + FILE_SEPARATOR + "db" + FILE_SEPARATOR + "evtdb.o");
		ui.keyClick(WT.CR);
		ui.click(new FilteredTreeItemLocator("OSE/Signal Database"));
		ui.click(new ButtonLocator("Add..."));
		ui.enterText(LOGFILE_PATH + FILE_SEPARATOR + "db" + FILE_SEPARATOR + "sigdb.o");
		ui.keyClick(WT.CR);
		
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Preferences"));
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		IUIContext ui = getUI();
		ui.ensureThat(new WorkbenchLocator().hasFocus());
		ui.ensureThat(ViewLocator.forName("Welcome").isClosed());
		//Open "Log Analyzer" Perspective.
		ui.ensureThat(PerspectiveLocator.forId("com.zealcore.se.ui.PerspectiveFactory")
				.isActive());
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}

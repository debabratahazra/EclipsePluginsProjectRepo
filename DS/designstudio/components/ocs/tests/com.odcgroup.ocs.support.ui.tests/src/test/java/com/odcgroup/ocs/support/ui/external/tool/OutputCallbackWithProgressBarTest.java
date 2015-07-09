package com.odcgroup.ocs.support.ui.external.tool;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OutputCallbackWithProgressBarTest {
	
	private TestProgressMonitor testProgressMonitor;
	private ProgressMonitorDialog dialog;

	@Before
	public void setUp() {
		testProgressMonitor = new TestProgressMonitor();
		testProgressMonitor.beginTask("Demo", 100);
		Shell shell = Display.getDefault().getActiveShell();
		dialog = new ProgressMonitorDialog(shell);
		dialog.setBlockOnOpen(false);
		dialog.open();
	}
	
	@After
	public void tearDown() {
		dialog.close();
	}
	
	@Test
	public void testOutputCallbackWithProgressBar() {
		OutputCallbackWithProgressBar callback = new OutputCallbackWithProgressBar(dialog, testProgressMonitor);
		
		Assert.assertEquals(100, testProgressMonitor.getTotalWork());
		Assert.assertEquals(0, testProgressMonitor.getWorked());

		callback.outputLine(OutputCallbackWithProgressBar.PROGRESS_PREFIX + "50", false);
		Assert.assertEquals(0, testProgressMonitor.getWorked());
		callback.outputLine(OutputCallbackWithProgressBar.PROGRESS_PREFIX + " xyz", false);
		Assert.assertEquals(2, testProgressMonitor.getWorked());

		for (int i=0; i<25; i++) {
			callback.outputLine(OutputCallbackWithProgressBar.PROGRESS_PREFIX + " xyz", false);
		}
		Assert.assertEquals(52, testProgressMonitor.getWorked());

		for (int i=0; i<24; i++) {
			callback.outputLine(OutputCallbackWithProgressBar.PROGRESS_PREFIX + " xyz", false);
		}
		Assert.assertEquals(100, testProgressMonitor.getWorked());
	}

}

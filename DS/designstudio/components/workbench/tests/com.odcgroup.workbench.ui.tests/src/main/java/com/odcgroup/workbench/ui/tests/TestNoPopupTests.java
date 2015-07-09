package com.odcgroup.workbench.ui.tests;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author yandenmatten
 */
public class TestNoPopupTests {
	
	@BeforeClass
	public static void setupPopupKillerThread() {
		new PopupKiller().start();
	}

	/**
	 * 
	 */
	private static final String MESSAGE = "No popup should be displayed during unit tests execution. " +
	"If the build is stuck in this test it means the option " +
	"--launcher.suppressErrors is not applied to the test runner " +
	"in the build script.";

	@Test
	public void testNoErrorDialogShouldBeDisplayedDuringUnitTests() {
		ErrorDialog.openError(Display.getDefault().getActiveShell(), 
				"Error Dialog Check", 
				MESSAGE, 
				Status.OK_STATUS);
	}
	
	@Test
	public void testNoMessageDialogShouldBeDisplayedDuringUnitTests() {
		MessageDialog.openError(Display.getDefault().getActiveShell(), 
				"Message Dialog Check - openError", MESSAGE);
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), 
				"Message Dialog Check - openInformation", MESSAGE);
		MessageDialog.openWarning(Display.getDefault().getActiveShell(), 
				"Message Dialog Check - openWarning", MESSAGE);
		MessageDialog.openQuestion(Display.getDefault().getActiveShell(), 
				"Message Dialog Check - openQuestion", MESSAGE);
	}

}

package com.odcgroup.ocs.support.ui.external.tool;

import org.eclipse.swt.widgets.Display;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.ocs.support.external.tool.ExternalToolException;

/**
 * @author yan
 */
public class ExternalToolUITest  {
	
	@Test
	public void testExecuteJavaWithProgressBar() throws ExternalToolException, InterruptedException {
		int retCode = ExternalToolUI.executeJavaWithProgressBar(
				Display.getCurrent().getActiveShell(), 
				"Some title", 
				"-version");
		Assert.assertEquals("Should be successful", 0, retCode);
	}

	@Test
	public void testExecuteJavaErrorWithProgressBar() throws ExternalToolException, InterruptedException {
		int retCode = ExternalToolUI.executeJavaWithProgressBar(
				Display.getCurrent().getActiveShell(), 
				"Some title", 
				"Test");
		Assert.assertNotSame("Shouldn't be successful", 0, retCode);
	}

	@Test
	public void testExecuteCmdWithProgressBar() throws ExternalToolException, InterruptedException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
		int retCode = ExternalToolUI.executeCmdWithProgressBar(
				Display.getCurrent().getActiveShell(), 
				"Some title", 
				(isWindows?"cmd.exe /C ":"") + "echo Hello");
		Assert.assertEquals("Should return a 0 exit code", 0, retCode);
	}
	
	@Test
	public void testExecuteCmdNotFoundWithProgressBar() throws InterruptedException {
		try {
			ExternalToolUI.executeCmdWithProgressBar(
					Display.getCurrent().getActiveShell(), 
					"Some title", 
					"UnknownCommand");
			Assert.fail();
		} catch (ExternalToolException e) {
			// expected
		}
	}
	
	@Test
	public void testExecuteCmdRetCodeWithProgressBar() throws ExternalToolException, InterruptedException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
		int retCode = ExternalToolUI.executeCmdWithProgressBar(
				Display.getCurrent().getActiveShell(), 
				"Some title", 
				(isWindows?"cmd.exe /C ":"") + "exit 99");
		Assert.assertEquals("", 99, retCode);
	}
	
}

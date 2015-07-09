package com.odcgroup.ocs.support.external.tool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yan
 */
public class ExternalToolTest  {
	
	@Test
	public void testExecuteCmd() throws ExternalToolException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
		final StringBuffer cmdExecutionResult = new StringBuffer();
		int retCode = ExternalTool.executeCmd((isWindows?"cmd.exe /C ":"") + "echo Hello", new IOutputCallback() {
			public void outputLine(String line, boolean isError) {
				cmdExecutionResult.append(line);
			}
		});
		Assert.assertEquals("Should be successful", 0, retCode);
		Assert.assertTrue("Should contains the Hello word", 
				cmdExecutionResult.toString().contains("Hello"));
	}
	
	@Test
	public void testExecuteCmdNotFound() {
		final StringBuffer cmdExecutionResult = new StringBuffer();
		try {
			ExternalTool.executeCmd("unknownCommand", new IOutputCallback() {
				public void outputLine(String line, boolean isError) {
					cmdExecutionResult.append(line);
				}
			});
			Assert.fail();
		} catch (ExternalToolException e) {
			// Expected
		}
	}
	
	@Test
	public void testExecuteCmdRetCode() throws ExternalToolException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
		final StringBuffer cmdExecutionResult = new StringBuffer();
		int retCode = ExternalTool.executeCmd((isWindows?"cmd.exe /C ":"") + "exit -1", new IOutputCallback() {
			public void outputLine(String line, boolean isError) {
				cmdExecutionResult.append(line);
			}
		});
		Assert.assertEquals("Should be failing", -1, retCode);
	}
	
	@Test
	public void testExecuteJava() throws ExternalToolException {
		final StringBuffer cmdExecutionResult = new StringBuffer();
		int retCode = ExternalTool.executeJava("-version", new IOutputCallback() {
			public void outputLine(String line, boolean isError) {
				cmdExecutionResult.append(line);
			}
		});
		Assert.assertEquals("Should be successful", 0, retCode);
		Assert.assertTrue("Should contains the jdk", 
				cmdExecutionResult.toString().contains("java version"));
	}
	
	@Test
	public void testExecuteJavaError() throws ExternalToolException {
		final StringBuffer cmdExecutionResult = new StringBuffer();
		int retCode = ExternalTool.executeJava("Test", new IOutputCallback() {
			public void outputLine(String line, boolean isError) {
				cmdExecutionResult.append(line);
				System.out.println(line);
			}
		});
		Assert.assertNotSame("Should be successful", 0, retCode);
	}
}

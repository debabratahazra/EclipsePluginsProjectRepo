package com.temenos.ds.t24.data.util;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yandenmatten
 */
public class SafeIntegrationTestExecutorTest {
	
	@Test
	public void testNoProblemExecution() throws AssertionError, Exception {
		new SafeIntegrationTestExecutor(1, 0).execute(new Execution() { public void execute() {}});
	}
	
	@Test(expected=AssertionError.class)
	public void testAssertErrorExecution() throws AssertionError, Exception {
		new SafeIntegrationTestExecutor(1, 0).execute(new Execution() { public void execute() { throw new AssertionError("simulate assert error"); }});
	}
	
	@Test(expected=RuntimeException.class)
	public void testRuntimeExceptionExecution() throws AssertionError, Exception {
		new SafeIntegrationTestExecutor(1, 0).execute(new Execution() { public void execute() { throw new RuntimeException("simulate runtime exception failure"); }});
	}
	
	@Test(expected=InvocationTargetException.class)
	public void testExceptionExecution() throws AssertionError, Exception {
		new SafeIntegrationTestExecutor(1, 0).execute(new Execution() { public void execute() throws Exception { throw new Exception("simulate checked exception failure"); }});
	}
	
	@Test
	public void testRetries() throws AssertionError, Exception {
		class RetriesExecution implements Execution {
			public int nbExecution = 0;
			public void execute() throws Exception { 
				nbExecution++;
				throw new Exception("simulate checked exception failure"); 
			}
		}
		RetriesExecution execution = new RetriesExecution();
		try {
			new SafeIntegrationTestExecutor(1, 0).execute(execution);
		} catch (Exception e) {
			Assert.assertEquals(2, execution.nbExecution);
		}
	}
	

}

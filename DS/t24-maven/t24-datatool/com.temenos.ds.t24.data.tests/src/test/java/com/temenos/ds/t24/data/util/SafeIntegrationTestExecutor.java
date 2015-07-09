package com.temenos.ds.t24.data.util;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yandenmatten
 * 
 * Usage:
 * new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() {  }});
 */
public class SafeIntegrationTestExecutor {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SafeIntegrationTestExecutor.class);
	
	private static int DEFAULT_NUMBER_OF_RETRIES = 5;
	private static long DEFAULT_MAX_DELAY_BETWEEN_RETRIES = 60000;
	
	private int numberOfRetries = DEFAULT_NUMBER_OF_RETRIES;
	private long maxDelayBetweenRetries = DEFAULT_MAX_DELAY_BETWEEN_RETRIES;
	
	public SafeIntegrationTestExecutor() { }
	
	public SafeIntegrationTestExecutor(int numberOfRetries, long maxDelayBetweenRetries) {
		this.numberOfRetries = numberOfRetries;
		this.maxDelayBetweenRetries = maxDelayBetweenRetries;
	}
	
	private long getDelayBetweenRetries() {
		return (long)(Math.random() * maxDelayBetweenRetries);
	}
	
	public void execute(Execution execution) throws InvocationTargetException, AssertionError {
		int retry=0;
		while (true) {
			try {
				try {
					execution.execute();
					// success, then exit
					return;
				} catch (AssertionError e) {
					throw new InvocationTargetException(e);
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} 
			} catch (InvocationTargetException e) {
				if (retry++ >= numberOfRetries) {
					// Too much retries, fails
					if (e.getTargetException() instanceof AssertionError) {
						throw (AssertionError)e.getTargetException();
					} else if (e.getTargetException() instanceof RuntimeException) {
						throw (RuntimeException)e.getTargetException();
					} else {
						throw e;
					}
				} else {
					LOGGER.warn("Integration Test failed with this execption/error: ", e.getTargetException());
					try {
						// Wait a (random) while
						long delay = getDelayBetweenRetries();
						LOGGER.info("Waiting " + delay + " ms before next try.");
						Thread.sleep(delay);
					} catch (InterruptedException e2) {
						// Ignore
					}
				}
			}
		}
	}
	
}

package com.odcgroup.workbench.core.tests;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.spi.AppenderAttachable;

/**
 * @author yandenmatten
 */
public class DSLoggingTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DSLoggingTest.class);

    @Test
    public void testSlf4jImpl() {
    	try {
			Class.forName("org.slf4j.impl.StaticLoggerBinder");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("The sfj4j implementation is missing. This means the " +
					"DS logging is broken (i.e. not working). This implementation is provided by " +
					"com.odcgroup.logback-x.y.z p2 repository and is not present if you see this " +
					"message. Previous reason for this problem to appear were upgrade of slf4j " +
					"(through orbit upgrade) without a com.odcgroup.logback uprade.", e);
		}
    }
    
    @Test 
    public void testBasicLogging() {
    	logger.debug("debug message");
    	logger.info("info message");
    	logger.warn("warn message");
    	logger.error("error message");
    }
    
    @Test
    public void testSlf4jNoNoOpLogger() {
    	Logger eclipseLogger = LoggerFactory.getLogger(DSLoggingTest.class);
    	Assert.assertFalse("Logger not properly configured. NOPLogger means sfl4j couldn't initialize properly and will discard any log.", 
    			eclipseLogger.getClass().getName().equals("org.apache.log4j.spi.NOPLogger"));
    			// !(eclipseLogger instanceof NOPLogger)) doesn't work as all logger implementation inherits from NOPLogger
    	Assert.assertTrue("The logger manipulation done in M2EclipseLoggingHelper will not work", eclipseLogger instanceof AppenderAttachable);
    }
}

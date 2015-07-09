package com.odcgroup.workbench.core.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.template.log.ILogger;
import com.odcgroup.template.log.ILoggerFactory;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;

/**
 * Adapt Design Studio logger to the TemplateCreator. 
 * @author yan
 */
public class DSLoggerFactory implements ILoggerFactory {

	/**
	 * @return a logger for the clazz class
	 */
	public ILogger getLogger(Class<?> clazz) {
		final Logger logger = LoggerFactory.getLogger(TargetPlatform.class);
		return new ILogger() {
			public void error(String message, Exception e) {
				logger.error(message, e);
			}
			
			public void error(String message) {
				logger.error(message);
			}
			
			public void warn(String message, Exception e) {
				logger.warn(message, e);
			}
			
			public void warn(String message) {
				logger.warn(message);
			}
			
			public void info(String message, Exception e) {
				logger.info(message, e);
			}

			public void info(String message) {
				logger.info(message);
			}
			
			public void debug(String message, Exception e) {
				logger.debug(message, e);
			}
			
			public void debug(String message) {
				logger.debug(message);
			}
		};
	}

}

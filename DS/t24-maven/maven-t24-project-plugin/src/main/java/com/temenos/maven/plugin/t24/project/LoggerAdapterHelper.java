package com.temenos.maven.plugin.t24.project;

import org.apache.maven.plugin.logging.Log;

import com.odcgroup.ds.t24.packager.log.PackagerLogger;

public class LoggerAdapterHelper {

	static PackagerLogger createPackagerLoggerAdapter(final Log log) {
		
		return new PackagerLogger() {
			public void warn(String message, Throwable t) {
				log.warn(message, t);
			}
			
			public void warn(String message) {
				log.warn(message);
			}
			
			public void info(String message) {
				log.info(message);
			}
			
			public void error(String message, Throwable t) {
				log.error(message, t);
			}
			
			public void error(String message) {
				log.error(message);
			}
			
			public void debug(String message, Throwable t) {
				log.debug(message, t);
			}
			
			public void debug(String message) {
				log.debug(message);
			}
		};
	}
}

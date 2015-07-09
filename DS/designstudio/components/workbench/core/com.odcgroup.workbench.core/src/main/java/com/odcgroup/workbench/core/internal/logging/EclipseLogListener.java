package com.odcgroup.workbench.core.internal.logging;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.odcgroup.workbench.core.helper.FeatureSwitches;

public class EclipseLogListener implements LogListener, ILogListener {

	private static Map<Integer, Integer> severityMap = new HashMap<Integer, Integer>();
	static {
		severityMap.put(IStatus.OK, LogService.LOG_DEBUG);
		severityMap.put(IStatus.INFO, LogService.LOG_INFO);
		severityMap.put(IStatus.WARNING, LogService.LOG_WARNING);
		severityMap.put(IStatus.ERROR, LogService.LOG_ERROR);
	}
	
 	public void logged(LogEntry entry) {
 		log(entry.getLevel(),
 			entry.getBundle().getSymbolicName(),
 			entry.getMessage(),
 			entry.getException());		
   	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime.IStatus, java.lang.String)
	 */
	public void logging(IStatus status, String plugin) {
		log(severityMap.get(status.getSeverity()), status.getPlugin(), status.getMessage(), status.getException());
	}

	/**
	 * @param level
	 * @param marker
	 * @param message
	 * @param exception
	 */
	private void log(int level, String plugin, String message, Throwable exception) {
		// DS-8394
		if (FeatureSwitches.checkInfiniteCallInLogger.get() &&
				(level == LogService.LOG_WARNING || level == LogService.LOG_ERROR)) {
			if (infiniteLoop(new Throwable().getStackTrace())) {
				if (FeatureSwitches.checkInfiniteCallInLoggerSneakyThrow.get()) {
					Exceptions.sneakyThrow(new Throwable(message, exception));
				} else {
					System.err.println(message);
					exception.printStackTrace(System.err);
				}
			}
		}
		Marker marker = MarkerFactory.getMarker(plugin);
		Logger logger = LoggerFactory.getLogger(plugin);
		switch(level) {
 		case LogService.LOG_DEBUG  : logger.debug(marker, message, exception); break;
 		case LogService.LOG_INFO   : logger.info(marker, message, exception); break;
 		case LogService.LOG_WARNING: logger.warn(marker, message, exception); break;
 		case LogService.LOG_ERROR  : logger.error(marker, message, exception); break;
 		}
	}

	private String myClassName = EclipseLogListener.class.getName();

	private boolean infiniteLoop(StackTraceElement[] elements) {
		int index = 0;
		for (StackTraceElement element : elements) {
			if (myClassName.equals(element.getClassName()) && (index++ > 2)) {
				return true;
			}
		}
		return false;
	}

}
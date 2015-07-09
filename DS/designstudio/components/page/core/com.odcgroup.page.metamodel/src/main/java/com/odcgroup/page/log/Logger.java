package com.odcgroup.page.log;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.odcgroup.page.metamodel.PageMetamodelActivator;

/**
 * A simple Logger which uses the Plugin.
 * 
 * @author Gary Hayes
 */
public class Logger {
	
	/** The Logger to use. */
	private static ILog logger;
	
	/** Flag to determine whether we are in debug mode or not */
	private static final boolean DEBUG =
	      PageMetamodelActivator.getDefault().isDebugging() &&
	         "true".equalsIgnoreCase(Platform.getDebugOption("com.odcgroup.page.metamodel/debug"));
	
	/**
	 * Logs an info message.
	 * 
	 * @param message The message to log
	 */
	public static void info(String message) {
		if(DEBUG) {
			IStatus s = createStatus(IStatus.INFO, message, null);
			getLog().log(s);
		}
	}
	
	/**
	 * Logs an info message.
	 * 
	 * @param message The message to log
	 * @param throwable The throwable that occurred
	 */
	public static void info(String message, Throwable throwable) {
		if(DEBUG) {
			IStatus s = createStatus(IStatus.INFO, message, throwable);
			getLog().log(s);
		}
	}	
	
	/**
	 * Logs a warning message.
	 * 
	 * @param message The message to log
	 */
	public static void warning(String message) {
		IStatus s = createStatus(IStatus.WARNING, message, null);
		getLog().log(s);
	}	
	
	/**
	 * Logs an warning message.
	 * 
	 * @param message The message to log
	 * @param throwable The throwable that occurred
	 */
	public static void warning(String message, Throwable throwable) {
		IStatus s = createStatus(IStatus.WARNING, message, throwable);
		getLog().log(s);
	}	

	/**
	 * Logs an error message.
	 * 
	 * @param message The message to log
	 */
	public static void error(String message) {
		IStatus s = createStatus(IStatus.ERROR, message, null);
		getLog().log(s);
	}
	
	/**
	 * Logs an error message.
	 * 
	 * @param message The message to log
	 * @param throwable The throwable that occurred
	 */
	public static void error(String message, Throwable throwable) {
		IStatus s = createStatus(IStatus.ERROR, message, throwable);
		getLog().log(s);
	}	
	
	/**
	 * Creates a new IStatus object.
	 * 
	 * @param severity The severity
	 * @param message The message
	 * @param throwable The exception (or null)
	 * @return IStatus The Status
	 */
	private static IStatus createStatus(int severity, String message, Throwable throwable) {
		return new Status(severity, PageMetamodelActivator.PLUGIN_ID, IStatus.OK, message, throwable);
	}
	
	/**
	 * Gets the log. If the Log has not previously been set the ILog from the plugin is used.
	 * 
	 * @return ILog The log
	 */
	public static ILog getLog() {
		if (logger == null) {
			logger =  PageMetamodelActivator.getDefault().getLog();
		}
		return logger;
	}
	
	/**
	 * Sets the log. This is a convenience method for when we wish to run certain parts
	 * of the application in stand-alone. In this case the plugin is not active
	 * so Activator.getDefault().getLog() throws an Exception.
	 * 
	 * @param log The ILog to set
	 */
	public static void setLog(ILog log) {
		logger = log;
	}
}
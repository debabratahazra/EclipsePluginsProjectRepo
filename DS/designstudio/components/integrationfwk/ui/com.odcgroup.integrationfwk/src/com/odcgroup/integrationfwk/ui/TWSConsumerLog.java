package com.odcgroup.integrationfwk.ui;

import java.io.File;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class TWSConsumerLog {

	/** point out the log location within user workspace */
	private static final String _logLocation = File.separator + ".metadata"
			+ File.separator + ".log";

	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, IntegrationToolingActivator.PLUGIN_ID, code, message,
				exception);
	}

	/**
	 * get the TWS error log location.
	 * 
	 * @return log location.
	 */
	public static String getLogLocation() {
		String workSpace = getWorkSpaceLocation();
		if (workSpace == null) {
			return "";
		}
		return workSpace + _logLocation;
	}

	// TODO : This method has to be placed in Eclipse related util class while
	// re-factoring.
	/**
	 * get the user's current workspace location
	 * 
	 * @return workspace location as string
	 */
	public static String getWorkSpaceLocation() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root == null) {
			return null;
		}
		return root.getLocation().toOSString();
	}

	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	public static void log(IStatus status) {
		IntegrationToolingActivator.getDefault().getLog().log(status);
	}

	public static void logError(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	public static void logError(Throwable exception) {
		logError("Unexpected Exception", exception);
	}

	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}
}

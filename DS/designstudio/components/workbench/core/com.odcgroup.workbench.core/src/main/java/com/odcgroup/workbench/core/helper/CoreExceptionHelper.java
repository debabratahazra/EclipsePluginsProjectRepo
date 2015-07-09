package com.odcgroup.workbench.core.helper;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Helper class to create CoreExceptions
 *
 * @author amc
 *
 */
public class CoreExceptionHelper {

	/**
	 * Creates a new CoreException
	 * @param status an IStatus
	 * @param pluginId
	 * @param message
	 * @param cause the underlying exception
	 */
	public static CoreException createCoreException(int status, String pluginId, String message, Exception cause) {
		IStatus istatus = new Status(status, pluginId, message, cause);
		return new CoreException(istatus);
	}
	
	/**
	 * Creates a new CoreException
	 * @param status an IStatus
	 * @param pluginId
	 * @param message
	 * @param cause the underlying exception
	 */
	public static CoreException createCoreException(int status, String pluginId, String message) {
		IStatus istatus = new Status(status, pluginId, message);
		return new CoreException(istatus);
	}
	
	/**
	 * Creates and throws a CoreException
	 * @see #createCoreException
	 */
	public static void throwCoreException(int status, String pluginId, String message, Exception cause) throws CoreException {
		throw createCoreException(status, pluginId, message, cause);
	}
	
	/**
	 * Creates and throws a CoreException
	 * @see #createCoreException
	 */
	public static void throwCoreException(int status, String pluginId, String message) throws CoreException {
		throw createCoreException(status, pluginId, message);
	}
}

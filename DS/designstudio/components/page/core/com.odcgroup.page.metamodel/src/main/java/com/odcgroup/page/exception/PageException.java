/**
 * 
 */
package com.odcgroup.page.exception;

import org.eclipse.core.runtime.IStatus;

import com.odcgroup.page.log.Logger;

/**
 * General Exception class for the PageDesigner.
 * 
 * @author Gary Hayes
 */
public class PageException extends RuntimeException {
	
	/** The serial version id. */
	static final long serialVersionUID = 8512739539001430780L;
	
	/** The status of this exception */
	private IStatus status;
	
	/**
	 * @return the status of this exception
	 */
	public final IStatus getStatus() {
		return status;
	}

	/**
	 * Creates a new PageException.
	 */
	public PageException() {
	}

	/**
	 * Creates a new PageException.
	 * 
	 * @param message The message
	 */
	public PageException(String message) {
		super(message);
		Logger.error(message);
	}

	/**
	 * Creates a new PageException.
	 * 
	 * @param message The message
	 * @param status The status
	 */
	public PageException(String message, IStatus status) {
		super(message);
		Logger.error(message);
		this.status = status;
	}
	
	/**
	 * Creates a new PageException.
	 * 
	 * @param throwable The Exception
	 */
	public PageException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Creates a new PageException.
	 * 
	 * @param throwable The Exception
	 * @param status The status
	 */
	public PageException(Throwable throwable, IStatus status) {
		super(throwable);
		this.status = status;
	}

	/**
	 * Creates a new PageException.
	 * 
	 * @param message The message
	 * @param throwable The Exception
	 */
	public PageException(String message, Throwable throwable) {
		super(message, throwable);
		Logger.error(message);
	}

	/**
	 * Creates a new PageException.
	 * 
	 * @param message The message
	 * @param throwable The Exception
	 * @param status The status
	 */
	public PageException(String message, Throwable throwable, IStatus status) {
		super(message, throwable);
		Logger.error(message);
		this.status = status;
	}
	
}

package com.odcgroup.translation.ui.editor.controls;

import org.eclipse.core.runtime.IStatus;

/**
 *
 * @author pkk
 *
 */
public class TranslationLoadException extends Exception {

	/**  */
	private static final long serialVersionUID = -7644840643177022965L;
	
	/** Status object. */
	private IStatus status;

	/**
	 * Creates a new exception with the given status object.  The message
	 * of the given status is used as the exception message.
	 *
	 * @param status the status object to be associated with this exception
	 */
	public TranslationLoadException(IStatus status) {
		super(status.getMessage());
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	public Throwable getCause() {
		return status.getException();
	}


	/**
	 * @return
	 */
	public final IStatus getStatus() {
		return status;
	}

	
}

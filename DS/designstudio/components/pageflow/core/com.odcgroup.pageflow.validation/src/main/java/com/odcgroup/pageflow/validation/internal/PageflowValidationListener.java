package com.odcgroup.pageflow.validation.internal;

import org.eclipse.core.runtime.IStatus;

/**
 * @author atr
 * @version 1.0
 */
public interface PageflowValidationListener {
	
	/**
	 * @param status
	 * @return
	 */
	public void onValidation(IStatus status);
	
}

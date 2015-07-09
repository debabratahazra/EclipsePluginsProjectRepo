package com.odcgroup.page.validation.internal.constraint;

import org.eclipse.core.runtime.IStatus;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;

/**
 * Status aggregator
 * 
 * @author atr
 * @version 1.0
 */
public interface PageValidationListener {
	
	/**
	 * @param status
	 */
	public void onValidation(IStatus status);
	
	/**
	 * @param status
	 * @param property
	 */
	public void onValidation(IStatus status, Property property);

	/**
	 * @param status
	 * @param event
	 */
	public void onValidation(IStatus status, Event event);
}

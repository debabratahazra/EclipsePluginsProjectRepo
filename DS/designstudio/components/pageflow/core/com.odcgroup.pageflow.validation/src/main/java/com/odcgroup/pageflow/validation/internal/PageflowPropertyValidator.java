package com.odcgroup.pageflow.validation.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.validation.Activator;

/**
 * This class implements the validation constraints of Property in a Pageflow
 * 
 * @author atr
 */
public class PageflowPropertyValidator {

	/** */
	private PageflowValidationListener listener;

	/**
	 * @param property
	 * @return IStatus
	 */
	private IStatus propertyNameNotNull(Property property) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.propertyNameNotNull(property)) {
			String message = "Property''s Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param property
	 */
	public void accept(Property property) {
		listener.onValidation(propertyNameNotNull(property));
	}

	/**
	 * @param listener
	 */
	public PageflowPropertyValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}

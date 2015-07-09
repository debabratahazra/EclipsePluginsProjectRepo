package com.odcgroup.pageflow.validation.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.validation.Activator;

/**
 * This class implements the validation constraints of Action in a Pageflow
 * 
 * @author atr
 */
public class PageflowActionValidator {

	/** */
	private PageflowValidationListener listener;

	/**
	 * @param action
	 * @return IStatus
	 */
	private IStatus checkForActionNameUpper(Action action) {
		
		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.checkForActionNameUpper(action)) {
			StringBuilder message = new StringBuilder();
			message.append("The feature ''Name'' of <<");
			message.append(action.getName());
			message.append(">> must start with an Upper Case character");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param action
	 */
	public void accept(Action action) {
		listener.onValidation(checkForActionNameUpper(action));
	}

	/**
	 * @param listener
	 */
	public PageflowActionValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}

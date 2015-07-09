package com.odcgroup.pageflow.validation.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.validation.Activator;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class PageflowStateValidator {

	/** */
	private PageflowValidationListener listener;

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkUniqueOutgoingTransitions(State state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkUniqueOutgoingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("out-transitions of State<<");
			message.append(state.getDisplayName());
			message.append(">> should have unique name and display name");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkForStateNamePatternErr(State state) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkForStateNamePatternErr(state)) {
			StringBuilder message = new StringBuilder();
			message.append("The feature ''Name'' of <<");
			message.append(state.getDisplayName());
			message.append(">> must be changed as it matches a reserved value");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkforIDUniqueness(State state) {

		IStatus status = Status.OK_STATUS;

		// added for issue OCS-11024
		if (!PageflowConstraints.checkforIDUniqueness(state)) {
			StringBuilder message = new StringBuilder();
			message.append("The feature ''ID'' of <<");
			message.append(state.getDisplayName());
			message.append(">> has to be unique. The value specified ["); 
			message.append(state.getName()); 
			message.append("] is already taken");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 */
	public void accept(State state) {
		listener.onValidation(checkUniqueOutgoingTransitions(state));
		listener.onValidation(checkForStateNamePatternErr(state));
		listener.onValidation(checkforIDUniqueness(state));
	}

	/**
	 * @param listener
	 */
	public PageflowStateValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}
  	
 

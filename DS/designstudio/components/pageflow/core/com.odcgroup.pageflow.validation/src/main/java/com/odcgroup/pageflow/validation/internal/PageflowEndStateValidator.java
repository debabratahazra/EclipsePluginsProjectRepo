package com.odcgroup.pageflow.validation.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.validation.Activator;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class PageflowEndStateValidator {

	/** */
	private PageflowValidationListener listener;
	
	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkIncomingTransitions(EndState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkIncomingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("EndState<<");
			message.append(state.getDisplayName());
			message.append(">> must have at least one incoming transition");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkforNameUniqueness(EndState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkforNameUniqueness(state)) {
			StringBuilder message = new StringBuilder();
			message.append("EndState feature ''Name'' of <<");
			message.append(state.getDisplayName());
			message.append(">> should be unique");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkEndStateNameNotNull(EndState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.endStateNameNotNull(state)) {
			String message = "EndState's Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkEndStateIDNotNull(EndState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.endStateIDNotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("EndState <<");
			message.append(state.getDisplayName());
			message.append(">>'s ID must be specified");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkEndStateExitURINotNull(EndState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.endStateExitURINotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("EndState <<");
			message.append(state.getDisplayName());
			message.append(">>'s exit URI must be specified");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 */
	public void accept(EndState state) {
		listener.onValidation(checkIncomingTransitions(state));
		listener.onValidation(checkforNameUniqueness(state));
		listener.onValidation(checkEndStateNameNotNull(state));
		listener.onValidation(checkEndStateIDNotNull(state));
		listener.onValidation(checkEndStateExitURINotNull(state));
	}

	/**
	 * @param listener
	 */
	public PageflowEndStateValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}
  	
 

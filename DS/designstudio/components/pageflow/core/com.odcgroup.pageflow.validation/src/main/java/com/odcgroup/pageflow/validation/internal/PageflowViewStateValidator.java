package com.odcgroup.pageflow.validation.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.ViewState;
import com.odcgroup.pageflow.validation.Activator;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class PageflowViewStateValidator {

	/** */
	private PageflowValidationListener listener;
	
	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkIncomingTransitions(ViewState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkIncomingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("View State<<");
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
	private IStatus checkOutgoingTransitions(ViewState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkOutgoingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("View State<<");
			message.append(state.getDisplayName());
			message.append(">> must have at least one outgoing transition");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkViewStateNameNotNull(ViewState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.viewStateNameNotNull(state)) {
			String message = "ViewState Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkViewStateIDNotNull(ViewState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.viewStateNameNotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("View State<<");
			message.append(state.getDisplayName());
			message.append(">> ID's must be specified");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkPageReference(ViewState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkPageReference(state)) {
			StringBuilder message = new StringBuilder();
			message.append("The page URI for ViewState <<");
			message.append(state.getDisplayName());
			message.append(">> is not valid");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkPageReferenceNotEmpty(ViewState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkPageReferenceNotEmpty(state)) {
			StringBuilder message = new StringBuilder();
			message.append("The page URI for ViewState <<");
			message.append(state.getDisplayName());
			message.append(">> is empty");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}	
	
	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkforNameUniqueness(ViewState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkforNameUniqueness(state)) {
			StringBuilder message = new StringBuilder();
			message.append("ViewState feature ''Name'' of <<");
			message.append(state.getDisplayName());
			message.append(">> should be unique");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param state
	 */
	public void accept(ViewState state) {
		listener.onValidation(checkIncomingTransitions(state));
		listener.onValidation(checkOutgoingTransitions(state));
		listener.onValidation(checkViewStateNameNotNull(state));
		listener.onValidation(checkViewStateIDNotNull(state));
		listener.onValidation(checkPageReference(state));
		listener.onValidation(checkPageReferenceNotEmpty(state));
		//listener.onValidation(checkforNameUniqueness(state));
	}

	/**
	 * @param listener
	 */
	public PageflowViewStateValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}
  	
 

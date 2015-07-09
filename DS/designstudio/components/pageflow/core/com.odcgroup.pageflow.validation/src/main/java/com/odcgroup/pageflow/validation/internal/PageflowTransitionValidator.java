package com.odcgroup.pageflow.validation.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.validation.Activator;

/**
 * This class implements the validation constraints of Transition in a Pageflow
 * 
 * @author atr
 */
public class PageflowTransitionValidator {

	/** */
	private PageflowValidationListener listener;

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkIsIntResultOutgoingTransition(Transition transition) {
		
		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.isIntResultOutgoingTransition(transition)) {
			StringBuilder message = new StringBuilder();
			message.append("Transition ID specified <<");
			message.append(transition.getName());
			message.append(">> has to be an Integer");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkForTransitionIDUniqueness(Transition transition) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkforTransitionIDUniqueness(transition)) {
			StringBuilder message = new StringBuilder();
			message.append("The feature ''ID'' of <<");
			message.append(transition.getDisplayName());
			message.append(">> has to be unique. The value specified [");
			message.append(transition.getDisplayName());
			message.append("] is already taken");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
			  	
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkNoTransitionFromEndState(Transition transition) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.noTransitionFromEndState(transition)) {
			StringBuilder message = new StringBuilder();
			message.append("Transition<<");
			message.append(transition.getName());
			message.append(">> is invalid. EndState should not have out-transitions");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkForTransitionNamePatternErr(Transition transition) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkForTransitionNamePatternErr(transition)) {
			StringBuilder message = new StringBuilder();
			message.append("The feature ''Name'' of <<");
			message.append(transition.getDisplayName());
			message.append(">> must be changed as it matches a reserved value");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		  	
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkForTransitionNameUniqueness(Transition transition) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkforTransitionNameUniqueness(transition)) {
			StringBuilder message = new StringBuilder();
			message.append("The feature ''Name'' of <<");
			message.append(transition.getDisplayName());
			message.append(">> has to be unique. The value specified [");
			message.append(transition.getDisplayName());
			message.append("] is already taken");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}

		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkTransitionNameNotNull(Transition transition) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.transitionNameNotNull(transition)) {
			String message = "Transition Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		  	
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkTransitionIDNotNull(Transition transition) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.transitionIDNotNull(transition)) {
			StringBuilder message = new StringBuilder();
			message.append("Transition <<");
			message.append(transition.getDisplayName());
			message.append(">>'s ID must be specified");

			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		  	
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkTransActionNameNotNull(Transition transition) {
		
		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.transActionNameNotNull(transition)) {
			String message = "Transition Action Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		 	
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkSubPageflowStateSelfTransition(Transition transition) {
		
		IStatus status = Status.OK_STATUS;

		if (transition.getFromState() == transition.getToState() && transition.getFromState() instanceof SubPageflowState) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<<");
			message.append(transition.getFromState().getDisplayName()); 
			message.append(">> should not support self-transition");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
			
		return status;
	}

	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkDecitionStateSelfTransition(Transition transition) {
		
		IStatus status = Status.OK_STATUS;

		if (transition.getFromState() == transition.getToState() && transition.getFromState() instanceof DecisionState) {
			StringBuilder message = new StringBuilder();
			message.append("DecisionState<<"); 
			message.append(transition.getFromState().getDisplayName());
			message.append(">> should not support self-transition");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
			
		return status;
	}
	
	/**
	 * @param transition
	 * @return IStatus
	 */
	private IStatus checkTransActionURIEmpty(Transition transition) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.transActionURIEmpty(transition)) {
			StringBuilder message = new StringBuilder();
			message.append("Transition Action <<"); 
			message.append(transition.getName());
			message.append(">>'s URI is empty");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param transition
	 */
	public void accept(Transition transition) {
		listener.onValidation(checkIsIntResultOutgoingTransition(transition));
		listener.onValidation(checkForTransitionIDUniqueness(transition));
		listener.onValidation(checkNoTransitionFromEndState(transition));
		listener.onValidation(checkForTransitionNamePatternErr(transition));
		listener.onValidation(checkForTransitionNameUniqueness(transition));
		listener.onValidation(checkTransitionNameNotNull(transition));
		listener.onValidation(checkTransitionIDNotNull(transition));
		listener.onValidation(checkTransActionNameNotNull(transition));
		listener.onValidation(checkSubPageflowStateSelfTransition(transition));
		listener.onValidation(checkDecitionStateSelfTransition(transition));
		listener.onValidation(checkTransActionURIEmpty(transition));
	}

	/**
	 * @param listener
	 */
	public PageflowTransitionValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}

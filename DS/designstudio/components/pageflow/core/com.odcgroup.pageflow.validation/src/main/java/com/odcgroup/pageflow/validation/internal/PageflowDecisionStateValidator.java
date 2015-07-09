package com.odcgroup.pageflow.validation.internal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.validation.Activator;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class PageflowDecisionStateValidator {

	/** */
	private PageflowValidationListener listener;
	
//	(!((Pageflow)this.eContainer).transitions.exists(e|(e.fromState == this) && (e.toState == this)));
	
	private boolean hasSelfTransition(final DecisionState state) {
		return CollectionUtils.exists(((Pageflow)state.eContainer()).getTransitions(),
			new Predicate() {
				public boolean evaluate(Object object) {
					Transition transition = (Transition) object;
					return transition.getFromState() == state && transition.getToState() == state;
				}
			});
	}	
	
	/**
	 * DS-3539 multiple incoming transitions are allowed
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkIncomingTransitions(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkIncomingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("Decision State<<");
			message.append(state.getDisplayName());
			message.append(">> must have atleast one incoming transition");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkOutgoingTransitions(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkOutgoingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("Decision State<<");
			message.append(state.getDisplayName());
			message.append(">> must have at least two outgoing transitions");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkDecisionStateNameNotNull(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.decisionStateNameNotNull(state)) {
			String message = "DecisionState's Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkDecisionStateIDNotNull(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.decisionStateIDNotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("DecisionState <<");
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
	private IStatus checkDecisionStateActionNameNotNull(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.decisionStateActionNameNotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("DecisionState <<");
			message.append(state.getDisplayName());
			message.append(">>'s action must specified");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkDecisionActionNameNotNull(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.decisionActionNameNotNull(state)) {
			String message = "Decision state Action Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkDecisionActionURIEmpty(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.decisionActionURIEmpty(state)) {
			StringBuilder message = new StringBuilder();
			message.append("Decision state Action <<");
			message.append(state.getName());
			message.append(">>'s URI is empty");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkSelfTransition(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
//		context DecisionState WARNING "DecisionState<<"+this.displayName+">> should not support self-transition" :
//		(!((Pageflow)this.eContainer).transitions.exists(e|(e.fromState == this) && (e.toState == this)));
//	  		
		
		if (hasSelfTransition(state)) {
			StringBuilder message = new StringBuilder();
			message.append("DecisionState<<");
			message.append(state.getDisplayName());
			message.append(">> should not support self-transition");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkforNameUniqueness(DecisionState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkforNameUniqueness(state)) {
			StringBuilder message = new StringBuilder();
			message.append("DecisionState feature ''Name'' of <<");
			message.append(state.getDisplayName());
			message.append(">> should be unique");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 */
	public void accept(DecisionState state) {
		listener.onValidation(checkIncomingTransitions(state));
		listener.onValidation(checkOutgoingTransitions(state));
		listener.onValidation(checkDecisionStateNameNotNull(state));
		listener.onValidation(checkDecisionStateIDNotNull(state));
		listener.onValidation(checkDecisionStateActionNameNotNull(state));
		listener.onValidation(checkDecisionActionNameNotNull(state));
		listener.onValidation(checkDecisionActionURIEmpty(state));
		//listener.onValidation(checkDuplicateOutgoingTransitions(state));
		listener.onValidation(checkSelfTransition(state));
		//listener.onValidation(checkforNameUniqueness(state));
	}

	/**
	 * @param listener
	 */
	public PageflowDecisionStateValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}
  	
 

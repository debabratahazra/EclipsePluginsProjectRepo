package com.odcgroup.pageflow.validation.internal;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionMapping;
import com.odcgroup.pageflow.validation.Activator;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class PageflowSubPageflowStateValidator {

	/** */
	private PageflowValidationListener listener;
	
	private boolean hasSelfTransition(final SubPageflowState state) {
		return CollectionUtils.exists(((Pageflow)state.eContainer()).getTransitions(),
			new Predicate() {
				public boolean evaluate(Object object) {
					Transition transition = (Transition) object;
					return transition.getFromState() == state && transition.getToState() == state;
				}
			});
	}	
	
	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkSubPageflowStateNameNotNull(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.subPageflowStateNameNotNull(state)) {
			String message = "SubPageflowState Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkSubPageflowStateIDNotNull(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.subPageflowStateIDNotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<<");
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
	private IStatus checkIncomingTransitions(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkIncomingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<<");
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
	private IStatus checkOutgoingTransitions(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkOutgoingTransitions(state)) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<<");
			message.append(state.getDisplayName());
			message.append(">> must have at least one outgoing flow");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkSubPageflowStatePageflowNotNull(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.subPageflowStatePageflowNotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<<");
			message.append(state.getDisplayName());
			message.append(">>'s sub-pageflow must be specified");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus validateSubPageflow(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.validateSubPageflow(state)) {
			StringBuilder message = new StringBuilder();
			message.append("The sub-pageflow <<");
			message.append(PageflowConstraints.subPageflowURI(state));
			message.append(">> specified is not valid");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkSelfReference(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.checkSelfReference(state)) {
			StringBuilder message = new StringBuilder();
			message.append("Pageflow<<");
			message.append(((Pageflow)state.eContainer()).getName());
			message.append(">> cannot be referenced as sub-pageflow");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkCyclicReference(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.checkCyclicReference(state)) {
			StringBuilder message = new StringBuilder();
			message.append("Cyclic Reference with Pageflow<<");
			message.append(state.getSubPageflow().getName());
			message.append(">> found");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * DS-4907
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkCyclicSubPageflow(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.checkCyclicSubPageflow(state)) {
			StringBuilder message = new StringBuilder();
			message.append("Cyclic SubPageFlow with Pageflow<<");
			message.append(state.getSubPageflow().getName());
			message.append(">> found");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkHasTransitionMappings(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		boolean hasTransitionMappings = 
			(PageflowConstraints.subPageflowStatePageflowNotNull(state) 
					&& PageflowConstraints.validateSubPageflow(state)) 
						? PageflowConstraints.checkForEndStateMapping(state) 
						: true;	

		// added for issue OCS-11024
		if (!hasTransitionMappings) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<<");
			message.append(state.getDisplayName());
			message.append(">> must have at least ");
			message.append(PageflowConstraints.getSubPageflowEnd(state));
			message.append(" transition mapping(s) with ");
			message.append(PageflowConstraints.getSubPageflowEndStateNames(state));
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkHasEndstate(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		boolean hasEndstate = 
			(PageflowConstraints.subPageflowStatePageflowNotNull(state) 
				&&	PageflowConstraints.validateSubPageflow(state) 
				&&  state.getTransitionMappings().size()>0) 
						? PageflowConstraints.checkForEndStateAndTransition(state) 
						: true;
		
		if (!hasEndstate) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<< ");
			message.append(state.getDisplayName());
			message.append(">> ''s outgoing transition must be mapped to at least one EndState");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkDuplicateEndStatesForTransitionMappings(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkDuplicateEndStatesForTransitionMappings(state)) {
			String message = "TransitionMappings with the same end-state found";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkforNameUniqueness(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkforNameUniqueness(state)) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState feature ''Name'' of <<");
			message.append(state.getDisplayName());
			message.append(">> should be unique");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkHasSelfTransition(SubPageflowState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (hasSelfTransition(state)) {
			StringBuilder message = new StringBuilder();
			message.append("SubPageflowState<<");
			message.append(state.getDisplayName());
			message.append(">> should not support self-transition");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * DS-4907
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Transition getFaultyTransitionMapping(SubPageflowState substate){
		List<TransitionMapping> mappings = substate.getTransitionMappings();
		Pageflow parent = (Pageflow) substate.eContainer();
		for (TransitionMapping mapping : mappings) {
			Transition tr = mapping.getTransition();
			if (!parent.equals(tr.eContainer())) {
				return tr;
			}
		}
		return null;
	}
	
	/**
	 * DS-4907
	 * @param state
	 * @return
	 */
	private IStatus checkTransitionsInMappings(SubPageflowState state) {		
		IStatus status = Status.OK_STATUS;
		Transition tr = getFaultyTransitionMapping(state);
		if (tr != null) {
			StringBuilder message = new StringBuilder();
			message.append("Transition<<");
			message.append(tr.getDisplayName());
			message.append(">> in transition-mappings is not from the parent pageflow");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);			
		}
		return status;
	}

	/**
	 * @param state
	 */
	public void accept(SubPageflowState state) {
		listener.onValidation(checkSubPageflowStateNameNotNull(state));
		listener.onValidation(checkSubPageflowStateIDNotNull(state));
		listener.onValidation(checkIncomingTransitions(state));
		listener.onValidation(checkOutgoingTransitions(state));
		listener.onValidation(checkSubPageflowStatePageflowNotNull(state));
		listener.onValidation(validateSubPageflow(state));
		listener.onValidation(checkSelfReference(state));
		listener.onValidation(checkCyclicReference(state));
		//listener.onValidation(checkHasAtLeastOneTransitionMapping(state));
		listener.onValidation(checkHasTransitionMappings(state));
		listener.onValidation(checkHasEndstate(state));
		listener.onValidation(checkDuplicateEndStatesForTransitionMappings(state));
		//listener.onValidation(checkDuplicateOutgoingTransitions(state));
		//listener.onValidation(checkforNameUniqueness(state));
		listener.onValidation(checkHasSelfTransition(state));
		listener.onValidation(checkCyclicSubPageflow(state));
		listener.onValidation(checkTransitionsInMappings(state));
	}

	/**
	 * @param listener
	 */
	public PageflowSubPageflowStateValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}
  	
 

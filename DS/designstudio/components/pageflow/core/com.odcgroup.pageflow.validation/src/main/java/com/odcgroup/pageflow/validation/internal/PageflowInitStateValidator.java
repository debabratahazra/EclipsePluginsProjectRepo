package com.odcgroup.pageflow.validation.internal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.validation.Activator;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class PageflowInitStateValidator {

	/** */
	private PageflowValidationListener listener;
	
	private boolean hasOneOutTransition(InitState state) {
		return CollectionUtils.countMatches(((Pageflow)state.eContainer()).getTransitions(),
				new Predicate() {
					public boolean evaluate(Object object) {
						State fromState = ((Transition)object).getFromState();
						return fromState != null && fromState instanceof InitState;
					}
				}) == 1;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkHasOneOutTransition(InitState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!hasOneOutTransition(state)) {
			StringBuilder message = new StringBuilder();
			message.append("InitState<<");
			message.append(state.getName());
			message.append(">> must have one out transition");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkInitStateNameNotNull(InitState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.initStateNameNotNull(state)) {
			String message = "InitState's Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}

	/**
	 * @param state
	 * @return IStatus
	 */
	private IStatus checkInitStateIDNotNull(InitState state) {
		
		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.initStateIDNotNull(state)) {
			StringBuilder message = new StringBuilder();
			message.append("InitState <<");
			message.append(state.getDisplayName());
			message.append(">>'s ID must be specified");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param state
	 */
	public void accept(InitState state) {
		listener.onValidation(checkHasOneOutTransition(state));
		listener.onValidation(checkInitStateNameNotNull(state));
		listener.onValidation(checkInitStateIDNotNull(state));
	}

	/**
	 * @param listener
	 */
	public PageflowInitStateValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}
  	
 

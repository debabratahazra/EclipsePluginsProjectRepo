package com.odcgroup.pageflow.validation.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.validation.Activator;

/**
 * This class implements the validation constraints for a pageflow
 * 
 * @author atr
 */
public class PageflowPageflowValidator {

	/** */
	private PageflowValidationListener listener;
	
	
	/** return true if the pageflow has at least one InitState */
	private boolean hasInitState(Pageflow pageflow) {
		return CollectionUtils.exists(pageflow.getStates(),
			new Predicate() {
				public boolean evaluate(Object object) {
					return object instanceof InitState;
				}
			});
	}	

	/** return true if the pageflow has at least one EndState */
	private boolean hasEndState(Pageflow pageflow) {
		return CollectionUtils.exists(pageflow.getStates(),
			new Predicate() {
				public boolean evaluate(Object object) {
					return object instanceof EndState;
				}
			});
	}	
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkInitState(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		if (!hasInitState(pageflow)) {
			StringBuilder message = new StringBuilder();
			message.append("InitState is not specified for pageflow<<"); 
			message.append(pageflow.getName());
			message.append(">>");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkEndState(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		if (!(hasEndState(pageflow))) {
			StringBuilder message = new StringBuilder();
			message.append("EndState is not specified for pageflow<<"); 
			message.append(pageflow.getName());
			message.append(">>");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkForModelNameUpper(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;

		if (!PageflowConstraints.checkForModelNameUpper(pageflow)) {
			StringBuilder message = new StringBuilder();
			message.append("The feature ''Name'' of <<"); 
			message.append(pageflow.getName());
			message.append(">> must start with an Upper Case character");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkPageflowNameNotNull(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		// added for issue 	OCS-23097
		if (!PageflowConstraints.pageflowNameNotNull(pageflow)) {
			String message = "Pageflow name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkPageflowFileNameNotNull(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.pageflowFileNameNotNull(pageflow)) {
			String message = "Target File Name must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkForModelTargetNameCharErr(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.checkForModelTargetNameCharErr(pageflow)) {
			String message = "The feature Model file name/Model Target name must only contain charaters a..z, A..Z, 0..9,-";
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message, null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkforFileNamePattern(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
			
		if (!PageflowConstraints.checkforFileNamePattern(pageflow, "-pageflow-")) {
			StringBuilder message = new StringBuilder();
			message.append("The feature Model Target Name of <<"); 
			message.append(pageflow.getName());
			message.append(">> should be changed as the naming convention is: *-pageflow-*");
			status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkIsModelNameDifferentFromFile(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.isModelNameDifferentFromFile(pageflow)) {
			StringBuilder message = new StringBuilder();
			message.append("Pageflow model name<<"); 
			message.append(pageflow.getName());
			message.append(">> is different from its file name");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	/**
	 * @param pageflow
	 * @return IStatus
	 */
	private IStatus checkCycles(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		List<List<State>> allSCC = PageflowConstraints.getStronglyConnectedComponents(pageflow);
		
		if (! allSCC.isEmpty()) { 
			StringBuilder message = new StringBuilder();
			message.append("Pageflow model name<<"); 
			message.append(pageflow.getName());
			message.append(">> has ");
			int nbCycles = allSCC.size();
			message.append(nbCycles);
			message.append(" cyclical flow");
			if (nbCycles > 1) {
				message.append("s");
			}
			message.append(" (due to decision states).\n");
			
			int index = 1;
			for (List<State> cycle : allSCC) {
				message.append(" Cycle ");
				message.append(index++);
				message.append(": list of connected decision states (redirectors)\n");
				for (State state : cycle) {
					message.append("\tName=");
					message.append(state.getDisplayName());
					message.append(" ID=");
					message.append(state.getName());
					message.append("\n ");
				}
				//message.append("\n");
			}
			//System.out.println(message.toString());
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	
	private IStatus buildDependencyGraph(Pageflow pageflow) {

		IStatus status = Status.OK_STATUS;
		
		if (!PageflowConstraints.isModelNameDifferentFromFile(pageflow)) {
			StringBuilder message = new StringBuilder();
			message.append("Pageflow model name<<"); 
			message.append(pageflow.getName());
			message.append(">> is different from its file name");
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		
		return status;
	}
	
	
	private IStatus findPropertiesConflictsInFlattenedPageflow(Pageflow pageflow) {
		IStatus status = Status.OK_STATUS;
		PageflowPropertiesConflictDetector detector = new PageflowPropertiesConflictDetector(pageflow);
		if (detector.hasConflicts()) {
			StringBuilder message = new StringBuilder();
			message.append("Pageflow model name<<"); 
			message.append(pageflow.getName());
			message.append(">> contains ambiguous declaration of properties in some nested pageflows:\n");
			message.append(detector.getErrorMessage());
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message.toString(), null);
		}
		return status;
	}
	

	/**
	 * @param pageflow
	 */
	public void accept(Pageflow pageflow) {
		listener.onValidation(checkInitState(pageflow));
		listener.onValidation(checkEndState(pageflow));
		listener.onValidation(checkForModelNameUpper(pageflow));
		listener.onValidation(checkPageflowNameNotNull(pageflow));
		listener.onValidation(checkPageflowFileNameNotNull(pageflow));
		listener.onValidation(checkForModelTargetNameCharErr(pageflow));
		listener.onValidation(checkforFileNamePattern(pageflow));
		listener.onValidation(checkIsModelNameDifferentFromFile(pageflow));
		listener.onValidation(checkCycles(pageflow));
		listener.onValidation(buildDependencyGraph(pageflow));
		listener.onValidation(findPropertiesConflictsInFlattenedPageflow(pageflow));
	}

	/**
	 * @param listener
	 */
	public PageflowPageflowValidator(PageflowValidationListener listener) {
		this.listener = listener;
	}

}


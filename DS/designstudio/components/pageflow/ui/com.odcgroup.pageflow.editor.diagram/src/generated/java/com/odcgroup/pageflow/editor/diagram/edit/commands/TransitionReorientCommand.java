/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.commands;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.odcgroup.pageflow.editor.diagram.edit.policies.PageflowBaseItemSemanticEditPolicy;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionMapping;

/**
 * @generated
 */
public class TransitionReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public TransitionReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof Transition) {
			return false;
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated NOT
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof State && newEnd instanceof State)) {
			return false;
		}
		State target = getLink().getToState();
		if (!(getLink().eContainer() instanceof Pageflow)) {
			return false;
		}

		if (getNewSource() instanceof InitState) {
			Pageflow pageflow = (Pageflow) ((InitState) getNewSource()).eContainer();
			List<?> transitions = pageflow.getTransitions();
			int temp = 0;
			for (Object obj : transitions) {
				Transition transition = (Transition) obj;
				if (transition.getFromState().equals(((InitState) getNewSource()))) {
					if (temp >= 0)
						return false;
					temp++;
				}
			}
		}
		Pageflow container = (Pageflow) getLink().eContainer();
		return PageflowBaseItemSemanticEditPolicy.LinkConstraints.canExistTransition_3001(container, getNewSource(),
				target, true);
	}

	/**
	 * @generated NOT
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof State && newEnd instanceof State)) {
			return false;
		}
		State source = getLink().getFromState();
		if (!(getLink().eContainer() instanceof Pageflow)) {
			return false;
		}
		Pageflow container = (Pageflow) getLink().eContainer();
		return PageflowBaseItemSemanticEditPolicy.LinkConstraints.canExistTransition_3001(container, source,
				getNewTarget(), true);
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * for a source of subpageflow, remove the transition from the transition mapping
	 * @generated NOT
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		if (getOldSource() instanceof SubPageflowState) {
			SubPageflowState substate = (SubPageflowState) getOldSource();
			List mappings = substate.getTransitionMappings();
			for (int i = 0; i < mappings.size(); i++) {
				TransitionMapping mapping = (TransitionMapping) mappings.get(i);
				if (getLink().equals(mapping.getTransition())) {
					mappings.remove(mapping);
				}
			}
		}
		getLink().setFromState(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setToState(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected Transition getLink() {
		return (Transition) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected State getOldSource() {
		return (State) oldEnd;
	}

	/**
	 * @generated
	 */
	protected State getNewSource() {
		return (State) newEnd;
	}

	/**
	 * @generated
	 */
	protected State getOldTarget() {
		return (State) oldEnd;
	}

	/**
	 * @generated
	 */
	protected State getNewTarget() {
		return (State) newEnd;
	}
}

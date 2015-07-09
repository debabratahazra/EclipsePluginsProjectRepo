/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.odcgroup.process.diagram.edit.policies.ProcessBaseItemSemanticEditPolicy;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessItem;

/**
 * @generated
 */
public class FlowReorientCommand extends EditElementCommand {

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
	public FlowReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof Flow) {
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
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof ProcessItem && newEnd instanceof ProcessItem)) {
			return false;
		}
		ProcessItem target = getLink().getTarget();
		if (!(getLink().eContainer() instanceof Process)) {
			return false;
		}
		Process container = (Process) getLink().eContainer();
		return ProcessBaseItemSemanticEditPolicy.LinkConstraints.canExistFlow_3001(container, getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof ProcessItem && newEnd instanceof ProcessItem)) {
			return false;
		}
		ProcessItem source = getLink().getSource();
		if (!(getLink().eContainer() instanceof Process)) {
			return false;
		}
		Process container = (Process) getLink().eContainer();
		return ProcessBaseItemSemanticEditPolicy.LinkConstraints.canExistFlow_3001(container, source, getNewTarget());
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
	 * @generated
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setSource(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setTarget(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected Flow getLink() {
		return (Flow) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected ProcessItem getOldSource() {
		return (ProcessItem) oldEnd;
	}

	/**
	 * @generated
	 */
	protected ProcessItem getNewSource() {
		return (ProcessItem) newEnd;
	}

	/**
	 * @generated
	 */
	protected ProcessItem getOldTarget() {
		return (ProcessItem) oldEnd;
	}

	/**
	 * @generated
	 */
	protected ProcessItem getNewTarget() {
		return (ProcessItem) newEnd;
	}
}

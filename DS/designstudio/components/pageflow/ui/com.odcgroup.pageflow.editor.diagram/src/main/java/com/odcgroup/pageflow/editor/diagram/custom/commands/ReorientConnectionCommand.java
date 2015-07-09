package com.odcgroup.pageflow.editor.diagram.custom.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.Transition;

/**
 * @author pkk
 *
 */
public class ReorientConnectionCommand extends EditElementCommand {
	
	/**
	 * The reorient direction.
	 */
	private final int reorientDirection;
	
	/**
	 * The relationship's new source or target.
	 */
	private final EObject newEnd;



	/**
	 * @param request
	 */
	public ReorientConnectionCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);		
		this.reorientDirection = request.getDirection();
		this.newEnd = request.getNewRelationshipEnd();

	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
            IAdaptable info) throws ExecutionException {
		Transition connection = (Transition) getElementToEdit();
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE && newEnd instanceof State) {
			connection.setFromState((State) newEnd);
		} else if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET && newEnd instanceof State) {
			connection.setToState((State) newEnd);
		}
		return CommandResult.newOKCommandResult(connection);

	}
	
	/**
	 * The source can be changed to a new output terminal. The target can be
	 * changed to a new target terminal.
	 */
	public boolean canExecute() {

		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE
			&& (newEnd instanceof EndState)) {
			return false;
		}

		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET
			&& (newEnd instanceof InitState)) {
			return false;
		}
		return super.canExecute();
	}


}

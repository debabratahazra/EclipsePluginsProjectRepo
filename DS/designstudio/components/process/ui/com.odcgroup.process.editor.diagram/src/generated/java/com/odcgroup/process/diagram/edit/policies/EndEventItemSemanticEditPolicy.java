/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.policies;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.odcgroup.process.diagram.edit.commands.FlowCreateCommand;
import com.odcgroup.process.diagram.edit.commands.FlowReorientCommand;
import com.odcgroup.process.diagram.edit.parts.FlowEditPart;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.ParallelFork;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.Task;
import com.odcgroup.process.model.impl.ProcessImpl;

/**
 * @generated
 */
public class EndEventItemSemanticEditPolicy extends ProcessBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		CompoundCommand cc = getDestroyEdgesCommand();
		addDestroyShortcutsCommand(cc);
		cc.add(getGEFWrapper(new DestroyElementCommand(req)));
		return cc.unwrap();
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (ProcessElementTypes.Flow_3001 == req.getElementType()) {
			return getGEFWrapper(new FlowCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated NOT
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (req.getSource() instanceof ParallelFork || req.getSource() instanceof StartEvent) {
			return UnexecutableCommand.INSTANCE;
		}
		/*
		if (req.getSource() instanceof StartEvent 
				|| req.getSource() instanceof ParallelMerge
				|| req.getSource() instanceof ExclusiveMerge){
			return UnexecutableCommand.INSTANCE;			
		}
		 */
		if (ProcessElementTypes.Flow_3001 == req.getElementType()) {
			return getGEFWrapper(new FlowCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated NOT
	 */
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		if (req.getRelationship() instanceof Flow) {
			Flow flow = (Flow) req.getRelationship();
			if (req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET
					&& (flow.getSource() instanceof ParallelFork || flow.getSource() instanceof StartEvent)) {
				return UnexecutableCommand.INSTANCE;
			}
		}
		switch (getVisualID(req)) {
		case FlowEditPart.VISUAL_ID:
			return getGEFWrapper(new FlowReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

	/**
	 * @param source
	 * @return
	 */
	protected boolean checkOutgoingFlowExistsToAnyTask(EObject source) {
		ProcessImpl process = (ProcessImpl) source.eContainer().eContainer();
		List transitions = process.getTransitions();
		if (containsOutgoingFlowToAnyTask(transitions, source)) {
			return true;
		}
		return false;
	}

	/**
	 * @param transitions
	 * @param source
	 * @return
	 */
	protected boolean containsOutgoingFlowToAnyTask(List<?> transitions, EObject source) {
		for (Object object : transitions) {
			Flow transition = (Flow) object;
			if (transition.getSource().equals(source) && transition.getTarget() instanceof Task) {
				return true;
			}
		}
		return false;
	}

}

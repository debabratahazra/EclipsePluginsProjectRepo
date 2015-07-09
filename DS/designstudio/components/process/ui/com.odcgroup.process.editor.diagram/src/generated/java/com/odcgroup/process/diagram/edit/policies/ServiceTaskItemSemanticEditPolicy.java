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
import com.odcgroup.process.model.Gateway;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.impl.ProcessImpl;

/**
 * @generated
 */
public class ServiceTaskItemSemanticEditPolicy extends ProcessBaseItemSemanticEditPolicy {

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
	 * @generated NOT
	 */
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (!allowOnlyOneOutgoingTransition(req.getSource())) {
			return UnexecutableCommand.INSTANCE;
		}
		if (ProcessElementTypes.Flow_3001 == req.getElementType()) {
			return getGEFWrapper(new FlowCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated NOT
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (!(req.getSource() instanceof Gateway) && !allowOnlyOneInComingTransition(req.getTarget())) {
			return UnexecutableCommand.INSTANCE;
		}
		if (req.getSource() instanceof Gateway && checkRedundantFlow(req.getSource(), req.getTarget())) {
			return UnexecutableCommand.INSTANCE;
		}
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
			if (req.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE
					&& !allowOnlyOneOutgoingTransition(req.getNewRelationshipEnd())) {
				return UnexecutableCommand.INSTANCE;
			}
			if (req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET
					&& !(flow.getSource() instanceof Gateway)
					&& checkRedundantFlow(flow.getSource(), req.getNewRelationshipEnd())
					&& !allowOnlyOneInComingTransition(req.getNewRelationshipEnd())) {
				return UnexecutableCommand.INSTANCE;
			}
			if (req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET
					&& flow.getSource() instanceof Gateway
					&& checkRedundantFlow(flow.getSource(), req.getNewRelationshipEnd())) {
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
	protected boolean allowOnlyOneOutgoingTransition(EObject source) {
		ProcessImpl process = (ProcessImpl) source.eContainer().eContainer();
		List transitions = process.getTransitions();
		if (containsServiceTask(transitions, source, true)) {
			return false;
		}
		return true;
	}

	/**
	 * @param target
	 * @return
	 */
	protected boolean allowOnlyOneInComingTransition(EObject target) {
		ProcessImpl process = (ProcessImpl) target.eContainer().eContainer();
		List transitions = process.getTransitions();
		if (containsServiceTask(transitions, target, false)) {
			return false;
		}
		return true;

	}

	/**
	 * @param source
	 * @param target
	 * @return
	 */
	protected boolean checkRedundantFlow(EObject source, EObject target) {
		ProcessImpl process = (ProcessImpl) target.eContainer().eContainer();
		List transitions = process.getTransitions();
		for (Object object : transitions) {
			Flow flow = (Flow) object;
			if (flow.getSource().equals(source) && flow.getTarget().equals(target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param transitions
	 * @param source
	 * @return
	 */
	protected boolean containsServiceTask(List<?> transitions, EObject eObject, boolean source) {
		for (Object object : transitions) {
			Flow transition = (Flow) object;
			if (!(transition.getSource() instanceof StartEvent)) {
				if (source) {
					if (transition.getSource().equals(eObject)) {
						return true;
					}
				} else {
					if (transition.getTarget().equals(eObject)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}

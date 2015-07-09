/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.editor.diagram.edit.commands.TransitionCreateCommand;
import com.odcgroup.pageflow.editor.diagram.edit.commands.TransitionReorientCommand;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionEditPart;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.Transition;

/**
 * @generated
 */
public class InitStateItemSemanticEditPolicy extends PageflowBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		CompoundCommand cc = getDestroyEdgesCommand();
		addDestroyShortcutsCommand(cc);
		View view = (View) getHost().getModel();
		if (view.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
			req.setElementToDestroy(view);
		}
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
		// allow only one out transition from initstate
		if (!isLoneTransition(req.getContainer())) {
			return UnexecutableCommand.INSTANCE;
		}

		if (PageflowElementTypes.Transition_3001 == req.getElementType()) {
			return getGEFWrapper(new TransitionCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * check for transitions from initstate
	 * @param initState
	 * @return
	 */
	private boolean isLoneTransition(EObject initState) {
		Pageflow pageflow = (Pageflow) initState.eContainer();
		EList transitions = pageflow.getTransitions();
		for (Iterator iter = transitions.iterator(); iter.hasNext();) {
			Transition transition = (Transition) iter.next();
			if (transition.getFromState().equals(initState)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (PageflowElementTypes.Transition_3001 == req.getElementType()) {
			return getGEFWrapper(new TransitionCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case TransitionEditPart.VISUAL_ID:
			return getGEFWrapper(new TransitionReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}

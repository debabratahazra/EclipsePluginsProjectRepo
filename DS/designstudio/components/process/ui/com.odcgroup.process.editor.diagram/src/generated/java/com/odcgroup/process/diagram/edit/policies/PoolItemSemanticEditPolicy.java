/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.process.diagram.edit.parts.ComplexGatewayEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolProcessItemCompartmentEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskEditPart;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;

/**
 * @generated
 */
public class PoolItemSemanticEditPolicy extends ProcessBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		CompoundCommand cc = getDestroyEdgesCommand();
		addDestroyChildNodesCommand(cc);
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
	protected void addDestroyChildNodesCommand(CompoundCommand cmd) {
		View view = (View) getHost().getModel();
		EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation != null) {
			return;
		}
		for (Iterator it = view.getChildren().iterator(); it.hasNext();) {
			Node node = (Node) it.next();
			switch (ProcessVisualIDRegistry.getVisualID(node)) {
			case PoolProcessItemCompartmentEditPart.VISUAL_ID:
				for (Iterator cit = node.getChildren().iterator(); cit.hasNext();) {
					Node cnode = (Node) cit.next();
					switch (ProcessVisualIDRegistry.getVisualID(cnode)) {
					case UserTaskEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case ServiceTaskEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case ComplexGatewayEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case ExclusiveMergeEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case ParallelForkEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case ParallelMergeEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case StartEventEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case EndEventEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					}
				}
				break;
			}
		}
	}

}

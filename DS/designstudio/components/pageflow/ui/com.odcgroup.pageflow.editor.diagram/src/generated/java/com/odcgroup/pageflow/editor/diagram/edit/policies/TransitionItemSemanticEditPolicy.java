/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.policies;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionMapping;

/**
 * @generated
 */
public class TransitionItemSemanticEditPolicy extends PageflowBaseItemSemanticEditPolicy {

	/**
	 * if the transition source is a subpageflowstate, remove the 
	 * associated transition mapping with reference to the transition
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getGEFWrapper(new DestroyElementCommand(req) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				EObject destructee = getElementToDestroy();
				if (destructee instanceof Transition) {
					Transition transition = (Transition) destructee;
					if (transition.getFromState() instanceof SubPageflowState) {
						SubPageflowState substate = (SubPageflowState) transition.getFromState();
						List mappings = substate.getTransitionMappings();
						for (int i = 0; i < mappings.size(); i++) {
							TransitionMapping mapping = (TransitionMapping) mappings.get(i);
							if (transition.equals(mapping.getTransition())) {
								mappings.remove(mapping);
							}
						}
					}
				}
				return super.doExecuteWithResult(monitor, info);
			}
		});
	}

}

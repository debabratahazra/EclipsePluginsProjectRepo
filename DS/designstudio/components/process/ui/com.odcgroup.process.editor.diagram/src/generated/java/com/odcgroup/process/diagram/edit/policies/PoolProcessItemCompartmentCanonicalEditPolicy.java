/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.process.diagram.edit.parts.ComplexGatewayEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskEditPart;
import com.odcgroup.process.diagram.part.ProcessDiagramUpdater;
import com.odcgroup.process.diagram.part.ProcessNodeDescriptor;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated
 */
public class PoolProcessItemCompartmentCanonicalEditPolicy extends CanonicalEditPolicy {

	/**
	 * @generated
	 */
	Set myFeaturesToSynchronize;

	/**
	 * @generated
	 */
	protected List getSemanticChildrenList() {
		View viewObject = (View) getHost().getModel();
		List result = new LinkedList();
		for (Iterator it = ProcessDiagramUpdater.getPoolProcessItemCompartment_5001SemanticChildren(viewObject)
				.iterator(); it.hasNext();) {
			result.add(((ProcessNodeDescriptor) it.next()).getModelElement());
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected boolean isOrphaned(Collection semanticChildren, final View view) {
		int visualID = ProcessVisualIDRegistry.getVisualID(view);
		switch (visualID) {
		case UserTaskEditPart.VISUAL_ID:
		case ServiceTaskEditPart.VISUAL_ID:
		case ComplexGatewayEditPart.VISUAL_ID:
		case ExclusiveMergeEditPart.VISUAL_ID:
		case ParallelForkEditPart.VISUAL_ID:
		case ParallelMergeEditPart.VISUAL_ID:
		case StartEventEditPart.VISUAL_ID:
		case EndEventEditPart.VISUAL_ID:
			if (!semanticChildren.contains(view.getElement())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

	/**
	 * @generated
	 */
	protected Set getFeaturesToSynchronize() {
		if (myFeaturesToSynchronize == null) {
			myFeaturesToSynchronize = new HashSet();
			myFeaturesToSynchronize.add(ProcessPackage.eINSTANCE.getPool_Tasks());
			myFeaturesToSynchronize.add(ProcessPackage.eINSTANCE.getPool_Gateways());
			myFeaturesToSynchronize.add(ProcessPackage.eINSTANCE.getPool_Start());
			myFeaturesToSynchronize.add(ProcessPackage.eINSTANCE.getPool_End());
		}
		return myFeaturesToSynchronize;
	}

}

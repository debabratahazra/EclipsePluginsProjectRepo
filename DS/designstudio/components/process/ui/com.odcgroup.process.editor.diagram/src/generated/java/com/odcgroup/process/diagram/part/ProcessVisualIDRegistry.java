/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.process.diagram.edit.parts.ComplexGatewayEditPart;
import com.odcgroup.process.diagram.edit.parts.ComplexGatewayNameEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeNameEditPart;
import com.odcgroup.process.diagram.edit.parts.FlowEditPart;
import com.odcgroup.process.diagram.edit.parts.FlowNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeNameEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolNameEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolProcessItemCompartmentEditPart;
import com.odcgroup.process.diagram.edit.parts.ProcessEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskNameEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventNameEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskNameEditPart;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessPackage;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class ProcessVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "com.odcgroup.process.editor.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (ProcessEditPart.MODEL_ID.equals(view.getType())) {
				return ProcessEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return com.odcgroup.process.diagram.part.ProcessVisualIDRegistry.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(Platform.getDebugOption(DEBUG_KEY))) {
				ProcessDiagramEditorPlugin.getInstance().logError(
						"Unable to parse view type as a visualID number: " + type);
			}
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static String getType(int visualID) {
		return String.valueOf(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (ProcessPackage.eINSTANCE.getProcess().isSuperTypeOf(domainElement.eClass())
				&& isDiagram((Process) domainElement)) {
			return ProcessEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		String containerModelID = com.odcgroup.process.diagram.part.ProcessVisualIDRegistry.getModelID(containerView);
		if (!ProcessEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (ProcessEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = com.odcgroup.process.diagram.part.ProcessVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProcessEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case PoolProcessItemCompartmentEditPart.VISUAL_ID:
			if (ProcessPackage.eINSTANCE.getUserTask().isSuperTypeOf(domainElement.eClass())) {
				return UserTaskEditPart.VISUAL_ID;
			}
			if (ProcessPackage.eINSTANCE.getServiceTask().isSuperTypeOf(domainElement.eClass())) {
				return ServiceTaskEditPart.VISUAL_ID;
			}
			if (ProcessPackage.eINSTANCE.getComplexGateway().isSuperTypeOf(domainElement.eClass())) {
				return ComplexGatewayEditPart.VISUAL_ID;
			}
			if (ProcessPackage.eINSTANCE.getExclusiveMerge().isSuperTypeOf(domainElement.eClass())) {
				return ExclusiveMergeEditPart.VISUAL_ID;
			}
			if (ProcessPackage.eINSTANCE.getParallelFork().isSuperTypeOf(domainElement.eClass())) {
				return ParallelForkEditPart.VISUAL_ID;
			}
			if (ProcessPackage.eINSTANCE.getParallelMerge().isSuperTypeOf(domainElement.eClass())) {
				return ParallelMergeEditPart.VISUAL_ID;
			}
			if (ProcessPackage.eINSTANCE.getStartEvent().isSuperTypeOf(domainElement.eClass())) {
				return StartEventEditPart.VISUAL_ID;
			}
			if (ProcessPackage.eINSTANCE.getEndEvent().isSuperTypeOf(domainElement.eClass())) {
				return EndEventEditPart.VISUAL_ID;
			}
			break;
		case ProcessEditPart.VISUAL_ID:
			if (ProcessPackage.eINSTANCE.getPool().isSuperTypeOf(domainElement.eClass())) {
				return PoolEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = com.odcgroup.process.diagram.part.ProcessVisualIDRegistry.getModelID(containerView);
		if (!ProcessEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (ProcessEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = com.odcgroup.process.diagram.part.ProcessVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProcessEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case PoolEditPart.VISUAL_ID:
			if (PoolNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PoolProcessItemCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case UserTaskEditPart.VISUAL_ID:
			if (UserTaskNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ServiceTaskEditPart.VISUAL_ID:
			if (ServiceTaskNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ComplexGatewayEditPart.VISUAL_ID:
			if (ComplexGatewayNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ExclusiveMergeEditPart.VISUAL_ID:
			if (ExclusiveMergeNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ParallelForkEditPart.VISUAL_ID:
			if (ParallelForkNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ParallelMergeEditPart.VISUAL_ID:
			if (ParallelMergeNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case StartEventEditPart.VISUAL_ID:
			if (StartEventNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case EndEventEditPart.VISUAL_ID:
			if (EndEventNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PoolProcessItemCompartmentEditPart.VISUAL_ID:
			if (UserTaskEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ServiceTaskEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ComplexGatewayEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ExclusiveMergeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ParallelForkEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ParallelMergeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (StartEventEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (EndEventEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ProcessEditPart.VISUAL_ID:
			if (PoolEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case FlowEditPart.VISUAL_ID:
			if (FlowNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (ProcessPackage.eINSTANCE.getFlow().isSuperTypeOf(domainElement.eClass())) {
			return FlowEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(Process element) {
		return true;
	}

}

/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.EndStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.EndStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.InitStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.InitStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.PageflowEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateDescEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateDescEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateEditPart;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class PageflowVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "com.odcgroup.pageflow.editor.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (PageflowEditPart.MODEL_ID.equals(view.getType())) {
				return PageflowEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry.getVisualID(view.getType());
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
				PageflowDiagramEditorPlugin.getInstance().logError(
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
		if (PageflowPackage.eINSTANCE.getPageflow().isSuperTypeOf(domainElement.eClass())
				&& isDiagram((Pageflow) domainElement)) {
			return PageflowEditPart.VISUAL_ID;
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
		String containerModelID = com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry
				.getModelID(containerView);
		if (!PageflowEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (PageflowEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = PageflowEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case PageflowEditPart.VISUAL_ID:
			if (PageflowPackage.eINSTANCE.getInitState().isSuperTypeOf(domainElement.eClass())) {
				return InitStateEditPart.VISUAL_ID;
			}
			if (PageflowPackage.eINSTANCE.getDecisionState().isSuperTypeOf(domainElement.eClass())) {
				return DecisionStateEditPart.VISUAL_ID;
			}
			if (PageflowPackage.eINSTANCE.getEndState().isSuperTypeOf(domainElement.eClass())) {
				return EndStateEditPart.VISUAL_ID;
			}
			if (PageflowPackage.eINSTANCE.getViewState().isSuperTypeOf(domainElement.eClass())) {
				return ViewStateEditPart.VISUAL_ID;
			}
			if (PageflowPackage.eINSTANCE.getSubPageflowState().isSuperTypeOf(domainElement.eClass())) {
				return SubPageflowStateEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry
				.getModelID(containerView);
		if (!PageflowEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (PageflowEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = PageflowEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case InitStateEditPart.VISUAL_ID:
			if (InitStateDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DecisionStateEditPart.VISUAL_ID:
			if (DecisionStateDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case EndStateEditPart.VISUAL_ID:
			if (EndStateDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ViewStateEditPart.VISUAL_ID:
			if (ViewStateDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ViewStateDescEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case SubPageflowStateEditPart.VISUAL_ID:
			if (SubPageflowStateDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (SubPageflowStateDescEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PageflowEditPart.VISUAL_ID:
			if (InitStateEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (DecisionStateEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (EndStateEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ViewStateEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (SubPageflowStateEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TransitionEditPart.VISUAL_ID:
			if (TransitionDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
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
		if (PageflowPackage.eINSTANCE.getTransition().isSuperTypeOf(domainElement.eClass())) {
			return TransitionEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(Pageflow element) {
		return true;
	}

}

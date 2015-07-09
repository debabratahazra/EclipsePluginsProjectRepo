/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.EndStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.InitStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.PageflowEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateEditPart;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.ViewState;

/**
 * @generated
 */
public class PageflowDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (PageflowVisualIDRegistry.getVisualID(view)) {
		case PageflowEditPart.VISUAL_ID:
			return getPageflow_79SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPageflow_79SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Pageflow modelElement = (Pageflow) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getStates().iterator(); it.hasNext();) {
			State childElement = (State) it.next();
			int visualID = PageflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == InitStateEditPart.VISUAL_ID) {
				result.add(new PageflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == DecisionStateEditPart.VISUAL_ID) {
				result.add(new PageflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EndStateEditPart.VISUAL_ID) {
				result.add(new PageflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ViewStateEditPart.VISUAL_ID) {
				result.add(new PageflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == SubPageflowStateEditPart.VISUAL_ID) {
				result.add(new PageflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (PageflowVisualIDRegistry.getVisualID(view)) {
		case PageflowEditPart.VISUAL_ID:
			return getPageflow_79ContainedLinks(view);
		case InitStateEditPart.VISUAL_ID:
			return getInitState_1001ContainedLinks(view);
		case DecisionStateEditPart.VISUAL_ID:
			return getDecisionState_1002ContainedLinks(view);
		case EndStateEditPart.VISUAL_ID:
			return getEndState_1003ContainedLinks(view);
		case ViewStateEditPart.VISUAL_ID:
			return getViewState_1004ContainedLinks(view);
		case SubPageflowStateEditPart.VISUAL_ID:
			return getSubPageflowState_1005ContainedLinks(view);
		case TransitionEditPart.VISUAL_ID:
			return getTransition_3001ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (PageflowVisualIDRegistry.getVisualID(view)) {
		case InitStateEditPart.VISUAL_ID:
			return getInitState_1001IncomingLinks(view);
		case DecisionStateEditPart.VISUAL_ID:
			return getDecisionState_1002IncomingLinks(view);
		case EndStateEditPart.VISUAL_ID:
			return getEndState_1003IncomingLinks(view);
		case ViewStateEditPart.VISUAL_ID:
			return getViewState_1004IncomingLinks(view);
		case SubPageflowStateEditPart.VISUAL_ID:
			return getSubPageflowState_1005IncomingLinks(view);
		case TransitionEditPart.VISUAL_ID:
			return getTransition_3001IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (PageflowVisualIDRegistry.getVisualID(view)) {
		case InitStateEditPart.VISUAL_ID:
			return getInitState_1001OutgoingLinks(view);
		case DecisionStateEditPart.VISUAL_ID:
			return getDecisionState_1002OutgoingLinks(view);
		case EndStateEditPart.VISUAL_ID:
			return getEndState_1003OutgoingLinks(view);
		case ViewStateEditPart.VISUAL_ID:
			return getViewState_1004OutgoingLinks(view);
		case SubPageflowStateEditPart.VISUAL_ID:
			return getSubPageflowState_1005OutgoingLinks(view);
		case TransitionEditPart.VISUAL_ID:
			return getTransition_3001OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPageflow_79ContainedLinks(View view) {
		Pageflow modelElement = (Pageflow) view.getElement();
		List result = new LinkedList();
		result.addAll(getContainedTypeModelFacetLinks_Transition_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getInitState_1001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDecisionState_1002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEndState_1003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getViewState_1004ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getSubPageflowState_1005ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getTransition_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInitState_1001IncomingLinks(View view) {
		InitState modelElement = (InitState) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getDecisionState_1002IncomingLinks(View view) {
		DecisionState modelElement = (DecisionState) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEndState_1003IncomingLinks(View view) {
		EndState modelElement = (EndState) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getViewState_1004IncomingLinks(View view) {
		ViewState modelElement = (ViewState) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getSubPageflowState_1005IncomingLinks(View view) {
		SubPageflowState modelElement = (SubPageflowState) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getTransition_3001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInitState_1001OutgoingLinks(View view) {
		InitState modelElement = (InitState) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getDecisionState_1002OutgoingLinks(View view) {
		DecisionState modelElement = (DecisionState) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEndState_1003OutgoingLinks(View view) {
		EndState modelElement = (EndState) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getViewState_1004OutgoingLinks(View view) {
		ViewState modelElement = (ViewState) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getSubPageflowState_1005OutgoingLinks(View view) {
		SubPageflowState modelElement = (SubPageflowState) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getTransition_3001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_Transition_3001(Pageflow container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getTransitions().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Transition) {
				continue;
			}
			Transition link = (Transition) linkObject;
			if (TransitionEditPart.VISUAL_ID != PageflowVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			State dst = link.getToState();
			State src = link.getFromState();
			result.add(new PageflowLinkDescriptor(src, dst, link, PageflowElementTypes.Transition_3001,
					TransitionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_Transition_3001(State target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() != PageflowPackage.eINSTANCE.getTransition_ToState()
					|| false == setting.getEObject() instanceof Transition) {
				continue;
			}
			Transition link = (Transition) setting.getEObject();
			if (TransitionEditPart.VISUAL_ID != PageflowVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			State src = link.getFromState();
			result.add(new PageflowLinkDescriptor(src, target, link, PageflowElementTypes.Transition_3001,
					TransitionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_Transition_3001(State source) {
		Pageflow container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Pageflow) {
				container = (Pageflow) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getTransitions().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Transition) {
				continue;
			}
			Transition link = (Transition) linkObject;
			if (TransitionEditPart.VISUAL_ID != PageflowVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			State dst = link.getToState();
			State src = link.getFromState();
			if (src != source) {
				continue;
			}
			result.add(new PageflowLinkDescriptor(src, dst, link, PageflowElementTypes.Transition_3001,
					TransitionEditPart.VISUAL_ID));
		}
		return result;
	}

}

/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
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
import com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry;
import com.odcgroup.pageflow.editor.diagram.view.factories.DecisionStateDisplayNameViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.DecisionStateViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.EndStateDisplayNameViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.EndStateViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.InitStateDisplayNameViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.InitStateViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.PageflowViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.SubPageflowStateDescViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.SubPageflowStateDisplayNameViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.SubPageflowStateViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.TransitionDisplayNameViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.TransitionViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.ViewStateDescViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.ViewStateDisplayNameViewFactory;
import com.odcgroup.pageflow.editor.diagram.view.factories.ViewStateViewFactory;

/**
 * @generated
 */
public class PageflowViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	protected Class getDiagramViewClass(IAdaptable semanticAdapter, String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (PageflowEditPart.MODEL_ID.equals(diagramKind)
				&& PageflowVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
			return PageflowViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(IAdaptable semanticAdapter, View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}
		IElementType elementType = getSemanticElementType(semanticAdapter);
		EObject domainElement = getSemanticElement(semanticAdapter);
		int visualID;
		if (semanticHint == null) {
			// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
			// In this situation there should be NO elementType, visualID will be determined
			// by VisualIDRegistry.getNodeVisualID() for domainElement.
			if (elementType != null || domainElement == null) {
				return null;
			}
			visualID = PageflowVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		} else {
			visualID = PageflowVisualIDRegistry.getVisualID(semanticHint);
			if (elementType != null) {
				// Semantic hint is specified together with element type.
				// Both parameters should describe exactly the same diagram element.
				// In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
				// domainElement (if specified) is the same as in element type.
				if (!PageflowElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
					return null; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
				if (!semanticHint.equals(elementTypeHint)) {
					return null; // if semantic hint is specified it should be the same as in element type
				}
				if (domainElement != null
						&& visualID != PageflowVisualIDRegistry.getNodeVisualID(containerView, domainElement)) {
					return null; // visual id for node EClass should match visual id from element type
				}
			} else {
				// Element type is not specified. Domain element should be present (except pure design elements).
				// This method is called with EObjectAdapter as parameter from:
				//   - ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint) 
				//   - generated ViewFactory.decorateView() for parent element
				if (!PageflowEditPart.MODEL_ID.equals(PageflowVisualIDRegistry.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case InitStateEditPart.VISUAL_ID:
				case DecisionStateEditPart.VISUAL_ID:
				case EndStateEditPart.VISUAL_ID:
				case ViewStateEditPart.VISUAL_ID:
				case SubPageflowStateEditPart.VISUAL_ID:
					if (domainElement == null
							|| visualID != PageflowVisualIDRegistry.getNodeVisualID(containerView, domainElement)) {
						return null; // visual id in semantic hint should match visual id for domain element
					}
					break;
				case InitStateDisplayNameEditPart.VISUAL_ID:
					if (InitStateEditPart.VISUAL_ID != PageflowVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case DecisionStateDisplayNameEditPart.VISUAL_ID:
					if (DecisionStateEditPart.VISUAL_ID != PageflowVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case EndStateDisplayNameEditPart.VISUAL_ID:
					if (EndStateEditPart.VISUAL_ID != PageflowVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ViewStateDisplayNameEditPart.VISUAL_ID:
				case ViewStateDescEditPart.VISUAL_ID:
					if (ViewStateEditPart.VISUAL_ID != PageflowVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case SubPageflowStateDisplayNameEditPart.VISUAL_ID:
				case SubPageflowStateDescEditPart.VISUAL_ID:
					if (SubPageflowStateEditPart.VISUAL_ID != PageflowVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case TransitionDisplayNameEditPart.VISUAL_ID:
					if (TransitionEditPart.VISUAL_ID != PageflowVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				default:
					return null;
				}
			}
		}
		return getNodeViewClass(containerView, visualID);
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(View containerView, int visualID) {
		if (containerView == null || !PageflowVisualIDRegistry.canCreateNode(containerView, visualID)) {
			return null;
		}
		switch (visualID) {
		case InitStateEditPart.VISUAL_ID:
			return InitStateViewFactory.class;
		case InitStateDisplayNameEditPart.VISUAL_ID:
			return InitStateDisplayNameViewFactory.class;
		case DecisionStateEditPart.VISUAL_ID:
			return DecisionStateViewFactory.class;
		case DecisionStateDisplayNameEditPart.VISUAL_ID:
			return DecisionStateDisplayNameViewFactory.class;
		case EndStateEditPart.VISUAL_ID:
			return EndStateViewFactory.class;
		case EndStateDisplayNameEditPart.VISUAL_ID:
			return EndStateDisplayNameViewFactory.class;
		case ViewStateEditPart.VISUAL_ID:
			return ViewStateViewFactory.class;
		case ViewStateDisplayNameEditPart.VISUAL_ID:
			return ViewStateDisplayNameViewFactory.class;
		case ViewStateDescEditPart.VISUAL_ID:
			return ViewStateDescViewFactory.class;
		case SubPageflowStateEditPart.VISUAL_ID:
			return SubPageflowStateViewFactory.class;
		case SubPageflowStateDisplayNameEditPart.VISUAL_ID:
			return SubPageflowStateDisplayNameViewFactory.class;
		case SubPageflowStateDescEditPart.VISUAL_ID:
			return SubPageflowStateDescViewFactory.class;
		case TransitionDisplayNameEditPart.VISUAL_ID:
			return TransitionDisplayNameViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(IAdaptable semanticAdapter, View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (!PageflowElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
			return null; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null) {
			return null; // our hint is visual id and must be specified
		}
		if (semanticHint != null && !semanticHint.equals(elementTypeHint)) {
			return null; // if semantic hint is specified it should be the same as in element type
		}
		int visualID = PageflowVisualIDRegistry.getVisualID(elementTypeHint);
		EObject domainElement = getSemanticElement(semanticAdapter);
		if (domainElement != null && visualID != PageflowVisualIDRegistry.getLinkWithClassVisualID(domainElement)) {
			return null; // visual id for link EClass should match visual id from element type
		}
		return getEdgeViewClass(visualID);
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(int visualID) {
		switch (visualID) {
		case TransitionEditPart.VISUAL_ID:
			return TransitionViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	private IElementType getSemanticElementType(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return (IElementType) semanticAdapter.getAdapter(IElementType.class);
	}
}

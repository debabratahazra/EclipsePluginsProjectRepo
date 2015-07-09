/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
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
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;
import com.odcgroup.process.diagram.view.factories.ComplexGatewayNameViewFactory;
import com.odcgroup.process.diagram.view.factories.ComplexGatewayViewFactory;
import com.odcgroup.process.diagram.view.factories.EndEventNameViewFactory;
import com.odcgroup.process.diagram.view.factories.EndEventViewFactory;
import com.odcgroup.process.diagram.view.factories.ExclusiveMergeNameViewFactory;
import com.odcgroup.process.diagram.view.factories.ExclusiveMergeViewFactory;
import com.odcgroup.process.diagram.view.factories.FlowNameViewFactory;
import com.odcgroup.process.diagram.view.factories.FlowViewFactory;
import com.odcgroup.process.diagram.view.factories.ParallelForkNameViewFactory;
import com.odcgroup.process.diagram.view.factories.ParallelForkViewFactory;
import com.odcgroup.process.diagram.view.factories.ParallelMergeNameViewFactory;
import com.odcgroup.process.diagram.view.factories.ParallelMergeViewFactory;
import com.odcgroup.process.diagram.view.factories.PoolNameViewFactory;
import com.odcgroup.process.diagram.view.factories.PoolProcessItemCompartmentViewFactory;
import com.odcgroup.process.diagram.view.factories.PoolViewFactory;
import com.odcgroup.process.diagram.view.factories.ProcessViewFactory;
import com.odcgroup.process.diagram.view.factories.ServiceTaskNameViewFactory;
import com.odcgroup.process.diagram.view.factories.ServiceTaskViewFactory;
import com.odcgroup.process.diagram.view.factories.StartEventNameViewFactory;
import com.odcgroup.process.diagram.view.factories.StartEventViewFactory;
import com.odcgroup.process.diagram.view.factories.UserTaskNameViewFactory;
import com.odcgroup.process.diagram.view.factories.UserTaskViewFactory;

/**
 * @generated
 */
public class ProcessViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	protected Class getDiagramViewClass(IAdaptable semanticAdapter, String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (ProcessEditPart.MODEL_ID.equals(diagramKind)
				&& ProcessVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
			return ProcessViewFactory.class;
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
			visualID = ProcessVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		} else {
			visualID = ProcessVisualIDRegistry.getVisualID(semanticHint);
			if (elementType != null) {
				// Semantic hint is specified together with element type.
				// Both parameters should describe exactly the same diagram element.
				// In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
				// domainElement (if specified) is the same as in element type.
				if (!ProcessElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
					return null; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
				if (!semanticHint.equals(elementTypeHint)) {
					return null; // if semantic hint is specified it should be the same as in element type
				}
				if (domainElement != null
						&& visualID != ProcessVisualIDRegistry.getNodeVisualID(containerView, domainElement)) {
					return null; // visual id for node EClass should match visual id from element type
				}
			} else {
				// Element type is not specified. Domain element should be present (except pure design elements).
				// This method is called with EObjectAdapter as parameter from:
				//   - ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint) 
				//   - generated ViewFactory.decorateView() for parent element
				if (!ProcessEditPart.MODEL_ID.equals(ProcessVisualIDRegistry.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case PoolEditPart.VISUAL_ID:
				case UserTaskEditPart.VISUAL_ID:
				case ServiceTaskEditPart.VISUAL_ID:
				case ComplexGatewayEditPart.VISUAL_ID:
				case ExclusiveMergeEditPart.VISUAL_ID:
				case ParallelForkEditPart.VISUAL_ID:
				case ParallelMergeEditPart.VISUAL_ID:
				case StartEventEditPart.VISUAL_ID:
				case EndEventEditPart.VISUAL_ID:
					if (domainElement == null
							|| visualID != ProcessVisualIDRegistry.getNodeVisualID(containerView, domainElement)) {
						return null; // visual id in semantic hint should match visual id for domain element
					}
					break;
				case PoolNameEditPart.VISUAL_ID:
				case PoolProcessItemCompartmentEditPart.VISUAL_ID:
					if (PoolEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case UserTaskNameEditPart.VISUAL_ID:
					if (UserTaskEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ServiceTaskNameEditPart.VISUAL_ID:
					if (ServiceTaskEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ComplexGatewayNameEditPart.VISUAL_ID:
					if (ComplexGatewayEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ExclusiveMergeNameEditPart.VISUAL_ID:
					if (ExclusiveMergeEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ParallelForkNameEditPart.VISUAL_ID:
					if (ParallelForkEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ParallelMergeNameEditPart.VISUAL_ID:
					if (ParallelMergeEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case StartEventNameEditPart.VISUAL_ID:
					if (StartEventEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case EndEventNameEditPart.VISUAL_ID:
					if (EndEventEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case FlowNameEditPart.VISUAL_ID:
					if (FlowEditPart.VISUAL_ID != ProcessVisualIDRegistry.getVisualID(containerView)
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
		if (containerView == null || !ProcessVisualIDRegistry.canCreateNode(containerView, visualID)) {
			return null;
		}
		switch (visualID) {
		case PoolEditPart.VISUAL_ID:
			return PoolViewFactory.class;
		case PoolNameEditPart.VISUAL_ID:
			return PoolNameViewFactory.class;
		case UserTaskEditPart.VISUAL_ID:
			return UserTaskViewFactory.class;
		case UserTaskNameEditPart.VISUAL_ID:
			return UserTaskNameViewFactory.class;
		case ServiceTaskEditPart.VISUAL_ID:
			return ServiceTaskViewFactory.class;
		case ServiceTaskNameEditPart.VISUAL_ID:
			return ServiceTaskNameViewFactory.class;
		case ComplexGatewayEditPart.VISUAL_ID:
			return ComplexGatewayViewFactory.class;
		case ComplexGatewayNameEditPart.VISUAL_ID:
			return ComplexGatewayNameViewFactory.class;
		case ExclusiveMergeEditPart.VISUAL_ID:
			return ExclusiveMergeViewFactory.class;
		case ExclusiveMergeNameEditPart.VISUAL_ID:
			return ExclusiveMergeNameViewFactory.class;
		case ParallelForkEditPart.VISUAL_ID:
			return ParallelForkViewFactory.class;
		case ParallelForkNameEditPart.VISUAL_ID:
			return ParallelForkNameViewFactory.class;
		case ParallelMergeEditPart.VISUAL_ID:
			return ParallelMergeViewFactory.class;
		case ParallelMergeNameEditPart.VISUAL_ID:
			return ParallelMergeNameViewFactory.class;
		case StartEventEditPart.VISUAL_ID:
			return StartEventViewFactory.class;
		case StartEventNameEditPart.VISUAL_ID:
			return StartEventNameViewFactory.class;
		case EndEventEditPart.VISUAL_ID:
			return EndEventViewFactory.class;
		case EndEventNameEditPart.VISUAL_ID:
			return EndEventNameViewFactory.class;
		case PoolProcessItemCompartmentEditPart.VISUAL_ID:
			return PoolProcessItemCompartmentViewFactory.class;
		case FlowNameEditPart.VISUAL_ID:
			return FlowNameViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(IAdaptable semanticAdapter, View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (!ProcessElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
			return null; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null) {
			return null; // our hint is visual id and must be specified
		}
		if (semanticHint != null && !semanticHint.equals(elementTypeHint)) {
			return null; // if semantic hint is specified it should be the same as in element type
		}
		int visualID = ProcessVisualIDRegistry.getVisualID(elementTypeHint);
		EObject domainElement = getSemanticElement(semanticAdapter);
		if (domainElement != null && visualID != ProcessVisualIDRegistry.getLinkWithClassVisualID(domainElement)) {
			return null; // visual id for link EClass should match visual id from element type
		}
		return getEdgeViewClass(visualID);
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(int visualID) {
		switch (visualID) {
		case FlowEditPart.VISUAL_ID:
			return FlowViewFactory.class;
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

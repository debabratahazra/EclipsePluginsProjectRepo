/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.part;

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

import com.odcgroup.process.diagram.edit.parts.ComplexGatewayEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.FlowEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolProcessItemCompartmentEditPart;
import com.odcgroup.process.diagram.edit.parts.ProcessEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskEditPart;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.model.ComplexGateway;
import com.odcgroup.process.model.EndEvent;
import com.odcgroup.process.model.ExclusiveMerge;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.Gateway;
import com.odcgroup.process.model.ParallelFork;
import com.odcgroup.process.model.ParallelMerge;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessItem;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.ServiceTask;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.Task;
import com.odcgroup.process.model.UserTask;

/**
 * @generated
 */
public class ProcessDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (ProcessVisualIDRegistry.getVisualID(view)) {
		case PoolProcessItemCompartmentEditPart.VISUAL_ID:
			return getPoolProcessItemCompartment_5001SemanticChildren(view);
		case ProcessEditPart.VISUAL_ID:
			return getProcess_79SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPoolProcessItemCompartment_5001SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Pool modelElement = (Pool) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTasks().iterator(); it.hasNext();) {
			Task childElement = (Task) it.next();
			int visualID = ProcessVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == UserTaskEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ServiceTaskEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getGateways().iterator(); it.hasNext();) {
			Gateway childElement = (Gateway) it.next();
			int visualID = ProcessVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == ComplexGatewayEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ExclusiveMergeEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ParallelForkEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ParallelMergeEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		{
			StartEvent childElement = modelElement.getStart();
			int visualID = ProcessVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == StartEventEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
			}
		}
		for (Iterator it = modelElement.getEnd().iterator(); it.hasNext();) {
			EndEvent childElement = (EndEvent) it.next();
			int visualID = ProcessVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == EndEventEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getProcess_79SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Process modelElement = (Process) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getPools().iterator(); it.hasNext();) {
			Pool childElement = (Pool) it.next();
			int visualID = ProcessVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == PoolEditPart.VISUAL_ID) {
				result.add(new ProcessNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (ProcessVisualIDRegistry.getVisualID(view)) {
		case ProcessEditPart.VISUAL_ID:
			return getProcess_79ContainedLinks(view);
		case PoolEditPart.VISUAL_ID:
			return getPool_1001ContainedLinks(view);
		case UserTaskEditPart.VISUAL_ID:
			return getUserTask_2001ContainedLinks(view);
		case ServiceTaskEditPart.VISUAL_ID:
			return getServiceTask_2002ContainedLinks(view);
		case ComplexGatewayEditPart.VISUAL_ID:
			return getComplexGateway_2003ContainedLinks(view);
		case ExclusiveMergeEditPart.VISUAL_ID:
			return getExclusiveMerge_2004ContainedLinks(view);
		case ParallelForkEditPart.VISUAL_ID:
			return getParallelFork_2005ContainedLinks(view);
		case ParallelMergeEditPart.VISUAL_ID:
			return getParallelMerge_2006ContainedLinks(view);
		case StartEventEditPart.VISUAL_ID:
			return getStartEvent_2007ContainedLinks(view);
		case EndEventEditPart.VISUAL_ID:
			return getEndEvent_2008ContainedLinks(view);
		case FlowEditPart.VISUAL_ID:
			return getFlow_3001ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (ProcessVisualIDRegistry.getVisualID(view)) {
		case PoolEditPart.VISUAL_ID:
			return getPool_1001IncomingLinks(view);
		case UserTaskEditPart.VISUAL_ID:
			return getUserTask_2001IncomingLinks(view);
		case ServiceTaskEditPart.VISUAL_ID:
			return getServiceTask_2002IncomingLinks(view);
		case ComplexGatewayEditPart.VISUAL_ID:
			return getComplexGateway_2003IncomingLinks(view);
		case ExclusiveMergeEditPart.VISUAL_ID:
			return getExclusiveMerge_2004IncomingLinks(view);
		case ParallelForkEditPart.VISUAL_ID:
			return getParallelFork_2005IncomingLinks(view);
		case ParallelMergeEditPart.VISUAL_ID:
			return getParallelMerge_2006IncomingLinks(view);
		case StartEventEditPart.VISUAL_ID:
			return getStartEvent_2007IncomingLinks(view);
		case EndEventEditPart.VISUAL_ID:
			return getEndEvent_2008IncomingLinks(view);
		case FlowEditPart.VISUAL_ID:
			return getFlow_3001IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (ProcessVisualIDRegistry.getVisualID(view)) {
		case PoolEditPart.VISUAL_ID:
			return getPool_1001OutgoingLinks(view);
		case UserTaskEditPart.VISUAL_ID:
			return getUserTask_2001OutgoingLinks(view);
		case ServiceTaskEditPart.VISUAL_ID:
			return getServiceTask_2002OutgoingLinks(view);
		case ComplexGatewayEditPart.VISUAL_ID:
			return getComplexGateway_2003OutgoingLinks(view);
		case ExclusiveMergeEditPart.VISUAL_ID:
			return getExclusiveMerge_2004OutgoingLinks(view);
		case ParallelForkEditPart.VISUAL_ID:
			return getParallelFork_2005OutgoingLinks(view);
		case ParallelMergeEditPart.VISUAL_ID:
			return getParallelMerge_2006OutgoingLinks(view);
		case StartEventEditPart.VISUAL_ID:
			return getStartEvent_2007OutgoingLinks(view);
		case EndEventEditPart.VISUAL_ID:
			return getEndEvent_2008OutgoingLinks(view);
		case FlowEditPart.VISUAL_ID:
			return getFlow_3001OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProcess_79ContainedLinks(View view) {
		Process modelElement = (Process) view.getElement();
		List result = new LinkedList();
		result.addAll(getContainedTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPool_1001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getUserTask_2001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getServiceTask_2002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getComplexGateway_2003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getExclusiveMerge_2004ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getParallelFork_2005ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getParallelMerge_2006ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getStartEvent_2007ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEndEvent_2008ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getFlow_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPool_1001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getUserTask_2001IncomingLinks(View view) {
		UserTask modelElement = (UserTask) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getServiceTask_2002IncomingLinks(View view) {
		ServiceTask modelElement = (ServiceTask) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getComplexGateway_2003IncomingLinks(View view) {
		ComplexGateway modelElement = (ComplexGateway) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getExclusiveMerge_2004IncomingLinks(View view) {
		ExclusiveMerge modelElement = (ExclusiveMerge) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getParallelFork_2005IncomingLinks(View view) {
		ParallelFork modelElement = (ParallelFork) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getParallelMerge_2006IncomingLinks(View view) {
		ParallelMerge modelElement = (ParallelMerge) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getStartEvent_2007IncomingLinks(View view) {
		StartEvent modelElement = (StartEvent) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEndEvent_2008IncomingLinks(View view) {
		EndEvent modelElement = (EndEvent) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Flow_3001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFlow_3001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPool_1001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getUserTask_2001OutgoingLinks(View view) {
		UserTask modelElement = (UserTask) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getServiceTask_2002OutgoingLinks(View view) {
		ServiceTask modelElement = (ServiceTask) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getComplexGateway_2003OutgoingLinks(View view) {
		ComplexGateway modelElement = (ComplexGateway) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getExclusiveMerge_2004OutgoingLinks(View view) {
		ExclusiveMerge modelElement = (ExclusiveMerge) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getParallelFork_2005OutgoingLinks(View view) {
		ParallelFork modelElement = (ParallelFork) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getParallelMerge_2006OutgoingLinks(View view) {
		ParallelMerge modelElement = (ParallelMerge) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getStartEvent_2007OutgoingLinks(View view) {
		StartEvent modelElement = (StartEvent) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEndEvent_2008OutgoingLinks(View view) {
		EndEvent modelElement = (EndEvent) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Flow_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFlow_3001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_Flow_3001(Process container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getTransitions().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Flow) {
				continue;
			}
			Flow link = (Flow) linkObject;
			if (FlowEditPart.VISUAL_ID != ProcessVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			ProcessItem dst = link.getTarget();
			ProcessItem src = link.getSource();
			result
					.add(new ProcessLinkDescriptor(src, dst, link, ProcessElementTypes.Flow_3001,
							FlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_Flow_3001(ProcessItem target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() != ProcessPackage.eINSTANCE.getFlow_Target()
					|| false == setting.getEObject() instanceof Flow) {
				continue;
			}
			Flow link = (Flow) setting.getEObject();
			if (FlowEditPart.VISUAL_ID != ProcessVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			ProcessItem src = link.getSource();
			result.add(new ProcessLinkDescriptor(src, target, link, ProcessElementTypes.Flow_3001,
					FlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_Flow_3001(ProcessItem source) {
		Process container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Process) {
				container = (Process) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getTransitions().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Flow) {
				continue;
			}
			Flow link = (Flow) linkObject;
			if (FlowEditPart.VISUAL_ID != ProcessVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			ProcessItem dst = link.getTarget();
			ProcessItem src = link.getSource();
			if (src != source) {
				continue;
			}
			result
					.add(new ProcessLinkDescriptor(src, dst, link, ProcessElementTypes.Flow_3001,
							FlowEditPart.VISUAL_ID));
		}
		return result;
	}

}

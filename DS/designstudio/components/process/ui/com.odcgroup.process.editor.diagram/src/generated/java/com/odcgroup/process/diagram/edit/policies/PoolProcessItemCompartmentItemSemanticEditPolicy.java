/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import com.odcgroup.process.diagram.edit.commands.ComplexGatewayCreateCommand;
import com.odcgroup.process.diagram.edit.commands.EndEventCreateCommand;
import com.odcgroup.process.diagram.edit.commands.ExclusiveMergeCreateCommand;
import com.odcgroup.process.diagram.edit.commands.ParallelForkCreateCommand;
import com.odcgroup.process.diagram.edit.commands.ParallelMergeCreateCommand;
import com.odcgroup.process.diagram.edit.commands.ServiceTaskCreateCommand;
import com.odcgroup.process.diagram.edit.commands.StartEventCreateCommand;
import com.odcgroup.process.diagram.edit.commands.UserTaskCreateCommand;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated
 */
public class PoolProcessItemCompartmentItemSemanticEditPolicy extends ProcessBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (ProcessElementTypes.UserTask_2001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_Tasks());
			}
			return getGEFWrapper(new UserTaskCreateCommand(req));
		}
		if (ProcessElementTypes.ServiceTask_2002 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_Tasks());
			}
			return getGEFWrapper(new ServiceTaskCreateCommand(req));
		}
		if (ProcessElementTypes.ComplexGateway_2003 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_Gateways());
			}
			return getGEFWrapper(new ComplexGatewayCreateCommand(req));
		}
		if (ProcessElementTypes.ExclusiveMerge_2004 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_Gateways());
			}
			return getGEFWrapper(new ExclusiveMergeCreateCommand(req));
		}
		if (ProcessElementTypes.ParallelFork_2005 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_Gateways());
			}
			return getGEFWrapper(new ParallelForkCreateCommand(req));
		}
		if (ProcessElementTypes.ParallelMerge_2006 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_Gateways());
			}
			return getGEFWrapper(new ParallelMergeCreateCommand(req));
		}
		if (ProcessElementTypes.StartEvent_2007 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_Start());
			}
			return getGEFWrapper(new StartEventCreateCommand(req));
		}
		if (ProcessElementTypes.EndEvent_2008 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(ProcessPackage.eINSTANCE.getPool_End());
			}
			return getGEFWrapper(new EndEventCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}

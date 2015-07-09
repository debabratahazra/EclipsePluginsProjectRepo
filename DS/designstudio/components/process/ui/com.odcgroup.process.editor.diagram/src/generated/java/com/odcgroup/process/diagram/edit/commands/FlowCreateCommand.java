/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import com.odcgroup.process.diagram.edit.policies.ProcessBaseItemSemanticEditPolicy;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessItem;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated
 */
public class FlowCreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	/**
	 * @generated
	 */
	private Process container;

	/**
	 * @generated
	 */
	public FlowCreateCommand(CreateRelationshipRequest request, EObject source, EObject target) {
		super(request);
		this.source = source;
		this.target = target;
		if (request.getContainmentFeature() == null) {
			setContainmentFeature(ProcessPackage.eINSTANCE.getProcess_Transitions());
		}

		// Find container element for the new link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null; element = element.eContainer()) {
			if (element instanceof Process) {
				container = (Process) element;
				super.setElementToEdit(container);
				break;
			}
		}
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && false == source instanceof ProcessItem) {
			return false;
		}
		if (target != null && false == target instanceof ProcessItem) {
			return false;
		}
		if (getSource() == null) {
			return true; // link creation is in progress; source is not defined yet
		}
		// target may be null here but it's possible to check constraint
		if (getContainer() == null) {
			return false;
		}
		return ProcessBaseItemSemanticEditPolicy.LinkConstraints.canCreateFlow_3001(getContainer(), getSource(),
				getTarget());
	}

	/**
	 * @generated
	 */
	protected EObject doDefaultElementCreation() {
		Flow newElement = ProcessFactory.eINSTANCE.createFlow();
		getContainer().getTransitions().add(newElement);
		newElement.setSource(getSource());
		newElement.setTarget(getTarget());
		return newElement;
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return ProcessPackage.eINSTANCE.getProcess();
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in create link command"); //$NON-NLS-1$
		}
		return super.doExecuteWithResult(monitor, info);
	}

	/**
	 * @generated
	 */
	protected ConfigureRequest createConfigureRequest() {
		ConfigureRequest request = super.createConfigureRequest();
		request.setParameter(CreateRelationshipRequest.SOURCE, getSource());
		request.setParameter(CreateRelationshipRequest.TARGET, getTarget());
		return request;
	}

	/**
	 * @generated
	 */
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected ProcessItem getSource() {
		return (ProcessItem) source;
	}

	/**
	 * @generated
	 */
	protected ProcessItem getTarget() {
		return (ProcessItem) target;
	}

	/**
	 * @generated
	 */
	public Process getContainer() {
		return container;
	}
}

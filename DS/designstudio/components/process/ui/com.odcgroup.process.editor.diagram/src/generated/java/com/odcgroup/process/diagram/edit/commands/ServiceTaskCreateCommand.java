/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.process.diagram.custom.commands.CreateNamedElementCommand;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated NOT
 */
public class ServiceTaskCreateCommand extends CreateNamedElementCommand {

	/**
	 * @generated
	 */
	public ServiceTaskCreateCommand(CreateElementRequest req) {
		super(req);
	}

	/**
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest()).getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return ProcessPackage.eINSTANCE.getPool();
	}
}

/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.editor.diagram.custom.commands.PageflowCreateElementCommand;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @generated NOT
 */
public class InitStateCreateCommand extends PageflowCreateElementCommand {

	/**
	 * @generated
	 */
	public InitStateCreateCommand(CreateElementRequest req) {
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
		return PageflowPackage.eINSTANCE.getPageflow();
	}
}

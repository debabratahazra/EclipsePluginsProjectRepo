/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import com.odcgroup.pageflow.editor.diagram.edit.commands.DecisionStateCreateCommand;
import com.odcgroup.pageflow.editor.diagram.edit.commands.EndStateCreateCommand;
import com.odcgroup.pageflow.editor.diagram.edit.commands.InitStateCreateCommand;
import com.odcgroup.pageflow.editor.diagram.edit.commands.SubPageflowStateCreateCommand;
import com.odcgroup.pageflow.editor.diagram.edit.commands.ViewStateCreateCommand;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @generated
 */
public class PageflowItemSemanticEditPolicy extends PageflowBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (PageflowElementTypes.InitState_1001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(PageflowPackage.eINSTANCE.getPageflow_States());
			}
			return getGEFWrapper(new InitStateCreateCommand(req));
		}
		if (PageflowElementTypes.DecisionState_1002 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(PageflowPackage.eINSTANCE.getPageflow_States());
			}
			return getGEFWrapper(new DecisionStateCreateCommand(req));
		}
		if (PageflowElementTypes.EndState_1003 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(PageflowPackage.eINSTANCE.getPageflow_States());
			}
			return getGEFWrapper(new EndStateCreateCommand(req));
		}
		if (PageflowElementTypes.ViewState_1004 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(PageflowPackage.eINSTANCE.getPageflow_States());
			}
			return getGEFWrapper(new ViewStateCreateCommand(req));
		}
		if (PageflowElementTypes.SubPageflowState_1005 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(PageflowPackage.eINSTANCE.getPageflow_States());
			}
			return getGEFWrapper(new SubPageflowStateCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(TransactionalEditingDomain editingDomain, DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req.getElementsToBeDuplicated(), req.getAllDuplicatedElementsMap());
		}

	}

}

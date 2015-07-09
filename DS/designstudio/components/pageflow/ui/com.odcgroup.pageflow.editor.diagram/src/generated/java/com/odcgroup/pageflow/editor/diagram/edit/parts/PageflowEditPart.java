/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.editor.diagram.custom.policies.DescLabelDisplayEditPolicy;
import com.odcgroup.pageflow.editor.diagram.custom.policies.PageflowActionLabelsEditPolicy;
import com.odcgroup.pageflow.editor.diagram.edit.policies.PageflowCanonicalEditPolicy;
import com.odcgroup.pageflow.editor.diagram.edit.policies.PageflowItemSemanticEditPolicy;

/**
 * @generated
 */
public class PageflowEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public final static String MODEL_ID = "Pageflow"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 79;

	/**
	 * @generated
	 */
	public PageflowEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new PageflowItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new PageflowCanonicalEditPolicy());
		installEditPolicy(EditPolicyRoles.REFRESH_CONNECTIONS_ROLE, new PageflowActionLabelsEditPolicy());
		installEditPolicy(EditPolicyRoles.SHOW_ELEMENTS_ROLE, new DescLabelDisplayEditPolicy());
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
	}

}

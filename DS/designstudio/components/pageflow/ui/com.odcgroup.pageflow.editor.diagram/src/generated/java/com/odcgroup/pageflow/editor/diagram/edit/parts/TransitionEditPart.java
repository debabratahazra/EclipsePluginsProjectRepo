/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.editor.diagram.custom.policies.PageflowConnectionSelectionEditPolicy;
import com.odcgroup.pageflow.editor.diagram.edit.policies.TransitionItemSemanticEditPolicy;

/**
 * @generated
 */
public class TransitionEditPart extends ConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	public TransitionEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new TransitionItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new PageflowConnectionSelectionEditPolicy());
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof TransitionDisplayNameEditPart) {
			((TransitionDisplayNameEditPart) childEditPart).setLabel(getPrimaryShape()
					.getFigureTransitionDisplayNameFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */

	protected Connection createConnectionFigure() {
		return new TransitionFigure();
	}

	/**
	 * @generated
	 */
	public TransitionFigure getPrimaryShape() {
		return (TransitionFigure) getFigure();
	}

	/**
	 * @generated
	 */
	public class TransitionFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		private WrapLabel fFigureTransitionDisplayNameFigure;

		/**
		 * @generated NOT
		 */
		public TransitionFigure() {
			this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black);
			setTargetDecoration(createTargetDecoration());
			ConnectionEndpointLocator targetEndpointLocator = new ConnectionEndpointLocator(this, true);
			targetEndpointLocator.setUDistance(0);
			targetEndpointLocator.setVDistance(20);
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureTransitionDisplayNameFigure = new WrapLabel();
			fFigureTransitionDisplayNameFigure.setText("<...>");

			this.add(fFigureTransitionDisplayNameFigure);

		}

		/**
		 * @generated NOT
		 */
		private RotatableDecoration createTargetDecoration() {
			org.eclipse.draw2d.PolylineDecoration df = new org.eclipse.draw2d.PolylineDecoration();
			// dispatchNext?
			org.eclipse.draw2d.geometry.PointList pl = new org.eclipse.draw2d.geometry.PointList();
			pl.addPoint(-2, 2);
			pl.addPoint(0, 0);
			pl.addPoint(-2, -2);
			df.setTemplate(pl);
			df.setScale(7, 3);
			return df;
		}

		/**
		 * @generated
		 */
		public WrapLabel getFigureTransitionDisplayNameFigure() {
			return fFigureTransitionDisplayNameFigure;
		}

	}

}

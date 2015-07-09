/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.process.diagram.custom.policies.ProcessItemSelectionEditPolicy;
import com.odcgroup.process.diagram.edit.policies.FlowItemSemanticEditPolicy;

/**
 * @generated
 */
public class FlowEditPart extends ConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	public FlowEditPart(View view) {
		super(view);
	}

	/**
	 * added connection selection feedback editpolicy
	 * 
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new FlowItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ProcessItemSelectionEditPolicy());
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof FlowNameEditPart) {
			((FlowNameEditPart) childEditPart).setLabel(getPrimaryShape().getFigureFlowNameFigure());
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
		return new FlowFigure();
	}

	/**
	 * @generated
	 */
	public FlowFigure getPrimaryShape() {
		return (FlowFigure) getFigure();
	}

	/**
	 * @generated
	 */
	public class FlowFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		private WrapLabel fFigureFlowNameFigure;

		/**
		 * @generated
		 */
		public FlowFigure() {

			createContents();
			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureFlowNameFigure = new WrapLabel();
			fFigureFlowNameFigure.setText("");

			this.add(fFigureFlowNameFigure);

		}

		/**
		 * @generated
		 */
		private RotatableDecoration createTargetDecoration() {
			PolygonDecoration df = new PolygonDecoration();
			PointList pl = new PointList();
			pl.addPoint(getMapMode().DPtoLP(-2), getMapMode().DPtoLP(2));
			pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
			pl.addPoint(getMapMode().DPtoLP(-2), getMapMode().DPtoLP(-2));
			df.setTemplate(pl);
			df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
			return df;
		}

		/**
		 * @generated
		 */
		public WrapLabel getFigureFlowNameFigure() {
			return fFigureFlowNameFigure;
		}

	}

}

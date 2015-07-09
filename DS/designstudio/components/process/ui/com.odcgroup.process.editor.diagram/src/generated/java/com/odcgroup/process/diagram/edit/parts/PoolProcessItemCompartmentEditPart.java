/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.process.diagram.custom.figures.FixedOneLineBorder;
import com.odcgroup.process.diagram.custom.policies.PoolCompartmentSelectionEditPolicy;
import com.odcgroup.process.diagram.edit.policies.PoolProcessItemCompartmentCanonicalEditPolicy;
import com.odcgroup.process.diagram.edit.policies.PoolProcessItemCompartmentItemSemanticEditPolicy;
import com.odcgroup.process.diagram.part.Messages;

/**
 * @generated
 */
public class PoolProcessItemCompartmentEditPart extends ShapeCompartmentEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 5001;

	/**
	 * @generated
	 */
	public PoolProcessItemCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	public String getCompartmentName() {
		return Messages.PoolProcessItemCompartmentEditPart_title;
	}

	/**
	 * @generated NOT
	 */
	public IFigure createFigureGenerated() {
		ResizableCompartmentFigure result = (ResizableCompartmentFigure) super.createFigure();
		result.setForegroundColor(ColorConstants.black);
		result.getScrollPane().getContents().setBorder(new MarginBorder(0, 0, 0, 0));
		result.setTitleVisibility(false);

		result.getScrollPane().setEnabled(false);
		result.getScrollPane().setScrollBarVisibility(ScrollPane.NEVER);
		result.getScrollPane().setVerticalScrollBarVisibility(ScrollPane.NEVER);
		result.getScrollPane().getViewport().setContentsTracksHeight(true);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart#createFigure()
	 */
	public IFigure createFigure() {
		ResizableCompartmentFigure result = (ResizableCompartmentFigure) this.createFigureGenerated();
		result.setOpaque(true);
		FixedOneLineBorder border = new FixedOneLineBorder(ColorConstants.black, 1, PositionConstants.LEFT);
		result.setBorder(border);
		return result;
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new PoolProcessItemCompartmentItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new CreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new PoolProcessItemCompartmentCanonicalEditPolicy());
		// selection feedback
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new PoolCompartmentSelectionEditPolicy());
	}

	/**
	 * @generated
	 */
	protected void setRatio(Double ratio) {
		if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
			super.setRatio(ratio);
		}
	}

}

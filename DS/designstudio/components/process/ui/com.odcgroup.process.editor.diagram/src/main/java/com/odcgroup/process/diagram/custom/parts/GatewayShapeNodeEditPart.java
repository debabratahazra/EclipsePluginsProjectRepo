package com.odcgroup.process.diagram.custom.parts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.notation.View;

public abstract class GatewayShapeNodeEditPart extends ProcessItemAbstractBorderedShapeEditPart {

	/**
	 * @param view
	 */
	public GatewayShapeNodeEditPart(View view) {
		super(view);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connectionEditPart) {	
		return new GatewayConnectionAnchor(getFigure());

	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connectionEditPart) {
		return new GatewayConnectionAnchor(getFigure());
	}	

}

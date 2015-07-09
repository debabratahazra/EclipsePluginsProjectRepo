package com.odcgroup.process.diagram.custom.policies;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;

/**
 * @author pkk
 *
 */
public class PoolCompartmentSelectionEditPolicy extends SelectionEditPolicy {

	@Override
	protected void hideSelection() {		
		if(getHost() instanceof ShapeCompartmentEditPart){
			ShapeCompartmentEditPart sep = (ShapeCompartmentEditPart)getHost();
			sep.getContentPane().setBorder(null);
		}
	}

	@Override
	protected void showSelection() {		
		if(getHost() instanceof ShapeCompartmentEditPart){
			ShapeCompartmentEditPart sep = (ShapeCompartmentEditPart)getHost();
			sep.getContentPane().setBorder(new LineBorder(ColorConstants.red,2));
		}
	}

}

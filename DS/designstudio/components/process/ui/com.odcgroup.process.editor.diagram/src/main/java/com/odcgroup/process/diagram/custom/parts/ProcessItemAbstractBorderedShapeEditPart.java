package com.odcgroup.process.diagram.custom.parts;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;

import com.odcgroup.process.diagram.custom.policies.ProcessItemSelectionEditPolicy;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.ProcessItem;

/**
 * @author pkk
 *
 */
public abstract class ProcessItemAbstractBorderedShapeEditPart extends AbstractBorderedShapeEditPart {

	/**
	 * @param view
	 */
	public ProcessItemAbstractBorderedShapeEditPart(View view) {
		super(view);
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart#createDefaultEditPolicies()
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ProcessItemSelectionEditPolicy());
	}		

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart#refreshBounds()
	 */
	protected void refreshBounds(){	
		super.refreshBounds();
		
		int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();
		Dimension size = new Dimension(width, height);
		int x = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
		int y = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
		List<?> siblings = getParent().getChildren();
		int index = siblings.indexOf(this);
		index = index+1;
		boolean linked = false;
		if (x == (index * 10)){	
			for (Object object : siblings) {
				ShapeNodeEditPart ep = (ShapeNodeEditPart)object;
				NodeImpl node = (NodeImpl)ep.getModel();
				ProcessItem processItem = (ProcessItem)node.getElement();
				List<?> transitions = ((com.odcgroup.process.model.Process)processItem.eContainer().eContainer()).getTransitions();
				for (Object flow : transitions) {
					Flow transition = (Flow)flow;
					if (transition.getTarget().equals(((NodeImpl)this.getModel()).getElement())){
						x = ((Integer) ep.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue()+siblings.indexOf(ep) * 125+30;
						y = y/index+125;
						linked = true;
					} 
				}			
			}
			if (!linked){			
				x = x+ (index * 125);
				if (index != 0)
					y = y/index;
			}
		}
		if (y < 15)
			y = 15;
		Point loc = new Point(x, y);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,	getFigure(), new Rectangle(loc, size));	
		
		
		if (getParent().getParent() instanceof PoolShapeNodeEditPart){
			PoolShapeNodeEditPart pep = (PoolShapeNodeEditPart)getParent().getParent();
			pep.refresh();	
		}
	}	
}

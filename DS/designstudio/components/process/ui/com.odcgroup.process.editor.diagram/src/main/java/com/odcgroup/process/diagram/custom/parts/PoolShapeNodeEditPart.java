package com.odcgroup.process.diagram.custom.parts;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

public abstract class PoolShapeNodeEditPart extends ShapeNodeEditPart {

	/**
	 * @param view
	 */
	public PoolShapeNodeEditPart(View view) {
		super(view);
	}
	
	/**
	 * @return
	 */
	protected int getSuggestedPoolWidth() {
		int widthInPx = super.getViewer().getControl().getBounds().width - 30;
		if (widthInPx < 900) {
			widthInPx = 900;
		}
		int res = getMapMode().DPtoLP(widthInPx);
		if (res < 900)
			res = 900;
		int written = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		
		List<?> siblings = getParent().getChildren();
		
		Iterator<?> iter = siblings.iterator();
		int width = 0;
		while(iter.hasNext()) {		
			Object ep = iter.next();
			if(ep instanceof GraphicalEditPart){
				GraphicalEditPart editPart = (GraphicalEditPart)ep;
				if (!editPart.equals(this)) {
					width = ((Integer) editPart.getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
					if (width > written){
						written = width;
					}
				}
			}			
		}

		if (written > res)
			return written;
		return res;
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart#refreshBounds()
	 */
	protected void refreshBounds() {
		List siblings = getParent().getChildren();
		siblings = getSortedPoolsByY(siblings);
		int width = getSuggestedPoolWidth();//((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		int x = ((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
		int lastY = 0;
		int lastHeight = 0;
		for (int ii =0;ii<siblings.size();ii++){
			Object obj = siblings.get(ii);	
			int height = 0;
			GraphicalEditPart oep = (GraphicalEditPart) obj;
			height = ((Integer) oep.getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();
			int y = ((Integer) oep.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
			if (height == -1 || height < 250){
				height = 250;
			}			
			if(y < 10)
				y = 10;
			int index = siblings.indexOf(oep);		
			if (index != 0) {
				Object precEp = siblings.get(ii-1);
				int siblingY = 0;
				int siblingHeight = 0;
				if (precEp instanceof GraphicalEditPart){
					GraphicalEditPart part = (GraphicalEditPart) precEp;
					siblingY = ((Integer) part.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();				
					siblingHeight = ((Integer) part.getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();
					if(siblingHeight == -1 || siblingHeight < 250)
						siblingHeight = 250;					 			
					if (siblingY == 0)
						siblingY = (ii-1)* siblingHeight;
					if (siblingY < lastY)
						siblingY = lastY;
					if (lastHeight > siblingHeight)
						siblingHeight = lastHeight;					
					if (y == 0 || y < siblingY+siblingHeight){
						y = siblingY+siblingHeight;
					}
				}
				y = (y+10);
				lastY = y;
			}
			List<GraphicalEditPart> children = oep.getChildren();
			if (children.size() > 0) {
				children = children.get(1).getChildren();
			} 
			int writtenHeight = 0;
			int writtenWidth = 0;
			for(int i=0;i<children.size();i++){
				GraphicalEditPart ep = (GraphicalEditPart)children.get(i);
				int childY = ((Integer)ep.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
				int childX = ((Integer)ep.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
				int ch = (ep.getFigure().getBounds().height == 0)?115:ep.getFigure().getBounds().height+15;
				int cw = (ep.getFigure().getBounds().width == 0)?115:ep.getFigure().getBounds().width+15;
				if ((childY+ch)> height){
					if ((childY+ch)> writtenHeight)
						writtenHeight = childY+ch;					
				}
				if ((childX+cw)>width) {
					if ((childX+cw)>writtenWidth)
						writtenWidth = childX+cw+100;
				}
			}
			if (writtenHeight > height){
				height = writtenHeight;
			}
			if (writtenWidth > width){
				width = writtenWidth;
			}
			lastHeight = height;
			Dimension size = new Dimension(width, height);
			Point loc = new Point(x, y);
			((GraphicalEditPart) oep.getParent()).setLayoutConstraint(oep, oep.getFigure(), new Rectangle(loc, size));			
		}
	}
	
	/**
	 * @param siblings
	 * @return
	 */
	public List getSortedPoolsByY(List siblings){
		Collections.sort(siblings, new Comparator<GraphicalEditPart>() {
			public int compare(GraphicalEditPart arg0, GraphicalEditPart arg1) {
				int y1 = ((Integer)arg0.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
				int y2 = ((Integer)arg1.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
				return y1 > y2 ? 1 : -1;
			}

		});
		return siblings;
	}


}

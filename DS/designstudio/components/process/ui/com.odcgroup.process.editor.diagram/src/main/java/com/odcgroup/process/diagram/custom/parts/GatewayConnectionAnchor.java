package com.odcgroup.process.diagram.custom.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.gef.ui.figures.SlidableAnchor;

public class GatewayConnectionAnchor extends SlidableAnchor {

	
	/**
	 * @param f
	 */
	public GatewayConnectionAnchor(IFigure f){
		super(f);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor#getPolygonPoints()
	 */
	protected PointList getPolygonPoints(){
		Rectangle rBox = getBox();
		PointList ptList = new PointList();
		ptList.addPoint(new Point(rBox.x+(rBox.width/2), rBox.y));
		ptList.addPoint(new Point(rBox.x+rBox.width, rBox.y+(rBox.height/2)));
		ptList.addPoint(new Point(rBox.x+(rBox.width/2), rBox.y+rBox.height));
		ptList.addPoint(new Point(rBox.x, rBox.y+(rBox.height/2)));
		ptList.addPoint(new Point(rBox.x+(rBox.width/2), rBox.y));
		return ptList;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
	 */
	public Point getLocation(Point reference) {
		Point ref = super.getLocation(reference);
		return PointListUtilities.pickClosestPoint(getPolygonPoints(), ref);
	}
	

}

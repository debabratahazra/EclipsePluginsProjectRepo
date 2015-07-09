package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.ColorConstants;


public class ConnectionFigure extends
		org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx {

	
	/**
	 * 
	 */
	public ConnectionFigure() {
		this.setForegroundColor(ColorConstants.black);
		setTargetDecoration(createTargetDecoration());
	}

	
	/**
	 * @return
	 */
	private org.eclipse.draw2d.PolygonDecoration createTargetDecoration() {
		org.eclipse.draw2d.PolygonDecoration df = new org.eclipse.draw2d.PolygonDecoration();
		// dispatchNext?
		df.setFill(true);
		org.eclipse.draw2d.geometry.PointList pl = new org.eclipse.draw2d.geometry.PointList();
		pl.addPoint(-2, 2);
		pl.addPoint(0, 0);
		pl.addPoint(-2, -2);
		df.setTemplate(pl);
		df.setScale(7, 3);

		return df;
	}

}

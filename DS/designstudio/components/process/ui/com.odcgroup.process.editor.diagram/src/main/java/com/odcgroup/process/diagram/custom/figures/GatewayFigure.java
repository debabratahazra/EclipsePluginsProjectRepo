package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class GatewayFigure extends Figure {

	/**
	 * 
	 */
	public GatewayFigure() {
		this.setPreferredSize(60,60);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
	 */
	public void paintFigure(Graphics graphics) {
		Rectangle bounds = getBounds().getCopy();
		org.eclipse.swt.graphics.Color fg = ColorConstants.black;
		org.eclipse.swt.graphics.Color bg = graphics.getBackgroundColor();
		bounds.width--;
		bounds.height--;
		graphics.setBackgroundColor(fg);
		int points[] = new int[8];
		points[0] = bounds.getTop().x;
		points[1] = bounds.getTop().y;
		points[2] = bounds.getRight().x;
		points[3] = bounds.getRight().y;
		points[4] = bounds.getBottom().x;
		points[5] = bounds.getBottom().y;
		points[6] = bounds.getLeft().x;
		points[7] = bounds.getLeft().y;
		graphics.setBackgroundColor(fg);
		graphics.fillPolygon(points);
		int distance = bounds.width / 20 / 2;
		bounds.shrink(distance, distance);
		points[0] = bounds.getTop().x;
		points[1] = bounds.getTop().y;
		points[2] = bounds.getRight().x;
		points[3] = bounds.getRight().y;
		points[4] = bounds.getBottom().x;
		points[5] = bounds.getBottom().y;
		points[6] = bounds.getLeft().x;
		points[7] = bounds.getLeft().y;
		graphics.setBackgroundColor(bg);
		graphics.fillPolygon(points);
	}

}

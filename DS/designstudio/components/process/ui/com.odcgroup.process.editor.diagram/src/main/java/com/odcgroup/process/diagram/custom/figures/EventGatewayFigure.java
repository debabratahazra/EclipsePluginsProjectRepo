package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;

public class EventGatewayFigure extends GatewayFigure {

	public EventGatewayFigure() {
	}

	public void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		GatewayFigureHelper.drawEventBasedXORMarker(graphics, getBounds().getCopy(), ColorConstants.black);
	}

}

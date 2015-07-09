package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;

public class ComplexGatewayFigure extends GatewayFigure {

	public ComplexGatewayFigure() {
	}

	public void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		GatewayFigureHelper.drawComplexMarker(graphics, getBounds().getCopy(), ColorConstants.black);
	}

}

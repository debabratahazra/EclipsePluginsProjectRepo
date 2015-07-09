package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;

public class ParallelMergeFigure extends GatewayFigure {

	public ParallelMergeFigure() {
	}

	public void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		GatewayFigureHelper.drawParallelMarker(graphics, getBounds().getCopy(), ColorConstants.black);
	}

}

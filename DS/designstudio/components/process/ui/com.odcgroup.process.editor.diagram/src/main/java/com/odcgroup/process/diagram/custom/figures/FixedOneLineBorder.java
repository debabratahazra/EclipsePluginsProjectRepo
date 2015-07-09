package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OneLineBorder;
import org.eclipse.swt.graphics.Color;

public class FixedOneLineBorder extends OneLineBorder {

	
    public FixedOneLineBorder(Color color, int width, int position)
    {
        super(color, width, position);
    }
	
	@Override
	public void paint(IFigure arg0, Graphics arg1, Insets arg2) {
		arg1.pushState();
		try {
			arg1.setForegroundColor(getColor());
			super.paint(arg0, arg1, arg2);
		} finally {
			arg1.popState();
		}
	}

}
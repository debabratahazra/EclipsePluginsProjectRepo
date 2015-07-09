package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;

/**
 * 
 * @author phanikumark
 * 
 */
public class CompartmentFigure extends Figure {
	
	private int offset;

	public CompartmentFigure(int offset) {
		this.offset = offset;
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(true);
		layout.setSpacing(2);
		setLayoutManager(layout);
		setBackgroundColor(ColorConstants.button);
		setOpaque(true);
		setBorder(new CompartmentFigureBorder());
	}

	public class CompartmentFigureBorder extends AbstractBorder {
		public Insets getInsets(IFigure figure) {
			return new Insets(5, offset, 0, 0);
		}

		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			
		}
	}
}
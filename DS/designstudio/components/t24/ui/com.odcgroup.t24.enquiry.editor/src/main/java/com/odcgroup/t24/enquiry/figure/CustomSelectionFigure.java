package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author phanikumark
 * 
 */
public class CustomSelectionFigure extends Figure {
	private RectangleFigure rectangle;
	private Panel panel;

	public CustomSelectionFigure() {
		setLayoutManager(new XYLayout());
		panel = new Panel();
		panel.setLayoutManager(new VerticalLayout());
		Label label = new Label("Custom Selection");
		label.setLabelAlignment(PositionConstants.LEFT);
		panel.add(label);
		rectangle = new RectangleFigure();
		rectangle.setMinimumSize(new Dimension(-1, 100));
		rectangle.setFill(false);
		rectangle.setLayoutManager(new VerticalLayout());
		rectangle.setBorder(new LineBorder(2));
		panel.add(rectangle);
		add(panel);
	}

	public IFigure getContentPane() {
		return rectangle;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		setConstraint(panel, new Rectangle(0, 0, r.width, r.height));
		panel.invalidate();
	}

}

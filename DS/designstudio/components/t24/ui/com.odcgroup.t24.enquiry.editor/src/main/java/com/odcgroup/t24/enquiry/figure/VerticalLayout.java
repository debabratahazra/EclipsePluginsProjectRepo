package com.odcgroup.t24.enquiry.figure;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author phanikumark
 * 
 */
public class VerticalLayout extends AbstractLayout {
	
	private static final int WIDGET_DEFAULT_SPACING = 4;

	@SuppressWarnings("rawtypes")
	@Override
	public void layout(IFigure container) {
		Point origin;
		Rectangle b = container.getBounds();
		if (container.getParent() == null) {
			origin = new Point(0, 0);			
		} else {
			origin = new Point(b.x,b.y);
		}
		List children = container.getChildren();
		if (children.size() == 0) {
			return;
		}
		calculateBounds(children, b.width);
		calculatePositions(children, origin);
	}


	@SuppressWarnings("rawtypes")
	protected void calculatePositions(List children, Point origin) {
		int xPos = origin.x;
		int yPos = origin.y;
		
		for (Iterator it = children.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Rectangle b = child.getBounds();
			b.x = xPos+WIDGET_DEFAULT_SPACING;
			b.y = yPos+WIDGET_DEFAULT_SPACING;
			child.setBounds(b);
			yPos += b.height;
			yPos += WIDGET_DEFAULT_SPACING;
		}
	}

	@SuppressWarnings("rawtypes")
	protected void calculateBounds(List children, int containerWidth) {
		int width = containerWidth - 2 * WIDGET_DEFAULT_SPACING;		
		for (Iterator it = children.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Dimension d = child.getMinimumSize();
			int height;
			if (d.height == 0) {
				height = 20;
			} else {
				height = d.height;
			}
			Rectangle nb = new Rectangle(0, 0, width, height);
			child.setBounds(nb);
		}
	}

	@Override
	protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
		return null;
	}

}

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
public class HorizontalLayout extends AbstractLayout {
	
	private static final int WIDGET_DEFAULT_SPACING = 6;

	@SuppressWarnings("rawtypes")
	@Override
	public void layout(IFigure container) {
		Rectangle pb = container.getParent().getBounds();
		Point origin = new Point(pb.x, pb.y);
		Rectangle b = container.getBounds();
		List children = container.getChildren();
		if (children.size() == 0) {
			return;
		}
		calculateBounds(children, b.width, b.height);
		calculatePositions(children, origin);
	}


	@SuppressWarnings("rawtypes")
	protected void calculatePositions(List children, Point origin) {
		int xPos =  origin.x+20;
		int yPos =  origin.y+20;
		
		for (Iterator it = children.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Rectangle b = child.getBounds();
			b.x = xPos;
			b.y = yPos;
			child.setBounds(b);
			xPos += b.width;
			xPos += WIDGET_DEFAULT_SPACING;
		}
	}
	
	protected int calculateChildWidth(int containerWidth, int childCount) {
		int width = containerWidth - ((childCount+1) * WIDGET_DEFAULT_SPACING);
		return (width/ childCount);
	}

	@SuppressWarnings("rawtypes")
	protected void calculateBounds(List children, int containerWidth, int containerHeight) {
		int height = containerHeight-20 - (2 * WIDGET_DEFAULT_SPACING);	
		int width = calculateChildWidth(containerWidth-20, children.size());
		for (Iterator it = children.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Rectangle nb = new Rectangle(0, 0, width, height);
			child.setBounds(nb);
		}
	}


	@Override
	protected Dimension calculatePreferredSize(IFigure arg0, int arg1, int arg2) {
		return null;
	}

}

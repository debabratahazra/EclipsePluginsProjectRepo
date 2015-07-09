package com.odcgroup.page.ui.figure.table;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.HorizontalLayout;

/**
 * This class implement a layout manager for the ColumnBody figure
 * 
 * @author Alexandre Jaquet
 *
 */
public class ColumnBodyHorizontalLayout extends HorizontalLayout {
	
	/**
	 * Creates a new HorizontalLayout.
	 * 
	 * @param figureContext The context in which the Layout is being used
	 */
	public ColumnBodyHorizontalLayout(FigureContext figureContext) {
		super(figureContext);
	}
	
	/**
	 * Gets the horizontal alignment of the figure. We need to retrieve the value of
	 * the ColumnBody figure. And then it will be used by the HorizontalLayoutManager to calculate
	 * the correct position of the children.
	 * 
	 * @param figure
	 * 			The figure
	 * @return String The horizontal alignment
	 */
	protected String getHorizontalAlignment(IFigure figure) {
		IFigure p = figure.getParent();
		while (p != null) {
			if (p instanceof ColumnBody) {
				ColumnBody cb = (ColumnBody) p;
				return cb.getHorizontalAlignment();
			}
			p = p.getParent();
		}
		return "";
	}
	
	/**
	 * Gets the origin of the figure.
	 * 
	 * @param container The container
	 * @return Point
	 */
	protected Point getOrigin(IFigure container) {
		ColumnBody cb = (ColumnBody) container;
		if (cb.isGrouped()) {
			return new Point(0, -ColumnBody.IMAGE_HEIGHT);
		} else {
			return new Point(0, 0);
		}	
	}	
}

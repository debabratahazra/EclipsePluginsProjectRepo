package com.odcgroup.page.ui.figure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;

/**
 * Base class for WidgetLayouts.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractWidgetLayout extends AbstractLayout implements CalculatingLayout {
	
	/** The context contains information such as whether we are in design mode... */
	private FigureContext figureContext;
	
	/**
	 * Creates a new AbstractWidgetLayout.
	 * 
	 * @param figureContext The context in which the Layout is being used
	 */
	public AbstractWidgetLayout(FigureContext figureContext) {
		this.figureContext = figureContext;
	}
	
	/**
	 * Calculates the preferred size of a Widget. This method is currently unused.
	 * 
	 * @param container
	 *            The container
	 * @param wHint
	 *            The hint for the width
	 * @param hHint
	 *            The hine for the height
	 * @return Dimension The preferred size of the Widget
	 */
	protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
		return null;
	}

	/**
	 * Gets the FigureConstants.
	 * 
	 * @return FigureConstants
	 */
	protected FigureConstants getFigureConstants() {
		 return getFigureContext().getFigureConstants();
	 }	

	/**
	 * Builds the List of Widgets to be laid out. This method takes into account ComposedWidgetFigure's.
	 * 
	 * @param container
	 *            The container to build the List of Widgets for
	 * @return List The List of WidgetFigures to be laid out
	 */
	protected List buildWidgetList(IFigure container) {
		List list = new ArrayList();
		for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			if (child instanceof WidgetFigureContainer) {
				WidgetFigureContainer cwf = (WidgetFigureContainer) child;
				list.addAll(cwf.getWidgetFigures());
			} else {
				list.add(child);
			}
		}

		return list;
	}	
	
	/**
	 * Gets the FigureContext.
	 * 
	 * @return FigureContext The FigureContext
	 */
	protected FigureContext getFigureContext() {
		return figureContext;
	}	
	
	/**
	 * Calculates the minimum x-position of a set of Widgets.
	 * 
	 * @param widgets The List of Widgets to calculate the minimum x-position for
	 * @return int The minimum x-position
	 */
	protected int calculateMinX(List widgets) {
		int minX = 0;
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			if (minX == 0) {
				minX = child.getBounds().x;
			} else {
				minX = Math.min(minX, child.getBounds().x);
			}
		}
		
		return minX;
	}
	
	/**
	 * Calculates the minimum y-position of a set of Widgets.
	 * 
	 * @param widgets The List of Widgets to calculate the minimum y-position for
	 * @return int The minimum y-position
	 */
	protected int calculateMinY(List widgets) {
		int minY = 0;
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			if (minY == 0) {
				minY = child.getBounds().y;
			} else {
				minY = Math.min(minY, child.getBounds().y);
			}
		}
		
		return minY;
	}	
}
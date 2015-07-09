package com.odcgroup.page.ui.figure;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.LayoutManager;

/**
 * The BoxType for HorizontalBoxes.
 *
 * @author Gary Hayes
 */
public class HorizontalBoxType extends AbstractBoxType {
	
	/** The absolute box type character used to display. */
	private static String BOX_TYPE_TO_DISPLAY = "H";
	
	/**
	 * Creates a new HorizontalBoxType.
	 * 
	 * @param box The BoxFigure that this is the type for
	 */
	public HorizontalBoxType(BoxFigure box) {
		super(box);
	}
	
	/**
	 * Creates a LayoutManager for this type of box.
	 * 
	 * @param figureContext The FigureContext
	 * @return LayoutManager The newly created LayoutManager
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		return new HorizontalLayout(figureContext);
	}

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	@SuppressWarnings("rawtypes")
	public int getMinWidth() {
		List children = getChildren();
		int width = 0;
		for (Iterator it = children.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			width += child.getMinWidth();
		}
		
		if (children.size() > 0) {	
			// Allow a spacing before the first Widget, after the last Widget and between each Widget
			width += getFigureConstants().getWidgetSpacing() * (children.size() + 1);
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		
		// Users can define a fixed width for the HorizontalBox. However if the width
		// needed to display all its components is larger than this width then the
		// defined width is ignored.
		if (width < getPixelWidth()) {
			width = getPixelWidth();
		}
		
		return width;
	}	
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	@SuppressWarnings("rawtypes")
	public int getMinHeight() {
		// Users can define a fixed height for the HorizontalBox. If this is smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}
		
		List children = getChildren();
		
		int height = 0;
		for (Iterator it = children.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height= Math.max(height, child.getMinHeight());
		}	
		
		if (children.size() > 0) {
			// Allow a space above and below the tallest Widget
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		
		return height;	
	}	

	/**
	 * Gets the maximum width of the figure. HorizontalBoxes do not have
	 * maximum widths. Instead they can expand to fill the entire width
	 * of the parent container.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		// Users can define a fixed width for the HorizontalBox. However if the width
		// needed to display all its components is larger than this width then the
		// defined width is ignored.
		if (getPixelWidth() > 0) {
			return getPixelWidth();
		}		
		
		return -1;
	}
	
	/**
	 * Gets the maximum height of the figure.
	 * 
	 * @return int The maximum height of the figure
	 */
	@SuppressWarnings("rawtypes")
	public int getMaxHeight() {
		// Users can define a fixed height for the HorizontalBox. If this is smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}		
		
		List children = getChildren();
		int height = 0;
		for (Iterator it = children.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height= Math.max(height, child.getMaxHeight());
		}	
		
		if (children.size() > 0) {
			// Allow a space above and below the tallest Widget
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		return height;
	}
	
	/**
	 * Gets the String used to display a difference between the boxes.
	 * 
	 * @return String
	 */
	public String getBoxType() {
		return BOX_TYPE_TO_DISPLAY;
	}
}
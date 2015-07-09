package com.odcgroup.page.ui.figure.table;

import java.util.Iterator;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractWidgetFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * The body of a Table.
 * 
 * @author Gary Hayes
 */
public class TableBody extends AbstractWidgetFigure {

	/**
	 * Creates a new TableBody.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public TableBody(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);

		setLayoutManager(new TableBodyLayout(figureContext));
	}
	
	/**
	 * Returns true since we wish to use local coordinates.
	 * 
	 * @return boolean True since we wish to use local coordinates
	 */
	protected boolean useLocalCoordinates() {
		return true;
	}
	
	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		int width = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			width += child.getMinWidth();
		}
		
		if (getChildren().size() > 0) {	
			// Allow a spacing before the first Widget, after the last Widget and between each Widget
			width += getFigureConstants().getWidgetSpacing() * (getChildren().size() + 1);
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		
		return width;
	}	
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {	
		int height = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height= Math.max(height, child.getMinHeight());
		}	
		
		if (getChildren().size() > 0) {
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
		return -1;
	}
	
	/**
	 * Gets the maximum height of the figure.
	 * 
	 * @return int The maximum height of the figure
	 */
	public int getMaxHeight() {		
		int height = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height= Math.max(height, child.getMaxHeight());
		}	
		
		if (getChildren().size() > 0) {
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
}
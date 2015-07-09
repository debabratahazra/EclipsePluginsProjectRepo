package com.odcgroup.page.ui.figure.tab;

import java.util.Iterator;
import java.util.List;

import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.VerticalBoxType;

public class TabBodyBoxType extends VerticalBoxType {

	/**
	 * @param box
	 */
	public TabBodyBoxType(BoxFigure box) {
		super(box);
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
			width += 2*getFigureConstants().getWidgetSpacing() * (children.size() + 1);
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

}

package com.odcgroup.page.ui.figure.tab;

import java.util.Iterator;
import java.util.List;

import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.VerticalBoxType;

public class ConditionalBodyBoxType extends VerticalBoxType {

	/**
	 * @param box
	 */
	public ConditionalBodyBoxType(BoxFigure box) {
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
		boolean vertical = false;
		if ("V".equals(getBoxType())) {
			vertical = true;
		}
		for (Iterator it = children.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			if (vertical) {
				width = Math.max(width, child.getMinWidth());
			} else {
				width += child.getMinWidth();
			}
		}
		
		int swidth = 2*getFigureConstants().getWidgetSpacing();
		if (children.size() > 0 && !vertical) {	
			// Allow a spacing before the first Widget, after the last Widget and between each Widget
			width +=  swidth * (children.size() + 1);
		}			
		if (vertical) {
			width += swidth;
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

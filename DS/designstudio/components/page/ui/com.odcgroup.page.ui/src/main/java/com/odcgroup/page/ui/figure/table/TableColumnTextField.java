package com.odcgroup.page.ui.figure.table;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

public class TableColumnTextField extends TableColumnItem {	
	
	/** The field minimum width*/
	private static final int TEXTFIELD_WIDTH = 100;
	
	public TableColumnTextField(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	protected void paintSpecificFigure(Graphics graphics) {
		// TODO: reuse TextField
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(0, 0, b.width - 1, b.height - 1));
		
		// The 1 serves to center the text in the rectangle
		String pv = getPreviewValue();
		if (! StringUtils.isEmpty(pv)) {
			graphics.drawText(pv, 1, 1);
		}
	}
	
	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return TEXTFIELD_WIDTH;
	}

}

package com.odcgroup.page.ui.figure.table;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

public class TableColumnCalendar extends TableColumnItem {	
	
	/** The Calendar image. */
	private static final Image IMAGE = createImage("/icons/obj16/calendar.png"); //$NON-NLS-1$	

	/** The field minimum width*/
	private static final int CDATE_WIDTH = 100;
	
	public TableColumnCalendar(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	protected void paintSpecificFigure(Graphics graphics) {
		//TODO: reuse CaldateField
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		Rectangle b = getBounds();
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		graphics.drawRectangle(new Rectangle(0, 0, b.width - getFigureConstants().getSimpleWidgetDefaultHeight() - 1, b.height - 1));
		graphics.drawImage(IMAGE, b.width - 16, 0);		
	}
	
	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return CDATE_WIDTH;
	}
}

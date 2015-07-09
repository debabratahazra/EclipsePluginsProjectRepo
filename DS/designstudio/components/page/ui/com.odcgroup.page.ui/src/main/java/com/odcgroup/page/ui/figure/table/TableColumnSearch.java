package com.odcgroup.page.ui.figure.table;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

public class TableColumnSearch extends TableColumnItem {	
	
	/** The ComboBox width*/
	private static final int SEARCH_FIELD_WIDTH = 100;
	
	/** The SearchField in the unchecked state. */
	private static final Image IMAGE = createImage("/icons/obj16/search.png"); //$NON-NLS-1$
	
	public TableColumnSearch(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return SEARCH_FIELD_WIDTH;
	}
	
	protected void paintSpecificFigure(Graphics graphics) {
		// TODO: reuse SearchField
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(0, 0, b.width - getFigureConstants().getSimpleWidgetDefaultWidth() - 1, b.height - 1));
		
		graphics.drawImage(IMAGE, b.width - getFigureConstants().getSimpleWidgetDefaultWidth(), 0);
	
		// The 1 serves to center the text in the rectangle
		String pv = getPreviewValue();
		if (! StringUtils.isEmpty(pv)) {
			graphics.drawText(pv, 1, 1);
		}
	}
	
}

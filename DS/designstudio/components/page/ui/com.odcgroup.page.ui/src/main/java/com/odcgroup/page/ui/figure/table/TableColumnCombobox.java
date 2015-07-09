package com.odcgroup.page.ui.figure.table;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

public class TableColumnCombobox extends TableColumnItem {	

	/** The ComboBox width*/
	private static final int COMBO_BOX_WIDTH = 100;
		
	/** The percentage character. */
	private static final String CHARACTER_PERCENT = PropertyTypeConstants.CHARACTER_PERCENT;

	/** The CheckBox in the unchecked state. */
	private static final Image IMAGE = createImage("/icons/obj16/comboBox.png"); 
	
	public TableColumnCombobox(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	protected void paintSpecificFigure(Graphics graphics) {
		// TODO: reuse ComboBox
		FigureConstants fc = getFigureConstants();
		
		Rectangle b = getBounds();
		int width = getMinWidth();
		
		// The -1 ensures that the right and bottom lines are drawn, otherwise they would fall outside the bounds of the ComboBox
		graphics.setBackgroundColor(ColorConstants.white);
		graphics.fillRectangle(new Rectangle(0, 0, width - 1, b.height - 1));
		
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.drawRectangle(new Rectangle(0, 0, width - 1, b.height - 1));
		
		graphics.drawImage(IMAGE, new Point(width - IMAGE.getBounds().width, 1));	
		
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
		return COMBO_BOX_WIDTH;
	}

}

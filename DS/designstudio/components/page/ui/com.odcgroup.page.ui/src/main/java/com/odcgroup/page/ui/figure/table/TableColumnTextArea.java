package com.odcgroup.page.ui.figure.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

public class TableColumnTextArea extends TableColumnItem {	
	
	/** The RadioButton in the unchecked state. */
	private static Image RICH_TEXTAREA = createImage("/icons/obj16/richtextarea.png");
		
	/** Thw width of the image. */
	private static final int IMAGE_WIDTH = 384;
		
	/** The height of the image. */
	private static final int IMAGE_HEIGHT = 48;
		
	/** The data displayed in the preview pane. */
	private List<String> previewValueList;
	
	public TableColumnTextArea(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	protected void paintSpecificFigure(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(0, 0, b.width - 1, getHeight() - 1));
		
//		int y = 0;
//		if (isRichText()) {
//			y = IMAGE_HEIGHT;
//		
//			graphics.setBackgroundColor(fc.getRichTextAreaBackgroundColor());
//			graphics.fillRectangle(new Rectangle(0, 0, b.width - 1, IMAGE_HEIGHT));
//			graphics.drawImage(RICH_TEXTAREA, 0, 0);
//		}
//		
//		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
//		graphics.drawRectangle(new Rectangle(0, y, b.width - 1, b.height - y - 1));
//		
//		List<String> pvs = getPreviewValueList();
//		int yPos = y+1;
//		for (String s : pvs) {
//			graphics.drawText(s, 1, yPos);
//			yPos += 12;
//		}
//		
	}
	
	/**
	 * Returns true of the TextArea can contain Rich Text.
	 * 
	 * @return boolean
	 */
	private boolean isRichText() {
		return getBoolean(PropertyTypeConstants.RICHTEXT);
	}
	
	/**
	 * Gets the preview value as a List. This takes into account the size 
	 * of the text.
	 * 
	 * @return List of String's
	 */
	private List<String> getPreviewValueList() {
		return new ArrayList<String>();
//		if (previewValueList != null) {
//			return previewValueList;
//		}
//		
//		previewValueList = new ArrayList<String>();
//		
//		int minWidth = getMinWidth();
//		String pv = getPreviewValue();
//		
//		int pvWidth = calculateTextSize(pv).width;
//		
//		if (pvWidth < minWidth) {
//			previewValueList.add(pv);
//			return previewValueList;
//		} 
//		
//		int sWidth;
//		while (true) {
//			String s = pv;
//			do {
//				s = s.substring(0, s.length() - 1);
//				sWidth = calculateTextSize(s).width;
//			} while (sWidth > minWidth);
//			previewValueList.add(s);
//			pv = pv.substring(s.length());
//			
//			pvWidth = calculateTextSize(pv).width;
//			if (pvWidth < minWidth) {
//				previewValueList.add(pv);
//				return previewValueList;
//			}
//		}
	}
	
	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		int min = (int) (getColumns() *  getFigureConstants().getTextAreaColumnScalingFactor());
		
		if (isRichText()) {
			if (min < IMAGE_WIDTH) {
				min = IMAGE_WIDTH;
			}
		}
		
		return min;
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getHeight() {
		int min = getRows() * getFigureConstants().getRowScalingFactor();
		
		if (isRichText()) {
			min += IMAGE_HEIGHT;
		}
		
		return min;
	}	
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMaxHeight() {
		int min = getRows() * getFigureConstants().getRowScalingFactor();
		
		if (isRichText()) {
			min += IMAGE_HEIGHT;
		}
		
		return min;
	}	
	
	/**
	 * Return the number of columns.
	 *  
	 * @return int The number of columns.
	 */	
	public int getColumns() {
		return 30;
	}

	/**
	 * Return the number of rows of the TextArea.
	 * 
	 * @return int The number of rows of the TextArea.
	 */
	public int getRows() {
		return 2;
	}
	

}

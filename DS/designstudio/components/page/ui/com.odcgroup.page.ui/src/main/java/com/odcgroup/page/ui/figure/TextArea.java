package com.odcgroup.page.ui.figure;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * Represents a TextArea. This is a multi-line field where text can be entered.
 * 
 * @author Gary Hayes
 */
public class TextArea extends AbstractAlignableWidgetFigure  {
	
	/** The RadioButton in the unchecked state. */
	private static Image RICH_TEXTAREA = createImage("/icons/obj16/richtextarea.png");

	/** Thw width of the image. */
	private static final int IMAGE_WIDTH = 384;
		
	/** The height of the image. */
	private static final int IMAGE_HEIGHT = 48;
	
	/** The data displayed in the preview pane. */
	private List<String> previewValueList;
	
	/**
	 * Creates a new TextArea.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public TextArea(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
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
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				return Math.max(min, PaintUtils.getWidth(getWidget()));
			}
			root = root.getParent();
		}
		return min + PaintUtils.getWidth(getWidget());
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		int min = getRows() * getFigureConstants().getRowScalingFactor();
		
		if (isRichText()) {
			min += IMAGE_HEIGHT;
		}
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				min += PaintUtils.IMAGE_HEIGHT;
				break;
			}
			root = root.getParent();
		}
		return min;
	}	
	
	/**
	 * Paints the TextArea.
	 * 
	 * @param graphics The Graphics context
	 * @see Figure#paintFigure(Graphics)
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		Widget root = getWidget().getParent();
		int y = 0;
		int x = 0;
		boolean isWidgetInsdeTable=false;
		int widthOfXtooltip=PaintUtils.getWidth(getWidget());
		while(root != null) {
			if(root.getTypeName().equals("TableColumn")) {
			    isWidgetInsdeTable=true;	
			    break;
			}
			root = root.getParent();
		}
		if(PaintUtils.isWidgethAlignLead(getWidget()) && !isWidgetInsdeTable){
		    PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - widthOfXtooltip);
		}else {
		    x=PaintUtils.paintIcons(getWidget(), graphics,0);
		}
		if(isWidgetInsdeTable && widthOfXtooltip>0){
		   y = PaintUtils.IMAGE_HEIGHT;
		   x = 0;
		}
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		
		Rectangle b = getBounds();
		
		if (isRichText()) {
			graphics.setBackgroundColor(fc.getRichTextAreaBackgroundColor());
			graphics.fillRectangle(new Rectangle(x, y, b.width - ((widthOfXtooltip + 1)), IMAGE_HEIGHT));
			graphics.drawImage(RICH_TEXTAREA, x, y);
		}
		
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		graphics.drawRectangle(new Rectangle(x, y, b.width - (widthOfXtooltip+1), b.height - y - 1));
		
		List<String> pvs = getPreviewValueList();
		int yPos = y+1;
		for (String s : pvs) {
			graphics.drawText(s, x + 1, yPos + y);
			yPos += 12;
		}
		
	}	
	
	/**
	 * Return the number of columns.
	 *  
	 * @return int The number of columns.
	 */	
	public int getColumns() {
		return getInt(PropertyTypeConstants.COLUMNS);
	}

	/**
	 * Return the number of rows of the TextArea.
	 * 
	 * @return int The number of rows of the TextArea.
	 */
	public int getRows() {
		return getInt(PropertyTypeConstants.ROWS);
	}
	
	/**
	 * Sets the number of rows.
	 * 
	 * @param rows The number of rows to set
	 */
	public void setRows(String rows) {
		getWidget().setPropertyValue(PropertyTypeConstants.ROWS, rows);
	}
	
	/**
	 * Sets the number of columns.
	 * 
	 * @param columns The number of columns to set
	 */
	public void setColumns(String columns) {
		getWidget().setPropertyValue(PropertyTypeConstants.COLUMNS, columns);
	}	
	
	
	/**
	 * Implements the required treatment after the property have been
	 * changed.
	 * 
	 * @param name
	 * 		The property name
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.PREVIEW_VALUE)) {
			previewValueList = null;
		}
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
		if (previewValueList != null) {
			return previewValueList;
		}
		
		previewValueList = new ArrayList<String>();
		
		int minWidth = getMinWidth();
		String pv = getPreviewValue();
		
		int pvWidth = calculateTextSize(pv).width;
		
		if (pvWidth < minWidth) {
			previewValueList.add(pv);
			return previewValueList;
		} 
		
		int sWidth;
		while (true) {
			String s = pv;
			do {
				s = s.substring(0, s.length() - 1);
				sWidth = calculateTextSize(s).width;
			} while (sWidth > minWidth);
			previewValueList.add(s);
			pv = pv.substring(s.length());
			
			pvWidth = calculateTextSize(pv).width;
			if (pvWidth < minWidth) {
				previewValueList.add(pv);
				return previewValueList;
			}
		}
	}	
}
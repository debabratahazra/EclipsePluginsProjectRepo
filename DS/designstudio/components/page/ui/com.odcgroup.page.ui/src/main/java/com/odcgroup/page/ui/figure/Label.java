package com.odcgroup.page.ui.figure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.grid.GridCellFigure;
import com.odcgroup.page.ui.figure.grid.GridCellLayoutUtil;

/**
 * A class which represents a simple labels.
 * 
 * @author Gary Hayes
 */
public class Label extends AbstractAlignableWidgetFigure {
	
	/** The minimum size of the Label. */
	private final static int MIN_SIZE = 50;
	
	/**
	 * Creates a new Label.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public Label(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Method called when a Preference is changed in Eclipse. 
	 * The default version does nothing.
	 */
	public void preferenceChange() {
		revalidate();
	}	

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {	
		boolean inTable = false;
		int utilsWidth = PaintUtils.getWidth(getWidget());
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				inTable = true;
				break;
			}
			root = root.getParent();
		}
		String text = getText();
		GridCellFigure gridCell = GridCellLayoutUtil.fetchParentGridCell(this);
		int minWidth = calculateTextSize(text).width;		
		if (text.length() == 0) {	
			minWidth = MIN_SIZE;
		}
		if (gridCell != null) {		
			int gridCellWidth = gridCell.getBounds().width;
			if (minWidth < MIN_SIZE) {
				if(inTable) {
					return Math.max(minWidth, utilsWidth);
				}
				return minWidth + utilsWidth;
			}
			int ret = GridCellLayoutUtil.getPossibleWidth(getFigureConstants(), gridCell, 
					getParent(), gridCellWidth, minWidth);
			int min = getFigureConstants().getSimpleWidgetDefaultWidth();
			if(inTable) {
				return Math.max(((ret > min) ? ret : min), utilsWidth);
			}
			return ((ret > min) ? ret : min) + utilsWidth;
		}
		if(inTable) {
			return Math.max(minWidth, utilsWidth);
		}
		return minWidth + utilsWidth;
	}	
	
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		int imageHeight = 0;
		int textHeight = getFigureConstants().getSimpleWidgetDefaultHeight();
		int textWidth = calculateTextSize(getText()).width;
		int minWidth = getMinWidth();
		if (minWidth <= 0) {
			GridCellFigure gridCell = GridCellLayoutUtil.fetchParentGridCell(this);
			if (gridCell != null) {
				int gridCellWidth = gridCell.getBoxType().getMinWidth();
				minWidth = GridCellLayoutUtil.getPossibleWidth(getFigureConstants(), 
						gridCell, getParent(), gridCellWidth, textWidth);
			}
		}
		minWidth = Math.max(MIN_SIZE, minWidth);
		if (textWidth > minWidth && (minWidth > 0)) {
			String text = getText();
			int fraction = (int) Math.round((textWidth/text.length()) + 0.5);
			int charPerLine = (int) Math.round((minWidth/fraction) +0.5);			
			int lines = (int) Math.round((text.length()/charPerLine)+0.5);
			textHeight = lines * textHeight;
		}
		int result = Math.max(imageHeight, textHeight);
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				result += PaintUtils.IMAGE_HEIGHT;
				break;
			}
			root = root.getParent();
		}
		return result;
	}	
	
	/**
	 * Paints the Label.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		Widget root = getWidget().getParent();
		int y = 0;
		int x = 0;
		if(PaintUtils.isWidgethAlignLead(getWidget())){
		    PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - PaintUtils.getWidth(getWidget()));
		}else {
		    x=PaintUtils.paintIcons(getWidget(), graphics,0);
		}
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && x > 0) {
				y = PaintUtils.IMAGE_HEIGHT;
				x = 0;
				break;
			}
			root = root.getParent();
		}
		FigureConstants fc = getFigureConstants();
				String s = getHTextPosition();
		String text = getText();

		graphics.setForegroundColor(getTextForegroundColor());
		

		int textWidth = calculateTextSize(text).width;
		int minWidth = getMinWidth();
		if (!StringUtils.isEmpty(text)) {
			if (textWidth > minWidth) {
				int fraction = (int) Math.round((textWidth/text.length()) + 0.5);
				int charPerLine = (int) Math.round((minWidth/fraction) +0.5);
				if (charPerLine > 0) {
					List<String> list = new ArrayList<String>();
					splitText(list, text, charPerLine);
					//y = 0;
					for (String string : list) {
						if (Alignable.LEAD.equals(s)) {
							// Draw the image after the text
							x = 2;
							graphics.drawText(string, x, y);	
						} else {
							graphics.drawText(string, x, y);	
						}
						y += getFigureConstants().getSimpleWidgetDefaultHeight();
					}
				}
			} else {
				if (Alignable.LEAD.equals(s)) {
					// Draw the image after the text
					x = 2;
					graphics.drawText(text, x, y);	
				} else {
					graphics.drawText(text, x, y);	
				}
			}
		}
		
		if (getFigureContext().isDesignMode() && StringUtils.isEmpty(text)) {
		    Rectangle r = getBounds();
		    graphics.setForegroundColor(fc.getOutlineBorderForegroundColor());
		    graphics.setLineStyle(fc.getOutlineBorderLineStyle());
		    graphics.drawRectangle(x, y, r.width - (x+1), r.height - 1);
		    graphics.setClip(new Rectangle(x + 4, -6 + y, 36 + x, 12 + y));
            graphics.drawText("Label", x + 4, -6 + y);
		    
		}
	}	
	
	/**
	 * @param list
	 * @param text
	 * @param charLength
	 * @return list
	 */
	private List<String> splitText(List<String> list, String text, int charLength) {
		if (text.length() > charLength) {
			String str = text.substring(0, charLength);
			int index = str.lastIndexOf(" ");
			if (index == -1) {
				list.add(str);
				splitText(list, text.substring(charLength), charLength);				
			} else {
				list.add(str.substring(0, index));
				splitText(list, text.substring(index+1), charLength);
			}
		} else {
			list.add(text);
		}
		return list;
	}
	  
	/**
	 * Get the position of the accompanying the text horizontally to the icon.
	 * 
	 * @return String
	 */
	private String getHTextPosition() {
		return getString(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION);
	}	
	
	/**
	 * Reset the image to null if the icon has changed.
	 * 
	 * @param name
	 * 		The property name
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.HORIZONTAL_ALIGNMENT)) {
			revalidate();
		}
	}
	
}

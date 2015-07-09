package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * A ComboBox presents a select in a user-interface.
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class ComboBox extends AbstractAlignableWidgetFigure {

	/** The percentage character. */
	private static final String CHARACTER_PERCENT = PropertyTypeConstants.CHARACTER_PERCENT;

	/** The CheckBox in the unchecked state. */
	private static final Image IMAGE = createImage("/icons/obj16/comboBox.png"); 
	
	/** The ComboBox width*/
	private static final int COMBO_BOX_WIDTH = 100;
	
	int boundsWidth = 0;
		
	/**
	 * Creates a new ComboBox.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public ComboBox(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
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
		if (getComboBoxBoundsWidth() > COMBO_BOX_WIDTH) {
			if(inTable) {
				return getComboBoxBoundsWidth();
			}
			return getComboBoxBoundsWidth() ;
		}else {
			if(inTable) {
				return COMBO_BOX_WIDTH;
			}
			return COMBO_BOX_WIDTH + PaintUtils.getWidth(getWidget());
		}
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		if (getSizeProperty() > 0) {
			return getSizeProperty() * 14;
		}
		int result = getFigureConstants().getSimpleWidgetDefaultHeight();
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
	 * Paints the ComboBox.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		Widget root = getWidget().getParent();
		boolean isinsideTable=false;
		int y = 0;
		int x = 0;
		while(root != null) {
			if(root.getTypeName().equals("TableColumn")) {
				isinsideTable =true;
				break;
			}
			root = root.getParent();
		}
		if(PaintUtils.isWidgethAlignLead(getWidget()) && !isinsideTable){
		    PaintUtils.paintIcons(getWidget(), graphics, getMinWidth() - PaintUtils.getWidth(getWidget()));
		}else {
		  x=PaintUtils.paintIcons(getWidget(), graphics,0);
		}
		if(isinsideTable && PaintUtils.getWidth(getWidget())!=0 ){
		    x=0;
		    y=PaintUtils.IMAGE_HEIGHT;
		}
		FigureConstants fc = getFigureConstants();
	
		Rectangle b = getBounds();
		int width = getComboBoxBoundsWidth();
		// The -1 ensures that the right and bottom lines are drawn, otherwise they would fall outside the bounds of the ComboBox
		graphics.setBackgroundColor(ColorConstants.white);
		graphics.fillRectangle(new Rectangle(x, y, width - (1+ PaintUtils.getWidth(getWidget())), b.height - (1+y)));
		
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.drawRectangle(new Rectangle(x, y, width - (1+ PaintUtils.getWidth(getWidget())), b.height - (1+y)));
		
		// A drop-down list is no longer displayed when a size has been selected
		if (getSizeProperty() == 0) {
			// +1 so as not to hide the rectangle
		    int imageX=IMAGE.getBounds().width;
		    if(x==0){
			imageX+=PaintUtils.getWidth(getWidget());
		     }
		     graphics.drawImage(IMAGE, new Point(width -imageX , 1+y));	
		}
		
		// The 1 serves to center the text in the rectangle
		String pv = getPreviewValue();
		if (! StringUtils.isEmpty(pv)) {
			graphics.drawText(pv, x+1, 1+y);
		}
	}	

	/**
	 * Get the ComboBox width.
	 * 
	 * @return String
	 */
	public String getWidth() {
		return getString(PropertyTypeConstants.WIDTH);
	}

	/**
	 * Set the ComboBox width.
	 * 
	 * @param width The width to set
	 */
	public void setWidth(String width) {
		getProperty(PropertyTypeConstants.WIDTH).setValue(width);
	}
	
	/**
	 * Gets the size (height) of the ComboBox. Note that the method getSize() is already
	 * taken by Figure so I have named this method getSizeProperty.
	 * 
	 * @return int
	 */
	private int getSizeProperty() {
		return getInt(PropertyTypeConstants.SIZE);
	}
	
	/**
	 * Gets the width of the box as a percentage. If the width is expressed
	 * in pixels this returns 100.
	 * 
	 * @return int The width of the box as a percentage
	 */
	public int getPercentageWidth() {
		String w = getWidth();
		if (! w.contains(CHARACTER_PERCENT)) {
			return 100;
		}
		try {
			w = w.replaceAll(CHARACTER_PERCENT, "");
			return Integer.parseInt(w);
		} catch(NumberFormatException nfe) {
			return 0;
		}
	}	
	/**
	 * Gets the width of the box in pixels. If the width is expressed
	 * in percentage this returns 0.
	 * 
	 * @return int The width of the box in pixels
	 */
	public int getPixelWidth() {
		String w = getWidth();
		if (w.contains(CHARACTER_PERCENT)) {
			return 0;
		}
		try {
			return Integer.parseInt(w);
		} catch(NumberFormatException nfe) {
			return 0;
		}
	}	
	/**
	 * Calculates the width to draw the box taking into account the percentage
	 * width.
	 * 
	 * @return int The width to draw the box
	 */
	public int getComboBoxBoundsWidth() {
		if (getWidth().equals("0") || getWidth().equals("")) {
			return getBounds().width;
		}else if (getWidth().contains(CHARACTER_PERCENT)) {
			return (int) ((getParent().getBounds().width - 1) * getPercentageWidth() / 100);
		}else {
			return getPixelWidth();
		}
	}
}

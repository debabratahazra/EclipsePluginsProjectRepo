package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * Base-class for drawing Buttons.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractToggleButton extends AbstractAlignableWidgetFigure {

	/** The width of the Image. */
	private static final int IMAGE_WIDTH = 16;
	
	/** The image to use to draw the button. */
	private Image buttonImage;

	/**
	 * Creates a new AbstractToggleButton.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public AbstractToggleButton(Widget widget,FigureContext figureContext) {
		super(widget, figureContext);
	}	
	
	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {			
		int minWidth = 0;
		if (displayLabel()) {
			minWidth = calculateTextSize(getText()).width;
		}
		minWidth += IMAGE_WIDTH;
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				return Math.max(minWidth, PaintUtils.getWidth(getWidget()));
			}
			root = root.getParent();
		}
		return minWidth + PaintUtils.getWidth(getWidget());
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		int imageHeight = 0;
		int textHeight = getFigureConstants().getSimpleWidgetDefaultHeight();
		int resultHeight = Math.max(imageHeight, textHeight);
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				resultHeight += PaintUtils.IMAGE_HEIGHT;
				break;
			}
			root = root.getParent();
		}
		return resultHeight;
	}	
	
	/**
	 * @return
	 */
	protected boolean displayLabel() {
		Property displayLabelProp = getWidget().findProperty(PropertyTypeConstants.DISPLAY_LABEL);
		boolean displayLabel = true;
		if(displayLabelProp != null) {
			displayLabel = displayLabelProp.getBooleanValue();
		}
		return displayLabel;
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
		    PaintUtils.paintIcons(getWidget(), graphics, getMinWidth() - PaintUtils.getWidth(getWidget()));
		}else {
		x = PaintUtils.paintIcons(getWidget(), graphics,0);
		}
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && x > 0) {
				y = PaintUtils.IMAGE_HEIGHT;
				x = 0;
				break;
			}
			root = root.getParent();
		}
		String s = getHTextPosition();
		String text = getText();
		graphics.setForegroundColor(getTextForegroundColor());
		
		if (Alignable.LEAD.equals(s)) {
			// Draw the image after the text
			x = 2;
			if(displayLabel()) {
				graphics.drawText(text, x, y);	
				x += calculateTextSize(text).width;
			}
			Image buttonImage = getButtonImage();
			graphics.drawImage(buttonImage, new Point(x, y));
			
		} 
		else {
			// Draw the image before the text
			Image buttonImage = getButtonImage();
			graphics.drawImage(buttonImage, new Point(x, y));
			x += buttonImage.getBounds().width;
			if(displayLabel()) {
				graphics.drawText(text, x, y);	
			}
		}
	}
	
	/**
	 * Get the position of the accompanying the text horizontally to the icon.
	 * 
	 * @return String
	 */
	protected String getHTextPosition() {
		return getString(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION);
	}	
	
	/**
	 * Get if the checkbox is checked.
	 * 
	 * @return boolean
	 */
	protected boolean getSelected() {
		boolean result = false;
		try {
			result = Boolean.valueOf(getString(PropertyTypeConstants.SELECTED));
		}catch (Exception ex) {
			return result;
		}
		return result;
	}
		
	/**
	 * Get the enabled value.
	 * 
	 * @return boolean The enabled value.
	 */
	protected boolean getEnabled() {
		boolean result = false;
		try {
			result = Boolean.valueOf(getString(PropertyTypeConstants.ENABLED));
		}catch (Exception ex) {
			return result;
		}
		return result;
	}
	
	/**
	 * Gets the image to be drawn representing the button.
	 * 
	 * @return Image The image to be drawn representing the button
	 */
	protected Image getButtonImage() {
		if (buttonImage == null) {
			buttonImage = createButtonImage();
		}
		return buttonImage;
	}
	
	/**
	 * Creates the image to be drawn representing the button.
	 * 
	 * @return Image The image to be drawn representing the button
	 */
	abstract protected Image createButtonImage();
	
	/**
	 * Reset the image to null if the icon has changed.
	 * 
	 * @param name
	 * 		The property name
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.SELECTED) || name.equals(PropertyTypeConstants.ENABLED)) {
			buttonImage = null;
		}
	}
	

}
package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * A figure representing a Button.
 * 
 * @author Gary Hayes
 */
public class Button extends AbstractAlignableWidgetFigure {
	
	/** The default Button image. */
	private static Image BUTTON = createImage("/icons/button.png");	 
	
	/** The width of the Button image. */
	private static final int BUTTON_IMAGE_WIDTH = 57;
	
	/** The height of the Button image. */
	private static final int BUTTON_IMAGE_HEIGHT = 16;
	
	/** The minimum width of the Button image. */
	private static final int BUTTON_IMAGE_MINIMUM_WIDTH = 25;	
	
	/** The width between the edge of the button and the label. */
	private static final int BUTTON_SPACING = 10;	
	/**
	 * Creates a new Button.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public Button(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}	
	
	/**
	 * @see com.odcgroup.page.ui.figure.AbstractPositionableWidgetFigure#preferenceChange()
	 */
	public void preferenceChange() {
		revalidate();
	}

	/**
	 * Gets the minimum width of the figure.
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
	int minWidth = calculateTextSize(getText()).width;
		
		minWidth += 2 * BUTTON_SPACING;
		if (minWidth < BUTTON_IMAGE_MINIMUM_WIDTH) {
			minWidth = BUTTON_IMAGE_MINIMUM_WIDTH;
		}
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
	 * Reset the image to null if the icon has changed.
	 * 
	 * @param name
	 * 		The property name
	 */
	public void afterPropertyChange(String name) {
	}	
	
	/**
	 * Paint the Button.
	 * 
	 * @param graphics The Graphics context
	 * @see Figure#paintFigure
	 */
	protected void paintSpecificFigure(Graphics graphics) {	
	    Widget root = getWidget().getParent();
	    int y = 0;
	    int x = 0;
	    boolean isTable =false;
	    while (root != null) {
		if (root.getTypeName().equals("TableColumn")) {
		    isTable = true;
		    break;
		}
		root = root.getParent();
	    }
	    if (PaintUtils.isWidgethAlignLead(getWidget()) && !isTable) {
		PaintUtils.paintIcons(getWidget(), graphics, getMinWidth() - PaintUtils.getWidth(getWidget()));
		drawStandardButton(graphics, x, y); 
	    } else {
		x = PaintUtils.paintIcons(getWidget(), graphics,0);
		if(isTable && x>0){
		    y = PaintUtils.IMAGE_HEIGHT;			
		    x = 0;
		    
		   }
		drawStandardButton(graphics, x, y); 
	    }
	   
	  
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	/**
	 * Paints the Button image.
	 * 
	 * @param graphics The Graphics context
	 */
	private void drawStandardButton(Graphics graphics, int x, int y) {	
	    	Rectangle source = new Rectangle();
		source.x = 0;
		source.y = 0;
		source.width = BUTTON_IMAGE_WIDTH;
		source.height = BUTTON_IMAGE_HEIGHT;
		
		Rectangle target = new Rectangle();
		target.x = x;
		target.y = y;
		int minWid = getMinWidth();
		if (PaintUtils.isWidgethAlignLead(getWidget())) {
		    minWid -= PaintUtils.getWidth(getWidget());
		}
		if(minWid!=0){
		target.width = minWid - 1;
		} else {
		    target.width =getMinWidth()-1;
		}
		target.height = BUTTON_IMAGE_HEIGHT;
		
		graphics.drawImage(BUTTON, source, target);
		graphics.drawText(getText(), BUTTON_SPACING + x, y);	
	}	
	
	/**
	 * Get the position of the accompanying the text horizontally to the icon.
	 * 
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String getHTextPosition() {
		return getString(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION);
	}
	
}

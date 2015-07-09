package com.odcgroup.page.ui.figure;		
import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * Represents a Calendar field.
 * 
 * @author Gary Hayes
 */
public class CaldateField extends AbstractAlignableWidgetFigure {
	
	/** The Calendar image. */
	private static final Image IMAGE = createImage("/icons/obj16/calendar.png"); //$NON-NLS-1$	
	/** The field minimum width*/
	private static final int CDATE_WIDTH = 100;

	/**
	 * Creates a new CaldateField.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public CaldateField(Widget widget, FigureContext figureContext) {
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
		
		int width = (int)(getColumns() * getFigureConstants().getColumnScalingFactor()) + getFigureConstants().getSimpleWidgetDefaultWidth();
		if (width > CDATE_WIDTH) {
			if(inTable) {
				Math.max(width, utilsWidth);
			}
			return width + utilsWidth;
		}
		if(inTable) {
			Math.max(CDATE_WIDTH, utilsWidth);
		}
		
		return CDATE_WIDTH + PaintUtils.getWidth(getWidget());
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
	 * Paints the CaldateField.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		Widget root = getWidget().getParent();
		int y = 0;
		int x = 0;
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		Rectangle b = getBounds();
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		while(root != null) {
		    if(root.getTypeName().equals("TableColumn")&& PaintUtils.getWidth(getWidget())!=0) {
			y = PaintUtils.IMAGE_HEIGHT;
			x = 0;
			break;
		    }
		    root = root.getParent();
		}
		int imageWidth=b.width -16;
		if(y==0){
		    if(PaintUtils.isWidgethAlignLead(getWidget())){
			PaintUtils.paintIcons(getWidget(), graphics,b.width - (PaintUtils.getWidth(getWidget())+16));
			imageWidth=b.width - (32+PaintUtils.getWidth(getWidget()));
		    }else {
			x=PaintUtils.paintIcons(getWidget(), graphics,0);
		    }
		    graphics.drawRectangle(new Rectangle(x, y, b.width - (PaintUtils.getWidth(getWidget()) + getFigureConstants().getSimpleWidgetDefaultHeight() + 1), b.height - 1));  
		}
		if(y>0){
		    PaintUtils.paintIcons(getWidget(), graphics,0);
		    graphics.drawRectangle(new Rectangle(x, y, b.width -  (getFigureConstants().getSimpleWidgetDefaultHeight() + 1), b.height - 1));
		}
		graphics.drawImage(IMAGE, imageWidth, y);
		String pv = getPreviewValue();
		if (! StringUtils.isEmpty(pv)) {
			graphics.drawText(pv, 1 + x,y);
		}
	}

	/**
	 * Gets the number of columns of the CaldateField.
	 * 
	 * @return int The number of columns of the CaldateField.
	 */
	public int getColumns() {
		return getInt(PropertyTypeConstants.COLUMNS);
	}	
}

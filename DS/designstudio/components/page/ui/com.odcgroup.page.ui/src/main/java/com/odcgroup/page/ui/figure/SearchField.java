package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * Represents a SearchField.
 * 
 * @author Gary Hayes
 */
public class SearchField extends AbstractAlignableWidgetFigure {
	
	/** The SearchField in the unchecked state. */
	private static final Image IMAGE = createImage("/icons/obj16/search.png"); //$NON-NLS-1$

	/**
	 * Creates a new SearchField.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public SearchField(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		FigureConstants fc = getFigureConstants();
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				return Math.max((int)(getColumns() * fc.getColumnScalingFactor()) + fc.getSimpleWidgetDefaultWidth(), PaintUtils.getWidth(getWidget()));
			}
			root = root.getParent();
		}
		if(PaintUtils.isWidgethAlignLead(getWidget())){
		    return (int)(getColumns() * fc.getColumnScalingFactor()) + fc.getSimpleWidgetDefaultWidth() + PaintUtils.getWidth(getWidget())+16;
		}
		return (int)(getColumns() * fc.getColumnScalingFactor()) + fc.getSimpleWidgetDefaultWidth() + PaintUtils.getWidth(getWidget());
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
	 * Paints the TextField.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
	    Widget root = getWidget().getParent();
		int y = 0;
		int x = 0;
		boolean isInsideTable =false;
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		Rectangle b = getBounds();
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		while(root != null) {
		    if(root.getTypeName().equals("TableColumn")&& PaintUtils.getWidth(getWidget())!=0) {
			isInsideTable=true;
			break;
		    }
		    root = root.getParent();
		}
		int imageWidth=b.width -14;
		if(isInsideTable && PaintUtils.getWidth(getWidget())>0 ){
		    x=0;
		    y=PaintUtils.IMAGE_HEIGHT;
		}
		if(!isInsideTable){
		    if(PaintUtils.isWidgethAlignLead(getWidget())){
			PaintUtils.paintIcons(getWidget(), graphics,b.width - (PaintUtils.getWidth(getWidget())));
			imageWidth=b.width - (16+PaintUtils.getWidth(getWidget()));
		    }else {
			x=PaintUtils.paintIcons(getWidget(), graphics,0);
		    }
		    graphics.drawRectangle(new Rectangle(x, y, b.width - (PaintUtils.getWidth(getWidget()) + getFigureConstants().getSimpleWidgetDefaultHeight() + 1), b.height - 1));  
		}
		if(isInsideTable && PaintUtils.getWidth(getWidget())>0){
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
	 * Gets the number of columns.
	 * 
	 * @return int The number of columns.
	 */
	public int getColumns() {
		return getInt(PropertyTypeConstants.COLUMNS);
	}
}

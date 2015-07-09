package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.grid.GridCellFigure;
import com.odcgroup.page.ui.figure.grid.GridCellLayoutUtil;

/**
 * A simple TextField.
 * 
 * @author Gary Hayes
 */
public class TextField extends AbstractAlignableWidgetFigure {

	/**
	 * Creates a new TextField.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public TextField(Widget widget, FigureContext figureContext) {
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
		int colwidth = (int)(getColumns() * getFigureConstants().getColumnScalingFactor());
		GridCellFigure gridCell = GridCellLayoutUtil.fetchParentGridCell(this);
		if (gridCell != null) {
			int gridCellWidth = gridCell.getBounds().width;
			int ret = GridCellLayoutUtil.getPossibleWidth(getFigureConstants(), 
					gridCell, getParent(), gridCellWidth, colwidth);
			int min = getFigureConstants().getSimpleWidgetDefaultWidth();
			if(inTable) {
				return Math.max(((ret > min) ? ret : min), PaintUtils.getWidth(getWidget()));
			}
			return ((ret > min) ? ret : min) + PaintUtils.getWidth(getWidget());
		}
		if(inTable) {
			return Math.max(colwidth, PaintUtils.getWidth(getWidget()));
		}
		return colwidth + PaintUtils.getWidth(getWidget());
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
		boolean insideTable =false;
		while(root != null) {
			if(root.getTypeName().equals("TableColumn")) {
			        insideTable =true;
				break;
			}
			root = root.getParent();
		}
		if(!PaintUtils.isWidgethAlignLead(getWidget())|insideTable){
		    x=PaintUtils.paintIcons(getWidget(), graphics,0);
		}
		if(insideTable && PaintUtils.getWidth(getWidget())>0){
		    x=0;
		    y=PaintUtils.IMAGE_HEIGHT;
		}
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(x, y, b.width-(PaintUtils.getWidth(getWidget())+1), b.height-(1+y)));
		if(PaintUtils.isWidgethAlignLead(getWidget()) && y==0){
		  PaintUtils.paintIcons(getWidget(), graphics,b.width+2-(PaintUtils.getWidth(getWidget())+1));
		}
		// The 1 serves to center the text in the rectangle
		String pv = getPreviewValue();
		if (! StringUtils.isEmpty(pv)) {
			graphics.drawText(pv, x+1, y);
		}
	}

	/**
	 * Get the number of columns of the TextField.
	 * 
	 * @return int The number of columns of the TextField.
	 */
	protected int getColumns() {
		return getInt(PropertyTypeConstants.COLUMNS);
	}

}
package com.odcgroup.page.ui.figure.matrix;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 *
 * @author pkk
 *
 */
public class MatrixContentCellItemFigure extends BoxFigure {
	

	/** The image for an aggregated column header. */
	private static Image AGGREGATE = createImage("/icons/obj16/cog.png"); //$NON-NLS-1$
	
	/** The border Color. */
	private Color borderColor;
	private static final String MATRIX_CONTENTCELL_ITEM_BORDER_COLOR = "MATRIX_CONTENTCELL_ITEM_BORDER_COLOR";

	/**
	 * @param widget
	 * @param figureContext
	 */
	public MatrixContentCellItemFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
	}
	
	/**
	 * @return
	 */
	public IMatrixContentCellItem getMatrixContentCellItem() {
		return MatrixHelper.getMatrixContentCellItem(getWidget());
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getHeight()
	 */
	public int getHeight() {
		return 20;
	}	
	
	/**
	 * Draw the caption.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {
		int x = 10;
		Rectangle b = getBounds();
		String caption = null;
		boolean domainBound = getMatrixContentCellItem().isDomainBound();
		if (domainBound) {
			caption =  getMatrixContentCellItem().getDomainAttribute().getValue();	
		} else {
			caption = getMatrixContentCellItem().getColumnName();
		}
		caption = getTrimmedCaption(caption);
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.drawText(caption, x, 2);		
		
		//if (domainBound) {
			String agg = getMatrixContentCellItem().getAggregationType().getValue();
			int size = calculateTextSize(agg, getFont()).width;
			graphics.setFont(getFont());
			PaintUtils.paintIcons(getWidget(), graphics,b.width-PaintUtils.getWidth(getWidget()));
			x = b.width - (10+size+PaintUtils.getWidth(getWidget()));
			graphics.drawText(agg, x, 2);
			x -= AGGREGATE.getBounds().width+2;
			graphics.drawImage(AGGREGATE, x, 2);
			
		//}
	}	
	
	/**
	 * @param caption
	 * @return
	 */
	private String getTrimmedCaption(String caption) {
		if (StringUtils.isEmpty(caption)) {
			return caption;
		}
		FigureConstants fc = getFigureConstants();
		String agg = getMatrixContentCellItem().getAggregationType().getValue();
		int size = calculateTextSize(agg, getFont()).width;
		size += AGGREGATE.getBounds().width+20;
		int availSize = getBounds().width - size;
		int textWidth = calculateTextSize(caption, fc.getCaptionFont(false)).width;
		if (textWidth > availSize) {
			int fraction = (int) Math.round((textWidth/caption.length()) + 0.5);
			int charPerLine = (int) Math.round((availSize/fraction) +0.5);
			if (charPerLine > 4) {
				caption = caption.substring(0, charPerLine -4);
				return caption.concat(" ...");
			}
			
		}
		return caption;
	}
	
	/**
	 * Draws a red border around the table (design mode only)
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void drawBorder(Graphics graphics) {
		if (getFigureContext().isDesignMode()) {
			Rectangle b = getBounds();
			FigureConstants fc = getFigureConstants();
			graphics.setLineStyle(fc.getFieldLineStyle());
			graphics.setForegroundColor(getBorderColor());
			// -1 ensures that the left and bottom parts are drawn
			graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
		}
	}
	
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(MATRIX_CONTENTCELL_ITEM_BORDER_COLOR);
		}
		return borderColor;
	}
	
	/**
	 * Sets the border color.
	 * 
	 * @param newBorderColor The border color to set
	 */
	protected void setBorderColor(Color newBorderColor) {
		this.borderColor = newBorderColor;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractWidgetFigure#dispose()
	 */
	public void dispose() {
		super.dispose();
	}	

	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		//do nothing
	}
        
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(MATRIX_CONTENTCELL_ITEM_BORDER_COLOR, new RGB(92, 127, 146));
	}
}

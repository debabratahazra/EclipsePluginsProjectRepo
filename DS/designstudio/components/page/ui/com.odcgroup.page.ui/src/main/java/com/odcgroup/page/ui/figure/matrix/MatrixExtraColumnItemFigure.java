package com.odcgroup.page.ui.figure.matrix;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.util.SWTUtils;

/**
 *
 * @author pkk
 *
 */
public class MatrixExtraColumnItemFigure extends BoxFigure {
	
	/** The image for an aggregated column header. */
	private static Image AGGREGATE = createImage("/icons/obj16/cog.png"); //$NON-NLS-1$
	
	/** The border Color. */
	private Color borderColor;
	private static final String MATRIX_EXTRA_COLUMNITEM_FIGURE_BORDER_COLOR = "MATRIX_EXTRA_COLUMNITEM_FIGURE_BORDER_COLOR";

	/**
	 * @param widget
	 * @param figureContext
	 */
	public MatrixExtraColumnItemFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
	}
	
	/**
	 * @return
	 */
	public IMatrixExtraColumnItem getMatrixExtraColumnItem() {
		return MatrixHelper.getMatrixExtraColumnItem(getWidget());
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
		Rectangle b = getBounds();
		FigureConstants fc = getFigureConstants();
		graphics.setBackgroundColor(fc.getCaptionBackgroundColor());
		graphics.fillRectangle(0, 0, b.width, b.y);
		
		decorateColumnHeader(graphics);		
	}	
	
	/**
	 * @param graphics
	 */
	private void decorateColumnHeader(Graphics graphics) {
		int x = 10;
		String domainAttrib = getMatrixExtraColumnItem().getDomainAttribute().getValue();	
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.drawText(getTrimmedCaption(domainAttrib), x, 2);
		org.eclipse.swt.graphics.Rectangle sr = SWTUtils.calculateSize(getFont(), domainAttrib);
		PaintUtils.paintIcons(getWidget(), graphics, x+sr.width);
		String agg = getMatrixExtraColumnItem().getAggregationType().getValue();
		graphics.setFont(getFont());
		x+=10;
		graphics.drawText(agg, x, 30);
		x -= AGGREGATE.getBounds().width+2;
		graphics.drawImage(AGGREGATE, x, 30);	
	}
	
	/**
	 * @param caption
	 * @return
	 */
	private String getTrimmedCaption(String caption) {
		FigureConstants fc = getFigureConstants();
		int availSize = getBounds().width;
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
			borderColor = PageUIPlugin.getColor(MATRIX_EXTRA_COLUMNITEM_FIGURE_BORDER_COLOR);
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
	private void initialize() {
	    PageUIPlugin.setColorInRegistry(MATRIX_EXTRA_COLUMNITEM_FIGURE_BORDER_COLOR, new RGB(92, 127, 146));
	}

}

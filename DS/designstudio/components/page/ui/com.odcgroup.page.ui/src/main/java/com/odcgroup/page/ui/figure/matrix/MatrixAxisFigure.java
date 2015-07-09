package com.odcgroup.page.ui.figure.matrix;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.grid.GridCellHorizontalBoxType;
import com.odcgroup.page.ui.util.SWTUtils;

/**
 *
 * @author pkk
 *
 */
public class MatrixAxisFigure extends BoxFigure {

	/**
	 * @param widget
	 * @param figureContext
	 */
	public MatrixAxisFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setYOffset(getFigureConstants().getSimpleWidgetDefaultHeight());
		initialize();
	}	

	
	/**
	 * @return
	 */
	public IMatrixAxis getMatrixAxis() {
		return MatrixHelper.getMatrixAxis(getWidget());
	}
	
	/**
	 * Draw the caption.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {
		String axisType = "";
		if (getMatrixAxis().isXAxis()) {
			axisType = "x-axis";
		} else {
			axisType = "y-axis";			
		}
		decorateAxisHeader(graphics, axisType);
		decorateAxisDomainAttribute(graphics);			
	}	
	
	/**
	 * @param graphics
	 * @param caption
	 */
	private void decorateAxisHeader(Graphics graphics, String caption) {	
	     int x=0;
		x += 5;
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.drawText(caption, x, 0);
		org.eclipse.swt.graphics.Rectangle sr = SWTUtils.calculateSize(getFont(), caption);
		PaintUtils.paintIcons(getWidget(), graphics, x+sr.width+4);
	}
	
	/**
	 * @param graphics
	 * @param caption
	 */
	private void decorateAxisDomainAttribute(Graphics graphics) {	
		String caption = getMatrixAxis().getDomainAttribute().getValue();	
		if (!StringUtils.isEmpty(caption)) {
			graphics.setBackgroundColor(getFigureConstants().getCaptionBackgroundColor());		
			Rectangle b = getBounds();
			graphics.fillRectangle(5, 15, b.width-10, getYOffset()+2);
			int x = 10;
			FigureConstants fc = getFigureConstants();
			graphics.setFont(fc.getCaptionFont(false));
			graphics.drawText(getTrimmedCaption(caption), x, 15);			
		}
	}
	
	/**
	 * @param caption
	 * @return
	 */
	private String getTrimmedCaption(String caption) {
		FigureConstants fc = getFigureConstants();
		String preview = "";
		int size = calculateTextSize(preview, getFont()).width;
		int availSize = getBounds().width-10 - size;
		int textWidth = calculateTextSize(caption, fc.getCaptionFont(false)).width;
		if (textWidth > availSize) {
			int fraction = (int) Math.round((textWidth/caption.length()) + 0.5);
			int charPerLine = (int) Math.round((availSize/fraction) +0.5);
			if (charPerLine > 5) {
				caption = caption.substring(0, charPerLine -5);
				return caption.concat(" ... ");
			}
			
		}
		return caption;
	}

	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		// Do nothing
	}
	
	/** The border Color. */
	private Color borderColor;
	private static final String MATRIX_AXIS_FIGURE_BORDER_COLOR = "MATRIX_AXIS_FIGURE_BORDER_COLOR";
	
	@Override
	public void dispose() {
		super.dispose();
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#hasXScrollBar()
	 */
	protected boolean hasXScrollBar() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#hasYScrollBar()
	 */
	protected boolean hasYScrollBar() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getHeight()
	 */
	public int getHeight() {
		return 100;
	}
	
	/**
	 * Gets the width of the box in pixels. If the width is expressed
	 * in percentage this returns 0.
	 * 
	 * @return int The width of the box in pixels
	 */
	public int getPixelWidth() {
		int width  = ((getParent().getBounds().width - 1) * 25 / 100);
		return width;
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
			graphics.setLineStyle(fc.getOutlineBorderLineStyle());
			// -1 ensures that the left and bottom parts are drawn
			graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
			
			graphics.setLineStyle(fc.getFieldLineStyle());
			graphics.setForegroundColor(getBorderColor());
			graphics.drawRectangle(5, 15, b.width -10, b.height -20);
		}
	}
	
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(MATRIX_AXIS_FIGURE_BORDER_COLOR);
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
	
	/*
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new GridCellHorizontalBoxType(this));
	}
       
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(MATRIX_AXIS_FIGURE_BORDER_COLOR, new RGB(0, 128, 255));
	}	
}

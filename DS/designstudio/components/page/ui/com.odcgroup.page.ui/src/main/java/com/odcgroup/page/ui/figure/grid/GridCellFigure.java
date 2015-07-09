package com.odcgroup.page.ui.figure.grid;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridCellFigure extends BoxFigure {
	
	/** The percentage character. */
	private static final String CHARACTER_PERCENT = PropertyTypeConstants.CHARACTER_PERCENT;

	/** The border Color. */
	private static final String GRDI_CELL_FIGURE_BORDER_COLOR = "GRDI_CELL_FIGURE_BORDER_COLOR" ;
	private  Color borderColor ; 
	
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
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getWidth()
	 */
	public String getWidth() {
		return getString("gridColumnWidth");
	}	
	
	/**
	 * Gets the width of the box in pixels. If the width is expressed
	 * in percentage this returns 0.
	 * 
	 * @return int The width of the box in pixels
	 */
	public int getPixelWidth() {
		int width = 0;		
		String w = getWidth();  // percentage
		try {
			width = Integer.parseInt(w);
		} catch(NumberFormatException nfe) {
			//;
		}
		int parentWidth = getParent().getBounds().width;
		if (parentWidth > 0) {
			int childCount = getParent().getChildren().size() -1;
			parentWidth = parentWidth - 8 * 2 * childCount;
		}
		width =  ((parentWidth * width) / 100);
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
			graphics.setLineStyle(fc.getFieldLineStyle());
			graphics.setForegroundColor(getBorderColor());
			// -1 ensures that the left and bottom parts are drawn
			graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
		}
	}

	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		// Do nothing
	}
	
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(GRDI_CELL_FIGURE_BORDER_COLOR);
		}
		return borderColor;
	}
	
	/**
	 * Sets the border color.
	 * 
	 * @param newBorderColor The border color to set
	 */
	protected void setBorderColor(Color newBorderColor) {
		if (this.borderColor != null && this.borderColor != newBorderColor) {
			this.borderColor.dispose();
		}
		this.borderColor = newBorderColor;
	}
	
	/*
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new GridCellHorizontalBoxType(this));
	}
	
	/**
	 * Creates a new GridCellFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public GridCellFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
	}
       /**
        * initialize method to register color.
        */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(GRDI_CELL_FIGURE_BORDER_COLOR, new RGB( 0, 128, 255));
        }
}

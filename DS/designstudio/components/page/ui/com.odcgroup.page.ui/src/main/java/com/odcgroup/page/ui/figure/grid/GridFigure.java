package com.odcgroup.page.ui.figure.grid;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridFigure extends BoxFigure {
	
	/** The border Color. */
	private Color borderColor;
	private static final String GRID_FIGURE_BORDER_COLOR = "GRID_FIGURE_BORDER_COLOR"; 
	/**
	 * @return
	 */
	protected Grid getGrid() {
		return new GridAdapter(getWidget());
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
		return "0";
	}
		
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(GRID_FIGURE_BORDER_COLOR);	
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
	
	/*
	 * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure, java.lang.Object, int)
	 */
	public void add(IFigure figure, Object constraint, int index) {
		if (constraint == null) {
			int cellStyle = GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL;
			constraint = new GridData(cellStyle);
		}
		super.add(figure, constraint, index);
	}
	
	public void afterPropertyChange(String name) {
		if (name.equals("gridColumnCount")) {
			int nbColumns = getGrid().getColumnCount();
			GridLayout layout = (GridLayout)getLayoutManager();
			if (nbColumns != layout.numColumns) {
				layout.numColumns = nbColumns;
				layout.invalidate();
			}
		}
	}	
	
	public void setBoxType(String newBoxType) {
		setBoxType(new GridBoxType(this));
	}
	
	/**
	 * Creates a new Grid.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public GridFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
	}	
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(GRID_FIGURE_BORDER_COLOR, new RGB(128, 0, 0));
	}
}

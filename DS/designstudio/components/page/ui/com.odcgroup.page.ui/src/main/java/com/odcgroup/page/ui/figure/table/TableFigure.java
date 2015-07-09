package com.odcgroup.page.ui.figure.table;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class TableFigure extends BoxFigure {
	
	/** The border Color. */
	private Color borderColor;
	private static final String TABLE_FIGURE_BORDER_COLOR = "TABLE_FIGURE_BORDER_COLOR";
	private static final String TABLE_FIGURE_INITIAL_BORDER_COLOR = "TABLE_FIGURE_INITIAL_BORDER_COLOR";
	
	/**
	 * @return table
	 */
	protected ITable getTable() {
		return TableHelper.getTable(getWidget());
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#hasXScrollBar()
	 */
	protected boolean hasXScrollBar() {
		return false;
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#hasYScrollBar()
	 */
	protected boolean hasYScrollBar() {
		return false;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getHeight()
	 */
	public int getHeight() {
		return 0;
	}
	
	/**
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
			borderColor = PageUIPlugin.getColor(TABLE_FIGURE_BORDER_COLOR);
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
		//if (getFigureContext().isDesignMode()) {
			Rectangle b = getBounds();
			FigureConstants fc = getFigureConstants();
			graphics.setLineStyle(fc.getFieldLineStyle());
			graphics.setForegroundColor(getBorderColor());
			// -1 ensures that the left and bottom parts are drawn
			graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
		//}
	}	
	
	/**
	 * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure, java.lang.Object, int)
	 */
	public void add(IFigure figure, Object constraint, int index) {
		if (constraint == null) {
			int cellStyle = GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL;
			constraint = new GridData(cellStyle);
		}
		super.add(figure, constraint, index);
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#afterPropertyChange(java.lang.String)
	 */
	public void afterPropertyChange(String name) {
		if (name.equals("table-column-count")) {
			int nbColumns = getTable().getColumnCount();
			GridLayout layout = (GridLayout)getLayoutManager();
			if (nbColumns != layout.numColumns) {
				layout.numColumns = nbColumns;
				layout.invalidate();
			}
		}
	}	
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new TableBoxType(this));
	}
	
	/**
	 * Creates a new Grid.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public TableFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
		if (figureContext.isDesignMode())
		    setBorderColor(PageUIPlugin.getColor(TABLE_FIGURE_INITIAL_BORDER_COLOR));
	}	
  
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(TABLE_FIGURE_INITIAL_BORDER_COLOR, new RGB(139, 69, 19));
	    PageUIPlugin.setColorInRegistry(TABLE_FIGURE_BORDER_COLOR, new RGB(92, 127, 146));
	}
}

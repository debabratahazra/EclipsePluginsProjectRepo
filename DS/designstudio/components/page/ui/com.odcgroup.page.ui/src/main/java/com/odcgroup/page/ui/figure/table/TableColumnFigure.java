package com.odcgroup.page.ui.figure.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.model.widgets.table.TableGroupRankSorter;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.AbstractBoxType;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.util.SWTUtils;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnFigure extends BoxFigure {

	private static int notifyCount = 1;
	/** The border Color. */
	private Color borderColor;
	private static final String TABLE_COLUMN_FIGURE_BORDER_COLOR = "TABLE_COLUMN_FIGURE_BORDER_COLOR";
	/** tablecolumn */
	private ITableColumn tableColumn;

	private CompartmentFigure groupingCompartment = null;

	/** The image for checkbox column header. */
	//private static Image CHECKED = createImage("/icons/obj16/checkboxon.png"); //$NON-NLS-1$
	/** The image for an aggregated column header. */
	private static Image AGGREGATE = createImage("/icons/obj16/sum.png"); //$NON-NLS-1$
	/** The image for an sorting(asc) column header. */
	private static Image ARROW_UP = createImage("/icons/obj16/arrow_up.png"); //$NON-NLS-1$
	/** The image for an sorting(desc) column header. */
	private static Image ARROW_DOWN = createImage("/icons/obj16/arrow_down.png"); //$NON-NLS-1$
	/** The image for computed column. */
	private static Image COMPUTED = createImage("/icons/obj16/cog.png"); //$NON-NLS-1$
	/** The image for dynamic column. */
	private static Image DYNAMIC = createImage("/icons/obj16/application_side_expand.png"); //$NON-NLS-1$

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
		return getBoxType().getMinHeight();
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getWidth()
	 */
	public String getWidth() {
		return getString("table-column-width");
	}

	/**
	 * Gets the width of the box in pixels. If the width is expressed in
	 * percentage this returns 0.
	 * 
	 * @return int The width of the box in pixels
	 */
	public int getPixelWidth() {
		int width = 0;
		String w = getWidth(); // percentage
		try {
			width = Integer.parseInt(w);
		} catch (NumberFormatException nfe) {
			// ;
		}
		return ((getParent().getBounds().width - 1) * width / 100);

	}

	@Override
	public void setText(String text) {
		super.setText(text);
		setYOffset(getFigureConstants().getSimpleWidgetDefaultHeight());
	}

	/**
	 * The panel's caption is always empty in preview mode.
	 * 
	 * @return String The caption
	 */
	public String getText() {
		String caption = "";
		if (getFigureContext().isPreviewMode()) {
			caption = super.getText();
			if (StringUtils.isEmpty(caption)) {
				caption = getProperty("column-name").getValue();
			}
		} else {
			caption = getProperty("column-name").getValue();
		}
		if (caption == null) {
			caption = "";
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
		// if (getFigureContext().isDesignMode()) {
		Rectangle b = getBounds();
		FigureConstants fc = getFigureConstants();
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.setForegroundColor(getBorderColor());
		// -1 ensures that the left and bottom parts are drawn
		graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
		// }
	}

	/**
	 * @see org.eclipse.draw2d.Figure#paintChildren(org.eclipse.draw2d.Graphics)
	 */
	protected void paintChildren(Graphics graphics) {
		IFigure child;
       	for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure) getChildren().get(i);
			if (child.isVisible()) {
				Rectangle bounds = child.getBounds();
				if (child instanceof CompartmentFigure) {
					bounds.y = getYOffset() + 25;
				}
				graphics.clipRect(bounds);
				child.paint(graphics);
				graphics.restoreState();
			}		
		}		
	}

	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		// do nothing
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#afterPropertyChange(java.lang.String)
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.COLUMN_VISIBILITY)) {
			setBackgroundColor(getColumnTypeIndicatorColor());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		AbstractBoxType boxType = null;
		if (PropertyTypeConstants.BOX_TYPE_HORIZONTAL.equals(newBoxType)) {
			boxType = new TableColumnBoxType(this);
			setLayoutManager(new TableColumnLayout(getFigureContext()));
		} 
		setBoxType(boxType);
		setBoxTypeName(newBoxType);
	}

	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(TABLE_COLUMN_FIGURE_BORDER_COLOR);
		}
		return borderColor;
	}

	/**
	 * Sets the border color.
	 * 
	 * @param newBorderColor
	 *            The border color to set
	 */
	protected void setBorderColor(Color newBorderColor) {
		this.borderColor = newBorderColor;
	}

	/**
	 * @return color
	 */
	private Color getColumnTypeIndicatorColor() {
		String val = getProperty("column-visibility").getValue();
		if (val.equals("never-visible")) {
			return ColorConstants.gray;
		} else if (val.equals("not-visible")) {
			return ColorConstants.lightGray;
		} else {
			return ColorConstants.white;
		}
	}

	/**
	 * Draw the caption.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {
		String text = getText();
		if (StringUtils.isEmpty(text)) {
			return;
		}
		FigureConstants fc = getFigureConstants();
		graphics.setBackgroundColor(fc.getCaptionBackgroundColor());

		Rectangle b = getBounds();
		graphics.fillRectangle(0, 0, b.width, getYOffset() + 2);

		decorateColumnHeader(graphics, text);
	}

	/**
	 * @param graphics
	 * @param caption
	 */
	private void decorateColumnHeader(Graphics graphics, String caption) {
		int x = 5;
		
		if (tableColumn.isPlaceHolder()) {
			caption = "[ ] " + caption;
			x = x + 7;
		} else if (tableColumn.isComputed()) {
			drawImage(graphics, COMPUTED, x);
			x = x + 26;
		} else if (tableColumn.isDynamic()) {
			drawImage(graphics, DYNAMIC, x);
			x = x + 26;
		}
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.drawText(caption, x, 0);
		org.eclipse.swt.graphics.Rectangle sr = SWTUtils.calculateSize(getFont(), caption);
		PaintUtils.paintIcons(getWidget(), graphics, x+sr.width);
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#paintClientArea(org.eclipse.draw2d.Graphics)
	 */
	protected void paintClientArea(Graphics graphics) {
		super.paintClientArea(graphics);
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.setForegroundColor(ColorConstants.black);
		Rectangle b = getBounds();
		graphics.translate(b.x, b.y);
		renderColumnAttributes(graphics);
		graphics.translate(-b.x, -b.y);
	}

	/**
	 * @return int
	 */
	public int getColumnItemY() {
		int y = 0;
		y = getYOffset() + 5;
		ITableSort sort = getSortingColumn();
		ITableAggregate aggregate = getAggregateColumn();
		if (sort != null || aggregate != null) {
			y = y + 20;
		}
		return y;
	}

	/**
	 * @param graphics
	 */
	private void renderColumnAttributes(Graphics graphics) {
		int loc_x = 10;
		int y = getYOffset() + 5;

		ITableSort sort = getSortingColumn();
		if (sort != null) {
			if (sort.isAscending()) {
				drawImage(graphics, ARROW_UP, loc_x, y);
			} else {
				drawImage(graphics, ARROW_DOWN, loc_x, y);
			}
			loc_x = loc_x + ARROW_DOWN.getBounds().width;
		}

		ITableAggregate aggregate = getAggregateColumn();
		if (aggregate != null) {
			drawImage(graphics, AGGREGATE, loc_x, y);
			loc_x = loc_x + AGGREGATE.getBounds().width;
			graphics.drawText(aggregate.getComputation(), loc_x, y);
			loc_x = loc_x + 50;
		}
	}
	
	/**
	 * @param groupName
	 * @return list
	 */
	@SuppressWarnings("unused")
	private List<String> getAggregatedColumnsByGroup(String groupName) {
		List<String> aggCols = new ArrayList<String>();
		List<ITableAggregate> aggregates = tableColumn.getTable()
				.getAggregatedColumns();
		for (ITableAggregate aggregate : aggregates) {
			for (String gName : aggregate.getGroupNames()) {
				if (groupName.equals(gName)) {
					aggCols.add(aggregate.getColumnName());
				}
			}
		}
		return aggCols;
	}

	/**
	 * @param graphics
	 * @param image
	 * @param x
	 */
	private void drawImage(Graphics graphics, Image image, int x) {
		drawImage(graphics, image, x + 5, 0);
	}

	/**
	 * @param graphics
	 * @param image
	 * @param x
	 * @param y
	 */
	private void drawImage(Graphics graphics, Image image, int x, int y) {
		graphics.drawImage(image, x, y);
	}

	/**
	 * @return boolean
	 */
	private ITableSort getSortingColumn() {
		List<ITableSort> tableAggregates = tableColumn.getTable().getSorts();
		for (ITableSort tableSort : tableAggregates) {
			if (tableColumn.getColumnName().equals(tableSort.getColumnName())) {
				return tableSort;
			}
		}
		return null;
	}

	/**
	 * @return ITableAggregate
	 */
	private ITableAggregate getAggregateColumn() {
		List<ITableAggregate> tableAggregates = tableColumn.getTable()
				.getAggregatedColumns();
		for (ITableAggregate tableAggregate : tableAggregates) {
			if (tableColumn.getColumnName().equals(
					tableAggregate.getColumnName())) {
				return tableAggregate;
			}
		}
		return null;
	}

	/**
	 * @return tableColumn
	 */
	protected final ITableColumn getTableColumn() {
		return tableColumn;
	}

	/**
	 * @param tableColumn
	 */
	private void setTableColumn(ITableColumn tableColumn) {
		this.tableColumn = tableColumn;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure, java.lang.Object, int)
	 */
	@SuppressWarnings("unchecked")
	public void add(IFigure figure, Object constraint, int index) {
		if (figure instanceof TableGroupFigure) {
			if(groupingCompartment == null) {
				 groupingCompartment = new CompartmentFigure(getWidget(), getFigureContext());
				 add(groupingCompartment);
			}
			int child = getOtherChildrenSize();
			if (child > 0) {
				index = index-child;
			}
			groupingCompartment.add(figure, constraint, index);
		} else {
			if(figure instanceof CompartmentFigure) {
				List<IFigure> list = super.getChildren();
				super.removeAll();
				super.add(figure,constraint, 0);
				int i = 1;
				for(IFigure fig : list) {
					super.add(fig,constraint, i++);
				}
			} else {
				super.add(figure, constraint, index);
			}
		}
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes") 
	private int getOtherChildrenSize() {
		List children = getChildren();
		int other = 0;
		for (Object object : children) {
			IFigure fig = (IFigure) object;
			if (!(fig instanceof CompartmentFigure)) {
				other++;
			}
		}
		return other;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#remove(org.eclipse.draw2d.IFigure)
	 */
	public void remove(IFigure figure) {
		if (figure instanceof TableGroupFigure) {
			groupingCompartment.remove(figure);
			if(groupingCompartment.getChildren().size() <= 0) {
				super.remove(groupingCompartment);
				groupingCompartment = null;
				setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
			}
		} else {
			super.remove(figure);
		}
	}

	/**
	 * Creates a new GridCellFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the
	 *            page correctly
	 */
	public TableColumnFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
		setTableColumn(TableHelper.getTableColumn(getWidget()));
		setBackgroundColor(getColumnTypeIndicatorColor());
		setYOffset(getFigureConstants().getSimpleWidgetDefaultHeight());
		initialize();
	}
	
	@Override
	public void notifyPropertyChange(String name) {
		Hashtable<ITableGroup, IWidgetFigure> map = new Hashtable<ITableGroup, IWidgetFigure>();
		if(name.equals("group-rank")) {
			if(notifyCount != 2) {
				notifyCount++;
			} else {
				List<ITableGroup> tabGroups = new ArrayList<ITableGroup>();
				notifyCount = 1;
				if(groupingCompartment != null) {
					List<IWidgetFigure> groups = groupingCompartment.getAllChildren();
					groupingCompartment.removeAll();
				
					for(int i=0;i<groups.size();i++) {
						Widget wid = groups.get(i).getWidget();		
						ITableGroup temp = TableHelper.getTableGroup(wid);
						tabGroups.add(temp);
						map.put(temp, groups.get(i));
					}
					Collections.sort(tabGroups, new TableGroupRankSorter());
				}
		        
				for(int i=0;i<tabGroups.size();i++) {
					if(groupingCompartment == null) {
						groupingCompartment = new CompartmentFigure(getWidget(), getFigureContext());
						add(groupingCompartment);
					}
					groupingCompartment.add(map.get(tabGroups.get(i)));
				}
			}
		}else {
			super.notifyPropertyChange(name);
		}
	}
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(TABLE_COLUMN_FIGURE_BORDER_COLOR, new RGB(204, 0, 204));
	}
	
}

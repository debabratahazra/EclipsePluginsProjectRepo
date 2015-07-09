package com.odcgroup.page.model.widgets.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.model.widgets.GridRow;

/**
 * Helper class for Grid Widget
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridAdapter implements Grid {

	private static final String GRID_COLUMNS_WIDTH = "gridColumnsWidth";
	private static final String GRID_COLUMN_WIDTH = "gridColumnWidth";
	private static final String GRID_COLUMN_CSS_CLASS = "gridColumnCssClass";
	private static final String GRID_COLUMN_COUNT = "gridColumnCount";
	private static final String GRID_ROW_COUNT = "gridRowCount";
	private static final String GRID_CELL_LIBRARY = WidgetLibraryConstants.XGUI;
	private static final String GRID_CELL_TEMPLATE = "GridCell";
	private static final String COLUMN_ITEM = "column";
	private static final String ROW_ITEM = "row";
	private static final String COLUMN_SEPARATOR = ",";

	// the wrapped widget
	private Widget gridWidget;

	protected int doIncrementCount(String item, int incr) {
		int count = 0;
		Property prop = getGridWidget().findProperty(item);
		if (prop != null) {
			count = prop.getIntValue() + incr;
			prop.setValue(count);
		}
		return count;
	}
	
	private final int incrRowCount(int incr) {
		return doIncrementCount(GRID_ROW_COUNT, incr);
	}

	private final int incrColumnCount(int incr) {
		return doIncrementCount(GRID_COLUMN_COUNT, incr);
	}
	
	private final void doSetColumnCount(int value) {
		getGridWidget().setPropertyValue(GRID_COLUMN_COUNT, value+"");
	}
	
	private void checkIndex(String item, int upperBound, int index) {
		if ((index < 0) || (index >= upperBound)) {
			StringBuilder builder = new StringBuilder();
			builder.append("The ");
			builder.append(item);
			builder.append(" index: ");
			builder.append(index);
			builder.append(" is not in the range [0,");
			builder.append(upperBound);
			builder.append("]");
			throw new IndexOutOfBoundsException(builder.toString());
		}
	}

	protected final Widget getGridWidget() {
		return this.gridWidget;
	}

	protected final void checkRowIndex(int index) {
		checkIndex(ROW_ITEM, getRowCount(), index);
	}

	protected final void checkColumnIndex(int index) {
		checkIndex(COLUMN_ITEM, getColumnCount(), index);
	}
	
	protected void updateColumnsWidth() {
		int nbColumns = getColumnCount();
		int nbRows = getRowCount();
		if (nbRows <= 0) {
			return;
		}
		Property prop = getGridWidget().findProperty(GRID_COLUMNS_WIDTH);
		StringBuffer widths = new StringBuffer();
		for (int cx = 0; cx < nbColumns; cx++) {
			int pos = getPosition(0, cx);
			Widget cell = getGridWidget().getContents().get(pos);
			if (cx > 0) {
				widths.append(COLUMN_SEPARATOR);
			}
			widths.append(cell.getPropertyValue(GRID_COLUMN_WIDTH));
		}
		prop.setValue(widths.toString());		
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#getPosition(int, int)
	 */
	public int getPosition(int rowIndex, int columnIndex) {
		return getColumnCount() * rowIndex + columnIndex;
	}
	
	/* 
	 * @see com.odcgroup.page.model.widgets.Grid#getColumnWidth(int)
	 */
	public int getColumnWidth(int columnIndex) {
		checkColumnIndex(columnIndex); 
		int width = 0;
		Property prop = getGridWidget().findProperty(GRID_COLUMNS_WIDTH);
		if (prop != null) {
			String[] widths = StringUtils.split(prop.getValue(), COLUMN_SEPARATOR);
			width = Integer.parseInt(widths[columnIndex]);
		}
		return width;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.Grid#setColumnWidth(int, int)
	 */
	public void setColumnWidth(int columnIndex, int width) {
		checkColumnIndex(columnIndex); 		
		int nbRows = getRowCount();
		for (int rx = 0; rx < nbRows; rx++) {
			int pos = getPosition(rx, columnIndex);
			Widget cell = getGridWidget().getContents().get(pos);
			cell.setPropertyValue(GRID_COLUMN_WIDTH, width+"");
		}
		updateColumnsWidth();
	}


	/*
	 * @see com.odcgroup.page.model.widgets.Grid#getColumnCssClass(int)
	 */
	public String getColumnCssClass(int columnIndex) {
		checkColumnIndex(columnIndex);
		String value = "";
		if (getRowCount() > 0) {
			int pos = getPosition(0, columnIndex);
			Widget cell = getGridWidget().getContents().get(pos);
			value = cell.getPropertyValue(GRID_COLUMN_CSS_CLASS);
			if (value == null) {
				value = "";
			}
		}
		return value;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#setColumnCssClass(int, java.lang.String)
	 */
	public void setColumnCssClass(int columnIndex, String value) {
		checkColumnIndex(columnIndex); 
		int nbRows = getRowCount();
		for (int rx = 0; rx < nbRows; rx++) {
			int pos = getPosition(rx, columnIndex);
			Widget cell = getGridWidget().getContents().get(pos);
			cell.setPropertyValue(GRID_COLUMN_CSS_CLASS, value);
		}
	}	
	
	/*
	 * @see com.odcgroup.page.model.widgets.Grid#getColumn(int)
	 */
	public GridColumn getColumn(int columnIndex) {
		checkColumnIndex(columnIndex);
		return new GridColumnAdapter(getGridWidget(), columnIndex);
	}
	
	/* 
	 * @see com.odcgroup.page.model.widgets.Grid#setColumnCount(int)
	 */
	public List<List<Widget>> setColumnCount(int newColumnCount) {
		List<List<Widget>> deletedColumns = new ArrayList<List<Widget>>();
		int delta = newColumnCount - getColumnCount();
		if (delta > 0) {
			// add new columns
			for (int kx = 0; kx < delta; kx++) {
				insertColumn();
			}
		} else if (delta < 0) {
			// remove columns at columnIndex
			int columnIndex = getColumnCount() + delta;
			for (int kx = 0; kx < -delta; kx++) {
				deletedColumns.add(removeColumn(columnIndex));
			}
		}
		return deletedColumns;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#getColumnCount()
	 */
	public int getColumnCount() {
		int count = 0;
		Property prop = getGridWidget().findProperty(GRID_COLUMN_COUNT);
		if (prop != null) {
			count = prop.getIntValue();
		}
		return count;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#getRow(int)
	 */
	public GridRow getRow(int rowIndex) {
		checkRowIndex(rowIndex);
		return new GridRowAdapter(getGridWidget(), rowIndex);
	}

	/* 
	 * @see com.odcgroup.page.model.widgets.Grid#setRowCount(int)
	 */
	public List<List<Widget>> setRowCount(int newRowCount) {
		List<List<Widget>> deletedRows = new ArrayList<List<Widget>>();
		int delta = newRowCount - getRowCount();
		if (delta > 0) {
			// add new rows
			for (int kx = 0; kx < delta; kx++) {
				insertRow();
			}
		} else if (delta < 0) {
			// remove rows at rowIndex
			int rowIndex = getRowCount() + delta;
			for (int kx = 0; kx < -delta; kx++) {
				deletedRows.add(removeRow(rowIndex));
			}
		}
		return deletedRows;
	}
	
	/*
	 * @see com.odcgroup.page.model.widgets.Grid#getRowCount()
	 */
	public int getRowCount() {
		int count = 0;
		Property prop = getGridWidget().findProperty(GRID_ROW_COUNT);
		if (prop != null) {
			count = prop.getIntValue();
		}
		return count;
	}
	
	protected void doInsertColumnAt(int columnIndex) {
		// check the give row index (insertion index)
		int nbColumns = getColumnCount();
		if (columnIndex < 0 || columnIndex > nbColumns) {
			throw new IllegalArgumentException("The column index ["+columnIndex+"] is not valid.");
		}
		
		// if the grid has no columns we add one.
		int nbRows = getRowCount();
		if (nbRows == 0) {
			nbRows = incrRowCount(+1);
		}
		
		/*
		 * 1. We use a script to create a collection of GridCell widgets.
		 */
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(GRID_CELL_LIBRARY);
		WidgetTemplate template = library.findWidgetTemplate(GRID_CELL_TEMPLATE);
		WidgetFactory factory = new WidgetFactory();
			
		/*
		 * 2. create the collection of widget from the template As the cells
		 * are stored by row, the cell are just added at the end of the grid
		 * widget content
		 */
		int pos = getPosition(nbRows - 1, columnIndex);
		List<Widget> contents = getGridWidget().getContents();
		for (int rx = 0; rx < nbRows; rx++) {
			contents.add(pos,factory.create(template));
			pos -= nbColumns;
		}
		incrColumnCount(+1);
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertColumnAt(int)
	 */
	public void insertColumnAt(int columnIndex) {
		doInsertColumnAt(columnIndex);
		setColumnWidth(columnIndex, 5);
	}
	
	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertColumn()
	 */
	public int insertColumn() {
		int nbColumns = getColumnCount();
		insertColumnAt(nbColumns);
		return nbColumns;
	}

	public void doInsertColumnAt(int columnIndex, List<Widget> cells, boolean updateColumnsWidth) {
		int nbColumns = getColumnCount();
		if ((columnIndex >= 0) && (columnIndex <= nbColumns)) {
			if (cells != null && cells.size() > 0) {
				int nbRows = getRowCount();

				// if the grid has no columns we add one.
				if (nbRows == 0) {
					nbRows = incrRowCount(cells.size());
				}
				for (int rx = nbRows-1; rx >= 0; rx--) {
					int pos = getPosition(rx, columnIndex);
					getGridWidget().getContents().add(pos, cells.get(rx));
				}
				incrColumnCount(+1);
				if (updateColumnsWidth) {
					updateColumnsWidth();
				}
			}
		} else {
			throw new IllegalArgumentException("column index ("+columnIndex+") is invalid");
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertColumnAt(int, java.util.List)
	 */
	public void insertColumnAt(int columnIndex, List<Widget> cells) {
		doInsertColumnAt(columnIndex, cells, true);
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertColumnAfter(int)
	 */
	public void insertColumnRight(int columnIndex) {
		insertColumnAt(columnIndex+1);
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertColumnBefore(int)
	 */
	public void insertColumnLeft(int columnIndex) {
		insertColumnAt(columnIndex);
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertRow()
	 */
	public int insertRow() {
		int nbRows = getRowCount(); 
		insertRowAt(nbRows);
		return nbRows;
	}
	
	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertRowAt(int)
	 */
	public void insertRowAt(int rowIndex) {
		
		// check the give row index (insertion index)
		int nbRows = getRowCount();
		if (rowIndex < 0 || rowIndex > nbRows) {
			throw new IllegalArgumentException("The row index ["+rowIndex+"] is not valid.");
		}
		
		// if the grid has no columns we add one.
		int nbColumns = getColumnCount();
		if (nbColumns == 0) {
			nbColumns = incrColumnCount(+1);
			getGridWidget().setPropertyValue(GRID_COLUMNS_WIDTH, "100");			
		}
		
		// TODO in order to be decoupled from the meta model, The operation
		// is bound to this script externalized !!
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(GRID_CELL_LIBRARY);
		WidgetTemplate template = library.findWidgetTemplate(GRID_CELL_TEMPLATE);
		WidgetFactory factory = new WidgetFactory();

		/*
		 * 2. create the collection of widget from the template As the cells
		 * are stored by row, the cell are just added at the end of the grid
		 * widget content
		 */
		List<Widget> contents = getGridWidget().getContents();
		int pos = getPosition(rowIndex, 0);
		for (int cx = 0; cx < nbColumns; cx++) {
			Widget cell = factory.create(template);
			cell.setPropertyValue(GRID_COLUMN_WIDTH, getColumnWidth(cx)+"");
			cell.setPropertyValue(GRID_COLUMN_CSS_CLASS, getColumnCssClass(cx));
			contents.add(pos++, cell);
		}
		
		incrRowCount(+1);
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertRowAt(int, java.util.List)
	 */
	public void insertRowAt(int rowIndex, List<Widget> cells) {
		int nbRows = getRowCount();
		if ((rowIndex >= 0) && (rowIndex <= nbRows)) {
			if (cells != null && cells.size() > 0) {
				int pos = getPosition(rowIndex, 0);
				getGridWidget().getContents().addAll(pos, cells);
				incrRowCount(+1);
				if (nbRows == 0) {
					doSetColumnCount(cells.size());
					updateColumnsWidth();
				}
			}
		} else {
			throw new IllegalArgumentException("row index ("+rowIndex+") is invalid");
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertRowAfter(int)
	 */
	public void insertRowBelow(int rowIndex) {
		insertRowAt(rowIndex+1);	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#insertRowBefore(int)
	 */
	public void insertRowAbove(int rowIndex) {
		insertRowAt(rowIndex);
	}
	
	protected List<Widget> doRemoveColumn(int columnIndex) {
		checkColumnIndex(columnIndex);
		List<Widget> deletedCells = new ArrayList<Widget>();
		int nbColumns = getColumnCount();
		int nbRows = getRowCount();
		if (nbRows > 0) {
			int pos = getPosition(nbRows-1, columnIndex);
			for (int rx = 0; rx < nbRows; rx++) {
				deletedCells.add(0,getGridWidget().getContents().remove(pos));
				pos -= nbColumns;
			}
			incrColumnCount(-1);
			if (getColumnCount() == 0) {
				incrRowCount( - getRowCount());
			}
		}
		return deletedCells;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#removeColumn(int)
	 */
	public List<Widget> removeColumn(int columnIndex) {
		List<Widget> deletedCells = doRemoveColumn(columnIndex);
		updateColumnsWidth();
		return deletedCells;
	}
	
	/*
	 * @see com.odcgroup.page.model.widgets.Grid#removeRow(int)
	 */
	public List<Widget> removeRow(int rowIndex) {
		checkRowIndex(rowIndex);
		List<Widget> deletedCells = new ArrayList<Widget>();
		int nbColumns = getColumnCount();
		if (nbColumns > 0) {
			int pos = getPosition(rowIndex, nbColumns-1);
			for (int cx = 0; cx < nbColumns; cx++) {
				deletedCells.add(0,getGridWidget().getContents().remove(pos--));
			}
			incrRowCount(-1);
			if (getRowCount() == 0) {
				doSetColumnCount(0);
				getGridWidget().setPropertyValue(GRID_COLUMNS_WIDTH, "");					
			}
		}
		return deletedCells;
	}
	
	public List<Widget> removeRow() {
		int lastRowIndex = getRowCount()-1;
		return removeRow(lastRowIndex);
	}
	
	protected void swapCell(int leftPosition, int rightPosition) {
		List<Widget> cells = getGridWidget().getContents();
		Widget srcWidget = cells.get(leftPosition);
		cells.set(leftPosition, cells.remove(rightPosition));
		cells.add(rightPosition, srcWidget);
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveCellDown(int, int)
	 */
	public void moveCellDown(int rowIndex, int columnIndex) {
		int srcPos = getPosition(rowIndex, columnIndex);
		int dstPos = getPosition(rowIndex+1, columnIndex);
		if (srcPos < dstPos) {
			swapCell(srcPos, dstPos);
		} else if (srcPos > dstPos) {
			swapCell(dstPos, srcPos);
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveCellLeft(int, int)
	 */
	public void moveCellLeft(int rowIndex, int columnIndex) {
		int srcPos = getPosition(rowIndex, columnIndex-1);
		int dstPos = getPosition(rowIndex, columnIndex);
		if (srcPos < dstPos) {
			swapCell(srcPos, dstPos);
		} else if (srcPos > dstPos) {
			swapCell(dstPos, srcPos);
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveCellRight(int, int)
	 */
	public void moveCellRight(int rowIndex, int columnIndex) {
		int srcPos = getPosition(rowIndex, columnIndex);
		int dstPos = getPosition(rowIndex, columnIndex+1);
		if (srcPos < dstPos) {
			swapCell(srcPos, dstPos);
		} else if (srcPos > dstPos) {
			swapCell(dstPos, srcPos);
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveCellUp(int, int)
	 */
	public void moveCellUp(int rowIndex, int columnIndex) {
		int srcPos = getPosition(rowIndex, columnIndex);
		int dstPos = getPosition(rowIndex-1, columnIndex);
		if (srcPos < dstPos) {
			swapCell(srcPos, dstPos);
		} else if (srcPos > dstPos) {
			swapCell(dstPos, srcPos);
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveColumnLeft(int)
	 */
	public void moveColumnLeft(int columnIndex) {
		if (columnIndex > 0) {
			List<Widget> cells = doRemoveColumn(columnIndex);
			doInsertColumnAt(columnIndex-1, cells, false);
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveColumnRight(int)
	 */
	public void moveColumnRight(int columnIndex) {
		if (columnIndex < getColumnCount()) {
			List<Widget> cells = doRemoveColumn(columnIndex);
			doInsertColumnAt(columnIndex+1, cells, false);
		}
	}			
	
	
	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveRowDown(int)
	 */
	public void moveRowDown(int rowIndex) {
		if (rowIndex < getRowCount()) {
			List<Widget> cells = removeRow(rowIndex);
			insertRowAt(rowIndex+1, cells);
		}
	}

	/*
	 * @see com.odcgroup.page.model.widgets.Grid#moveRowUp(int)
	 */
	public void moveRowUp(int rowIndex) {
		if (rowIndex > 0) {
			List<Widget> cells = removeRow(rowIndex);
			insertRowAt(rowIndex-1, cells);
		}
	}	

	/* 
	 * @see com.odcgroup.page.model.widgets.Grid#getCellWidget(int, int)
	 */
	public Widget getCellWidgetAt(int rowIndex, int columnIndex) {
		checkRowIndex(rowIndex);
		checkColumnIndex(columnIndex);
		int pos = getPosition(rowIndex, columnIndex);
		return getGridWidget().getContents().get(pos);
	}


	/**
	 * @param gridWidget
	 */
	public GridAdapter(Widget gridWidget) {
		this.gridWidget = gridWidget;
	}


}

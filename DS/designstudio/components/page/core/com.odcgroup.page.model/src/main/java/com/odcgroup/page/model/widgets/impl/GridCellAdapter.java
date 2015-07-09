package com.odcgroup.page.model.widgets.impl;

import java.util.List;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.model.widgets.GridRow;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridCellAdapter implements GridCell {
	
	private Widget cellWidget;
	
	protected final Widget getCellWidget() {
		return this.cellWidget;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#canMoveDown()
	 */
	public final boolean canMoveDown() {
		GridRow row = getRow();
		return (row != null) && (! row.isLast());
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#canMoveLeft()
	 */
	public final boolean canMoveLeft() {
		GridColumn column = getColumn();
		return (column != null) && (! column.isFirst());
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#canMoveRight()
	 */
	public final boolean canMoveRight() {
		GridColumn column = getColumn();
		return (column != null) && (! column.isLast());
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#canMoveUp()
	 */
	public final boolean canMoveUp() {
		GridRow row = getRow();
		return (row != null) && (! row.isFirst());
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#getColumn()
	 */
	public GridColumn getColumn() {
		GridColumn column = null;
		Widget gridWidget = getCellWidget().getParent();
		if (gridWidget != null) {
			int pos = gridWidget.getContents().indexOf(getCellWidget());
			Grid grid = getGrid();
			int columnIndex = pos % grid.getColumnCount();
			column = grid.getColumn(columnIndex);
		}
		return column;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#getColumnIndex()
	 */
	public final int getColumnIndex() {
		return getColumn().getIndex();
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#getGrid()
	 */
	public Grid getGrid() {
		return new GridAdapter(getCellWidget().getParent());
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#getRow()
	 */
	public GridRow getRow() {
		GridRow row = null;
		Widget gridWidget = getCellWidget().getParent();
		if (gridWidget != null) {
			int pos = gridWidget.getContents().indexOf(getCellWidget());
			Grid grid = getGrid();
			int rowIndex = pos / grid.getColumnCount();
			row = grid.getRow(rowIndex);
		}
		return row;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridCell#getRowIndex()
	 */
	public final int getRowIndex() {
		return getRow().getIndex();
	}

	/* 
	 * @see com.odcgroup.page.model.widgets.GridCell#getContents()
	 */
	public List<Widget> getContents() {
		return getCellWidget().getContents();
	}
	
	/**
	 * @param cellWidget
	 */
	public GridCellAdapter(Widget cellWidget) {
		this.cellWidget = cellWidget;
	}


}

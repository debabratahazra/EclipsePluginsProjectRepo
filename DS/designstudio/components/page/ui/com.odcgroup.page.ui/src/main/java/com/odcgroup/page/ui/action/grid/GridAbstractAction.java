package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.model.widgets.GridRow;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.model.widgets.impl.GridCellAdapter;
import com.odcgroup.page.ui.action.AbstractGenericAction;
import com.odcgroup.page.ui.action.ActionParameters;

/**
 * @author atr
 * @since DS 1.40.0
 */
class GridAbstractAction extends AbstractGenericAction {

	/**
	 * @return
	 */
	protected GridCell getCell() {
		GridCell cell = null;
		Widget widget = getSelectedWidget();
		if (widget.getTypeName().equals("GridCell")) {
			cell = new GridCellAdapter(widget);
		}
		return cell;
	}
	

	/**
	 * @return
	 */
	protected GridColumn getColumn() {
		GridColumn column = null;
		GridCell cell = getCell();
		if (cell != null) {
			column = cell.getColumn();
		}
		return column;
	}
	
	/**
	 * @return
	 */
	protected GridRow getRow() {
		GridRow row = null;
		GridCell cell = getCell();
		if (cell != null) {
			row = cell.getRow();
		}
		return row;
	}
	
	/**
	 * @return
	 */
	protected Grid getGrid() {
		Grid grid = null;
		GridCell cell = getCell();
		if (cell != null) {
			grid = cell.getGrid();
		} else {
			Widget widget = getSelectedWidget();
			if (widget.getTypeName().equals("Grid")) {
				grid = new GridAdapter(widget);
			}
		}
		return grid;
	}
	
	/**
	 * @param parameters
	 */
	protected GridAbstractAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}

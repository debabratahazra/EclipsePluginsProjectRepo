package com.odcgroup.page.model.widgets;

import java.util.List;

import com.odcgroup.page.model.Widget;

/**
 * @author atr
 * @since DS 1.40.0
 */
public interface GridCell {

	Grid getGrid();

	GridRow getRow();

	GridColumn getColumn();

	int getRowIndex();

	int getColumnIndex();

	boolean canMoveUp();

	boolean canMoveDown();

	boolean canMoveLeft();

	boolean canMoveRight();

	List<Widget> getContents();

}

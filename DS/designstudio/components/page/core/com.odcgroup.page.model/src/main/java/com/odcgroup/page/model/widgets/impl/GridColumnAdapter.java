package com.odcgroup.page.model.widgets.impl;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.GridColumn;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridColumnAdapter extends AbstractGridItem implements GridColumn {

	/*
	 * @see com.odcgroup.page.model.widgets.impl.AbstractGridItem#isLast()
	 */
	public boolean isLast() {
		return getIndex() == (getGrid().getColumnCount() - 1);
	}
	
	/*
	 * @see com.odcgroup.page.model.widgets.GridColumn#canMoveLeft()
	 */
	public final boolean canMoveLeft() {
		return ! isFirst();
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridColumn#canMoveRight()
	 */
	public final boolean canMoveRight() {
		return ! isLast();
	}

	/**
	 * @param widget
	 */
	GridColumnAdapter(Widget widget, int index) {
		super(widget, index);
	}

}

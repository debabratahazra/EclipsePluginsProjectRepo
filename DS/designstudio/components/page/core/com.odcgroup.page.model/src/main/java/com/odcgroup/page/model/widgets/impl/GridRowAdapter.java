package com.odcgroup.page.model.widgets.impl;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.GridRow;

/**
 * @author atr
 * @since DS 1.40.0
 */ 
public class GridRowAdapter extends AbstractGridItem implements GridRow {

	/*
	 * @see com.odcgroup.page.model.widgets.impl.AbstractGridItem#isLast()
	 */
	public boolean isLast() {
		return getIndex() == (getGrid().getRowCount() - 1);
	}
	
	/*
	 * @see com.odcgroup.page.model.widgets.GridRow#canMoveDown()
	 */
	public final boolean canMoveDown() {
		return ! isLast();
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridRow#canMoveUp()
	 */
	public final boolean canMoveUp() {
		return ! isFirst();
	}

	/**
	 * @param widget
	 */
	GridRowAdapter(Widget widget, int index) {
		super(widget, index);
	}

}

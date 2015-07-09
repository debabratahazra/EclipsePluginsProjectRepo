package com.odcgroup.page.model.widgets.impl;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridItem;

/**
 * @author atr
 * @since DS 1.40.0
 */
abstract class AbstractGridItem implements GridItem {
	
	private Widget gridWidget;
	private int index;
	
	/**
	 * @return
	 */
	protected final Widget getGridWidget() {
		return gridWidget;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridItem#getGrid()
	 */
	public final Grid getGrid() {
		return new GridAdapter(getGridWidget());
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridItem#getIndex()
	 */
	public final int getIndex() {
		return index;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridItem#isFirst()
	 */
	public boolean isFirst() {
		return getIndex() == 0;
	}

	/*
	 * @see com.odcgroup.page.model.widgets.GridItem#isLast()
	 */
	public abstract boolean isLast();

	/**
	 * @param widget
	 */
	protected AbstractGridItem(Widget gridWidget, int index) {
		this.gridWidget = gridWidget;
		this.index = index;
	}


}

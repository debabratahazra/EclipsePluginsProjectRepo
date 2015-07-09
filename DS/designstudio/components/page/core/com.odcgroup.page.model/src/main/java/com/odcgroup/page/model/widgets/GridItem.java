package com.odcgroup.page.model.widgets;

/**
 * This is the common interface for {@link GridRow} and {@link GridColumn}
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface GridItem {

	Grid getGrid();

	int getIndex();

	boolean isLast();

	boolean isFirst();

}

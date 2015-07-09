package com.odcgroup.page.ui.figure;

/**
 * An interface for a WidgetFiguer which iterates over the paint method.
 * An example is the ColumnBody. The contents of a ColumnBody are displayed
 * n times where n is the value of the PageSize property. The current index
 * is used to allow child Widgets to know the current iteration.
 * 
 * @author Gary Hayes
 */
public interface PaintIterator {

	/**
	 * Gets the current index.
	 * 
	 * @return int The current index
	 */
	public int getCurrentIndex();
}

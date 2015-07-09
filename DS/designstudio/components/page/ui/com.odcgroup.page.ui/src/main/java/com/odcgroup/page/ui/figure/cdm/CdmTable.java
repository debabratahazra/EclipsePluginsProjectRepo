package com.odcgroup.page.ui.figure.cdm;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.table.Table;

/**
 * The figure for CdmTables.
 * 
 * A Table consists of a HeaderFigure and a BoxFigure. The BoxFigure contains the Columns, ColumnHeader's and
 * ColumnBody's.
 * 
 * @author Gary Hayes
 */
public class CdmTable extends Table {

	/**
	 * Creates a new TableFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public CdmTable(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Gets the name of the WidgetType which is used by the HeaderFigure to 
	 * display itself.
	 * 
	 * @return String 
	 */
	protected String getHeaderFigureWidgetType() {
		return WidgetTypeConstants.CDM_COLUMN;
	}	
}
	

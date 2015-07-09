package com.odcgroup.page.ui.command.table;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITable;

/**
 * This command inserts a new column in the table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableInsertColumnWidgetCommand extends AbstractTableColumnCommand {
	/** */
	private Widget widget;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().insertColumnAt(getColumnIndex(),widget);
		String domainAttrb = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		if (!StringUtils.isEmpty(domainAttrb)) {
			widget.setID(domainAttrb);
			Widget itemWidget = widget.getContents().get(0);
			itemWidget.setPropertyValue("item-column", domainAttrb);
		} 
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().removeColumn(getColumnIndex());
	}

	/**
	 * @param table
	 * @param widget 
	 * @param index 
	 */
	public TableInsertColumnWidgetCommand(ITable table, Widget widget, int index) {
		super(table);
		setColumnIndex(index);
		this.widget = widget;
	}

}

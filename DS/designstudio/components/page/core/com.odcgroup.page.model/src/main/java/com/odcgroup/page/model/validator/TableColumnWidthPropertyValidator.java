package com.odcgroup.page.model.validator;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnWidthPropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/*
	 * @see com.odcgroup.page.model.validator.PropertyValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		Property p = getProperty();
		if (p != null) {
			int width = Integer.parseInt((String)value);
			ITableColumn column = TableHelper.getTableColumn(p.getWidget());
			int myColIndex = column.getColumnIndex();
			ITable table = column.getTable();
			int totalWidths = width;
			int nbColumns = table.getColumnCount();
			for (int cx = 0; cx < nbColumns; cx++) {
				if (cx != myColIndex) {
					totalWidths += table.getColumnWidth(cx);
				}
			}
			if (totalWidths > 100) {
				return "the sum of column widths cannot be greater than 100";
			}
		}
		return null;
	}

}

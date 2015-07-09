package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;

/**
 *
 * @author pkk
 *
 */
public class TableColumnCombo extends AbstractTypeCombo<String> {
	

	/**
	 * @param parent
	 * @param table 
	 * @param editMode 
	 * @param filterList 
	 */
	public TableColumnCombo(Composite parent, ITable table, boolean editMode, List<String> filterList) {
		super(parent, getColumns(table, editMode, filterList).toArray(new String[0]));
	}

	/**
	 * @param table 
	 * @param editMode 
	 * @param filterList 
	 * @return string[]
	 */
	private static List<String> getColumns(ITable table, boolean editMode, List<String> filterList) {
		List<ITableColumn> list = table.getColumns();
		List<String> columnNames = new ArrayList<String>();
		for (ITableColumn column : list) {
			if (!checkFilter(column.getColumnName(), editMode, filterList)) {
				if (!column.isBoundToDomain()) {
					columnNames.add(column.getColumnName());
				}
			}
		}
		List<String> dAttributes = TableDomainObjectUtil.getDomainAttributeNames(table);
		for (String attribute : dAttributes) {
			if (!checkFilter(attribute, editMode, filterList)) {
				columnNames.add(attribute);
			}
		}
		Collections.sort(columnNames, String.CASE_INSENSITIVE_ORDER);
		return columnNames;
	}
	
	/**
	 * @param name
	 * @param editMode 
	 * @param filterList 
	 * @return boolean
	 */
	private static boolean checkFilter(String name, boolean editMode, List<String> filterList) {
		if (editMode) {
			return false;
		}
		for(String columnName : filterList) {
			if (columnName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ITypeCombo#getSelectedValue()
	 */
	@Override
	public String getSelectedValue() {
		return getSelectedItem();
	}

}

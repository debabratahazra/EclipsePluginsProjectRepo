package com.odcgroup.page.ui.properties.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.ui.properties.AbstractPropertyEditor;

/**
 *
 * @author pkk
 *
 */
public class TableColumnItemCellEditor extends AbstractPropertyEditor {

	/**
	 * @param property
	 */
	public TableColumnItemCellEditor(Property property) {
		super(property);
	}

	/**
	 * @see com.odcgroup.page.ui.properties.PropertyEditor#getCellEditor(org.eclipse.swt.widgets.Composite, org.eclipse.jface.viewers.ILabelProvider)
	 */
	public CellEditor getCellEditor(Composite parent, ILabelProvider labelProvider) {
		ITableColumnItem item = TableHelper.getTableColumnItem(getProperty().getWidget());
		ITableColumn column = item.getTableColumn();
		ITable table = column.getTable();
		List<ITableColumn> columns = column.getTable().getColumns();
		List<String> possibleValues = new ArrayList<String>();
		possibleValues.addAll(TableDomainObjectUtil.getDomainAttributeNames(table));
		for (ITableColumn tColumn : columns) {
			if (tColumn.isComputed()) {
				possibleValues.add(tColumn.getColumnName());
			}
		}
		Collections.sort(possibleValues);
		
		LabelProvider provider = new LabelProvider() {
			public String getText(Object element) {
				return (String) element;
			}			
		};
		
		CellEditor editor = new ExtendedComboBoxCellEditor(parent, possibleValues, provider, false) {
			public void activate() {
				getControl().setEnabled(!getProperty().isReadonly());
				super.activate();
			}
		};
		return editor;
	}

}

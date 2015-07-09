package com.odcgroup.page.ui.properties.table;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.properties.AbstractPropertyEditor;
import com.odcgroup.page.ui.properties.DomainAttributeCellEditor;

/**
 * @author phanikumark
 *
 */
public class TableGroupSortingColumnCellEditor extends AbstractPropertyEditor {

	/**
	 * @param property
	 */
	public TableGroupSortingColumnCellEditor(Property property) {
		super(property);
	}

	@Override
	public CellEditor getCellEditor(Composite parent,
		ILabelProvider labelProvider) {
	    Property prop = getProperty();
	    CellEditor editor = new DomainAttributeCellEditor(parent, prop);
	    String name = prop.getValidatorName();
	    if (!StringUtils.isEmpty(name)) {
		editor.setValidator(makeCellValidator(name));
	    }
	    return editor;
	}

}

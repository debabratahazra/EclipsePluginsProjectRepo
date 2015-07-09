package com.odcgroup.page.ui.properties;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

/**
 * 
 * @author atr
 *
 * $id$
 */
public class PropertyCheckBoxEditor extends AbstractPropertyEditor {
	
	/**
	 * 
	 * @param property
	 */
	public PropertyCheckBoxEditor(Property property) {
		super(property);
	}
	
	/**
	 * Gets the CellEditor for this Property.
	 * 
	 * @param parent The parent
	 * @param labelProvider The label provider
	 * @return CellEditor The cell editor
	 */
	public CellEditor getCellEditor(Composite parent, ILabelProvider labelProvider) {
		CellEditor editor = new ExtendedComboBoxCellEditor(parent, 
				Arrays.asList(new Object [] { "false", "true"}), labelProvider, true);
		String name = getProperty().getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			editor.setValidator(makeCellValidator(name));
		}
		return editor;
	}


}

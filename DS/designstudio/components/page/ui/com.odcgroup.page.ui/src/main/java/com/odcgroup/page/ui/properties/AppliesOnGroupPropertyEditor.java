package com.odcgroup.page.ui.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

public class AppliesOnGroupPropertyEditor extends AbstractPropertyEditor {

	/**
	 * Creates a new AppliesOnGroupPropertyEditor.
	 * @param property
	 */
	public AppliesOnGroupPropertyEditor(Property property) {
		super(property);
	}

	/**
	 * Gets the cell editor of the current property.
	 * 
	 * @param parent
	 *            The parent
	 * @param labelProvider
	 *            The label provider
	 * @return CellEditor
	 * 
	 */
	
	public CellEditor getCellEditor(Composite parent,
			ILabelProvider labelProvider) {
		Property p = getProperty();		
		CellEditor editor = new ApplyOnGroupCellEditor(parent, p);
		return editor;
	}

}

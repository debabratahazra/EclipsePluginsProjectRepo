package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

/**
 * An expression editor to edit property
 * 
 * @author Kai Kreuzer
 * 
 */
public class ExpressionPropertyTextEditor extends AbstractPropertyEditor {


	/**
	 * Creates a new MultiLinePropertyTextEditor.
	 * 
	 * @param property
	 *            The Property
	 */
	public ExpressionPropertyTextEditor(Property property) {
		super(property);
	}

	
	/**
	 * Gets the CellEditor for this Property.
	 * 
	 * @param parent
	 *            The parent
	 * @param labelProvider
	 *            The label provider
	 * @return CellEditor The cell editor
	 */
	public CellEditor getCellEditor(Composite parent,
			ILabelProvider labelProvider) {
		final CellEditor cellEditor = new ExpressionCellEditor(parent,
				getProperty());
		String name = getProperty().getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			cellEditor.setValidator(makeCellValidator(name));
		}
		return cellEditor;
	}
}

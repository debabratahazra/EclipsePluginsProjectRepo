package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.properties.enabled.EnabledConstants;

/**
 * Property editor used to define the "Enabled is based on" property.
 * @author yan
 */
public class EnabledIsBasedOnPropertyEditor extends AbstractPropertyEditor {

	/**
     * Creates a new EnabledIsBasedOnPropertyEditor.
     * 
     * @param property The property
     */
    public EnabledIsBasedOnPropertyEditor(Property property) {
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
		String propertyValue = p.getWidget().getPropertyValue(EnabledConstants.ENABLED_PROPERTY_NAME);
		if (propertyValue.equals(EnabledConstants.ENABLED_CONDITIONAL_VALUE)) {
			CellEditor editor = new EnabledIsBasedOnCellEditor(parent, p);
			String name = p.getValidatorName();
			if (!StringUtils.isEmpty(name)) {
				editor.setValidator(makeCellValidator(name));
			}
			return editor;
		} else {
			return null;
		}
	}

}

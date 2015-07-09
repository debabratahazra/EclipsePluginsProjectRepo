package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

/**
 * Property editor used to define the "Editable is based on" property.
 * @author scn
 */
public class EditableIsBasedOnPropertyEditor extends AbstractPropertyEditor {

	/**
     * Creates a new EditableIsBasedOnPropertyEditor.
     * 
     * @param property The property
     */
    public EditableIsBasedOnPropertyEditor(Property property) {
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
		String propertyValue = p.getWidget().getPropertyValue("editable");
		if (propertyValue.equals("conditional")) {
			CellEditor editor = new EditableIsBasedOnCellEditor(parent, p);
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

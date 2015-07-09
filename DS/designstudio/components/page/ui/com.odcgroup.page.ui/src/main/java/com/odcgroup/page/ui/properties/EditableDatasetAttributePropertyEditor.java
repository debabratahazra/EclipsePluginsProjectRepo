package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

/**
 * To be used in editable table/tree. Allow the user to select an editable dataset attribute 
 */
public class EditableDatasetAttributePropertyEditor extends AbstractPropertyEditor {

	/**
     * Creates a new DomainAttributePropertyEditor.
     * 
     * @param property The property
     */
    public EditableDatasetAttributePropertyEditor(Property property) {
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
		CellEditor editor = new EditableDatasetAttributeCellEditor(parent, p, false);
		String name = p.getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			editor.setValidator(makeCellValidator(name));
		}
		return editor;
	}

}

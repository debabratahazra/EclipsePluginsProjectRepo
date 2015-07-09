package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

/**
 * Definition of an ContextPropertyEditor who contain a file dialog.
 * 
 * @author Gary Hayes
 */
public class DomainAssociationPropertyEditor extends AbstractPropertyEditor {

	/**
     * Creates a new DomainAssociationPropertyEditor.
     * 
     * @param property The property
     */
    public DomainAssociationPropertyEditor(Property property) {
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
		CellEditor editor = new DomainAssociationCellEditor(parent, p);
		String name = p.getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			// cellEditor.setValidator(makeCellValidator(name));
		}
		return editor;
	}

}

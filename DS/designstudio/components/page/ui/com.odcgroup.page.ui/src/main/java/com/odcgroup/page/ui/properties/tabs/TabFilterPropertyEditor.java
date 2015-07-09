package com.odcgroup.page.ui.properties.tabs;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.properties.AbstractPropertyEditor;

/**
 * @author atr
 */
public class TabFilterPropertyEditor extends AbstractPropertyEditor {

	/**
     * Creates a new DomainAttributePropertyEditor.
     * 
     * @param property The property
     */
    public TabFilterPropertyEditor(Property property) {
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
		CellEditor editor = new TabFilterCellEditor(parent, p);
		String name = p.getValidatorName();
		if (StringUtils.isNotEmpty(name)) {
			editor.setValidator(makeCellValidator(name));
		}
		return editor;
	}

}

package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

public class OutputDesignPropertyEditor extends AbstractPropertyEditor {
	
	/**
     * 
     * @param property
     */
    public OutputDesignPropertyEditor(Property property) {
        super(property);
    }
	
    /**
	 * Gets the cell editor of the current property.
	 * @param parent
	 * 			The parent
	 * @param labelProvider
	 * 			The label provider 
	 * @return CellEditor
	 *  	
	 */
	public CellEditor getCellEditor(Composite parent, ILabelProvider labelProvider) {
		CellEditor editor = new OutputDesignCellEditor(parent, getProperty());
		String name = getProperty().getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			// cellEditor.setValidator(makeCellValidator(name));
		}
		return editor;
	}


}


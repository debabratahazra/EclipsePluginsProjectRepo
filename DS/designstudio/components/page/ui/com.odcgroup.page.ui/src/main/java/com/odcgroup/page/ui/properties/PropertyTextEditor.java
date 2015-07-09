package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

/**
 * 
 * @author Gary Hayes
 */
public class PropertyTextEditor extends AbstractPropertyEditor {

    /**
     * Creates a new PropertyTextEditor.
     * 
     * @param property The Property
     */
    public PropertyTextEditor(Property property) {
        super(property);
    }
	
	/**
	 * @param parent
	 * @param labelProvider
	 * @return CellEditor
	 */
	public CellEditor getCellEditor(Composite parent, ILabelProvider labelProvider) {
		CellEditor cellEditor = new TextCellEditor(parent);
		
		String name = getProperty().getValidatorName();	
		if (!StringUtils.isEmpty(name)) {
			 cellEditor.setValidator(makeCellValidator(name));
		}
		return cellEditor;
	}

}

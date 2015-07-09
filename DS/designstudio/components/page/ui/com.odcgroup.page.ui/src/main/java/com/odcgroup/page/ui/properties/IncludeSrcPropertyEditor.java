package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;

/**
 * Define a property editor for the include src property.
 * 
 * @author Alexandre Jaquet
 */
public class IncludeSrcPropertyEditor  extends AbstractPropertyEditor {
	
	/**
     * 
     * @param property
     */
    public IncludeSrcPropertyEditor(Property property) {
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
		CellEditor editor = new IncludeSrcCellEditor(parent, getProperty());
		String name = getProperty().getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			// cellEditor.setValidator(makeCellValidator(name));
		}
		return editor;
	}


}

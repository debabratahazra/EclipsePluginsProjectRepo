package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;
/**
 * Definition of an ImagePropertyEditor who contain a file dialog
 * 
 * @author Alexandre Jaquet
 *
 */
public class ImagePropertyEditor extends AbstractPropertyEditor {
	
	/**
     * 
     * @param property
     */
    public ImagePropertyEditor(Property property) {
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
		CellEditor editor = new ImageCellEditor(parent);
		String name = getProperty().getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			editor.setValidator(makeCellValidator(name));
		}
		return editor;
	}


}

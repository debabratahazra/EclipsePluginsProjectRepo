
package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.util.ClassUtils;

/**
 * The PropertyDescriptor for a Property's value.
 *
 * @author Gary Hayes
 */
public class PropertyValuePropertyDescriptor extends PropertyDescriptor {

    /** The Property. */
    private Property property;
    
	/**
	 * Creates a new PropertyValuePropertyDescriptor.
	 * 
	 * @param property The property
	 */
	public PropertyValuePropertyDescriptor(Property property) {
		super(property.getTypeName(), property.getType().getDisplayName());
		String subCategory = property.getType().getSubCategory(); 
		if (StringUtils.isNotBlank(subCategory)) {
			setCategory(subCategory);
		}
				
		this.property = property;
		
		if (property.getType().getDataType().getValues().size() > 0) {
		    // In this case we use a ComboBox and pass DataValues
		    setLabelProvider(new DataValueLabelProvider());
		}
	}

	/**
	 * Creates the property editor.
	 * 
	 * @param parent
	 * @return CellEditor
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor cellEditor = null;
		if (! property.isReadonly()) {
			String editorName = property.getEditorName();
			PropertyEditor wpe = (PropertyEditor) ClassUtils.newInstance(getClass().getClassLoader(), editorName, property);
			cellEditor = wpe.getCellEditor(parent, getLabelProvider());			
		}
		
		return cellEditor;
	}
}
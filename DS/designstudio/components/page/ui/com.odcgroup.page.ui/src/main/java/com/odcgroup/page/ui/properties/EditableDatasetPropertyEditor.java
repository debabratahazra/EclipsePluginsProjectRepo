package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * @author phanikumark
 *
 */
public class EditableDatasetPropertyEditor extends AbstractPropertyEditor {

	/**
	 * @param property
	 */
	public EditableDatasetPropertyEditor(Property property) {
		super(property);
	}

	@Override
	public CellEditor getCellEditor(Composite parent,
			ILabelProvider labelProvider) {		
		Property p = getProperty();		
		Widget rw = p.getWidget().getRootWidget();
    	Property entity = rw.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
		CellEditor editor = null;
    	if(StringUtils.isNotEmpty(entity.getValue()) 
    			&& EditableDatasetUtil.entityHasSQLNameAnnotation(p, entity.getValue())) {
    		editor = new EditableDatasetCellEditor(parent, p);
    	} else {
    		editor = new TextCellEditor(parent);
    	} 
		String name = p.getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			editor.setValidator(makeCellValidator(name));
		}
		return editor;
	}
	

}

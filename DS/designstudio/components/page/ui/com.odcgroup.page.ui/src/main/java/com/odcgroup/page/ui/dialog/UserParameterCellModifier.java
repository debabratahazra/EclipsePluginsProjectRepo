package com.odcgroup.page.ui.dialog;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import com.odcgroup.page.model.Parameter;

/**
 *
 * @author pkk
 *
 */
public class UserParameterCellModifier implements ICellModifier {

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object getValue(Object element, String property) {	
		Parameter parameter = (Parameter) element;
		if (property.equals("Name")) {
			return parameter.getName();
		} else {
			return parameter.getValue();
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public void modify(Object element, String property, Object value) {	
		if (value == null) {
			return;
		}
		String val = (String) value;
		Parameter parameter = (Parameter) ((TableItem) element).getData();
		if (property.equals("Name")) {
			parameter.setName(val);
		} else {
			parameter.setValue(val);
		}		
	}

}

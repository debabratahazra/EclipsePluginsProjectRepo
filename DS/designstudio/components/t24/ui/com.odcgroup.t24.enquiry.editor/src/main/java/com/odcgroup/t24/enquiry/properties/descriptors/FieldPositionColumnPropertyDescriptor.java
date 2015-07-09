package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.properties.celleditors.IntegerCellEditor;

/**
 * @author satishnangi
 *
 */
public class FieldPositionColumnPropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldPositionColumnPropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getFieldPosition_Column().getName(), "Column");
	}
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new IntegerCellEditor(parent);
	}
	
	
}

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
public class FieldPositionLinePropertyDescriptor extends PropertyDescriptor {
	/**
	 * @param id
	 * @param displayName
	 */
	public FieldPositionLinePropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getFieldPosition_Line().getName(), "Line");
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new IntegerCellEditor(parent , true);
	}
}

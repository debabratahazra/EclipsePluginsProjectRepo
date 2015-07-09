package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.properties.celleditors.BooleanCellEditor;

/**
 * @author satishnangi
 *
 */
public class FieldPositionRelativeMultiLinePropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldPositionRelativeMultiLinePropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getFieldPosition_MultiLine().getName(), "Relative Multi Line");
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new BooleanCellEditor(parent);
	}
}

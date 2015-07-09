package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.properties.celleditors.BooleanCellEditor;

/**
 * TODO: Document me!
 *
 * @author satishnangi
 *
 */
public class FieldPositionPageThrowPropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldPositionPageThrowPropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getFieldPosition_PageThrow().getName(), "Page Throw");
		
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new BooleanCellEditor(parent);
	}
}

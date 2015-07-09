package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FieldFormat;

/**
 * @author satishnangi
 *
 */
public class FieldFormatTypePropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldFormatTypePropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getFormat_Format().getName(), "Type");
		setCategory("Format");
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new ExtendedComboBoxCellEditor(parent, FieldFormat.VALUES, new LabelProvider());
	}
	
	
}

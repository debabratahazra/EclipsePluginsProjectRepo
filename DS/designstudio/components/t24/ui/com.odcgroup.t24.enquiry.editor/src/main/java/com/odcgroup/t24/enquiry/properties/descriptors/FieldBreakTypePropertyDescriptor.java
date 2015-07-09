package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.BreakKind;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

/**
 * @author satishnangi
 *
 */
public class FieldBreakTypePropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldBreakTypePropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getBreakCondition_Break().getName(), "Break Type");
	}
   
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new ExtendedComboBoxCellEditor(parent, BreakKind.VALUES, new LabelProvider());
	}
}

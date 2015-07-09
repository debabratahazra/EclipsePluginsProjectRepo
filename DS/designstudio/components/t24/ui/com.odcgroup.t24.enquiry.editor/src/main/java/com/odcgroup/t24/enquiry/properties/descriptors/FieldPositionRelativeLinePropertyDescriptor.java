package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.Arrays;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

/**
 * TODO: Document me!
 *
 * @author satishnangi
 *
 */
public class FieldPositionRelativeLinePropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldPositionRelativeLinePropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getFieldPosition_Relative().getName(), "Relative Line");
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		String[] relativeLineItems = {"None" ,"+","-"};
		ExtendedComboBoxCellEditor cellEditor = new ExtendedComboBoxCellEditor(parent,Arrays.asList(relativeLineItems),new LabelProvider());
		return cellEditor;
	}
} 

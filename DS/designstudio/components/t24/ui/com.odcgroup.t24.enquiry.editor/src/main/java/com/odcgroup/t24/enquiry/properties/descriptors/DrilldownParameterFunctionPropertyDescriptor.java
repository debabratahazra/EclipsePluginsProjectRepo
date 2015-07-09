package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FunctionKind;

/**
 * @author phanikumark
 *
 */
public class DrilldownParameterFunctionPropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public DrilldownParameterFunctionPropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getParameters_Function(), "Function");
		setCategory("Parameters:");
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new ExtendedComboBoxCellEditor(parent, FunctionKind.VALUES, new LabelProvider());
	}
	
	
}

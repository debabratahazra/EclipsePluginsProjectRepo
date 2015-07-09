package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.properties.celleditors.BooleanCellEditor;

/**
 * 
 * @author phanikumark
 *
 */
public class DrilldownParameterRunPropertyDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public DrilldownParameterRunPropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getParameters_RunImmediately(), "Run Immediately");
		setCategory("Parameters:");
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new BooleanCellEditor(parent);
	}

}

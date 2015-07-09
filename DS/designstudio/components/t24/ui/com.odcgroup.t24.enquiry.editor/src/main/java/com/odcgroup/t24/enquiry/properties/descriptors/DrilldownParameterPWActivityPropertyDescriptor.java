package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

/**
 *
 * @author phanikumark
 *
 */
public class DrilldownParameterPWActivityPropertyDescriptor extends PropertyDescriptor {
	/**
	 * @param id
	 * @param displayName
	 */
	public DrilldownParameterPWActivityPropertyDescriptor() {
		super(EnquiryPackage.eINSTANCE.getParameters_PwActivity(), "PW Activity");
		setCategory("Parameters:");
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new TextCellEditor(parent) {

			@Override
			protected void doSetValue(Object value) {
				if (value == null) {
					value = "";
				}
				super.doSetValue(value);
			}
			
		};
	}
	
}

package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.List;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

public class FieldDisplayTypePropertyDescriptor extends AbstractPropertyDescriptorWrapper {

	private Enquiry enquiry;
	/**
	 * @param descriptor
	 */
	public FieldDisplayTypePropertyDescriptor(IPropertyDescriptor descriptor, EObject enquiry) {
		super(descriptor);
		this.enquiry = (Enquiry) enquiry;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		List<String> displayTypes = EnquiryUtil.getEBReportsEnquiryDisplayTypes(enquiry.eResource());
		return new ExtendedComboBoxCellEditor(parent, displayTypes, new LabelProvider());
	}
}

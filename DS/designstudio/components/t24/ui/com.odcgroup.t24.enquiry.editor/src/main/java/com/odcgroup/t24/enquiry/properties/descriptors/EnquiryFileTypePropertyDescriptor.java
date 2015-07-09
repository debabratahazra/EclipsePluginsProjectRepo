package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.ui.actions.NewEnquiryWizardPage;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * TODO: Property Descriptor to provide a ComboBox Cell editor and get the value
 * and to append to the Application as a fileType <br\>
 * DS-8785
 * 
 * @author rameshsampenga
 * 
 */
public class EnquiryFileTypePropertyDescriptor extends PropertyDescriptor {

	public static final String ID = "fileType";
	private Enquiry model;

	public EnquiryFileTypePropertyDescriptor(EObject model) {
		super(ID, "File Type");
		this.model = (Enquiry) model;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new ExtendedComboBoxCellEditor(parent, Arrays.asList(NewEnquiryWizardPage.APPLICATION_FILE_TYPES),
				new LabelProvider());
	}

}

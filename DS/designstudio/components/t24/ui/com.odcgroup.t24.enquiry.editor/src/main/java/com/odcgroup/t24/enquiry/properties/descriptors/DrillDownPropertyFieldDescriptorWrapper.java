package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.EnquiryFieldSelectionDialog;

/**
 * @author phanikumark
 *
 */
public class DrillDownPropertyFieldDescriptorWrapper extends PropertyDescriptor {
	
	private DrillDown model;

	public DrillDownPropertyFieldDescriptorWrapper(EObject model) {
		super(EnquiryPackage.eINSTANCE.getParameters_FieldName(), "Field(s)");
		this.model = (DrillDown) model;
		setCategory("Parameters:");
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				return EnquiryFieldSelectionDialog.openEnquiryFieldsDialog(cellEditorWindow.getShell(), (Enquiry) model
						.eContainer(), "Enquiry Field Selection", "Select an Enquiry Field", model.getParameters()
						.getFieldName(), true);
			}
		};
		return dialog;
	}

}

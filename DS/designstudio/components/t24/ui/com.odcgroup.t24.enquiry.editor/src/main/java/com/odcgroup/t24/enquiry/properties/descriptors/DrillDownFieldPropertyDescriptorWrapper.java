package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.ArrayList;
import java.util.List;

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
public class DrillDownFieldPropertyDescriptorWrapper extends PropertyDescriptor {
	
	private DrillDown model;

	public DrillDownFieldPropertyDescriptorWrapper(EObject model) {
		super(EnquiryPackage.eINSTANCE.getDrillDown_LabelField(), "Applies To Enquiry Field (Label Field)");
		this.model = (DrillDown) model;
		setCategory("General:");
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				List<String> list = new ArrayList<String>();
				list.add(model.getLabelField());
				return EnquiryFieldSelectionDialog.openEnquiryFieldsDialog(cellEditorWindow.getShell(),
						(Enquiry) model.eContainer(), "Enquiry Field Selection", "Select an Enquiry Field as Label",
						list, false);
			}
		};
		return dialog;
	}

}

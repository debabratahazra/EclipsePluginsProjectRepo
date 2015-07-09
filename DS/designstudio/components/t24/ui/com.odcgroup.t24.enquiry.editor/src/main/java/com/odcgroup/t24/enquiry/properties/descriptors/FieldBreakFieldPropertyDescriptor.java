package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.EnquiryFieldSelectionDialog;

/**
 * @author satishnangi
 *
 */
public class FieldBreakFieldPropertyDescriptor extends PropertyDescriptor {
   private EObject model =null;
	/**
	 * @param id
	 * @param displayName
	 */
	public FieldBreakFieldPropertyDescriptor(EObject model) {
		super(EnquiryPackage.eINSTANCE.getBreakCondition_Field().getName(), "Break Field");
		this.model = model;
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				List<String> list = new ArrayList<String>();
				Field field = (Field) model;
				if(field.getBreakCondition() != null){
					list.add(field.getBreakCondition().getField());
				}
				Enquiry enquiry = (Enquiry) field.eContainer();
				return EnquiryFieldSelectionDialog.openEnquiryFieldsDialog(cellEditorWindow.getShell(), enquiry,
						"Enquiry Field Selection", "Select an Enquiry Field as Break Field", list, false);
			}
		};
		return dialog;
	}
}

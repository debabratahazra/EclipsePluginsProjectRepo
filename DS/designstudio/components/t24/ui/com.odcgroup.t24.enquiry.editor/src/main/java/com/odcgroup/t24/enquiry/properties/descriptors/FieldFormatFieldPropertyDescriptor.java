package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.Format;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.EnquiryFieldSelectionDialog;

/**
 *
 * @author mumesh
 *
 */
public class FieldFormatFieldPropertyDescriptor extends PropertyDescriptor {

	public static final String FORMAT_FIELD = "Format_Field";
	private EObject model = null;

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldFormatFieldPropertyDescriptor(EObject model) {
		super(FORMAT_FIELD, "Field");
		this.model = model;
		setCategory("Format");
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				Field field = (Field) model;
				Enquiry enquiry = (Enquiry) field.eContainer();
				Format frmt = field.getFormat();
				Field format = EnquiryFactory.eINSTANCE.createField();
				if (frmt != null) {
					format.setName(field.getFormat().getField());
				}
				return EnquiryFieldSelectionDialog.openDialog(cellEditorWindow.getShell(), enquiry, format, null, false);
			}
		};
		return dialog;
	}
}

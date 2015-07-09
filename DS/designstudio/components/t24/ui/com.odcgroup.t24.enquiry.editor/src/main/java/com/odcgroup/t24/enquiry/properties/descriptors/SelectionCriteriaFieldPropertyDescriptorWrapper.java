package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.google.common.collect.Lists;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.EnquiryFieldSelectionDialog;

/**
 * @author phanikumark
 *
 */
public class SelectionCriteriaFieldPropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
    private Enquiry model;
    private String value;
	/**
	 * @param descriptor
	 */
	public SelectionCriteriaFieldPropertyDescriptorWrapper(IPropertyDescriptor descriptor, Enquiry model, String value) {
		super(descriptor);
        this.model = model;
        this.value = value;
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				Shell shell = cellEditorWindow.getShell();
				return EnquiryFieldSelectionDialog.openEnquiryFieldsDialog(shell, model, "Field Dialog",
						"Select an Enquriy Field", Lists.newArrayList(value), false);
			}
		};
		return dialog;
	}
  
	
	
}

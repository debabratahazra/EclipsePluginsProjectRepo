package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.EnquiryFieldSelectionDialog;

/**
 * @author satishnangi
 *
 */
public class EnquiryFieldPropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
    private EObject model;
    private String displayName =null;
    private String fileName = null;
	/**
	 * @param descriptor
	 */
	public EnquiryFieldPropertyDescriptorWrapper(IPropertyDescriptor descriptor ,EObject model) {
		super(descriptor);
        this.model = model;
	}
	/**
	 * @param descriptor
	 */
	public EnquiryFieldPropertyDescriptorWrapper(IPropertyDescriptor descriptor ,String displayName,EObject model) {
		this(descriptor, model);
        this.displayName = displayName;
        
	}

	/**
	 * @param descriptor
	 * @param model
	 * @param fileName
	 */
	public EnquiryFieldPropertyDescriptorWrapper(IPropertyDescriptor descriptor, EObject model, String fileName) {
		super(descriptor);
		this.model = model;
		this.fileName = fileName;
	}

	@Override
	public String getDisplayName() {
		if(displayName !=null){
			return displayName;
		}
		return super.getDisplayName();
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider(), model) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				return EnquiryFieldSelectionDialog.openDialog(cellEditorWindow.getShell(), model, fileName, false);
			}
		};
		return dialog;
	}
  
	
	
}

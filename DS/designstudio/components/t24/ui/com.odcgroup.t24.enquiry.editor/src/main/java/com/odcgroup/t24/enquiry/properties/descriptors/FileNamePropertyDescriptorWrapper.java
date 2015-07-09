package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.properties.util.EnquiryDialogUtil;

/**
 *
 * @author satishnangi
 *
 */
public class FileNamePropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
   public static String ENQUIRY_FILE_NAME = "Application";
   
   protected boolean selectApplicationWithFullName() {
	   return true;
   }
	
	/**
	 * @param descriptor
	 */
	public FileNamePropertyDescriptorWrapper(IPropertyDescriptor descriptor) {
		super(descriptor);		
	}
	
    @Override
	public String getDisplayName() {
		return ENQUIRY_FILE_NAME;
	}
    
    @Override
	public CellEditor createPropertyEditor(Composite parent) {
    	ExtendedDialogCellEditor dialog = new ExtendedDialogCellEditor(parent,new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				return EnquiryDialogUtil.selectApplicationName(cellEditorWindow.getShell(), selectApplicationWithFullName());
			}
		};
		return dialog;
	}
}

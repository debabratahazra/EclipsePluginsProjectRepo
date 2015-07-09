package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.mdf.editor.ui.dialogs.FieldSelectionDialog;
import com.odcgroup.mdf.editor.ui.dialogs.ModelElementTreeSelectionUI;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * @author satishnangi
 *
 */
public class AccountFieldPropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
    private Enquiry model;
	/**
	 * @param descriptor
	 */
	public AccountFieldPropertyDescriptorWrapper(IPropertyDescriptor descriptor ,EObject model) {
		super(descriptor);
        this.model = (Enquiry)model;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedDialogCellEditor dialog = new ExtendedDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				ModelElementTreeSelectionUI elemntUI = new ModelElementTreeSelectionUI();
				FieldSelectionDialog dialog = new FieldSelectionDialog(cellEditorWindow.getShell(), elemntUI);
				elemntUI.setFieldSelectionDialog(dialog);
				String fileName = model.getFileName();
				MdfClass klass = EnquiryUtil.getEnquiryApplication(fileName, model);
				Resource res = ((EObject) klass).eResource();
				dialog.setInput(klass);
				if (res != null) {
					int i = dialog.open();
					if(i == Dialog.OK){
						if(dialog.getResult() !=null && dialog.getResult().length !=0){
						    MdfModelElement element =(MdfModelElement)dialog.getResult()[0];
						    return T24Aspect.getT24Name(element);
						}
					}
				} else {
					MessageDialog mDialog = new MessageDialog(null, "Missing Application for this Enquiry", null,
							"The Application for the Enquiry does not exists!", MessageDialog.ERROR,
							new String[] { "OK" }, 0);
					mDialog.open();
				}
				return null;
			}
		};
		return dialog;
	}
  
	
}

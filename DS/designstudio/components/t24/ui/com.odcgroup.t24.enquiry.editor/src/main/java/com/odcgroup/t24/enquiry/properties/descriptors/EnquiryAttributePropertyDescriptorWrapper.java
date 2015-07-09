package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.List;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.properties.dialogs.ValueSelectionDialog;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * @author satishnangi
 * 
 */
public class EnquiryAttributePropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
	
	private Enquiry enquiry;
	/**
	 * @param descriptor
	 */
	public EnquiryAttributePropertyDescriptorWrapper(IPropertyDescriptor descriptor ,EObject model) {
		super(descriptor);
        this.enquiry = (Enquiry)model;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedDialogCellEditor attributeCellEditor = new ExtendedDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
					AttributePropertyDialog dialog = new AttributePropertyDialog(cellEditorWindow.getShell());
					int i = dialog.open();
					if(i == Dialog.OK){
						return dialog.getSelectedValues();
					}
				return null;
			}
		};
		return attributeCellEditor;
	}

	private class AttributePropertyDialog extends ValueSelectionDialog {
		
		@Override
		protected Control createDialogArea(Composite parent) {
			 Control control = super.createDialogArea(parent);
				 List<String> enquiryAttributes = EnquiryUtil.getEBReportsEnquiryAttributes(enquiry.eResource());
				 setFromListInput(enquiryAttributes.toArray(new String[enquiryAttributes.size()]));
				 if(enquiry !=null){
					EList<String> attributesList = enquiry.getAttributes();
					if(attributesList !=null && !attributesList.isEmpty()) {
						setToListValues(attributesList.toArray(new String[0]));
					}
				 }
			 return control;
		}
		/**
		 * @param parentShell
		 */
		protected AttributePropertyDialog(Shell parentShell) {
			super(parentShell);
		}
		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText("Enquiry Attribute Selection Dialog");
		}
		@Override
		protected Point getInitialSize() {
			return new Point(600, 500);
		}
		
	}

}

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

import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.dialogs.ValueSelectionDialog;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldAttributePropertyDescriptor extends AbstractPropertyDescriptorWrapper{

	private Field field;
	/**
	 * @param descriptor
	 */
	public FieldAttributePropertyDescriptor(IPropertyDescriptor descriptor,EObject model) {
		super(descriptor);
		this.field = (Field)model;
		// TODO Auto-generated constructor stub
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
				 List<String> attributes = EnquiryUtil.getEBReportsEnquiryAttributes(field.eResource());
				 setFromListInput(attributes.toArray(new String[attributes.size()]));
				 if(field !=null){
					EList<String> attributesList = field.getAttributes();
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

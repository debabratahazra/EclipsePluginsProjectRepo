package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.Arrays;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.properties.dialogs.FieldOperationTypesDialog;
import com.odcgroup.t24.enquiry.properties.util.OperationEnum;
import com.odcgroup.t24.enquiry.properties.util.OperationPropertiesUtil;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class FieldOperationPropertyDescriptor extends PropertyDescriptor {

	private Field model;
	private String[] operationTypes = OperationEnum.getOperationTypes().toArray(new String[0]);

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldOperationPropertyDescriptor(EObject model) {
		super(EnquiryPackage.eINSTANCE.getOperation().getName(), "Operation");
		this.model = (Field) model;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		LabelProvider lableProvider = new LabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof Operation){
					return OperationPropertiesUtil.getOperationSummary((Operation) element);
				}
				return super.getText(element);
			}
		};
		ExtendedDialogCellEditor dilogCellEditor = new ExtendedDialogCellEditor(parent, lableProvider) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				Arrays.sort(operationTypes);
				FieldOperationTypesDialog dialog = new FieldOperationTypesDialog(cellEditorWindow.getShell(), model,
						"Operation", operationTypes);
				int i = dialog.open();
				if (i == Dialog.OK) {
					if (null != dialog.getFieldOpertaion()) {
						return dialog.getFieldOpertaion();
					}
					return "None";
				}
				return null;
			}
			
			@Override
			public void dispose() {
			}
		};
		return dilogCellEditor;
	}

	
}

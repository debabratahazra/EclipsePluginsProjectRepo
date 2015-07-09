package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.ArrayList;

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
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;
import com.odcgroup.t24.enquiry.properties.dialogs.ValueSelectionDialog;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class SelectionOperandsPropertyDescriptor extends PropertyDescriptor{

	
	private Selection model;

	/**
	 * @param id
	 * @param displayName
	 */
	public SelectionOperandsPropertyDescriptor(EObject model) {
		super(EnquiryPackage.eINSTANCE.getSelection_Operands().getName(), "Operands");
		this.model = (Selection)model;
	}

	
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedDialogCellEditor dilogCellEditor = new ExtendedDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				SelectionValueSelectionDialog dialog = new SelectionValueSelectionDialog(cellEditorWindow.getShell());
				int i = dialog.open();
				if (i == Dialog.OK) {
					return dialog.getSelectedOptions();
				}
				return null;
			}
		};

		return dilogCellEditor;
	}

	private class SelectionValueSelectionDialog extends ValueSelectionDialog {
		@Override
		protected Control createDialogArea(Composite parent) {
			Control control = super.createDialogArea(parent);
			EList<SelectionOperator> operandList = model.getOperands();
			if(!operandList.isEmpty()){
				ArrayList<String> toListValues = new ArrayList<String>();
				for(SelectionOperator option : operandList){
					toListValues.add(option.getName());
				}
				setToListValues(toListValues.toArray(new String[0]));
			}
			ArrayList<String> fromListValues = new ArrayList<String>();
			for(SelectionOperator options : SelectionOperator.VALUES){
				fromListValues.add(options.getName());
			}
			setFromListInput(fromListValues.toArray(new String[0]));
			return control;
		}

		public SelectionValueSelectionDialog(Shell parentShell) {
			super(parentShell);
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(" Operands Selection Dialog");
		}

		@Override
		protected Point getInitialSize() {
			return new Point(500, 300);
		}

		public ArrayList<SelectionOperator> getSelectedOptions() {
			ArrayList<SelectionOperator> options = new ArrayList<SelectionOperator>();
			for(String value : super.getSelectedValues()){
				options.add(SelectionOperator.getByName(value));
			}
			return options;
		}
	}
}

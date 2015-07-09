package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.MultiValueSelectionDialog;

/**
 *
 * @author phanikumark
 *
 */
public class DrilldownParameterVariablePropertyDescriptor extends PropertyDescriptor {
	
	private DrillDown model;

	/**
	 * @param id
	 * @param displayName
	 */
	public DrilldownParameterVariablePropertyDescriptor(EObject model) {
		super(EnquiryPackage.eINSTANCE.getParameters_Variable(), "Parameter(s)");
		setCategory("Parameters:");
		this.model = (DrillDown) model;
	}
	
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		if (model.getParameters() == null) {
			return null;
		}
		final List<String> variables = model.getParameters().getVariable();
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				MultiValueSelectionDialog dialog = new MultiValueSelectionDialog(cellEditorWindow.getShell(), "Drilldown Parameters", variables, true) ;
				if(dialog.open() == Dialog.OK) {
					return dialog.getSelectedOptions();
				}
				return null;
			}
		};
		return dialog;
	}
	

}

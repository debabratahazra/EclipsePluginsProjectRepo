package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.MultiValueSelectionDialog;

/**
 * @author satishnangi
 *
 */
public class MultiValueSelectionPropertyDescriptor  extends PropertyDescriptor{
	private List<String> selectedValues;
	private boolean isSequential = false;
	private String dialogName;

	public MultiValueSelectionPropertyDescriptor( String dialogName, Object id, String displayName ,List<String> selectedValues, boolean isSequential){
		super(id, displayName);
		this.selectedValues = selectedValues;
		this.isSequential = isSequential;
		this.dialogName = dialogName;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				MultiValueSelectionDialog dialog = new MultiValueSelectionDialog(cellEditorWindow.getShell(), dialogName,selectedValues,isSequential) ;
				if(dialog.open() == Dialog.OK) {
					return dialog.getSelectedOptions();
				}
				return null;
			}
		};
		return dialog;
	}
}

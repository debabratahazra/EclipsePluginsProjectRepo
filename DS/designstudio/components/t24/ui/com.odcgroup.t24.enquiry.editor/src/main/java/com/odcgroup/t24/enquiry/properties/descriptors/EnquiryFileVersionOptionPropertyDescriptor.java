package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FileVersion;
import com.odcgroup.t24.enquiry.enquiry.FileVersionOption;
import com.odcgroup.t24.enquiry.properties.dialogs.ValueSelectionDialog;

/**
 * TODO: Document me!
 * 
 * @author satishnangi
 * 
 */
public class EnquiryFileVersionOptionPropertyDescriptor extends PropertyDescriptor {
    private Enquiry model;
    
	/**
	 * @param id
	 * @param displayName
	 */
	public EnquiryFileVersionOptionPropertyDescriptor(EObject model) {
		super(EnquiryPackage.eINSTANCE.getFileVersion_Values().getName(), "File Version");
		this.model = (Enquiry)model;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedDialogCellEditor dilogCellEditor = new ExtendedDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				FileVersionOptionDialog dialog = new FileVersionOptionDialog(cellEditorWindow.getShell());
				int i = dialog.open();
				if (i == Dialog.OK) {
					return dialog.getSelectedOptions();
				}
				return null;
			}
		};

		return dilogCellEditor;
	}

	private class FileVersionOptionDialog extends ValueSelectionDialog {
		@Override
		protected Control createDialogArea(Composite parent) {
			Control control = super.createDialogArea(parent);
			List<FileVersion> fileVersionList = model.getFileVersion();
			if(!fileVersionList.isEmpty()){
				List<FileVersionOption> options = fileVersionList.get(0).getValues();
				ArrayList<String> toListValues = new ArrayList<String>();
				for(FileVersionOption option : options){
					toListValues.add(option.getName());
				}
				setToListValues(toListValues.toArray(new String[0]));
			}
			ArrayList<String> fromListValues = new ArrayList<String>();
			for(FileVersionOption options : FileVersionOption.VALUES){
				fromListValues.add(options.getName());
			}
			setFromListInput(fromListValues.toArray(new String[0]));
			return control;
		}

		public FileVersionOptionDialog(Shell parentShell) {
			super(parentShell);
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText("File VersionOption Selection Dialog");
		}

		@Override
		protected Point getInitialSize() {
			return new Point(500, 300);
		}

		public ArrayList<FileVersionOption> getSelectedOptions() {
			ArrayList<FileVersionOption> options = new ArrayList<FileVersionOption>();
			for(String value : super.getSelectedValues()){
				options.add(FileVersionOption.getByName(value));
			}
			return options;
		}
	}
}

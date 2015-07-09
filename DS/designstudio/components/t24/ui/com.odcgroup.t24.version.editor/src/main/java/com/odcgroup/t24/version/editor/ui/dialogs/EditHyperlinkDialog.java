/**
 * 
 */
package com.odcgroup.t24.version.editor.ui.dialogs;

import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * @author arajeshwari
 *
 */
public class EditHyperlinkDialog extends AddItemsToListDialog {

	public EditHyperlinkDialog(Shell parentShell, ISWTObservable widget) {
		super(parentShell, widget);
		this.widget = widget;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getOKButton().setEnabled(true);
	}
	
	@Override
	protected void okPressed() {
		if(!text.getText().isEmpty()){
			((ISWTObservableValue)widget).setValue("<a>" + text.getText() + "</a>");
		}else{
			((ISWTObservableValue)widget).setValue(null);
		}
		super.okPressed();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit Hyperlink");
	}
}

package com.odcgroup.t24.helptext.ui.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class ImportErrorDilaog extends ErrorDialog {

    public ImportErrorDilaog(Shell parentShell, String dialogTitle,
	    String message, IStatus status, int displayMask) {
	super(parentShell, dialogTitle, message, status, displayMask);
	this.message =message;
	
    }
    @Override
    protected void createDialogAndButtonArea(Composite parent) {
	super.createDialogAndButtonArea(parent);
        createDropDownList(parent);
        
    }
    @Override
    protected boolean shouldShowDetailsButton() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	// create Continue and Cancel buttons
         createButton(parent, IDialogConstants.OK_ID, "Continue",true);
         createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL,true);
    }
    @Override
    protected void constrainShellSize() {
	getShell().setBounds(new Rectangle(getShell().getLocation().x+300, getShell().getLocation().y, 700, 500));
    }
    
}

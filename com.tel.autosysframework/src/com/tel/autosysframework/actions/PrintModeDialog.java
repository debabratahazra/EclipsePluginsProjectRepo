package com.tel.autosysframework.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.jface.dialogs.Dialog;

import org.eclipse.draw2d.PrintFigureOperation;



/**
 * @author Eric Bordeau
 */
public class PrintModeDialog extends Dialog {

private Button tile, fitPage, fitWidth, fitHeight;

public PrintModeDialog(Shell shell) {
	super(shell);
}

protected void cancelPressed() {
	setReturnCode(-1);
	close();
}

protected void configureShell(Shell newShell) {
	newShell.setText("Select Print Mode");
	super.configureShell(newShell);
}

protected Control createDialogArea(Composite parent) {
	Composite composite = (Composite)super.createDialogArea(parent);
	
	tile = new Button(composite, SWT.RADIO);
	tile.setText("Tile");
	tile.setSelection(true);
	
	fitPage = new Button(composite, SWT.RADIO);
	fitPage.setText("Fit Page");

	fitWidth = new Button(composite, SWT.RADIO);
	fitWidth.setText("Fit Width");

	fitHeight = new Button(composite, SWT.RADIO);
	fitHeight.setText("Fit Height");

	return composite;
}

protected void okPressed() {
	int returnCode = -1;
	if (tile.getSelection())
		returnCode = PrintFigureOperation.TILE;
	else if (fitPage.getSelection())
		returnCode = PrintFigureOperation.FIT_PAGE;
	else if (fitHeight.getSelection())
		returnCode = PrintFigureOperation.FIT_HEIGHT;
	else if (fitWidth.getSelection())
		returnCode = PrintFigureOperation.FIT_WIDTH;
	setReturnCode(returnCode);
	close();
}
}

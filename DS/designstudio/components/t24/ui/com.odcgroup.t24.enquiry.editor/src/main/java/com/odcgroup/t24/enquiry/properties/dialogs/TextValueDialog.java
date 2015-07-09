package com.odcgroup.t24.enquiry.properties.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author satishnangi
 *
 */
public  class TextValueDialog extends Dialog {
	private String code = null;
	private Text value = null;
	private String textValue = null;

	/**
	 * @param parentShell
	 */
	protected TextValueDialog(Shell parentShell) {
		super(parentShell);
	}

	protected TextValueDialog(Shell parentShell,String textValue) {
		super(parentShell);
		this.textValue = textValue;
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		container.setLayout(gridLayout);
		GridData containerdata = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(containerdata);
		value = new Text(container, SWT.BORDER);
		if(textValue != null){
			value.setText(textValue);
		}
		GridData codeTextData = new GridData(GridData.FILL_HORIZONTAL);
		value.setLayoutData(codeTextData);
		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(300, 150);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Value Dialog");
	}

	@Override
	protected void okPressed() {
		code = value.getText();
		super.okPressed();
	}

	public String getResult() {
		return code;
	}
}


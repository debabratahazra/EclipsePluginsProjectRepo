package com.odcgroup.t24.version.editor.ui.dialogs;

import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.jface.databinding.swt.ISWTObservableList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddItemsToListDialog extends Dialog {
	protected Text text;
	private final String DEFAULT_WINDOW_TITLE = "Add item";
	protected ISWTObservable widget;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param list_1
	 * @param dataBindingContext 
	 * @param listWidget 
	 * @param formEditor 
	 * @param vResource 
	 */
	public AddItemsToListDialog(Shell parentShell, ISWTObservable widget) {
		super(parentShell);
		setShellStyle(SWT.TITLE);
		this.widget = widget;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		Label lblItemName = new Label(container, SWT.NONE);
		lblItemName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblItemName.setText("Name:");

		text = new Text(container, SWT.BORDER);
		text.setTextLimit(100);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				String trimText = text.getText().trim();
				if(trimText != null && !trimText.isEmpty()){
					getOKButton().setEnabled(true);
				}else{
					getOKButton().setEnabled(false);
				}
				
			}
		});

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		
		getOKButton().setEnabled(false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(455, 151);
	}

	@Override
	protected void okPressed() {
		if(widget instanceof ISWTObservableList)
			((ISWTObservableList)widget).add(text.getText().trim());
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		// TODO Auto-generated method stub
		super.cancelPressed();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(DEFAULT_WINDOW_TITLE );
	}

}

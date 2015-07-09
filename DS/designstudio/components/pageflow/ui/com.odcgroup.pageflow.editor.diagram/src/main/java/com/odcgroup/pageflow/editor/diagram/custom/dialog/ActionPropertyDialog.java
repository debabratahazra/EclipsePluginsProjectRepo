package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

/**
 *
 * @author pkk
 *
 */
public class ActionPropertyDialog extends AbstractTitleAreaDialog {
	
	
	private ActionProperty actionProperty = null;
	private Text valField = null;
	private Text nameField = null;

	/**
	 * @param parentShell
	 * @param addCommand
	 */
	public ActionPropertyDialog(Shell parentShell, ActionProperty actionProperty) {
		super(parentShell);
		this.actionProperty = actionProperty;
	}
	
	
	
	/**
	 * @param parentShell
	 * @param addCommand
	 * @param update
	 */
	public ActionPropertyDialog(Shell parentShell, ActionProperty actionProperty, boolean update){
		this(parentShell, actionProperty);
		this.update = update;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		if (update){
			setMessage("Update Property");
		} else {
			setMessage("Create Property");
		}
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);		
		shell.setText("Property"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite body = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(body, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = 15;
		layout.marginHeight = 10;
		layout.numColumns = 2;	
		composite.setLayout(layout);
		
		Label nlabel = createLabel(composite, "Name");
		nlabel.setToolTipText("Property Name"); //$NON-NLS-1$
		nameField = createTextField(composite);
		if (update){
			nameField.setText(actionProperty.getName());
		}
		Label vlabel = createLabel(composite, "Value");
		vlabel.setToolTipText("Property Value"); //$NON-NLS-1$
		valField = createTextField(composite);
		if (update){
			valField.setText(actionProperty.getValue());
		}
		return composite;
	}

	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.widthHint = 100;
		label.setLayoutData(data);
		return label;
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Text createTextField(Composite parent) {
		Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = GridData.CENTER;
		data.grabExcessVerticalSpace = false;
		data.widthHint = 300;
		text.setLayoutData(data);
		return text;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if (validate()){
			actionProperty.setName(nameField.getText());	
			actionProperty.setValue(valField.getText());
			super.okPressed();
		}
	}
	
	private boolean validate() {
		if (nameField.getText() == null || nameField.getText().trim() == ""){
			this.setErrorMessage("Name is required");
			return false;
		}
		if (valField.getText() == null || valField.getText().trim() == "") {
			this.setErrorMessage("Value is required");
			return false;
		}
		return true;
	}



	public ActionProperty getActionProperty() {
		return actionProperty;
	}

	

}

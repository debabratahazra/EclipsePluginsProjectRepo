package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class ShowHideDescriptionSelectionDialog extends AbstractTitleAreaDialog {

	private Composite radio;

	/**
	 * @param parentShell
	 */
	public ShowHideDescriptionSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle(Messages.StateDescLabelDialogTitle);
		this.setMessage(Messages.StateDescLabelDialogMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.StateDescDialogShellTxt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		// create the composite to hold the widgets
		GridData gridData;

		Composite body = new Composite(composite, SWT.NULL);
		GridLayout layout = new GridLayout(1, false);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 4;
		body.setLayout(layout);
		body.setLayoutData(gridData);

		radio = new Composite(body, SWT.NULL);
		radio.setLayout(new GridLayout(1, false));

		boolean currVal = getPreferredValue();
		
		final Button fullClassBtn = new Button(radio, SWT.RADIO);
		fullClassBtn.setText(Messages.StateDescBtnShowLabel);
		if (currVal) {
			fullClassBtn.setSelection(true);
		}

		final Button actionBtn = new Button(radio, SWT.RADIO);
		actionBtn.setText(Messages.StateDescBtnHideLabel);
		if (!currVal) {
			actionBtn.setSelection(true);
		}

		Label empty = new Label(body, SWT.NONE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 4;
		empty.setLayoutData(gridData);

		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		Control[] child = radio.getChildren();
		for (int i = 0; i < child.length; i++) {
			Control control = child[i];
			if (control instanceof Button) {
				Button button = (Button) control;
				boolean val = false;
				if(button.getText().equals(Messages.StateDescBtnShowLabel)){
					val = true;
				} else {
					val = false;
				}
				if ((button.getStyle() & SWT.RADIO) != 0
						&& button.getSelection()) {
					getPreferenceStore().setValue(PageflowPreferenceConstants.PREF_DESC_LABEL_DISPLAY,  val);
				}
			}
		}
		super.okPressed();
	}

	/**
	 * @return
	 */
	private boolean getPreferredValue() {
		return getPreferenceStore().getBoolean(PageflowPreferenceConstants.PREF_DESC_LABEL_DISPLAY);
	}
	
	/**
	 * @return
	 */
	private IPreferenceStore getPreferenceStore() {
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

}

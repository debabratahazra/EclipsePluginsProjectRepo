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

import com.odcgroup.pageflow.editor.diagram.custom.util.ActionLabel;
import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class ActionLabelDisplaySelectionDialog extends AbstractTitleAreaDialog {

	private Composite radio;

	/**
	 * @param parentShell
	 */
	public ActionLabelDisplaySelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle(Messages.ActionLabelDialogTitle);
		this.setMessage(Messages.ActionLabelDialogMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.ActionLabelDialogShellTxt);
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

		String currVal = getPreferredValue();
		if (currVal == null) {
			currVal = ActionLabel.ACTION_NONE_LITERAL.getName();
		}
		final Button fullClassBtn = new Button(radio, SWT.RADIO);
		fullClassBtn.setText(ActionLabel.ACTION_URI_LITERAL
				.getName());
		if (currVal.equals(ActionLabel.ACTION_URI_LITERAL
				.getName())) {
			fullClassBtn.setSelection(true);
		}

		final Button actionBtn = new Button(radio, SWT.RADIO);
		actionBtn.setText(ActionLabel.ACTION_NAME_LITERAL.getName());
		if (currVal.equals(ActionLabel.ACTION_NAME_LITERAL.getName())) {
			actionBtn.setSelection(true);
		}

		final Button noActionBtn = new Button(radio, SWT.RADIO);
		noActionBtn.setText(ActionLabel.ACTION_NONE_LITERAL.getName());
		if (currVal.equals(ActionLabel.ACTION_NONE_LITERAL.getName())) {
			noActionBtn.setSelection(true);
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
				if ((button.getStyle() & SWT.RADIO) != 0
						&& button.getSelection()) {
					getPreferenceStore().setValue(PageflowPreferenceConstants.PREF_TRN_LABEL_ACTION_CONTENT, button.getText());
				}
			}
		}
		super.okPressed();
	}

	/**
	 * @return
	 */
	private String getPreferredValue() {
		return getPreferenceStore().getString(PageflowPreferenceConstants.PREF_TRN_LABEL_ACTION_CONTENT);
	}
	
	/**
	 * @return
	 */
	private IPreferenceStore getPreferenceStore() {
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

}

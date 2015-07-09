package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog for Creating a New Folder in Remote Site
 * 
 * @author yasar arafath
 * 
 */
public class RemoteSiteNewFolderDialog extends Dialog {

    private Label dialogLabel = null;
    private String folderName = "";
    private Text folderNameText;
    private Button okButton;

    /**
     * Creates a dialog instance. Note that the window will have no visual
     * representation (no widgets) until it is told to open. By default,
     * <code>open</code> blocks for dialogs.
     * 
     * @param parentShell the parent shell
     */
    public RemoteSiteNewFolderDialog(Shell parentShell) {
        super(parentShell);
    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        okButton = super.getButton(IDialogConstants.OK_ID);
        if (okButton != null) {
            okButton.setEnabled(false);
        }
    }

    /**
     * Configures the given shell in preparation for opening this window in it.
     * In this case, we set the dialog title.
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("New Folder Dialog");
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);
        dialogLabel = new Label(container, SWT.NONE);
        dialogLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));
        dialogLabel.setText("Specify your folder name");
        final Label folderNameLabel = new Label(container, SWT.NONE);
        folderNameLabel.setText("Folder Name");
        folderNameText = new Text(container, SWT.BORDER);
        folderNameText.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, true, false, 1, 1));
        setupListeners();
        updateNewFolderDialog();
        return container;
    }

    private void setupListeners() {
        folderNameText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                folderName = folderNameText.getText();
                updateNewFolderDialog();
            }
        });
    }

    public String getFolderName() {
        return folderName;
    }

    private void updateNewFolderDialog() {
        if (folderName.length() == 0) {
            enableOKButton(false);
        } else {
            enableOKButton(true);
        }
    }

    private void enableOKButton(boolean value) {
        if (okButton != null) {
            okButton.setEnabled(value);
        }
    }
}

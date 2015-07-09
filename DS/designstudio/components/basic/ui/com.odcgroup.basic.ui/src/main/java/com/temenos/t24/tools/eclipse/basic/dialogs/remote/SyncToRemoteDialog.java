package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.OperationType;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

public class SyncToRemoteDialog extends Dialog {

	private Combo siteNameCombo;
	private Text serverDirText;
	private Button installButton;
	private Button transferButton;
	private Button okButton;
	private String siteName = "";
	private String sourceDirectory = "";
	private OperationType operationType = OperationType.TRANSFER;
	private RemoteSitesManager remoteSitesManager = RemoteSitesManager
			.getInstance();

	public SyncToRemoteDialog(Shell parentShell) {
		super(parentShell);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		okButton = super.getButton(IDialogConstants.OK_ID);
		if (okButton != null) {
			okButton.setEnabled(false);
		}
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Sync To T24 Dialog");
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		final Label siteNameLabel = new Label(container, SWT.NONE);
		siteNameLabel.setLayoutData(new GridData(GridData.BEGINNING,
				GridData.CENTER, false, false));
		siteNameLabel.setText("Remote Site:");
		siteNameLabel.setToolTipText("Name of the remote site");
		siteNameCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData siteNameCombData = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false, 1, 1);
		siteNameCombData.minimumWidth = 125;
		siteNameCombo.setLayoutData(siteNameCombData);
		final Label serverDirLabel = new Label(container, SWT.NONE);
		serverDirLabel.setLayoutData(new GridData(GridData.BEGINNING,
				GridData.CENTER, false, false));
		serverDirLabel.setText("Source Item Directory:");
		serverDirLabel
				.setToolTipText("Server Directory in the remote site. Eg. BP");
		serverDirText = new Text(container, SWT.BORDER);
		GridData data = new GridData(GridData.BEGINNING, GridData.BEGINNING,
				true, false, 1, 1);
		data.minimumWidth = 150;
		data.grabExcessVerticalSpace = true;
		data.minimumHeight = 20;
		serverDirText.setLayoutData(data);
		final Label operationTypeLable = new Label(container, SWT.NONE);
		operationTypeLable.setText("Operation Type:");
		operationTypeLable.setToolTipText("Type of operation needs to perform");
		Group operationTypeButtonGroup = new Group(container, SWT.HORIZONTAL);
		GridLayout operationTypeButtonsLayout = new GridLayout();
		operationTypeButtonsLayout.numColumns = 2;
		installButton = new Button(operationTypeButtonGroup, SWT.RADIO);
		installButton.setText("Install");
		installButton
				.setToolTipText("Transfers the routine to Source Item Directory and compile the files");
		transferButton = new Button(operationTypeButtonGroup, SWT.RADIO);
		transferButton.setText("Transfer");
		transferButton
				.setToolTipText("Transfers the routine to Source Item Directory");
		transferButton.setSelection(true);
		operationTypeButtonGroup.setLayout(operationTypeButtonsLayout);
		setupListeners();
		initializeDialog();
		updateButtons();

		return container;
	}

	private void setupListeners() {

		siteNameCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				siteName = siteNameCombo.getText().trim();
				updateButtons();
			}
		});

		serverDirText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				sourceDirectory = serverDirText.getText().trim();
				updateButtons();
			}
		});

		installButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				if (installButton.getSelection()) {
					operationType = OperationType.INSTALL;
					updateButtons();
				}
			}
		});

		transferButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				if (transferButton.getSelection()) {
					operationType = OperationType.TRANSFER;
					updateButtons();
				}
			}
		});

	}

	private void initializeDialog() {
		siteNameCombo.setItems(getSites());
		siteNameCombo.setText(getSite());

	}

	private String getSite() {
		RemoteSite defaultsite = RemoteSitesManager.getInstance()
				.getDefaultSite();
		if (defaultsite != null) {
			return defaultsite.getSiteName();
		} else {
			return "";
		}
	}

	private String[] getSites() {
		if (remoteSitesManager.getRemoteSiteSize() > 0) {
			return remoteSitesManager.getRemoteSiteNames();
		} else {
			return new String[] { "" };
		}
	}

	public String getSiteName() {
		return siteName;
	}

	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public OperationType getSyncType() {
		return operationType;
	}

	private void updateButtons() {
		if (okButton == null) {
			return;
		}
		if (siteName.length() > 0 &&  sourceDirectory.length() > 0 && isValidDirectoryName(sourceDirectory)) {
			okButton.setEnabled(true);
		} else {
			okButton.setEnabled(false);
		}

	}
	
	
	private boolean isValidDirectoryName(String str) {
        boolean isValidDirectoryName = false;
        char chr[] = null;
        if (str != null)
            chr = str.toCharArray();
        for (int i = 0; i < chr.length; i++) {
            if ((chr[i] >= 'A' && chr[i] <= 'Z') || (chr[i] >= 'a' && chr[i] <= 'z') || (chr[i] == '.')|| (chr[i] >= '0' && chr[i] <= '9')) {
            	isValidDirectoryName = true;
                continue;
            } else {
            	 T24MessageDialog errorDialog = new T24MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                         "Error Dialog", "The directory name you specified is not valid.Please specify a different directory name.",
                         MessageDialog.ERROR);
                 errorDialog.open();          	
            	isValidDirectoryName = false;
                break;
            }
        }
        return isValidDirectoryName;
    }

}

package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

/**
 * Abstract class for Remote Operations dialog
 * 
 * @author vsanjai
 * 
 */
public abstract class RemoteOperationDialog extends Dialog {

    protected Text fileNameText;
    protected Text serverDirText;
    protected Combo siteNameCombo;
    protected Combo locationCombo;
    protected String siteName = "";
    protected String fileName = "";
    protected String serverDir = "";
    protected String folderName = "";
    protected Button newFolderButton;
    protected Button moveFolderButton;
    protected String currentLocation = null;
    protected RemoteSite remoteSite = null;
    protected RemoteSitesManager remoteSitesManager = RemoteSitesManager.getInstance();
    protected RemoteOperationsManager remoteOperationsManager = RemoteOperationsManager.getInstance();
    protected RemoteSite oldRemoteSite = null;
    protected Button okButton;
    protected Label locationLabel;
    private boolean canEnable = false;

    protected abstract void initializeDialog();

    public RemoteOperationDialog(Shell parentShell) {
        super(parentShell);
    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        okButton = super.getButton(IDialogConstants.OK_ID);
        if (okButton != null) {
            okButton.setEnabled(canEnable);
        }
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 4;
        container.setLayout(gridLayout);
        final Label dialogLabel = new Label(container, SWT.NONE);
        dialogLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 4, 1));
        dialogLabel.setText("Enter the details of remote site");
        final Label fileNameLabel = new Label(container, SWT.NONE);
        fileNameLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        fileNameLabel.setText("File Name :");
        fileNameLabel.setToolTipText("Name of the remote file");
        fileNameText = new Text(container, SWT.BORDER);
        GridData data = new GridData(GridData.BEGINNING, GridData.BEGINNING, true, false, 3, 1);
        data.minimumWidth = 150;
        data.grabExcessVerticalSpace = true;
        data.minimumHeight = 20;
        fileNameText.setLayoutData(data);
        final Label siteNameLabel = new Label(container, SWT.NONE);
        siteNameLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        siteNameLabel.setText("Site Name :");
        siteNameLabel.setToolTipText("Name of the remote site");
        siteNameCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
        GridData siteNameCombData = new GridData(GridData.BEGINNING, GridData.CENTER, true, false, 2, 1);
        siteNameCombData.minimumWidth = 125;
        siteNameCombo.setLayoutData(siteNameCombData);
        new Label(container, SWT.NONE);
        final Label serverDirLabel = new Label(container, SWT.NONE);
        serverDirLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        serverDirLabel.setText("Directory:");
        serverDirLabel.setToolTipText("Server Directory in the remote site. Eg. BP");
        serverDirText = new Text(container, SWT.BORDER);
        serverDirText.setLayoutData(data);
        locationLabel = new Label(container, SWT.NONE);
        locationLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        locationLabel.setText("Remote Location :");
        locationLabel.setToolTipText("Folder location of the file in the remote site");
        locationCombo = new Combo(container, SWT.DROP_DOWN | SWT.SIMPLE | SWT.READ_ONLY);
        GridData locationCombodata = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
        locationCombodata.minimumWidth = 350;
        locationCombo.setLayoutData(locationCombodata);
        moveFolderButton = new Button(container, SWT.BUTTON1);
        moveFolderButton.setText("..");
        moveFolderButton.setToolTipText("Navigate remote folder");
        newFolderButton = new Button(container, SWT.BUTTON1);
        newFolderButton.setText("New Folder");
        newFolderButton.setToolTipText("Create new remote folder");
        setupListeners();
        initializeDialog();
        updateButtons();
        return container;
    }
    
    private void setupListeners() {
        fileNameText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                fileName = fileNameText.getText();
                updateButtons();
            }
        });
        serverDirText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                serverDir = serverDirText.getText().trim();
                if (serverDir.length() <= 0) {
                    locationCombo.setEnabled(true);
                    moveFolderButton.setEnabled(true);
                    newFolderButton.setEnabled(true);
                }
                updateButtons();
            }
        });
        
        siteNameCombo.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                String siteName = siteNameCombo.getText();
                if (siteName == null || siteName.length() <= 0) {
                    return;
                }
                if (!RemoteSitesManager.getInstance().connect(siteName)) {
                    if (!remoteOperationsManager.isShowDialog()) {
                        MessageDialog.openError(Display.getDefault().getActiveShell(), "Unable to Connect", "Unable to connect to the "
                                + siteName + " server.");
                    }
                    if(remoteSite != null){
                        int index = siteNameCombo.indexOf(remoteSite.getSiteName());
                        if(index != -1){
                            siteNameCombo.select(index);
                        }
                    }else {
                        siteNameCombo.deselectAll();
                    }
                    return;
                }
                remoteSite = RemoteSitesManager.getInstance().getRemoteSiteFromName(siteName);
                String location = RemoteOperationsManager.getInstance().getHomeDirectory(remoteSite);
                currentLocation = location;
                String[] items = RemoteOperationsManager.getInstance().getDirectoryNames(remoteSite, location);
                locationCombo.setItems(items);
                locationCombo.setText(location);
                updateButtons();
            }
        });
        locationCombo.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // Nothing to do
            }

            public void widgetSelected(SelectionEvent e) {
                String location = locationCombo.getText();
                if (location.equals(currentLocation)) {
                    return;
                }
                currentLocation = location;
                String[] items = RemoteOperationsManager.getInstance().getDirectoryNames(remoteSite, location);
                locationCombo.removeAll();
                locationCombo.setItems(items);
                locationCombo.setText(currentLocation);
                updateButtons();
            }
        });
        moveFolderButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // Nothing to do
            }

            public void widgetSelected(SelectionEvent e) {
                siteName = siteNameCombo.getText();
                String location = remoteOperationsManager.getParentDirectory(remoteSitesManager.getRemoteSiteFromName(siteName),
                        currentLocation);
                String[] items = RemoteOperationsManager.getInstance().getDirectoryNames(remoteSite, location);
                currentLocation = location;
                locationCombo.setItems(items);
                locationCombo.setText(currentLocation);
                updateButtons();
            }
        });
        newFolderButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                folderName = newFolderButton.getText();
                siteName = siteNameCombo.getText();
                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                RemoteSiteNewFolderDialog dialog = new RemoteSiteNewFolderDialog(window.getShell());
                if (dialog.open() != InputDialog.OK) {
                    return;
                } else {
                    String newFolderName = dialog.getFolderName();
                    newFolderName = currentLocation + "/" + newFolderName;
                    if (remoteOperationsManager.createNewDirectory(newFolderName, remoteSitesManager
                            .getRemoteSiteFromName(siteName))) {
                        String[] items = RemoteOperationsManager.getInstance().getDirectoryNames(remoteSite, currentLocation);
                        locationCombo.setItems(items);
                        locationCombo.setText(currentLocation);
                        updateButtons();
                    }
                }
            }
        });
            }

    public String getSiteName() {
        return siteName;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public String getServerDir() {
        return serverDir;
    }

     private void updateButtons() {
        siteName = siteNameCombo.getText().trim();
        currentLocation = locationCombo.getText().trim();
        fileName = fileNameText.getText().trim();
        serverDir = serverDirText.getText().trim();
        disableAllButtons();
        if (currentLocation.length() > 0 && locationCombo.isEnabled()) {
            moveFolderButton.setEnabled(true);
        }
        if (locationCombo.isEnabled() && currentLocation.length() > 0 && siteName.length() > 0) {
            newFolderButton.setEnabled(true);
        }
        if (siteName.length() > 0 && fileName.length() > 0
                && ((serverDirText.isEnabled() && serverDir.length() > 0) || currentLocation.length() > 0)) {
            enableOKButton(true);
        }
    }

    private void disableAllButtons() {
        moveFolderButton.setEnabled(false);
        newFolderButton.setEnabled(false);
        enableOKButton(false);
    }

    private void enableOKButton(boolean value) {
        if (value == true) {
            canEnable = true;
        }
        if (okButton != null) {
            okButton.setEnabled(value);
        }
    }
}

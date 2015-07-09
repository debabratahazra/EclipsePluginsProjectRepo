package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

/**
 * class for Remote File Execute dialog
 * 
 * @author yasar
 * 
 */
public class RemoteFileExecuteDialog extends Dialog {

    protected Combo siteNameCombo;
    protected Combo locationCombo;
    protected String siteName = "";
    protected String folderName = "";
    protected Button newSiteButton;
    protected Button newFolderButton;
    protected Button moveFolderButton;
    protected String currentLocation = null;
    protected RemoteSite remoteSite = null;
    protected RemoteSitesManager remoteSitesManager = RemoteSitesManager.getInstance();
    protected RemoteOperationsManager remoteOperationsManager = RemoteOperationsManager.getInstance();
    protected RemoteSite oldRemoteSite = null;
    protected Button okButton;
    private RemoteSite defaultsite = RemoteSitesManager.getInstance().getDefaultSite();
    private boolean canEnable = false;

    public RemoteFileExecuteDialog(Shell parentShell) {
        super(parentShell);
    }

    /**
     * Configures the given shell in preparation for opening this window in it.
     * In this case, we set the dialog title.
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Execute T24Unit Test Dialog");
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
        GridData data = new GridData(GridData.BEGINNING, GridData.BEGINNING, true, false, 3, 1);
        data.minimumWidth = 150;
        data.grabExcessVerticalSpace = true;
        data.minimumHeight = 20;
        final Label siteNameLabel = new Label(container, SWT.NONE);
        siteNameLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        siteNameLabel.setText("Site Name :");
        siteNameLabel.setToolTipText("Name of the remote site");
        siteNameCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
        GridData siteNameCombData = new GridData(GridData.BEGINNING, GridData.CENTER, true, false, 2, 1);
        siteNameCombData.minimumWidth = 125;
        siteNameCombo.setLayoutData(siteNameCombData);
        newSiteButton = new Button(container, SWT.BUTTON1);
        newSiteButton.setText(" New Site  ");
        newSiteButton.setToolTipText("Create new remote site");
        newSiteButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        final Label locationLabel = new Label(container, SWT.NONE);
        locationLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
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
        siteNameCombo.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                String siteName = siteNameCombo.getText();
                if (siteName == null || siteName.length() <= 0) {
                    return;
                }
                remoteSite = RemoteSitesManager.getInstance().getRemoteSiteFromName(siteName);
                if (!RemoteSitesManager.getInstance().connect(siteName)) {
                    return;
                }
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

    private void updateButtons() {
        siteName = siteNameCombo.getText().trim();
        currentLocation = locationCombo.getText().trim();
        disableAllButtons();
        if (currentLocation.length() > 0) {
            moveFolderButton.setEnabled(true);
        }
        if (currentLocation.length() > 0 && siteName.length() > 0) {
            newFolderButton.setEnabled(true);
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

    private void initializeDialog() {
        siteNameCombo.setItems(getSites());
        newFolderButton.setEnabled(false);
        moveFolderButton.setEnabled(false);
        siteNameCombo.setText(getSite());
        locationCombo.setItems(getLocationNames());
        locationCombo.setText(getLocation());
    }

    private String[] getSites() {
        if (remoteSitesManager.getRemoteSiteSize() > 0) {
            return remoteSitesManager.getRemoteSiteNames();
        } else {
            return new String[] { "" };
        }
    }

    private String getSite() {
        if (defaultsite != null) {
            return defaultsite.getSiteName();
        } else {
            return "";
        }
    }

    private String getLocation() {
        if (defaultsite != null) {
            return RemoteOperationsManager.getInstance().getHomeDirectory(defaultsite);
        } else {
            return "";
        }
    }

    private String[] getLocationNames() {
        if (defaultsite != null) {
            locationCombo.removeAll();
            String location = RemoteOperationsManager.getInstance().getHomeDirectory(defaultsite);
            return RemoteOperationsManager.getInstance().getDirectoryNames(defaultsite, location);
        } else {
            return new String[] { "" };
        }
    }
}

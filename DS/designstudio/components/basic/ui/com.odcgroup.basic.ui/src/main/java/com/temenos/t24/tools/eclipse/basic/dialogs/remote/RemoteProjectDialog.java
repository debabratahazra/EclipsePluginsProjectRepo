package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * class for Remote Project dialog
 * 
 * @author yasar
 * 
 */
public class RemoteProjectDialog extends Dialog {
    Logger logger = LoggerFactory.getLogger(RemoteProjectDialog.class);
    
    protected Combo siteNameCombo;
    protected Combo locationCombo;
    protected String siteName = "";
    protected String folderName = "";
    protected Button newSiteButton;
    protected Button newFolderButton;
    protected Button moveFolderButton;
    protected String currentLocation = null;
    //protected RemoteSitesManagerActions siteManagerActions = new RemoteSitesManagerActions();
    protected RemoteSite remoteSite = null;
    protected RemoteSitesManager remoteSitesManager = RemoteSitesManager.getInstance();
    protected RemoteOperationsManager remoteOperationsManager = RemoteOperationsManager.getInstance();
    protected RemoteSite oldRemoteSite = null;
    protected Button okButton;
    protected ArrayList<IFile> basicFileList;
    protected Map<IFile, Boolean> basicFileSelectedMap = new HashMap<IFile, Boolean>();
    private final int LABEL_LENGTH = 14; // Labels = name, prompts, body
    private Table filesTable = null;
    private final int DIALOG_WIDTH = 300; // Dialog width pixels
    private RemoteSite defaultsite = RemoteSitesManager.getInstance().getDefaultSite();
    private boolean canEnable = false;

    public RemoteProjectDialog(Shell parentShell) {
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
        StringUtil su = new StringUtil();
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
        final Label filenameLabel = new Label(container, SWT.NONE);
        filenameLabel.setText(su.pad("Select Files :", LABEL_LENGTH, " "));
        filenameLabel.setLayoutData((new GridData()));
        // Build table
        filesTable = new Table(container, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
        filesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        filesTable.setHeaderVisible(false);
        filesTable.setLinesVisible(false);
        // Add columns + properties
        TableColumn column1 = new TableColumn(filesTable, SWT.NULL);
        column1.setWidth(30);
        TableColumn column2 = new TableColumn(filesTable, SWT.NULL);
        column2.setWidth(DIALOG_WIDTH);
        // Add items (rows) to the table
        for (int i = 0; i < basicFileSelectedMap.size(); i++) {
            new TableItem(filesTable, SWT.NONE);
        }
        TableItem[] tabItems = filesTable.getItems();
        // Iterate through all the items, adding text and check buttons
        for (int i = 0; i < tabItems.length; i++) {
            final int j = i;
            TableEditor editor = new TableEditor(filesTable);
            final Button button = new Button(filesTable, SWT.CHECK);
            button.pack();
            button.setSelection(true);
            editor.minimumWidth = button.getSize().x;
            editor.horizontalAlignment = SWT.CENTER;
            editor.setEditor(button, tabItems[i], 0);
            button.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(SelectionEvent e) {
                    // TODO Auto-generated method stub
                }

                public void widgetSelected(SelectionEvent event) {
                    basicFileSelectedMap.put(basicFileList.get(j), button.getSelection());
                    updateButtons();
                }
            });
            editor = new TableEditor(filesTable);
            Text text = new Text(filesTable, SWT.NONE);
            String fileName = "";
            fileName = basicFileList.get(j).getName();
            text.setText(fileName);
            editor.grabHorizontal = true;
            editor.setEditor(text, tabItems[i], 1);
        }
        setupListeners();
        initializeDialog();
        updateButtons();
        return container;
    }

    private void setupListeners() {
        /*newSiteButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub
            }

            public void widgetSelected(SelectionEvent e) {
                boolean siteCreated = false;
                siteName = siteNameCombo.getText();
                siteCreated = siteManagerActions.createNewSite();
                if (!siteCreated) {
                    return;
                }
                siteNameCombo.setItems(remoteSitesManager.getRemoteSiteNames());
                siteNameCombo.setText(siteName);
                updateButtons();
            }
        });*/
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
            if (isAtleastOneFileSelected()) {
                enableOKButton(true);
            } else {
                enableOKButton(false);
            }
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
        newFolderButton.setEnabled(false);
        moveFolderButton.setEnabled(false);
        siteNameCombo.setItems(getSites());
        siteNameCombo.setText(getSite());
        locationCombo.setItems(getLocationNames());
        locationCombo.setText(getLocation());
    }

    private String[] getSites() {
        if (remoteSitesManager.getRemoteSiteSize() == 0) {
            try {
                remoteSitesManager.loadAllRemoteSitesForce();
            } catch (T24ServerException e) {
                logger.error("Unable to load the active server.", e.getMessage());
            }
        }
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

    public Map<IFile, Boolean> getFilesSelected() {
        return this.basicFileSelectedMap;
    }

    /**
     * Checks whether atleast one file is selected in the Save project dialog
     * 
     * @return true if atleast one file is selected in the Save project dialog .
     *         False otherwise
     */
    private boolean isAtleastOneFileSelected() {
        Set<IFile> files = basicFileSelectedMap.keySet();
        for (final IFile file : files) {
            Boolean selected = (Boolean) basicFileSelectedMap.get(file);
            if (selected) {
                return true;
            }
        }
        return false;
    }
}

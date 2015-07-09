package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.swt.widgets.Shell;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsDialogHelper;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;

/**
 * Dialog for Opening a Remote File
 * 
 * @author yasar arafath
 * 
 */
public class RemoteFileOpenDialog extends RemoteOperationDialog {

    private String location = "";
    private String basicFileName = "";
    private RemoteSite remoteSite = null;

    public RemoteFileOpenDialog(Shell shell, String basicFilenameNoPrefix, RemoteSite remoteSite, String serverDir) {
        super(shell);
        this.basicFileName = basicFilenameNoPrefix;
        this.remoteSite = remoteSite;
        this.location = serverDir;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Open Remote File Dialog");
    }

    @Override
    protected void initializeDialog() {
        fileNameText.setText(basicFileName);
        siteNameCombo.setItems(getSites());
        newFolderButton.setVisible(false);
        locationCombo.setItems(getLocationNames());
        String serverDir = RemoteOperationsDialogHelper.getServerDirectory();
        if(serverDir!=null){
        serverDirText.setText(serverDir);
        }
           }

    private String[] getSites() {
        try {
            remoteSitesManager.loadAllRemoteSitesForce();
        } catch (T24ServerException e) {
            // Try to reload all Server details.
        }
        if (remoteSitesManager.getRemoteSiteSize() > 0) {
            return remoteSitesManager.getRemoteSiteNames();
        } else {
            return new String[] { "" };
        }
    }

    private String[] getLocationNames() {
        if (remoteSite != null) {
            locationCombo.removeAll();
            return RemoteOperationsManager.getInstance().getDirectoryNames(remoteSite, location);
        } else {
            return new String[] { "" };
        }
    }
}

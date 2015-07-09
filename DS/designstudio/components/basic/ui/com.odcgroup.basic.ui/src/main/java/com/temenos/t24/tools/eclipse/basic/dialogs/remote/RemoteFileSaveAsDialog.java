package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.swt.widgets.Shell;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsDialogHelper;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

/**
 * Dialog for Saving a File to RemoteSite
 * 
 * @author yasar arafath
 * 
 */
public class RemoteFileSaveAsDialog extends RemoteOperationDialog {

    private String location = "";
    private String basicFileName = "";
    private RemoteSite remoteSite = null;
    private RemoteSite defaultsite = RemoteSitesManager.getInstance().getDefaultSite();

    public RemoteFileSaveAsDialog(Shell shell, String basicFilenameNoPrefix, RemoteSite remoteSite, String serverDir) {
        super(shell);
        this.basicFileName = basicFilenameNoPrefix;
        this.remoteSite = remoteSite;
        this.location = serverDir;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Save to Remote Site Dialog");
    }

    @Override
    protected void initializeDialog() {
        fileNameText.setText(basicFileName);
        siteNameCombo.setItems(getSites());
        siteNameCombo.setText(getSite());
        locationCombo.setItems(getLocationNames());
        locationCombo.setText(getLocation());
        String serverDir = RemoteOperationsDialogHelper.getServerDirectory();
        if(serverDir!=null){
        serverDirText.setText(serverDir);
        }
            }

    private String[] getSites() {
        if (remoteSitesManager.getRemoteSiteSize() > 0) {
            return remoteSitesManager.getRemoteSiteNames();
        } else {
            return new String[] { "" };
        }
    }

    private String getSite() {
        if (remoteSite != null) {
            return remoteSite.getSiteName();
        } else if (defaultsite != null) {
            return defaultsite.getSiteName();
        } else {
            return "";
        }
    }

    private String getLocation() {
        if (remoteSite != null) {
            return location;
        } else if (defaultsite != null) {
            return RemoteOperationsManager.getInstance().getHomeDirectory(defaultsite);
        } else {
            return "";
        }
    }

    private String[] getLocationNames() {
        if (remoteSite != null) {
            locationCombo.removeAll();
            return RemoteOperationsManager.getInstance().getDirectoryNames(remoteSite, location);
        } else if (defaultsite != null) {
            locationCombo.removeAll();
            String location = RemoteOperationsManager.getInstance().getHomeDirectory(defaultsite);
            return RemoteOperationsManager.getInstance().getDirectoryNames(defaultsite, location);
        } else {
            return new String[] { "" };
        }
    }
}

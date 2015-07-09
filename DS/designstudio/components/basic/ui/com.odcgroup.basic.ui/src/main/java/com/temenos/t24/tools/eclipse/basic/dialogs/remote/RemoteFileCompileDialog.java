package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsDialogHelper;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

/**
 * Dialog for Compiling a File in RemoteSite
 * 
 * @author yasar arafath
 * 
 */
public class RemoteFileCompileDialog extends RemoteOperationDialog {
    Logger logger = LoggerFactory.getLogger(RemoteFileCompileDialog.class);
    
    private String basicFileName = "";
    private RemoteSite defaultsite = RemoteSitesManager.getInstance().getDefaultSite();

    public RemoteFileCompileDialog(Shell shell, String basicFilenameNoPrefix) {
        super(shell);
        this.basicFileName = basicFilenameNoPrefix;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Compile File Dialog");
    }

    @Override
    protected void initializeDialog() {
        fileNameText.setText(basicFileName);
        fileNameText.setEnabled(false);
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

    private String[] getLocationNames() {
        if (defaultsite != null) {
            locationCombo.removeAll();
            String location = RemoteOperationsManager.getInstance().getHomeDirectory(defaultsite);
            return RemoteOperationsManager.getInstance().getDirectoryNames(defaultsite, location);
        } else {
            return new String[] { "" };
        }
    }

    private String getLocation() {
        if (defaultsite != null) {
            return RemoteOperationsManager.getInstance().getHomeDirectory(defaultsite);
        } else {
            return "";
        }
    }
}

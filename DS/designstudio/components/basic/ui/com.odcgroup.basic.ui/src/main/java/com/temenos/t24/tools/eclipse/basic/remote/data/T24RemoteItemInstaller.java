package com.temenos.t24.tools.eclipse.basic.remote.data;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.jremote.IJremoteClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitePlatform;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

/**
 * This class is responsible for installing any source or data items into T24
 * Server.<br>
 * A source item would be compiled from the remote directory chosen. A data item
 * would be installed into the respective application through the standard T24
 * release
 * 
 * @author ssethupathi
 * 
 */
public class T24RemoteItemInstaller {

    private static T24RemoteItemInstaller INSTANCE = new T24RemoteItemInstaller();
    private String remotePgm = null;

    private T24RemoteItemInstaller() {
    }

    public static T24RemoteItemInstaller getInstance() {
        return INSTANCE;
    }

    /**
     * Installs source items in the remote T24 Server.
     * 
     * @param items list of {@link InstallableItem}
     * @param remoteDir remote directory
     * @param siteName remote site name
     * @param monitor progress monitor
     */
    public List<String> installSource(List<InstallableItem> items, String remoteDir, String siteName, IProgressMonitor monitor) {
        remotePgm = PluginConstants.T24_INSTALL_SOURCE;
        return install(items, remoteDir, siteName, monitor, PluginConstants.SOURCE_TYPE);
    }

    /**
     * Installs data items in the remote T24 Server.
     * 
     * @param items list of {@link InstallableItem}
     * @param remoteDir remote directory
     * @param siteName remote site name
     * @param monitor progress monitor
     */
    public List<String> installData(List<InstallableItem> items, String remoteDir, String siteName, IProgressMonitor monitor) {
        remotePgm = PluginConstants.T24_INSTALL_DATA;
        return install(items, remoteDir, siteName, monitor, PluginConstants.DATA_TYPE);
    }

    private List<String> install(List<InstallableItem> items, String remoteDir, String siteName, IProgressMonitor monitor,
            String itemType) {
        List<String> transferedFiles = T24RemoteItemTransfer.getInstance().transfer(items, remoteDir, siteName, monitor);
        String[] args = new String[3];
        args[0] = RemoteInstallerHelper.buildString(transferedFiles);
        if (itemType.equals(PluginConstants.SOURCE_TYPE)) {
            args[1] = remoteDir;
        } else {
            args[1] = getRemoteDir();
        }
        args[2] = "";
        JSubroutineParameters params = RemoteInstallerHelper.getParameters(args);
        params = executeRemoteSubroutine(siteName, params, monitor);
        if (params == null) {
            return null;
        }
        String[] resultArray = RemoteInstallerHelper.getStringParams(params);
        String result = resultArray[2];
        RemoteOperationsLog.info("Result returned from " + remotePgm + " is:\n" + result);
        return transferedFiles;
    }

    private String getRemoteDir() {
        RemoteSitePlatform defaultSitePlatform = RemoteSitesManager.getInstance().getDefaultSite().getPlatform();
        String remoteDir = "";
        if (RemoteSitePlatform.NT.equals(defaultSitePlatform)) {
            remoteDir = PluginConstants.SERVER_PRIMARY_DATA;
        } else {
            remoteDir = T24DataTransferHelper.getInstance().getDataTempDir();
        }
        return remoteDir;
    }

    private JSubroutineParameters executeRemoteSubroutine(String siteName, JSubroutineParameters params, IProgressMonitor monitor) {
        IJremoteClient client = RemoteSitesManager.getInstance().getJremoteClient(siteName);
        try {
            monitor.subTask("Executing subroutine " + remotePgm + "...");
            params = client.run(remotePgm, params);
            return params;
        } catch (JSubroutineNotFoundException e) {
            RemoteOperationsLog.error("Subroutine not found. " + e.getMessage());
        } catch (JRemoteException e) {
            RemoteOperationsLog.error("Remote Error. " + e.getMessage());
        }
        return null;
    }
}

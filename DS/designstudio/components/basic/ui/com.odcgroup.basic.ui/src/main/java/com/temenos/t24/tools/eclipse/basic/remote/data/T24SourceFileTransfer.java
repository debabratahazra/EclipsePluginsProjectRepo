package com.temenos.t24.tools.eclipse.basic.remote.data;

import org.eclipse.core.resources.IFile;

import com.jbase.jremote.JRemoteException;
import com.temenos.t24.tools.eclipse.basic.jremote.IJremoteClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

public class T24SourceFileTransfer {

    private static T24SourceFileTransfer INSTANCE = new T24SourceFileTransfer();
    private RemoteSitesManager rsm = RemoteSitesManager.getInstance();
    private RemoteOperationsManager rom = RemoteOperationsManager.getInstance();

    private T24SourceFileTransfer() {
    }

    public static T24SourceFileTransfer getInstance() {
        return INSTANCE;
    }

    public boolean installToT24(String fileName, String localPath) {
        RemoteSite defaultSite = rsm.getDefaultSite();
        if (defaultSite == null) {
            // TODO: handle null
        }
        IFile file = EditorDocumentUtil.getIFile(localPath);
        fileName = StringUtil.removeBasicExtension(fileName);
        boolean saved = transferSource(defaultSite, fileName, file);
        if (!saved) {
            RemoteOperationsLog.error("Unable to save file");
            return false;
        }
//        RemoteCompileAction compileAction = new RemoteCompileAction(defaultSite, getRemoteDir(), fileName);
        IJremoteClient jremoteClient = rsm.getJremoteClient(defaultSite.getSiteName());
        try {
            jremoteClient.compile(getRemoteDir(), fileName);
        } catch (JRemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean transferSource(RemoteSite remoteSite, String fileName, IFile file) {
        rom.saveFile(remoteSite, getRemoteDir(), fileName, file);
        return true;
    }

    private String getRemoteDir() {
        IT24FTPClient ftpClient = rsm.getDefaultFtpClient();
        String remoteDir = ftpClient.getHomeDir();
        remoteDir = rom.getParentDirectory(rsm.getDefaultSite(), remoteDir) + "/GLOBUS.BP";
        return remoteDir;
    }
}

/**
 * 
 */
package com.temenos.t24.tools.eclipse.basic.remote.data;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteFileTransferUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitePlatform;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

/**
 * This class takes care of transferring T24 data items between T24 server and
 * eclipse plug-in.
 * 
 * @author ssethupathi
 * 
 */
public class T24DataFileTransfer {

    private static T24DataFileTransfer instance = new T24DataFileTransfer();
    private T24DataTransferHelper helper = T24DataTransferHelper.getInstance();

    private T24DataFileTransfer() {
    }

    /**
     * Returns this instance.
     * 
     * @return
     */
    public static T24DataFileTransfer getInstance() {
        return instance;
    }

    /**
     * Retrieves the data item from remote site.
     * 
     * @param localPath local path
     * @param localName file name
     * @return true if retrieved, false otherwise.
     */
    public boolean getFromT24(String localPath, String localName) {
        if (!helper.isSiteAvailable()) {
            return false;
        }
        String fileName = UnsupportedCharConversion.convertFromT24(localName);
        boolean transfered = getToTemp(localPath, fileName);
        if (transfered) {
            return retriveFromTemp(localPath, fileName);
        }
        return false;
    }

    /**
     * Sends the data item to remote site.
     * 
     * @param localPath local path
     * @param localName file name
     * @return true if done, false otherwise.
     */
    public boolean sendToT24(String localPath, String localName) {
        if (!helper.isSiteAvailable()) {
            return false;
        }
        String fileName = UnsupportedCharConversion.convertToT24(localName);
        boolean sent = sendToTemp(localPath, fileName);
        if (sent) {
            return releaseToFile(localPath, fileName);
        }
        return false;
    }

    private boolean getToTemp(String localPath, String fileName) {
        fileName = RemoteFileTransferUtil.removeDataFileExtension(fileName);
        String t24FileName = helper.getT24FileName(fileName);
        String t24RecordKey = helper.getT24RecordKey(fileName);
        String[] args = { t24FileName, t24RecordKey, "" };
        args = helper.callSubroutine(PluginConstants.T24SUBROUTINE_DATA_GET, args);
        return checkResult(args, 3, 2);
    }

    private boolean retriveFromTemp(String localPath, String fileName) {
        RemoteSitePlatform defaultSitePlatform = RemoteSitesManager.getInstance().getDefaultSite().getPlatform();
        String remoteName = RemoteFileTransferUtil.removeDataFileExtension(fileName);
        if (RemoteSitePlatform.UNIX.equals(defaultSitePlatform) || RemoteSitePlatform.LINUX.equals(defaultSitePlatform)) {
            remoteName = remoteName.replaceFirst("!", ">");
        }
        String remotePath = helper.getDataTempDir() + "/" + remoteName;
        localPath = helper.retriveFile(localPath, remotePath);
        if (localPath == null) {
            return false;
        }
        return true;
    }

    private boolean releaseToFile(String localPath, String fileName) {
        RemoteSitePlatform defaultSitePlatform = RemoteSitesManager.getInstance().getDefaultSite().getPlatform();
        fileName = RemoteFileTransferUtil.removeDataFileExtension(fileName);
        String t24FileName = helper.getT24FileName(fileName);
        String t24RecordKey = helper.getT24RecordKey(fileName);
        String remoteDir = "";
        if (RemoteSitePlatform.NT.equals(defaultSitePlatform)) {
            remoteDir = PluginConstants.SERVER_PRIMARY_DATA;
        } else {
            remoteDir = T24DataTransferHelper.getInstance().getDataTempDir();
        }
        String[] args = { t24FileName, t24RecordKey, remoteDir, "RTC", "" };
        args = helper.callSubroutine(PluginConstants.T24SUBROUTINE_DATA_SEND, args);
        return checkResult(args, 5, 4);
    }

    private boolean sendToTemp(String localPath, String fileName) {
        RemoteSitePlatform defaultSitePlatform = RemoteSitesManager.getInstance().getDefaultSite().getPlatform();
        String remoteName = RemoteFileTransferUtil.removeDataFileExtension(fileName);
        if (RemoteSitePlatform.UNIX.equals(defaultSitePlatform) || RemoteSitePlatform.LINUX.equals(defaultSitePlatform)) {
            remoteName = remoteName.replaceFirst("!", ">");
        }
        String remotePath = helper.getDataTempDir();
        boolean sent = helper.sendFile(localPath, remotePath, remoteName);
        return sent;
    }

    private boolean checkResult(String[] returnParams, int argsCount, int resultArg) {
        if (returnParams == null || returnParams.length != argsCount) {
            helper.informFailure("Invalid parameters returned from T24 Subroutine");
            return false;
        }
        String errorMessage = returnParams[resultArg];
        if (errorMessage != null && errorMessage.length() > 0) {
            helper.informFailure("Failed to transfer data record.\n" + errorMessage);
            return false;
        }
        return true;
    }
}

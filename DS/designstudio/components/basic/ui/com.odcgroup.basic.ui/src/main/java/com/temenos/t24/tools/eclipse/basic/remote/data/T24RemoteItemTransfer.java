package com.temenos.t24.tools.eclipse.basic.remote.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteFileTransferUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitePlatform;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * This class is responsible for transferring T24 source/data items to a remote
 * T24 Server.<br>
 * 
 * @author ssethupathi
 * 
 */
public class T24RemoteItemTransfer {

    private static T24RemoteItemTransfer INSTANCE = new T24RemoteItemTransfer();
    private RemoteSitesManager rsm;
    private RemoteOperationsManager rom;
    private List<String> createdDirectories;

    private T24RemoteItemTransfer() {
        rsm = RemoteSitesManager.getInstance();
        rom = RemoteOperationsManager.getInstance();
        createdDirectories = new ArrayList<String>();
    }

    public static T24RemoteItemTransfer getInstance() {
        return INSTANCE;
    }

    /**
     * Performs the transfer. The source file structure is not maintained at the
     * destination.
     * 
     * @param items list of {@link InstallableItem}
     * @param remoteRoot root directory in the remote site
     * @param remoteSiteName remote site name
     * @param monitor progress monitor
     * @return list of transfered file names.
     */
    public List<String> transfer(List<InstallableItem> items, String remoteRoot, String remoteSiteName, IProgressMonitor monitor) {
        return transfer(items, null, remoteRoot, remoteSiteName, monitor);
    }

    /**
     * Performs the transfer. The source file structure is maintained at the
     * destination.
     * 
     * @param items list of {@link InstallableItem}
     * @param localRoot local root
     * @param remoteRoot root directory in the remote site
     * @param remoteSiteName remote site name
     * @param monitor progress monitor
     * @return list of transfered file names.
     */
    public List<String> transfer(List<InstallableItem> items, String localRoot, String remoteRoot, String siteName,
            IProgressMonitor monitor) {
        createRemoteRoot(remoteRoot, siteName);
        createdDirectories.clear();
        List<String> transferedItems = new ArrayList<String>();
        RemoteSite remoteSite = rsm.getRemoteSiteFromName(siteName);
        String transferedItem = null;
        int filesTotal = items.size();
        int processed = 0;
        monitor.subTask("Transfering files to remote site " + siteName + "...");
        for (InstallableItem item : items) {
            transferedItem = transfer(item, localRoot, remoteRoot, remoteSite);
            if (transferedItem != null && transferedItem.length() > 0) {
                processed++;
                monitor.subTask("Transfering files to remote site " + siteName + "(" + processed + "/" + filesTotal + ")");
                transferedItems.add(transferedItem);
            }
        }
        return transferedItems;
    }

    private void createRemoteRoot(String remoteRoot, String siteName) {
        RemoteSite remoteSite = rsm.getRemoteSiteFromName(siteName);
        RemoteOperationsManager.getInstance().createNewDirectory(remoteRoot, remoteSite);
    }

    private String transfer(InstallableItem item, String localRoot, String remotePath, RemoteSite remoteSite) {
        String fileName = item.getName();
        String localPath = item.getLocalPath();
        IFile file = EditorDocumentUtil.getIFile(localPath);
        if (item.getType().equals(ItemType.DATA)) {
            fileName = UnsupportedCharConversion.convertToT24(fileName);
            fileName = RemoteFileTransferUtil.removeDataFileExtension(fileName);
            if (!remoteSite.getPlatform().equals(RemoteSitePlatform.NT)) {
                fileName = fileName.replaceFirst("!", ">");
            }
        } else {
            fileName = StringUtil.removeBasicExtension(fileName);
        }
        if (localRoot != null) {
            String extendedRemotePath = RemoteFileTransferUtil.getExtendedeRmotePath(localRoot, item.getLocalPath(), remotePath);
            String[] children = RemoteFileTransferUtil.getSubDirectories(remotePath, extendedRemotePath);
            if (children != null) {
                createDir(remoteSite, remotePath, children);
                remotePath = extendedRemotePath;
            }
        }
        boolean saved = rom.saveFile(remoteSite, remotePath, fileName, file);
        if (!saved) {
            RemoteOperationsLog.error("Unable to transfer file " + fileName);
            return null;
        } else {
            RemoteOperationsLog.info("Transfering file " + fileName + " to " + remotePath);
        }
        return fileName;
    }

    private void createDir(RemoteSite remoteSite, String root, String[] children) {
        if (root == null || children == null) {
            return;
        }
        for (String dir : children) {
            if (dir == null || dir.length() <= 0) {
                continue;
            }
            root = root + "/" + dir;
            if (createdDirectories.contains(root)) {
                continue;
            }
            boolean created = rom.createNewDirectory(root, remoteSite);
            if (created) {
                createdDirectories.add(root);
            }
        }
    }
}

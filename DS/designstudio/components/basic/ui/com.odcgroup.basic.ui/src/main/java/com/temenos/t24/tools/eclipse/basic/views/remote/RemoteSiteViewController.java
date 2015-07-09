package com.temenos.t24.tools.eclipse.basic.views.remote;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteConnectionException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

/**
 * This class acts as a data controller to the associated {@link RemoteSiteView}
 * . It has reference to the {@link IT24FTPClient} through which it receives
 * data required by the view.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteSiteViewController {

    private IT24FTPClient ftpClient;
    private String pwd;

    /**
     * Controller for the {@link RemoteSiteView}.
     * 
     * @param ftpClient
     */
    public RemoteSiteViewController(IT24FTPClient ftpClient) {
        this.ftpClient = ftpClient;
        if (checkRemote()) {
            this.pwd = ftpClient.printWorkingDir();
        }
    }

    /**
     * Empties the view.
     * 
     * @return empty tree
     */
    public IT24TreeViewRoot emptyView() {
        IT24TreeViewRoot emptyRoot = new RemoteSiteRoot();
        return emptyRoot;
    }

    /**
     * Returns the child nodes of the node passed in.<br>
     * This is used when the node is expanded in the tree view.
     * 
     * @param parentNode
     * @return array of child nodes
     */
    public IT24TreeViewNode[] getChildren(IT24TreeViewNode parentNode) {
        if (!checkRemote()) {
            return null;
        }
        if (parentNode.hasChildren()) {
            IRemoteFile parentFile = ((RemoteFileNode) parentNode).getRemoteFile();
            List<IRemoteFile> files = new ArrayList<IRemoteFile>();
            try {
                files = ftpClient.getFiles(parentFile.getFullPath());
            } catch (RemoteOperationsException e) {
                String message = FTPClientImplConstants.ERROR_GET_LIST;
                RemoteOperationsLog.error(message + " " + e.getMessage());
            }
            List<IT24TreeViewNode> nodes = new ArrayList<IT24TreeViewNode>();
            if (files == null) {
                return null;
            }
            RemoteSite remoteSite = ftpClient.getRemoteSite();
            for (IRemoteFile remoteFile : files) {
                nodes.add(new RemoteFileNode(remoteSite, remoteFile, (RemoteFileNode) parentNode));
            }
            IT24TreeViewNode[] children = new IT24TreeViewNode[files.size()];
            nodes.toArray(children);
            return children;
        }
        return null;
    }

    /**
     * Returns the tree root to display in the view.
     * 
     * @return {@link IT24TreeViewRoot} tree root.
     */
    public IT24TreeViewRoot getTree() {
        return getTree(getParentNode());
    }

    /**
     * Returns the tree with the passed-in node as the parent of the root.
     * 
     * @param parentNode
     * @return {@link IT24TreeViewRoot} tree root.
     */
    public IT24TreeViewRoot getTree(RemoteFileNode parentNode) {
        if (parentNode == null) {
            parentNode = getParentNode();
        }
        IT24TreeViewRoot tree = new RemoteSiteRoot();
        List<IRemoteFile> files = getRemoteFiles();
        for (IRemoteFile file : files) {
            tree.addParentNode(new RemoteFileNode(ftpClient.getRemoteSite(), file, parentNode));
        }
        return tree;
    }

    /**
     * Closes the view.
     */
    public void closeView() {
        RemoteSiteViewManager.getInstance().closeView(ftpClient.getRemoteSite());
    }

    /**
     * Deletes the selected file at the {@link RemoteSite}.
     * 
     * @param remoteFile
     * @return true if deleted, and false otherwise.
     */
    public boolean deleteFile(IRemoteFile remoteFile) {
        if (checkRemote()) {
            try {
                if (remoteFile.isDirectory()) {
                    return ftpClient.deleteDirectory(remoteFile.getFullPath());
                }
                return ftpClient.deleteFile(remoteFile.getFullPath());
            } catch (RemoteOperationsException e) {
                String message = FTPClientImplConstants.ERROR_DELETE_FILE;
                RemoteOperationsLog.error(message + " " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Changes the present working directory of the view to its parent directory
     * to access the files in the parent directory.
     * 
     * @return true if pwd is changed, and false otherwise.
     */
    public boolean changeToParentDir() {
        if (checkRemote()) {
            try {
                String oldDir = ftpClient.printWorkingDir();
                ftpClient.changeWorkingDir(pwd);
                ftpClient.changeToParentDirectory();
                pwd = ftpClient.printWorkingDir();
                ftpClient.changeWorkingDir(oldDir);
                return true;
            } catch (RemoteOperationsException e) {
                String message = FTPClientImplConstants.ERROR_GENERIC_OPR;
                RemoteOperationsLog.error(message + " " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Changes the pwd to the selected node thus making it root of the view.
     * 
     * @param selectedNode
     * @return true if successful, false otherwise.
     */
    public boolean goIntoDirectory(RemoteFileNode selectedNode) {
        if (selectedNode == null) {
            return false;
        }
        return changeDirectory(selectedNode);
    }

    /**
     * Returns the view name which is the name of associated {@link RemoteSite}.
     * 
     * @return name of the view.
     */
    public String getViewName() {
        return ftpClient.getRemoteSite().getSiteName();
    }

    /**
     * Returns the tooltip text for the view.
     * 
     * @return tooltip text
     */
    public String getViewTooltip() {
        String tooltipText = ftpClient.getRemoteSite().getUserName() + "@" + ftpClient.getRemoteSite().getHostIP() + "\n" + pwd;
        return tooltipText;
    }

    /**
     * Returns the associated {@link IT24FTPClient}.
     * 
     * @return {@link IT24FTPClient}
     */
    public IT24FTPClient getFtpClient() {
        return ftpClient;
    }

    public RemoteFileNode getParentNode() {
        IRemoteFile parentFile = ftpClient.getParentFile(pwd);
        if (parentFile == null) {
            return null;
        }
        RemoteFileNode parentNode = new RemoteFileNode(ftpClient.getRemoteSite(), parentFile, null);
        return parentNode;
    }

    private List<IRemoteFile> getRemoteFiles() {
        List<IRemoteFile> files = new ArrayList<IRemoteFile>();
        if (!checkRemote()) {
            return files;
        }
        try {
            files = ftpClient.getFiles(pwd);
        } catch (RemoteOperationsException e) {
            String message = FTPClientImplConstants.ERROR_GET_LIST;
            RemoteOperationsLog.error(message + " " + e.getMessage());
        }
        return files;
    }

    /**
     * Changes the pwd to the node passed in.
     * 
     * @param node
     * @return true if successful, false otherwise.
     */
    private boolean changeDirectory(RemoteFileNode node) {
        if (!checkRemote()) {
            return false;
        }
        String remotePath = node.getRemoteFile().getFullPath();
        String oldDir = ftpClient.printWorkingDir();
        ftpClient.changeWorkingDir(remotePath);
        pwd = ftpClient.printWorkingDir();
        ftpClient.changeWorkingDir(oldDir);
        return true;
    }

    private boolean checkRemote() {
        if (ftpClient.isConnected() && ftpClient.sendNoOp()) {
            return true;
        }
        try {
            ftpClient.disconnect();
            return ftpClient.connect();
        } catch (RemoteConnectionException e) {
            return false;
        }
    }
}

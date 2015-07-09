package com.temenos.t24.tools.eclipse.basic.views.remote;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * This class represents a node in the Remote System tree view. It has
 * references to it's parent node, corresponding remote file and the remote
 * site.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteFileNode implements IT24TreeViewNode {

    private RemoteFileNode parent;
    private RemoteSite site;
    private IRemoteFile remoteFile;
    private static Image fileImage = FTPClientImplConstants.REMOTE_FILE_IMAGE;
    private static Image directoryImage = FTPClientImplConstants.REMOTE_DIR_IMAGE;

    public RemoteFileNode(RemoteSite site, IRemoteFile remoteFile, RemoteFileNode parent) {
        this.site = site;
        this.remoteFile = remoteFile;
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getChildren() {
        RemoteSiteViewController viewController = RemoteSiteViewManager.getInstance().getViewController(site);
        return viewController.getChildren(RemoteFileNode.this);
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage() {
        if (remoteFile.isDirectory()) {
            return directoryImage;
        } else {
            return fileImage;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel() {
        return remoteFile.getName();
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasChildren() {
        return isDirectory();
    }

    /**
     * Returns the {@link IRemoteFile} represented by this node.
     * 
     * @return {@link IRemoteFile}
     */
    public IRemoteFile getRemoteFile() {
        return remoteFile;
    }

    /**
     * Returns whether the {@link IRemoteFile} represented by this node is a
     * directory or not.
     * 
     * @return true/false
     */
    public boolean isDirectory() {
        return remoteFile.isDirectory();
    }

    public RemoteSite getSite() {
        return site;
    }
}

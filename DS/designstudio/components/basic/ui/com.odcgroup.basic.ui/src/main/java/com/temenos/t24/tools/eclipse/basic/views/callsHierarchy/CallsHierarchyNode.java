package com.temenos.t24.tools.eclipse.basic.views.callsHierarchy;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * A node in the tree of the {@link CallsHierarchyView}.
 * 
 * @author ssethupathi
 * 
 */
public class CallsHierarchyNode implements IT24TreeViewNode {

    private T24Subroutine subroutine;
    private IT24TreeViewNode parent;
    private IT24TreeViewNode[] children;
    private static Image fileImage = FTPClientImplConstants.REMOTE_FILE_IMAGE;

    public CallsHierarchyNode(T24Subroutine subroutine, IT24TreeViewNode parent) {
        this.subroutine = subroutine;
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getChildren() {
        if (children != null) {
            return children;
        }
        List<IT24TreeViewNode> childNodes = CallsHierarchyViewController.getInstance().getCalledSubroutines(this);
        children = new IT24TreeViewNode[childNodes.size()];
        childNodes.toArray(children);
        return children;
    }

    /**
     * Returns the {@link T24Subroutine} represented by this node.
     * 
     * @return T24 Subroutine
     */
    public T24Subroutine getSubroutine() {
        return subroutine;
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage() {
        return fileImage;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel() {
        return subroutine.getName();
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
        return true;
    }
}

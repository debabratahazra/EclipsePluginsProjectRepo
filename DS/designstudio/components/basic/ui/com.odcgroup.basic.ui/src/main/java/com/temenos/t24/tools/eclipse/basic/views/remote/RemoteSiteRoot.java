package com.temenos.t24.tools.eclipse.basic.views.remote;

import java.util.ArrayList;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

/**
 * This class represents the {@link IT24TreeViewRoot} of the Remote System tree
 * view.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteSiteRoot implements IT24TreeViewRoot {

    private ArrayList<RemoteFileNode> files = new ArrayList<RemoteFileNode>();

    /**
     * {@inheritDoc}
     */
    public void addParentNode(IT24TreeViewNode node) {
        if (node instanceof RemoteFileNode) {
            files.add((RemoteFileNode) node);
        }
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getParentNodes() {
        int arraySize = files.size();
        IT24TreeViewNode[] parentArray = new IT24TreeViewNode[arraySize];
        files.toArray(parentArray);
        return parentArray;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNodes() {
        if (files.size() > 0) {
            return true;
        }
        return false;
    }
}

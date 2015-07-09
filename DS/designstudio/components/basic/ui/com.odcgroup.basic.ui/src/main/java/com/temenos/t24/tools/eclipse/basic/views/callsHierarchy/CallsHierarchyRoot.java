package com.temenos.t24.tools.eclipse.basic.views.callsHierarchy;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

/**
 * Root of the tree in the {@link CallsHierarchyView}.
 * 
 * @author ssethupathi
 * 
 */
public class CallsHierarchyRoot implements IT24TreeViewRoot {

    private List<IT24TreeViewNode> nodes = new ArrayList<IT24TreeViewNode>();

    /**
     * {@inheritDoc}
     */
    public void addParentNode(IT24TreeViewNode node) {
        nodes.add(node);
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getParentNodes() {
        int size = nodes.size();
        IT24TreeViewNode[] nodeArr = new IT24TreeViewNode[size];
        nodes.toArray(nodeArr);
        return nodeArr;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNodes() {
        return true;
    }
}

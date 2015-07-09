package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

/**
 * root of the In-line document tree
 * 
 * @author sbharathraja
 * 
 */
public class T24InLineDocsTree implements IT24TreeViewRoot {

    /** collection of parent nodes */
    private List<IT24TreeViewNode> parents = new ArrayList<IT24TreeViewNode>();

    public void addParentNode(IT24TreeViewNode node) {
        if (node instanceof InLineRootNode)
            parents.add((InLineRootNode) node);
    }

    public IT24TreeViewNode[] getParentNodes() {
        IT24TreeViewNode[] parentNodes = new IT24TreeViewNode[parents.size()];
        parents.toArray(parentNodes);
        return parentNodes;
    }

    public boolean hasNodes() {
        if (parents != null && parents.size() > 0)
            return true;
        else
            return false;
    }
}

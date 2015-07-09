package com.temenos.t24.tools.eclipse.basic.views.document;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

public class T24ComponentTree implements IT24TreeViewRoot {

    private List<T24ProductDocNode> parents = new ArrayList<T24ProductDocNode>();

    public void addParentNode(IT24TreeViewNode node) {
        if (node instanceof T24ProductDocNode) {
            parents.add((T24ProductDocNode) node);
        }
    }

    public IT24TreeViewNode[] getParentNodes() {
        IT24TreeViewNode[] parentNodes = new IT24TreeViewNode[parents.size()];
        parents.toArray(parentNodes);
        return parentNodes;
    }

    public boolean hasNodes() {
        if (parents != null && parents.size() > 0) {
            return true;
        }
        return false;
    }
}

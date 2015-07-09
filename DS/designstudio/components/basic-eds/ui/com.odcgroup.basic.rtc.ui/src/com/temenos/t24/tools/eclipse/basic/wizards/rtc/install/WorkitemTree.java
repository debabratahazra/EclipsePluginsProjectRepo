package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

public class WorkitemTree implements IT24TreeViewRoot {

    private List<IT24TreeViewNode> nodes = new ArrayList<IT24TreeViewNode>();

    public void addParentNode(IT24TreeViewNode node) {
        nodes.add(node);
    }

    public IT24TreeViewNode[] getParentNodes() {
        int size = nodes.size();
        IT24TreeViewNode[] nodesArray = new IT24TreeViewNode[size];
        nodes.toArray(nodesArray);
        return nodesArray;
    }

    public boolean hasNodes() {
        return nodes.size() > 0 ? true : false;
    }
}

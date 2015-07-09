package com.temenos.t24.tools.eclipse.views.graphical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.temenos.t24.tools.eclipse.basic.views.checkDependency.CheckDependencyView;
import com.temenos.t24.tools.eclipse.basic.views.checkDependency.T24UpdateDetails;

/** This class provides content for the ZestView. */

public class NodeModelContentProvider {

    private List<MyConnection> connections;
    private Map<String, MyNode> updateMap = new HashMap<String, MyNode>();
    private Map<String, String> map = new HashMap<String, String>();
    private List<MyNode> nodes;

    public NodeModelContentProvider() {
        Set<String> set = getNodeList(CheckDependencyView.getUpdateInput());
        nodes = new ArrayList<MyNode>();
        Iterator<String> iter = set.iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            MyNode node = new MyNode(name);
            nodes.add(node);
            updateMap.put(name, node);
        }
        connections = new ArrayList<MyConnection>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String val = map.get(key);
            String updates[] = null;
            updates = val.split(",");
            MyNode sourceNode = updateMap.get(key);
            for (String update : updates) {
                MyNode destinationNode = updateMap.get(update);
                MyConnection connect = new MyConnection(sourceNode, destinationNode);
                connections.add(connect);
            }
        }
        for (MyConnection connection : connections) {
            connection.getSource().getConnectedTo().add(connection.getDestination());
        }
    }

    private Set<String> getNodeList(List<T24UpdateDetails> details) {
        Set<String> nodeList = new HashSet<String>();
        String dependencyList = "";
        String updateName = "";
        String updates[] = null;
        for (T24UpdateDetails detail : details) {
            updateName = detail.getUpdateName();
            dependencyList = detail.getDependencyList();
            map.put(updateName, dependencyList);
            updates = dependencyList.split(",");
            for (String update : updates) {
                nodeList.add(update);
            }
        }
        return nodeList;
    }

    public List<MyNode> getNodes() {
        return nodes;
    }
}

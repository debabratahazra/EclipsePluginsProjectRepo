package com.temenos.t24.tools.eclipse.views.graphical;

import java.util.ArrayList;
import java.util.List;

/** This object represents each node in the graph */

public class MyNode {

    private final String name;
    private List<MyNode> connections;

    public MyNode(String name) {
        this.name = name;
        this.connections = new ArrayList<MyNode>();
    }

    public String getName() {
        return name;
    }

    public List<MyNode> getConnectedTo() {
        return connections;
    }
}

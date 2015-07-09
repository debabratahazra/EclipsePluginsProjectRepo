package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractionNodeContainer;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractiontNode;

/**
 * controller class for zest view
 * 
 * @author sbharathraja
 * 
 */
public class ComponentInteractionViewController {

    /** instance of class {@link ComponentInteractionViewController } */
    private static ComponentInteractionViewController zestController = new ComponentInteractionViewController();
    /** instance of class {@link ComponentInteractionNodeContainer} */
    private ComponentInteractionNodeContainer nodeContainer = ComponentInteractionNodeContainer.getInstance();
    /** collection of connection to be drawn in graph view **/
    private List<ComponentInteractiontNode> connections;
    /** collection of grandparents and its children's **/
    private HashMap<ComponentInteractiontNode, HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>>> grandParentMap;
    /** collection of parents and its children's */
    private HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>> parentMap;

    /**
     * get the instance of the class {@link ComponentInteractionViewController}
     * 
     * @return
     */
    public static ComponentInteractionViewController getInstance() {
        return zestController;
    }

    /**
     * make the connection as new connection for displaying the interaction of
     * new selected component.
     */
    public void makeNewConnection() {
        this.connections = new ArrayList<ComponentInteractiontNode>();
    }

    /**
     * get the connection nodes
     * 
     * @param selectedComponent - component selected from landscape view
     * @return list of connections
     */
    public ArrayList<ComponentInteractiontNode> getNodes(String selectedComponent) {
        connections = getConnectionFromCollection(selectedComponent);
        return (ArrayList<ComponentInteractiontNode>) connections;
    }

    /**
     * get the connection nodes from collection.
     * 
     * @param selectedComponent - component selected from landscape view
     * @return - list of connections
     */
    private ArrayList<ComponentInteractiontNode> getConnectionFromCollection(String selectedComponent) {
        grandParentMap = nodeContainer.getCollectionMap();
        if (grandParentMap != null) {
            for (Object node : grandParentMap.keySet()) {
                if (((ComponentInteractiontNode) node).getNodeName().equalsIgnoreCase(selectedComponent)) {
                    connections.add((ComponentInteractiontNode) node);
                    return (ArrayList<ComponentInteractiontNode>) connections;
                }
            }
        }
        return (ArrayList<ComponentInteractiontNode>) connections;
    }

    /**
     * get the re-arranged connection nodes.
     * 
     * @param clickedNode - double clicked node
     * @return - list of connections
     */
    public ArrayList<ComponentInteractiontNode> getReArrangedNodes(ComponentInteractiontNode clickedNode) {
        if (isEligibleToReArrange(clickedNode)) {
            getReArrangedConnectionFromCollection(clickedNode);
        }
        return (ArrayList<ComponentInteractiontNode>) connections;
    }

    /**
     * checking whether the double clicked node eligible to re-arrange, on the
     * basis - if the clicked node is in key of grand parent map that is not
     * eligible, if the clicked node is is the key of parent map is eligible ,
     * if it is in value of parent map not eligible.
     * 
     * @param clickedNode - double clicked node
     * @return true - if eligible, false otherwise
     */
    private boolean isEligibleToReArrange(ComponentInteractiontNode clickedNode) {
        grandParentMap = nodeContainer.getCollectionMap();
        if (grandParentMap.containsKey(clickedNode))
            return false;
        for (Object keyOfGrandParentMap : grandParentMap.keySet()) {
            parentMap = grandParentMap.get(keyOfGrandParentMap);
            if (parentMap.containsKey(clickedNode))
                return true;
        }
        return false;
    }

    /**
     * get the re-arranged connection from collection.
     * 
     * @param doubleClickedNode
     * @return list of connection
     */
    private ArrayList<ComponentInteractiontNode> getReArrangedConnectionFromCollection(ComponentInteractiontNode doubleClickedNode) {
        if (!connections.contains(doubleClickedNode))
            connections.add(doubleClickedNode);
        else
            connections.remove(doubleClickedNode);
        return (ArrayList<ComponentInteractiontNode>) connections;
    }
}

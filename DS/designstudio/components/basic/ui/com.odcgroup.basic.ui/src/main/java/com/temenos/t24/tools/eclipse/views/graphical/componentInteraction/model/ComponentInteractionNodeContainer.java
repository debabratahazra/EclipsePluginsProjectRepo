package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * container class contains collection of model, in addition it holds the
 * component which user selected from component landscape view
 * {@link ComponentInteractiontNode}
 * 
 * @author sbharathraja
 * 
 */
public class ComponentInteractionNodeContainer {

    /** instance of class {@link ComponentInteractionNodeContainer} */
    private static ComponentInteractionNodeContainer nodeContainer = new ComponentInteractionNodeContainer();
    /** component selected from component landscape view */
    private String selectedComponent="";
    /** collection of model with parent child relationship **/
    private HashMap<ComponentInteractiontNode, HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>>> modelCollection;
    /** grand parent model */
    private ComponentInteractiontNode parent;
    /** collection of childrens for {@link parent} */
    private HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>> childrens;
    /**
     * collection of model using to collect the children/s of both parent and
     * grandparent
     */
    private ArrayList<ComponentInteractiontNode> childList;

    /**
     * get the instance of class {@link ComponentInteractionNodeContainer}
     * 
     * @return instance of class {@link ComponentInteractionNodeContainer}
     */
    public static ComponentInteractionNodeContainer getInstance() {
        return nodeContainer;
    }

    private ComponentInteractionNodeContainer() {
        // block the instance creation
    }

    /**
     * set the selected component
     * 
     * @param selectedComponent - component selected from landscape view
     */
    public void setSelectedComponent(String selectedComponent) {
        this.selectedComponent = selectedComponent;
    }

    /**
     * get the selected component
     * 
     * @return component selected from landscape view
     */
    public String getSelectedComponent() {
        return selectedComponent;
    }

    /**
     * saving the models in hash map. In addition to that setting the children
     * into each parent
     * 
     * @param parent - parent node
     * @param childrens - list of children's node
     */
    public void saveInCollection(ComponentInteractiontNode parent,
            HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>> childrens) {
        this.parent = parent;
        this.childrens = childrens;
        if (modelCollection == null)
            modelCollection = new HashMap<ComponentInteractiontNode, HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>>>();
        if (parent != null) {
            setChildrenForGrandParent();
            setChildrenForParent();
            modelCollection.put(parent, childrens);
        }
    }

    /**
     * get the map which hold the collection of models with relationship
     * 
     * @return hash map contains models
     */
    public HashMap<ComponentInteractiontNode, HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>>> getCollectionMap() {
        return modelCollection;
    }

    /**
     * setting the children's for grand parent. In total hash map grand parent
     * is key of total map, parent is key of inner hash map, child is value of
     * inner hash map. Hence this method will set the children as list the group
     * of component as product.
     */
    private void setChildrenForGrandParent() {
        childList = new ArrayList<ComponentInteractiontNode>();
        for (Object keyOfGrandChild : childrens.keySet()) {
            if (keyOfGrandChild instanceof ComponentInteractiontNode)
                childList.add((ComponentInteractiontNode) keyOfGrandChild);
        }
        parent.setChilderns(childList);
    }

    /**
     * setting the children's for parent. In total hash map grand parent is key
     * of total map, parent is key of inner hash map, child is value of inner
     * hash map. Hence this method will set the children as list of components
     */
    private void setChildrenForParent() {
        childList = new ArrayList<ComponentInteractiontNode>();
        for (Object keyOfChild : childrens.keySet()) {
            if (keyOfChild instanceof ComponentInteractiontNode) {
                childList = childrens.get(keyOfChild);
                ((ComponentInteractiontNode) keyOfChild).setChilderns(childList);
            }
        }
    }

    /**
     * checking whether the selected component already read from .properties
     * file and presented as model in collection.
     * 
     * @param selectedComponent - component selected from landscape view
     * @return true - if already read from file, false otherwise.
     */
    public boolean isSelectedComponentAlreadyRead(String selectedComponent) {
        if (modelCollection == null || modelCollection.isEmpty())
            return false;
        for (Object readComponent : modelCollection.keySet()) {
            if (((ComponentInteractiontNode) readComponent).getNodeName().equalsIgnoreCase(selectedComponent))
                return true;
        }
        return false;
    }
}

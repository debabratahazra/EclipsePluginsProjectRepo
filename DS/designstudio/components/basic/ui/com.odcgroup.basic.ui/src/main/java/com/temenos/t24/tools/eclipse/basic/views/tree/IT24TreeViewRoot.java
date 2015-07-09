package com.temenos.t24.tools.eclipse.basic.views.tree;

/**
 * Interface represents the root of any tree in the T24 Tree viewer
 * 
 * @author ssethupathi
 * 
 */
public interface IT24TreeViewRoot {

    /** Returns true if this root has any nodes (children) under it */
    public boolean hasNodes();

    /** Adds parent nodes which are the direct children of the root */
    public void addParentNode(IT24TreeViewNode node);

    /** Returns all the parent nodes which are the direct children of the root */
    public IT24TreeViewNode[] getParentNodes();
}

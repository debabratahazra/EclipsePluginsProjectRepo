package com.temenos.t24.tools.eclipse.basic.views.tree;

import org.eclipse.swt.graphics.Image;

/**
 * Interface represents a node in the T24 tree viewer. All T24 Tree viewer nodes
 * should implement this interface.
 * 
 * @author ssethupathi
 * 
 */
public interface IT24TreeViewNode {

    /** Returns the label of the node to be displayed in the tree viewer */
    public String getLabel();

    /** Returns the image of the node */
    public Image getImage();

    /** Returns true if this node has any child. False otherwise. */
    public boolean hasChildren();

    /** Returns the array of nodes which are children of this node */
    public IT24TreeViewNode[] getChildren();

    /** Returns it's parent node */
    public IT24TreeViewNode getParent();
}

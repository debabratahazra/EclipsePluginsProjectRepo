package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * Class for Creating a Source Folder under the Component Landscape view class
 * 
 * @author Mahudesh
 * 
 */
public class T24SourceParentDocNode implements IT24TreeViewNode {

    private T24ComponentDocNode parent;
    private volatile int hashCode = 0;

    public T24SourceParentDocNode(T24ComponentDocNode parent) {
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return T24ComponentViewController.getInstance().getSourcesChildren(this);
    }

    public Image getImage() {
        return DocumentViewConstants.T24PRODUCT_IMAGE;
    }

    public String getLabel() {
        return "Sources";
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return true;
    }

    public IDocInput getDocument() {
        return null;
    }

    public boolean equals(Object currentSource) {
        if (this == currentSource)
            return true;
        if (!(currentSource instanceof T24SourceParentDocNode))
            return false;
        return parent.equals(((T24SourceParentDocNode) currentSource).getParent());
    }

    public int hashCode() {
        final int multiplier = 5;
        if (hashCode == 0) {
            int code = 10;
            code = multiplier * code + parent.hashCode();
            hashCode = code;
        }
        return hashCode;
    }
}

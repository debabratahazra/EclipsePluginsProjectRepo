package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24SourceDocNode implements IT24TreeViewNode, IDocumentNode {

    private T24ComponentDocNode parent;

    public T24SourceDocNode(T24ComponentDocNode parent) {
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return T24ComponentViewController.getInstance().getSourceChildren(this);
    }

    public Image getImage() {
        return DocumentViewConstants.T24FILE_IMAGE;
    }

    public String getLabel() {
        return "Source";
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return true;
    }

    public IDocInput  getDocument() {
//        return "Contains source of "+parent.getLabel();
        return null;
    }
}

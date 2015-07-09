package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24SubroutineDocNode implements IT24TreeViewNode, IDocumentNode {

    private String subroutineName;
    private T24SourceDocNode parent;

    public T24SubroutineDocNode(String subroutineName, T24SourceDocNode parent) {
        this.subroutineName = subroutineName;
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return T24ComponentViewController.getInstance().getGosubs(this);
    }

    public Image getImage() {
        return DocumentViewConstants.T24SUBROUTINE_IMAGE;
    }

    public String getLabel() {
        return subroutineName;
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return true;
    }

    public IDocInput getDocument() {
        // return
        // SubroutineDocumentSupplier.getSubroutineDocument(this.getLabel());
        return null;
    }
}

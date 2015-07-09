package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.SubroutineDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24GosubDocNode implements IDocumentNode, IT24TreeViewNode {

    private String gosubName;
    private T24SubroutineDocNode parent;

    public T24GosubDocNode(String gosubName, T24SubroutineDocNode parent) {
        this.gosubName = gosubName;
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return null;
    }

    public Image getImage() {
        return DocumentViewConstants.T24GOSUB_IMAGE;
    }

    public String getLabel() {
        return gosubName;
    }

    public IT24TreeViewNode getParent() {
        return (IT24TreeViewNode) parent;
    }

    public boolean hasChildren() {
        return false;
    }

    public IDocInput getDocument() {
        SubroutineDocumentSupplier.getGosubDocument(parent.getLabel(), this.getLabel());
        // TODO  
        return null; 
    }
}

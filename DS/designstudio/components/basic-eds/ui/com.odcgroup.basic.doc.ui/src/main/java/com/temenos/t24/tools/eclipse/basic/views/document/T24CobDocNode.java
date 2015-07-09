package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.DocInput;
import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.ViewSection;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24CobDocNode implements IT24TreeViewNode, IDocumentNode {

    private T24ComponentDocNode parent;

    public T24CobDocNode(T24ComponentDocNode parent) {
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return T24ComponentViewController.getInstance().getCobChildren(this);
    }

    public Image getImage() {
        return DocumentViewConstants.T24PRODUCT_IMAGE;
    }

    public String getLabel() {
        return "Batch";
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return true;
    }

    public IDocInput getDocument() {
        String component = parent.getLabel();
        String product = parent.getParent().getLabel();
        String doc = "Contains batches for component " + parent.getLabel();
        return new DocInput(component, doc, product, component, ViewSection.BATCHES);
    }
}

package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.DocInput;
import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.ViewSection;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24TablesDocNode implements IT24TreeViewNode, IDocumentNode {

    private T24ComponentDocNode parent;

    public T24TablesDocNode(T24ComponentDocNode parent) {
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return T24ComponentViewController.getInstance().getDataChildren(this);
    }

    public Image getImage() {
        return DocumentViewConstants.T24PRODUCT_IMAGE;
    }

    public String getLabel() {
        return "Tables";
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return true;
    }

    public IDocInput getDocument() {
        String doc = "Contains tables for component " + parent.getLabel();
        return new DocInput(parent.getLabel(),doc, parent.getParent().getLabel(), parent.getLabel(), ViewSection.TABLES);
    }
}

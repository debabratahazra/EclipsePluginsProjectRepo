package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.DataDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.DocInput;
import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.ViewSection;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24DataFileDocNode implements IT24TreeViewNode, IDocumentNode {

    private String fileName;
    private T24TablesDocNode parent;

    public T24DataFileDocNode(String fileName, T24TablesDocNode parent) {
        this.fileName = fileName;
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return null;
    }

    public Image getImage() {
        return DocumentViewConstants.T24FILE_IMAGE;
    }

    public String getLabel() {
        return fileName;
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return false;
    }

    public IDocInput getDocument() {
        String doc = DataDocumentSupplier.getDataFileDocument(parent.getParent().getParent().getLabel(), parent.getParent()
                .getLabel(), fileName);
        IT24TreeViewNode compNode = parent.getParent();
        String product = compNode.getParent().getLabel();
        String component = compNode.getLabel();
        return new DocInput(fileName, doc, product, component, ViewSection.OVERVIEW);
    }
}

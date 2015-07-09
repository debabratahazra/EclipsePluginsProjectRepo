package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24JavaClassDocNode implements IT24TreeViewNode, IDocumentNode {

    private T24SourceDocNode parent;
    private String javaClassName;

    public T24JavaClassDocNode(String javaClassName, T24SourceDocNode parent) {
        this.javaClassName = javaClassName;
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return null;
    }

    public Image getImage() {
        return DocumentViewConstants.T24JAVA_IMAGE;
    }

    public String getLabel() {
        return javaClassName;
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return false;
    }

    public IDocInput getDocument() {
        // return
        // JavaDocumentSupplier.getInstance().getJavaClassDocument(parent.getParent().getLabel(),
        // parent.getLabel(),
        // this.getLabel());
        return null;
    }
}

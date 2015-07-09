package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.ComponentDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.DocInput;
import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.ViewSection;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24ComponentDocNode implements IT24TreeViewNode, IDocumentNode {

    private String componentName;
    private T24ProductDocNode parent;
    private volatile int hashCode = 0;

    public T24ComponentDocNode(String componentName, T24ProductDocNode parent) {
        this.componentName = componentName;
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return T24ComponentViewController.getInstance().getComponentChildren(this);
    }

    public Image getImage() {
        return DocumentViewConstants.T24COMPONENT_IMAGE;
    }

    public String getLabel() {
        return componentName;
    }

    public IT24TreeViewNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return true;
    }

    public IDocInput getDocument() {
        String componentDoc = ComponentDocumentSupplier.getComponentDocument(parent.getLabel(), this.getLabel());
        if (componentDoc == null) {
            return null;
        }
        if (componentDoc.length() <= 0) {
            componentDoc = "No overview found for " + componentName;
        }
        return new DocInput(componentName, componentDoc, parent.getLabel(), componentName, ViewSection.OVERVIEW);
    }

    public boolean equals(Object incomingComponent) {
        if (this == incomingComponent)
            return true;
        if (!(incomingComponent instanceof T24ComponentDocNode))
            return false;
        return componentName.equals(((T24ComponentDocNode) incomingComponent).getLabel())
                && parent.getLabel().equalsIgnoreCase((((T24ComponentDocNode) incomingComponent).getParent().getLabel()));
    }

    public int hashCode() {
        final int multiplier = 5;
        if (hashCode == 0) {
            int code = 10;
            code = multiplier * code + componentName.hashCode();
            code = multiplier * code + parent.hashCode();
            hashCode = code;
        }
        return hashCode;
    }
}

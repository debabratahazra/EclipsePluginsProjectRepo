package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.DocInput;
import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.ProductDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.ViewSection;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24ProductDocNode implements IT24TreeViewNode, IDocumentNode {

    private String productName;

    public T24ProductDocNode(String productName) {
        this.productName = productName;
    }

    public IT24TreeViewNode[] getChildren() {
        return T24ComponentViewController.getInstance().getComponents(this);
    }

    public Image getImage() {
        return DocumentViewConstants.T24PRODUCT_IMAGE;
    }

    public String getLabel() {
        return productName;
    }

    public IT24TreeViewNode getParent() {
        return null;
    }

    public boolean hasChildren() {
        return true;
    }

    public IDocInput getDocument() {
        String doc = ProductDocumentSupplier.getProductDocument(this.getLabel());
        if (doc == null) {
            return null;
        }
        if (doc.length() <= 0) {
            String[] components = DocumentTreeViewInitialiser.getInstance().getComponents(productName);
            doc = "Total number of components: " + components.length;
        }
        return new DocInput(productName, doc, productName, null, ViewSection.OVERVIEW);
    }
}

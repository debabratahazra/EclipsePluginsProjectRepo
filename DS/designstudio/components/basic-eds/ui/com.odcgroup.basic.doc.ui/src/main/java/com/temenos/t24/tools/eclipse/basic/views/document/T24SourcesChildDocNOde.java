package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public class T24SourcesChildDocNOde implements IT24TreeViewNode, IDocumentNode {

    private String fileName;
    private T24SourceParentDocNode parent;
    private volatile int hashCode = 0;

    /**
     * Create a class for accessing the T24SourcesDocNode to show the List of
     * Source files under Source folder
     */
    // Mahudesh
    public T24SourcesChildDocNOde(String fileName, T24SourceParentDocNode parent) {
        this.fileName = fileName;
        this.parent = parent;
    }

    public IT24TreeViewNode[] getChildren() {
        return null;
    }

    public Image getImage() {
        return DocumentViewConstants.T24SOURCES_IMAGE;
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
        return null;
    }

    public boolean equals(Object currentSourceChild) {
        if (this == currentSourceChild)
            return true;
        if (!(currentSourceChild instanceof T24SourcesChildDocNOde))
            return false;
        return parent.equals(((T24SourcesChildDocNOde) currentSourceChild).getParent())
                && fileName.equalsIgnoreCase(((T24SourcesChildDocNOde) currentSourceChild).getLabel());
    }

    public int hashCode() {
        final int multiplier = 5;
        if (hashCode == 0) {
            int code = 10;
            code = multiplier * code + parent.hashCode();
            code = multiplier * code + fileName.hashCode();
            hashCode = code;
        }
        return hashCode;
    }
}

package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * model class which contains Labels node of In-Line tree viewer
 * 
 * @author sbharathraja
 * 
 */
public class InLineLabelsNode implements IT24TreeViewNode {

    /** parent node for child node */
    private IT24TreeViewNode parentNode;
    /** name of the label */
    private String goSubName;

    /**
     * constructor of {@link InLineLabelsNode}
     * 
     * @param goSubName - name of the label
     */
    public InLineLabelsNode(IT24TreeViewNode parentNode, String goSubName) {
        this.parentNode = parentNode;
        this.goSubName = goSubName;
    }

    public IT24TreeViewNode[] getChildren() {
        return null;
    }

    public Image getImage() {
        // return DocumentViewConstants.T24LABEL_IMAGE;
        return null;
    }

    public String getLabel() {
        return goSubName;
    }

    public IT24TreeViewNode getParent() {
        return parentNode;
    }

    public boolean hasChildren() {
        return true;
    }
}

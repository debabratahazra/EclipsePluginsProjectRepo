package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.InLineDocsViewController;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * model class which contains root node of in line doc tree viewer
 * 
 * @author sbharathraja
 * 
 */
public class InLineRootNode implements IT24TreeViewNode {

    /** name of the subroutine which lies on root of in-line doc tree viewer */
    private String subRoutineName;

    /**
     * constructor of class {@link InLineRootNode}
     * 
     * @param routineName - name of the subroutine
     */
    public InLineRootNode(String routineName) {
        this.subRoutineName = routineName;
    }

    public IT24TreeViewNode[] getChildren() {
        return InLineDocsViewController.getInstance().getLabelsNode(this);
    }

    public Image getImage() {
//        return DocumentViewConstants.T24SOURCES_IMAGE;
        return null;
    }

    public String getLabel() {
        return subRoutineName;
    }

    public IT24TreeViewNode getParent() {
        return this;
    }

    public boolean hasChildren() {
        return true;
    }
}

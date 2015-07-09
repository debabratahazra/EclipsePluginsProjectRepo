package com.temenos.t24.tools.eclipse.basic.views.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * This class acts as a label provider to all T24 Tree views. The label and
 * image are obtained from
 * {@link com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode}.
 * This implements the {@link org.eclipse.jface.viewers.LabelProvider}
 * 
 * @author ssethupathi
 * 
 */
public class T24TreeViewLabelProvider extends LabelProvider {

    /**
     * Returns the label of the node to be displayed in the tree view
     */
    public String getText(Object obj) {
        if (obj instanceof IT24TreeViewNode) {
            return ((IT24TreeViewNode) obj).getLabel();
        }
        return null;
    }

    /**
     * Returns the image of the node to be displayed in the tree view
     */
    public Image getImage(Object obj) {
        if (obj instanceof IT24TreeViewNode) {
            return ((IT24TreeViewNode) obj).getImage();
        }
        return null;
    }
}

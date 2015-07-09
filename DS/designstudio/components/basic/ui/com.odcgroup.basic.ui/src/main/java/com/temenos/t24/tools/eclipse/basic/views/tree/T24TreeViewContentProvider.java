package com.temenos.t24.tools.eclipse.basic.views.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class acts as a content provider to all T24 Tree views. It implements
 * {@link ITreeContentProvider}.
 * 
 * @author ssethupathi
 * 
 */
public class T24TreeViewContentProvider implements ITreeContentProvider {

    /**
     * {@inheritDoc}
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof IT24TreeViewRoot) {
            return (IT24TreeViewNode[]) ((IT24TreeViewRoot) parentElement).getParentNodes();
        }
        if (parentElement instanceof IT24TreeViewNode) {
            return ((IT24TreeViewNode) parentElement).getChildren();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Object getParent(Object element) {
        if (element instanceof IT24TreeViewNode) {
            return ((IT24TreeViewNode) element).getParent();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasChildren(Object element) {
        if (element instanceof IT24TreeViewNode) {
            return ((IT24TreeViewNode) element).hasChildren();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /**
     * {@inheritDoc}
     */
    public void dispose() {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // TODO implement only if required!
    }
}

package com.odcgroup.mdf.editor.ui.providers.content;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelChangeListener;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;


public class MdfTreeContentProvider extends MdfStructuredContentProvider
    implements ITreeContentProvider, ModelChangeListener {
    private StructuredViewer viewer = null;
    private boolean registered = false;

    /**
     * Constructor for MdfTreeContentProvider
     */
    public MdfTreeContentProvider() {
    }

    public Object[] getChildren(Object inputElement) {
        return getElements(inputElement);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object obj) {
        return MdfUtility.getParent(obj);
    }

    public void dispose() {
        super.dispose();
        MdfProjectRepository.removeModelChangeListener(this);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object obj) {
        Object[] children = getChildren(obj);        
        return (children == null) ? false : (children.length > 0);
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        super.inputChanged(viewer, oldInput, newInput);

        this.viewer = (StructuredViewer) viewer;

        if (!registered) {
            MdfProjectRepository.addModelChangeListener(this);
            registered = true;
        }
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelChangeListener#onModelChanged(com.odcgroup.mdf.editor.model.ModelChangedEvent)
     */
    public void onModelChanged(ModelChangedEvent event) {
        Object changed = event.getChangedElement();

        if ((changed != null) && (event.getSource() != this)) {
            switch (event.getAction()) {
            case ModelChangedEvent.ELEMENT_ADDED:
                postAdded(changed);
                break;

            case ModelChangedEvent.ELEMENT_REMOVED:
                postRemoved(changed);
                break;

            case ModelChangedEvent.ELEMENT_UPDATED:
                postRefresh(changed);
                break;
            }
        }
    }

    private void postAdded(final Object changed) {
        postRunnable(new Runnable() {
                public void run() {
                    if (viewer instanceof TreeViewer) {
                        TreeViewer tree = (TreeViewer) viewer;
                        tree.add(getParent(changed), changed);
                    } else if (viewer instanceof TableViewer) {
                        TableViewer table = (TableViewer) viewer;
                        table.add(changed);
                    } else {
                        throw new ClassCastException(
                            "Viewer must be a TreeViewer or a TableViewer");
                    }
                }
            });
    }

    private void postRefresh(final Object changed) {
        if (!viewer.getControl().isDisposed()) {
            postRunnable(new Runnable() {
                    public void run() {
                        viewer.refresh(changed, true);
                    }
                });
        }
    }

    private void postRemoved(final Object changed) {
        postRunnable(new Runnable() {
                public void run() {
                    if (viewer instanceof TreeViewer) {
                        ((TreeViewer) viewer).remove(changed);
                    } else if (viewer instanceof TableViewer) {
                        ((TableViewer) viewer).remove(changed);
                    } else {
                        throw new ClassCastException(
                            "Viewer must be a TreeViewer or a TableViewer");
                    }
                }
            });
    }

    private void postRunnable(final Runnable r) {
        Control ctrl = viewer.getControl();

        if ((ctrl != null) && !ctrl.isDisposed()) {
            ctrl.getDisplay().asyncExec(r);
        }
    }
}

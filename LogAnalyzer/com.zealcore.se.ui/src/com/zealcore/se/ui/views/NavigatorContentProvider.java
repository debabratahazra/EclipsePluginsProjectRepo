package com.zealcore.se.ui.views;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.ui.core.AbstractSafeUIJob;

/**
 * 
 * @author stch
 */
class NavigatorContentProvider extends WorkbenchContentProvider {

    private final TreeViewer viewer;

    private final UIJob navigatorUpdater;

    private IResourceDelta delta;

    public NavigatorContentProvider(final TreeViewer viewer) {
        super();
        this.viewer = viewer;
        navigatorUpdater = new AbstractSafeUIJob("Updating Navigator") {
            @Override
            public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
                processResourceDelta();
                return Status.OK_STATUS;
            }
        };
        navigatorUpdater.setSystem(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(final Object parentElement) {
        final Object[] children = super.getChildren(parentElement);
        return children;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(final Object element) {
        return getChildren(element).length > 0;
    }

    @Override
    public Object[] getElements(final Object element) {
        return getChildren(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.model.WorkbenchContentProvider#processDelta(org.eclipse.core.resources.IResourceDelta)
     */
    @Override
    protected void processDelta(final IResourceDelta delta) {

        this.delta = delta;
        navigatorUpdater.schedule(100);
    }

    private void processResourceDelta() {

        super.processDelta(delta);
        if (delta != null && delta.getResource() != null
                && !viewer.getTree().isDisposed()) {
            viewer.refresh(delta.getResource());
        }
    }
}

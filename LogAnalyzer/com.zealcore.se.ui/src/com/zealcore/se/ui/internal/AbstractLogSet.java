package com.zealcore.se.ui.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.IconManager;

abstract class AbstractLogSet extends PlatformObject implements ILogSet {

    private final Set<IChangeListener> changeListeners = new HashSet<IChangeListener>();

    protected final void notifyListeners() {
        for (final IChangeListener listener : this.changeListeners) {
            listener.update(true);
        }
    }

    public void addChangeListener(final IChangeListener changeListener) {
        this.changeListeners.add(changeListener);
    }

    public void removeChangeListener(final IChangeListener changeListener) {
        this.changeListeners.remove(changeListener);
    }

    /**
     * {@inheritDoc}
     */
    public ImageDescriptor getImageDescriptor(final Object object) {
        return IconManager
                .getImageDescriptor(IconManager.LOGFILE_SMALL_IMAGE_ID);
    }

    /**
     * {@inheritDoc}
     */
    public Object getParent(final Object o) {
        return getCaseFile();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(final Class adapter) {
        if (adapter.equals(ITimeCluster.class)) {
            return getDefaultViewSet();
        }
        return super.getAdapter(adapter);
    }
}

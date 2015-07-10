package com.zealcore.se.ui.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

public class FolderDecorator implements ILightweightLabelDecorator {

    private Set<ILabelProviderListener> listeners = new HashSet<ILabelProviderListener>();

    public void decorate(final Object element, final IDecoration decoration) {
        if (element instanceof IFolder) {
            final IFolder folder = (IFolder) element;
            if (DirectoryLogSession.isLogSession(folder)) {
                ILogSessionWrapper logsession = DirectoryLogSession
                        .valueOf(folder);
                ITimeCluster viewSet = logsession.getDefaultViewSet();
                if (viewSet.isChained()) {
                    ImageDescriptor img = IconManager
                            .getImageDescriptor(IconManager.CHAIN_OVERLAY_ICON);
                    decoration.addOverlay(img, SWTResourceManager.TOP_LEFT);
                }
            }
        }
    }

    public void addListener(final ILabelProviderListener listener) {
        listeners.add(listener);
    }

    public void dispose() {
        listeners.clear();
    }

    public boolean isLabelProperty(final Object element, final String property) {
        return false;
    }

    public void removeListener(final ILabelProviderListener listener) {
        listeners.remove(listener);
    }

}

package com.zealcore.se.ui.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.ui.IconManager;

public class IconDecorator implements ILightweightLabelDecorator {

    private Set<ILabelProviderListener> listeners = new HashSet<ILabelProviderListener>();

    public void decorate(final Object element, final IDecoration decoration) {
        if (element instanceof IFile) {
            final IFile file = (IFile) element;
            if (IFWFacade.isImported(file)) {
                ImageDescriptor img = IconManager
                        .getImageDescriptor(IconManager.OVR_IMPORTED);
                decoration.addOverlay(img, SWTResourceManager.TOP_RIGHT);
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

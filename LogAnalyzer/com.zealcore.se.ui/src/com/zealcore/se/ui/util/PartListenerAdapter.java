package com.zealcore.se.ui.util;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * This adapter class provides default implementation for the methods described
 * by the <code>IPartListener2</code> interface.
 * <p>
 * Classes that wish to deal with <code>IWorkbenchPartReference</code> eventss
 * can extend this class and override only the methods which they are interested
 * in.
 * </p>
 */
public class PartListenerAdapter implements IPartListener2 {

    /**
     * {@inheritDoc}
     */
    public void partActivated(final IWorkbenchPartReference partRef) {}

    /**
     * {@inheritDoc}
     */
    public void partBroughtToTop(final IWorkbenchPartReference partRef) {}

    /**
     * {@inheritDoc}
     */
    public void partClosed(final IWorkbenchPartReference partRef) {}

    /**
     * {@inheritDoc}
     */
    public void partDeactivated(final IWorkbenchPartReference partRef) {}

    /**
     * {@inheritDoc}
     */
    public void partHidden(final IWorkbenchPartReference partRef) {}

    /**
     * {@inheritDoc}
     */
    public void partInputChanged(final IWorkbenchPartReference partRef) {}

    /**
     * {@inheritDoc}
     */
    public void partOpened(final IWorkbenchPartReference partRef) {}

    /**
     * {@inheritDoc}
     */
    public void partVisible(final IWorkbenchPartReference partRef) {}
}

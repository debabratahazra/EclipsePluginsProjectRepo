/*
 * 
 */
package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.SeUiPlugin;

/**
 * The AbstractObjectDelegate is a package-wide helper class which implements a
 * basic {@link IObjectActionDelegate} and provide functionality for subclasses.
 */
public abstract class AbstractObjectDelegate implements IObjectActionDelegate {

    /** The selection. */
    private ISelection selection;

    /** The part. */
    private IWorkbenchPart part;

    public abstract void runSafe(final IAction action);

    /**
     * Runs an action and presents an error dialog on runtimeexceptions.
     */
    public final void run(final IAction action) {
        try {
            runSafe(action);
        } catch (RuntimeException e) {
            handleException(e);
        }
    }

    protected void handleException(final Throwable e) {
        SeUiPlugin.reportUnhandledRuntimeException(this.getClass(), e, true);
    }

    /**
     * {@inheritDoc}
     */
    public void setActivePart(final IAction action,
            final IWorkbenchPart targetPart) {
        setPart(targetPart);

    }

    /**
     * {@inheritDoc}
     */
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        setSelection(selection);
    }

    /**
     * Check if guard code fail (seletion is null or the part was null).
     * 
     * @return true, if guard fail
     */
    protected boolean guardFail() {
        return getSelection() == null || getPart() == null;
    }

    /**
     * Sets the part.
     * 
     * @param part
     *                the part to set
     */
    protected void setPart(final IWorkbenchPart part) {
        this.part = part;
    }

    /**
     * Gets the part.
     * 
     * @return the part
     */
    protected IWorkbenchPart getPart() {
        return this.part;
    }

    /**
     * Sets the selection.
     * 
     * @param selection
     *                the selection to set
     */
    protected void setSelection(final ISelection selection) {
        this.selection = selection;
    }

    /**
     * Gets the selection.
     * 
     * @return the selection
     */
    protected ISelection getSelection() {
        return this.selection;
    }

    /**
     * Get first element of the selection.
     * 
     * @return the first element
     */
    protected Object getFirstElement() {
        return ((IStructuredSelection) getSelection()).getFirstElement();
    }

    /**
     * Gets the shell.
     * 
     * @return the shell
     */
    protected Shell getShell() {
        return getPart().getSite().getShell();
    }

    protected final IServiceProvider getServiceProvider() {
        return SeCorePlugin.getDefault();
    }

    protected void logException(final Exception e) {
        SeUiPlugin.logError(e);
    }

}

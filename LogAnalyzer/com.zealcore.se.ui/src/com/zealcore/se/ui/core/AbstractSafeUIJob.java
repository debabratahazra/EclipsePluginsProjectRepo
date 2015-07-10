package com.zealcore.se.ui.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.ui.SeUiPlugin;

public abstract class AbstractSafeUIJob extends UIJob {

    public AbstractSafeUIJob(final String name) {
        super(name);
    }

    public abstract IStatus runInUIThreadSafe(IProgressMonitor monitor);

    @Override
    public final IStatus runInUIThread(final IProgressMonitor monitor) {
        try {
            return runInUIThreadSafe(monitor);
        } catch (RuntimeException e) {
            handleException(e);
            return Status.CANCEL_STATUS;
        }
    }

    protected void handleException(final RuntimeException e) {
        SeUiPlugin.reportUnhandledRuntimeException(this.getClass(), e, true);
    }
}

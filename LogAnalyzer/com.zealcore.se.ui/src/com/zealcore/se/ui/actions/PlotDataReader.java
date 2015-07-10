package com.zealcore.se.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.views.PlotSearchQuery;

class PlotDataReader extends Job {

    private PlotSearchQuery plotSearchQuery;
    
    private Logset logset;
    
    private long syncKey;
    
    public PlotDataReader(final String name, final PlotSearchQuery plotSearchQuery,
            final Logset logset, final long syncKey) {
        super(name);
        this.plotSearchQuery = plotSearchQuery;
        this.logset = logset;
        this.syncKey = syncKey;
    }

    @Override
    protected IStatus run(final IProgressMonitor monitor) {
        try {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            plotSearchQuery.setMonitor(monitor);
            long time = logset.getCurrentTime();
            logset.setCurrentTime(0);
            logset.addQuery(plotSearchQuery, syncKey);
            logset.removeQuery(plotSearchQuery);
            logset.setCurrentTime(time);
            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
        } finally {
        	logset.releaseLock(syncKey);
            monitor.done();
        }
    }
}

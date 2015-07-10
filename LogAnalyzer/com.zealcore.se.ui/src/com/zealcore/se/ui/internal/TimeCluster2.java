/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.core.ifw.StatisticQuery;

/**
 * The Class TimeCluster2 is a TimeCluster (Synchronize utility for a ViewSet),
 * to be used with the DirectoryLogSession.
 */
final class TimeCluster2 extends AbstractViewSet {

    private static final int WRITE_CFG_DELAY = 3000;

    /** The parent. */
    private final DirectoryLogSession parent;

    private final WorkspaceModifyOperation stateChangeOperation;

    private final Job stateChangeJob;

    /**
     * The Constructor.
     * 
     * @param parent
     *                the parent
     */
    private TimeCluster2(final DirectoryLogSession parent) {
        this.parent = parent;
        stateChangeOperation = new WorkspaceModifyOperation() {

            @Override
            protected void execute(final IProgressMonitor monitor) {
                getParent().writeConfig();

            }

        };
        stateChangeJob = new Job("ViewSet Configuration") {

            @Override
            protected IStatus run(final IProgressMonitor monitor) {
                try {
                    stateChangeOperation.run(monitor);
                } catch (final InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (final InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return Status.OK_STATUS;
            }

        };
        stateChangeJob.setSystem(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fireStateChanged(final boolean immediate) {
        if (immediate) {
            stateChangeJob.schedule();
        } else {
            stateChangeJob.schedule(TimeCluster2.WRITE_CFG_DELAY);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getId() {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public long getMax() {
        final StatisticQuery query = getStats();
        return query.getMaxTs();

    }

    /**
     * {@inheritDoc}
     */
    public long getMin() {
        return getStats().getMinTs();
    }

    private StatisticQuery getStats() {
        return StatisticQuery.valueOf(getParent().getLogset());
    }

    /**
     * {@inheritDoc}
     */
    public DirectoryLogSession getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    public String getUid() {
        return getParent().getFolder().getFullPath().toString();
    }

    /**
     * Creates a new TimeCluster2 with the specified DirectoryLogSession as
     * parent value.
     * 
     * @param parent
     *                the parent
     * 
     * @return the time cluster2
     */
    static AbstractViewSet valueOf(final DirectoryLogSession parent) {
        return new TimeCluster2(parent);
    }

    // /**
    // * Creates a new TimeCluster2 representing the specified
    // DirectoryLogSession
    // * and with the values previously saved in the IMemento instance.
    // *
    // * @param memento
    // * the memento
    // * @param parent
    // * the parent
    // *
    // * @return the time cluster2
    // */
    // static TimeCluster2 valueOf(final DirectoryLogSession parent,
    // final IMemento memento) {
    // final TimeCluster2 timeCluster2 = new TimeCluster2(parent);
    // timeCluster2.init(memento);
    // if (timeCluster2.getName() == null) {
    // timeCluster2.setName(parent.getLabel(null));
    // }
    // return timeCluster2;
    // }

}

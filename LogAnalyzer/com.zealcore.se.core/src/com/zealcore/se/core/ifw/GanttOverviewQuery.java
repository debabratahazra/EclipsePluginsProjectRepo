package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.generic.GenericTaskExecution;
import com.zealcore.se.core.util.SimpleScaler;

public final class GanttOverviewQuery extends AbstractQuery {

    private IDataSource dataSource;

    private List<ITaskDuration> executions = new LinkedList<ITaskDuration>();

    private Map<ITask, ProcessedTaskDuration> processedExecutions = new HashMap<ITask, ProcessedTaskDuration>();

    private Collection<IFilter<IObject>> filterOutThese = new ArrayList<IFilter<IObject>>();

    private SimpleScaler ruler;

    public GanttOverviewQuery(final ITimeProvider time, final SimpleScaler ruler) {
        this.ruler = ruler;
    }


    public void initialize(final IDataSource data) {
        dataSource = data;
    }

    public boolean visit(final IObject item) {

        if (item instanceof ITaskDuration) {
            ITaskDuration dur = (ITaskDuration) item;

            for (final IFilter<IObject> f : filterOutThese) {
                if (!f.filter(dur.getOwner())) {
                    return true;
                }
            }
            ProcessedTaskDuration pTaskDur = processedExecutions.get(dur
                    .getOwner());
            if (pTaskDur == null) {
                ProcessedTaskDuration processedTaskDuration = new ProcessedTaskDuration(
                        dur, ruler.toScreen(dur.getEndTime()));
                processedExecutions.put(dur.getOwner(), processedTaskDuration);
            } else {

                if (ruler.toScreen(dur.getStartTime()) - pTaskDur.getXposEnd() < 1) {
                    pTaskDur.setStopEvent(dur.getStopEvent());
                } else {
                    executions.add(pTaskDur);
                    processedExecutions.remove(dur.getOwner());
                    ProcessedTaskDuration processedTaskDuration = new ProcessedTaskDuration(
                            dur, ruler.toScreen(dur.getEndTime()));
                    processedExecutions.put(dur.getOwner(),
                            processedTaskDuration);
                }
            }
        }

        return true;
    }

    public boolean visitBegin(final Reason reason) {
        if (((Logset) dataSource).getNumberOfTaskSwitchEvents() == 0) {
            return false;
        }
        executions.clear();
        processedExecutions.clear();
        return true;
    }

    private void refresh() {
        dataSource.refresh();
    }

    public void visitEnd(final boolean atEnd) {
        for (ITaskDuration dur : processedExecutions.values()) {
            executions.add(dur);
            processedExecutions.remove(dur);
        }
    }

    public Collection<IFilter<IObject>> getFilter() {
        return filterOutThese;
    }

    public void setFilter(final Collection<IFilter<IObject>> filter) {
        filterOutThese = filter;
        refresh();
    }

    public List<ITask> getTasks() {
        return dataSource.getArtifacts(ITask.class);
    }

    public List<ITaskDuration> getExecutions() {
        return new ArrayList<ITaskDuration>(executions);
    }

    class ProcessedTaskDuration extends GenericTaskExecution {

        private int xposEnd;

        public ProcessedTaskDuration(final ITaskDuration dur, final int xposEnd) {
            super(dur.getStartEvent(), (IProcessSwitch) dur.getStopEvent());
            this.xposEnd = xposEnd;
        }

        public int getXposEnd() {
            return xposEnd;
        }

    }
}

package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;

public class TaskQuery extends AbstractQuery {

    private static Map<Logset, TaskQuery> stat = new HashMap<Logset, TaskQuery>();

    private Set<ITask> taskExec = new HashSet<ITask>();

    private Set<ITask> taskSuperset = new HashSet<ITask>();

    private ITaskDuration firstDuration;

    private ITaskDuration lastDuration;

    private long firstTs;

    private long lastTs;

    private Collection<IFilter<IObject>> filterOutThese = new ArrayList<IFilter<IObject>>();

    public static TaskQuery valueOf(final Logset logset) {
        TaskQuery query = stat.get(logset);
        if (query == null) {
            query = new TaskQuery();
            stat.put(logset, query);
        }
        return query;

    }

    public void initialize(final IDataSource dataSource) {}

    public boolean visit(final IObject item) {
        if (item instanceof ITaskDuration) {
            ITaskDuration dur = (ITaskDuration) item;
            ITask task = dur.getOwner();

            /*
             * add to executed tasks list
             */
            taskExec.add(task);

            /*
             * save min and max duration
             */
            if (firstDuration == null) {
                firstDuration = dur;
            }
            if (lastDuration == null) {
                lastDuration = dur;
            }
            if (dur.getStartEvent().getTs() < this.firstDuration.getStartTime()) {
                this.firstDuration = dur;
            } else if (dur.getStopEvent().getTs() > this.lastDuration
                    .getEndTime()) {
                this.lastDuration = dur;
            }
        } else if (item instanceof ITask) {
            ITask task = (ITask) item;
            taskSuperset.add(task);
        }
        return true;
    }

    public Collection<ITask> getExecutedTasks() {
        return taskExec;
    }

    public Collection<ITask> getFilteredTasks() {
        List<ITask> filteredTasks = new ArrayList<ITask>();
        Set<ITask> taskExecCopy = new HashSet<ITask>();
        taskExecCopy.addAll(taskExec);

        if (filterOutThese.size() > 0) {
            for (ITask task : taskExec) {

                for (final IFilter<IObject> f : filterOutThese) {
                    if (!f.filter(task)) {
                        filteredTasks.add(task);
                        break;
                    }
                }

            }
            taskExecCopy.removeAll(filteredTasks);
        }

        return taskExecCopy;
    }

    public Collection<ITask> getNotExecutedTasks() {
        taskSuperset.removeAll(taskExec);

        return taskSuperset;
    }

    public boolean visitBegin(final Reason reason) {
        taskExec.clear();
        taskSuperset.clear();
        this.firstDuration = null;
        this.lastDuration = null;
        return true;
    }

    public void visitEnd(final boolean atEnd) {}

    public Collection<IFilter<IObject>> getFilter() {
        return filterOutThese;
    }

    public void setFilter(final Collection<IFilter<IObject>> filter) {
        filterOutThese = filter;
    }

    public ITaskDuration getFirstDuration() {
        return firstDuration;
    }

    public ITaskDuration getLastDuration() {
        return lastDuration;
    }

    public void setTaskExec(final Set<ITask> taskExec) {
        this.taskExec.addAll(taskExec);
    }

    public void setTaskSuperset(final Set<ITask> taskSuperset) {
        this.taskSuperset.addAll(taskSuperset);
    }

    public long getFirstTs() {
        if (firstDuration != null) {
            return firstDuration.getStartTime();
        }
        return firstTs;
    }

    public void setFirstTs(final long firstTs) {
        this.firstTs = firstTs;
    }

    public long getLastTs() {
        if (lastDuration != null) {
            return lastDuration.getEndTime();
        }
        return lastTs;
    }

    public void setLastTs(final long lastTs) {
        this.lastTs = lastTs;
    }
}

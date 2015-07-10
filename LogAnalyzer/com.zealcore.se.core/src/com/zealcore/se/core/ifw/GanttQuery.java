package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;

public final class GanttQuery extends AbstractQuery {

    private final TimeCache<ITaskDuration> cache;

    private IDataSource dataSource;

    private boolean failure;

    private List<ILogEvent> events = new ArrayList<ILogEvent>();

    private List<IProcessSwitch> swapEvents = new ArrayList<IProcessSwitch>();

    private boolean eventsOn;

    private boolean end;

    private Collection<IFilter<IObject>> filterOutThese = new ArrayList<IFilter<IObject>>();

    private long lastTaskTimestamp;

    public GanttQuery(final ITimeProvider time) {
        cache = new TimeCache<ITaskDuration>(time);
    }

    @SuppressWarnings("unchecked")
    public List<ITaskDuration> getExecutions(final int backward,
            final int forward) {
        if (failure || getLogset().getLogs().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<ITaskDuration> data = cache.get(backward, forward);
        if (data == null) {
            refresh();
            data = cache.get(backward, forward);
            if (data == null) {
            	// FIXME: UGLY FIX, getting available events in the cache.
            	cache.setAtStart(true);
            	cache.setAtEnd(true);
                data = cache.get(backward, forward);
            	cache.setAtStart(false);
            	cache.setAtEnd(false);
                if (data == null) {
                	data = Collections.emptyList();
                }
                // failure = true;
                // throw new IllegalStateException(
                // "Error in GanttQuery. Got null result after refresh");
            }
        }
        return data;
    }

    public List<ILogEvent> getEvents() {
        return new ArrayList<ILogEvent>(events);
    }

    public void initialize(final IDataSource data) {
        dataSource = data;
    }

    public boolean visit(final IObject item) {
        if (cache.isFull()) {
            return false;
        }
        if (item instanceof ITaskDuration) {
            ITaskDuration dur = (ITaskDuration) item;

            for (final IFilter<IObject> f : filterOutThese) {
                if (!f.filter(dur.getOwner())) {
                    return true;
                }
            }
            ITaskDuration lost = cache.put(dur);
            if (lost != null) {
                clearEvents(lost);
            }
        }
        if (item instanceof IProcessSwitch) {
            eventsOn = true;
            swapEvents.add((IProcessSwitch) item);

        } else if (eventsOn && (item instanceof ILogEvent)) {
            events.add((ILogEvent) item);
            if ((lastTaskTimestamp != -1)
                    && (((ILogEvent) item).getTimeReference() > lastTaskTimestamp)) {
                end = true;
                cache.setAtEnd(true);
                return false;
            }
        }
        return true;
    }

    private void clearEvents(final ITaskDuration lost) {
        for (Iterator<ILogEvent> iter = events.iterator(); iter.hasNext();) {
            ILogEvent e = iter.next();
            if (e.getTs() <= lost.getStopEvent().getTs()) {
                iter.remove();
            } else {
                break;
            }

        }
    }

    public boolean visitBegin(final Reason reason) {
        lastTaskTimestamp = ((Logset) dataSource).getLastTaskDurationTime();
        cache.clear();
        eventsOn = false;
        events.clear();
        swapEvents.clear();
        end = false;
        if (((Logset) dataSource).getNumberOfTaskSwitchEvents() == 0) {
            return false;
        }
        return true;
    }

    private void refresh() {
        dataSource.refresh();
    }

    public void visitEnd(final boolean atEnd) {
        if (!end)
            cache.setAtEnd(atEnd);
    }

    public Collection<IFilter<IObject>> getFilter() {
        return filterOutThese;
    }

    public void setFilter(final Collection<IFilter<IObject>> filter) {
        filterOutThese = filter;
        // refresh();
    }

    public List<ITask> getTasks() {
        return dataSource.getArtifacts(ITask.class);
    }

    public List<IProcessSwitch> getSwap() {
        return swapEvents;
    }

    @Override
    public void setStart(final boolean start) {
        cache.setAtStart(start);
    }

    @Override
    public void setEnd(final boolean end) {
        if (!end) {
            cache.setAtEnd(end);
        }
    }
}

package com.zealcore.se.ui.contributions;

import java.util.ArrayList;
import java.util.List;

import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.ITimeProvider;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.AbstractTaskInstance;
import com.zealcore.se.core.model.IObject;

class TaskInstanceQuery extends AbstractQuery {

    private final List<AbstractTaskInstance> data = new ArrayList<AbstractTaskInstance>(
            1000);

    // private IDataSource source;

    public TaskInstanceQuery(final ITimeProvider time) {
    // TODO Need a TimeCache
    }

    public void initialize(final IDataSource data) {
    // source = data;
    }

    public boolean visit(final IObject item) {
        if (item instanceof AbstractTaskInstance) {
            // TODO Only cache what we need
            add((AbstractTaskInstance) item);

        }
        return true;
    }

    private void add(final AbstractTaskInstance item) {
        // FIXME Need timetache
        data.add(item);
    }

    public List<AbstractTaskInstance> getBetween(final long start,
            final long end) {
        final List<AbstractTaskInstance> sub = new ArrayList<AbstractTaskInstance>();

        if (end < start) {
            throw new IllegalArgumentException("start < end: " + start + " < "
                    + end);
        }
        for (final AbstractTaskInstance instance : data) {
            if (contains(instance, start, end)) {
                sub.add(instance);
            }

        }
        return sub;
    }

    private boolean contains(final AbstractTaskInstance duration,
            final long v0, final long v1) {
        final long start = duration.getReleaseTs();
        final long max = Math.max(duration.getDeadline(), duration
                .getCompletionTs());
        final long end = start + max;

        final boolean isWithin = contains(start, v0, v1)
                || contains(end, v0, v1);
        final boolean spansOver = contains(v0, start, end)
                || contains(v1, start, end);
        return isWithin || spansOver;
    }

    private boolean contains(final long k, final long v0, final long v1) {
        return v0 <= k && k <= v1;
    }

    public boolean visitBegin(final Reason reason) {
        switch (reason) {
        // case QUERY_ADDED:
        // return false;
        // case REFRESH:
        // return false;
        default:
            data.clear();
            return true;
        }
    }

    public void visitEnd(final boolean atEnd) {}

}

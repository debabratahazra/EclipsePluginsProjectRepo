package com.zealcore.se.core.ifw;

import java.util.Collections;
import java.util.List;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;

public class EventQuery extends AbstractQuery {

    private IDataSource dataSource;

    private final ITimeProvider time;

    private TimeCache<ILogEvent> cache;

    private boolean failure;

    public EventQuery(final ITimeProvider provider) {
        time = provider;
        this.cache = new TimeCache<ILogEvent>(time);

    }

    @SuppressWarnings("unchecked")
    public List<ILogEvent> getEvents(final int backward, final int forward) {
        if (failure || getLogset().getLogs().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<ILogEvent> data = cache.get(backward, forward);
        if (data == null) {
            refresh();
            data = cache.get(backward, forward);
            if (data == null) {
                // failure = true;
                // /throw new IllegalStateException(
                // "Error in Query: Null values from cache after a refresh");
                data = Collections.emptyList();
            }
        }
        return data;
    }

    public void initialize(final IDataSource data) {
        dataSource = data;
    }

    public boolean visit(final IObject item) {
        if (failure || cache.isFull()) {
            return false;
        }
        if (item instanceof ILogEvent) {
            cache.putInCache((ILogEvent) item);
        }
        return true;
    }

    public boolean visitBegin(final Reason reason) {
        cache.clear();
        return true;
    }

    public void visitEnd(final boolean atEnd) {
        cache.setAtEnd(atEnd);
    }

    protected int getCacheSize() {
        return cache.getSize();
    }

    void setCacheSize(final int cachesize) {
        cache.setSize(cachesize);
    }

    private void refresh() {
        if (dataSource == null) {
            failure = true;
            throw new IllegalStateException(
                    "The DataSource is null. No Initialize has been called");
        }
        dataSource.refresh();
    }

    @Override
    public void setStart(final boolean start) {
        cache.setAtStart(start);
    }

    @Override
    public void setEnd(final boolean end) {
        cache.setAtEnd(end);
    }
}

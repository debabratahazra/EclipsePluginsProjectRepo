/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.ISynchable;
import com.zealcore.se.ui.IconManager;

/**
 * The Class AbstractViewSet.
 */
abstract class AbstractViewSet implements IViewSet, IAdaptable,
        IWorkbenchAdapter {

    private static final String INSTANCE = "instance";

    private static final String LOGMARKS_NODE = "logmarks";

    private static final String TIME_NODE = "time";

    private static final String NAME_NODE = "name";

    private String name = "N/A";

    /** The time. */
    private long time;

    /** The chained. */
    private boolean chained;
    
    /** The logmarks. */
    private final Collection<LogMark> logmarks = new HashSet<LogMark>();

    /** The synchables. */
    private final Collection<ISynchable> synchables = new HashSet<ISynchable>();
    
    private Map<String, Long> offsetMap = new HashMap<String, Long>();
    
    private long synchTime;

    /**
     * {@inheritDoc}
     */
    public boolean addSynchable(final ISynchable synchable) {
        if (synchables.add(synchable)) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public ILogMark logmark(final String note) {
    	Logset logset = Logset.valueOf(getParent().getId());
    	ILogEvent event = logset.getEventAtIndex(logset.getIndexAtTimestamp(this.time));
        final LogMark logmark = new LogMark(getCurrentTime(), note, System
                .currentTimeMillis(), event.getLogFile().toString(), event.getType().toString());
        logmarks.add(logmark);
        fireStateChanged(true);
        return logmark;
    }

    public void addLogMark(final ILogMark mark) {
        logmarks.add((LogMark) mark);
        fireStateChanged(true);
    }

    /**
     * {@inheritDoc}
     */
    public boolean chain() {
        if (isChained()) {
            return isChained();
        }
        chained = true;
        final IViewSetChain chain = getParent().getCaseFile().getViewSetChain();
        chain.addViewSet(this);
        fireStateChanged(true);
        synchTime = this.time;
        return chained;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized ILogMark[] getLogmarks() {
        return logmarks.toArray(new ILogMark[logmarks.size()]);
    }

    /**
     * {@inheritDoc}
     */
    public long getCurrentTime() {
        return time;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isChained() {
        return chained;
    }

    /**
     * {@inheritDoc}
     */
    public void removeLogmark(final ILogMark bm) {
        if (logmarks.remove(bm)) {
            fireStateChanged(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeSynchable(final ISynchable synchable) {
        if (synchables.remove(synchable)) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void setCurrentTime(final long time) {
        if (this.time != time) {
            final TimeEvent timeEvent = new TimeEvent(this, this.time);
            getParent().setCurrentTime(time);
            this.time = time;
            final Collection<ISynchable> safeIterable = new ArrayList<ISynchable>(
                    synchables);
            for (final ISynchable synchable : safeIterable) {
                synchable.synch(timeEvent);
            }
            fireStateChanged(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean unChain() {
        if (!isChained()) {
            return false;
        }
        chained = false;

        final IViewSetChain chain = getParent().getCaseFile().getViewSetChain();
        chain.removeViewSet(this);
        fireStateChanged(true);
        offsetMap.clear();
        synchTime = -1;
        return chained;
    }

    /**
     * Called to subclasses when the state of the timecluster has changed.
     * 
     * @param immediate
     *                whether immediate or not
     */
    protected abstract void fireStateChanged(boolean immediate);

    /**
     * Initializes the TimeCluster2 instance with values previously saved in the
     * specified IMemento instance. Must not spawn stateChange events
     * 
     * @param memento
     *                the memento
     */
    protected void init(final IMemento memento) {
        name = memento.getString(AbstractViewSet.NAME_NODE);
        time = Long.valueOf(memento.getString(AbstractViewSet.TIME_NODE));

        final IMemento child = memento.getChild(AbstractViewSet.LOGMARKS_NODE);

        if (child != null) {
            for (final IMemento iMemento : child
                    .getChildren(AbstractViewSet.INSTANCE)) {
                final LogMark mark = LogMark.valueOf(iMemento);
                logmarks.add(mark);
            }
        }
    }

    /**
     * Saves the state as defined by the AbstractViewSet. This method expects a
     * single IMemento node, it does not create its own instance.
     * 
     * @param memento
     *                the memento
     */
    public void saveState(final IMemento memento) {
        memento.putString(AbstractViewSet.NAME_NODE, getName());
        memento.putString(AbstractViewSet.TIME_NODE, Long
                .toString(getCurrentTime()));
        final IMemento logmarksNode = memento
                .createChild(AbstractViewSet.LOGMARKS_NODE);

        for (final LogMark logMark : logmarks) {
            final IMemento logmarkMemento = logmarksNode
                    .createChild(AbstractViewSet.INSTANCE);
            logMark.saveState(logmarkMemento);
        }

    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *                the name
     */
    void setName(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel(final Object o) {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    public Object getParent(final Object o) {
        return getParent();
    }

    /**
     * {@inheritDoc}
     */
    public ImageDescriptor getImageDescriptor(final Object object) {
        return IconManager
                .getImageDescriptor(IconManager.TIMECLUSTER_SMALL_IMG_ID);
    }

    /**
     * {@inheritDoc}
     */
    public Object[] getChildren(final Object o) {
        return new Object[0];
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(final Class adapter) {
        if (adapter.equals(IWorkbenchAdapter.class)) {
            return this;
        }
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    @Override
    public String toString() {
        return getName();
    }
    
    /**
     * It sets synch offset relative to this logset for a related logset with name 'name'
     */
    public void setSynchronizationOffset(final String name, final long offset) {
    	if(name.equalsIgnoreCase(this.getName())) {
    		return;
    	}
    	offsetMap.put(name.toLowerCase(), offset);
    }
    
    /**
     * It returns synch offset relative to this logset cluster for a related logset with name 'name' 
     */
    public long getSynchronizationOffset(final String name) {
    	Long offset0 = offsetMap.get(name.toLowerCase());
    	return (offset0 == null) ? 0 : offset0;
    }
    
    /**
     * It returns a map holding related logset names and the offset pairs relative to this logset
     */
    public Set<Entry<String, Long>> getSynchronizationOffsets() {
        return Collections.unmodifiableSet(offsetMap.entrySet());
    }
    
    
    public Object removeOffset(String name) {
        return offsetMap.remove(name);
    }
    
    public void removeAllOffsets() {
        offsetMap.clear();
    }
    
    /**
     * It returns time of the marked synchronized event.
     */
    public long getPrimarySynchronizedEventTime() {
        return synchTime;
    }
}

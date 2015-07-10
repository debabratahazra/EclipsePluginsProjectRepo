package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.ifw.TimeCache.Seed;
import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.ITimed;

public final class SequenceQuery extends AbstractQuery {

    @SuppressWarnings("unchecked")
    private static final Data EMPTY_DATA = new Data(new ArrayList(),
            Collections.EMPTY_LIST);

    private IDataSource dataSource;

    private final ISequence diagram;

    private final List<IActivity> activities = new ArrayList<IActivity>();

    private final TimeCache<ITimed> cachedActivities;

    private final ArrayList<ISequenceMessage> messages = new ArrayList<ISequenceMessage>();

    private boolean failure;

    private long endTs = -1;

    private Collection<IFilter<IObject>> filterOutThese = new ArrayList<IFilter<IObject>>();

    public SequenceQuery(final ISequence sequence, final ITimeProvider time) {
        this.diagram = sequence;
        cachedActivities = new TimeCache<ITimed>(time);

    }

    public void initialize(final IDataSource data) {
        dataSource = data;

    }

    public boolean visit(final IObject item) {
        boolean filterOut = false;

        boolean elementIsPutInCache = false;
        if (item instanceof IActivity) {
            if (cachedActivities.isFull()) {
                return false;
            }
            final IActivity activity = (IActivity) item;
            ISequenceMember owner = activity.getOwner();
            if (owner != null) {
                for (final IFilter<IObject> f : filterOutThese) {
                    if (!f.filter(owner)) {
                        filterOut = true;
                        break;
                    }
                }
                if (owner.getParent() != null && !filterOut
                        && owner.getParent().equals(diagram)) {
                    putInCache(activity);
                    elementIsPutInCache = true;
                }
            }
        }
        if (item instanceof ISequenceMessage) {
            final ISequenceMessage msg = (ISequenceMessage) item;

            for (final IFilter<IObject> f : filterOutThese) {
                if (!f.filter(msg.getRecipent())) {
                    filterOut = true;
                    break;
                } else if (!f.filter(msg.getSender())) {
                    filterOut = true;
                    break;
                }
            }
            try {
	            if (!filterOut && msg.getSender().getParent().equals(diagram)) {
	                putInCache(msg);
	                elementIsPutInCache = true;
	            }
            } catch(Exception ex) {
            	return true;
            }
        }
        if (item instanceof ILogEvent) {
            /*
             * since events arrive in time we can be sure that if the activity
             * cache is full AND we receive an event larger than the endTs, we
             * do not want more events
             */
            ILogEvent event = (ILogEvent) item;
            if (cachedActivities.isFull() && event.getTs() > endTs) {
                return false;
            }

        }
        if (item instanceof ITimed) {
            if (!filterOut && !elementIsPutInCache) {
                putInCache((ITimed) item);
            }
        }
        return true;
    }

    private void putInCache(final ISequenceMessage msg) {
        messages.add(msg);
        ITimed old = cachedActivities.put(msg);
        if (old != null) {
            truncateMessages(old.getTimeReference());
        }
        endTs = Math.max(endTs, msg.getTs());
    }

    private void putInCache(final IActivity activity) {
        ITimed old = cachedActivities.put(activity);
        if (old != null) {
            truncateMessages(old.getTimeReference());
        }
        endTs = Math.max(endTs, activity.getStartTime()
                + activity.getDurationTime());
    }

    private void putInCache(final ITimed element) {
        ITimed old = cachedActivities.put(element);
        if (old != null) {
            truncateMessages(old.getTimeReference());
        }
        endTs = Math.max(endTs, element.getTimeReference());
    }

    private void truncateMessages(final long ts) {
        while (messages.size() > 0 && messages.get(0).getTs() < ts) {
            messages.remove(0);
        }
    }

    public boolean visitBegin(final Reason reason) {
        activities.clear();
        messages.clear();
        cachedActivities.clear();
        endTs = -1;
        return true;
    }

    public void visitEnd(final boolean atEnd) {
        cachedActivities.setAtEnd(atEnd);
    }

    private void refresh() {
        dataSource.refresh();
    }

    public Data getData(final int backward, final int forward) {
        if (failure) {
            return EMPTY_DATA;
        }
        ArrayList<ITimed> acts = cachedActivities.get(backward, forward);
        if (acts == null) {
            // cache miss
            refresh();
        }
        acts = cachedActivities.get(backward, forward);
        if (acts == null) {
        	// FIXME: UGLY FIX, getting available events in the cache.
        	cachedActivities.setAtStart(true);
        	cachedActivities.setAtEnd(true);
        	acts = cachedActivities.get(backward, forward);
        	cachedActivities.setAtStart(false);
        	cachedActivities.setAtEnd(false);
        	if (acts == null) {
        		failure = true;
        		return EMPTY_DATA;
        	}
        }

        long currentTime = cachedActivities.getCurrentTime();
        List<ISequenceMessage> msgs = getMessages(currentTime, backward,
                forward);

        return new Data(acts, msgs);
    }

    private List<ISequenceMessage> getMessages(final long currentTime,
            final int backward, final int forward) {
        final long ts = currentTime;
        int from = 0;
        int to = 0;
        int size = 5000;
        int halfsize = size / 2;

        ArrayList<ISequenceMessage> elements = null;
        ArrayList<ISequenceMessage> arrayList = null;

        elements = new ArrayList<ISequenceMessage>(messages);
        int index = Collections.binarySearch(elements, new Seed(ts), null);

        int actualBackward = backward;
        int actualForward = forward;

        if (backward + 1 > halfsize) {
            actualBackward = halfsize;
        }
        if (forward + 1 > halfsize) {
            actualForward = halfsize;
        }

        /*
         * If failed to found object with exactly the same time
         */
        if (index < 0) {
            index = -index - 1;
        }
        from = index - actualBackward;
        to = index + actualForward;

        if (from < 0) {
            from = 0;
        }
        if (to > messages.size()) {
            to = messages.size();
        }

        // cachemisses
        if (from < 0) {
            return null;
        } else if (to > messages.size()) {
            return null;
        }

        arrayList = new ArrayList<ISequenceMessage>(elements.subList(from, to));

        return arrayList;
    }

    public Collection<ISequenceMember> getMembers() {
        List<ISequenceMember> members = new ArrayList<ISequenceMember>();
        for (ISequenceMember member : dataSource
                .getArtifacts(ISequenceMember.class)) {
            if (member.getParent().equals(diagram)) {
                members.add(member);
            }
        }
        return members;
    }

    public static final class Data {
        private final ArrayList<ITimed> elements;

        private final List<ISequenceMessage> messages;

        private Data(final ArrayList<ITimed> activities,
                final List<ISequenceMessage> messages) {
            super();
            this.elements = activities;
            this.messages = messages;
        }

        public ArrayList<ITimed> getElements() {
            return elements;
        }

        public List<ISequenceMessage> getMessages() {
            messages.clear();
            for (ITimed element : elements) {
                if (element instanceof ISequenceMessage) {
                    messages.add((ISequenceMessage) element);
                }
            }
            return messages;
        }
    }

    public void setCacheSize(final int size) {
        cachedActivities.setSize(size);
    }

    public void setFiltered(final Collection<IFilter<IObject>> filterOutThese) {
        this.filterOutThese = filterOutThese;
        refresh();
    }
    
    @Override
    public void setStart(final boolean start) {
    	cachedActivities.setAtStart(start);
    }

    @Override
    public void setEnd(final boolean end) {
        if (!end) {
        	cachedActivities.setAtEnd(end);
        }
    }
}

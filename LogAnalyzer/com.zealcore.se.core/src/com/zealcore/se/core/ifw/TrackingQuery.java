package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;

public final class TrackingQuery extends AbstractQuery {

    private final ISequenceMember member;

    private TimeCache<IActivity> activities;

    private TimeCache<ISequenceMessage> msgs;

    private IDataSource source;

    public TrackingQuery(final ISequenceMember member, final ITimeProvider time) {
        this.member = member;
        activities = new TimeCache<IActivity>(time, 100);
        msgs = new TimeCache<ISequenceMessage>(time, 100);
    }

    public void initialize(final IDataSource data) {
        this.source = data;

    }

    public ISequenceMember getTracked() {
        return member;
    }

    public boolean visit(final IObject item) {
        if ((activities.isFull() && msgs.isFull())) {
            return false;
        }
        if (item instanceof IActivity) {
            IActivity act = (IActivity) item;
            ISequenceMember owner = act.getOwner();

            if (owner != null && owner.equals(member)) {
                activities.put(act);
            }
        }
        if (item instanceof ISequenceMessage) {
            ISequenceMessage msg = (ISequenceMessage) item;
            if (msg.getSender().equals(member)) {
                msgs.put(msg);
            }

        }
        return true;
    }

    public Long next() {
        List<Long> times = getTimestamps();

        for (Long lng : times) {
            if (lng > msgs.getTime().getCurrentTime()) {
                return lng;
            }
        }

        return null;
    }

    private List<Long> getTimestamps() {
        List<Long> times = new ArrayList<Long>(10);

        List<IActivity> data = activities.get(2, 2);
        List<ISequenceMessage> msgData = msgs.get(2, 2);
        if (data == null || msgData == null) {
            source.refresh();
            data = activities.get(2, 2);
            msgData = msgs.get(2, 2);
        }
        if (data != null) {
            for (IActivity a : data) {
                times.add(a.getStartTime());
                times.add(a.getStartTime() + a.getDurationTime());
            }
        }
        if (msgData != null) {
            for (ISequenceMessage message : msgData) {
                times.add(message.getTs());
            }
        }
        Collections.sort(times);
        return times;
    }

    public Long previous() {
        Long prev = null;
        for (Long lng : getTimestamps()) {
            if (lng < activities.getTime().getCurrentTime()) {
                prev = lng;
            }
        }
        return prev;
    }

    public boolean visitBegin(final Reason reason) {
        activities.clear();
        msgs.clear();
        return true;
    }

    public void visitEnd(final boolean atEnd) {
        activities.setAtEnd(atEnd);
        msgs.setAtEnd(atEnd);
    }
}

package com.zealcore.se.core.model.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IActivitySetter;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;

public final class GenericFunctionExit extends AbstractLogEvent implements
        ISequenceMessage, IActivitySetter {

    private final String functionName;

    private ISequenceMember recipent;

    private ISequenceMember sender;

    private long endTime = -1;

    private long drawingTs;

    public GenericFunctionExit(final String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public long getDeliveryTime() {
        return getTs();
    }

    public String getMessage() {
        return "Exit";
    }

    public ISequenceMember getRecipent() {
        return recipent;
    }

    public ISequenceMember getSender() {
        return sender;
    }

    public void setRecipent(final ISequenceMember recipent) {
        this.recipent = recipent;
    }

    public void setSender(final ISequenceMember sender) {
        this.sender = sender;
    }

    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
        artifacts.add(sender);
        artifacts.add(recipent);
        return Collections.unmodifiableCollection(artifacts);
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        ISequenceMember oldSequenceMember = null;
        ISequenceMember newSequenceMember = null;

        if (oldArtifact instanceof ISequenceMember) {
            oldSequenceMember = (ISequenceMember) oldArtifact;
        }
        if (newArtifact instanceof ISequenceMember) {
            newSequenceMember = (ISequenceMember) newArtifact;
        }
        if (oldSequenceMember == null) {
            throw new IllegalArgumentException(
                    "oldArtifact is not a ISequenceMember");
        }
        if (oldSequenceMember.equals(sender)) {
            setSender(newSequenceMember);
            return;
        }
        if (oldSequenceMember.equals(recipent)) {
            setRecipent(newSequenceMember);
            return;
        }
        throw new IllegalArgumentException(
                "Can not substitute an anreferenced artifact: " + oldArtifact);
    }

    public String getEndNote() {
        return "returnvalue";
    }

    public ISequenceMember getOwner() {
        return this.recipent;
    }

    public long getTriggerTime() {
        return getTs();
    }

    public boolean contains(final long value) {
        if (getStartTime() > value) {
            return false;
        }
        if (getEndTime() < value) {
            return false;
        }
        return true;
    }

    public long getDurationTime() {
        return getEndTime() - getStartTime();
    }

    public long getEndTime() {
        if (endTime == -1) {
            return getStartTime() + 100;
        }
        return endTime;
    }

    public long getStartTime() {
        return getTs();
    }

    public void setEndTime(final long endTime) {
        this.endTime = endTime;
    }

    public long getDrawingTs() {
        return drawingTs;
    }

    public void setDrawingTs(long ts) {
        this.drawingTs = ts;
    }

}

package com.zealcore.se.core.model;

import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.generic.GenericAdapter;
import com.zealcore.se.core.model.generic.GenericReceiveEvent;

/**
 * @author cafa
 * 
 */
public class UMLReceiveEvent extends GenericReceiveEvent implements
        ITimedTransition, IActivity {

    private static final String TYPE_NAME = "ReceiveEvent";

    // Adapter start
    private GenericAdapter adapter = new GenericAdapter();

    private long finish = -1;

    private IState currentState;

    private IStateTransition transition;

    private IState nextState;

    public UMLReceiveEvent(final long ts) {
        super();
        adapter.setTypeName(TYPE_NAME);
        this.setTs(ts);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#getEndTime()
     */
    @ZCProperty(name = "End Time", description = "The actual end time of this duration in ns", searchable = true)
    public long getEndTime() {
        return this.finish;
    }

    /**
     * @param finishHigh
     *            specifies the high part (32 + 32 bit) of the time stamp when
     *            the message was received and processed
     * @param finishLow
     *            specifies the low part (32 + 32 bit) of the time stamp when
     *            the message was received and processed
     */
    public void setFinish(final long finishHigh, final long finishLow) {
        this.finish = finishHigh * GenericReceiveEvent.INT_MAX + finishLow;
    }

    /**
     * @param ts
     *            specifies the time stamp when the message was received and
     *            processed
     */
    public void setFinish(final long ts) {
        this.finish = ts;
    }

    public void setCurrentState(final IState currentState) {
        this.currentState = currentState;
    }

    @ZCProperty(searchable = false, name = "Current State", description = "The state at which the machine is in prior to this event")
    public IState getCurrentState() {
        return currentState;
    }

    public IStateTransition getTransition() {
        return transition;
    }

    public void setTransition(final IStateTransition transition) {
        this.transition = transition;
    }

    public IState getStateIn() {
        return nextState;
    }

    public void setStateIn(final IState nextState) {
        this.nextState = nextState;
    }

    @Override
    public Collection<IArtifact> referencedArtifacts() {

        final Collection<IArtifact> refs = super.referencedArtifacts();
        refs.add(getCurrentState());
        refs.add(getTransition());
        refs.add(getStateIn());

        return refs;
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == getCurrentState()) {
            setCurrentState((IState) newArtifact);
        }
        if (oldArtifact == getTransition()) {
            setTransition((IStateTransition) newArtifact);
        }

        if (oldArtifact == getStateIn()) {
            setStateIn((IState) newArtifact);
        }
        super.substitute(oldArtifact, newArtifact);
    }

    public String getEndNote() {
        return getStateIn().getName();
    }

    public ISequenceMember getOwner() {
        return (ISequenceMember) getStateIn().getParent();
    }

    public boolean contains(final long value) {
        return getTs() >= value && value <= getEndTime();
    }

    public long getDurationTime() {
        return getEndTime() - getTs();
    }

    /**
     * @return
     */
    public long getTriggerTime() {
        return getSentAt();
    }

    /**
     * @return
     */
    public long getStartTime() {
        return getTs();
    }

    /**
     * @return
     */
    public String getLabel() {
        return getMessage();
    }
}

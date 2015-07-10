package com.zealcore.se.core.model;

import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.annotation.ZSearchable;

/**
 * AbstractDuration is the abstract base class for all durations in time started
 * by a certain event and stopped by another event. An example of a duration is
 * a duration of a task: The duration starts when the task is switched in and
 * lasts until it's switched out.
 * 
 * Durations are used when drawing Gantt charts.
 * 
 * @author pasa
 * @version 1.0
 * @since 1.0
 * 
 */

@ZSearchable(name = "Duration")
public abstract class AbstractDuration extends AbstractObject implements
        IDuration {
    /*
     * The owner of this duration, i.e. who has done something during this
     * duration
     */
    private IArtifact owner;

    /*
     * The event that has started this duration.
     */
    private ILogEvent startEvent;

    /*
     * The event that has made this duration to end.
     */
    private ILogEvent stopEvent;

    @ZCProperty(name = "Owner", searchable = true, description = "The name of the user that caused the duration")
    public IArtifact getOwner() {
        if (this.owner == null) {
            throw new IllegalStateException(
                    "No owner is set this. getClass(): " + getClass());
        }
        return this.owner;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.IDuration#setOwner(com.zealcore.se.core.model
     * .IArtifact)
     */

    public void setOwner(final IArtifact owner) {
        this.owner = owner;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#getStartEvent()
     */
    @ZCProperty(name = "StartEvent", description = "The event that started this duration")
    public ILogEvent getStartEvent() {
        return this.startEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.IDuration#setStartEvent(com.zealcore.se.core
     * .model.ILogEvent)
     */
    public void setStartEvent(final ILogEvent startEvent) {
        this.startEvent = startEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#getStopEvent()
     */
    @ZCProperty(name = "StopEvent", description = "The event that ended this duration")
    public ILogEvent getStopEvent() {
        return this.stopEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.IDuration#setStopEvent(com.zealcore.se.core
     * .model.ILogEvent)
     */

    public void setStopEvent(final ILogEvent stopEvent) {
        this.stopEvent = stopEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#getStartTime()
     */
    @ZCProperty(name = "Start Time", description = "The actual start time of this duration in ns")
    public long getStartTime() {
        return getStartEvent().getTs();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#getDurationTime()
     */
    @ZCProperty(name = "Duration", plottable = true, searchable = true, description = "The time between the start event and the stop event")
    public long getDurationTime() {
        final ILogEvent le = getStopEvent();
        long durationTime = 0L;
        if (le != null) {
            durationTime = le.getTs() - getStartTime();
        }
        return durationTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#getEndTime()
     */
    @ZCProperty(name = "End Time", description = "The actual end time of this duration in ns")
    public long getEndTime() {
        return getStopEvent().getTs();
    }

    /**
     * toString method for AbstractDuration
     */
    @Override
    public String toString() {
        final String toString = this.getClass().getSimpleName().replace(
                "Abstract", "")
                + '['
                + "Duration="
                + getDurationTime()
                + ",StartTime="
                + getStartTime()
                + ",Owner="
                + getOwner()
                + ",StartEvent="
                + getStartEvent() + ",StopEvent=" + getStopEvent() + ']';
        return toString;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#referencedArtifacts()
     */
    @Override
    public Collection<IArtifact> referencedArtifacts() {

        final Collection<IArtifact> abstractArtifacts = super
                .referencedArtifacts();
        abstractArtifacts.add(getOwner());
        return abstractArtifacts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.IDuration#substitute(com.zealcore.se.core.
     * model.IArtifact, com.zealcore.se.core.model.IArtifact)
     */
    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == this.owner) {
            setOwner(newArtifact);
            return;
        }
        super.substitute(oldArtifact, newArtifact);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(final Object o) {
        if (o instanceof ITimed) {
            final ITimed other = (ITimed) o;
            return Long.valueOf(getStartTime()).compareTo(
                    other.getTimeReference());
        }
        return super.compareTo(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#contains(long)
     */
    public boolean contains(final long value) {
        return value >= getStartTime()
                && value <= getStartTime() + getDurationTime();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IDuration#getTimeReference()
     */
    public Long getTimeReference() {
        return getStartTime();
    }
}

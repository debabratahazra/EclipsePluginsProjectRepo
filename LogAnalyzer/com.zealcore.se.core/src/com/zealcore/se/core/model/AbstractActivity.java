/*
 * 
 */
package com.zealcore.se.core.model;

import java.util.ArrayList;
import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;

public abstract class AbstractActivity extends AbstractDuration implements
        IActivity {

    private final Collection<ISequenceMessage> messages = new ArrayList<ISequenceMessage>();

    private String endNote = IActivity.UNKNOWN;

    /**
     * Adds the message to the collection of messages.
     * 
     * @param message
     *                the message to add
     */
    public final void addMessage(final ISequenceMessage message) {
        this.messages.add(message);
    }

    /**
     * Gets relevant messages for this activity.
     * 
     * @return the messages
     */
    @ZCProperty(name = "Messages")
    public Collection<ISequenceMessage> getMessages() {
        return this.messages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IActivity#getParent()
     */
    @Override
    @ZCProperty(name = "Parent", searchable = true)
    public ISequenceMember getOwner() {
        return (ISequenceMember) super.getOwner();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IActivity#getEndNote()
     */
    public String getEndNote() {
        return this.endNote;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IActivity#setEndNote(java.lang.String)
     */
    public void setEndNote(final String endNote) {
        this.endNote = endNote;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IActivity#getTriggerTime()
     */
    public long getTriggerTime() {
        return -1;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '[' + "DurationTime="
                + getDurationTime() + ",StartTime=" + getStartTime()
                + ",Owner=" + getOwner() + ",AbstractStartEvent="
                + getStartEvent() + ",AbstractStopEvent=" + getStopEvent();
    }
}

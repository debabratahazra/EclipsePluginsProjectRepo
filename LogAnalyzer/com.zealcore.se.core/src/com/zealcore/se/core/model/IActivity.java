package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

/**
 * An IActivity is a unit that has display capability in the sequence diagram
 * 
 * @author stch
 * 
 */
public interface IActivity extends IDuration {

    String UNKNOWN = "unknown";

    @ZCProperty(name = "Parent", searchable = true)
    ISequenceMember getOwner();

    /**
     * @return the note at the bottom of the Activity in the sequence diagram
     *         (or null/unkown)
     */
    String getEndNote();

    /**
     * @return The time of the cause which triggered this IActivity. The trigger
     *         time may differ from start time if for example a
     *         {@link com.zealcore.se.core.model.ISequenceMessage} were sent
     *         before the start time of this activity.
     */
    long getTriggerTime();

}

package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

public interface IDuration extends ITimed, IObject {

    
    /**
     * Gets the owner of this duration.
     * 
     * @return the owner, which is a IArtifact
     */
    @ZCProperty(name = "Owner", searchable = true, description = "The name of the user that caused the duration")
    IArtifact getOwner();

   
    /**
     * Gets the time when this duration starts. The granularity of this time is
     * the same as in the timestamps (ts) in the LogEvents.
     * 
     * @return start time of this duration
     */
    @ZCProperty(name = "Start Time", description = "The actual start time of this duration in ns")
    long getStartTime();

    /**
     * Gets the time that has expired during this duration. The granularity of
     * this time should be the same as the timestamps (ts) of the LogEvents.
     * 
     * @return the length of this duration in time
     */
    @ZCProperty(name = "Duration", plottable = true, searchable = true, description = "The length in time of this duration")
    long getDurationTime();

    /**
     * Gets the time when this duration ends. The granularity of this time is
     * the same as in the timestamps (ts) in the LogEvents.
     * 
     * @return end time of this duration
     */
    @ZCProperty(name = "End Time", description = "The actual end time of this duration in ns")
    long getEndTime();

    /**
     * 
     * Checks wether the value is contained within this duration. The check is
     * inclusive and is calculated as followed
     * 
     * value >= getStartTime() && value <= (getStartTime() + getDurationTime());
     * 
     * @return value >= getStartTime() && value <= (getStartTime() +
     *          getDurationTime());
     * 
     */
    boolean contains(final long value);
}

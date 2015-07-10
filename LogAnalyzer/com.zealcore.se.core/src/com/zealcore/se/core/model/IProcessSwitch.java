package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

public interface IProcessSwitch extends ILogEvent {

    /**
     * Gets the user that is switched in by this event.
     * 
     * @return the AbstractArtifact that is switched in
     */
    @ZCProperty(name = "Process in", searchable = true, description = "The name of the process that will be active after this event")
    IArtifact getResourceUserIn();

    /**
     * Sets the given user as the one that is switched in by this event.
     * 
     * @param resourceUserIn
     *                the AbstractArtifact that is switched in by this event
     */
//    void setResourceUserIn(final IArtifact resourceUserIn);

    /**
     * Gets the user that is switched out by this event.
     * 
     * @return the AbstractArtifact that is switched out
     */
    @ZCProperty(name = "Process out", searchable = true, description = "The name of the process that were active prior to this event")
    IArtifact getResourceUserOut();
//
//    /**
//     * Sets the given user as the one that is switched out by this event.
//     * 
//     * @param resourceUserOut
//     *                the AbstractArtifact that is switched out by this event
//     */
//    void setResourceUserOut(final IArtifact resourceUserOut);
//
//    /**
//     * toString method for AbstractSwitchEvent
//     */
//    Collection<IArtifact> referencedArtifacts();
//
//    void substitute(final IArtifact oldArtifact, final IArtifact newArtifact);

}

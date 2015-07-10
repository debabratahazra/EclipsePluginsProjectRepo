/*
 * 
 */
package com.zealcore.se.core.model;

/**
 * The Interface ISequenceMember. These are actors in a sequence diagram. At
 * current specification, all implementatators of this interface must also
 * extend AbstractArtifact.
 */
public interface ISequenceMember extends IArtifact {

    /**
     * Gets the parent sequence diagram. The parent is displayable sequence
     * containing many ISequenceMember who in turn are referenced by many
     * AbstractActivities.
     * 
     * @return the parent
     */
    ISequence getParent();

    /**
     * Sets the parent.
     * 
     * @param parent
     *                the parent
     */
    void setParent(ISequence parent);

    /**
     * @return the name of the IArtifact}
     */
    String getName();

}

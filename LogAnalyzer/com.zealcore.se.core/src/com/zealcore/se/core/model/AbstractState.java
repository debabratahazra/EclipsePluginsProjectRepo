package com.zealcore.se.core.model;

import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;

public abstract class AbstractState extends AbstractArtifact implements IState {


    private IArtifact parent;

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IState#getParent()
     */
    @ZCProperty(name = "Parent", description = "The parent statemachine")
    public IArtifact getParent() {
        return this.parent;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IState#setParent(com.zealcore.se.core.model.IArtifact)
     */
    public void setParent(final IArtifact parent) {
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IState#referencedArtifacts()
     */
    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> abstractArtifacts = super
                .referencedArtifacts();
        abstractArtifacts.add(getParent());
        return abstractArtifacts;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IState#substitute(com.zealcore.se.core.model.IArtifact, com.zealcore.se.core.model.IArtifact)
     */
    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (getParent() == oldArtifact) {
            setParent(newArtifact);
            return;
        }
        super.substitute(oldArtifact, newArtifact);
    }
}

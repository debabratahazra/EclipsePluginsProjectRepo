package com.zealcore.se.core.model;

import java.util.Collection;

public abstract class AbstractStateTransition extends AbstractArtifact implements IStateTransition {

    private IState from;

    private IState to;

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IStateTransition#getFrom()
     */
    public IState getFrom() {
        return this.from;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IStateTransition#getTo()
     */
    public IState getTo() {
        return this.to;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IStateTransition#setFrom(com.zealcore.se.core.model.IState)
     */
    public void setFrom(final IState from) {
        this.from = from;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IStateTransition#setTo(com.zealcore.se.core.model.IState)
     */
    public void setTo(final IState to) {
        this.to = to;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IStateTransition#referencedArtifacts()
     */
    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> abstractArtifacts = super
                .referencedArtifacts();
        abstractArtifacts.add(getFrom());
        abstractArtifacts.add(getTo());
        return abstractArtifacts;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IStateTransition#substitute(com.zealcore.se.core.model.IArtifact, com.zealcore.se.core.model.IArtifact)
     */
    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (getFrom() == oldArtifact) {
            setFrom((IState) newArtifact);
        }
        if (getTo() == oldArtifact) {
            setTo((IState) newArtifact);
        }
        super.substitute(oldArtifact, newArtifact);
    }
}

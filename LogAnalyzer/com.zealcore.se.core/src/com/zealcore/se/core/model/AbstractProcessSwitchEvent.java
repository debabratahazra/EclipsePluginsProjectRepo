package com.zealcore.se.core.model;

import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.annotation.ZSearchable;

@ZSearchable
public abstract class AbstractProcessSwitchEvent extends AbstractLogEvent implements IProcessSwitch2 {

    private IArtifact resourceUserIn;

    private IArtifact resourceUserOut;

    public AbstractProcessSwitchEvent() {
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IProcessSwitch#getResourceUserIn()
     */
    @ZCProperty(name = "Process in", searchable = true, description = "The name of the process that will be active after this event")
    public IArtifact getResourceUserIn() {
        return this.resourceUserIn;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IProcessSwitch#setResourceUserIn(com.zealcore.se.core.model.IArtifact)
     */
    public void setResourceUserIn(final IArtifact resourceUserIn) {
        this.resourceUserIn = resourceUserIn;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IProcessSwitch#getResourceUserOut()
     */
    @ZCProperty(name = "Process out", searchable = true, description = "The name of the process that were active prior to this event")
    public IArtifact getResourceUserOut() {
        return this.resourceUserOut;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IProcessSwitch#setResourceUserOut(com.zealcore.se.core.model.IArtifact)
     */
    public void setResourceUserOut(final IArtifact resourceUserOut) {
        this.resourceUserOut = resourceUserOut;
    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IProcessSwitch#referencedArtifacts()
     */
    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> abstractArtifacts = super
                .referencedArtifacts();
        abstractArtifacts.add(getResourceUserIn());
        abstractArtifacts.add(getResourceUserOut());

        return abstractArtifacts;

    }

    /* (non-Javadoc)
     * @see com.zealcore.se.core.model.IProcessSwitch#substitute(com.zealcore.se.core.model.IArtifact, com.zealcore.se.core.model.IArtifact)
     */
    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
       
        if (getResourceUserIn() == oldArtifact) {
            setResourceUserIn(newArtifact);
        }

        if (getResourceUserOut() == oldArtifact) {
            setResourceUserOut(newArtifact);
        }
        super.substitute(oldArtifact, newArtifact);
    }
}

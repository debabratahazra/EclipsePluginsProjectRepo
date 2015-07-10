/*
 * 
 */
package com.zealcore.se.core.model;

import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.annotation.ZSearchable;

@ZSearchable
public abstract class AbstractTimedTransition extends AbstractDuration
        implements ITimedTransition {

    private IStateTransition transition;

    private long execTime = -1;

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ITimedTransition#getTransition()
     */
    @ZCProperty(name = "StateTransition", searchable = true, description = "The name of the state transition (Usually From.name to To.name)")
    public IStateTransition getTransition() {
        return this.transition;
    }

    public void setTransition(final IStateTransition transition) {
        this.transition = transition;
    }

    @ZCProperty(name = "ExecutionTime", searchable = true, description = "The execution time of this transition in ns, execution"
            + " time differs from duration in that the execution time is active CPU time whereas duration includes interference")
    public long getExecTime() {
        return this.execTime;
    }

    public void setExecTime(final long execTime) {
        this.execTime = execTime;
    }

    // TODO Could override setter of startEvent stopEvent to ensure that they
    // are atleast of IArtifactSwitchIn type

    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> abstractArtifacts = super
                .referencedArtifacts();
        abstractArtifacts.add(getTransition());
        return abstractArtifacts;
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (getTransition() == oldArtifact) {
            setTransition((IStateTransition) newArtifact);
            return;
        }
        super.substitute(oldArtifact, newArtifact);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ITimedTransition#getLabel()
     */
    public String getLabel() {
        return "";
    }
}

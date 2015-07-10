package com.zealcore.se.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class Function extends AbstractArtifact implements ISequenceMember {

    private ISequence parent;

    public ISequence getParent() {
        return parent;
    }

    public void setParent(final ISequence parent) {
        this.parent = parent;
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        ISequence oldSequence = null;
        ISequence newSequence = null;

        if (oldArtifact instanceof ISequence) {
            oldSequence = (ISequence) oldArtifact;
        }
        if (newArtifact instanceof ISequence) {
            newSequence = (ISequence) newArtifact;
        }
        if (oldSequence == null) {
            throw new IllegalArgumentException("oldArtifact is not a ISequence");
        }
        if (newSequence == null) {
            throw new IllegalArgumentException("newArtifact is not a ISequence");
        }
        if (oldSequence.equals(parent)) {
            setParent(newSequence);
            return;
        }
        throw new IllegalArgumentException(
                "Can not substitute an unreferenced artifact: " + oldArtifact);
    }

    @Override
    public Collection<IArtifact> referencedArtifacts() {
        Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
        artifacts.add(parent);
        return Collections.unmodifiableCollection(artifacts);
    }
}

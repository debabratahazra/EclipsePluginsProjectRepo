package com.enea.sd.example.importer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;

public class Component extends AbstractArtifact implements ISequenceMember {

    private ISequence parent;

    public ISequence getParent() {
        System.out.println("trace");
        return this.parent;
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

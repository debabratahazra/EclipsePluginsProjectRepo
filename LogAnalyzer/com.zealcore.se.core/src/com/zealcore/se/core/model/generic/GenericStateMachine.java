package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.Map;

import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;

public class GenericStateMachine extends AbstractArtifact implements
        ISequenceMember, IGenericLogItem {
    private ISequence parent;

    // Adapter start
    private final GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "StateMachine";

    public GenericStateMachine() {
        adapter.setTypeName(TYPE_NAME);
    }

    public GenericStateMachine(final String name) {
        adapter.setTypeName(TYPE_NAME);
        setName(name);
    }

    @Override
    public String toString() {
        return getName();
    }

    public ISequence getParent() {
        return this.parent;
    }

    public void setParent(final ISequence parent) {
        this.parent = parent;
    }

    public void setTypeName(final String typeName) {
        adapter.setTypeName(typeName);
    }
    
    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> artifacts = super.referencedArtifacts();
        artifacts.add(getParent());
        return artifacts;
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == getParent()) {
            setParent((ISequence) newArtifact);
        } else {
            super.substitute(oldArtifact, newArtifact);
        }
    }

    public void addProperty(final String name, final Object value) {
        this.adapter.addProperty(name, value);
    }

    public Object getProperty(final String key) {
        return this.adapter.getProperty(key);
    }

    public String getTypeName() {
        return this.adapter.getTypeName();
    }

    public Map<String, Object> properties() {
        return this.adapter.properties();
    }
}

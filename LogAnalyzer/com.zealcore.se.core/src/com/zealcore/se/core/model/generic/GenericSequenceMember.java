package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.AbstractSequence;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

public class GenericSequenceMember extends AbstractArtifact implements
        IGenericLogItem, ISequenceMember {

    // Adapter start
    private GenericAdapter adapter = new GenericAdapter();

    private ISequence parent;

    private static final String TYPE_NAME = "Sequencemember";

    public GenericSequenceMember() {
        adapter.setTypeName(TYPE_NAME);
    }

    public GenericSequenceMember(final String name) {
        this.setName(name);
    }

    public void addProperty(final String name, final Object value) {
        adapter.addProperty(name, value);
    }

    @Override
    public List<SEProperty> getZPropertyAnnotations() {

        List<SEProperty> list = super.getZPropertyAnnotations();
        list.addAll(adapter.toSEProperties());

        return list;
    }

    @Override
    public String getName() {
        return this.getName();
    }

    public ISequence getParent() {
        return parent;
    }

    public void setParent(final AbstractSequence parent) {
        this.parent = parent;
    }

    @Override
    public String getClassName() {
        return this.getClassName();
    }

    @Override
    public Collection<IArtifact> referencedArtifacts() {
        Collection<IArtifact> artifacts = super.referencedArtifacts();
        artifacts.add(getParent());
        return artifacts;
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == getParent()) {
            setParent((AbstractSequence) newArtifact);
        } else {
            super.substitute(oldArtifact, newArtifact);
        }
    }

    public Object getProperty(final String key) {
        return adapter.getProperty(key);
    }

    public String getTypeName() {
        return adapter.getTypeName();
    }

    public Map<String, Object> properties() {
        return adapter.properties();
    }

    @Override
    public IType getType() {
        return GenericType.valueOf(this);
    }

    public void setTypeName(final String string) {
        adapter.setTypeName(string);
    }

    public void setParent(final ISequence parent) {
        this.parent = parent;
    }
}

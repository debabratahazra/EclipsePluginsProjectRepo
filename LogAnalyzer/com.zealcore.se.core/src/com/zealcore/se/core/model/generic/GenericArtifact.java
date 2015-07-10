package com.zealcore.se.core.model.generic;

import java.util.List;
import java.util.Map;

import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

public class GenericArtifact extends AbstractArtifact implements
        IGenericLogItem {
    // Adapter start
    private final GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "Artifact";

    public GenericArtifact() {
        adapter.setTypeName(TYPE_NAME);
    }

    public void addProperty(final String name, final Object value) {
        this.adapter.addProperty(name, value);
    }

    @Override
    public List<SEProperty> getZPropertyAnnotations() {

        final List<SEProperty> list = super.getZPropertyAnnotations();
        list.addAll(this.adapter.toSEProperties());

        return list;
    }

    public void setTypeName(final String typeName) {
        this.adapter.setTypeName(typeName);
    }

    /**
     * This method is for internal use only {@inheritDoc}
     */
    public Map<String, Object> properties() {
        return this.adapter.properties();
    }

    @Override
    public IType getType() {
        return GenericType.valueOf(this);
    }

    public Object getProperty(final String key) {
        return this.adapter.getProperty(key);
    }

    public String getTypeName() {
        return this.adapter.getTypeName();
    }

    // Adapter end
}

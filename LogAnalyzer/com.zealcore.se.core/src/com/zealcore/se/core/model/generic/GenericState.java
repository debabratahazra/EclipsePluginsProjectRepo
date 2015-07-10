package com.zealcore.se.core.model.generic;

import java.util.Map;

import com.zealcore.se.core.model.AbstractState;
import com.zealcore.se.core.model.IArtifact;

public class GenericState extends AbstractState implements IGenericLogItem {
    private static final String TYPE_NAME = "State";

    // Adapter start
    private GenericAdapter adapter = new GenericAdapter();

    public GenericState() {
        adapter.setTypeName(TYPE_NAME);
    }

    public GenericState(final String name) {
        adapter.setTypeName(TYPE_NAME);
        setName(name);
    }

    public GenericState(final String name, final IArtifact parent) {
        adapter.setTypeName(TYPE_NAME);
        setName(name);
        setParent(parent);
    }

    public void setTypeName(final String typeName) {
        adapter.setTypeName(typeName);
    }
    
    private String id;

    @Override
    public String getId() {
        if (id != null) {
            return id;
        }
        id = getName() + getParent().getId();
        return id;
    }

    @Override
    public String toString() {
        return getName();
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

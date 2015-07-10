package com.zealcore.se.core.model.generic;

import java.util.Map;

import org.eclipse.core.runtime.Assert;

import com.zealcore.se.core.model.AbstractStateTransition;
import com.zealcore.se.core.model.IState;

public class GenericStateTransition extends AbstractStateTransition implements
        IGenericLogItem {

    private String id;

    // Adapter start
    private final GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "StateTransition";

    public GenericStateTransition() {
        adapter.setTypeName(TYPE_NAME);
    }

    public GenericStateTransition(final IState currentState,
            final IState stateIn) {
        adapter.setTypeName(TYPE_NAME);
        Assert.isNotNull(currentState);
        Assert.isNotNull(stateIn);

        setFrom(currentState);
        setTo(stateIn);
        setName(getFrom() + " to " + getTo());
    }

    @Override
    public String getId() {
        if (this.id == null) {
            this.id = getFrom().getId() + getTo().getId();
        }
        return this.id;
    }

    public void addProperty(final String name, final Object value) {
        this.adapter.addProperty(name, value);
    }

    public void setTypeName(final String typeName) {
        adapter.setTypeName(typeName);
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

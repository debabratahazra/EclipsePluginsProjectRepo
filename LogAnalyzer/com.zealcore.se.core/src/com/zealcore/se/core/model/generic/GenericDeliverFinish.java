package com.zealcore.se.core.model.generic;

import java.util.Map;

import com.zealcore.se.core.model.AbstractLogEvent;

public class GenericDeliverFinish extends AbstractLogEvent implements
        IGenericLogItem {
    // Adapter start
    private final GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "DeliverFinish";

    public GenericDeliverFinish() {
        adapter.setTypeName(TYPE_NAME);
    }

    public GenericDeliverFinish(final long finish) {
        super();
        adapter.setTypeName(TYPE_NAME);
        setTs(finish);
    }

    // Adapter end

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

package com.zealcore.se.core.model.generic;

import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractTaskInstance;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IType;

public class GenericTaskInstance extends AbstractTaskInstance implements
        IGenericLogItem {

    private final GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "TaskInstance";

    public GenericTaskInstance() {
        this.adapter.setTypeName(TYPE_NAME);
    }

    @Override
    public long getStartTime() {
        // This is the same as releasetime, override and hide it
        return super.getStartTime();
    }

    @ZCProperty(name = "Task", searchable = true, description = "The name of the task")
    public IArtifact getOwner() {
        return super.getOwner();
    }

    @ZCProperty(name = "Response Time", plottable = true, searchable = true, ts = true, description = "The time between the release event and the completion event")
    public long getDurationTime() {
        long durationTime = super.getDurationTime();
        return durationTime;
    }

    public IType getType() {
        return GenericType.valueOf(this);
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

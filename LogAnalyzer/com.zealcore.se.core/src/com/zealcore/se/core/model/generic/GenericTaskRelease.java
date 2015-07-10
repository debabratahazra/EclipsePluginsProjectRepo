package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IType;

public class GenericTaskRelease extends AbstractLogEvent implements
        IGenericLogItem {
    private static final String TYPE_NAME = "TaskRelease";

    private IArtifact taskId;

    private long timeBudget;

    private final GenericAdapter adapter = new GenericAdapter();

    public GenericTaskRelease(final long ts) {
        this.setTs(ts);
        this.adapter.setTypeName(TYPE_NAME);
    }

    @ZCProperty(name = "Task", searchable = true, description = "The task")
    public IArtifact getTaskId() {
        return taskId;
    }

    public void setTaskId(final IArtifact task) {
        taskId = task;
    }

    @ZCProperty(name = "Time Budget", searchable = true, description = "Time budget in ns")
    public long getTimeBudget() {
        return timeBudget;
    }

    public void setTimeBudget(final long timeBudget) {
        this.timeBudget = timeBudget;
    }

    public void setTypeName(final String typeName) {
        adapter.setTypeName(typeName);
    }
    
    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> referencedArtifacts = super
                .referencedArtifacts();
        referencedArtifacts.add(taskId);
        return referencedArtifacts;
    }

    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == getTaskId()) {
            setTaskId(newArtifact);
        }
        super.substitute(oldArtifact, newArtifact);
    }

    public IType getType() {
        return GenericType.valueOf(this);
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

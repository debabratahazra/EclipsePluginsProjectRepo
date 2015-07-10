package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IType;

public class GenericTaskCompletion extends AbstractLogEvent implements
        IGenericLogItem {

    private IArtifact taskId;

    private long remainingTime;

    private static final String TYPE_NAME = "TaskCompletion";

    private final GenericAdapter adapter = new GenericAdapter();

    public GenericTaskCompletion(final long ts) {
        this.adapter.setTypeName(TYPE_NAME);
        this.setTs(ts);
    }

    public GenericTaskCompletion() {
        this.adapter.setTypeName(TYPE_NAME);
    }

    @ZCProperty(name = "Task", searchable = true, description = "The task")
    public IArtifact getTaskId() {
        return taskId;
    }

    public void setTaskId(final IArtifact task) {
        taskId = task;
    }

    @ZCProperty(name = "Remaining Time", searchable = true, plottable = true, description = "Remaining time of budget", ts = true)
    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(final long remainingTime) {
        this.remainingTime = remainingTime;
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

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == getTaskId()) {
            setTaskId(newArtifact);
        }
        super.substitute(oldArtifact, newArtifact);
    }

    @Override
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

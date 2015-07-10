package com.zealcore.se.core.model.generic;

import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractDuration;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.IProcessSwitch2;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.IType;

/**
 * GenericTaskExecution is an artifact that keeps information about what
 * tasks/processes/objects that have been executing during the log.
 * 
 * @author cafa
 * 
 */
/**
 * @author cafa
 * 
 */
public class GenericTaskExecution extends AbstractDuration implements
        ITaskDuration, IGenericLogItem {
    // Adapter start
    private final GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "TaskExecution";

    /**
     * @param lastTaskSwitch
     *            is the last task switch logged from this tasks execution.
     * @param sw
     *            is the stop event for this task.
     */
    public GenericTaskExecution(final IProcessSwitch lastTaskSwitch,
            final IProcessSwitch sw) {
        setLogFile(lastTaskSwitch.getLogFile());
        setStartEvent(lastTaskSwitch);
        setStopEvent(sw);
        adapter.setTypeName(TYPE_NAME);
        setOwner(lastTaskSwitch.getResourceUserIn());
    }

    /**
     * @param owner
     *            is the owner that keeps track of this task/process/object.
     */
    public GenericTaskExecution(final IArtifact owner) {
        adapter.setTypeName(TYPE_NAME);
        setOwner(owner);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractDuration#getOwner()
     */
    @Override
    public ITask getOwner() {
        return (ITask) super.getOwner();
    }

    /**
     * @return the priority of this task/process/object. It is up to the
     *         implementer/user of this class to define different priorities.
     */
    public int getPriority() {
        return ((IProcessSwitch2) getStartEvent()).getPriority();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractDuration#getStartEvent()
     */
    @Override
    public IProcessSwitch getStartEvent() {
        return (IProcessSwitch) super.getStartEvent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractDuration#getStopEvent()
     */
    @Override
    public IProcessSwitch getStopEvent() {
        return (IProcessSwitch) super.getStopEvent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#addProperty(java.lang
     * .String, java.lang.Object)
     */
    public void addProperty(final String name, final Object value) {
        this.adapter.addProperty(name, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#getProperty(java.lang
     * .String)
     */
    public Object getProperty(final String key) {
        return this.adapter.getProperty(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.generic.IGenericLogItem#getTypeName()
     */
    public String getTypeName() {
        return this.adapter.getTypeName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.generic.IGenericLogItem#properties()
     */
    public Map<String, Object> properties() {
        return this.adapter.properties();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractDuration#getDurationTime()
     */
    @ZCProperty(name = "Execution Time", searchable = true, description = "The execution time in ns", plottable = true)
    public long getDurationTime() {
        return super.getDurationTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#getType()
     */
    public IType getType() {
        return GenericType.valueOf(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#setTypeName(java.lang
     * .String)
     */
    public void setTypeName(final String typeName) {
        this.adapter.setTypeName(typeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractDuration#toString()
     */
    @Override
    public String toString() {
        return this.getTypeName() + "[" + this.getOwner().getName()
                + ", Execution Time=" + this.getDurationTime() + "ns]";
    }
}

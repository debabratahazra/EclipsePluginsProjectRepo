package com.zealcore.se.core.model.generic;

import java.util.List;
import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractProcessSwitchEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

/**
 * GenericTaskSwitchEvent are used to represent task/process/object switches,
 * i.e. when the focus goes from one to another.
 * 
 * @author cafa
 * 
 */
public class GenericTaskSwitchEvent extends AbstractProcessSwitchEvent
        implements IGenericLogItem {

    private static final String TYPE_NAME = "TaskSwitch";

    private final GenericAdapter adapter = new GenericAdapter();

    private int prio;

    /**
     * Constructor setting the log event type name to "TaskSwitch" and its time
     * stamp.
     * 
     * @param ts
     *            is the log events time stamp.
     */
    public GenericTaskSwitchEvent(final long ts) {
        this.setTs(ts);
        this.adapter.setTypeName(TYPE_NAME);
    }

    /**
     * Constructor.
     */
    public GenericTaskSwitchEvent() {}

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
     * @see com.zealcore.se.core.model.AbstractObject#getZPropertyAnnotations()
     */
    @Override
    public List<SEProperty> getZPropertyAnnotations() {

        final List<SEProperty> list = super.getZPropertyAnnotations();
        list.addAll(this.adapter.toSEProperties());

        return list;
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

    /**
     * This method is for internal use only {@inheritDoc}
     */
    public Map<String, Object> properties() {
        return this.adapter.properties();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#getType()
     */
    @Override
    public IType getType() {
        return GenericType.valueOf(this);
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

    /**
     * @param prio
     */
    public void setPriority(final int prio) {
        this.prio = prio;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IProcessSwitch2#getPriority()
     */
    public int getPriority() {
        return prio;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.AbstractProcessSwitchEvent#getResourceUserOut
     * ()
     */
    @Override
    @ZCProperty(name = "From", searchable = true, description = "The name of the task/process that were active prior to this event")
    public IArtifact getResourceUserOut() {
        return super.getResourceUserOut();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.AbstractProcessSwitchEvent#getResourceUserIn()
     */
    @Override
    @ZCProperty(name = "To", searchable = true, description = "The name of the task/process that will be active after this event")
    public IArtifact getResourceUserIn() {
        return super.getResourceUserIn();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractLogEvent#toString()
     */
    @Override
    public String toString() {
        return this.getTypeName() + "[From="
                + this.getResourceUserOut().getName() + ", To="
                + this.getResourceUserIn() + "]";
    }
}

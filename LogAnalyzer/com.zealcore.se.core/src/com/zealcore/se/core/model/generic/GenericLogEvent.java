package com.zealcore.se.core.model.generic;

import java.util.List;
import java.util.Map;

import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

/**
 * The GenericLogEvent is the basic type of log event handled by Log Analyzer.
 * When creating a log event the GenericLogEvent can be used or if more
 * specified types is going to be created it is possible to use one of the
 * Generic classes, e.g. {@link GenericSendEvent},
 * {@link GenericTaskSwitchEvent}, {@link GenericSequence} etc. It is possible
 * to create new LogEvent type classes by sub classing {@link AbstractLogEvent}.
 * 
 * @author cafa
 */
public class GenericLogEvent extends AbstractLogEvent implements
        IGenericLogItem {

    // Adapter start
    private final GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "LogEvent";

    /**
     * Creates a GenericLogEvent. The log event type name is set to "LogEvent".
     */
    public GenericLogEvent() {
        adapter.setTypeName(TYPE_NAME);
    }

    /**
     * Creates a GenericLogEvent. The log event type name is set to "LogEvent".
     * 
     * @param ts
     *            is the time stamp set to the log event.
     */
    public GenericLogEvent(final long ts) {
        adapter.setTypeName(TYPE_NAME);
        this.setTs(ts);
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

    // Adapter end

    /**
     * @deprecated use setTypeName instead
     * @param defaultEventName
     */
    @Deprecated
    public void setName(final String defaultEventName) {
        setTypeName(defaultEventName);
    }

    /**
     * @deprecated Use getTypeName instead
     * @return The Log Events type name.
     */
    @Deprecated
    public String getName() {
        return getTypeName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractLogEvent#toString()
     */
    @Override
    public String toString() {
        return this.getTypeName() + "[Time=" + getTs() + "]";
    }

}

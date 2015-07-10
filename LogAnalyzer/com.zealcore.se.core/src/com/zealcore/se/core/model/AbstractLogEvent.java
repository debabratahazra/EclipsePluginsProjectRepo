package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.annotation.ZSearchable;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * ILogEvent is the abstract base class for all events in a LogSession.
 * ILogEvent is a AbstractObject and it has a association relationship to the
 * associated LogSession. It also contains a timestamp of type long. It's up to
 * the user to choose the granularity of this timestamp.
 * 
 * @see IObject
 * @see AbstractArtifact
 * 
 * @author pasa
 * @version 1.0
 * @since 1.0
 * 
 */

@ZSearchable
public abstract class AbstractLogEvent extends AbstractObject implements
        ITimed, ILogEvent {

    /*
     * The timestamp of when this event occured.
     */
    private long ts;

    /**
     * Default constructor that creates en empty ILogEvent with the name set to
     * "unknown".
     * 
     */
    public AbstractLogEvent() {}

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ILogEvent#getTs()
     */
    @ZCProperty(name = ILogEvent.TS_PROPERTY, searchable = true, description = "The time in ns at which this event occured", ts = true)
    public long getTs() {
        return this.ts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ILogEvent#setTs(long)
     */

    public void setTs(final long ts) {
        this.ts = ts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ILogEvent#getDate()
     */
    @ZCProperty(name = ILogEvent.DATE_PROPERTY, searchable = true, description = "The date when this event occured (CCYY-MM-DD HH:MM:SS:NSEC)")
    public String getDate() {
        return TimestampUtil.tsToDate(getTs());
    }

    /**
     * toString method for ILogEvent
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '[' + "Time=" + getTs() + ']';
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ILogEvent#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(final Object o) {
        if (o instanceof ITimed) {
            final ITimed other = (ITimed) o;
            return Long.valueOf(this.ts).compareTo(other.getTimeReference());
        }
        return super.compareTo(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ILogEvent#getTimeReference()
     */
    public Long getTimeReference() {
        return this.ts;
    }
}

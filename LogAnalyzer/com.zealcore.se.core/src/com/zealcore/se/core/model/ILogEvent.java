package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

/**
 * The basic log event types that a log event must implement.<br>
 * 
 * @author cafa
 * 
 */
public interface ILogEvent extends ITimed, IObject {

    String DATE_PROPERTY = "Date";

    String TS_PROPERTY = "Timestamp";

    /**
     * Gets the timestamp of when this event occured. Granulation is up to the
     * user to choose.
     * 
     * @return a long representation of the timestamp for this AbstractLogEvent
     */
    @ZCProperty(name = ILogEvent.TS_PROPERTY, searchable = true, description = "The time in ns at which this event occured")
    long getTs();

    /**
     * Adds time to the timestamp of when this AbstractLogEvent occured. More
     * than one object can add time to this timestamp.
     * 
     * @param ts
     *            a long representation of the timestamp to be set
     */

    void setTs(final long ts);

    @ZCProperty(name = DATE_PROPERTY, searchable = true, description = "The date when this event occured (CCYY-MM-DD HH:MM:SS:NSEC)")
    String getDate();

    void setLogFile(LogFile fileObject);

    LogFile getLogFile();

}

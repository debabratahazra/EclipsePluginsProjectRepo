/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.zealcore.se.core.format;

/**
 * This class contains information about an event. An event has a time stamp
 * and optionally contains custom fields as defined by a given type description.
 *
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class GenericEventInfo {
    static final int EVENT_CLASS = 1;

    private final int eventClass;

    private final long timestamp;

    private int logFileId;

    private final int typeId;

    private final FieldValues fieldValues;

    /**
     * Create a new event object with the given time stamp and, optionally, the
     * custom fields as defined by the given type description ID.
     *
     * @param timestamp    the time stamp of the event in nanoseconds.
     * @param typeId       the ID of the optional type description for the event
     * or 0 if there is none.
     * @param fieldValues  the values for all the custom fields of the event as
     * defined by the given type description ID or null if no type description
     * is given.
     */
    public GenericEventInfo(final long timestamp, final int typeId,
            final FieldValues fieldValues) {
        this(EVENT_CLASS, timestamp, typeId, fieldValues);
    }

    /**
     * Create a new event object with the given type classification and time
     * stamp and, optionally, the custom fields as defined by the given type
     * description ID.
     *
     * @param eventClass   the classification of the type of the event.
     * @param timestamp    the time stamp of the event in nanoseconds.
     * @param typeId       the ID of the optional type description for the event
     * or 0 if there is none.
     * @param fieldValues  the values for all the custom fields of the event as
     * defined by the given type description ID or null if no type description
     * is given.
     */
    GenericEventInfo(final int eventClass, final long timestamp,
            final int typeId, final FieldValues fieldValues) {
        this.eventClass = eventClass;
        this.timestamp = timestamp;
        this.typeId = typeId;
        this.fieldValues = fieldValues;
    }

    /**
     * Return the classification of the type of this event.
     *
     * @return the classification of the type of this event.
     */
    public int getEventClass() {
        return eventClass;
    }

    /**
     * Return the time stamp of this event in nanoseconds.
     *
     * @return the time stamp of this event in nanoseconds.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Return the ID of the log file providing this event.
     *
     * @return the ID of the log file providing this event.
     */
    public int getLogFileId() {
        return logFileId;
    }

    /**
     * Set the ID of the log file providing this event.
     *
     * @param logFileId  a log file ID.
     */
    void setLogFileId(final int logFileId) {
        this.logFileId = logFileId;
    }

    /**
     * Return the ID of the optional type description for this event or 0 if
     * there is none.
     *
     * @return the ID of the optional type description for this event or 0 if
     * there is none.
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Return the values for all the custom fields of this event as defined by
     * the type description for this event or null if this event has no type
     * description.
     *
     * @return the values for all the custom fields of this event or null if
     * this event has no type description.
     */
    public FieldValues getFieldValues() {
        return fieldValues;
    }
}

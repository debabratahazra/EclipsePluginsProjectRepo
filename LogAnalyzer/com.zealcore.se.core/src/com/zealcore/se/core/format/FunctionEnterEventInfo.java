package com.zealcore.se.core.format;

/**
 * This class contains information about a function enter event. A function
 * enter event has a time stamp, a task ID, name, the end time of the function
 * enter, and optionally contains custom fields as defined by a given type
 * description.
 * 
 * @see com.zealcore.se.core.format.GenericEventInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class FunctionEnterEventInfo extends GenericEventInfo {
    static final int EVENT_CLASS = 9;

    private final String name;

    private final long endTime;

    /**
     * Create a new function enter event object with the given time stamp, task
     * ID, name of the function enter, end time of the function enter, and
     * optionally, the custom fields as defined by the given type description
     * ID.
     * 
     * @param timestamp
     *            the time stamp of the event in nanoseconds.
     * @param name
     *            the name of the function enter.
     * @param endTime
     *            the end time time.
     * @param typeId
     *            the ID of the optional type description for the event or 0 if
     *            there is none.
     * @param fieldValues
     *            the values for all the custom fields of the event as defined
     *            by the given type description ID or null if no type
     *            description is given.
     */
    public FunctionEnterEventInfo(long timestamp, String name, long endTime,
            int typeId, FieldValues fieldValues) {
        super(EVENT_CLASS, timestamp, typeId, fieldValues);
        this.name = name;
        this.endTime = endTime;
    }

    /**
     * Return the name of the function enter.
     * 
     * @return the name of the function enter.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the end time of the function enter.
     * 
     * @return the end time of the function enter.
     */
    public long getEndTime() {
        return endTime;
    }
}

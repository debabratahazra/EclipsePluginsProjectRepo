package com.zealcore.se.core.format;

/**
 * This class contains information about a function exit event. A function exit
 * event has a time stamp, a task ID, name, the end time of the function exit,
 * and optionally contains custom fields as defined by a given type description.
 * 
 * @see com.zealcore.se.core.format.GenericEventInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class FunctionExitEventInfo extends GenericEventInfo {
    static final int EVENT_CLASS = 10;

    private final String name;

    private final long endTime;

    /**
     * Create a new function exit event object with the given time stamp, task
     * ID, name, end time of the function enter, and, optionally, the custom
     * fields as defined by the given type description ID.
     * 
     * @param timestamp
     *            the time stamp of the event in nanoseconds.
     * @param name
     *            the name of the function exit.
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
    public FunctionExitEventInfo(long timestamp, String name, long endTime,
            int typeId, FieldValues fieldValues) {
        super(EVENT_CLASS, timestamp, typeId, fieldValues);
        this.name = name;
        this.endTime = endTime;
    }

    /**
     * Return the name of the function exit.
     * 
     * @return the name of the function exit.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the end time of the function exit.
     * 
     * @return the end time of the function exit.
     */
    public long getEndTime() {
        return endTime;
    }
}

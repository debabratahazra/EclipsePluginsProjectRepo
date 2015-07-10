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
 * This class contains information about a task release event. A task release
 * event has a time stamp, a task ID, the time budget of the task, and
 * optionally contains custom fields as defined by a given type description.
 *
 * @see com.zealcore.se.core.format.GenericEventInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class TaskReleaseEventInfo extends GenericEventInfo {
    static final int EVENT_CLASS = 3;

    private final int taskId;

    private final long timeBudget;

    /**
     * Create a new task release event object with the given time stamp, task
     * ID, time budget of the task, and, optionally, the custom fields as
     * defined by the given type description ID.
     *
     * @param timestamp       the time stamp of the event in nanoseconds.
     * @param taskId          the ID of the task that was released.
     * @param timeBudget      the time budget, in nanoseconds, of the task.
     * @param typeId          the ID of the optional type description for the
     * event or 0 if there is none.
     * @param fieldValues     the values for all the custom fields of the event
     * as defined by the given type description ID or null if no type
     * description is given.
     */
    public TaskReleaseEventInfo(final long timestamp, final int taskId,
            final long timeBudget, final int typeId,
            final FieldValues fieldValues) {
        super(EVENT_CLASS, timestamp, typeId, fieldValues);
        this.taskId = taskId;
        this.timeBudget = timeBudget;
    }

    /**
     * Return the ID of the task that was released.
     *
     * @return the ID of the task that was released.
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Return the time budget, in nanoseconds, of the task.
     *
     * @return the time budget, in nanoseconds, of the task.
     */
    public long getTimeBudget() {
        return timeBudget;
    }
}

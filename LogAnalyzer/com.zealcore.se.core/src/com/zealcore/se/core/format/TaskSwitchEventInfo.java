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
 * This class contains information about a task switch event. A task switch
 * event has a time stamp, a switched in task ID, a switched out task ID, the
 * priority of the switched in task, and optionally contains custom fields as
 * defined by a given type description.
 *
 * @see com.zealcore.se.core.format.GenericEventInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class TaskSwitchEventInfo extends GenericEventInfo {
    static final int EVENT_CLASS = 2;

    private final int inTaskId;

    private final int outTaskId;

    private final int inTaskPriority;

    /**
     * Create a new task switch event object with the given time stamp, switched
     * in task ID, switched out task ID, priority of the switched in task, and,
     * optionally, the custom fields as defined by the given type description
     * ID.
     *
     * @param timestamp       the time stamp of the event in nanoseconds.
     * @param inTaskId        the ID of the switched in task.
     * @param outTaskId       the ID of the switched out task.
     * @param inTaskPriority  the priority of the switched in task.
     * @param typeId          the ID of the optional type description for the
     * event or 0 if there is none.
     * @param fieldValues     the values for all the custom fields of the event
     * as defined by the given type description ID or null if no type
     * description is given.
     */
    public TaskSwitchEventInfo(final long timestamp, final int inTaskId,
            final int outTaskId, final int inTaskPriority, final int typeId,
            final FieldValues fieldValues) {
        super(EVENT_CLASS, timestamp, typeId, fieldValues);
        this.inTaskId = inTaskId;
        this.outTaskId = outTaskId;
        this.inTaskPriority = inTaskPriority;
    }

    /**
     * Return the ID of the switched in task.
     *
     * @return the ID of the switched in task.
     */
    public int getInTaskId() {
        return inTaskId;
    }

    /**
     * Return the ID of the switched out task.
     *
     * @return the ID of the switched out task.
     */
    public int getOutTaskId() {
        return outTaskId;
    }

    /**
     * Return the priority of the switched in task.
     *
     * @return the priority of the switched in task.
     */
    public int getInTaskPriority() {
        return inTaskPriority;
    }
}

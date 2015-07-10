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
 * This class contains information about a task artifact. A task artifact has
 * an ID, a name, a type, a priority, and optionally contains custom fields
 * as defined by a given type description.
 *
 * @see com.zealcore.se.core.format.GenericArtifactInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class TaskArtifactInfo extends GenericArtifactInfo {
    static final int ARTIFACT_CLASS = 2;

    private final TaskClass taskClass;

    private final int taskPriority;

    /**
     * Create a new task artifact object with the given ID, name, type,
     * priority, and, optionally, the custom fields as defined by the given
     * type description ID. The ID of the task artifact must be unique within
     * the context of the originating log file.
     *
     * @param id            the ID of the task.
     * @param name          the name of the task.
     * @param taskClass     the classification of the type of the task.
     * @param taskPriority  the priority of the task.
     * @param typeId        the ID of the optional type description for the
     * task artifact or 0 if there is none.
     * @param fieldValues   the values for all the custom fields of the task
     * artifact as defined by the given type description ID or null if no type
     * description is given.
     */
    public TaskArtifactInfo(final int id, final String name,
            final TaskClass taskClass, final int taskPriority,
            final int typeId, final FieldValues fieldValues) {
        super(ARTIFACT_CLASS, id, name, typeId, fieldValues);
        this.taskClass = taskClass;
        this.taskPriority = taskPriority;
    }

    /**
     * Return the classification of the type of this task.
     *
     * @return the classification of the type of this task.
     * @see com.zealcore.se.core.format.TaskArtifactInfo.TaskClass
     */
    public TaskClass getTaskClass() {
        return taskClass;
    }

    /**
     * Return the priority of this task.
     *
     * @return the priority of this task.
     */
    public int getTaskPriority() {
        return taskPriority;
    }

    /**
     * This enumeration defines the possible task types.
     */
    public static enum TaskClass {
        /** A task. */
        TASK(1),
        /** A process. */
        PROCESS(2),
        /** A thread. */
        THREAD(3);

        private final int id;

        /**
         * Private constructor for creating the task type enumeration objects.
         *
         * @param id  the ID of this task type enumeration object.
         */
        private TaskClass(final int id) {
            this.id = id;
        }

        /**
         * Return the ID of this task type.
         *
         * @return the ID of this task type.
         */
        public int toId() {
            return id;
        }

        /**
         * Return the task type object for the given task type ID.
         *
         * @param id  a task type ID.
         * @return the task type object for the given task type ID or null if
         * the task type ID is invalid.
         */
        public static TaskClass fromId(final int id) {
            for (TaskClass e : TaskClass.values()) {
                if (e.id == id) {
                    return e;
                }
            }
            return null;
        }

        /**
         * Return a string representation of this task type object.
         *
         * @return a string representation of this task type object.
         * @see java.lang.Object#toString()
         */
        public String toString() {
            switch (this) {
            case TASK:
                return "Task";
            case PROCESS:
                return "Process";
            case THREAD:
                return "Thread";
            default:
                return "Unknown";
            }
        }
    }
}

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
 * This class contains information about a receive event. A receive event has a
 * time stamp, a sender task ID, a receiver task ID, message meta-data, and
 * optionally contains custom fields as defined by a given type description.
 * 
 * @see com.zealcore.se.core.format.GenericEventInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class ReceiveEventInfo extends GenericEventInfo {
    static final int EVENT_CLASS = 6;

    private final int senderTaskId;

    private final int receiverTaskId;

    private final long sentAtTimestamp;

    private final String messageName;

    /**
     * Create a new receive event object with the given time stamp, sender task
     * ID, receiver task ID, message meta-data, and, optionally, the custom
     * fields as defined by the given type description ID.
     * 
     * @param timestamp        the time stamp of the event in nanoseconds.
     * @param senderTaskId     the ID of the task that sent the message.
     * @param receiverTaskId   the ID of the task that received the message.
     * @param sentAtTimestamp  the time stamp, in nanoseconds, when the
     * message was sent at.
     * @param messageName      the name of the message.
     * @param typeId           the ID of the optional type description for the
     * event or 0 if there is none.
     * @param fieldValues      the values for all the custom fields of the event
     * as defined by the given type description ID or null if no type
     * description is given.
     */
    public ReceiveEventInfo(final long timestamp, final int senderTaskId,
            final int receiverTaskId, final long sentAtTimestamp,
            final String messageName, final int typeId,
            final FieldValues fieldValues) {
        super(EVENT_CLASS, timestamp, typeId, fieldValues);
        this.senderTaskId = senderTaskId;
        this.receiverTaskId = receiverTaskId;
        this.sentAtTimestamp = sentAtTimestamp;
        this.messageName = messageName;
    }

    /**
     * Create a new receive event object with the given type classification,
     * time stamp, sender task ID, receiver task ID, message meta-data, and,
     * optionally, the custom fields as defined by the given type description
     * ID.
     * 
     * @param eventClass
     *            the classification of the type of the event.
     * @param timestamp
     *            the time stamp of the event in nanoseconds.
     * @param senderTaskId
     *            the ID of the task that sent the message.
     * @param receiverTaskId
     *            the ID of the task that received the message.
     * @param sentAtTimestamp
     *            the time stamp, in nanoseconds, when the message was sent at.
     * @param messageName
     *            the name of the message.
     * @param typeId
     *            the ID of the optional type description for the event or 0 if
     *            there is none.
     * @param fieldValues
     *            the values for all the custom fields of the event as defined
     *            by the given type description ID or null if no type
     *            description is given.
     */
    public ReceiveEventInfo(final int eventClass, final long timestamp,
            final int senderTaskId, final int receiverTaskId,
            final long sentAtTimestamp, final String messageName,
            final int typeId, final FieldValues fieldValues) {
        super(eventClass, timestamp, typeId, fieldValues);
        this.senderTaskId = senderTaskId;
        this.receiverTaskId = receiverTaskId;
        this.sentAtTimestamp = sentAtTimestamp;
        this.messageName = messageName;
    }

    /**
     * Return the ID of the task that sent the message.
     * 
     * @return the ID of the task that sent the message.
     */
    public int getSenderTaskId() {
        return senderTaskId;
    }

    /**
     * Return the ID of the task that received the message.
     * 
     * @return the ID of the task that received the message.
     */
    public int getReceiverTaskId() {
        return receiverTaskId;
    }

    /**
     * Return the time stamp, in nanoseconds, when the message was sent at.
     * 
     * @return the time stamp, in nanoseconds, when the message was sent at.
     */
    public long getSentAtTimestamp() {
        return sentAtTimestamp;
    }

    /**
     * Return the name of the message.
     * 
     * @return the name of the message.
     */
    public String getMessageName() {
        return messageName;
    }
}

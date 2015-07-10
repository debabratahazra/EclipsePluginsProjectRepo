package com.zealcore.se.core.format;

public class RoseSendEventInfo extends SendEventInfo {
    static final int EVENT_CLASS = 7;

    private int receiverInstance;

    public RoseSendEventInfo(long timestamp, int senderTaskId,
            int receiverTaskId, long receivedAtTimestamp, String messageName,
            int receiverInstance, int typeId, FieldValues fieldValues) {
        super(EVENT_CLASS, timestamp, senderTaskId, receiverTaskId,
                receivedAtTimestamp, messageName, typeId, fieldValues);
        this.receiverInstance = receiverInstance;
    }

    public int getReceiverInstance() {
        return receiverInstance;
    }
}

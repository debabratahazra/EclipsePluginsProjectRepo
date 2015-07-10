package com.zealcore.se.core.format;

public class RoseReceiveEventInfo extends ReceiveEventInfo {
    static final int EVENT_CLASS = 8;

    private final int stateId;

    private final long finish;

    public RoseReceiveEventInfo(long timestamp, int senderTaskId,
            int receiverTaskId, long sentAtTimestamp, String messageName,
            int stateId, long finish, int typeId, FieldValues fieldValues) {
        super(EVENT_CLASS, timestamp, senderTaskId, receiverTaskId,
                sentAtTimestamp, messageName, typeId, fieldValues);
        this.stateId = stateId;
        this.finish = finish;
    }

    public int getStateId() {
        return stateId;
    }

    public long getFinish() {
        return finish;
    }
}

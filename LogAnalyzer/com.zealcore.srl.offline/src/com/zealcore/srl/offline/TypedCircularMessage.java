package com.zealcore.srl.offline;

public class TypedCircularMessage extends AbstractTypedMessage {
    private final long ts;

    public TypedCircularMessage(final CircularMessage message,
            final Struct struct) {
        super(message, struct);
        ts = message.getTs();
    }

    @Override
    String getType() {
        return "Circular";

    }
    
    public long getTs() {
        return ts;
    }
}

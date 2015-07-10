package com.zealcore.srl.offline;

public class TypedLinearMessage extends AbstractTypedMessage {

    public TypedLinearMessage(final LinearMessage message, final Struct struct) {
        super(message, struct);
    }

    @Override
    String getType() {
        return "Linear";
    }
}

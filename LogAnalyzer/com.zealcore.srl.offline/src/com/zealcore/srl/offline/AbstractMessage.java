package com.zealcore.srl.offline;

import java.nio.ByteBuffer;

public class AbstractMessage {
    private int typeId;

    private ByteBuffer data;

    public ByteBuffer getData() {
        return data;
    }

    public void setData(final ByteBuffer data) {
        this.data = data;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(final int typeId) {
        this.typeId = typeId;
    }
}

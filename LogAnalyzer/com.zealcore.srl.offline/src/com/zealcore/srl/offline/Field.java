package com.zealcore.srl.offline;

import java.nio.ByteBuffer;

public class Field {
    static final int ID = 1003;

    private final int numElements;

    public Field(final ByteBuffer data) {
        // skip id, because it is not needed. To be removed in srl 2.0
        data.getInt();
        idStruct = data.getInt();
        idType = data.getInt();
        numElements = data.getInt();
        name = BuffUtil.getNullTermString(data);
    }

    public Field(final int structId, final int fieldTypeId, final int fieldElementCount,
            final String fieldName) {
        idStruct = structId;
        idType = fieldTypeId;
        numElements = fieldElementCount;
        name = fieldName;
    }

    private int idStruct;

    private final int idType;

    private final String name;

    private Type type;

    int getIdStruct() {
        return idStruct;
    }

    public int getIdType() {
        return idType;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type t) {
        type = t;
    }

    public int getNumElements() {
        return numElements;
    }

    public boolean isNullTerminated() {
        return getNumElements() == 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(type).append(" ");
        sb.append(name);
        return sb.toString();
    }

    // Begin temp fix for #663
    void setIdStruct(final int id) {
        idStruct = id;
    }
    // End temp fix for #663
}

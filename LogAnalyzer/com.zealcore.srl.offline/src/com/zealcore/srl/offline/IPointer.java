package com.zealcore.srl.offline;

public interface IPointer {
    int getId();

    long getPointer();

    Object getValue();

    void setValue(Object object);
}

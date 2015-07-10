package com.zealcore.se.ui.views;

import java.util.Iterator;
import java.util.List;

public class KeyedValues {

    private List data;
    
    private Comparable rowKey;

    public KeyedValues(final Comparable rowKey) {
        this.rowKey = rowKey;
        this.data = new java.util.ArrayList();
    }

    public void addValue(final Comparable key, final Number value) {
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument.");
        }
        int keyIndex = getIndex(key);
        if (keyIndex >= 0) {
            KeyedValue kv = (KeyedValue) this.data.get(keyIndex);
            kv.setValue(value);
        } else {
            KeyedValue kv = new KeyedValue(key, value);
            this.data.add(kv);
        }
    }

    private int getIndex(final Comparable key) {
        int i = 0;
        Iterator iterator = this.data.iterator();
        while (iterator.hasNext()) {
            KeyedValue kv = (KeyedValue) iterator.next();
            if (kv.getKey().equals(key)) {
                return i;
            }
            i++;
        }
        // key not found
        return -1;
    }

    public List getData() {
        return data;
    }

    public Comparable getRowKey() {
        return rowKey;
    }
}

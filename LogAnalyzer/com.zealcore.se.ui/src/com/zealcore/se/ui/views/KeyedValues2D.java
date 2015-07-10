package com.zealcore.se.ui.views;

import java.util.List;

public class KeyedValues2D {
    private List rowKeys;

    private List rows;

    public KeyedValues2D() {
        this.rowKeys = new java.util.ArrayList();
        this.rows = new java.util.ArrayList();
    }

    private int getRowIndex(final Comparable key) {
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument. ");
        }
        return this.rowKeys.indexOf(key);
    }

    public void addValue(final Number value, final Comparable rowKey,
            final Comparable columnKey) {

        KeyedValues row;
        int rowIndex = getRowIndex(rowKey);

        if (rowIndex >= 0) {
            row = (KeyedValues) this.rows.get(rowIndex);
        } else {
            row = new KeyedValues(rowKey);
            this.rowKeys.add(rowKey);
            this.rows.add(row);
        }
        row.addValue(columnKey, value);
    }

    public List getRows() {
        return rows;
    }
    
    public void clear() {
        rowKeys.clear();
        rows.clear();
    }
    
}

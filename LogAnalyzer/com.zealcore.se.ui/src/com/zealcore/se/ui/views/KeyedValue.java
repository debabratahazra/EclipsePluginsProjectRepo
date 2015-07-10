package com.zealcore.se.ui.views;

public class KeyedValue {
    /** The key. */
    private Comparable key;

    /** The value. */
    private Number value;

    /**
     * Creates a new (key, value) item.
     * 
     * @param key
     *            the key (should be immutable).
     * @param value
     *            the value (<code>null</code> permitted).
     */
    public KeyedValue(final Comparable key, final Number value) {
        this.key = key;
        this.value = value;
    }

    public Comparable getKey() {
        return key;
    }

    public Number getValue() {
        return value;
    }

    public void setKey(final Comparable key) {
        this.key = key;
    }

    public void setValue(final Number value) {
        this.value = value;
    }

}

package com.zealcore.srl.offline;

public class Pointer implements IPointer {

    private final long pointer;

    private final int typeId;

    private Object value;

    Pointer(final long pointer, final int id, final Object value) {
        super();
        this.pointer = pointer;
        typeId = id;
        this.value = value;
    }

    public long getPointer() {
        return pointer;
    }

    public int getId() {
        return typeId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object obj) {

        if (obj instanceof Pointer) {
            Pointer other = (Pointer) obj;
            if (other.getId() != this.getId()) {
                return false;
            }
            if (other.pointer == this.pointer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (typeId * 23 + pointer * 17);
    }
}

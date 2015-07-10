package com.zealcore.se.ui.util;

import com.zealcore.se.core.NotImplementedException;

public class SynchOffset implements Comparable<Object> {

    private String logsetName;

    private String offsetValue;

    private String baseOffsetValue;

    public SynchOffset() {}

    public String getLogsetName() {
        return logsetName;
    }

    public void setLogsetName(String logsetName) {
        this.logsetName = logsetName;
    }

    public String getOffsetValue() {
        return offsetValue;
    }

    public void setOffsetValue(String offsetValue) {
        this.offsetValue = offsetValue;
    }

    public void setBaseOffsetValue(String offsetValue) {
        this.baseOffsetValue = offsetValue;
        setOffsetValue(offsetValue);
    }

    public String getBaseOffsetValue() {
        return baseOffsetValue;
    }

    @Override
    public int hashCode() {
        return 10 * logsetName.length();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SynchOffset)) {
            return false;
        }

        if (obj == this)
            return true;

        SynchOffset offset = (SynchOffset) obj;

        return logsetName.equalsIgnoreCase(offset.getLogsetName());
    }

    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        if (o instanceof SynchOffset) {
            return this.logsetName.compareTo(((SynchOffset) o).logsetName);
        }
        throw new NotImplementedException();
    }
}

package com.odcgroup.aaa.connector.domainmodel.enums;

import com.odcgroup.otf.jpa.EnumWithValue;


/**
 * Enumeration of the possible values for the edit_e of a DictAttribute.
 * 
 * @author Michael Vorburger (MVO)
 */
public enum DictAttributeEditable implements EnumWithValue {

    NOT_EDITABLE(0),
    INSERT_UPDATE(1),
    INSERT(2),
    INSERT_UPDATE_FILTER(3),
    INSERT_FILTER(4);
    
    private final int value;

    private DictAttributeEditable(int value) {
        this.value = value;
    }
    
    public int value() {
        return this.value;
    }
    
    public static DictAttributeEditable valueOf(int value) {
        for (DictAttributeEditable m : values()) { if(m.value == value) return m; }
        throw new IllegalArgumentException("No Enumeration for this constant: " + value);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }

}

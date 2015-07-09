package com.odcgroup.aaa.connector.domainmodel.enums;

import com.odcgroup.otf.jpa.EnumWithValue;


/**
 * Enumeration of the possible values for the edit_e of a DictAttribute.
 * 
 * @author Michael Vorburger (MVO)
 */
public enum DictAttributeDateTimeWidget implements EnumWithValue {

    DATE_ONLY(0),
    DATETIME_GUIONLY(1),
    DATETIME_IMPORTONLY(2),
    DATETIME_GUIANDIMPORT(3);
    
    private final int value;

    private DictAttributeDateTimeWidget(int value) {
        this.value = value;
    }
    
    public int value() {
        return this.value;
    }
    
    public static DictAttributeDateTimeWidget valueOf(int value) {
        for (DictAttributeDateTimeWidget m : values()) { if(m.value == value) return m; }
        throw new IllegalArgumentException("No Enumeration for this constant: " + value);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }

}

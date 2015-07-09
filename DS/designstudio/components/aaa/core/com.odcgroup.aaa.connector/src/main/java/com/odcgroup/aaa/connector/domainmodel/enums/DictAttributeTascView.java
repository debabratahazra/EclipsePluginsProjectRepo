package com.odcgroup.aaa.connector.domainmodel.enums;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.otf.jpa.EnumWithValue;


/**
 * Enumeration of the possible values for the tasc view attribute ('tasc_view_e') of a DictAttribute.
 * 
 * @see DictAttribute#getTascView()
 * 
 * @author Michael Vorburger (MVO)
 */
public enum DictAttributeTascView implements EnumWithValue {

    REGULAR_FIELD(1),
    MML_SPECIFIC_ASSOCIATION(2),
    MML_SPECIFIC_ENUMERATION(3),
    TAMD_TO_MAPP(4);
    
    private final int value;

    private DictAttributeTascView(int value) {
        this.value = value;
    }
    
    public int value() {
        return this.value;
    }
    
    public static DictAttributeTascView valueOf(int value) {
        for (DictAttributeTascView m : values()) { if(m.value == value) return m; }
        throw new IllegalArgumentException("No Enumeration for this constant: " + value);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }

}

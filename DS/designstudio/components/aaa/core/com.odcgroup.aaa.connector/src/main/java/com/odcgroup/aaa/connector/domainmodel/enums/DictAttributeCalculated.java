package com.odcgroup.aaa.connector.domainmodel.enums;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.otf.jpa.EnumWithValue;


/**
 * Enumeration of the possible values for the calculated attribute ('calculated_e') of a DictAttribute.
 * 
 * @see DictAttribute#getCalculated()
 * 
 * @author Michael Vorburger (MVO)
 */
public enum DictAttributeCalculated implements EnumWithValue {

    PHYSICAL(0),
    CALCULATED(1),
    VIRTUAL(2),
    DENORM(3),
    PHYSICAL_AUTOMATIC(4);
    
    private final int value;

    private DictAttributeCalculated(int value) {
        this.value = value;
    }
    
    public int value() {
        return this.value;
    }
    
    public static DictAttributeCalculated valueOf(int value) {
        for (DictAttributeCalculated m : values()) { if(m.value == value) return m; }
        throw new IllegalArgumentException("No Enumeration for this constant: " + value);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }

}

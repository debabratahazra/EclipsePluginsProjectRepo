package com.odcgroup.aaa.connector.domainmodel.enums;

import com.odcgroup.otf.jpa.EnumWithValue;


/**
 * Enumeration of the possible values for the perm_auth_f of a DictAttribute.
 * 
 * @author Michael Vorburger (MVO)
 */
public enum DictAttributePermAuth implements EnumWithValue {

    NO_USER_PERMITTED_VALUES_ALLOWED(0),
    USER_USER_PERMITTED_VALUES_ALLOWED_STARTING_100(1),
    USER_USER_PERMITTED_VALUES_ALLOWED_STARTING_1(2);
    
    private final int value;

    private DictAttributePermAuth(int value) {
        this.value = value;
    }
    
    public int value() {
        return this.value;
    }
    
    public static DictAttributePermAuth valueOf(int value) {
        for (DictAttributePermAuth m : values()) { if(m.value == value) return m; }
        throw new IllegalArgumentException("No Enumeration for this constant: " + value);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }

}

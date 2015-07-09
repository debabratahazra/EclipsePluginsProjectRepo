package com.odcgroup.aaa.connector.domainmodel.enums;

import com.odcgroup.otf.jpa.EnumWithValue;

/**
 * Enumeration of the possible values for the fk-presentation attribute
 * ('fk_presentation_e') of a DictAttribute.
 *
 * @see DictAttribute#getForeignKeyPresentation()
 *
 * @author ATR
 */
public enum DictAttributeForeignKeyPresentation implements EnumWithValue {

    /** */
    VALUE_0(0),

    /** */
    VALUE_1(1),

    /** */
    VALUE_2(2),

    /** */
    VALUE_3(3);

    /** */
    private final int value;

    /** */
    private DictAttributeForeignKeyPresentation(int value) {
        this.value = value;
    }

    /** */
    public int value() {
        return this.value;
    }

    /** */
    public static DictAttributeForeignKeyPresentation valueOf(int value) {
        for (DictAttributeForeignKeyPresentation m : values()) { if(m.value == value) return m; }
        throw new IllegalArgumentException("No Enumeration for this constant: " + value);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }

}

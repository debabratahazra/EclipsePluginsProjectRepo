package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;

/**
 * @author cafa
 * 
 */
public class IgnoreType implements IModifyingFieldType {

    private static final String LABEL = "Ignore field";

    private static final String ID = "IGNORE";

    public static final IgnoreType INSTANCE = new IgnoreType();

    private static final int HASHCODE = 123456789;

    public void modify(final GenericLogEvent event, final String value) {
    // Do nothing since IgnoreType should ignore to add the field data.
    }

    public String getId() {
        return IgnoreType.ID;
    }

    public String getLabel() {
        return IgnoreType.LABEL;
    }

    public Object valueOf(final String s) {
        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof IgnoreType) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return IgnoreType.HASHCODE;
    }

    public boolean canMatch(final String proposal) {
        return true;
    }
}

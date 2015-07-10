/**
 * 
 */
package com.zealcore.se.core;

import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProperty;

public class TestProperty implements IProperty {

    private final String name;

    private final Object value;

    public TestProperty(final String name, final Object value) {
        this.name = name;
        this.value = value;
    }

    public String getDescription() {
        return "Description";
    }

    public String getName() {
        return this.name;
    }

    @SuppressWarnings("unchecked")
    public Class getReturnType() {
        return this.value.getClass();
    }

    public Object getValue(final IObject object) {
        return this.value;
    }

    public boolean isPlotable() {
        return false;
    }

    public boolean isSearchable() {
        return false;
    }

    public boolean isGeneric() {
        return false;
    }
}

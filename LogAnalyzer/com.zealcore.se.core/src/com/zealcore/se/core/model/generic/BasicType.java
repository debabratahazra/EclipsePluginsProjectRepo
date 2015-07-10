package com.zealcore.se.core.model.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zealcore.se.core.model.IProperty;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;

public class BasicType implements IType {

    private String id = "";

    private final List<IProperty> properties = new ArrayList<IProperty>();

    private String name = "";

    private final Class<? extends IGenericLogItem> baseClass = null;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Collection<IProperty> getProperties() {
        return properties;
    }

    public boolean isA(final IType type) {
        // TODO Need test if this actually works as expected
        if (ReflectiveType.valueOf(this.baseClass).isA(type)) {
            return true;
        }
        return type.getId().equals(getId());
    }

    public boolean isPlottable() {
        return false;
    }

    public boolean isSearchable() {
        return false;
    }
}

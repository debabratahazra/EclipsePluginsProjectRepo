package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class PropertyNestedDescriptor implements IPropertySource {

    private List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();

    private Object key;

    private String propName;

    public PropertyNestedDescriptor(final Object key, final String propName) {
        this.key = key;
        this.propName = propName;
    }

    public PropertyNestedDescriptor(final String propName) {
        this.propName = propName;
    }

    public Object getEditableValue() {
        return propName;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return descriptors.toArray(new IPropertyDescriptor[descriptors.size()]);
    }

    public void addPropertyDescriptor(final IPropertyDescriptor desc) {
        this.descriptors.add(desc);
    }

    public Object getPropertyValue(final Object id) {
        if (null != id) {
            return new String(id.toString());
        }
        return null;
    }

    public boolean isPropertySet(final Object id) {
        return (key != null);
    }

    public void resetPropertyValue(final Object id) {}

    public void setPropertyValue(final Object id, final Object value) {}

    public String toString() {
        return this.propName;
    }
}

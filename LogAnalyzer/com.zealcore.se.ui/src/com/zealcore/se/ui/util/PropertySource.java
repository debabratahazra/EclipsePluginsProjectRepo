/*
 * 
 */
package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

/**
 * The PropertySource wraps {@link IObject}s into {@link IPropertySource}s.
 */
public class PropertySource implements IPropertySource, IAdaptable {

    private static final String TIMESTAMP_SEPARATOR = " ";

    private static final String UNKNOWN = "???";

    private static final String ABSTRACT_PREFIX = "Abstract";

    private IObject[] wrapped = new IObject[0];

    /**
     * Creates a new {@link PropertySource} from a {@link IObject}.
     * 
     * @param wrapped
     *                the wrapped
     */
    public PropertySource(final IObject wrapped) {
        super();
        this.wrapped = new IObject[] { wrapped };

    }

    /**
     * Create a new {@link PropertySource} from a list of {@link IObject}s
     * 
     * @param items
     *                the items
     */
    public PropertySource(final IObject... items) {
        wrapped = items.clone();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    public Object getEditableValue() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
        final List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
        for (final IObject item : wrapped) {
            if (item != null) {
                toDescriptors(descriptors, item);
            }

        }
        return descriptors.toArray(new IPropertyDescriptor[0]);
    }

    /**
     * Creates descriptors of an item and puts them into the descriptors
     * parameter.
     * 
     * @param item
     *                the item
     * @param descriptors
     *                the descriptors
     */
    private void toDescriptors(final List<IPropertyDescriptor> descriptors,
            final IObject item) {
        for (final SEProperty property : item.getZPropertyAnnotations()) {

            final Object key = property;
            if (key != null && property.getData() != null) {
                final PropertyDescriptor desc = new PropertyDescriptor(key,
                        PropertySource.getDisplayName(property.getName()));

                final String name = item.getType().getName();
                desc.setCategory(name);
                descriptors.add(desc);
            }
        }
    }

    /**
     * Gets the display name of a class.
     * 
     * @param type
     *                the clazz
     * 
     * @return the display name
     */
    private static String getDisplayName(final IType type) {
        final String name = type.getName();
        return PropertySource.getDisplayName(name);
    }

    /**
     * Gets the display name of a String name.
     * 
     * @param name
     *                the name
     * 
     * @return the display name
     */
    private static String getDisplayName(final String name) {
        if (name.startsWith(PropertySource.ABSTRACT_PREFIX)) {
            return name.replaceFirst(PropertySource.ABSTRACT_PREFIX, "");
        }
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(final Object obj) {
        if (obj instanceof SEProperty) {
            final SEProperty property = (SEProperty) obj;
            String propertyData = property.getData().toString();
            if (property.isTs()) {
                // String format = TimeFormat.format(property.format(),
                // ((Number) property.getData()).longValue());
                return splitTimestampString(propertyData);
            }
            // Is it a data struct that should be presented as nested info
            if (property.getName().equals("Data") && propertyData.contains("{")
                    && propertyData.contains("}")) {
                PropertyNestedDescriptor nested = null;
                String[] groups = propertyData.split("\\{");
                if (groups.length > 1) {
                    nested = new PropertyNestedDescriptor(groups[0]);
                    groups = groups[1].split(";");
                    for (int i = 0; i < groups.length - 1; i++) {
                        if (null != groups[i] && null != groups[i + 1]) {
                            String[] split = groups[i].split("=");
                            String id = split[0].trim();
                            String displayName = split[1].trim();
                            nested
                                    .addPropertyDescriptor(new PropertyDescriptor(
                                            displayName, id));
                        }
                    }
                }
                return nested;
            }
            return propertyData;

        }
        return PropertySource.UNKNOWN;
    }

    public static String splitTimestampString(final String ts) {
        StringBuilder bld = new StringBuilder();
        String ts2 = ts;

        if (ts2.length() <= 3) {
            return ts2;
        }

        if (ts2.length() % 3 != 0) {
            if (ts2.length() % 3 == 1) {
                bld.append(ts2.substring(0, 1));
                bld.append(TIMESTAMP_SEPARATOR);
                ts2 = ts2.substring(1);
            } else if (ts.length() % 3 == 2) {
                bld.append(ts2.substring(0, 2));
                bld.append(TIMESTAMP_SEPARATOR);
                ts2 = ts2.substring(2);
            } else {
                throw new RuntimeException("Failed to split timestamp: " + ts);
            }
        }

        for (int i = 0; i < ts2.length() / 3; i++) {
            bld.append(ts2.substring(i * 3, i * 3 + 3) + TIMESTAMP_SEPARATOR);
        }
        bld.deleteCharAt(bld.lastIndexOf(TIMESTAMP_SEPARATOR));
        return bld.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(final Object arg0) {
        return false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(final Object arg0) {
    // Do nothing

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
     *      java.lang.Object)
     */
    public void setPropertyValue(final Object arg0, final Object arg1) {
    // Do nothing

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(final Class adapter) {
        if (adapter == IPropertySource.class) {
            return this;
        }
        return null;
    }

    /**
     * Converts an {@link IObject} to a {@link PropertySource} String value .
     * 
     * @param item
     *                the item
     * 
     * @return the string
     */
    public static String toString(final IObject item) {
        final StringBuilder bld = new StringBuilder();
        // PropertySource src = new PropertySource(item);
        final String newline = System.getProperty("line.separator");

        final IType type = item.getType();

        // bld.append("<b>");
        bld.append(PropertySource.getDisplayName(type));
        for (final SEProperty prop : item.getZPropertyAnnotations()) {

            bld.append(newline);
            final String name = PropertySource.getDisplayName(prop.getName());
            bld.append(name == null ? PropertySource.UNKNOWN : name);
            bld.append(":  ");
            final Object data = prop.getData();
            if (prop.isTs()) {
                bld.append(splitTimestampString(prop.getData().toString()));
            } else {
                bld.append(data == null ? PropertySource.UNKNOWN : data
                        .toString());
            }
        }
        final String toString = bld.toString();

        return toString;
    }
}

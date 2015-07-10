package com.zealcore.se.core.model.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zealcore.se.core.model.SEProperty;

/**
 * Stores information about log events. A log event have a type and any number
 * of properties stored in a list. All 'Generic' log events have a
 * GenericAdapter.
 * 
 * @author cafa
 * 
 */
public final class GenericAdapter {
    private final Map<String, Object> properties = new HashMap<String, Object>();

    private String name;

    /**
     * @param name
     *            is the name of the property to store.
     * @param value
     *            is the properties value
     */
    void addProperty(final String name, final Object value) {
        this.properties.put(name, value);
    }

    /**
     * @return the properties as a list of {@link SEProperty}
     */
    public List<SEProperty> toSEProperties() {

        final List<SEProperty> list = new ArrayList<SEProperty>();
        for (final Entry<String, Object> entry : this.properties.entrySet()) {
            SEProperty property = null;
            if (entry.getValue() instanceof Number) {
                Object value = entry.getValue();
                String objectName = entry.getKey();
                property = SEProperty.builder(objectName, value).searchable(
                        true).plotable(isPlotable(objectName, value)).build();
            } else {
                property = SEProperty.builder(entry.getKey(), entry.getValue())
                        .searchable(true).build();
            }
            list.add(property);
        }
        return list;
    }

    /**
     * @param defaultEventName
     *            is the name of the log event. This name is shown as the log
     *            event name in the GUI views.
     */
    public void setTypeName(final String defaultEventName) {
        this.name = defaultEventName;
    }

    /**
     * @return the name of the log event. This name is shown as the log event
     *         name in the GUI views.
     */
    public String getTypeName() {
        return this.name;
    }

    /**
     * This method is for internal use only
     */
    Map<String, Object> properties() {
        return Collections.unmodifiableMap(this.properties);
    }

    /**
     * @param key
     *            is the name of the properties value that should be returned.
     * @return is the value of the wanted property.
     */
    Object getProperty(final String key) {
        return this.properties.get(key);
    }

    /**
     * @param name
     *            is the name of the properties value that should be tested if
     *            it can be plotted
     * @param value
     *            is the value of the properties value that should be tested if
     *            it can be plotted
     * @return true if the property can be plotted in the plot view othervise
     *         false.
     */
    public boolean isPlotable(final String name, final Object value) {
        boolean retValue;
        if ("nTick".equalsIgnoreCase(name)) {
            retValue = false;
        } else if ("Tick".equalsIgnoreCase(name)) {
            retValue = false;
        } else if ("Entry".equalsIgnoreCase(name)) {
            retValue = false;
        } else {
            retValue = Number.class.isAssignableFrom(value.getClass());
        }
        return retValue;
    }
}

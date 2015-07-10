package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

/**
 * SEProperty is the property class for annotated attributes in the Log Analyzer
 * Model. It consists of an attribute object (data) and a name of the property.
 * Instances of this class is created by the getZPropertyAnnotations() method in
 * AbstractObject. The method searches through the class for ZProperty
 * annotations and for each annotation that is found an instance of the
 * SEProperty class is created. The instances then goes up to the GUI via the
 * datalayer and the businesslayer.
 * 
 * 
 * 
 * @author pasa
 * @version 1.0
 * @since 1.0
 * 
 */
public class SEProperty {
    /*
     * The name of the property
     */
    private String name;

    /*
     * The property of the attribute
     */
    private Object data;

    private boolean searchable;

    private boolean plotable;

    private final boolean direct;

    private String format;

    private boolean ts;

    public SEProperty(final String name, final Object data) {
        this.name = name;
        this.data = data;
        this.searchable = false;
        this.plotable = false;
        this.direct = false;
    }

    /**
     * Constructor that creates a SEProperty with the given name and attribute
     * object.
     * 
     * @param annotation
     *                a name for the new property
     * @param data
     *                a attribute object for the new property
     */
    SEProperty(final ZCProperty annotation, final Object data) {
        this.name = annotation.name();
        this.searchable = annotation.searchable();
        this.data = data;
        this.plotable = annotation.plottable();
        this.direct = annotation.direct();
        this.format = annotation.format();
        ts = annotation.ts();
    }

    /**
     * Gets the attribute object of this property
     * 
     * @return an attribute object of this property
     */
    public Object getData() {
        return this.data;
    }

    /**
     * Sets the attribute object of this property.
     * 
     * @param data
     *                a attribute object to be set for this property
     */
    public void setData(final Object data) {
        this.data = data;
    }

    /**
     * Gets the name of this property.
     * 
     * @return the name of this property
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the given name to this property.
     * 
     * @param name
     *                a name to be set of this property
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * toString method for SEProperty
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[Name=" + getName()
                + ",Attribute" + getData() + ']';
    }

    public boolean isSearchable() {
        return this.searchable;
    }

    public boolean isPlotable() {
        return this.plotable;
    }

    public boolean isDirect() {
        return this.direct;
    }

    public static Builder builder(final String name, final Object value) {

        return new Builder(name, value);

    }

    public static final class Builder {

        private final String name;

        private final Object value;

        private boolean searchable;

        private boolean plotable;

        public Builder(final String name, final Object value) {
            this.name = name;
            this.value = value;
        }

        public SEProperty build() {
            final SEProperty property = new SEProperty(this.name, this.value);
            property.plotable = this.plotable;
            property.searchable = this.searchable;
            return property;
        }

        public Builder searchable(final boolean searchable) {
            this.searchable = searchable;
            return this;
        }

        public Builder plotable(final boolean plotable) {
            this.plotable = plotable;
            return this;
        }

    }

    public String format() {
        return format;
    }

    public boolean isTs() {
        return ts;
    }
}

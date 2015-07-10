package com.zealcore.se.iw.types.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FieldTypeFactory {

    private static FieldTypeFactory instance;

    private final List<IFieldType> fieldTypes = new ArrayList<IFieldType>();

    private FieldTypeFactory() {
        this.fieldTypes.add(new IgnoreType());

        this.fieldTypes.add(new NumberType(Long.class));
        this.fieldTypes.add(new NumberType(Integer.class));
        this.fieldTypes.add(new NumberType(Float.class));
        this.fieldTypes.add(new NumberType(Double.class));

        // fieldTypes.add(new TimeStampType());
        this.fieldTypes.add(new TimestampTypeYMD());
        this.fieldTypes.add(new TimestampTypeTimeMicrosec());
        this.fieldTypes.add(new TimestampTypeYear());
        this.fieldTypes.add(new TimestampTypeMonth());
        this.fieldTypes.add(new TimestampTypeDay());
        this.fieldTypes.add(new TimestampTypeHour());
        this.fieldTypes.add(new TimestampTypeMinute());
        this.fieldTypes.add(new TimestampTypeSecond());
        this.fieldTypes.add(new TimestampTypeMillisecond());
        this.fieldTypes.add(new TimestampTypeMicrosecond());
        this.fieldTypes.add(new TimestampTypeNanosecond());

        this.fieldTypes.add(new TimestampTypeSecondMicrosecond());
        this.fieldTypes.add(new StringType());
        this.fieldTypes.add(new NameType());
    }

    /**
     * Get a field type by its Id. Returns null if no such type was found
     * 
     * @param id
     *                the id
     * 
     * @return the field type
     */
    public IFieldType getFieldType(final String id) {
        for (final IFieldType type : getRegisteredFieldTypes()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }

    public List<IFieldType> getRegisteredFieldTypes() {
        return Collections.unmodifiableList(this.fieldTypes);
    }

    public static FieldTypeFactory getInstance() {
        if (FieldTypeFactory.instance == null) {
            FieldTypeFactory.instance = new FieldTypeFactory();
        }
        return FieldTypeFactory.instance;
    }
}

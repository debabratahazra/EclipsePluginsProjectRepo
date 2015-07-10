/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.zealcore.se.core.format;

/**
 * This class defines the description of a field in a type description.
 * A field description contains a name, a type, and the element count if it
 * describes an array.
 *
 * @see com.zealcore.se.core.format.TypeDescription
 */
public class FieldDescription {
    private final String fieldName;

    private final FieldType fieldType;

    private final int elementCount;

    /**
     * Create a new field description object with the given name and type.
     *
     * @param fieldName  the name of the field.
     * @param fieldType  the type of the field.
     * @see com.zealcore.se.core.format.FieldDescription.FieldType
     */
    public FieldDescription(final String fieldName, final FieldType fieldType) {
        this(fieldName, fieldType, 0);
    }

    /**
     * Create a new field description object with the given name, type, and
     * element count if the field is an array.
     *
     * @param fieldName     the name of the field.
     * @param fieldType     the type of the field.
     * @param elementCount  the element count if the field is an array, zero
     * otherwise.
     * @see com.zealcore.se.core.format.FieldDescription.FieldType
     */
    public FieldDescription(final String fieldName, final FieldType fieldType,
            final int elementCount) {
        if (fieldName == null) {
            throw new IllegalArgumentException();
        }
        if (elementCount < 0) {
            throw new IllegalArgumentException();
        }
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.elementCount = elementCount;
    }

    /**
     * Return the name of this field.
     *
     * @return the name of this field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Return the type of this field.
     *
     * @return the type of this field.
     * @see com.zealcore.se.core.format.FieldDescription.FieldType
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * Return the element count if this field is an array, zero otherwise.
     *
     * @return the element count if this field is an array, zero otherwise.
     */
    public int getElementCount() {
        return elementCount;
    }

    /**
     * Return the size of this field description in bytes.
     *
     * @return the size of this field description in bytes.
     */
    public int size() {
        return 4 + fieldName.length() + 4 + 4;
    }

    /**
     * This enumeration defines the possible types of a field in a type
     * description.
     */
    public static enum FieldType {
        /** An 8-bit signed integer. */
        INT8(1),
        /** An 8-bit unsigned integer. */
        UINT8(2),
        /** A 16-bit signed integer. */
        INT16(3),
        /** A 16-bit unsigned integer. */
        UINT16(4),
        /** A 32-bit signed integer. */
        INT32(5),
        /** An 32-bit unsigned integer. */
        UINT32(6),
        /** A 64-bit signed integer. */
        INT64(7),
        /** A 64-bit unsigned integer. */
        UINT64(8),
        /** A 32-bit IEEE 754 floating-point value. */
        FLOAT(9),
        /** A 64-bit IEEE 754 floating-point value. */
        DOUBLE(10),
        /** A boolean value. */
        BOOLEAN(11),
        /** A string of 8-bit characters. */
        STRING(12),
        /** An artifact ID (represented as a 32-bit unsigned integer). */
        ARTIFACT_ID(13);

        private final int id;

        /**
         * Private constructor for creating the field type enumeration objects.
         *
         * @param id  the ID of this field type enumeration object.
         */
        private FieldType(final int id) {
            this.id = id;
        }

        /**
         * Return the ID of this field type.
         *
         * @return the ID of this field type.
         */
        public int toId() {
            return id;
        }

        /**
         * Return the field type object for the given field type ID.
         *
         * @param id  a field type ID.
         * @return the field type object for the given field type ID or null if
         * the field type ID is invalid.
         */
        public static FieldType fromId(final int id) {
            for (FieldType e : FieldType.values()) {
                if (e.id == id) {
                    return e;
                }
            }
            return null;
        }
    }
}

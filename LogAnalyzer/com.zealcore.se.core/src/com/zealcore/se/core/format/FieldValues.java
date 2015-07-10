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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * An object of this class holds the values for all the fields in a type
 * description. The values for the fields must be added in the same order and
 * with the same types as they are defined in the list of field descriptions in
 * the corresponding type description object.
 *
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldDescription
 * @see com.zealcore.se.core.format.FieldDescription.FieldType
 */
public class FieldValues {
    private final ByteArrayOutputStream bout;

    private final DataOutputStream out;

    /**
     * Create a new and initially empty field values object.
     */
    public FieldValues() {
        bout = new ByteArrayOutputStream();
        out = new DataOutputStream(bout);
    }

    /**
     * Add a byte field value.
     *
     * @param value  the byte field value to add.
     */
    public void addByteValue(final byte value) {
        try {
            out.writeByte(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add a byte array field value.
     *
     * @param value  the byte array field value to add.
     */
    public void addByteArrayValue(final byte[] value) {
        try {
            out.write(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add a short field value.
     *
     * @param value  the short field value to add.
     */
    public void addShortValue(final short value) {
        try {
            out.writeShort(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add a short array field value.
     *
     * @param value  the short array field value to add.
     */
    public void addShortArrayValue(final short[] value) {
        try {
            for (int i = 0; i < value.length; i++) {
                out.writeShort(value[i]);
            }
        } catch (IOException ignore) {}
    }

    /**
     * Add an integer field value.
     *
     * @param value  the integer field value to add.
     */
    public void addIntValue(final int value) {
        try {
            out.writeInt(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add an integer array field value.
     *
     * @param value  the integer array field value to add.
     */
    public void addIntArrayValue(final int[] value) {
        try {
            for (int i = 0; i < value.length; i++) {
                out.writeInt(value[i]);
            }
        } catch (IOException ignore) {}
    }

    /**
     * Add a long field value.
     *
     * @param value  the long field value to add.
     */
    public void addLongValue(final long value) {
        try {
            out.writeLong(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add a long array field value.
     *
     * @param value  the long array field value to add.
     */
    public void addLongArrayValue(final long[] value) {
        try {
            for (int i = 0; i < value.length; i++) {
                out.writeLong(value[i]);
            }
        } catch (IOException ignore) {}
    }

    /**
     * Add a float field value.
     *
     * @param value  the float field value to add.
     */
    public void addFloatValue(final float value) {
        try {
            out.writeFloat(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add a float array field value.
     *
     * @param value  the float array field value to add.
     */
    public void addFloatArrayValue(final float[] value) {
        try {
            for (int i = 0; i < value.length; i++) {
                out.writeFloat(value[i]);
            }
        } catch (IOException ignore) {}
    }

    /**
     * Add a double field value.
     *
     * @param value  the double field value to add.
     */
    public void addDoubleValue(final double value) {
        try {
            out.writeDouble(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add a double array field value.
     *
     * @param value  the double array field value to add.
     */
    public void addDoubleArrayValue(final double[] value) {
        try {
            for (int i = 0; i < value.length; i++) {
                out.writeDouble(value[i]);
            }
        } catch (IOException ignore) {}
    }

    /**
     * Add a boolean field value.
     *
     * @param value  the boolean field value to add.
     */
    public void addBooleanValue(final boolean value) {
        try {
            out.writeBoolean(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add a string field value.
     *
     * @param value  the string field value to add.
     */
    public void addStringValue(final String value) {
        try {
            out.writeInt(value.length());
            out.writeBytes(value);
        } catch (IOException ignore) {}
    }

    /**
     * Add an artifact ID field value.
     *
     * @param value  the artifact ID field value to add.
     */
    public void addArtifactIdValue(final int value) {
        try {
            out.writeInt(value);
        } catch (IOException ignore) {}
    }

    /**
     * Return the size, in bytes, of all the field values in this object.
     *
     * @return the size, in bytes, of all the field values in this object.
     */
    public int size() {
        return out.size();
    }

    /**
     * Return the binary representation of this field values object as a byte
     * array.
     *
     * @return the binary representation of this field values object as a byte
     * array.
     */
    public byte[] toByteArray() {
        return bout.toByteArray();
    }
}

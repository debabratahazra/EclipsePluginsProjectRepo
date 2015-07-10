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

import java.util.List;

/**
 * This class defines the type description for a custom event or artifact.
 * A type description contains an ID, a name, and a list of field descriptions.
 *
 * @see com.zealcore.se.core.format.FieldDescription
 */
public class TypeDescription {
    private final int typeId;

    private final String typeName;

    private int logFileId;

    private final List<FieldDescription> fields;

    /**
     * Create a new type description object with the given ID, name, and field
     * descriptions.
     *
     * @param typeId    the ID of the type description.
     * @param typeName  the name of the type description.
     * @param fields    the list of field descriptions for the type description.
     * @see com.zealcore.se.core.format.FieldDescription
     */
    public TypeDescription(final int typeId, final String typeName,
            final List<FieldDescription> fields) {
        if (typeName == null) {
            throw new IllegalArgumentException();
        }
        if (fields == null) {
            throw new IllegalArgumentException();
        }
        this.typeId = typeId;
        this.typeName = typeName;
        this.fields = fields;
    }

    /**
     * Return the ID of this type description.
     *
     * @return the ID of this type description.
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Return the name of this type description.
     *
     * @return the name of this type description.
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Return the ID of the log file providing this type description.
     *
     * @return the ID of the log file providing this type description.
     */
    public int getLogFileId() {
        return logFileId;
    }

    /**
     * Set the ID of the log file providing this type description.
     *
     * @param logFileId  a log file ID.
     */
    void setLogFileId(final int logFileId) {
        this.logFileId = logFileId;
    }

    /**
     * Return the list of field descriptions for this type description.
     *
     * @return the list of field descriptions for this type description.
     * @see com.zealcore.se.core.format.FieldDescription
     */
    public List<FieldDescription> getFields() {
        return fields;
    }
}

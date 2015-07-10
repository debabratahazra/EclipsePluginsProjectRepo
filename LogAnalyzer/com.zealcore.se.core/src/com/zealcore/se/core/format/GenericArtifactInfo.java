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
 * This class contains information about an artifact. An artifact has an ID and
 * a name and optionally contains custom fields as defined by a given type
 * description.
 *
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class GenericArtifactInfo {
    static final int ARTIFACT_CLASS = 1;

    private final int artifactClass;

    private final int id;

    private final String name;

    private int logFileId;

    private final int typeId;

    private final FieldValues fieldValues;

    /**
     * Create a new artifact object with the given ID and name and, optionally,
     * the custom fields as defined by the given type description ID.
     * The ID of the artifact must be unique within the context of the
     * originating log file.
     *
     * @param id           the ID of the artifact.
     * @param name         the name of the artifact.
     * @param typeId       the ID of the optional type description for the
     * artifact or 0 if there is none.
     * @param fieldValues  the values for all the custom fields of the artifact
     * as defined by the given type description ID or null if no type
     * description is given.
     */
    public GenericArtifactInfo(final int id, final String name,
            final int typeId, final FieldValues fieldValues) {
        this(ARTIFACT_CLASS, id, name, typeId, fieldValues);
    }

    /**
     * Create a new artifact object with the given ID and name and, optionally,
     * the custom fields as defined by the given type description ID.
     * The ID of the artifact must be unique within the context of the
     * originating log file.
     *
     * @param artifactClass  the classification of the type of the artifact.
     * @param id             the ID of the artifact.
     * @param name           the name of the artifact.
     * @param typeId         the ID of the optional type description for the
     * artifact or 0 if there is none.
     * @param fieldValues    the values for all the custom fields of the
     * artifact as defined by the given type description ID or null if no type
     * description is given.
     */
    GenericArtifactInfo(final int artifactClass, final int id,
            final String name, final int typeId, final FieldValues fieldValues) {
        this.artifactClass = artifactClass;
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.fieldValues = fieldValues;
    }

    /**
     * Return the classification of the type of this artifact.
     *
     * @return the classification of the type of this artifact.
     */
    public int getArtifactClass() {
        return artifactClass;
    }

    /**
     * Return the ID of this artifact.
     *
     * @return the ID of this artifact.
     */
    public int getId() {
        return id;
    }

    /**
     * Return the name of this artifact.
     *
     * @return the name of this artifact.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the ID of the log file providing this artifact.
     *
     * @return the ID of the log file providing this artifact.
     */
    public int getLogFileId() {
        return logFileId;
    }

    /**
     * Set the ID of the log file providing this artifact.
     *
     * @param logFileId  a log file ID.
     */
    void setLogFileId(final int logFileId) {
        this.logFileId = logFileId;
    }

    /**
     * Return the ID of the optional type description for this artifact or 0 if
     * there is none.
     *
     * @return the ID of the optional type description for this artifact or 0 if
     * there is none.
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Return the values for all the custom fields of this artifact as defined
     * by the type description for this artifact or null if this artifact has no
     * type description.
     *
     * @return the values for all the custom fields of this artifact or null if
     * this artifact has no type description.
     */
    public FieldValues getFieldValues() {
        return fieldValues;
    }
}

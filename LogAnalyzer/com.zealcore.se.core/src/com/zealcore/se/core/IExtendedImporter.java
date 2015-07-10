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

package com.zealcore.se.core;

import java.io.File;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.se.core.ifw.ImportContext;

/**
 * This is the new interface for a Log Analyzer importer. An importer is a
 * parser that reads a log file and returns information about its events and
 * artifacts.
 */
public interface IExtendedImporter {
    /**
     * This method checks if the specified log file can be imported by this
     * importer.
     *
     * @param file  the log file to be checked.
     * @return true if the log file can be imported, false otherwise.
     */
    public boolean canRead(File file);

    /**
     * This method sets the import context, which means that an import is being
     * performed and the importer will soon be asked for its events and
     * artifacts.
     *
     * @param context  the context for the import.
     */
    public void setContext(ImportContext context);

    /**
     * This method should return an iterator over all the custom type
     * descriptions for the events and artifacts in the log file. This
     * method can return the whole information at once.
     *
     * @return an iterator over all the custom type descriptions for the
     * events and artifacts in the log file.
     */
    public Iterable<TypeDescription> getTypeDescriptions();

    /**
     * This method should return an iterator over all the events in the log
     * file. This method should be implemented lazily, which means that all
     * events should not be read in advance.
     * <p>
     * The events must be returned in chronological order.
     *
     * @return an iterator over all the events in the log file.
     */
    public Iterable<GenericEventInfo> getEvents();

    /**
     * This method should return an iterator over all the artifacts in the
     * log file. This method should be implemented lazily, which means that
     * all artifacts should not be read in advance. Artifacts are not
     * necessarily referred to by the events.
     *
     * @return an iterator over all the artifacts in the log file.
     */
    public Iterable<GenericArtifactInfo> getArtifacts();
}

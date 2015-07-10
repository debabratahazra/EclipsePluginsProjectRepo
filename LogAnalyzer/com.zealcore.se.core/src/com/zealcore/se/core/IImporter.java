/**
 * 
 */
package com.zealcore.se.core;

import java.io.File;

import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;

/**
 * This is the extension point for all importers. An importer is a parser who
 * reads a log and creates events and artifacts.
 */
public interface IImporter {
    /**
     * This method checks if the file can be imported by the importer.
     * 
     * @return true if the file can be imported, false otherwise
     */
    boolean canRead(final File file);

    /**
     * This method sets the import context, which means that an import is being
     * performed and soon will be asked for log events and artifacts.
     */
    void setContext(final ImportContext context);

    /**
     * This method should return an iterator over logEvents read from the
     * inputstream. This method should be implemented lazily, which means that
     * not all events should be read in advance.
     * 
     * The events must be in chronological order.
     * 
     * @return the prefered return may be an Iterable-iterator.
     */
    Iterable<ILogEvent> getLogEvents();

    /**
     * @return the number of logEvents or a negative number if the number is not
     *         known
     */
    int size();

    /**
     * @return artifacts that are not necessarily refered to by events from
     *         {@link IImporter#getLogEvents()} The prefered return may be an
     *         Iterable-iterator.
     * 
     */
    Iterable<IArtifact> getArtifacts();
}

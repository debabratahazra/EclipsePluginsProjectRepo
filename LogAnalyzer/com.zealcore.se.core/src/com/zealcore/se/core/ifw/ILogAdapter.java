package com.zealcore.se.core.ifw;

import org.eclipse.core.resources.IFile;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;

/**
 * This interface is NOT intended to be implemented by clients
 * 
 * @author stch
 * 
 */
public interface ILogAdapter {
    String getName();

    String getId();

    IFile getFile();

    /**
     * This method checks if the file can be imported by the importer.
     * 
     * @return true if the file can be imported, false otherwise
     */
    boolean canRead(final IFile file);

    /**
     * 
     * @return the number of logEvents or a negative number if the number is not
     *         known
     */
    int size();

    /**
     * @return This method should return an iterator over logEvents read from
     *         the inputstream in setInputStream. This method should be
     *         implemented lazily, which means that not all events should be
     *         read in advance.
     * 
     *         The prefered return may be an Iterable-iterator.
     */
    Iterable<ILogEvent> getLogEvents();

    /**
     * @return artifacts that are not necessarily refered to by events from
     *         IImporter#getLogEvents() The prefered return may be an
     *         Iterable-iterator.
     * 
     */
    Iterable<IArtifact> getArtifacts();

    /**
     * Closes the underlying log reader
     */
    void close();

    /**
     * Sets the context which this LogAdapter will be associated with during its
     * lifetime
     * 
     * @param context
     *            the context
     * @return this object, for convenience
     */

    ILogAdapter setContext(ImportContext context);

}

package com.zealcore.se.core.model;

import java.util.Collection;
import java.util.List;

import com.zealcore.se.core.annotation.ZCProperty;

public interface IObject {

    
    String LOGFILE_PROPERTY = "Log File";

    String LOGSET_PROPERTY = "LogSet";

    String DATE_PROPERTY = "Date";
    
    List<SEProperty> getZPropertyAnnotations();
    IType getType();
    
    /**
     * Gets the associated LogSession for this AbstractLogEvent.
     * 
     * @return the LogSession of this event
     */
    @ZCProperty(name = LOGFILE_PROPERTY, searchable = false, description = "The contextual LogFile")
    LogFile getLogFile();

    
    /**
     * Sets the associated LogSession for this AbstractLogEvent.
     * 
     * @param logFile
     *                a log file to be set for this event
     */
    void setLogFile(final LogFile logFile);

    /**
     * @return all artifacts referenced by this event
     */
    Collection<IArtifact> referencedArtifacts();
        

    /**
     * Substitutes the oldArtifact belonging to this.getArtifacts() with the
     * newArtifact. That is, in subsequent calls, newArtifact will belong to
     * this.getArtifacts() instead of oldArtifact.
     * 
     * If sublcsasses does a substitution, the method must return. If not, they
     * must propagate to the super implementation.
     * 
     * @throws IllegalArgumentException
     *                 when oldArtifact is not part this.getArtifacts()
     * @throws java.lang.ClassCastException
     *                 when newArtifact is not of the same class as oldArtifact
     */
    void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact);
}

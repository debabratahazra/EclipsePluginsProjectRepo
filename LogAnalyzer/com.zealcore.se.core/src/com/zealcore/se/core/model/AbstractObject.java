package com.zealcore.se.core.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.core.annotation.ZCProperty;

/**
 * AbstractObject is the abstract base class for all CLASSES whose instances are
 * to be stored in a database. An instance of AbstractObject consists of a name
 * and an identifier. The value of the id is assigned/generated automatically by
 * hibernate when the instance is stored in the database.
 * 
 * @author pasa
 * @version 1.0
 * @since 1.0
 * 
 */
@SuppressWarnings("unchecked")
public abstract class AbstractObject implements Comparable, IObject {

    public IType getType() {
        return ReflectiveType.valueOf(this.getClass());
    }

    /**
     * Gets all Log Analyzer Properties (annotated with ZCProperty) for the
     * calling object class.
     * 
     * Note: This method is placed here because else won't the id and name of
     * this abstract base class be searchable for the derived CLASSES.
     * 
     * return a list of SEProperty that was annotated in current class.
     * 
     * @see SEProperty
     * @see ZCProperty
     */
    private List<SEProperty> propertyList;

    private LogFile abstractLogFile;

    public List<SEProperty> getZPropertyAnnotations() {
        propertyList = new ArrayList<SEProperty>();
        for (final Method m : this.getClass().getMethods()) {
            // If found the property annotation
            if (m.isAnnotationPresent(ZCProperty.class)) {
                // Create a new Log Analyzer Property
                try {
                    this.propertyList.add(new SEProperty(m
                            .getAnnotation(ZCProperty.class), m.invoke(this)));
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (final InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            Collections.sort(this.propertyList, new Comparator<SEProperty>() {

                public int compare(final SEProperty o1, final SEProperty o2) {
                    return o1.getName().compareTo(o2.getName());
                }

            });
        }

        return propertyList;
    }

    /**
     * toString method for AbstractObject.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public int compareTo(final Object o) {
        // FIXME: Research default implementation
        throw new NotImplementedException();
    }

    /**
     * Gets the associated LogSession for this AbstractLogEvent.
     * 
     * @return the LogSession of this event
     */
    @ZCProperty(name = LOGFILE_PROPERTY, searchable = false, description = "The contextual LogFile")
    public LogFile getLogFile() {
        return this.abstractLogFile;
    }

    /**
     * Sets the associated LogSession for this AbstractLogEvent.
     * 
     * @param logFile
     *                a log file to be set for this event
     */
    public void setLogFile(final LogFile logFile) {
        this.abstractLogFile = logFile;
    }

    /**
     * @return all artifacts referenced by this event
     */
    public Collection<IArtifact> referencedArtifacts() {
        return new ArrayList<IArtifact>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IObject#substitute(com.zealcore.se.core.model.IArtifact,
     *      com.zealcore.se.core.model.IArtifact)
     */
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {}
}

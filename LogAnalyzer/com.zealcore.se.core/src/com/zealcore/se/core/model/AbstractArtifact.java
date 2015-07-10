package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

/**
 * The class AbstractArtifact is the abstract base class for all resources and
 * resource users in the Log Analyzer Model. An artifact is a AbstractObject,
 * so it contains a name and an identifier. It also contains a reference to the
 * LogSession to which it belongs.
 * 
 * @see IObject
 * @see AbstractResource
 * 
 * @author pasa
 * @version 1.0
 * @since 1.0
 * 
 */
public abstract class AbstractArtifact extends AbstractObject implements
        IArtifact {

    private String name;

    private boolean root;

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof IArtifactID) {
            final IArtifactID other = (IArtifactID) obj;
            if (!getId().equals(other.getId())) {
                return false;
            }
            if (!getClassName().equals(other.getClassName())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (getClassName() == null ? 0 : getClassName().hashCode());
        result = prime * result
                + (getId() == null ? 0 : getId().hashCode());
        return result;
    }

    /**
     * Gets the artifact pool of this AbstractObject
     * 
     * @return the artifact pool.
     */

    public String getClassName() {
        return this.getClass().getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IArtifact#getName()
     */
    @ZCProperty(name = "Name", searchable = true, description = "The unique name of the artifact")
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IArtifact#getId()
     */
    public String getId() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IArtifact#isRoot()
     */
    public boolean isRoot() {
        return this.root;
    }

    /**
     * Sets the name of this AbstractObject.
     * 
     * @param name
     *                the name to be set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    public void setRoot(final boolean root) {
        this.root = root;
    }

    /**
     * toString method for IArtifact
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '[' + getName() + ']';
    }

    @Override
    public int compareTo(final Object o) {
        if (o instanceof IArtifact) {
            IArtifact a = (IArtifact) o;
            return getName().compareTo(a.getName());

        }
        throw new IllegalArgumentException(
                "Unable to compare AbstractArtifact to " + o);
    }
}

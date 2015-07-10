/*
 * 
 */
package com.zealcore.se.ui.internal;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;

public class ArtifactID implements IArtifactID {

    private static final String NAME_NODE = "name";

    private static final String CLAZZ_NODE = "clazz";

    static final String ARTIFACT_ID_NODE = "artifactId";

    private String clazz;

    private String name;

    /**
     * {@inheritDoc}
     */
    public String getClassName() {
        return clazz;
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return name;
    }

    /**
     * Creates a new ArtifactID representing the specified artifact value.
     * 
     * @param artifact
     *                the artifact
     * 
     * @return the artifact ID
     */
    public static ArtifactID valueOf(final IArtifact artifact) {
        final ArtifactID id = new ArtifactID();

        id.clazz = artifact.getClass().getName();
        id.name = artifact.getName();
        return id;
    }

    /**
     * Creates a new ArtifactID representing the specified identifier value.
     * 
     * @param identifier
     *                the identifier
     * 
     * @return the artifact ID
     */
    public static ArtifactID valueOf(final IArtifactID identifier) {
        final ArtifactID id = new ArtifactID();

        id.clazz = identifier.getClassName();
        id.name = identifier.getId();
        return id;
    }

    /**
     * Creates a new IArtifactID representing the specified memento data values.
     * 
     * @param instance
     *                the instance
     * 
     * @return the I artifact ID
     */
    public static IArtifactID valueOf(final IMemento instance) {

        final String clazzName = instance.getString(ArtifactID.CLAZZ_NODE);
        final String name = instance.getString(ArtifactID.NAME_NODE);

        final ArtifactID id = new ArtifactID();

        id.clazz = clazzName;
        id.name = name;

        return id;
    }

    /**
     * Saves the {@link ArtifactID} to a memento instance
     * 
     * @param instance
     *                the instance
     */
    public void save(final IMemento instance) {

        instance.putString(ArtifactID.CLAZZ_NODE, getClassName());
        instance.putString(ArtifactID.NAME_NODE, getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (clazz == null ? 0 : getClassName().hashCode());
        result = prime * result + (name == null ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ArtifacId[Class=" + getClassName() + ", name=" + getId() + "]";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof IArtifactID)) {
            return false;
        }
        final IArtifactID other = (IArtifactID) obj;

        if (clazz == null) {
            if (other.getClassName() != null) {
                return false;
            }
        } else if (!clazz.equals(other.getClassName())) {
            return false;
        }
        if (name == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!name.equals(other.getId())) {
            return false;
        }
        return true;
    }
}

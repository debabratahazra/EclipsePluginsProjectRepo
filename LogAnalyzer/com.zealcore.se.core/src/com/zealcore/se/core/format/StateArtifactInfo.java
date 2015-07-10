package com.zealcore.se.core.format;

/**
 * This class contains information about a state artifact. A state artifact has
 * an ID, a name, a type, parent id, and optionally contains custom fields as
 * defined by a given type description.
 * 
 * @see com.zealcore.se.core.format.GenericArtifactInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class StateArtifactInfo extends GenericArtifactInfo {
    static final int ARTIFACT_CLASS = 4;

    private final int parentId;

    public StateArtifactInfo(int id, String name, int parentId, int typeId,
            FieldValues fieldValues) {
        super(ARTIFACT_CLASS, id, name, typeId, fieldValues);
        this.parentId = parentId;
    }

    /**
     * Return the parent id of this state.
     * 
     * @return the parent id of this state.
     */
    public int getParentId() {
        return parentId;
    }
}

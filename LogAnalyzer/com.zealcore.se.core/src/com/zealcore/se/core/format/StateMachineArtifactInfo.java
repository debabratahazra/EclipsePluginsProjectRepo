package com.zealcore.se.core.format;

/**
 * This class contains information about a state machine artifact. A state
 * machine artifact has an ID, a name, a type, and optionally contains custom
 * fields as defined by a given type description.
 * 
 * @see com.zealcore.se.core.format.GenericArtifactInfo
 * @see com.zealcore.se.core.format.TypeDescription
 * @see com.zealcore.se.core.format.FieldValues
 */
public class StateMachineArtifactInfo extends GenericArtifactInfo {
    static final int ARTIFACT_CLASS = 3;

    public StateMachineArtifactInfo(int id, String name, int typeId,
            FieldValues fieldValues) {
        super(ARTIFACT_CLASS, id, name, typeId, fieldValues);
    }

}

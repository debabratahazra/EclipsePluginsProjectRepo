package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

@SuppressWarnings("unchecked")
public interface IArtifact extends IArtifactID, IObject, Comparable {

    /**
     * Gets the name if this AbstractObject
     * 
     * @return the name in String representation.
     */
    @ZCProperty(name = "Name", searchable = true, description = "The unique name of the artifact")
    String getName();
    
    void setName(String name);

    String getId();

    boolean isRoot();

    void setRoot(boolean root);


}

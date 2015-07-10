package com.zealcore.se.core.model;

import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;

public interface IState extends IArtifact {

    String UNKNOWN = "Unknown";

    @ZCProperty(name = "Parent", description = "The parent statemachine")
    IArtifact getParent();

    void setParent(final IArtifact parent);

    Collection<IArtifact> referencedArtifacts();

    void substitute(final IArtifact oldArtifact, final IArtifact newArtifact);

}

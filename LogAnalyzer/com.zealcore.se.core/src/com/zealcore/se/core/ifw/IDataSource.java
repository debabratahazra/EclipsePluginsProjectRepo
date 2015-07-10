package com.zealcore.se.core.ifw;

import java.util.List;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;

public interface IDataSource {
    void refresh();
    
    IArtifact getArtifact(final IArtifactID id);

    List<IArtifact> getArtifacts();

    <T extends IArtifact> List<T> getArtifacts(final Class<T> clazz);
}

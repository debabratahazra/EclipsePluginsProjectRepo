package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;

abstract class AbstractQueryTest implements IDataSource, ITimeProvider {

    private boolean refreshCalled;
    private Map<IArtifactID, IArtifact> artifacts = new HashMap<IArtifactID, IArtifact>();

    public IArtifact getArtifact(final IArtifactID id) {
        return artifacts.get(id);
    }

    public List<IArtifact> getArtifacts() {
        return new ArrayList<IArtifact>(artifacts.values());
    }

    @SuppressWarnings("unchecked")
    public <T extends IArtifact> List<T> getArtifacts(final Class<T> clazz) {
        final List<T> data = new ArrayList<T>();
        for (final IArtifact a : this.getArtifacts()) {
            if (clazz.isInstance(a)) {
                data.add((T) a);
            }
        }
        return data;
    }
    
    abstract IQuery getQuery();
        
    

    public final void refresh() {
        populate(Reason.REFRESH);
        refreshCalled = true;
    }
    
    protected boolean getRefreshCalled() {
        return refreshCalled;
    }
    
    public void setRefreshCalled(final boolean refreshCalled) {
        this.refreshCalled = refreshCalled;
    }
    
    protected abstract void populate(Reason r);
    
    protected void addArtifact(final IArtifact m) {
        artifacts.put(m, m);
    }
}

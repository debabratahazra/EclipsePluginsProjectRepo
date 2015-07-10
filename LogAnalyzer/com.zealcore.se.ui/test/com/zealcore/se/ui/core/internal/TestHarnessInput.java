package com.zealcore.se.ui.core.internal;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.IPersistable;
import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.ISynchable;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.EventTimelineBrowser;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.ILogsetBrowser;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.util.ArtifactColorMap;

public class TestHarnessInput implements ILogViewInput {

    private final ICaseFile casefile = new TestCaseFile();

    private final ILogSessionWrapper log = new TestLogSet();

    private final TestViewSet cluster = new TestViewSet();

    private IArtifactID data;

    public boolean exists() {
        return true;
    }

    public ICaseFile getCaseFile() {
        return this.casefile;
    }

    public IArtifactID getData() {
        return this.data;
    }

    public ILogSessionWrapper getLog() {
        return this.log;
    }

    public IProject getProject() {
        throw new NotImplementedException();
    }

    public TestViewSet getTimeCluster() {
        return this.cluster;
    }

    public void setData(final IArtifactID data) {
        this.data = data;
    }

    private class TestCaseFile implements ICaseFile {

        public ILogSessionWrapper addLog(final String file) {
            return TestHarnessInput.this.log;
        }

        public ILogSessionWrapper[] getLogs() {
            return new ILogSessionWrapper[] { TestHarnessInput.this.log };
        }

        public IResource getResource() {
            throw new NotImplementedException();
        }

        public void removeLog(final ILogSessionWrapper log) {}

        public void saveState() {
            throw new NotImplementedException();

        }

        @SuppressWarnings("unchecked")
        public Object getAdapter(final Class adapter) {
            throw new NotImplementedException();
        }

        public Object[] getChildren(final Object o) {
            return new Object[] { TestHarnessInput.this.log };
        }

        public ImageDescriptor getImageDescriptor(final Object object) {
            throw new NotImplementedException();
        }

        public String getLabel(final Object o) {
            return "TEST-CASE-FILE";
        }

        public Object getParent(final Object o) {
            throw new NotImplementedException();
        }

    }

    public class TestLogSet implements ILogSessionWrapper {

        private final Set<IChangeListener> changeListeners = new HashSet<IChangeListener>();

        private final Set<ITimeCluster> clusters = new HashSet<ITimeCluster>();

        private final String name = "TEST-LOGSET";

        private final ArtifactColorMap artifactColorMap = new ArtifactColorMap();

        TestLogSet() {}

        public ITimeCluster createTimeCluster(final String name) {
            throw new NotImplementedException();
        }

        public ICaseFile getCaseFile() {
            return TestHarnessInput.this.casefile;
        }

        public ArtifactColorMap getColorMap() {
            return this.artifactColorMap;
        }

        public ITimeCluster getDefaultViewSet() {
            return TestHarnessInput.this.cluster;
        }

        public ITimeCluster[] getTimeClusters() {
            return new ITimeCluster[] { TestHarnessInput.this.cluster };
        }

        public boolean removeTimeCluster(final ITimeCluster cluster) {
            return this.clusters.remove(cluster);
        }

        public Object[] getChildren(final Object o) {
            throw new NotImplementedException();
        }

        public ImageDescriptor getImageDescriptor(final Object object) {
            throw new NotImplementedException();
        }

        public String getLabel(final Object o) {
            return this.name;
        }

        public Object getParent(final Object o) {
            return TestHarnessInput.this.casefile;
        }

        public void addChangeListener(final IChangeListener changeListener) {
            this.changeListeners.add(changeListener);

        }

        public void removeChangeListener(final IChangeListener changeListener) {
            this.changeListeners.remove(changeListener);
        }

        @SuppressWarnings("unchecked")
        public Object getAdapter(final Class adapter) {
            if (adapter.equals(ITimeCluster.class)) {
                return this.getDefaultViewSet();
            }
            return null;
        }

        public UUID getId() {
            return null;
        }

        public IFolder getFolder() {
            return null;
        }

    }

    public class TestViewSet implements ITimeCluster {

        private final ArrayList<ISynchable> syncs = new ArrayList<ISynchable>();

        private long time;

        private long max;

        private long min;
        
        private long offset;

        public boolean addSynchable(final ISynchable synchable) {
            return this.syncs.add(synchable);
        }

        public ILogMark logmark(final String note) {
            throw new NotImplementedException();
        }

        public boolean chain() {
            throw new NotImplementedException();
        }

        public ILogMark[] getLogmarks() {
            throw new NotImplementedException();
        }

        public long getCurrentTime() {
            return this.time;
        }

        public int getId() {
            return 0;
        }

        public long getMax() {
            return this.max;
        }

        public long getMin() {
            return this.min;
        }

        public void setMax(final long max) {
            this.max = max;
        }

        public void setMin(final long min) {
            this.min = min;
        }

        public String getName() {
            return this.getUid();
        }

        public ILogSessionWrapper getParent() {
            return TestHarnessInput.this.log;
        }

        public String getUid() {
            return "TEST-VIEW-SET";
        }

        public boolean isChained() {
            return false;
        }

        public void removeLogmark(final ILogMark bm) {
            throw new NotImplementedException();

        }

        public boolean removeSynchable(final ISynchable synchable) {
            return this.syncs.remove(synchable);
        }

        public void setCurrentTime(final long time) {
            final TimeEvent event = new TimeEvent(this, this.time);
            this.time = time;

            for (final ISynchable s : this.syncs) {
                s.synch(event);
            }

        }

        public boolean unChain() {
            return false;
        }

        public void addLogMark(final ILogMark mark) {}
        
        public void setSynchronizationOffset(long offset) {
            this.offset = offset;            
        }
        
        public long getSynchronizationOffset(String offsetWith) {
            // TODO Auto-generated method stub
            return 0;
        }
        
        public java.util.Set<java.util.Map.Entry<String,Long>> getSynchronizationOffsets() {
            return null;
        };
        
        public void removeAllOffsets() {
        }
        
        public Object removeOffset(String name) {
            return null;
        }

        public void setSynchronizationOffset(String offsetWith, long offset) {
        }
        
        public long getPrimarySynchronizedEventTime() {
            throw new NotImplementedException();
        }
    }

    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    public String getName() {
        return null;
    }

    public IPersistableElement getPersistable() {
        return null;
    }

    public String getToolTipText() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public Object getAdapter(final Class adapter) {
        return null;
    }

    public String getFactoryId() {
        return null;
    }

    public void saveState(final IMemento memento) {
    }

    public ILogsetBrowser getBrowser() {
        return new EventTimelineBrowser();
    }

    public void addPersitable(final IPersistable p) {
        throw new UnsupportedOperationException("NIE..");
    }

    public void removePersistable(final IPersistable p) {
        throw new UnsupportedOperationException("NIE");
    }

    public String getBrowserId() {
        return null;
    }

    public Logset getLogset() {
        return Logset.valueOf(getLog().getId());
    }

    public long getCurrentTime() {
        return getTimeCluster().getCurrentTime();
    }
}

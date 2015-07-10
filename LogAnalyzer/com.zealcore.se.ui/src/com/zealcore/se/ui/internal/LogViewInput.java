/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

import com.zealcore.se.core.IPersistable;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.editors.EventTimelineBrowser;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.LogsetEditorInputFactory;

public final class LogViewInput implements ILogViewInput {

    private static final String TAG_INPUT_NOT_EXIST = "NOT_EXIST";

    private static final String TAG_UID = "logset.uid";

    private static final String TAG_BROWSER_ID = "browser.id";

    private static final String TAG_DATA = "logset.object";

    /** The DELIMITER. */
    private static final String DELIMITER = " > ";

    /** The cluster. */
    private final ILogSessionWrapper logset;

    /** The data. */
    private IArtifactID data;

    private final List<IPersistable> additions = new ArrayList<IPersistable>();

    private IMemento savedState;

    private String browserId = EventTimelineBrowser.BROWSER_ID;

    /**
     * Creates a new ILogViewInput representing the specified cluster value.
     * 
     * @param cluster
     *                the cluster
     * 
     * @return the view input
     */
    public static ILogViewInput valueOf(final ILogSessionWrapper cluster) {
        return new LogViewInput(cluster);
    }

    /**
     * The Constructor.
     * 
     * @param cluster
     *                the cluster
     */
    LogViewInput(final ILogSessionWrapper cluster) {
        logset = cluster;
    }

    /**
     * {@inheritDoc}
     */
    public ICaseFile getCaseFile() {
        return logset.getCaseFile();

    }

    /**
     * {@inheritDoc}
     */
    public ILogSessionWrapper getLog() {
        return logset;
    }

    /**
     * {@inheritDoc}
     */
    public ITimeCluster getTimeCluster() {
        return logset.getDefaultViewSet();
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists() {
        if (logset == null) {
            return false;
        }

        if (getTimeCluster() == null) {
            return false;
        }
        if (logset.getCaseFile() == null) {
            return false;
        }

        return logset.getCaseFile().getResource().exists();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getCaseFile() + LogViewInput.DELIMITER + getLog()
                + LogViewInput.DELIMITER + getTimeCluster();
    }

    /**
     * {@inheritDoc}
     */
    public IArtifactID getData() {
        final Logset dataSource = getLogset();
        if (dataSource != null) {
            return dataSource.getArtifact(data);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void setData(final IArtifactID data) {
        this.data = data;
    }

    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    public String getName() {
        return getLog().getLabel(getLog());
    }

    public IPersistableElement getPersistable() {
        return this;
    }

    public String getToolTipText() {
        return getLog().toString();
    }

    @SuppressWarnings("unchecked")
    public Object getAdapter(final Class adapter) {
        return null;
    }

    public String getFactoryId() {
        return LogsetEditorInputFactory.getFactoryId();
    }

    public void saveState(final IMemento memento) {
        if (!exists()) {
            memento.putString(LogViewInput.TAG_UID,
                    LogViewInput.TAG_INPUT_NOT_EXIST);
            return;
        }
        final ILogSessionWrapper log = getLog();
        for (final IPersistable persistable : additions) {
            persistable.saveState(memento);
        }

        memento.putString(LogViewInput.TAG_BROWSER_ID, browserId);
        memento.putString(LogViewInput.TAG_UID, log.getId().toString());

        if (getData() instanceof IArtifact) {
            final IMemento instance = memento
                    .createChild(LogViewInput.TAG_DATA);
            final ArtifactID artifactId = ArtifactID
                    .valueOf((IArtifact) getData());
            artifactId.save(instance);
        }
    }

    public String getBrowserId() {
        return browserId;
    }

    public void addPersitable(final IPersistable p) {
        additions.add(p);
        if (getSavedState() != null) {
            p.init(getSavedState());
        }
    }

    public void removePersistable(final IPersistable p) {
        additions.remove(p);
    }

    /**
     * Returns the saved state, if any
     * 
     * @return the saved state, or null if there were none
     */
    private IMemento getSavedState() {
        return savedState;
    }

    public static IAdaptable valueOf(final IMemento memento) {

        final String uid = memento.getString(LogViewInput.TAG_UID);
        if (uid == null) {
            return null;
        }

        if (uid.equals(LogViewInput.TAG_INPUT_NOT_EXIST)) {
            return new MissingInput("N/A");
        }

        final ILogViewInput input = CaseFileManager.getInputByUID(uid);
        if (input instanceof LogViewInput) {
            final LogViewInput view = (LogViewInput) input;
            view.savedState = memento;
            view.browserId = memento.getString(LogViewInput.TAG_BROWSER_ID);
            view.initData(memento);

        } else {
            return null;
        }
        return input;
    }

    private void initData(final IMemento memento) {
        if (memento == null) {
            return;
        }
        final IMemento child = memento.getChild(LogViewInput.TAG_DATA);
        if (child == null) {
            return;
        }
        final IArtifactID id = ArtifactID.valueOf(child);
        final Logset dataSource = getLogset();
        if (dataSource != null) {
            data = dataSource.getArtifact(id);
        }
    }

    public static ILogViewInput valueOf(final ILogSessionWrapper logset,
            final String browserId) {
        final ILogViewInput input = LogViewInput.valueOf(logset);
        if (input instanceof LogViewInput) {
            final LogViewInput bid = (LogViewInput) input;
            bid.browserId = browserId;

        }
        return input;
    }

    public Logset getLogset() {
        return Logset.valueOf(getLog().getId());
    }
    public IFolder getLogsetDirectory() {
        return this.logset.getFolder();
    }

    private static class MissingInput implements ILogViewInput {

        private final String name;

        MissingInput(final String name) {
            this.name = name;
        }

        public void addPersitable(final IPersistable p) {}

        public boolean exists() {
            return false;
        }

        public String getBrowserId() {
            return null;
        }

        public ICaseFile getCaseFile() {
            return null;
        }

        public IArtifactID getData() {
            return null;
        }

        public ILogSessionWrapper getLog() {
            return null;
        }

        public ITimeCluster getTimeCluster() {
            return null;
        }

        public void removePersistable(final IPersistable p) {}

        public void setData(final IArtifactID data) {}

        public ImageDescriptor getImageDescriptor() {
            return IconManager.getImageDescriptor(IconManager.EVENT_SMALL_IMG);
        }

        public String getName() {
            return name;
        }

        public IPersistableElement getPersistable() {
            return null;
        }

        public String getToolTipText() {
            return name;
        }

        @SuppressWarnings("unchecked")
        public Object getAdapter(final Class adapter) {
            return null;
        }

        public String getFactoryId() {
            return LogsetEditorInputFactory.getFactoryId();
        }

        public void saveState(final IMemento memento) {}

        public Logset getLogset() {
            return null;
        }

        public long getCurrentTime() {
            return 0;
        }
    }

    public long getCurrentTime() {
        return getTimeCluster().getCurrentTime();
    }
}

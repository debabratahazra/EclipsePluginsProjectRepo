package com.zealcore.se.core.ifw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.zealcore.plugin.control.LicenseException;
import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.ITimestampUpdateListener;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ImportExceptionList;
import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.Messages;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.format.BBBinReader;
import com.zealcore.se.core.format.BBBinWriter;
import com.zealcore.se.core.format.LogFileInfo;
import com.zealcore.se.core.ifw.assertions.AssertionRunner;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.generic.GenericLogFile;
import com.zealcore.se.core.model.generic.GenericTask;

public final class Logset implements IDataSource {
    private final Set<IChangeListener> changeListeners = new HashSet<IChangeListener>();

    private final Set<ITimestampUpdateListener> timestampUpdateListeners = new HashSet<ITimestampUpdateListener>();

    private final Set<ILogAdapter> logs = new LinkedHashSet<ILogAdapter>();

    private QueryManager queries = new QueryManager();

    private QueryManager lastQueries = null;

    private final UUID id;

    private static boolean disabled;

    private String indexedLogFilePath;

    private BBBinReader binReader;

    private long currentTime;

    public static final String BIN_EXT = ".bin";

    public static final String INDEXED_LOG_FILE_NAME = "logset.bin";

    private Logset(final UUID id) {
        this.id = id;
        queries.setLogset(this);
    }

    private static Map<UUID, Logset> logsets = new HashMap<UUID, Logset>();

    private final Map<IArtifactID, IArtifact> artifacts = new HashMap<IArtifactID, IArtifact>();

    private static StatisticQuery statQuery;

    private static TaskQuery taskQuery;

    private boolean importSuccess;

    private int importerVersionType = IFWFacade.IEXTENDEDIMPORTER_VERSION;

    private boolean oldImporterVersionSet = false;

    private int noOfEventsToSkip;

    private boolean isLogsetLocked;

    private IProgressMonitor pMonitor;

    private List<ILogAdapter> currentActiveLogAdapters = new ArrayList<ILogAdapter>();

    private boolean logsetChanged = false;

    boolean populateTypePackage;

    static Collection<Logset> getLogsets() {
        return Collections.unmodifiableCollection(Logset.logsets.values());
    }

    public static Logset valueOf(final UUID id) {
        if (!Logset.logsets.containsKey(id)) {
            final Logset logset = new Logset(id);
            statQuery = StatisticQuery.valueOf(logset);
            taskQuery = TaskQuery.valueOf(logset);
            switch (logset.getImporterVersionType()) {
            case IFWFacade.IIMPORTER_VERSION:
                logset.queries.add(statQuery);
                logset.queries.add(taskQuery);
                break;
            case IFWFacade.IEXTENDEDIMPORTER_VERSION:
                // Fall through
            default:
            }
            Logset.logsets.put(id, logset);
        }
        Logset logset = Logset.logsets.get(id);
        if (logset == null) {
            throw new IllegalStateException(
                    "Logset map failed to get logset with id " + id + "!");
        }
        return logset;
    }

    public void addQuery(final IQuery query, final long syncKey) {
        query.initialize(this);
        queries.add(query);
        if (syncKey != this.syncKey) {
            return;
        }
        try {
            reImport(Reason.QUERY_ADDED);
        } catch (ImportOperationCancelledException e) {}
    }

    public void addQuery(final IQuery query) {
        query.initialize(this);
        queries.add(query);
        if (isLocked()) {
            return;
        }
        try {
            reImport(Reason.QUERY_ADDED);
        } catch (ImportOperationCancelledException e) {}
    }

    public void addQuery(final IQuery query, final IProgressMonitor monitor) {
        query.initialize(this);
        try {
            if (query instanceof SearchQuery
                    || query instanceof AssertionRunner) {
                lastQueries = queries;
                queries = new QueryManager();
                queries.setLogset(this);
                queries.add(query);
                reImport(Reason.QUERY_ADDED, monitor);
            } else {
                queries.add(query);
                reImport(Reason.QUERY_ADDED, monitor);
            }
        } catch (Exception ex) {}
    }

    private void reImport(final Reason reason, final IProgressMonitor monitor)
            throws ImportOperationCancelledException {
        this.doImport(monitor, reason);
    }

    public void removeQuery(final IQuery query) {
        if (query instanceof SearchQuery || query instanceof AssertionRunner) {
            queries = lastQueries;
            lastQueries = null;
        } else {
            queries.remove(query);
        }
    }

    public void addLog(final ILogAdapter log)
            throws ImportOperationCancelledException {
        /*
         * If a logset/project is renamed, the addLog is called before removLog.
         * The workaround is to check if all adapters have a correct path before
         * adding the new adapter.
         */
        List<ILogAdapter> adaptersToRemove = new ArrayList<ILogAdapter>();
        for (ILogAdapter adapter : logs) {

            if (!adapter.getFile().exists()) {
                adaptersToRemove.add(adapter);
                adapter.close();
            }
        }
        if (adaptersToRemove.size() > 0) {
            logs.removeAll(adaptersToRemove);
        }
        try {
            if (logs.add(log)) {
                currentActiveLogAdapters.clear();
                currentActiveLogAdapters.add(log);
                populateTypePackage = false;
                importSuccess = false;
                logsetChanged = true;
                reImport(Reason.FILE_ADDED);
            }
        } catch (final RuntimeException e) {
            if (logs.contains(log)) {
                logs.remove(log);
            }
            throw e;
        }
    }

    public void addAndRemoveLogs(Collection<ILogAdapter> removeLogs,
            Collection<ILogAdapter> addLogs, boolean rename)
            throws ImportOperationCancelledException {
        for (Iterator<ILogAdapter> i = this.logs.iterator(); i.hasNext();) {
            ILogAdapter logAdapter = (ILogAdapter) i.next();
            if (!logAdapter.getFile().exists()) {
                i.remove();
            }
        }
        if (removeLogs != null) {
            this.logs.removeAll(removeLogs);
        }
        if (addLogs != null) {
            addLogs(addLogs);
        }
        logsetChanged = true;
        if (!rename) {
            populateTypePackage = false;
            importSuccess = false;
            currentActiveLogAdapters.clear();
            currentActiveLogAdapters.addAll(addLogs);
            reImport(Reason.FILE_ADDED);
        }
    }

    public void removeLogs() {
        for (Iterator<ILogAdapter> i = this.logs.iterator(); i.hasNext();) {
            ILogAdapter logAdapter = (ILogAdapter) i.next();
            if (!logAdapter.getFile().exists()) {
                i.remove();
            }
        }
    }

    public void addLogs(final Collection<ILogAdapter> logs) {
        synchronized (this.logs) {

            if (disabled) {

                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        ErrorDialog.openError(new Shell(),
                                Messages.LICENSE_ERROR, Messages.IMPORT_ERROR,
                                new Status(IStatus.ERROR,
                                        SeCorePlugin.PLUGIN_ID, IStatus.ERROR,
                                        Messages.LICENSE_EXCEPTION + ".",
                                        getException()));

                    }
                });

                for (ILogAdapter iLogAdapter : logs) {
                    removeLog(iLogAdapter);
                }
                return;
            }
            
            for (Iterator<ILogAdapter> i1 = logs.iterator(); i1.hasNext();) {
                ILogAdapter newLogAdapter = (ILogAdapter) i1.next();
                boolean alreadyExists = false;
                ILogAdapter existingAdapter = null;
                for (Iterator<ILogAdapter> i2 = this.logs.iterator(); i2.hasNext();) {

                    existingAdapter = (ILogAdapter) i2.next();

                    /*
                     * This case occurs when a logset is renamed. There can't
                     * exist two files with the same name in a logset(folder)
                     */
                    if (newLogAdapter.getFile().getName()
                            .equals(existingAdapter.getFile().getName())) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (!alreadyExists) {
                    this.logs.add(newLogAdapter);
                } else {
                    this.logs.remove(existingAdapter);
                    this.logs.add(newLogAdapter);
                }
                logsetChanged = true;
            }
        }
    }

    /*
     * Removes the specified log files from the logset. Removes the sequences
     * from artifacts that comes from the log files that are to be removed.
     */
    public void removeLogs(final Collection<ILogAdapter> logs)
            throws ImportOperationCancelledException {
        if (this.logs.removeAll(logs)) {
            Collection<Object> removableItems = new ArrayList<Object>();
            for (Iterator<ILogAdapter> logsIterator = logs.iterator(); logsIterator
                    .hasNext();) {
                String logSetFileName = logsIterator.next().getFile().getName();
                for (Iterator<IArtifact> iterator = artifacts.values()
                        .iterator(); iterator.hasNext();) {
                    Object object = (Object) iterator.next();
                    if (object instanceof ISequence) {
                        String seqName = getSequenceFileName((ISequence) object);
                        if (seqName.equals(logSetFileName)) {
                            removableItems.add(object);
                        }
                    }
                }
            }

            for (Iterator<Object> removableItem = removableItems.iterator(); removableItem
                    .hasNext();) {
                Object artifact = (Object) removableItem.next();
                artifacts.remove(artifact);
            }
            logsetChanged = true;
            reImport(Reason.FILE_REMOVED);
        }
    }

    /*
     * The sequence names are on the format: "MySequence [myLogfile]" The file
     * name is found within the brackets.
     * 
     * @returns the sequence file name which it is created from
     */
    private String getSequenceFileName(final ISequence sequence) {
        String seqName = sequence.getName();
        int beginIndex = seqName.indexOf('[') + 1;
        int endIndex = seqName.indexOf(']');
        if (beginIndex >= 0 && endIndex > beginIndex) {
            seqName = seqName.substring(beginIndex, endIndex);
            seqName = getLogFileName(seqName);
        }
        return seqName;
    }

    /*
     * @returns the log file name without the ".bin" extension that some log
     * files have.
     */
    private String getLogFileName(final String fileName) {

        if (fileName.contains(BIN_EXT)) {
            return fileName.substring(0, fileName.indexOf(BIN_EXT));
        }
        return fileName;
    }

    public void removeLog(final ILogAdapter log) {
        if (logs.remove(log)) {
            artifacts.clear();
            importSuccess = false;
            logsetChanged = true;
            try {
                reImport(Reason.FILE_REMOVED);
            } catch (ImportOperationCancelledException e) {}
        }
    }

    public void removeLog(final IFile file)
            throws ImportOperationCancelledException {
        populateTypePackage = false;
        final List<ILogAdapter> list = new ArrayList<ILogAdapter>();

        for (final ILogAdapter adapter : logs) {
            final IFile other = adapter.getFile();
            if (other.equals(file)) {
                currentActiveLogAdapters.clear();
                currentActiveLogAdapters.add(adapter);
                list.add(adapter);
            }
        }
        if (list.size() > 1) {
            removeLogs(list);
            throw new IllegalStateException(
                    "Multiple LogAdapters to a single file detected." + list);
        }
        removeLogs(list);
    }

    public void removeLogs(final List<IFile> files)
            throws ImportOperationCancelledException {
        populateTypePackage = false;
        final List<ILogAdapter> list = new ArrayList<ILogAdapter>();

        for (final ILogAdapter adapter : logs) {
            final IFile other = adapter.getFile();
            for (IFile file : files) {
                if (other.equals(file)) {
                    currentActiveLogAdapters.clear();
                    currentActiveLogAdapters.add(adapter);
                    list.add(adapter);
                }
            }
        }
        removeLogs(list);
    }

    public Set<ILogAdapter> getLogs() {
        return Collections.unmodifiableSet(logs);
    }

    public void addTimestampUpdateListener(
            final ITimestampUpdateListener listener) {
        timestampUpdateListeners.add(listener);
    }

    public void removeTimestampUpdateListener(
            final ITimestampUpdateListener listener) {
        timestampUpdateListeners.remove(listener);
    }

    public void addChangeListener(final IChangeListener listener) {
        changeListeners.add(listener);
    }

    public void removeChangeListener(final IChangeListener listener) {
        changeListeners.remove(listener);
    }

    public UUID getId() {
        return id;
    }

    private void reImport(final Reason reason)
            throws ImportOperationCancelledException {
        if (pMonitor == null) {
            pMonitor = new NullProgressMonitor();
        }
        this.doImport(pMonitor, reason);
    }

    public void notifyListeners(final Reason reason) {
        switch (reason) {
        case QUERY_ADDED:
        case REFRESH:
            break;
        case FILE_CHANGED:
        case FILE_REMOVED:
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    for (final ITimestampUpdateListener listener : timestampUpdateListeners) {
                        listener.timestampUpdated(0);
                    }
                }
            });
            updateChangeListeners();
            break;
        default:
            updateChangeListeners();
        }
    }

    private void updateChangeListeners() {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                synchronized (changeListeners) {
                    for (final IChangeListener listener : changeListeners) {
                        listener.update(logsetChanged);
                    }
                    IFWFacade.notifyChangeListener(logsetChanged);
                    logsetChanged = false;
                }
            }
        });
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Logset other = (Logset) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Tests if the log is in this logset
     * 
     * @param log
     *            the log to test
     * @return true if the file is within the logset
     */
    public boolean contains(final IFile log) {
        for (final ILogAdapter adapter : logs) {
            IFile file = adapter.getFile();
            if (file != null) {
                if (file.equals(log)) {
                    return true;
                }
            } else {
                // TODO ugly fix
                logs.remove(adapter);
            }
        }
        return false;
    }

    public void dispose() {
        changeListeners.clear();
        timestampUpdateListeners.clear();
    }

    public synchronized void refresh() {
        long syncKey = getLock();
        if (syncKey != -1) {
            try {
                reImport(Reason.REFRESH);
            } catch (ImportOperationCancelledException e) {} 
			finally {
                releaseLock(syncKey);
            }
        }
    }

    void doImport(final IProgressMonitor monitor, final Set<ILogAdapter> logs,
            final Reason reason) {
        final IProgressMonitor mon = monitor == null ? new NullProgressMonitor()
                : monitor;
        boolean atEnd = true;
        Iterator<ILogEvent> iterator = null;
        try {
            queries.setProgressMonitor(mon);
            queries.visitBegin(reason);
            if (!disabled) {
                iterator = new MultiIterator(logs);
            }
        } catch (ImportException e) {} finally {
            // If the iterator is null, it means that there where exceptions and
            // the logfiles that created the exceptions are removed from "logs".
            // Create a new MultiIterator
            if (iterator == null) {
                iterator = new MultiIterator(logs);
            }
            if (iterator != null) {
                while (iterator.hasNext()) {
                    if (mon.isCanceled()) {
                        // TODO Perform cleanup.
                        rollBackLogs(reason);
                        break;
                    }
                    final ILogEvent item = iterator.next();
                    if (!queries.visit(item)) {
                        atEnd = false;
                        break;
                    }
                }

                for (IArtifact artifact : getArtifacts()) {
                    if (!queries.visit(artifact)) {
                        break;
                    }
                }
            }

            importSuccess = true;
            // FIXME Currently this is run in the UI Thread, therefore we should
            // notifyListeners here
            // When this is running in a workspace job, this should not be here
            notifyListeners(reason);
            try {
                if (logs.isEmpty()) {
                    queries.inValidate();
                } else {
                    queries.visitEnd(atEnd);
                }
            } catch (final Exception ex) {
                SeCorePlugin.logError(ex);
            }

            for (final ILogAdapter adapter : logs) {
                try {
                    adapter.close();
                } catch (final Exception ex) {
                    SeCorePlugin.logError(ex);
                }
            }
        }
    }

    private void rollBackLogs(Reason reason) {
        switch (reason) {
        case FILE_ADDED:
            for (Iterator<ILogAdapter> i = currentActiveLogAdapters.iterator(); i.hasNext();) {
                    
                ILogAdapter logAdapter = (ILogAdapter) i.next();
                logs.remove(logAdapter);
                logAdapter.close();
            }
            currentActiveLogAdapters.clear();
            break;
        case FILE_REMOVED:
            logs.addAll(currentActiveLogAdapters);
            currentActiveLogAdapters.clear();
            break;
        }
    }

    void doImport(final IProgressMonitor monitor, final Reason reason)
            throws ImportOperationCancelledException {
        if (disabled) {
            return;
        }
        switch (importerVersionType) {
        case IFWFacade.IIMPORTER_VERSION:
            this.doImport(monitor, this.logs, reason);
            break;
        case IFWFacade.IEXTENDEDIMPORTER_VERSION:
            // Fall through
        default:
            this.doIndexedImport(monitor, this.logs, reason);
            populateTypePackage();
        
        }
             
    }

    void doIndexedImport(final IProgressMonitor monitor,
            final Set<ILogAdapter> logs, final Reason reason)
            throws ImportOperationCancelledException {

        if (((reason == Reason.REFRESH) || (reason == Reason.QUERY_ADDED))
                && (binReader == null)) {
            reImport(Reason.FILE_ADDED);
            return;
        }

        final IProgressMonitor mon = (monitor != null) ? monitor
                : new NullProgressMonitor();

        boolean atEnd = true;
        Iterator<ILogEvent> iterator = null;

        queries.setProgressMonitor(mon);
        queries.visitBegin(reason);
        // Check for empty or null nature of log adapters and perform clean up
        // of indexed log files.
        if (logs == null || logs.isEmpty()) {
            performIndexedLogFileCleanup(reason);
            return;
        }

        indexedLogFilePath = new StringBuffer(logs.toArray(new ILogAdapter[logs
                .size()])[0].getFile().getParent().getLocation().toOSString())
                .append(File.separator).append(INDEXED_LOG_FILE_NAME)
                .toString();

        if ((reason != Reason.REFRESH) && (reason != Reason.QUERY_ADDED)) {
            try {
                createIndexedLogFile(reason);
            } catch (ImportOperationCancelledException ioce) {
                if (ioce.isImportFailed()) {
                    for (Iterator<ILogAdapter> i = currentActiveLogAdapters
                            .iterator(); i.hasNext();) {
                        ILogAdapter logAdapter = (ILogAdapter) i.next();
                        logs.remove(logAdapter);
                        logAdapter.close();
                    }
                    for (Iterator<ILogAdapter> i = ioce.getLogAdapters()
                            .iterator(); i.hasNext();) {
                        ILogAdapter logAdapter = (ILogAdapter) i.next();
                        logs.remove(logAdapter);
                        logAdapter.close();
                    }
                }
                updateProgressMonitor(reason,
                        monitor.isCanceled() || ioce.isImportFailed());
                throw ioce;
            } catch (IOException e) {
                return;
            }

            // Read the indexed log file.
            performIndexedLogFileRead(reason);
        }

        // Update the task query
        taskQuery = TaskQuery.valueOf(this);
        taskQuery.visitBegin(reason);

        // Iterate over tasks
        for (LogFileInfo logFile : binReader.getLogFiles().values()) {
            Set<ITask> taskSuperset = new HashSet<ITask>();
            Set<ITask> taskExecs = new HashSet<ITask>();
            for (GenericTask task : logFile.getTasks().values()) {
                taskSuperset.add(task);
            }
            for (GenericTask task : logFile.getTaskExecs().values()) {
                taskExecs.add(task);
            }
            taskQuery.setTaskSuperset(taskSuperset);
            taskQuery.setTaskExec(taskExecs);
        }
        taskQuery.setFirstTs(binReader.getFirstTaskDuration());
        taskQuery.setLastTs(binReader.getLastTaskDuration());
        // Update the statistics query
        statQuery = StatisticQuery.valueOf(this);
        statQuery.setCountEvent(binReader.getNumEvents());
        statQuery.setMaxTs(binReader.getLastEventTimestamp());
        statQuery.setMinTs(binReader.getFirstEventTimestamp());

        setCurrentTime(currentTime);

        // Iterate over events
        iterator = binReader.getEvents(currentTime, noOfEventsToSkip)
                .iterator();
        while (iterator.hasNext()) {
            if (updateProgressMonitor(reason, mon.isCanceled())) {
                break;
            }
            ILogEvent logEvent = iterator.next();
            if (!queries.visit(logEvent)) {
                atEnd = false;
                break;
            }
        }
        iterator.remove();

        // Iterate over artifacts
        // Iterate over artifacts
        for (LogFileInfo logFile : binReader.getLogFiles().values()) {
            for (IArtifact artifact : logFile.getArtifacts().values()) {
                if (!queries.visit(artifact)) {
                    break;
                }
            }
        }

        importSuccess = true;
        performIndexedLogFileCleanup(reason); // FIXME
        notifyListeners(reason);
        try {
            queries.visitEnd(atEnd);
        } catch (final Exception ex) {
            SeCorePlugin.logError(ex);
        }

        for (final ILogAdapter adapter : logs) {
            try {
                adapter.close();
            } catch (final Exception e) {
                SeCorePlugin.logError(e);
            }
        }
    }

    boolean updateProgressMonitor(final Reason reason, final boolean rollback) {
        if (rollback) {
            switch (reason) {
            case FILE_CHANGED:
            case REFRESH:
            case QUERY_ADDED:
                return true;
            default:
                // Perform cleanup and return immediately
                performRollBackAndRestoreState(reason);
                notifyListeners(Reason.FILE_REMOVED);
                return true;
            }
        }
        return false;
    }

    /**
     * Create the indexed log file under the given conditions.
     * 
     * @param reason
     * @return
     * @throws IOException
     */
    private File createIndexedLogFile(final Reason reason) throws IOException {
        File indexedLogFile = new File(indexedLogFilePath);
        BBBinWriter binWriter = null;

        if (!indexedLogFile.createNewFile()) {
            // The indexed log file already exists.
            // Check whether it is up to date or needs to be recreated.
            boolean canWrite = false;
            if (importSuccess) {
                // Consistency check of existing and imported indexed log file
                if (binReader == null) {
                    binReader = new BBBinReader(indexedLogFile);
                    binReader.read();
                }
                canWrite = performIndexedLogFileConsistencyChecks();
            } else {
                canWrite = true;
            }

            if (canWrite) {
                if (binReader != null) {
                    binReader.close();
                    binReader = null;
                }

                // Create a file backup and restore this state when a
                // cancel has been requested.

                File tempFile = new File(new StringBuffer(indexedLogFilePath)
                        .append(".tmp").toString());

                IFileSystem fileSystem = EFS.getLocalFileSystem();
                IFileStore srcFStore = fileSystem.getStore(new Path(
                        indexedLogFilePath));
                IFileStore destStore = fileSystem.getStore(new Path(tempFile
                        .getPath()));

                try {

                    srcFStore.copy(destStore, EFS.OVERWRITE, null);
                } catch (CoreException cx) {
                    cx.printStackTrace();
                }

                // Delete the existing indexed log file and create a new one
                if (indexedLogFile.delete()) {
                    indexedLogFile.createNewFile();
                }

                // Write the new indexed log file
                try {
                    binWriter = new BBBinWriter(indexedLogFile);
                    List<ExtendedLogAdapter> extendedLogAdapters = new ArrayList<ExtendedLogAdapter>();
                    for (ILogAdapter adapter : logs) {
                        if ((adapter instanceof ExtendedLogAdapter)
                                && adapter.getFile().exists()) {
                            extendedLogAdapters
                                    .add((ExtendedLogAdapter) adapter);
                        }
                    }
                    binWriter.write(pMonitor, extendedLogAdapters);
                } finally {
                    binWriter.close();
                    binWriter = null;
                }
            }
        } else {
            // The indexed log file did not exist but was successfully created.
            // Now write the newly created indexed log file.
            try {
                binWriter = new BBBinWriter(indexedLogFile);
                List<ExtendedLogAdapter> extendedLogAdapters = new ArrayList<ExtendedLogAdapter>();
                for (ILogAdapter adapter : logs) {
                    if (adapter instanceof ExtendedLogAdapter) {
                        extendedLogAdapters.add((ExtendedLogAdapter) adapter);
                    }
                }
                binWriter.write(pMonitor, extendedLogAdapters);
            } finally {
                binWriter.close();
                binWriter = null;
            }
        }

        return indexedLogFile;
    }

    private void populateTypePackage() {
        if (populateTypePackage) {
            return;
        }
        populateTypePackage = true;
        IQuery evtQuery = new IQuery() {
            int events;

            public Logset getLogset() {
                return null;
            }

            public void initialize(IDataSource data) {}

            public void setEnd(boolean end) {}

            public void setLogset(Logset logset) {}

            public void setStart(boolean start) {}

            public boolean visit(IObject item) {
                if (item instanceof ILogEvent) {
                    events++;
                    if (events > 5000) {
                        return false;
                    }
                }
                return true;
            }

            public boolean visitBegin(Reason reason) {
                return true;
            }

            public void visitEnd(boolean atEnd) {}
        };
        addQuery(evtQuery, syncKey);
        removeQuery(evtQuery);
    }

    private static class Item implements Comparable<Item> {
        private ILogEvent event;

        private final Iterator<ILogEvent> iter;

        public Item(final ILogEvent event, final Iterator<ILogEvent> iter) {
            super();
            this.event = event;
            this.iter = iter;
        }

        @SuppressWarnings("unchecked")
        public int compareTo(final Item arg0) {
            return event.compareTo(arg0.event);
        }
    }

    static class MultiIterator implements Iterator<ILogEvent> {

        private static final String QUOTE = "\"";

        private static final String IMPORT_FAILED_FOR_FILE = "Import failed for file \"";

        // TODO: Consider implementing as a lazy way, there is a risk that items
        // gets very big
        private final List<Item> items;

        public MultiIterator(final Set<ILogAdapter> logs) {
            items = new ArrayList<Item>();
            ImportExceptionList importExceptions = new ImportExceptionList();
            Collection<ILogAdapter> adaptersThatCantBeImported = new ArrayList<ILogAdapter>();
            for (final ILogAdapter adapter : logs) {
                try {
                    final Iterator<ILogEvent> iter = adapter.getLogEvents()
                            .iterator();

                    if (iter.hasNext()) {
                        items.add(new Item(iter.next(), iter));
                    }
                } catch (final ImportException e) {
                    if (e.getMessage().equals("Cancelled")) {
                        adaptersThatCantBeImported.add(adapter);
                    } else {
                        adaptersThatCantBeImported.add(adapter);
                        importExceptions.add(new ImportException(
                                IMPORT_FAILED_FOR_FILE
                                        + adapter.getFile().getFullPath()
                                        + QUOTE, e));
                    }
                } catch (final RuntimeException e) {
                    adaptersThatCantBeImported.add(adapter);
                    importExceptions.add(new ImportException(
                            IMPORT_FAILED_FOR_FILE
                                    + adapter.getFile().getFullPath() + QUOTE,
                            e));
                }
            }
            for (ILogAdapter logAdapter : adaptersThatCantBeImported) {
                logAdapter.close();
                logs.remove(logAdapter);
            }
            Collections.sort(items);
            if (importExceptions.size() > 0) {
                throw importExceptions;
            }
        }

        public boolean hasNext() {
            return !items.isEmpty();
        }

        public ILogEvent next() {

            final Item a = items.get(0);

            final ILogEvent nxt = a.event;
            items.remove(0);

            if (a.iter.hasNext()) {
                final int size = items.size();
                a.event = a.iter.next();
                int index = -1;
                for (int i = size - 1; i >= 0; i--) {
                    final Item o = items.get(i);
                    if (a.event.getTs() > o.event.getTs()) {
                        index = i;
                        break;
                    }
                }
                if (index == size) {
                    items.add(a);
                } else if (index == -1) {
                    items.add(0, a);
                } else {
                    items.add(index + 1, a);
                }
            }
            return nxt;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    IArtifact resolveArtifact(final IArtifact artifact) {
        IArtifact stored = artifacts.get(artifact);
        if (stored == null) {
            artifacts.put(artifact, artifact);
            stored = artifact;
        }
        return stored;
    }

    public IArtifact getArtifact(final IArtifactID id) {
        if (artifacts.size() == 0) {
            getArtifacts();
        }
        return artifacts.get(id);
    }

    public List<IArtifact> getArtifacts() {
        List<IArtifact> values = null;

        switch (importerVersionType) {
        case IFWFacade.IIMPORTER_VERSION:
            final Collection<IArtifact> vals = artifacts.values();
            values = new ArrayList<IArtifact>(vals);
            break;
        case IFWFacade.IEXTENDEDIMPORTER_VERSION:
            // Fall through
        default:
            if (!checkBBBinReader()) {
                return Collections.emptyList();
            }
            values = new ArrayList<IArtifact>();
            for (LogFileInfo logFile : binReader.getLogFiles().values()) {
                for (IArtifact artifact : logFile.getArtifacts().values()) {
                    values.add(artifact);
                    resolveArtifact(artifact);
                }
            }
        }
        return values;
    }

    @SuppressWarnings("unchecked")
    public <T extends IArtifact> List<T> getArtifacts(final Class<T> clazz) {
        final List<T> values = new ArrayList<T>();

        switch (importerVersionType) {
        case IFWFacade.IIMPORTER_VERSION:
            for (final IArtifact a : this.getArtifacts()) {
                if (clazz.isInstance(a)) {
                    values.add((T) a);
                }
            }
            break;
        case IFWFacade.IEXTENDEDIMPORTER_VERSION:
            // Fall through
        default:
            if (!checkBBBinReader()) {
                if (isLocked()) {
                    return values;
                }
                try {
                    reImport(Reason.FILE_ADDED);
                } catch (ImportOperationCancelledException e) {}
                if (binReader == null) {
                    return Collections.EMPTY_LIST;
                }
            }
            for (LogFileInfo logFile : binReader.getLogFiles().values()) {
                for (IArtifact artifact : logFile.getArtifacts().values()) {
                    if (clazz.isInstance(artifact)) {
                        values.add((T) artifact);
                    }
                }
            }
        }
        return values;
    }

    public static void setDisabled(final boolean disabled) {
        Logset.disabled = disabled;
    }

    public ILogEvent getEventAtIndex(final int i) {
        if (!checkBBBinReader()) {
            return null;
        }
        try {
            return binReader.getEventAtIndex(i & 0xFFFFFFFFL);
        } catch (IOException e) {
            throw new ImportException(e);
        }
    }

    public int getIndexAtTimestamp(final long time) {
        if (!checkBBBinReader()) {
            return 0;
        }
        try {
            return (int) binReader.getIndexAtTimestamp(time);
        } catch (IOException e) {
            throw new ImportException(e);
        }
    }

    public int getNumberOfEvents() {
        if (!checkBBBinReader()) {
            return 0;
        }
        return (int) binReader.getNumEvents();
    }

    public void setCurrentTime(final long time) {
        this.currentTime = time;
        if (!checkBBBinReader()) {
            return;
        }
        try {
            long index = binReader.getIndexAtTimestamp(time);
            boolean start = (index <= 5000);
            boolean end = (index >= binReader.getNumEvents());
            queries.setStart(start);
            queries.setEnd(end);
        } catch (IOException e) {
            throw new ImportException(e);
        }
    }

    public long getCurrentTime() {
        return currentTime;
    }

    /**
     * Performs the necessary clean up operations like freeing the intermediate
     * indexed log file handler and its subsequent deletion.
     * 
     * @param reason
     */
    private void performIndexedLogFileCleanup(final Reason reason) {
        switch (reason) {
        	case FILE_ADDED:
        	case FILE_REMOVED:
            	break;
        	default:
            	return;
        }

        if (indexedLogFilePath != null) {
            File indexedLogFile = new File(new StringBuffer(indexedLogFilePath)
                    .append(".tmp").toString());
            if (indexedLogFile.exists()) {
                indexedLogFile.delete();
            }
            if (!importSuccess) {
                if (binReader != null) {
                    binReader.close();
                    binReader = null;
                }
                indexedLogFile = new File(indexedLogFilePath);
                if (indexedLogFile.exists()) {
                    indexedLogFile.delete();
                }
                notifyListeners(reason);
                try {
                    queries.inValidate();
                } catch (final Exception ex) {
                    SeCorePlugin.logError(ex);
                }
            }
        }
    }

    /**
     * This method checks for log files consistency in terms of timestamps,
     * names, and sizes by comparing the actual log files with the log file
     * metadata in the intermediate indexed log file.
     * 
     * @return true if there is an inconsistency, false otherwise
     */
    private boolean performIndexedLogFileConsistencyChecks() {
        boolean canWrite = false;

        if (!importSuccess) {
            return true;
        }

        if (binReader == null) {
            return false;
        }

        Map<Integer, LogFileInfo> logFilesInfo = binReader.getLogFiles();

        if (logs.size() != logFilesInfo.size()) {
            return true;
        }

        for (ILogAdapter adapter : logs) {
            boolean matchFound = false;
            IFile adapterLogFile = adapter.getFile();
            File logFile = new File(adapterLogFile.getLocation().toOSString());

            for (int i = 0; i < logFilesInfo.size(); i++) {
                GenericLogFile gLogFile = logFilesInfo.get(i).getLogFile();

                // File name comparison
                if (adapterLogFile.getName().equalsIgnoreCase(
                        gLogFile.getFileName())) {
                    // Check if there's a timestamp difference and also
                    // their respective sizes.
                    if ((adapterLogFile.getModificationStamp() < gLogFile
                            .getImportedAt() && (logFile.length() == gLogFile
                            .getSize()))) {
                        matchFound = true;
                    }
                    break;
                }
            }

            if (!matchFound) {
                canWrite = true;
                break;
            }
        }
        return canWrite;
    }

    public boolean isImportSuccess() {
        return importSuccess;
    }

    public void setImportSuccess(final boolean importSuccess) {
        this.importSuccess = importSuccess;
        // if (importSuccess && queries.isQuerySetEmpty()) {
        // reImport(Reason.REFRESH);
        // }
    }

    public void setImporterVersionType(int versionType) {
        importerVersionType = versionType;

        if ((importerVersionType == IFWFacade.IIMPORTER_VERSION)
                && !oldImporterVersionSet) {
            oldImporterVersionSet = true;
            queries.add(statQuery);
            queries.add(taskQuery);
        }

        if ((importerVersionType == IFWFacade.IEXTENDEDIMPORTER_VERSION)
                && oldImporterVersionSet) {
            oldImporterVersionSet = false;
            queries.remove(statQuery);
            queries.remove(taskQuery);
        }
    }

    public int getImporterVersionType() {
        return importerVersionType;
    }

    public void invalidateLogset() {
        logs.clear();
        artifacts.clear();
        if (indexedLogFilePath != null) {
            if (binReader != null) {
                binReader.close();
                binReader = null;
            }
            File indexedLogFile = new File(indexedLogFilePath);
            if (indexedLogFile.exists()) {
                indexedLogFile.delete();
            }
            indexedLogFile = new File(new StringBuffer(indexedLogFilePath)
                    .append(".tmp").toString());
            if (indexedLogFile.exists()) {
                indexedLogFile.delete();
            }
        }
    }

    private void performIndexedLogFileRead(final Reason reason) {
        if (binReader == null && this.logs.size() != 0) {
            if (!checkFileExists(indexedLogFilePath)) {
                return;
            }
            if ((indexedLogFilePath == null)) {
                indexedLogFilePath = new StringBuffer(
                        this.logs.toArray(new ILogAdapter[this.logs.size()])[0]
                                .getFile().getParent().getLocation()
                                .toOSString()).append(File.separator)
                        .append(INDEXED_LOG_FILE_NAME).toString();

            }

            // Now perform a read of the indexed log file.
            try {
                binReader = new BBBinReader(new File(indexedLogFilePath));
                binReader.read();
            } catch (IOException e) {
                throw new ImportException("Error reading indexed log file", e);
            }

            // Update the task query
            taskQuery = TaskQuery.valueOf(this);
            taskQuery.visitBegin(reason);
            taskQuery.setFirstTs(binReader.getFirstTaskDuration());
            taskQuery.setLastTs(binReader.getLastTaskDuration());

            // Iterate over tasks
            Map<Integer, LogFileInfo> logFiles = binReader.getLogFiles();
            for (LogFileInfo logFile : logFiles.values()) {
                Map<Integer, GenericTask> logFileTasks = logFile.getTasks();
                Set<ITask> taskSuperset = new HashSet<ITask>();
                for (GenericTask task : logFileTasks.values()) {
                    taskSuperset.add(task);
                }

                Map<Integer, GenericTask> logFileTaskExecs = logFile
                        .getTaskExecs();
                Set<ITask> taskExecs = new HashSet<ITask>();
                for (GenericTask task : logFileTaskExecs.values()) {
                    taskExecs.add(task);
                }
                taskQuery.setTaskSuperset(taskSuperset);
                taskQuery.setTaskExec(taskExecs);
            }

            // Update the statistics query
            statQuery = StatisticQuery.valueOf(this);
            statQuery.setCountEvent(binReader.getNumEvents());
            statQuery.setMaxTs(binReader.getLastEventTimestamp());
            statQuery.setMinTs(binReader.getFirstEventTimestamp());
        }

    }

    private boolean checkFileExists(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    public void setNoOfEventsToSkip(int noOfEventsToSkip) {
        this.noOfEventsToSkip = noOfEventsToSkip;
    }

    public boolean canSkipEvents() {
        switch (importerVersionType) {
        case IFWFacade.IIMPORTER_VERSION:
            return false;
        case IFWFacade.IEXTENDEDIMPORTER_VERSION:
            // Fall through
        default:
            return true;
        }
    }

    public boolean isLocked() {
        return isLogsetLocked;
    }

    private long syncKey;

    public synchronized long getLock() {
        if (!isLogsetLocked) {
            isLogsetLocked = true;
            return syncKey = System.currentTimeMillis();
        }
        return -1;
    }

    public synchronized void releaseLock(long lock) {
        if (this.syncKey == lock) {
            isLogsetLocked = false;
            syncKey = -1;
        }
    }

    public void setProgressMonitor(IProgressMonitor monitor) {
        pMonitor = monitor == null ? new NullProgressMonitor() : monitor;
    }

    public IProgressMonitor getProgressMonitor() {
        return pMonitor;
    }

    /**
     * It performs a rollback and restores the recent stable consistent state of
     * the logset
     * 
     * @param reason
     */
    private void performRollBackAndRestoreState(final Reason reason) {
        rollBackLogs(reason);

        if (indexedLogFilePath != null) {
            if (binReader != null) {
                binReader.close();
                binReader = null;
            }

            try {
                // Now restore the backup file
                File tempFile = new File(new StringBuffer(indexedLogFilePath)
                        .append(".tmp").toString());

                File indexFile = new File(indexedLogFilePath);

                if (indexFile.exists()) {
                    indexFile.delete();
                }

                if (tempFile.exists() && !indexFile.exists()) {
                    tempFile.renameTo(new File(indexedLogFilePath));
                }

                // Now perform a read.
                performIndexedLogFileRead(Reason.FILE_CHANGED);
            } catch (Exception e) {}
        }
    }

    public void openIMBinaryStream() {
        if ((binReader == null) && !disabled
                && (importerVersionType == IFWFacade.IEXTENDEDIMPORTER_VERSION)) {
            performIndexedLogFileRead(Reason.FILE_CHANGED);
        }
    }

    public void closeIMBinaryStream() {
        if (binReader != null) {
            binReader.close();
            binReader = null;
        }
    }

    public long getNumberOfTaskSwitchEvents() {
        if (getLogs().size() == 0) {
            return 0;
        }
        if (binReader != null) {
            return binReader.getNumTaskSwitchEvents();
        }
        return -1;
    }

    public long getLastTaskDurationTime() {
        switch (importerVersionType) {
        case IFWFacade.IIMPORTER_VERSION:
            return -1;
        case IFWFacade.IEXTENDEDIMPORTER_VERSION:
            // Fall through
        default:
            return TaskQuery.valueOf(this).getLastTs();
        }
    }

    private boolean checkBBBinReader() {
        if (binReader == null) {
            openIMBinaryStream();
            if (binReader == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDisabled() {
        return disabled;
    }

    public static Exception getException() {
        if(SeCorePlugin.getException() == null) {
            return new LicenseException("Unknown exception");
        }
        return SeCorePlugin.getException();
    }

    
}

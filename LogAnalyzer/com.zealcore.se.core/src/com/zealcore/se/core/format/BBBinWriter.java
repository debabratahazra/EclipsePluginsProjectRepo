package com.zealcore.se.core.format;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.zealcore.se.core.IExtendedImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ImportExceptionList;
import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.ifw.ExtendedLogAdapter;
import com.zealcore.se.core.ifw.ILogAdapter;
import com.zealcore.se.core.model.LogFile;

public class BBBinWriter {
    private static final int MAGIC_NUMBER = 0xBADBABE;

    private static final int FILE_FORMAT_VERSION = 10;

    private static final long NUM_EVENTS_IN_BLOCK = 1000;

    private static final long LOG_FILES_FPTR_OFFSET = 8;

    private static final long TYPES_FPTR_OFFSET = 16;

    private static final long EVENTS_FPTR_OFFSET = 24;

    private static final long EVENT_INDEX_FPTR_OFFSET = 32;

    private static final long ARTIFACTS_FPTR_OFFSET = 40;

    private static final long TASK_EXECS_FPTR_OFFSET = 48;

    private static final long STATISTICS_FPTR_OFFSET = 56;

    private final RandomAccessFile file;

    private final EventIndex eventIndex;

    private boolean open;

    private long logFilesOffset;

    private long typesOffset;

    private long eventsOffset;

    private long eventIndexOffset;

    private long artifactsOffset;

    private long taskExecsOffset;

    private long statisticsOffset;

    private long numLogFiles;

    private long numTypes;

    private long numEvents;

    private long numArtifacts;

    private long numTaskExecs;

    private long lastEventTimestamp;

    private final Map<Integer, Map<Integer, Long>> taskExecs;

    private final Map<Integer, TaskSwitchEventInfo> lastTaskSwitchEvent;

    private long firstTaskDuration;

    private long lastTaskDuration;

    private long numTaskSwitches;

    public BBBinWriter(final File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        this.file = new RandomAccessFile(file, "rw");
        eventIndex = new EventIndex();
        open = true;
        taskExecs = new HashMap<Integer, Map<Integer, Long>>();
        lastTaskSwitchEvent = new HashMap<Integer, TaskSwitchEventInfo>();
        firstTaskDuration = -1;
        lastTaskDuration = 0;
    }

    
    public void write(IProgressMonitor monitor, final List<ExtendedLogAdapter> logAdapters)
            throws IOException {
        if (logAdapters == null) {
            throw new IllegalArgumentException();
        }

        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }

        if (monitor.isCanceled()) {
            throw new ImportOperationCancelledException();
        }

        checkState();

        // Write the file header.
        writeHeader();

        // Write info about all the log files.
        startWritingLogFiles();
        for (int i = 0; i < logAdapters.size(); i++) {
            LogFile logFile = logAdapters.get(i).getContext().getLog();
            writeLogFile(i, logFile.getFileName(), logFile.getSize(),
                    logFile.getImportedAt(), logFile.getImporterId());
        }
        endWritingLogFiles();

        // Write all the types.
        startWritingTypes();
        ImportExceptionList importExceptions = new ImportExceptionList();
        Collection<ILogAdapter> adaptersThatCantBeImported = new ArrayList<ILogAdapter>();
        for (int i = 0; i < logAdapters.size(); i++) {
            try {
                IExtendedImporter importer = logAdapters.get(i).getImporter(
                        true);
                for (Iterator<TypeDescription> j = importer
                        .getTypeDescriptions().iterator(); j.hasNext();) {
                    TypeDescription type = j.next();
                    type.setLogFileId(i);
                    writeType(type);
                }
            } catch (final Exception e) {
                adaptersThatCantBeImported.add(logAdapters.get(i));
                importExceptions.add(new ImportException(
                        "Import failed for file \""
                                + logAdapters.get(i).getFile().getFullPath()
                                + "\"", e));
            }
        }
        if (importExceptions.size() > 0) {
            throw new ImportOperationCancelledException(importExceptions,
                    adaptersThatCantBeImported);
        }
        endWritingTypes();

        // Output stream used for buffered writing of events and artifacts.
        ByteArrayOutputStream bout = new ByteArrayOutputStream(16384);
        DataOutputStream out = new DataOutputStream(bout);

        // Write all the events in chronological order.
        startWritingEvents();
        List<Iterator<GenericEventInfo>> eventIterators = new ArrayList<Iterator<GenericEventInfo>>();
        List<ILogAdapter> adapters = new ArrayList<ILogAdapter>();
        List<GenericEventInfo> events = new ArrayList<GenericEventInfo>();
        ExtendedLogAdapter adapter = null;
        for (Iterator<ExtendedLogAdapter> i = logAdapters.iterator(); i
                .hasNext();) {
            try {
                adapter = i.next();
                IExtendedImporter importer = adapter.getImporter(true);
                Iterator<GenericEventInfo> eventIterator = importer.getEvents()
                        .iterator();
                eventIterators.add(eventIterator);
                adapters.add(adapter);
                events.add(eventIterator.hasNext() ? eventIterator.next()
                        : null);
            } catch (final Exception e) {
                adaptersThatCantBeImported.add(adapter);
                importExceptions.add(new ImportException(
                        "Import failed for file \""
                                + adapter.getFile().getFullPath() + "\"", e));
            }
        }
        if (importExceptions.size() > 0) {
            throw new ImportOperationCancelledException(importExceptions,
                    adaptersThatCantBeImported);
        }
        GenericEventInfo event;
        while ((event = getOldestEvent(eventIterators, events, adapters)) != null) {
            if (monitor.isCanceled()) {
                closeImporters(logAdapters);
                throw new ImportOperationCancelledException();
            }
            writeEvent(event, out, bout);
        }
        if (bout.size() > 0) {
            file.write(bout.toByteArray());
            bout.reset();
        }
        endWritingEvents();

        // Write the event index.
        writeEventIndex();

        // Write all the artifacts.
        startWritingArtifacts();
        for (int i = 0; i < logAdapters.size(); i++) {
            IExtendedImporter importer = logAdapters.get(i).getImporter(true);
            for (Iterator<GenericArtifactInfo> j = importer.getArtifacts()
                    .iterator(); j.hasNext();) {
                if (monitor.isCanceled()) {
                    closeImporters(logAdapters);
                    throw new ImportOperationCancelledException();
                }
                GenericArtifactInfo artifact = j.next();
                artifact.setLogFileId(i);
                writeArtifact(artifact, out, bout);
            }
        }
        if (bout.size() > 0) {
            file.write(bout.toByteArray());
            out.close();
        }
        endWritingArtifacts();

        // Write info about all the task executions.
        startWritingTaskExecs();
        double period = lastTaskDuration - firstTaskDuration;
        for (Integer logFileId : taskExecs.keySet()) {
            Map<Integer, Long> taskExecsMap = taskExecs.get(logFileId);
            for (Integer taskId : taskExecsMap.keySet()) {
                Long exec = taskExecsMap.get(taskId);
                double util = exec / period;
                writeTaskExec(logFileId, taskId, round(util));
            }
        }
        endWritingTaskExecs();

        // Write statistics.
        writeStatistics();
    }

    private void checkState() throws IllegalStateException {
        if (!open) {
            throw new IllegalStateException("BBBinWriter is closed");
        }
    }

    private static GenericEventInfo getOldestEvent(
            final List<Iterator<GenericEventInfo>> eventIterators,
            final List<GenericEventInfo> events,
            final List<ILogAdapter> adapters) throws ImportOperationCancelledException {
        int oldestIndex = 0;
        GenericEventInfo oldestEvent = null;

        // Get the oldest event.
        for (int i = 0; i < events.size(); i++) {
            GenericEventInfo event = events.get(i);
            if (event != null) {
                if ((oldestEvent == null)
                        || (event.getTimestamp() < oldestEvent.getTimestamp())) {
                    oldestIndex = i;
                    oldestEvent = event;
                }
            }
        }

        ImportExceptionList importExceptions = new ImportExceptionList();
        Collection<ILogAdapter> adaptersThatCantBeImported = new ArrayList<ILogAdapter>();
    	try {
	        // Replace the oldest event with the next event from that event
	        // iterator.
	        if (oldestEvent != null) {
	            Iterator<GenericEventInfo> eventIterator = eventIterators
	                    .get(oldestIndex);
	            GenericEventInfo event = eventIterator.hasNext() ? eventIterator
	                    .next() : null;
	            events.set(oldestIndex, event);
	            oldestEvent.setLogFileId(oldestIndex);
	        }
    	} catch (final Exception e) {
            adaptersThatCantBeImported.add(adapters.get(oldestIndex));
            importExceptions.add(new ImportException(
            		"Import failed for file \""
                            + adapters.get(oldestIndex).getFile().getFullPath()
                            + "\"", e));
    	}
	    if (importExceptions.size() > 0) {
	    	throw new ImportOperationCancelledException(importExceptions, adaptersThatCantBeImported);
	    }
        // Return the oldest event or null if there are no events left.
        return oldestEvent;
    }

   
    private static void closeImporters(final List<ExtendedLogAdapter> logAdapters) {
        for (Iterator<ExtendedLogAdapter> i = logAdapters.iterator(); i.hasNext();) {
                
            IExtendedImporter importer = i.next().getImporter(true);
            if (importer instanceof Closeable) {
                try {
                    ((Closeable) importer).close();
                } catch (IOException ignore) {}
            }
        }
    }

    // Header

    void writeHeader() throws IOException {
        file.writeInt(MAGIC_NUMBER);
        file.writeInt(FILE_FORMAT_VERSION);
        // Placeholders for LOG_FILES_FPTR_OFFSET, TYPES_FPTR_OFFSET,
        // EVENTS_FPTR_OFFSET, EVENT_INDEX_FPTR_OFFSET, ARTIFACTS_FPTR_OFFSET,
        // TASK_EXECS_FPTR_OFFSET, and STATISTICS_FPTR_OFFSET.
        file.writeLong(0);
        file.writeLong(0);
        file.writeLong(0);
        file.writeLong(0);
        file.writeLong(0);
        file.writeLong(0);
        file.writeLong(0);
    }

    // Log files

    void startWritingLogFiles() throws IOException {
        logFilesOffset = file.getFilePointer();
        file.seek(LOG_FILES_FPTR_OFFSET);
        file.writeLong(logFilesOffset);
        file.seek(logFilesOffset);
        // Placeholder for number of log files.
        file.writeLong(0);
    }

    void writeLogFile(final int logFileId, final String logFileName,
            final long logFileSize, final long importedAtTimestamp,
            final String importerName) throws IOException {
        file.writeInt(logFileId);
        writeString(file, logFileName);
        file.writeLong(logFileSize);
        file.writeLong(importedAtTimestamp);
        writeString(file, importerName);
        numLogFiles++;
    }

    void endWritingLogFiles() throws IOException {
        long currentOffset = file.getFilePointer();
        file.seek(logFilesOffset);
        file.writeLong(numLogFiles);
        file.seek(currentOffset);
    }

    // Types

    void startWritingTypes() throws IOException {
        typesOffset = file.getFilePointer();
        file.seek(TYPES_FPTR_OFFSET);
        file.writeLong(typesOffset);
        file.seek(typesOffset);
        // Placeholder for number of types.
        file.writeLong(0);
    }

    void writeType(final TypeDescription type) throws IOException {
        file.writeInt(type.getTypeId());
        writeString(file, type.getTypeName());
        file.writeInt(type.getLogFileId());
        int totalFieldSize = 0;
        List<FieldDescription> fields = type.getFields();
        for (Iterator<FieldDescription> i = fields.iterator(); i.hasNext();) {
            FieldDescription field = i.next();
            totalFieldSize += field.size();
        }
        file.writeInt(totalFieldSize);
        for (Iterator<FieldDescription> i = fields.iterator(); i.hasNext();) {
            FieldDescription field = i.next();
            writeString(file, field.getFieldName());
            file.writeInt(field.getFieldType().toId());
            file.writeInt(field.getElementCount());
        }
        numTypes++;
    }

    void endWritingTypes() throws IOException {
        long currentOffset = file.getFilePointer();
        file.seek(typesOffset);
        file.writeLong(numTypes);
        file.seek(currentOffset);
    }

    // Events

    void startWritingEvents() throws IOException {
        eventsOffset = file.getFilePointer();
        file.seek(EVENTS_FPTR_OFFSET);
        file.writeLong(eventsOffset);
        file.seek(eventsOffset);
        // Placeholder for number of events.
        file.writeLong(0);
    }

    void writeEvent(final GenericEventInfo event, final DataOutputStream out,
            final ByteArrayOutputStream bout) throws IOException {
        long fileOffset = file.getFilePointer() + bout.size();
        out.writeInt(event.getEventClass());
        out.writeLong(event.getTimestamp());
        out.writeInt(event.getLogFileId());

        if (event instanceof TaskSwitchEventInfo) {
            writeTaskSwitchEvent((TaskSwitchEventInfo) event, out);
            addTaskExec((TaskSwitchEventInfo) event);
            setFirstTaskDuration((TaskSwitchEventInfo) event);
            setLastTaskDuration((TaskSwitchEventInfo) event);
            numTaskSwitches++;
        } else if (event instanceof TaskReleaseEventInfo) {
            writeTaskReleaseEvent((TaskReleaseEventInfo) event, out);
        } else if (event instanceof TaskCompletionEventInfo) {
            writeTaskCompletionEvent((TaskCompletionEventInfo) event, out);
        } else if (event instanceof RoseSendEventInfo) {
            writeRoseSendEvent((RoseSendEventInfo) event, out);
        } else if (event instanceof SendEventInfo) {
            writeSendEvent((SendEventInfo) event, out);
        } else if (event instanceof RoseReceiveEventInfo) {
            writeRoseReceiveEventInfo((RoseReceiveEventInfo) event, out);
        } else if (event instanceof ReceiveEventInfo) {
            writeReceiveEventInfo((ReceiveEventInfo) event, out);
        } else if (event instanceof FunctionEnterEventInfo) {
            writeFunctionEnterEvent((FunctionEnterEventInfo) event, out);
        } else if (event instanceof FunctionExitEventInfo) {
            writeFunctionExitEvent((FunctionExitEventInfo) event, out);
        }
        out.writeInt(event.getTypeId());
        FieldValues fieldValues = event.getFieldValues();
        if (fieldValues != null) {
            out.writeInt(fieldValues.size());
            out.write(fieldValues.toByteArray());
        } else {
            out.writeInt(0);
        }

        if ((numEvents % NUM_EVENTS_IN_BLOCK) == 0) {
            file.write(bout.toByteArray());
            bout.reset();
            createBlock(event.getTimestamp(), numEvents, fileOffset);
        }
        numEvents++;
        lastEventTimestamp = event.getTimestamp();
    }

    private void writeTaskSwitchEvent(final TaskSwitchEventInfo event,
            final DataOutputStream out) throws IOException {
        out.writeInt(event.getInTaskId());
        out.writeInt(event.getOutTaskId());
        out.writeInt(event.getInTaskPriority());
    }

    private void writeTaskReleaseEvent(final TaskReleaseEventInfo event,
            final DataOutputStream out) throws IOException {
        out.writeInt(event.getTaskId());
        out.writeLong(event.getTimeBudget());
    }

    private void writeTaskCompletionEvent(final TaskCompletionEventInfo event,
            final DataOutputStream out) throws IOException {
        out.writeInt(event.getTaskId());
        out.writeLong(event.getRemainingTime());
    }

    private void writeSendEvent(final SendEventInfo event,
            final DataOutputStream out) throws IOException {
        out.writeInt(event.getSenderTaskId());
        out.writeInt(event.getReceiverTaskId());
        out.writeLong(event.getReceivedAtTimestamp());
        writeString(out, event.getMessageName());
    }

    private void writeRoseSendEvent(final RoseSendEventInfo event,
            final DataOutputStream out) throws IOException {
        out.writeInt(event.getSenderTaskId());
        out.writeInt(event.getReceiverTaskId());
        out.writeLong(event.getReceivedAtTimestamp());
        writeString(out, event.getMessageName());
        out.writeInt(event.getReceiverInstance());
    }

    private void writeReceiveEventInfo(final ReceiveEventInfo event,
            final DataOutputStream out) throws IOException {
        out.writeInt(event.getSenderTaskId());
        out.writeInt(event.getReceiverTaskId());
        out.writeLong(event.getSentAtTimestamp());
        writeString(out, event.getMessageName());
    }

    private void writeRoseReceiveEventInfo(final RoseReceiveEventInfo event,
            final DataOutputStream out) throws IOException {
        out.writeInt(event.getSenderTaskId());
        out.writeInt(event.getReceiverTaskId());
        out.writeLong(event.getSentAtTimestamp());
        writeString(out, event.getMessageName());
        out.writeInt(event.getStateId());
        out.writeLong(event.getFinish());
    }

    private void writeFunctionEnterEvent(final FunctionEnterEventInfo event,
            final DataOutputStream out) throws IOException {
        writeString(out, event.getName());
        out.writeLong(event.getEndTime());
    }

    private void writeFunctionExitEvent(final FunctionExitEventInfo event,
            final DataOutputStream out) throws IOException {
        writeString(out, event.getName());
        out.writeLong(event.getEndTime());
    }

    private void addTaskExec(final TaskSwitchEventInfo event) {
        int logFileId = event.getLogFileId();
        TaskSwitchEventInfo lastEvent = lastTaskSwitchEvent.get(logFileId);
        if ((lastEvent != null) && (lastEvent.getInTaskId() == event.getOutTaskId())) {
            int taskId = event.getOutTaskId();
            long exec = getTaskExec(logFileId, taskId);
            exec += event.getTimestamp() - lastEvent.getTimestamp();
            Map<Integer, Long> taskExecsMap = taskExecs.get(logFileId);
            if (taskExecsMap == null) {
                taskExecsMap = new HashMap<Integer, Long>();
                taskExecsMap.put(taskId, exec);
                taskExecs.put(logFileId, taskExecsMap);
            } else {
                taskExecsMap.put(taskId, exec);
                taskExecs.put(logFileId, taskExecsMap);
            }
        }
        lastTaskSwitchEvent.put(logFileId, event);
    }

    private long getTaskExec(final int logFileId, final int taskId) {
        Map<Integer, Long> taskExecsMap = taskExecs.get(logFileId);
        if (taskExecsMap != null) {
            Long exec = taskExecsMap.get(taskId);
            return (exec != null) ? exec : 0;
        }
        return 0;
    }

    private void setFirstTaskDuration(final TaskSwitchEventInfo event) {
        if ((firstTaskDuration == -1)
                || (firstTaskDuration > event.getTimestamp())) {
            firstTaskDuration = event.getTimestamp();
        }
    }

    private void setLastTaskDuration(final TaskSwitchEventInfo event) {
        if (lastTaskDuration < event.getTimestamp()) {
            lastTaskDuration = event.getTimestamp();
        }
    }

    private void createBlock(final long timestamp, final long index,
            final long fileOffset) throws IOException {
        List<EventIndex.Block> blocks = eventIndex.getBlocks();
        EventIndex.Block prevBlock = blocks.isEmpty() ? null : blocks
                .get(blocks.size() - 1);
        if (prevBlock != null) {
            prevBlock.setTotalEventSize(fileOffset
                    - prevBlock.getFirstEventFptr());
            prevBlock.setLastEventTimestamp(lastEventTimestamp);
        }

        EventIndex.Block block = new EventIndex.Block();
        block.setNumEvents(NUM_EVENTS_IN_BLOCK);
        block.setTotalEventSize(0);
        block.setFirstEventTimestamp(timestamp);
        block.setLastEventTimestamp(0);
        block.setFirstEventIndex(index);
        block.setFirstEventFptr(fileOffset);
        eventIndex.addBlock(block);
    }

    void endWritingEvents() throws IOException {
        long currentOffset = file.getFilePointer();
        file.seek(eventsOffset);
        file.writeLong(numEvents);
        file.seek(currentOffset);

        List<EventIndex.Block> blocks = eventIndex.getBlocks();
        EventIndex.Block lastBlock = blocks.isEmpty() ? null : blocks
                .get(blocks.size() - 1);
        if (lastBlock != null) {
            if ((numEvents % NUM_EVENTS_IN_BLOCK) != 0) {
                lastBlock.setNumEvents(numEvents % NUM_EVENTS_IN_BLOCK);
            }
            lastBlock.setTotalEventSize(currentOffset
                    - lastBlock.getFirstEventFptr());
            lastBlock.setLastEventTimestamp(lastEventTimestamp);
        }

        eventIndex.setNumBlocks(blocks.size());
        eventIndex.setNumEvents(numEvents);
        eventIndex.setTotalEventSize(currentOffset - eventsOffset);
        if (lastBlock != null) {
            eventIndex.setFirstEventTimestamp(blocks.get(0)
                    .getFirstEventTimestamp());
            eventIndex.setLastEventTimestamp(lastBlock.getLastEventTimestamp());
        }
    }

    // Event index

    void writeEventIndex() throws IOException {
        eventIndexOffset = file.getFilePointer();
        file.seek(EVENT_INDEX_FPTR_OFFSET);
        file.writeLong(eventIndexOffset);
        file.seek(eventIndexOffset);

        file.writeLong(eventIndex.getNumBlocks());
        file.writeLong(eventIndex.getNumEvents());
        file.writeLong(eventIndex.getTotalEventSize());
        file.writeLong(eventIndex.getFirstEventTimestamp());
        file.writeLong(eventIndex.getLastEventTimestamp());

        for (Iterator<EventIndex.Block> i = eventIndex.getBlocks().iterator(); i
                .hasNext();) {
            EventIndex.Block block = i.next();
            file.writeLong(block.getNumEvents());
            file.writeLong(block.getTotalEventSize());
            file.writeLong(block.getFirstEventTimestamp());
            file.writeLong(block.getLastEventTimestamp());
            file.writeLong(block.getFirstEventIndex());
            file.writeLong(block.getFirstEventFptr());
        }
    }

    // Artifacts

    void startWritingArtifacts() throws IOException {
        artifactsOffset = file.getFilePointer();
        file.seek(ARTIFACTS_FPTR_OFFSET);
        file.writeLong(artifactsOffset);
        file.seek(artifactsOffset);
        // Placeholder for number of artifacts.
        file.writeLong(0);
    }

    void writeArtifact(final GenericArtifactInfo artifact,
            final DataOutputStream out, final ByteArrayOutputStream bout)
            throws IOException {
        out.writeInt(artifact.getArtifactClass());
        out.writeInt(artifact.getId());
        writeString(out, artifact.getName());
        out.writeInt(artifact.getLogFileId());
        if (artifact instanceof TaskArtifactInfo) {
            writeTaskArtifact((TaskArtifactInfo) artifact, out);
        } else if (artifact instanceof StateArtifactInfo) {
            writeStateArtifact((StateArtifactInfo) artifact, out);
        }
        out.writeInt(artifact.getTypeId());
        FieldValues fieldValues = artifact.getFieldValues();
        if (fieldValues != null) {
            out.writeInt(fieldValues.size());
            out.write(fieldValues.toByteArray());
        } else {
            out.writeInt(0);
        }
        numArtifacts++;
        if (numArtifacts % NUM_EVENTS_IN_BLOCK == 0) {
            file.write(bout.toByteArray());
            bout.reset();
        }
    }

    private void writeTaskArtifact(final TaskArtifactInfo artifact,
            final DataOutputStream out) throws IOException {
        out.writeInt(artifact.getTaskClass().toId());
        out.writeInt(artifact.getTaskPriority());
    }

    private void writeStateArtifact(final StateArtifactInfo artifact,
            final DataOutputStream out) throws IOException {
        out.writeInt(artifact.getParentId());
    }

    void endWritingArtifacts() throws IOException {
        long currentOffset = file.getFilePointer();
        file.seek(artifactsOffset);
        file.writeLong(numArtifacts);
        file.seek(currentOffset);
    }

    // Task executions

    void startWritingTaskExecs() throws IOException {
        taskExecsOffset = file.getFilePointer();
        file.seek(TASK_EXECS_FPTR_OFFSET);
        file.writeLong(taskExecsOffset);
        file.seek(taskExecsOffset);
        // Placeholder for number of task executions.
        file.writeLong(0);
    }

    void writeTaskExec(final int logFileId, final int taskId, final double exec)
            throws IOException {
        file.writeInt(logFileId);
        file.writeInt(taskId);
        file.writeDouble(exec);
        numTaskExecs++;
    }

    void endWritingTaskExecs() throws IOException {
        long currentOffset = file.getFilePointer();
        file.seek(taskExecsOffset);
        file.writeLong(numTaskExecs);
        file.seek(currentOffset);
    }

    // Statistics

    void writeStatistics() throws IOException {
        statisticsOffset = file.getFilePointer();
        file.seek(STATISTICS_FPTR_OFFSET);
        file.writeLong(statisticsOffset);
        file.seek(statisticsOffset);
        file.writeLong(firstTaskDuration);
        file.writeLong(lastTaskDuration);
        file.writeLong(numTaskSwitches);
    }

    public void close() {
        if (file != null) {
            try {
                file.close();
            } catch (IOException ignore) {}
        }
        open = false;
    }

    private static void writeString(final RandomAccessFile file,
            final String str) throws IOException {
        file.writeInt(str.length());
        file.writeBytes(str);
    }

    private static void writeString(final DataOutputStream out, final String str)
            throws IOException {
        out.writeInt(str.length());
        out.writeBytes(str);
    }

    private static double round(final double value) {
        // Return a percentage value with two decimals precision.
        return Math.round(value * 100.0 * 100.0) / 100.0;
    }
}

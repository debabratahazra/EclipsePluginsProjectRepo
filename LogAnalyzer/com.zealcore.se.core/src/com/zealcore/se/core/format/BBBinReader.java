package com.zealcore.se.core.format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.Function;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.UMLReceiveEvent;
import com.zealcore.se.core.model.generic.GenericArtifact;
import com.zealcore.se.core.model.generic.GenericFunctionEnter;
import com.zealcore.se.core.model.generic.GenericFunctionExit;
import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.model.generic.GenericLogFile;
import com.zealcore.se.core.model.generic.GenericReceiveEvent;
import com.zealcore.se.core.model.generic.GenericSendEvent;
import com.zealcore.se.core.model.generic.GenericSequence;
import com.zealcore.se.core.model.generic.GenericState;
import com.zealcore.se.core.model.generic.GenericStateMachine;
import com.zealcore.se.core.model.generic.GenericStateTransition;
import com.zealcore.se.core.model.generic.GenericTask;
import com.zealcore.se.core.model.generic.GenericTaskCompletion;
import com.zealcore.se.core.model.generic.GenericTaskRelease;
import com.zealcore.se.core.model.generic.GenericTaskSwitchEvent;
import com.zealcore.se.core.model.generic.IGenericLogItem;
import com.zealcore.se.core.util.BufferedRandomAccessFile;

public class BBBinReader {
    private static final int MAGIC_NUMBER = 0xBADBABE;

    private static final int FILE_FORMAT_VERSION = 10;

    private static final long MIN_FILE_SIZE = 120;
    
    private FunctionSequenceBuilder functionSequenceBuilder;

    private final File logFile;

    private final RandomAccessFile file;

    private final Map<Integer, LogFileInfo> logFiles;

    private final EventIndex eventIndex;

    private boolean open;

    private int fileFormatVersion;

    private long logFilesOffset;

    private long typesOffset;

    private long eventsOffset;

    private long eventIndexOffset;

    private long artifactsOffset;

    private long taskExecsOffset;

    private long statisticsOffset;

    private long firstTaskDuration;

    private long lastTaskDuration;

    private long numTaskSwitchEvents;

    private GenericTask currentTask;

    private Map<ISequenceMember, GenericState> currentStates;

    private final Map<GenericStateTransition, GenericStateTransition> cache = 
        new HashMap<GenericStateTransition, GenericStateTransition>();

    public BBBinReader(final File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        this.logFile = file;
        this.file = new BufferedRandomAccessFile(file, "r");
        logFiles = new HashMap<Integer, LogFileInfo>();
        eventIndex = new EventIndex();
        open = true;
        currentStates = new HashMap<ISequenceMember, GenericState>();
    }

    public void read() throws IOException {
        checkState();
        if (fileFormatVersion != 0) {
            throw new IllegalStateException("BBBin file has already been read.");
        }
        readHeader();
        readLogFiles();
        readTypes();
        readEventIndex();
        readArtifacts();
        readTaskExecs();
        readStatistics();
    }

    public Map<Integer, LogFileInfo> getLogFiles() {
        checkState();
        return logFiles;
    }

    public Iterable<ILogEvent> getEvents(final long time, final int numEventsToSkip) {
        checkState();
        return new EventIterable(time, numEventsToSkip);
    }

    public long getNumEvents() {
        checkState();
        return eventIndex.getNumEvents();
    }

    public long getFirstEventTimestamp() {
        checkState();
        return eventIndex.getFirstEventTimestamp();
    }

    public long getLastEventTimestamp() {
        checkState();
        return eventIndex.getLastEventTimestamp();
    }

    public synchronized ILogEvent getEventAtIndex(final long index)
            throws IOException {
        checkState();

        if ((index >= 0) && (index < eventIndex.getNumEvents())) {
            List<EventIndex.Block> blocks = eventIndex.getBlocks();

            for (Iterator<EventIndex.Block> i = blocks.iterator(); i.hasNext();) {
                EventIndex.Block block = i.next();

                if ((index >= block.getFirstEventIndex())
                        && (index < (block.getFirstEventIndex() + block
                                .getNumEvents()))) {
                    long offset = block.getFirstEventFptr();
                    for (long j = 0; j < block.getNumEvents(); j++) {
                        ILogEvent event = readEvent(offset, file);
                        if (index == (block.getFirstEventIndex() + j)) {
                            return event;
                        }
                        offset = file.getFilePointer();
                    }
                    return null;
                }
            }
        }

        return null;
    }

    public synchronized ILogEvent getEventAtTimestamp(final long timestamp)
            throws IOException {
        checkState();

        if ((timestamp >= eventIndex.getFirstEventTimestamp())
                && (timestamp <= eventIndex.getLastEventTimestamp())) {
            List<EventIndex.Block> blocks = eventIndex.getBlocks();

            for (Iterator<EventIndex.Block> i = blocks.iterator(); i.hasNext();) {
                EventIndex.Block block = i.next();

                if ((timestamp >= block.getFirstEventTimestamp())
                        && (timestamp <= block.getLastEventTimestamp())) {
                    long offset = block.getFirstEventFptr();
                    ILogEvent prevEvent = readEvent(offset, file);
                    for (long j = 0; j < block.getNumEvents(); j++) {
                        ILogEvent event = readEvent(offset, file);
                        if ((timestamp >= prevEvent.getTs())
                                && (timestamp <= event.getTs())) {
                            return (timestamp == event.getTs()) ? event : prevEvent;
                        }
                        offset = file.getFilePointer();
                        prevEvent = event;
                    }
                    return null;
                }
            }
        }

        return null;
    }

    public synchronized long getIndexAtTimestamp(final long timestamp)
            throws IOException {
        checkState();

        if ((timestamp >= eventIndex.getFirstEventTimestamp())
                && (timestamp <= eventIndex.getLastEventTimestamp())) {
            List<EventIndex.Block> blocks = eventIndex.getBlocks();
            long index = 0;

            for (Iterator<EventIndex.Block> i = blocks.iterator(); i.hasNext();) {
                EventIndex.Block block = i.next();

                if ((timestamp >= block.getFirstEventTimestamp())
                        && (timestamp <= block.getLastEventTimestamp())) {
                    index = block.getFirstEventIndex();
                    long offset = block.getFirstEventFptr();
                    ILogEvent prevEvent = readEvent(offset, file);
                    for (long j = 0; j < block.getNumEvents(); j++) {
                        index++;
                        ILogEvent event = readEvent(offset, file);
                        if ((timestamp >= prevEvent.getTs())
                                && (timestamp <= event.getTs())) {
                            return (index - 1);
                        }
                        offset = file.getFilePointer();
                        prevEvent = event;
                    }
                    return 0;
                }
            }
        }

        return 0;
    }

    // FIXME: Add methods for retrieving a bunch of events around a specific
    // index and around a specific timestamp, respectively?

    public long getFirstTaskDuration() {
        return firstTaskDuration;
    }

    public long getLastTaskDuration() {
        return lastTaskDuration;
    }

    public long getNumTaskSwitchEvents() {
        return numTaskSwitchEvents;
    }

    private void checkState() throws IllegalStateException {
        if (!open) {
            throw new IllegalStateException("BBBinReader is closed");
        }
    }

    // Header

    private void readHeader() throws IOException {
        int magicNumber = file.readInt();
        if ((magicNumber != MAGIC_NUMBER) || (file.length() < MIN_FILE_SIZE)) {
            throw new IOException("Not a BBBin file.");
        }
        fileFormatVersion = file.readInt();
        if (fileFormatVersion < 10) {
            // Compatibility was broken with version 9 and older when custom
            // types and indexing capabilities was added to the file format.
            throw new IOException(
                    "Too old BBBin file, reimport the corresponding log file(s).");
        }
        if (fileFormatVersion > FILE_FORMAT_VERSION) {
            throw new IOException("The BBBin file uses a newer version of the "
                    + "file format than this BBBin reader.");
        }
        logFilesOffset = file.readLong();
        typesOffset = file.readLong();
        eventsOffset = file.readLong();
        eventIndexOffset = file.readLong();
        artifactsOffset = file.readLong();
        taskExecsOffset = file.readLong();
        statisticsOffset = file.readLong();
    }

    // Log files

    private void readLogFiles() throws IOException {
        file.seek(logFilesOffset);
        long numLogFiles = file.readLong();

        for (long i = 0; i < numLogFiles; i++) {
            int logFileId = file.readInt();
            String logFileName = readString(file);
            long logFileSize = file.readLong();
            long importedAtTimestamp = file.readLong();
            String importerName = readString(file);
            GenericLogFile logFile = new GenericLogFile();
            logFile.setFileName(logFileName);
            logFile.setSize(logFileSize);
            logFile.setImportedAt(importedAtTimestamp);
            logFile.setImporterId(importerName);
            logFile.setVersion(1);
            LogFileInfo logFileInfo = new LogFileInfo(logFileId, logFile);
            logFiles.put(logFileId, logFileInfo);
        }
    }

    // Types

    private void readTypes() throws IOException {
        file.seek(typesOffset);
        long numTypes = file.readLong();

        for (long i = 0; i < numTypes; i++) {
            int typeId = file.readInt();
            String typeName = readString(file);
            int logFileId = file.readInt();
            int totalFieldSize = file.readInt();
            List<FieldDescription> fields = new ArrayList<FieldDescription>();
            while (totalFieldSize > 0) {
                String fieldName = readString(file);
                int fieldTypeInt = file.readInt();
                FieldDescription.FieldType fieldType = FieldDescription.FieldType
                        .fromId(fieldTypeInt);
                int elementCount = file.readInt();
                FieldDescription field = new FieldDescription(fieldName,
                        fieldType, elementCount);
                fields.add(field);
                totalFieldSize -= field.size();
            }
            TypeDescription type = new TypeDescription(typeId, typeName, fields);
            type.setLogFileId(logFileId);
            getLogFileInfo(logFileId).addType(typeId, type);
        }
    }

    // Events

    private ILogEvent readEvent(final long eventOffset,
            final RandomAccessFile raf) throws IOException {
        raf.seek(eventOffset);

        ILogEvent event = null;
        int eventClass = raf.readInt();
        long timestamp = raf.readLong();
        int logFileId = raf.readInt();

        switch (eventClass) {
        case TaskSwitchEventInfo.EVENT_CLASS:
            event = createTaskSwitchEvent(timestamp, logFileId, raf);
            break;
        case TaskReleaseEventInfo.EVENT_CLASS:
            event = createTaskReleaseEvent(timestamp, logFileId, raf);
            break;
        case TaskCompletionEventInfo.EVENT_CLASS:
            event = createTaskCompletionEvent(timestamp, logFileId, raf);
            break;
        case SendEventInfo.EVENT_CLASS:
            event = createSendEvent(timestamp, logFileId, raf);
            break;
        case RoseSendEventInfo.EVENT_CLASS:
            event = createRoseSendEvent(timestamp, logFileId, raf);
            break;
        case ReceiveEventInfo.EVENT_CLASS:
            event = createReceiveEvent(timestamp, logFileId, raf);
            break;
        case RoseReceiveEventInfo.EVENT_CLASS:
            event = createRoseReceiveEvent(timestamp, logFileId, raf);
            break;
        case FunctionEnterEventInfo.EVENT_CLASS:
            event = createFunctionEnterEvent(timestamp, logFileId, raf);
            break;
        case FunctionExitEventInfo.EVENT_CLASS:
            event = createFunctionExitEvent(timestamp, logFileId, raf);
            break;
        case GenericEventInfo.EVENT_CLASS:
            event = new GenericLogEvent(timestamp);
            break;
        default:
            throw new ImportException("ERROR, unknown event class: "
                    + eventClass);
        }
        event.setLogFile(getLogFileInfo(logFileId).getLogFile());
        int typeId = raf.readInt();
        if (event instanceof IGenericLogItem) {
	        readFields(raf, getLogFileInfo(logFileId).getType(typeId),
	                (IGenericLogItem) event);
        } else {
        	raf.readInt();
        }
        return event;
    }

    private GenericTaskSwitchEvent createTaskSwitchEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        GenericTaskSwitchEvent event = new GenericTaskSwitchEvent(timestamp);
        int inTaskId = raf.readInt();
        int outTaskId = raf.readInt();
        int inTaskPriority = raf.readInt();
        currentTask = getTask(logFileId, inTaskId);
        event.setResourceUserIn(getTask(logFileId, inTaskId));
        event.setResourceUserOut(getTask(logFileId, outTaskId));
        event.setPriority(inTaskPriority);
        return event;
    }

    private GenericTaskRelease createTaskReleaseEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        GenericTaskRelease event = new GenericTaskRelease(timestamp);
        int taskId = raf.readInt();
        long timeBudget = raf.readLong();
        event.setTaskId(getTask(logFileId, taskId));
        event.setTimeBudget(timeBudget);
        return event;
    }

    private GenericTaskCompletion createTaskCompletionEvent(
            final long timestamp, final int logFileId,
            final RandomAccessFile raf) throws IOException {
        GenericTaskCompletion event = new GenericTaskCompletion(timestamp);
        int taskId = raf.readInt();
        long remainingTime = raf.readLong();
        event.setTaskId(getTask(logFileId, taskId));
        event.setRemainingTime(remainingTime);
        return event;
    }

    private GenericSendEvent createSendEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        GenericSendEvent event = new GenericSendEvent(timestamp);
        int senderTaskId = raf.readInt();
        int receiverTaskId = raf.readInt();
        long receivedAtTimestamp = raf.readLong();
        String messageName = readString(raf);
        event.setSender(getTask(logFileId, senderTaskId));
        event.setRecipent(getTask(logFileId, receiverTaskId));
        event.setDeliveryTime(receivedAtTimestamp);
        event.setMessage(messageName);
        return event;
    }

    private GenericSendEvent createRoseSendEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        GenericSendEvent event = new GenericSendEvent(timestamp);
        int senderTaskId = raf.readInt();
        int receiverTaskId = raf.readInt();
        long receivedAtTimestamp = raf.readLong();
        String messageName = readString(raf);
        int receiverInstance = raf.readInt();
        event.setReceiverInstance(receiverInstance);
        GenericStateMachine send = getStateMachine(logFileId, senderTaskId);
        if (send == null) {
            send = new GenericStateMachine(String.valueOf(senderTaskId));
            send.setParent(logFiles.get(logFileId).getSequence());
            send.setLogFile(logFiles.get(logFileId).getLogFile());
            event.setSender(send);
        } else {
            event.setSender(send);
        }
        GenericStateMachine task = getStateMachine(logFileId, receiverTaskId);
        if (task == null) {
            GenericStateMachine recipent = new GenericStateMachine(
                    String.valueOf(receiverTaskId));
            recipent.setParent(logFiles.get(logFileId).getSequence());
            recipent.setLogFile(logFiles.get(logFileId).getLogFile());
            event.setRecipent(recipent);
        } else {
            event.setRecipent(task);
        }
        event.setDeliveryTime(receivedAtTimestamp);
        event.setMessage(messageName);
        return event;
    }

    private GenericReceiveEvent createReceiveEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        GenericReceiveEvent event = new GenericReceiveEvent(timestamp);
        raf.readInt(); // senderTaskId
        int receiverTaskId = raf.readInt();
        long sentAtTimestamp = raf.readLong();
        String messageName = readString(raf);
        GenericTask receiverTask = getTask(logFileId, receiverTaskId);
        event.setReceiver(receiverTask);
        event.setResourceUser(receiverTask);
        event.setSentAt(sentAtTimestamp);
        event.setMessage(messageName);
        return event;
    }

    private UMLReceiveEvent createRoseReceiveEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        UMLReceiveEvent event = new UMLReceiveEvent(timestamp);
        raf.readInt(); // senderTaskId
        int receiverTaskId = raf.readInt();
        long sentAtTimestamp = raf.readLong();
        String messageName = readString(raf);
        int stateId = raf.readInt();
        long finish = raf.readLong();
        GenericStateMachine recipent = getStateMachine(logFileId,
                receiverTaskId);
        if (recipent == null) {
            recipent = new GenericStateMachine(String.valueOf(receiverTaskId));
            recipent.setParent(logFiles.get(logFileId).getSequence());
            recipent.setLogFile(logFiles.get(logFileId).getLogFile());
            event.setReceiver(recipent);
        } else {
            event.setReceiver(recipent);
        }
        GenericState currentState = currentStates.get(event.getReceiver());
        if (currentState == null) {
            currentState = new GenericState(IState.UNKNOWN);
        }
        currentState.setParent(event.getReceiver());
        currentState.setLogFile(logFiles.get(logFileId).getLogFile());
        logFiles.get(logFileId).addArtifact(
                currentState.getName() + currentState.getParent().getName(),
                currentState);
        event.setCurrentState(currentState);
        event.setResourceUser(getCurrentTask(logFileId));
        event.setSentAt(sentAtTimestamp);
        event.setMessage(messageName);
        GenericState state = logFiles.get(logFileId).getState(stateId);
        if (state == null) {
            state = new GenericState(IState.UNKNOWN);
            logFiles.get(logFileId).addState(stateId, state);
        }
        state.setLogFile(logFiles.get(logFileId).getLogFile());
        state.setParent(event.getReceiver());
        event.setStateIn(state);
        currentStates.put(event.getReceiver(), state);
        event.setFinish(finish);
        event.setTransition(getTransition(logFileId, event));

        return event;
    }

    private GenericFunctionEnter createFunctionEnterEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        String name = readString(raf);
        GenericFunctionEnter event = new GenericFunctionEnter(name);
        event.setTs(timestamp);
        event.setLogFile(getLogFileInfo(logFileId).getLogFile());
        long endTime = raf.readLong();
        event.setEndTime(endTime);
        getFunctionSequenceBuilder(logFileId).addFunctionEnter(event);
        return event;
    }

    private GenericFunctionExit createFunctionExitEvent(final long timestamp,
            final int logFileId, final RandomAccessFile raf) 
            throws IOException {
        String name = readString(raf);
        GenericFunctionExit event = new GenericFunctionExit(name);
        event.setTs(timestamp);
        event.setLogFile(getLogFileInfo(logFileId).getLogFile());
        long endTime = raf.readLong();
        event.setEndTime(endTime);
        getFunctionSequenceBuilder(logFileId).addFunctionExit(event);
        return event;
    }

    private GenericTask getTask(final int logFileId, final int taskId) {
    	GenericTask task = null;
    	try {
    		task = getLogFileInfo(logFileId).getTask(taskId);
    	} catch (Exception ex) {
    		return currentTask;
    	}
        /*
         * if (task == null) { throw new
         * ImportException("ERROR, unknown task id: " + taskId); }
         */
        return task;
    }

    private GenericStateMachine getStateMachine(final int logFileId,
            final int taskId) {
        return getLogFileInfo(logFileId).getStateMachine(taskId);
    }

    // Event index

    private void readEventIndex() throws IOException {
        file.seek(eventIndexOffset);

        eventIndex.setNumBlocks(file.readLong());
        eventIndex.setNumEvents(file.readLong());
        eventIndex.setTotalEventSize(file.readLong());
        eventIndex.setFirstEventTimestamp(file.readLong());
        eventIndex.setLastEventTimestamp(file.readLong());

        for (long i = 0; i < eventIndex.getNumBlocks(); i++) {
            EventIndex.Block block = new EventIndex.Block();
            block.setNumEvents(file.readLong());
            block.setTotalEventSize(file.readLong());
            block.setFirstEventTimestamp(file.readLong());
            block.setLastEventTimestamp(file.readLong());
            block.setFirstEventIndex(file.readLong());
            block.setFirstEventFptr(file.readLong());
            eventIndex.addBlock(block);
        }
    }

    // Artifacts

    private void readArtifacts() throws IOException {
        file.seek(artifactsOffset);
        long numArtifacts = file.readLong();

        for (long i = 0; i < numArtifacts; i++) {
            IArtifact artifact = null;
            int artifactClass = file.readInt();
            int artifactId = file.readInt();
            String artifactName = readString(file);
            int logFileId = file.readInt();
            switch (artifactClass) {
            case TaskArtifactInfo.ARTIFACT_CLASS:
                artifact = createTaskArtifact(logFileId, artifactId,
                        artifactName, file);
                break;
            case StateMachineArtifactInfo.ARTIFACT_CLASS:
                artifact = createStateMachine(logFileId, artifactId,
                        artifactName);
                break;
            case StateArtifactInfo.ARTIFACT_CLASS:
                artifact = createState(logFileId, artifactId, artifactName,
                        file);
                break;
            case GenericArtifactInfo.ARTIFACT_CLASS:
                artifact = new GenericArtifact();
                getLogFileInfo(logFileId).addArtifact(String.valueOf(artifactId), artifact);
                break;
            default:
                throw new ImportException("ERROR, unknown artifact class: "
                        + artifactClass);
            }
            artifact.setLogFile(getLogFileInfo(logFileId).getLogFile());
            artifact.setName(artifactName);
            int typeId = file.readInt();
            readFields(file, getLogFileInfo(logFileId).getType(typeId),
                    (IGenericLogItem) artifact);
        }
    }

    private GenericTask createTaskArtifact(final int logFileId,
            final int taskId, final String taskName, 
            final RandomAccessFile raf) throws IOException {
        int taskClass = raf.readInt();
        int taskPriority = raf.readInt();
        GenericTask task = new GenericTask(taskId, taskName, taskPriority);
        task.setTypeName(TaskArtifactInfo.TaskClass.fromId(taskClass)
                .toString());
        task.setParent(getLogFileInfo(logFileId).getSequence());
        getLogFileInfo(logFileId).addArtifact(String.valueOf(taskId), task);
        getLogFileInfo(logFileId).addTask(taskId, task);
        return task;
    }

    private GenericStateMachine createStateMachine(final int logFileId,
            final int taskId, final String taskName) {
        GenericStateMachine stateMachine = new GenericStateMachine();
        stateMachine.setParent(getLogFileInfo(logFileId).getSequence());
        stateMachine.setName(taskName);
        getLogFileInfo(logFileId).addArtifact(String.valueOf(taskId),
                stateMachine);
        getLogFileInfo(logFileId).addStateMachine(taskId, stateMachine);
        return stateMachine;
    }

    private GenericState createState(final int logFileId, final int taskId,
            final String taskName, final RandomAccessFile raf)
            throws IOException {
        int parentID = raf.readInt();
        GenericState state = new GenericState(taskName);
        AbstractArtifact parentState = getLogFileInfo(logFileId).getStates()
                .get(parentID);
        if (parentState == null) {
            parentState = getLogFileInfo(logFileId).getStateMachines().get(
                    parentID);
        }
        state.setParent(parentState);
        getLogFileInfo(logFileId).addArtifact(String.valueOf(taskId), state);
        getLogFileInfo(logFileId).addState(taskId, state);
        return state;
    }

    private IArtifact getCurrentTask(int logFileId) {
        if (currentTask == null) {
            currentTask = GenericTask.createUnknown(getLogFileInfo(logFileId)
                    .getLogFile());
            currentTask.setTypeName("Task");
            getLogFileInfo(logFileId).addTask((int) currentTask.getTaskId(),
                    currentTask);
        }
        return currentTask;
    }

    // Task executions

    private void readTaskExecs() throws IOException {
        file.seek(taskExecsOffset);
        long numTaskExecs = file.readLong();

        for (long i = 0; i < numTaskExecs; i++) {
            int logFileId = file.readInt();
            int taskId = file.readInt();
            double exec = file.readDouble();
            getLogFileInfo(logFileId).addTaskExec(taskId, exec);
        }
    }

    // Statistics

    private void readStatistics() throws IOException {
        file.seek(statisticsOffset);
        firstTaskDuration = file.readLong();
        lastTaskDuration = file.readLong();
        numTaskSwitchEvents = file.readLong();
    }

    public void close() {
        if (file != null) {
            try {
                file.close();
            } catch (IOException ignore) {}
        }
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stes.length; i++) {
        	//System.out.println(stes[i].toString());	
		}
        
        logFiles.clear();
        eventIndex.clear();
        open = false;
    }

    private LogFileInfo getLogFileInfo(final int logFileId) {
    	return logFiles.get(logFileId);
    }

    private static String readString(final RandomAccessFile file)
            throws IOException {
        int length = file.readInt();
        byte[] buf = new byte[length];
        file.read(buf);
        return new String(buf);
    }

    private Object readFieldValue(final RandomAccessFile file,
            final int logFileId, final FieldDescription.FieldType fieldType,
            final int elementCount) throws IOException {
        switch (fieldType) {
        case INT8:
        case UINT8:
            if (elementCount == 0) {
                return new Byte(file.readByte());
            } else {
                byte[] array = new byte[elementCount];
                file.read(array);
                return array;
            }
        case INT16:
        case UINT16:
            if (elementCount == 0) {
                return new Short(file.readShort());
            } else {
                short[] array = new short[elementCount];
                for (int i = 0; i < array.length; i++) {
                    array[i] = file.readShort();
                }
                return array;
            }
        case INT32:
        case UINT32:
            if (elementCount == 0) {
                return new Integer(file.readInt());
            } else {
                int[] array = new int[elementCount];
                for (int i = 0; i < array.length; i++) {
                    array[i] = file.readInt();
                }
                return array;
            }
        case INT64:
        case UINT64:
            if (elementCount == 0) {
                return new Long(file.readLong());
            } else {
                long[] array = new long[elementCount];
                for (int i = 0; i < array.length; i++) {
                    array[i] = file.readLong();
                }
                return array;
            }
        case FLOAT:
            if (elementCount == 0) {
                return new Float(file.readFloat());
            } else {
                float[] array = new float[elementCount];
                for (int i = 0; i < array.length; i++) {
                    array[i] = file.readFloat();
                }
                return array;
            }
        case DOUBLE:
            if (elementCount == 0) {
                return new Double(file.readFloat());
            } else {
                double[] array = new double[elementCount];
                for (int i = 0; i < array.length; i++) {
                    array[i] = file.readDouble();
                }
                return array;
            }
        case BOOLEAN:
            if (elementCount == 0) {
                return new Boolean(file.readBoolean());
            } else {
                boolean[] array = new boolean[elementCount];
                for (int i = 0; i < array.length; i++) {
                    array[i] = file.readBoolean();
                }
                return array;
            }
        case STRING:
            if (elementCount == 0) {
                return readString(file);
            } else {
                String[] array = new String[elementCount];
                for (int i = 0; i < array.length; i++) {
                    array[i] = readString(file);
                }
                return array;
            }
        case ARTIFACT_ID:
            int artifactId = file.readInt();
            return getLogFileInfo(logFileId).getArtifact(artifactId);
        default:
            throw new ImportException("ERROR, unknown field type: " + fieldType);
        }
    }

    private void readFields(final RandomAccessFile file,
            final TypeDescription type, final IGenericLogItem item)
            throws IOException {
        file.readInt(); // fieldValuesSize
        item.setTypeName(type.getTypeName());
        for (Iterator<FieldDescription> i = type.getFields().iterator(); i
                .hasNext();) {
            FieldDescription field = i.next();
            Object value = readFieldValue(file, type.getLogFileId(), field
                    .getFieldType(), field.getElementCount());
            item.addProperty(field.getFieldName(), value);
        }
    }

    private class EventIterable implements Iterable<ILogEvent> {
        private final long time;

        private final int numEventsToSkip;

        public EventIterable(final long time, final int numEventsToSkip) {
            this.time = time;
            this.numEventsToSkip = numEventsToSkip;
        }

        public Iterator<ILogEvent> iterator() {
            return new EventIterator(time, numEventsToSkip);
        }
    }

    private class EventIterator implements Iterator<ILogEvent> {
        private final RandomAccessFile raf;

        private final long numEvents;

        private long index;

        private long fileOffset;

        public EventIterator(final long time, final int numEventsToSkip) {
            try {
                raf = new BufferedRandomAccessFile(logFile, "r");
                raf.seek(eventsOffset);
                numEvents = raf.readLong();
                if (numEventsToSkip > 0) {
                    fileOffset = skipEvents(numEventsToSkip - 1, raf.getFilePointer());
                    index = numEventsToSkip;
                } else {
                    fileOffset = skipEvents(time, raf.getFilePointer());
                }
            } catch (IOException e) {
                throw new ImportException(e);
            }
        }

        public boolean hasNext() {
            return (index < numEvents);
        }

        public ILogEvent next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            try {
                ILogEvent event = readEvent(fileOffset, raf);
                fileOffset = raf.getFilePointer();
                index++;
                return event;
            } catch (IOException e) {
                throw new ImportException(e);
            }
        }

        // FIXME: This is a terrible kludge!
        public void remove() {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException ignore) {}
            }
        }

        private long skipEvents(final int numEventsToSkip, final long filePointer)
                throws IOException {
            checkState();

            if ((numEventsToSkip >= 0)
                    && (numEventsToSkip < eventIndex.getNumEvents())) {
                List<EventIndex.Block> blocks = eventIndex.getBlocks();

                for (Iterator<EventIndex.Block> i = blocks.iterator(); i.hasNext();) {
                    EventIndex.Block block = i.next();

                    if ((numEventsToSkip >= block.getFirstEventIndex())
                            && (numEventsToSkip < (block.getFirstEventIndex() + block
                                    .getNumEvents()))) {
                        long offset = block.getFirstEventFptr();
                        for (long j = 0; j < block.getNumEvents(); j++) {
                            readEvent(offset, raf);
                            if (numEventsToSkip == (block.getFirstEventIndex() + j)) {
                                return raf.getFilePointer();
                            }
                            offset = raf.getFilePointer();
                        }
                        return filePointer;
                    }
                }
            }

            return filePointer;
        }

        private long skipEvents(final long timestamp, final long filePointer) {
            long[] blockFirstEventFptr = new long[5];
            blockFirstEventFptr[0] = filePointer;
            blockFirstEventFptr[1] = filePointer;
            blockFirstEventFptr[2] = filePointer;
            blockFirstEventFptr[3] = filePointer;
            blockFirstEventFptr[4] = filePointer;
            long[] ptr = new long[5];

            if ((timestamp >= eventIndex.getFirstEventTimestamp())
                    && (timestamp <= eventIndex.getLastEventTimestamp())) {
                List<EventIndex.Block> blocks = eventIndex.getBlocks();
                int blockIndex = 0;

                for (Iterator<EventIndex.Block> i = blocks.iterator(); i.hasNext();) {
                    EventIndex.Block block = i.next();

                    if ((timestamp >= block.getFirstEventTimestamp())
                            && (timestamp <= block.getLastEventTimestamp())) {
                        index = ptr[blockIndex];
                        return blockFirstEventFptr[blockIndex];
                    } else {
                        blockFirstEventFptr[blockIndex] = block
                                .getFirstEventFptr();
                        ptr[blockIndex] = block.getFirstEventIndex();
                    }
                    blockIndex++;
                    if (blockIndex == 5) {
                        blockIndex = 0;
                    }
                }
            }

            return blockFirstEventFptr[0];
        }
    }

    private GenericStateTransition getTransition(final int logFileId,
            final UMLReceiveEvent event) {
        GenericStateTransition key = new GenericStateTransition(
                event.getCurrentState(), event.getStateIn());
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        cache.put(key, key);
        key.setLogFile(logFiles.get(logFileId).getLogFile());
        logFiles.get(logFileId).addArtifact(key.getId(), key);
        return key;
    }

    private final class FunctionSequenceBuilder {
        private final int logFileId;

        private Map<String, ISequenceMember> sequenceMemberMap = new HashMap<String, ISequenceMember>();

        private Stack<ISequenceMember> functionStack = new Stack<ISequenceMember>();

        private ISequence sequence;

        public FunctionSequenceBuilder(final int logFileId) {
            this.logFileId = logFileId;

            sequence = new GenericSequence();
            sequence.setName("Function Calls");
            sequence.setLogFile(getLogFileInfo(logFileId).getLogFile());
            sequence.setName("FunctionTrace[" + getLogFileInfo(logFileId).getLogFile().getFileName()
                    + "]");
            sequence.setRoot(true);
            getLogFileInfo(logFileId).addArtifact(sequence.getName(), sequence);
            addUnKnownFunction();
        }
        
        private void addUnKnownFunction() {
        	functionStack.clear();
            ISequenceMember unknownFunction = new Function();
            unknownFunction.setLogFile(getLogFileInfo(logFileId).getLogFile());
            unknownFunction.setName("Unknown Function");
            unknownFunction.setParent(sequence);
            unknownFunction.setRoot(false);
            getLogFileInfo(logFileId).addArtifact(unknownFunction.getName(), unknownFunction);
            functionStack.push(unknownFunction);
        }
        
        private ISequenceMember getFunctionByName(final String name) {
            ISequenceMember sequenceMember = sequenceMemberMap.get(name);
            if (sequenceMember == null) {
                sequenceMember = new Function();
                sequenceMember.setLogFile(getLogFileInfo(logFileId).getLogFile());
                sequenceMember.setName(name);
                sequenceMember.setParent(sequence);
                sequenceMember.setRoot(false);
                getLogFileInfo(logFileId).addArtifact(sequenceMember.getName(), sequenceMember);
                sequenceMemberMap.put(name, sequenceMember);
            }
            return sequenceMember;
        }

        public void addFunctionExit(final GenericFunctionExit functionExit) {
        	try {
	            ISequenceMember sender = functionStack.pop();
	            functionExit.setSender(sender);
	            ISequenceMember recepient = functionStack.peek();
	            functionExit.setRecipent(recepient);
        	} catch(Exception ex) {}
        }

        public void addFunctionEnter(final GenericFunctionEnter functionEnter) {
    		if (functionStack.isEmpty()) {
    			addUnKnownFunction();
    		}
            ISequenceMember recipent = getFunctionByName(functionEnter
                    .getFunctionName());
            functionEnter.setRecipent(recipent);
            ISequenceMember sender = functionStack.peek();
            functionEnter.setSender(sender);
            functionStack.push(recipent);
        }

    }

    private FunctionSequenceBuilder getFunctionSequenceBuilder(int logFileId) {
        if (functionSequenceBuilder == null) {
            functionSequenceBuilder = new FunctionSequenceBuilder(logFileId);
        }
        return functionSequenceBuilder;
    }
}

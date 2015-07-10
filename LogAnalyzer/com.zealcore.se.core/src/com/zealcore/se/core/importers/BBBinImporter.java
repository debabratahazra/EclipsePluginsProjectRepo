package com.zealcore.se.core.importers;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.zip.ZipInputStream;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.importers.BBBinWriter.TaskType;
import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.Function;
import com.zealcore.se.core.model.IActivitySetter;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.model.UMLReceiveEvent;
import com.zealcore.se.core.model.generic.GenericFunctionEnter;
import com.zealcore.se.core.model.generic.GenericFunctionExit;
import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.model.generic.GenericSendEvent;
import com.zealcore.se.core.model.generic.GenericSequence;
import com.zealcore.se.core.model.generic.GenericState;
import com.zealcore.se.core.model.generic.GenericStateMachine;
import com.zealcore.se.core.model.generic.GenericStateTransition;
import com.zealcore.se.core.model.generic.GenericTask;
import com.zealcore.se.core.model.generic.GenericTaskCompletion;
import com.zealcore.se.core.model.generic.GenericTaskRelease;
import com.zealcore.se.core.model.generic.GenericTaskSwitchEvent;
import com.zealcore.srl.offline.Field;
import com.zealcore.srl.offline.Struct;
import com.zealcore.srl.offline.Type.Srl;

/**
 * @author pasa
 * 
 */
public class BBBinImporter implements IImporter, Closeable {

    private static final String TIMEOUT = "Timeout";

    private static final String NTICK = "nTick";

    private static final String TICK = "Tick";

    private DataInputStream buffer;

    // private final List<String> stringDb = new ArrayList<String>(200);

    private int nrOfEvents;

    private LogFile fileObject;

    private GenericSequence sequence;

    private GenericTask currentTask;

    private HashMap<Integer, GenericTask> taskMap;

    private Map<ActorId, GenericState> states;

    private Map<ISequenceMember, GenericState> currentStates;

    private GenericStateMachine currentSm;

    private Map<ActorId, GenericStateMachine> stateMachineMap;

    private File logFile;

    private ImportContext importContext;

    private int version;

    private FunctionSequenceBuilder functionSequenceBuilder;

    private static final String TASK = "Task";

    private static final int MIN_SIZE = 48;

    private String taskTypeName = TASK;

    private static final String PORT_INDEX = "PortIndex";

    private static final String MESSAGE_ADDRESS = "MessageAddress";

    private static final String PORT = "Port";

    private static final String NO_SUCH_TASK_TASK_ID = "No Such task: taskId = ";

    private static final String RBRACKET = "]";

    private static final String SEPARATOR = "_";

    private static final String NUMBER = "Signal Number";

    private static final String SIGNAL_ID = "Signal Id";

    private static final String PROCESS = "Process";

    private static final String ADDRESS = "Signal Address";

    private static final String EXECUTION_UNIT = "ExecutionUnit";

    private static final String SIGNAL_SIZE = "Signal Size";

    private static final String UNKNOWN = "Unknown";

    private static final String ERROR = "Error";

    private static final String DATA = "Data";

    private static final String NAME = "Name";

    private static final String REFNR = "Entry";

    @Override
    public String toString() {
        return "BBBin Importer";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#canRead(java.io.File)
     */
    public boolean canRead(final File logFile) {

        long id = 0;

        if (logFile.canRead() && logFile.length() > BBBinImporter.MIN_SIZE) {
            try {
                buffer = new DataInputStream(new FileInputStream(logFile));
                id = buffer.readInt();
                version = buffer.readInt();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (final IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            try {
                buffer.close();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return id == 0x0BADBABEL;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getArtifacts()
     */
    @SuppressWarnings("unchecked")
    public Iterable<IArtifact> getArtifacts() {
        return Collections.EMPTY_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getLogEvents()
     */
    public Iterable<ILogEvent> getLogEvents() {
        try {
            initialize();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new Iterable<ILogEvent>() {
            public Iterator<ILogEvent> iterator() {
                return new FileIterator();
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#setLogFile(java.io.File)
     */
    public void setContext(final ImportContext context) {
        importContext = context;
        final File file = context.getFile();

        if (logFile != null) {
            throw new IllegalStateException(
                    "SetLogFile should only be called once");
        }
        logFile = file;
        if (!file.canRead()) {
            throw new RuntimeException("Failed to read file: " + file.getName()
                    + " on path: " + file.getAbsolutePath());
        }
    }

    private void initialize() throws IOException {
        final File file = importContext.getFile();

        final InputStream fis = new FileInputStream(file);

        if (isZipped(file)) {
            final ZipInputStream zipIn = new ZipInputStream(fis);
            zipIn.getNextEntry();

            buffer = new DataInputStream(new BufferedInputStream(zipIn));
        } else {
            buffer = new DataInputStream(new BufferedInputStream(fis));
        }

        fileObject = importContext.getLog();
        fileObject.setVersion(1);

        cache.clear();
        taskMap = new HashMap<Integer, GenericTask>();
        stateMachineMap = new HashMap<ActorId, GenericStateMachine>();
        // stateMachineMapOse = new HashMap<Integer, GenericStateMachine>();
        states = new HashMap<ActorId, GenericState>();
        currentStates = new HashMap<ISequenceMember, GenericState>();

        if (1 > buffer.available()) {
            throw new RuntimeException("Failed to read buffer from file: "
                    + file.getName() + " on path : " + file.getAbsolutePath());
        }

        /*
         * Read and ignore magic id
         */
        buffer.readInt();

        version = buffer.readInt();
        /*
         * Read and ignore data for future usage (410 bytes)
         */
        buffer.skipBytes(36);
        // readStrings();
        if (version >= 6) {
            readUserStructs();
        }

        if (version >= 3) {
            readTaskTable();
        }

        if (version >= 2) {
            createStaticStateMachines();
        }
        nrOfEvents = buffer.readInt();
    }

    private void readUserStructs() throws IOException {
        int structCount = buffer.readInt();
        for (int i = 0; i < structCount; ++i) {
            int structId = buffer.readInt();
            // int structNameStringId = buffer.readInt();
            // String structName = getStringValue(structNameStringId);
            String structName = getStringFromBuffer();

            Struct struct = new Struct(structId, structName);

            int fieldCount = buffer.readInt();
            for (int j = 0; j < fieldCount; ++j) {
                // int fieldNameId = buffer.readInt();
                // String fieldName = getStringValue(fieldNameId);
                String fieldName = getStringFromBuffer();
                int fieldTypeId = buffer.readInt();
                int fieldElementCount = buffer.readInt();

                Field field = new Field(structId, fieldTypeId,
                        fieldElementCount, fieldName);

                struct.addField(field);

            }
            userStructMap.put(structId, struct);
        }
    }

    // private void readStrings() throws IOException {
    // final int stringCntr = buffer.readInt();
    // /*
    // * Read strings
    // */
    // int strLen;
    // final Charset charset = Charset.forName("US-ASCII");
    // for (int i = 0; i < stringCntr; i++) {
    // strLen = buffer.readInt();
    // final byte[] strB = new byte[strLen];
    // buffer.read(strB, 0, strLen);
    // stringDb.add(charset.decode(ByteBuffer.wrap(strB, 0, strLen))
    // .toString());
    // }
    // }

    private void readTaskTable() throws IOException {
        Map<Integer, TaskStats> taskStatsMap = new HashMap<Integer, TaskStats>();

        final int taskStatsCount = buffer.readInt();
        for (int i = 0; i < taskStatsCount; i++) {
            final int pid = buffer.readInt();
            TaskStats taskStats = new TaskStats(buffer);
            taskStatsMap.put(pid, taskStats);
        }

        final int taskCount = buffer.readInt();
        if (version > 6) {
            int taskTypeInt = buffer.readInt();
            if (taskTypeInt == TaskType.TASK.taskTypeId()) {
                this.taskTypeName = TASK;
            } else if (taskTypeInt == TaskType.PROCESS.taskTypeId()) {
                this.taskTypeName = PROCESS;
            } else if (taskTypeInt == TaskType.THREAD.taskTypeId()) {
                this.taskTypeName = "Thread";
            }
        }

        for (int i = 0; i < taskCount; i++) {
            final int pid = buffer.readInt();
            // final String name = getStringValue(buffer.readInt());
            final String name = getStringFromBuffer();
            final int prio = buffer.readInt();

            // hasExec
            boolean hasExecuted = buffer.readBoolean();
            GenericTask task = createTask(pid, name, prio);
            task.setTypeName(taskTypeName);
            task.setHasExecutedDuringLog(hasExecuted);

            TaskStats taskStats = taskStatsMap.get(pid);
            if (taskStats != null) {
                long remainingTicksCount = taskStats.getRemainingTicksCount();
                long remainingTicksMin = taskStats.getRemainingTicksMin();
                long remainingTicksMax = taskStats.getRemainingTicksMax();
                float remainingTicksAvg = taskStats.getRemainingTicksAvg();

                task.addProperty("RemainingTicksCount", remainingTicksCount);
                task.addProperty("RemainingTicksMin", remainingTicksMin);
                task.addProperty("RemainingTicksMax", remainingTicksMax);
                task.addProperty("RemainingTicksAvg", remainingTicksAvg);
            }
        }
    }

    private void createStaticStateMachines() throws IOException {
        /*
         * Read nr of statemachines
         */
        final int nrOfStateMachines = buffer.readInt();
        int nrOfStates;
        // int nameIndex;
        int nrOTransitions;
        int parentId;
        // int depth;
        int machineId;
        int stateId;

        for (int i = 0; i < nrOfStateMachines; i++) {
            // Read id of statemachine
            machineId = buffer.readInt();

            // Read name of statemachine (str index)
            // nameIndex = buffer.readInt();
            final String name1 = getStringFromBuffer();

            // Read nr of states
            nrOfStates = buffer.readInt();

            // Read nr of transitions
            nrOTransitions = buffer.readInt();

            currentSm = new GenericStateMachine();
            currentSm.setParent(getSequence());
            currentSm.setLogFile(fileObject);
            // currentSm.setName(getStringValue(nameIndex));
            currentSm.setName(name1);
            // currentSm.setRoot(true);
            currentSm = importContext.resolveArtifact(currentSm);

            stateMachineMap.put(new ActorId(machineId, machineId), currentSm);

            /*
             * Read states for current machine
             */
            for (int j = 0; j < nrOfStates; j++) {
                // Read id of state
                stateId = buffer.readInt();

                // Read name of state (str index)
                // nameIndex = buffer.readInt();
                final String name2 = getStringFromBuffer();

                // id of parent (str index)
                parentId = buffer.readInt();

                // depth
                buffer.readInt();

                GenericState state = new GenericState(name2);

                AbstractArtifact parentState = states.get(new ActorId(parentId,
                        parentId));
                if (parentState == null) {
                    parentState = stateMachineMap.get(new ActorId(parentId,
                            parentId));
                }

                state.setParent(parentState);

                state = importContext.resolveArtifact(state);
                states.put(new ActorId(stateId, stateId), state);
            }

            /*
             * Read transitions for current machine
             */
            for (int j = 0; j < nrOTransitions; j++) {
                buffer.readInt();
                buffer.readInt();
            }
        }

    }

    private boolean isZipped(final File logFile2) throws IOException {
        if (logFile2.length() < 4) {
            return false;
        }
        DataInputStream reader = null;
        try {
            reader = new DataInputStream(new FileInputStream(logFile2));
            final int magic = reader.readInt();
            return magic == 0x504b0304 || magic == 0x504b0506;

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    ILogEvent readEvent() throws IOException {
        ILogEvent event = null;

        final long ts = buffer.readLong();
        final int eventId = buffer.readInt();

        if (eventId == BBBinWriter.Message.ROSE_RECEIVE.binTypeId()) {
            event = createRoseRecEvent(ts);
        } else if (eventId == BBBinWriter.Message.ROSE_SEND.binTypeId()) {
            event = createRoseSendEvent(ts);
        } else if (eventId == BBBinWriter.Message.TASK_SWITCH.binTypeId()) {
            event = createTaskSwitchEvent(ts);
        } else if (eventId == BBBinWriter.Message.TASK_INSTANCE.binTypeId()) {
            throw new IllegalStateException("Taskinstance");
        } else if (eventId == BBBinWriter.Message.TASK_RELEASE.binTypeId()) {
            event = createTaskRelease(ts);
        } else if (eventId == BBBinWriter.Message.TASK_COMPLETE.binTypeId()) {
            event = createTaskComplete(ts);
        } else if (eventId == BBBinWriter.Message.FUNCTION_ENTER.binTypeId()) {
            event = createFunctionEnter(ts);
        } else if (eventId == BBBinWriter.Message.FUNCTION_EXIT.binTypeId()) {
            event = createFunctionExit(ts);
        } else if (eventId == BBBinWriter.Message.OSE_SWAP.binTypeId()) {
            event = createOSESwap(ts);
        } else if (eventId == BBBinWriter.Message.OSE_SEND.binTypeId()) {
            event = createOSESend(ts);
        } else if (eventId == BBBinWriter.Message.OSE_RECIEVE.binTypeId()) {
            event = createOSEReceive(ts);
        } else if (eventId == BBBinWriter.Message.OSE_KILL.binTypeId()) {
            event = createOSEKill(ts);
        } else if (eventId == BBBinWriter.Message.OSE_CREATE.binTypeId()) {
            event = createOSECreate(ts);
        } else if (eventId == BBBinWriter.Message.OSE_ERROR.binTypeId()) {
            event = createOSEError(ts);
        } else if (eventId == BBBinWriter.Message.OSE_ALLOC.binTypeId()) {
            event = createOSEAlloc(ts);
        } else if (eventId == BBBinWriter.Message.OSE_FREE.binTypeId()) {
            event = createOSEFree(ts);
        } else if (eventId == BBBinWriter.Message.OSE_RESET.binTypeId()) {
            event = createOSEReset(ts);
        } else if (eventId == BBBinWriter.Message.OSE_LOSS.binTypeId()) {
            event = createOSELoss(ts);
        } else if (eventId == BBBinWriter.Message.OSE_USER.binTypeId()) {
            event = createOSEUserEvents(ts);
        } else if (eventId == BBBinWriter.Message.OSE_BIND.binTypeId()) {
            event = createOSEBind(ts);
        } else if (eventId == BBBinWriter.Message.OSE_TIMEOUT.binTypeId()) {
            event = createOSETimeout(ts);
        } else {
            Struct struct = userStructMap.get(eventId);
            if (struct != null) {
                event = createUserEvent(ts, struct);
            } else {
                throw new ImportException("Unknown event type: " + eventId
                        + " was found in " + logFile.getName() + " on path "
                        + logFile.getAbsolutePath());
            }
        }

        if (event == null) {
            GenericLogEvent genericLogEvent = new GenericLogEvent();
            genericLogEvent.setTypeName("UnknownEventType");
            genericLogEvent.setLogFile(fileObject);
            genericLogEvent.setTs(ts);
            event = genericLogEvent;
        }
        return event;
    }

    private ILogEvent createOSETimeout(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName(TIMEOUT);
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        event.addProperty(TIMEOUT, buffer.readInt());
        event.addProperty("System Call", buffer.readInt());
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSESwap(final long ts) throws IOException {
        final GenericTaskSwitchEvent event = new GenericTaskSwitchEvent(ts);
        event.setTypeName("Swap");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }
        final int outPid = buffer.readInt();

        // int outName = buffer.readInt();
        final int inPid = buffer.readInt();

        // int inName = buffer.readInt();
        int prio = 0;

        // Read prio
        if (version > 1) {
            prio = buffer.readInt();
        }
        int refNr = buffer.readInt();
        event.addProperty(REFNR, refNr);
        final GenericTask in = getTaskFromId(inPid);
        final GenericTask out = getTaskFromId(outPid);

        // event.setOutProcessId(out.getTaskId());
        event.setResourceUserOut(out);
        // event.setInProcessId(in.getTaskId());
        event.setResourceUserIn(in);
        event.setPriority(prio);

        event.setLogFile(fileObject);
        currentTask = in;
        return event;
    }

    private ILogEvent createOSEError(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName(ERROR);
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        final int pid = buffer.readInt();
        // final int strIdx = buffer.readInt();
        final GenericTask proc = getTaskFromId(pid);
        event.addProperty(PROCESS, proc);
        event.addProperty(ERROR, buffer.readInt());
        // String stringValue = getStringValue(buffer.readInt());
        String stringValue = getStringFromBuffer();
        Boolean isExecutive = Boolean.getBoolean(stringValue);
        event.addProperty("Is Executive", isExecutive);
        event.addProperty("Extra", buffer.readInt());
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSEBind(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("Bind");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        final int pid = buffer.readInt();
        // final int strIdx = buffer.readInt();
        final GenericTask bound = getTaskFromId(pid);
        event.addProperty("Bound Process", bound);
        event.addProperty("Execution Unit", buffer.readInt());
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSEUserEvents(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("User");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        event.setLogFile(fileObject);

        event.addProperty("Event Number", buffer.readInt());
        event.addProperty("Event Datasize", buffer.readInt());
        // event.addProperty("Data", getStringValue(buffer.readInt()));
        // int struct = buffer.readInt();
        String structName = getStringFromBuffer();

        if (structName.length() == 0) {
            event.addProperty(NAME, "");
            // event.addProperty(DATA, getStringValue(buffer.readInt()));
            event.addProperty(DATA, getStringFromBuffer());
        } else {
            // event.addProperty(NAME, getStringValue(struct));
            event.addProperty(NAME, structName);
            // event.addProperty(DATA, getStringValue(buffer.readInt()));
            String stringFromBuffer = getStringFromBuffer();
            event.addProperty(DATA, stringFromBuffer);
        }
        event.addProperty(REFNR, buffer.readInt());
        return event;
    }

    private ILogEvent createOSELoss(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("Loss");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        event.addProperty("lost Size", buffer.readInt());
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSEReset(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("Reset");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSEAlloc(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("Alloc");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        final int pid = buffer.readInt();
        // final int strIdx = buffer.readInt();
        final GenericTask proc = getTaskFromId(pid);

        event.addProperty(PROCESS, proc);
        event.addProperty(ADDRESS, buffer.readInt());
        event.addProperty(SIGNAL_ID, buffer.readInt());
        event.addProperty(NUMBER, buffer.readInt());
        event.addProperty(SIGNAL_SIZE, buffer.readInt());
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSEFree(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("Free");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        final int pid = buffer.readInt();
        // final int strIdx = buffer.readInt();
        final GenericTask proc = getTaskFromId(pid);
        event.addProperty(PROCESS, proc);
        event.addProperty(ADDRESS, buffer.readInt());
        event.addProperty(SIGNAL_ID, buffer.readInt());
        event.addProperty(NUMBER, buffer.readInt());
        event.addProperty(SIGNAL_SIZE, buffer.readInt());
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSECreate(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("Create");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        final int createdPid = buffer.readInt();
        // final int createdStrIdx = buffer.readInt();
        final int creatorPid = buffer.readInt();
        // final int creatorStrIdx = buffer.readInt();
        if (creatorPid != -1) {
            final GenericTask creator = getTaskFromId(creatorPid);
            event.addProperty("Creator Process", creator);
        }
        final GenericTask created = getTaskFromId(createdPid);
        event.addProperty("Created Process", created);
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSEKill(final long ts) throws IOException {
        final GenericLogEvent event = new GenericLogEvent(ts);
        event.setTypeName("Kill");
        if (version > 8) {
            event.addProperty(TICK, buffer.readInt());
            event.addProperty(NTICK, buffer.readInt());
        }

        final int killedPid = buffer.readInt();
        // final int killedStrIdx = buffer.readInt();
        final int killerPid = buffer.readInt();
        // final int killerStrIdx = buffer.readInt();
        if (killerPid != -1) {
            final GenericTask killer = getTaskFromId(killerPid);
            event.addProperty("Killer Process", killer);
        }
        final GenericTask killed = getTaskFromId(killedPid);
        event.addProperty("Killed Process", killed);
        event.addProperty(REFNR, buffer.readInt());
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createOSEReceive(final long ts) throws IOException {
        final UMLReceiveEvent rec = new UMLReceiveEvent(ts);
        rec.setTypeName("Receive");
        if (version > 8) {
            rec.addProperty(TICK, buffer.readInt());
            rec.addProperty(NTICK, buffer.readInt());
        }

        final int senderId = buffer.readInt();
        final GenericTask from = getTaskFromId(senderId);
        // int senderNameidx = buffer.readInt();
        int receiverId = buffer.readInt();
        // int receiverNameidx = buffer.readInt();
        final GenericTask to = getTaskFromId(receiverId);
        rec.setReceiver(to);
        rec.setResourceUser(to);
        rec.addProperty("From", from);
        // event.setReceiverInstance();
        rec.setLogFile(fileObject);

        GenericState currentState = getCurrentState(rec.getReceiver());
        currentState.setParent(rec.getReceiver());
        currentState.setLogFile(fileObject);
        rec.setCurrentState(currentState);
        rec.setStateIn(currentState);
        rec.setTransition(getTransition(rec));
        currentState = importContext.resolveArtifact(currentState);

        rec.addProperty(EXECUTION_UNIT, buffer.readInt());
        rec.addProperty(SIGNAL_ID, buffer.readInt());
        rec.addProperty(ADDRESS, buffer.readInt());
        rec.addProperty(SIGNAL_SIZE, buffer.readInt());
        // int sig = buffer.readInt();
        final String sigName = getStringFromBuffer();
        if (sigName.length() == 0) {
            rec.setMessage("");
            // send.setSignal(getStringValue(sig));
            // rec.addProperty(DATA, getStringValue(buffer.readInt()));
            rec.addProperty(DATA, getStringFromBuffer());
        } else {
            // rec.setMessage(getStringValue(sig));
            rec.setMessage(sigName);
            // rec.addProperty(DATA, getStringValue(buffer.readInt()));
            rec.addProperty(DATA, getStringFromBuffer());
        }
        rec.addProperty(REFNR, buffer.readInt());
        return rec;
    }

    private ILogEvent createOSESend(final long ts) throws IOException {
        final GenericSendEvent send = new GenericSendEvent(ts);
        send.setTypeName("Send");
        if (version > 8) {
            send.addProperty(TICK, buffer.readInt());
            send.addProperty(NTICK, buffer.readInt());
        }

        // event.setDeliveryTime(receivedAt);

        int senderId = buffer.readInt();
        // int senderNameidx = buffer.readInt();
        send.setSender(getTaskFromId(senderId));

        int receiverId = buffer.readInt();
        // int receiverNameidx = buffer.readInt();
        send.setRecipent(getTaskFromId(receiverId));
        // event.setRecipent(senderId);
        // event.setReceiverInstance();
        send.setLogFile(fileObject);

        send.addProperty(EXECUTION_UNIT, buffer.readInt());
        send.addProperty(SIGNAL_ID, buffer.readInt());
        send.addProperty(ADDRESS, buffer.readInt());
        send.addProperty(SIGNAL_SIZE, buffer.readInt());
        // int sig = buffer.readInt();
        final String sigName = getStringFromBuffer();
        // if (sig == -1) {
        if (sigName.length() == 0) {
            send.setMessage("");
            // send.addProperty(DATA, getStringValue(buffer.readInt()));
            send.addProperty(DATA, getStringFromBuffer());
        } else {
            send.setMessage(sigName);
            send.addProperty(DATA, getStringFromBuffer());
        }
        send.addProperty(REFNR, buffer.readInt());
        return send;
    }

    private ILogEvent createUserEvent(final long ts, final Struct struct)
            throws IOException {
        GenericLogEvent event = new GenericLogEvent();
        String typeName = struct.getName();
        event.setTypeName(typeName);
        event.setTs(ts);
        Collection<Field> fields = struct.getFields();
        for (Field field : fields) {
            int typeId = field.getIdType();
            Object value = null;
            if (field.isNullTerminated()) {
                if (typeId == Srl.t_char.getId()) {
                    // int stringIndex = this.buffer.readInt();
                    // value = this.getStringValue(stringIndex);
                    value = getStringFromBuffer();
                } else {
                    throw new UnsupportedOperationException(
                            "Only null terminated 'char' sequences are currently supported, typeid  is "
                                    + typeId);
                }
            } else if (field.getNumElements() == 1) {
                Srl srlType = Srl.valueOf(typeId);
                switch (srlType) {
                case t_srl_I32:
                    long i32 = this.buffer.readLong();
                    value = Long.valueOf(i32);
                    break;
                case t_srl_U32:
                    long u32 = this.buffer.readLong();
                    value = Long.valueOf(u32);
                    break;
                default:
                    throw new UnsupportedOperationException(
                            "Unsupported type: " + typeId);
                }

            } else {
                throw new UnsupportedOperationException(
                        "Arrays are currently not supported, type is " + typeId);
            }

            event.addProperty(field.getName(), value);

        }
        event.setLogFile(fileObject);
        return event;
    }

    private ILogEvent createTaskRelease(final long ts) throws IOException {
        final GenericTaskRelease event = new GenericTaskRelease(ts);
        final int taskId = buffer.readInt();
        final IArtifact process = getTaskFromId(Integer.valueOf(taskId));
        if (process == null) {
            throw new IllegalStateException(NO_SUCH_TASK_TASK_ID + taskId);
        }
        event.setTaskId(process);
        event.setLogFile(fileObject);

        final long timeBudget = buffer.readLong();
        event.setTimeBudget(timeBudget);

        return event;
    }

    private ILogEvent createTaskComplete(final long ts) throws IOException {
        final GenericTaskCompletion event = new GenericTaskCompletion(ts);
        final int taskId = buffer.readInt();
        final long remainingTime = buffer.readLong();
        final IArtifact process = getTaskFromId(Integer.valueOf(taskId));
        if (process == null) {
            throw new IllegalStateException(NO_SUCH_TASK_TASK_ID + taskId);
        }
        event.setTaskId(process);
        event.setRemainingTime(remainingTime);
        event.setLogFile(fileObject);

        return event;
    }

    private ILogEvent createFunctionExit(final long ts) throws IOException {
        // int stringIndex = buffer.readInt();
        // final String functionName = getStringValue(stringIndex);
        final String functionName = getStringFromBuffer();
        GenericFunctionExit functionExit = new GenericFunctionExit(functionName);
        functionExit.setTs(ts);
        functionExit.setLogFile(this.fileObject);
        long endTime = buffer.readLong();
        functionExit.setEndTime(endTime);
        getFunctionSequenceBuilder().addFunctionExit(functionExit);
        return functionExit;
    }

    private ILogEvent createFunctionEnter(final long ts) throws IOException {
        // int stringIndex = buffer.readInt();
        // final String functionName = getStringValue(stringIndex);
        final String functionName = getStringFromBuffer();
        GenericFunctionEnter functionEnter = new GenericFunctionEnter(
                functionName);
        functionEnter.setTs(ts);
        functionEnter.setLogFile(this.fileObject);
        long endTime = buffer.readLong();
        functionEnter.setEndTime(endTime);
        getFunctionSequenceBuilder().addFunctionEnter(functionEnter);
        return functionEnter;
    }

    private ILogEvent createTaskSwitchEvent(final long ts) throws IOException {
        final GenericTaskSwitchEvent event = new GenericTaskSwitchEvent(ts);
        final int outPid = buffer.readInt();

        if (version < 7) {
            // int outName
            int size = buffer.readInt();
            buffer.read(new byte[size]);
        }
        final int inPid = buffer.readInt();

        if (version < 7) {
            // int inName
            int size = buffer.readInt();
            buffer.read(new byte[size]);
        }
        int prio = 0;

        // Read prio
        if (version > 1) {
            prio = buffer.readInt();
        }

        final GenericTask in = getTaskFromId(inPid);
        final GenericTask out = getTaskFromId(outPid);

        // event.setOutProcessId(out.getTaskId());
        event.setResourceUserOut(out);
        // event.setInProcessId(in.getTaskId());
        event.setResourceUserIn(in);
        event.setPriority(prio);

        event.setLogFile(fileObject);
        currentTask = in;
        return event;
    }

    private GenericTask createTask(final int pid, final String inName,
            final int priority) {
        GenericTask task;
        if (!taskMap.containsKey(pid)) {
            task = resolve(new GenericTask(pid, inName, priority));
            task.setLogFile(fileObject);
            task = importContext.resolveArtifact(task);
            task.setParent(getSequence());
            taskMap.put(pid, task);
        } else {
            task = importContext
                    .resolveArtifact((GenericTask) taskMap.get(pid));
        }
        return task;
    }

    private GenericTask getTaskFromId(final int id) {
        GenericTask task;
        if (!taskMap.containsKey(id)) {
            throw new ImportException("ERROR, failed to get task with id:" + id);
        } else {
            task = importContext.resolveArtifact((GenericTask) taskMap.get(id));
        }
        return task;
    }

    private <T extends IArtifact> T resolve(final T artifact) {
        return importContext.resolveArtifact(artifact);
    }

    //private final ByteBuffer sndBuf = ByteBuffer.allocate(12 * 4);

    private ILogEvent createRoseSendEvent(final long high) throws IOException {
        final GenericSendEvent event = new GenericSendEvent(high);
        //sndBuf.position(0);
        // sndBuf.rewind();
        //buffer.read(sndBuf.array(), 0, 48);
        final int senderActorStrIx = buffer.readInt();
        final long senderActorInstance = buffer.readInt() & BBBinImporter.MASK;
        // final long senderReplicationIndex
        // getUnsignedInt();
        buffer.readInt();
        final int recActorStrix = buffer.readInt();
        final long recevicerActorInstance = buffer.readInt()
                & BBBinImporter.MASK;
        // getUnsignedInt();
        // final long receiverReplicationIndex
        // getUnsignedInt();
        buffer.readInt();
        // final String signal = getStringValue(sndBuf.getInt());
        final String signal = getStringFromBuffer();
        // final String portStr = getStringValue(sndBuf.getInt());
        final String portStr = getStringFromBuffer();
        final int portIndex = buffer.readInt();
        // buffer.readInt();
        final long msgAddr = buffer.readInt() & BBBinImporter.MASK;
        // getUnsignedInt();

        // final long receivedAt = convert(sndBuf.getLong());
        final long receivedAt = buffer.readLong();
        // buffer.readLong();

        event.setDeliveryTime(receivedAt);
        if (version > 1) {
            event.setSender(fetchStatemachine(new ActorId(senderActorStrIx,
                    senderActorStrIx)));
        } else {
            event.setSender(fetchStatemachine(ActorId.valueOf(senderActorStrIx,
                    senderActorInstance)));
        }
        event.setMessage(signal);
        if (version > 1) {
            event.setRecipent(fetchStatemachine(new ActorId(recActorStrix,
                    recActorStrix)));
        } else {
            event.setRecipent(fetchStatemachine(ActorId.valueOf(recActorStrix,
                    recevicerActorInstance)));
        }
        event.setReceiverInstance(recevicerActorInstance);
        event.addProperty("SenderInstance", senderActorInstance);
        event.addProperty(PORT, portStr);
        event.addProperty(PORT_INDEX, portIndex);
        event.addProperty(MESSAGE_ADDRESS, msgAddr);
        event.setLogFile(fileObject);

        return event;
    }

    //private final ByteBuffer recBuf = ByteBuffer.allocate(12 * 4);

    private static final long MASK = 0xFFFFFFFFL;

    private ILogEvent createRoseRecEvent(final long ts) throws IOException {
        final UMLReceiveEvent event = new UMLReceiveEvent(ts);

        //recBuf.position(0);
        //buffer.read(recBuf.array(), 0, 48);
        final int receiverStrix = buffer.readInt();
        final long receiverActorInstance = buffer.readInt() & BBBinImporter.MASK;
        buffer.readInt();

        final int stateStrix = buffer.readInt();

        // final String receivedSignal = getStringValue(recBuf.getInt());
        final String receivedSignal = getStringFromBuffer();
        // final String portName = getStringValue(recBuf.getInt());
        final String portName = getStringFromBuffer();
        final long portIndex = buffer.readInt() & BBBinImporter.MASK;
        final long messagePointer = buffer.readInt() & BBBinImporter.MASK;
        final long finish = buffer.readLong();

        final long sentAt = buffer.readLong();

        event.setSentAt(sentAt);
        event.setResourceUser(getCurrentTask());

        final ActorId actorId;
        if (version > 1) {
            actorId = new ActorId(receiverStrix, receiverStrix);
        } else {
            actorId = ActorId.valueOf(receiverStrix, receiverActorInstance);
        }
        event.setReceiver(fetchStatemachine(actorId));

        event.setMessage(receivedSignal);
        event.addProperty("ReceiveInstance", receiverActorInstance);
        event.addProperty(PORT, portName);
        event.addProperty(PORT_INDEX, (int) portIndex);
        event.addProperty(MESSAGE_ADDRESS, messagePointer);

        event.setFinish(finish);
        event.setLogFile(fileObject);
        final ActorId stateId;
        if (version > 1) {
            stateId = new ActorId(stateStrix, stateStrix);
        } else {
            stateId = ActorId.valueOf(stateStrix, receiverActorInstance);
        }
        GenericState currentState = getCurrentState(event.getReceiver());
        currentState.setParent(event.getReceiver());
        currentState.setLogFile(fileObject);
        currentState = importContext.resolveArtifact(currentState);
        event.setCurrentState(currentState);

        GenericState state = states.get(stateId);
        if (state == null) {
            state = new GenericState(UNKNOWN);
            states.put(stateId.copy(), state);
        }
        state.setLogFile(fileObject);
        state.setParent(event.getReceiver());
        state = importContext.resolveArtifact(state);
        event.setStateIn(state);
        currentStates.put(event.getReceiver(), state);
        event.setTransition(getTransition(event));

        return event;
    }

    private IArtifact getCurrentTask() {
        if (currentTask == null) {
            currentTask = GenericTask.createUnknown(fileObject);
            currentTask.setTypeName(this.taskTypeName);
            currentTask = importContext.resolveArtifact(currentTask);
        }
        return currentTask;
    }

    private final Map<GenericStateTransition, GenericStateTransition> cache = new HashMap<GenericStateTransition, GenericStateTransition>();

    private Map<Integer, Struct> userStructMap = new HashMap<Integer, Struct>();

    private GenericStateTransition getTransition(final UMLReceiveEvent event) {
        GenericStateTransition key = new GenericStateTransition(event
                .getCurrentState(), event.getStateIn());
        key = importContext.resolveArtifact(key);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        cache.put(key, key);
        key.setLogFile(fileObject);
        return key;
    }

    private GenericState getCurrentState(final ISequenceMember id) {
        final GenericState state = currentStates.get(id);
        if (state == null) {
            return new GenericState(IState.UNKNOWN);
        }
        return state;
    }

    // private String getStringValue(final int index) {
    // if (index >= stringDb.size() || index < 0) {
    // throw new ImportException(
    // "ERROR, failed to get string value for string with index: "
    // + index + ", nr of strings in String DB are: "
    // + stringDb.size());
    //
    // }
    // final String value = stringDb.get(index);
    // return value;
    // }

    private String getStringFromBuffer() throws IOException {
        final int lenght = buffer.readInt();
        final byte[] strBuff = new byte[lenght];
        buffer.read(strBuff, 0, lenght);
        return new String(strBuff);
    }

    /**
     * Fetches the statemachine by name, creating a new one if none already
     * exists.
     * 
     * @param actorId
     *            the actor id
     * 
     * 
     * @return the rose state machine, guaranteed not to be null
     */
    private GenericStateMachine fetchStatemachine(final ActorId actorId) {
        GenericStateMachine machine = stateMachineMap.get(actorId);
        if (machine == null) {
            // machine = new GenericStateMachine(getStringValue(actorId.key)
            // + BBBinImporter.SEPARATOR + actorId.instance);
            machine = new GenericStateMachine(
                    "Actor name is not supported in this version"
                            + BBBinImporter.SEPARATOR + actorId.instance);
            machine.setParent(getSequence());
            machine.setLogFile(fileObject);
            stateMachineMap.put(actorId.copy(), machine);

        }
        return importContext.resolveArtifact(machine);
    }

    private ISequence getSequence() {
        if (sequence == null) {
            sequence = new GenericSequence();
            sequence.setLogFile(fileObject);
            sequence
                    .setName("Sequence [" + fileObject.getFileName() + RBRACKET);
            sequence = importContext.resolveArtifact(sequence);
        }
        return sequence;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#size()
     */
    public int size() {
        return nrOfEvents;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException {
        if (buffer != null) {
            buffer.close();
        }

    }

    class FileIterator implements Iterator<ILogEvent> {
        private int index;

        public boolean hasNext() {
            return index < nrOfEvents;
        }

        public ILogEvent next() {
            try {
                ILogEvent readEvent = null;
                while (readEvent == null && hasNext()) {
                    readEvent = readEvent();
                    ++index;
                }
                if (readEvent == null) {
                    readEvent = new ILogEvent() {

                        public String getDate() {
                            return "";
                        }

                        public LogFile getLogFile() {
                            return LogFile.DEFAULT;
                        }

                        public long getTs() {
                            return Long.MAX_VALUE;
                        }

                        public void setLogFile(final LogFile fileObject) {}

                        public void setTs(final long ts) {}

                        public Long getTimeReference() {
                            return Long.MAX_VALUE;
                        }

                        public int compareTo(final Object arg0) {
                            throw new UnsupportedOperationException();
                        }

                        public IType getType() {
                            throw new UnsupportedOperationException();
                        }

                        public List<SEProperty> getZPropertyAnnotations() {
                            throw new UnsupportedOperationException();
                        }

                        public Collection<IArtifact> referencedArtifacts() {
                            return new ArrayList<IArtifact>();
                        }

                        public void substitute(final IArtifact oldArtifact,
                                final IArtifact newArtifact) {

                        }
                    };
                }
                return readEvent;
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    static class ActorId {
        private int key;

        private long instance;

        private static ActorId single = new ActorId(0, 0);

        public ActorId(final int key, final long instance) {
            this.key = key;
            this.instance = instance;
        }

        public ActorId copy() {
            return new ActorId(key, instance);
        }

        static ActorId valueOf(final int strix, final long instance) {
            ActorId.single.instance = instance;
            ActorId.single.key = strix;
            return ActorId.single;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            long result = 1;
            result = prime * result + instance;
            result = prime * result + key;
            return (int) result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ActorId other = (ActorId) obj;
            if (instance != other.instance) {
                return false;
            }
            if (key != other.key) {
                return false;
            }
            return true;
        }

    }

    private final class TaskStats {

        private long remainingTicksCount;

        private long remainingTicksMin;

        private long remainingTicksMax;

        private float remainingTicksAvg;

        public TaskStats(final DataInputStream buffer) throws IOException {
            remainingTicksCount = buffer.readLong();
            remainingTicksMin = buffer.readLong();
            remainingTicksMax = buffer.readLong();
            remainingTicksAvg = buffer.readFloat();
        }

        long getRemainingTicksCount() {
            return remainingTicksCount;
        }

        long getRemainingTicksMin() {
            return remainingTicksMin;
        }

        long getRemainingTicksMax() {
            return remainingTicksMax;
        }

        float getRemainingTicksAvg() {
            return remainingTicksAvg;
        }
    }

    private final class FunctionSequenceBuilder {
        private final ImportContext importContext;

        private Map<String, ISequenceMember> sequenceMemberMap = new HashMap<String, ISequenceMember>();

        private Stack<ISequenceMember> functionStack = new Stack<ISequenceMember>();

        private ISequence sequence;

        private IActivitySetter lastActivity;

        public FunctionSequenceBuilder(final ImportContext importContext) {
            if (importContext == null) {
                throw new IllegalArgumentException(
                        "importContext must not be null");
            }
            this.importContext = importContext;
            if (fileObject == null) {
                throw new IllegalArgumentException(
                        "fileObject must not be null");
            }

            sequence = new GenericSequence();
            sequence.setName("Function Calls");
            sequence.setLogFile(fileObject);
            sequence.setName("FunctionTrace[" + fileObject.getFileName()
                    + RBRACKET);
            sequence.setRoot(true);
            sequence = importContext.resolveArtifact(sequence);

            ISequenceMember unknownFunction = new Function();
            unknownFunction.setLogFile(fileObject);
            unknownFunction.setName(UNKNOWN);
            unknownFunction.setParent(sequence);
            unknownFunction.setRoot(false);
            unknownFunction = importContext.resolveArtifact(unknownFunction);
            functionStack.push(unknownFunction);
        }

        private ISequenceMember getFunctionByName(final String name) {
            ISequenceMember sequenceMember = sequenceMemberMap.get(name);
            if (sequenceMember == null) {
                sequenceMember = new Function();
                sequenceMember.setLogFile(fileObject);
                sequenceMember.setName(name);
                sequenceMember.setParent(sequence);
                sequenceMember.setRoot(false);
                sequenceMember = importContext.resolveArtifact(sequenceMember);

                sequenceMemberMap.put(name, sequenceMember);
            }
            return sequenceMember;
        }

        public void addFunctionExit(final GenericFunctionExit functionExit) {
            ISequenceMember sender = functionStack.pop();
            ISequenceMember recepient = functionStack.peek();
            functionExit.setRecipent(recepient);
            functionExit.setSender(sender);
            updateLastActivity(functionExit);
            lastActivity = functionExit;
        }

        public void addFunctionEnter(final GenericFunctionEnter functionEnter) {
            ISequenceMember recipent = getFunctionByName(functionEnter
                    .getFunctionName());
            functionEnter.setRecipent(recipent);
            ISequenceMember sender = functionStack.peek();
            functionEnter.setSender(sender);
            functionStack.push(recipent);

            updateLastActivity(functionEnter);
            lastActivity = functionEnter;

        }

        private void updateLastActivity(final ISequenceMessage message) {
            if (lastActivity != null) {
                // lastActivity.setEndTime(message.getTs());
            }
        }
    }

    private FunctionSequenceBuilder getFunctionSequenceBuilder() {
        if (functionSequenceBuilder == null) {
            functionSequenceBuilder = new FunctionSequenceBuilder(importContext);
        }
        return functionSequenceBuilder;
    }
}

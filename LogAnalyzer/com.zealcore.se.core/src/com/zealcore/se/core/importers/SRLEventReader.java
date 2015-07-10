package com.zealcore.se.core.importers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.zealcore.se.core.format.FieldDescription;
import com.zealcore.se.core.format.FieldDescription.FieldType;
import com.zealcore.se.core.format.FieldValues;
import com.zealcore.se.core.format.FunctionEnterEventInfo;
import com.zealcore.se.core.format.FunctionExitEventInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.RoseReceiveEventInfo;
import com.zealcore.se.core.format.RoseSendEventInfo;
import com.zealcore.se.core.format.StateArtifactInfo;
import com.zealcore.se.core.format.StateMachineArtifactInfo;
import com.zealcore.se.core.format.TaskArtifactInfo;
import com.zealcore.se.core.format.TaskArtifactInfo.TaskClass;
import com.zealcore.se.core.format.TaskCompletionEventInfo;
import com.zealcore.se.core.format.TaskReleaseEventInfo;
import com.zealcore.se.core.format.TaskSwitchEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.srl.offline.Blackbox;
import com.zealcore.srl.offline.CircularMessage;
import com.zealcore.srl.offline.Field;
import com.zealcore.srl.offline.IPointer;
import com.zealcore.srl.offline.Logger;
import com.zealcore.srl.offline.MentorIndex;
import com.zealcore.srl.offline.Pointer;
import com.zealcore.srl.offline.Struct;
import com.zealcore.srl.offline.Type;
import com.zealcore.srl.offline.Type.Srl;
import com.zealcore.srl.offline.TypedCircularMessage;
import com.zealcore.srl.offline.TypedLinearMessage;
import com.zealcore.srl.offline.UmlState;
import com.zealcore.srl.offline.UmlStateMachine;

public class SRLEventReader {

    class UmlSend {
        private final long senderClassId;

        private final long senderInstanceId;

        private final long receiverClassId;

        private final long receiverInstanceId;

        private final long signalId;

        private final long signalInstanceId;

        long getSenderClassId() {
            return senderClassId;
        }

        long getSenderInstanceId() {
            return senderInstanceId;
        }

        long getReceiverClassId() {
            return receiverClassId;
        }

        long getReceiverInstanceId() {
            return receiverInstanceId;
        }

        long getSignalId() {
            return signalId;
        }

        UmlSend(final TypedCircularMessage message) {
            senderClassId = (Long) message.getValue("senderClassId");
            senderInstanceId = (Long) message.getValue("senderInstanceId");
            receiverClassId = (Long) message.getValue(RECEIVER_CLASS_ID);
            receiverInstanceId = (Long) message.getValue(RECEIVER_INSTANCE_ID);
            signalId = (Long) message.getValue(SIGNAL_ID);
            Object value = message.getValue(SIGNAL_INSTANCE_ID);
            if (value == null) {
                signalInstanceId = 0;
            } else {
                signalInstanceId = (Long) value;
            }
        }

        public Long getSignalInstanceId() {
            return signalInstanceId;
        }
    }

    class UmlReceive {
        private final long receiverClassId;

        private final Long receiverInstanceId;

        private final long signalId;

        private final long signalInstanceId;

        private final long nextStateId;

        private final long finishHi;

        private final long finishLo;

        UmlReceive(final TypedCircularMessage message) {
            receiverClassId = (Long) message.getValue(RECEIVER_CLASS_ID);
            receiverInstanceId = (Long) message.getValue(RECEIVER_INSTANCE_ID);
            signalId = (Long) message.getValue(SIGNAL_ID);
            nextStateId = (Long) message.getValue("nextStateId");
            finishHi = (Long) message.getValue(FINISH_HI);
            finishLo = (Long) message.getValue(FINISH_LO);
            Object value = message.getValue(SIGNAL_INSTANCE_ID);
            if (value == null) {
                signalInstanceId = 0;
            } else {
                signalInstanceId = (Long) value;
            }
        }

        public Long getSignalInstanceId() {
            return signalInstanceId;
        }

        long getReceiverClassId() {
            return receiverClassId;
        }

        int getReceiverInstanceId() {
            return receiverInstanceId.intValue();
        }

        long getSignalId() {
            return signalId;
        }

        long getNextStateId() {
            return nextStateId;
        }

        long getFinishHi() {
            return finishHi;
        }

        long getFinishLo() {
            return finishLo;
        }
    }

    private static final String SIGNAL_ID = "signalId";

    public static final String SIGNAL_INSTANCE_ID = "signalInstanceId";

    private static final String RECEIVER_INSTANCE_ID = "receiverInstanceId";

    private static final String RECEIVER_CLASS_ID = "receiverClassId";

    private static final String FINISH_LO = "finishLo";

    private static final String FINISH_HI = "finishHi";

    private static final String REMAINING_TIME = "remainingTime";

    private static final String UNKNOWN_TASK = "Unknown";

    private static final String FN_NAME = "name";

    private static final String FN_INT_ID = "interruptId";

    private static final String FN_ID = "id";

    private static final String FN_TASK_ID = "taskId";

    public static final String FN_MSG_ADDR = "msgAddr";

    private static final String FN_PORT_INDEX = "portIndex";

    private static final String FN_PORT = "port";

    private static final String FN_SIGNAL = "signalDescriptors";

    public static final String FN_STATE = "state";

    public static final String FN_RECEIVER_ACTOR_INSTANCE = "receiverActorInstance";

    private static final String FN_RECEIVER_ACTOR = "receiverActor";

    public static final String FN_SENDER_ACTOR_INSTANCE = "senderActorInstance";

    private static final String FN_SENDER_REPLICATION_INDEX = "senderReplicationIndex";

    private static final String FN_RECEIVER_REPLICATION_INDEX = "receiverReplicationIndex";

    private static final String FN_SENDER_ACTOR = "senderActor";

    private static final String FN_RECEIVER_ACTOR_REPLICATION_INDEX = SRLEventReader.FN_RECEIVER_REPLICATION_INDEX;

    public static final int MAGIC_CONSTANT = 0xBADBABE;

    private static final long NANO_PER_SEC = 1000000000;

    private static final long NANO_PER_MICRO = 1000;

    private static final String FUNCTION_FIELD = "function";

    private static int unknownIdCnt;

    private Map<Long, Task> taskMap;

    private final Map<Long, Long> sentTsByMsgAddr = new HashMap<Long, Long>();

    private final HashMap<String, UmlStateMachine> stateMachineMap = new HashMap<String, UmlStateMachine>();

    private Map<Long, Long> interruptMap;

    private String lastTaskName = SRLEventReader.UNKNOWN_TASK;

    private int lastTaskId;

    private int lastInterruptPrio;

    // private DataOutputStream bin;

    // private int nrOfWrittenEvents;

    private double ticksPerNano;

    private Map<Long, TypedLinearMessage> taskStatsMap = new HashMap<Long, TypedLinearMessage>();

    private Blackbox blackbox;

    private Map<IPointer, TypedLinearMessage> functionMap = new HashMap<IPointer, TypedLinearMessage>();;

    private static boolean isFirstRun;

    private List<Long> functionTimestamps = new ArrayList<Long>();

    private Set<Struct> userStructs = new HashSet<Struct>();

    private EventReaderClient client;

    private boolean dummyRun;

    public enum Message {
        ROSE_SEND(1008, 2), ROSE_RECEIVE(1009, 1), TASK(1010), TASK_STATS(1018), TASK_SWITCH(
                1011, 3), INTERRUPT(1016), INT_BEGIN(1014, 4), INT_END(1015, 5), TASK_INSTANCE(
                -1, 6), TASK_RELEASE(1012, 7), TASK_COMPLETE(1013, 8), TIME_REF(
                1017, 9), UML_SEND(1020), UML_RECEIVE(1021), UML_EXAMPLE_DATA(
                1004), FUNCTION(1022), FUNCTION_ENTER(1023, 10), FUNCTION_EXIT(
                1024, 11);

        private int srlTypeId;

        private int binTypeId;

        private Message(final int srlTypeId) {
            this.srlTypeId = srlTypeId;
            binTypeId = -1;
        }

        private Message(final int srlTypeId, final int binTypeId) {
            this.srlTypeId = srlTypeId;
            this.binTypeId = binTypeId;
        }

        public int srlTypeId() {
            return srlTypeId;
        }

        public int binTypeId() {
            return binTypeId;
        }
    }

    public SRLEventReader(EventReaderClient client) {
        this.client = client;
    }

    public void read(Blackbox blackbox, boolean dummyRun) {
        this.blackbox = blackbox;
        this.dummyRun = dummyRun;
        taskMap = new LinkedHashMap<Long, Task>();
        try {
            reset();
            preProcessLinear(blackbox.getTypedLinearMessages());
            correctMessageIds(blackbox.getTypedCircularMessages());
            preProcessCircular(blackbox.getTypedCircularMessages());
            preProcessStateMachines();
            preProcessTimeRef(blackbox.getTypedCircularMessages());

            if (dummyRun) {
                return;
            }
            // writeTaskStatsTable();
            getEvents(blackbox.getTypedCircularMessages());
            getTasks();
            getStateMachineMap();
        } finally {
            this.blackbox = null;
        }
    }

    private void getUserStructs() {
        for (Struct struct : userStructs) {
            List<FieldDescription> fieldDescs = new ArrayList<FieldDescription>();

            List<Field> fields = struct.getFields();
            for (Field field : fields) {
                Srl srlType = Srl.valueOf(field.getType().getId());
                switch (srlType) {
                case t_srl_I32:
                    fieldDescs.add(new FieldDescription(field.getName(),
                            FieldType.INT64));
                    break;
                case t_srl_U32:
                    fieldDescs.add(new FieldDescription(field.getName(),
                            FieldType.INT64));
                    break;
                default:
                }
            }
            client.typeDescRead(new TypeDescription(struct.getId(), struct
                    .getName(), fieldDescs));
        }
    }

    /**
     * A hack to fix the problem with swapped ids in SRL in the mentor delivery
     * 08032?
     * 
     * @param typedCircularMessages
     */
    private void correctMessageIds(
            final Collection<TypedCircularMessage> typedCircularMessages) {
        for (TypedCircularMessage typedCircularMessage : typedCircularMessages) {
            Struct struct = typedCircularMessage.getStruct();

            /*
             * Detect id - name mismatch
             */
            if (struct.getId() == Message.UML_SEND.srlTypeId()) {
                if (struct.getName().equals("srl_UmlSignalReceive")) {
                    struct.setId(Message.UML_RECEIVE.srlTypeId());
                }
            } else if (struct.getId() == Message.UML_RECEIVE.srlTypeId()) {
                if (struct.getName().equals("srl_UmlSignalSend")) {
                    struct.setId(Message.UML_SEND.srlTypeId());
                }
            }
        }
    }

    private void getEvents(final Collection<TypedCircularMessage> events) {
        final List<TypedCircularMessage> cmList = new ArrayList<TypedCircularMessage>(
                events);
        sort(cmList);
        boolean isFirstFunction = true;
        int functionIndex = 0;
        for (int i = 0; i < cmList.size(); i++) {
            final TypedCircularMessage message = cmList.get(i);
            final long ts = resolveTimeStamp(((CircularMessage) message
                    .getMessage()).getTs());
            final int id = message.getStruct().getId();
            if (dummyRun && (id >= 0)) {
                continue;
            }
            if (id == Message.ROSE_RECEIVE.srlTypeId()) {
                getRoseReceive(message, ts);
            } else if (id == Message.ROSE_SEND.srlTypeId()) {
                final long receivedAt = findReceiveTime(message, cmList, i);
                getRoseSend(message, receivedAt, ts);
            } else if (id == Message.TASK_SWITCH.srlTypeId()) {
                getTaskSwitch(message, ts);
            } else if (id == Message.INT_BEGIN.srlTypeId()) {
                handleInterruptBegin(message, ts);
            } else if (id == Message.INT_END.srlTypeId()) {
                handleInterruptEnd(message, ts);
            } else if (id == Message.TASK_COMPLETE.srlTypeId()) {
                if (getTaskCompletion(message, ts)) {}
            } else if (id == Message.TASK_RELEASE.srlTypeId()) {
                if (getTaskRelease(message, cmList, i, ts)) {}
            } else if (id == Message.TIME_REF.srlTypeId()) {
                // nothing todo
            } else if (id == Message.UML_SEND.srlTypeId()) {
                final long receivedAt = findReceiveTime(message, cmList, i);
                getUMLSend(message, receivedAt, ts);
            } else if (id == Message.UML_RECEIVE.srlTypeId()) {
                getUMLreceive(message, ts);
            } else if (id == Message.FUNCTION_ENTER.srlTypeId()) {
                long endTime = -1;
                if (isFirstRun) {
                    if (!isFirstFunction) {
                        functionTimestamps.add(ts);
                    } else {
                        isFirstFunction = false;
                    }
                } else {
                    if (functionIndex < functionTimestamps.size()) {
                        endTime = functionTimestamps.get(functionIndex++);
                    }
                }
                getFunctionEnter(message, endTime, ts);
            } else if (id == Message.FUNCTION_EXIT.srlTypeId()) {
                long endTime = -1;
                if (isFirstRun) {
                    if (!isFirstFunction) {
                        functionTimestamps.add(ts);
                    } else {
                        isFirstFunction = false;
                    }
                } else {
                    if (functionIndex < functionTimestamps.size()) {
                        endTime = functionTimestamps.get(functionIndex++);
                    }
                }
                getFunctionExit(message, endTime, ts);
            } else if (id == Message.UML_EXAMPLE_DATA.srlTypeId()) {

            } else if (id < 0) {
                getUserEvent(message, ts);
            } else {
                throw new UnsupportedOperationException("Can not handle id = "
                        + id);
            }
        }
    }

    private void getUserEvent(TypedCircularMessage message, long ts) {
        Struct struct = message.getStruct();
        userStructs.add(struct);

        int structId = struct.getId();
        FieldValues values = new FieldValues();
        List<Field> fields = struct.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object value = message.getValue(fieldName);
            Type type = field.getType();
            if (field.isNullTerminated()) {
                if (type.getId() == Type.Srl.t_char.getId()) {
                    String s = (String) value;
                    values.addStringValue(s);
                } else {
                    throw new UnsupportedOperationException(
                            "Only null terminated 'char' sequences are currently supported, type is "
                                    + type);
                }
            } else if (field.getNumElements() == 1) {
                Srl srlType = Srl.valueOf(type.getId());
                switch (srlType) {
                case t_srl_I32:
                case t_srl_U32:
                    Long i = (Long) value;
                    values.addLongValue(i);
                    break;
                default:
                    throw new UnsupportedOperationException(
                            "Unsupported type: " + type);
                }

            } else {
                throw new UnsupportedOperationException(
                        "Arrays are currently not supported, type is " + type);
            }
        }
        client.eventRead(new GenericEventInfo(ts, structId, values));

    }

    private void preProcessStateMachines() {
        if (stateMachineMap == null) {
            return;
        }
    }

    private void preProcessLinear(
            final Collection<TypedLinearMessage> linearMessages) {
        interruptMap = new HashMap<Long, Long>();
        for (final TypedLinearMessage message : linearMessages) {
            if (message.getStruct().getId() == Message.TASK.srlTypeId()) {
                final Long id = (Long) message.getValue(SRLEventReader.FN_ID);
                final String name = (String) message
                        .getValue(SRLEventReader.FN_NAME);
                taskMap.put(id, new Task(name));
            } else if (message.getStruct().getId() == Message.INTERRUPT
                    .srlTypeId()) {
                // hack to convert interrupts to tasks
                // negated id of interrupt used as taskid to avoid
                // collisions
                final Long id = -((Long) message.getValue(SRLEventReader.FN_ID));
                final String name = (String) message
                        .getValue(SRLEventReader.FN_NAME);
                taskMap.put(id, new Task(name));
                final Long prio = ((Long) message.getValue("prio"));
                interruptMap.put(id, prio);
            } else if (message.getStruct().getId() == Message.TASK_STATS
                    .srlTypeId()) {
                final Long id = (Long) message
                        .getValue(SRLEventReader.FN_TASK_ID);
                taskStatsMap.put(id, message);
            } else if (message.getStruct().getId() == Message.FUNCTION
                    .srlTypeId()) {
                final IPointer id = (IPointer) message
                        .getValue(SRLEventReader.FUNCTION_FIELD);
                functionMap.put(id, message);
            }
        }
    }

    /**
     * Extracts all strings (via a dry run of writeEvents) and populates the
     * strings list also sets the count of number of events. (The number of
     * events are the number of written events, it does not necessarily mean the
     * size of the SRL log. This is because not all events can be translated to
     * the BinaryRoseLog
     * 
     * @param copy
     * @throws IOException
     */
    private void preProcessCircular(
            final Collection<TypedCircularMessage> circularMessages) {
        isFirstRun = true;
        preProcessTimeRef(circularMessages);
        if (dummyRun) {
            getEvents(circularMessages);
            getUserStructs();
        }
        isFirstRun = false;
        reset();
    }

    private void preProcessTimeRef(
            final Collection<TypedCircularMessage> typedCircularMessages) {
        ticksPerNano = -1;
        long timeClock1 = -1;
        long timeClock2 = -1;
        long timeTicks1 = -1;
        long timeTicks2 = -1;
        long timeClocksPerSec = -1;
        for (final TypedCircularMessage msg : typedCircularMessages) {
            if (msg.getStruct().getId() != Message.TIME_REF.srlTypeId()) {
                continue;
            }
            final Long secondsSince1970 = (Long) msg
                    .getValue("secondsSince1970");
            if (secondsSince1970 != null) {
                // TODO: save timebase in header?
            }
            final Long clock = (Long) msg.getValue("clock");
            if (clock == null) {
                continue;
            }
            final Long clocksPerSec = (Long) msg.getValue("clocksPerSec");
            if (clocksPerSec == null || clocksPerSec < 1) {
                continue;
            }
            if (timeClock1 == -1) {
                timeClock1 = clock;
                timeTicks1 = ((CircularMessage) msg.getMessage()).getTs();
                timeClock2 = -1;
                continue;
            }
            if (timeClock2 == -1) {
                timeClock2 = clock;
                timeTicks2 = ((CircularMessage) msg.getMessage()).getTs();
                timeClocksPerSec = clocksPerSec;
                break;
            }
        }
        if (timeClock1 == -1 || timeClock2 == -1) {
            return;
        }
        if (timeTicks1 == -1 || timeTicks2 == -1) {
            return;
        }
        if (timeClock1 > timeClock2) {
            long temp = timeClock2;
            timeClock2 = timeClock1;
            timeClock1 = temp;
            temp = timeTicks2;
            timeTicks2 = timeTicks1;
            timeTicks1 = temp;
        }
        if (timeClock1 >= timeClock2 || timeTicks1 >= timeTicks2) {
            return;
        }
        final double temp1 = timeClocksPerSec * (timeTicks2 - timeTicks1);
        final double temp2 = NANO_PER_SEC * (timeClock2 - timeClock1);
        ticksPerNano = temp1 / temp2;
    }

    private void handleInterruptEnd(final TypedCircularMessage message,
            final long ts) {
        Long taskId = (Long) getValue(message, SRLEventReader.FN_INT_ID);
        int pid = taskId.intValue();
        pid = -pid;
        taskId = new Long(pid);
        getTaskFromId(taskId);
        client.eventRead(new TaskSwitchEventInfo(ts, lastTaskId, pid,
                lastInterruptPrio, EventFactory.TASK_SWITCH_EVENT_TYPE_ID,
                new FieldValues()));
    }

    private void handleInterruptBegin(final TypedCircularMessage message,
            final long ts) {
        // TODO: rewrite code so it handles nested interrupts
        // get interrupt id from message
        // get task from map
        // switch from last task to this task (write to file)
        // push task on stack
        // pop out-task from stack
        // peek in-task
        // switch from out task to in task
        final Long taskId = -((Long) getValue(message, SRLEventReader.FN_INT_ID));
        Task task = getTaskFromId(taskId);

        Long prio = interruptMap.get(taskId);
        if (prio == null) {
            prio = new Long(-777);
        } else {
            prio = -prio;
        }
        task.setPriority(prio.intValue());
        client.eventRead(new TaskSwitchEventInfo(ts, taskId.intValue(),
                lastTaskId, prio.intValue(),
                EventFactory.TASK_SWITCH_EVENT_TYPE_ID, new FieldValues()));

    }

    /*
     * private void writeTaskStatsTable() throws IOException { final
     * Set<Entry<Long, TypedLinearMessage>> taskStatsSet = taskStatsMap
     * .entrySet(); bin.writeInt(taskStatsSet.size()); for (final Entry<Long,
     * TypedLinearMessage> taskStats : taskStatsSet) {
     * bin.writeInt(taskStats.getKey().intValue()); TypedLinearMessage
     * taskStatsMessage = (TypedLinearMessage) taskStats .getValue();
     * bin.writeLong((Long) taskStatsMessage.getValue("rem_ticks_count"));
     * bin.writeLong((Long) taskStatsMessage.getValue("rem_ticks_min"));
     * bin.writeLong((Long) taskStatsMessage.getValue("rem_ticks_max"));
     * bin.writeFloat((Float) taskStatsMessage.getValue("rem_ticks_avg")); } }
     */

    private void getTasks() {
        final Set<Entry<Long, Task>> taskSet = taskMap.entrySet();
        for (final Entry<Long, Task> task : taskSet) {
            // bin.writeBoolean(task.getValue().hasExecutedDuringLog);
            FieldValues values = new FieldValues();
            /*
             * TypedLinearMessage taskStatsMessage =
             * taskStatsMap.get(task.getKey().intValue()); if (taskStatsMessage
             * == null) { values.addLongValue(-1); values.addLongValue(-1);
             * values.addLongValue(-1); values.addLongValue(-1); } else {
             * values.addLongValue((Long)
             * taskStatsMessage.getValue("rem_ticks_count"));
             * values.addLongValue((Long)
             * taskStatsMessage.getValue("rem_ticks_min"));
             * values.addLongValue((Long)
             * taskStatsMessage.getValue("rem_ticks_max"));
             * values.addLongValue((Long)
             * taskStatsMessage.getValue("rem_ticks_avg")); }
             */
            client.eventRead(new TaskArtifactInfo(task.getKey().intValue(),
                    task.getValue().getName(), TaskClass.TASK, task.getValue()
                            .getPriority(), EventFactory.TASK_EVENT_TYPE_ID,
                    values));
        }
    }

    private void getStateMachineMap() {
        if (stateMachineMap == null) {
            return;
        }
        for (final UmlStateMachine machine : stateMachineMap.values()) {
            client.eventRead(new StateMachineArtifactInfo(machine.getId(),
                    machine.getName(),
                    EventFactory.STATE_MACHINE_EVENT_TYPE_ID, new FieldValues()));
            for (final UmlState state : machine.getStates()) {
                int parentId = -1;
                if (state.getParent() == null) {
                    parentId = machine.getId();
                } else {
                    parentId = state.getParent().getId();
                }
                client.eventRead(new StateArtifactInfo(state.getId(), state
                        .getName(), parentId, EventFactory.STATE_EVENT_TYPE_ID,
                        new FieldValues()));
            }
            /*
             * for (final UmlTransition trans : machine.getTransitions()) {
             * bin.writeInt(trans.getSource()); bin.writeInt(trans.getTarget());
             * }
             */
        }
    }

    private boolean getTaskRelease(TypedCircularMessage message,
            List<TypedCircularMessage> cmList, int begin, long ts) {
        final Long taskId = (Long) getValue(message, SRLEventReader.FN_TASK_ID);

        // Make a dummy get to create the task in the first pass if it is
        // missing
        getTaskFromId(taskId);

        final Long timeBudget = (Long) getValue(message, "timeBudget");
        if (timeBudget == null) {
            return false;
        }

        FieldValues values = new FieldValues();
        client.eventRead(new TaskReleaseEventInfo(ts, taskId.intValue(),
                timeBudget * NANO_PER_MICRO,
                EventFactory.TASK_RELEASE_EVENT_TYPE_ID, values));
        return true;
    }

    private boolean getTaskCompletion(TypedCircularMessage message, long ts) {
        final Long taskId = (Long) getValue(message, SRLEventReader.FN_TASK_ID);

        // Make a dummy get to create the task in the first pass if it is
        // missing
        getTaskFromId(taskId);

        final Long remainingTime = (Long) getValue(message,
                SRLEventReader.REMAINING_TIME);
        if (remainingTime == null) {
            return false;
        }

        FieldValues values = new FieldValues();
        client.eventRead(new TaskCompletionEventInfo(ts, taskId.intValue(),
                remainingTime * NANO_PER_MICRO,
                EventFactory.TASK_COMPLETE_EVENT_TYPE_ID, values));

        return true;
    }

    private void getTaskSwitch(TypedCircularMessage message, long ts) {
        final Long objTaskId = (Long) getValue(message, 0);
        final int taskId = objTaskId.intValue();
        Task task = getTaskFromId(objTaskId);
        task.setHasExecutedDuringLog(true);
        final Object objPrio = getValue(message, 1);
        int prio;
        if (objPrio != null) {
            prio = ((Long) objPrio).intValue();
        } else {
            prio = -777;
        }
        FieldValues values = new FieldValues();
        client.eventRead(new TaskSwitchEventInfo(ts, taskId, lastTaskId, prio,
                EventFactory.TASK_SWITCH_EVENT_TYPE_ID, values));
        lastInterruptPrio = prio;
        lastTaskId = taskId;
        lastTaskName = task.getName();

    }

    private Task getTaskFromId(final Long id) {
        Task task = taskMap.get(id);
        if (task == null) {
            String name = Long.toHexString(id);
            task = new Task(name);
            taskMap.put(id, task);
        }
        return task;
    }

    public void getUMLSend(TypedCircularMessage message, long receivedAt,
            long ts) {

        UmlSend umlSend = new UmlSend(message);

        MentorIndex mentorIndex = blackbox.getMentorIndex();

        long senderClassId = umlSend.getSenderClassId();
        String senderStateMachine = mentorIndex.getClazz((int) senderClassId);
        final UmlStateMachine sendMachine = getStatemachineByName(senderStateMachine);

        long receiverClassId = umlSend.getReceiverClassId();
        String receiverStateMachine = mentorIndex
                .getClazz((int) receiverClassId);
        final UmlStateMachine receiveMachine = getStatemachineByName(receiverStateMachine);

        SRLSend srlSendEvent = new SRLSend();

        srlSendEvent.setSenderId(sendMachine.getId());
        srlSendEvent.setSenderInstance((int) umlSend.getSenderInstanceId());
        srlSendEvent.setSenderReplicatioIndex(0);
        srlSendEvent.setReceiverId(receiveMachine.getId());
        srlSendEvent.setReceiverInstance((int) umlSend.getReceiverInstanceId());
        srlSendEvent.setReceiverReplicationIndex(0);

        long signalId = umlSend.getSignalId();
        String signal = mentorIndex.getSignal((int) signalId);
        srlSendEvent.setSignal(signal);
        srlSendEvent.setMessageAddress(umlSend.getSignalInstanceId());
        srlSendEvent.setPort("");
        srlSendEvent.setPortIndex(Long.valueOf(0));
        srlSendEvent.setReceivedAt(resolveTimeStamp(receivedAt));
        srlSendEvent.setTs(resolveTimeStamp(((CircularMessage) message
                .getMessage()).getTs()));
        getSRLSend(srlSendEvent, ts);
    }

    private void getRoseSend(TypedCircularMessage message, long receivedAt,
            long ts) {
        final UmlStateMachine sendMachine = getStatemachineByName(getValue(
                message, SRLEventReader.FN_SENDER_ACTOR).toString());
        final UmlStateMachine receiveMachine = getStatemachineByName(getValue(
                message, SRLEventReader.FN_RECEIVER_ACTOR.toString())
                .toString());
        final Long msgAddr = (Long) getValue(message,
                SRLEventReader.FN_MSG_ADDR);

        SRLSend srlSendEvent = new SRLSend();

        srlSendEvent.setSenderId(sendMachine.getId());
        srlSendEvent.setSenderInstance(((Long) getValue(message,
                SRLEventReader.FN_SENDER_ACTOR_INSTANCE)).intValue());
        srlSendEvent.setSenderReplicatioIndex(((Long) getValue(message,
                SRLEventReader.FN_SENDER_REPLICATION_INDEX)).intValue());
        srlSendEvent.setReceiverId(receiveMachine.getId());
        srlSendEvent.setReceiverInstance(((Long) getValue(message,
                SRLEventReader.FN_RECEIVER_ACTOR_INSTANCE)).intValue());
        srlSendEvent.setReceiverReplicationIndex(((Long) getValue(message,
                SRLEventReader.FN_RECEIVER_REPLICATION_INDEX)).intValue());
        srlSendEvent.setSignal(getValue(message, SRLEventReader.FN_SIGNAL)
                .toString());
        srlSendEvent.setPort(getValue(message, SRLEventReader.FN_PORT)
                .toString());
        srlSendEvent.setPortIndex((Long) getValue(message,
                SRLEventReader.FN_PORT_INDEX));
        srlSendEvent.setMessageAddress(msgAddr);
        srlSendEvent.setReceivedAt(resolveTimeStamp(receivedAt));
        srlSendEvent.setTs(resolveTimeStamp(((CircularMessage) message
                .getMessage()).getTs()));

        getSRLSend(srlSendEvent, ts);
    }

    private void getSRLSend(SRLSend send, long ts) {
        FieldValues values = new FieldValues();
        // addProcess(event.getSenderProcess());
        // addProcess(event.getAddresseeProcess());
        values.addIntValue(send.getSenderInstance());
        values.addStringValue(send.getPort());
        values.addIntValue(send.getPortIndex().intValue());
        values.addIntValue(send.getMessageAddress().intValue());
        sentTsByMsgAddr.put(send.getMessageAddress(), send.getTs());
        client.eventRead(new RoseSendEventInfo(ts, send.getSenderId(), send
                .getReceiverId(), send.getReceivedAt(), send.getSignal(), send
                .getReceiverInstance(), EventFactory.ROSE_SEND_EVENT_TYPE_ID,
                values));
    }

    private class SRLSend {
        private int senderId;

        private int senderInstance;

        private int senderReplicatioIndex;

        private int receiverId;

        private int receiverInstance;

        private int receiverReplicationIndex;

        private String signal;

        private String port;

        private Long portIndex;

        private Long messageAddress;

        private long receivedAt;

        private long ts;

        public int getSenderId() {
            return senderId;
        }

        public void setSenderId(final int senderId) {
            this.senderId = senderId;
        }

        public int getSenderInstance() {
            return senderInstance;
        }

        public void setSenderInstance(final int senderInstance) {
            this.senderInstance = senderInstance;
        }

        public int getSenderReplicatioIndex() {
            return senderReplicatioIndex;
        }

        public void setSenderReplicatioIndex(final int senderReplicatioIndex) {
            this.senderReplicatioIndex = senderReplicatioIndex;
        }

        public int getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(final int receiverId) {
            this.receiverId = receiverId;
        }

        public int getReceiverInstance() {
            return receiverInstance;
        }

        public void setReceiverInstance(final int receiverInstance) {
            this.receiverInstance = receiverInstance;
        }

        public int getReceiverReplicationIndex() {
            return receiverReplicationIndex;
        }

        public void setReceiverReplicationIndex(
                final int receiverReplicationIndex) {
            this.receiverReplicationIndex = receiverReplicationIndex;
        }

        public String getSignal() {
            return signal;
        }

        public void setSignal(final String signal) {
            this.signal = signal;
        }

        public String getPort() {
            return port;
        }

        public void setPort(final String port) {
            this.port = port;
        }

        public Long getPortIndex() {
            return portIndex;
        }

        public void setPortIndex(final Long portIndex) {
            this.portIndex = portIndex;
        }

        public Long getMessageAddress() {
            return messageAddress;
        }

        public void setMessageAddress(final Long messageAddress) {
            this.messageAddress = messageAddress;
        }

        public long getReceivedAt() {
            return receivedAt;
        }

        public void setReceivedAt(final long receivedAt) {
            this.receivedAt = receivedAt;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(final long ts) {
            this.ts = ts;
        }
    }

    private class SRLReceive {
        private int receiverId;

        private int receiverInstance;

        private int receiverReplicationIndex;

        private int stateId;

        private String signal;

        private String port;

        private Long portIndex;

        private Long messageAddress;

        private long sentAt;

        private long ts;

        public int getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(final int receiverId) {
            this.receiverId = receiverId;
        }

        public int getReceiverInstance() {
            return receiverInstance;
        }

        public void setReceiverInstance(final int receiverInstance) {
            this.receiverInstance = receiverInstance;
        }

        public int getReceiverReplicationIndex() {
            return receiverReplicationIndex;
        }

        public void setReceiverReplicationIndex(
                final int receiverReplicationIndex) {
            this.receiverReplicationIndex = receiverReplicationIndex;
        }

        public int getStateId() {
            return stateId;
        }

        public void setStateId(final int stateId) {
            this.stateId = stateId;
        }

        public String getSignal() {
            return signal;
        }

        public void setSignal(final String signal) {
            this.signal = signal;
        }

        public String getPort() {
            return port;
        }

        public void setPort(final String port) {
            this.port = port;
        }

        public Long getPortIndex() {
            return portIndex;
        }

        public void setPortIndex(final Long portIndex) {
            this.portIndex = portIndex;
        }

        public Long getMessageAddress() {
            return messageAddress;
        }

        public void setMessageAddress(final Long messageAddress) {
            this.messageAddress = messageAddress;
        }

        public long getSentAt() {
            return sentAt;
        }

        public void setSentAt(final long sentAt) {
            this.sentAt = sentAt;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(final long ts) {
            this.ts = ts;
        }
    }

    public void getUMLreceive(TypedCircularMessage message, long ts) {
        UmlReceive umlReceive = new UmlReceive(message);

        MentorIndex index = blackbox.getMentorIndex();
        long receiverClassId = umlReceive.getReceiverClassId();
        String receiverClassName = index.getClazz((int) receiverClassId);
        if (receiverClassName == null) {
            receiverClassName = "UnknownClassName";
        }

        final UmlStateMachine machine = getStatemachineByName(receiverClassName);

        SRLReceive receive = new SRLReceive();

        receive.setReceiverId(machine.getId());
        receive.setReceiverInstance(umlReceive.getReceiverInstanceId());
        receive.setReceiverReplicationIndex(0);

        long stateId = umlReceive.getNextStateId();
        String stateName = index.getState((int) stateId);
        UmlState umlState = getStateByName(stateName, machine);
        receive.setStateId(umlState.getId());

        long signalId = umlReceive.getSignalId();
        String signalName = index.getSignal((int) signalId);
        receive.setSignal(signalName);
        Long signalInstanceId = umlReceive.getSignalInstanceId();
        receive.setMessageAddress(signalInstanceId);
        Long sentAt = getSentAt(signalInstanceId);
        receive.setSentAt(sentAt);

        receive.setPort("");
        receive.setPortIndex(Long.valueOf(0));

        final long high = umlReceive.getFinishHi();
        final long low = umlReceive.getFinishLo();

        long finishTime = resolveTimeStamp((high << 32) + low);
        finishTime = convertTime(finishTime);
        receive.setTs(finishTime);

        getSRLReceive(receive, ts);

        return;
    }

    private long convertTime(final long finishTime) {
        long result = finishTime;
        Long cpuTickFreq = this.blackbox.getCpuTickFreq();
        if (cpuTickFreq != null) {
            double k = 1000000000.0 / cpuTickFreq;
            result = (long) (finishTime * k);
        }
        return result;
    }

    private void getRoseReceive(TypedCircularMessage message, long ts) {
        final UmlStateMachine machine = getStatemachineByName(getValue(message,
                SRLEventReader.FN_RECEIVER_ACTOR).toString());
        Object fnState = getValue(message, SRLEventReader.FN_STATE);
        UmlState state = null;
        if (fnState != null) {
            state = getStateByName(fnState.toString(), machine);
        }
        final Long msgAddr = (Long) getValue(message,
                SRLEventReader.FN_MSG_ADDR);
        final long high = (Long) getValue(message, FINISH_HI);
        final long low = (Long) getValue(message, FINISH_LO);
        Long sentAt = getSentAt(msgAddr);
        SRLReceive receive = new SRLReceive();
        receive.setReceiverId(machine.getId());
        receive.setReceiverInstance(((Long) getValue(message,
                SRLEventReader.FN_RECEIVER_ACTOR_INSTANCE)).intValue());
        receive.setReceiverReplicationIndex(((Long) getValue(message,
                SRLEventReader.FN_RECEIVER_ACTOR_REPLICATION_INDEX)).intValue());
        if (state == null) {
            receive.setStateId(-1);
        } else {
            receive.setStateId(state.getId());
        }
        receive.setSignal(getValue(message, SRLEventReader.FN_SIGNAL)
                .toString());
        receive.setPort(getValue(message, SRLEventReader.FN_PORT).toString());
        receive.setPortIndex(((Long) getValue(message,
                SRLEventReader.FN_PORT_INDEX)));
        receive.setMessageAddress(msgAddr);

        long finishTime = resolveTimeStamp((high << 32) + low);
        finishTime = convertTime(finishTime);

        receive.setTs(finishTime);
        receive.setSentAt(sentAt);
        getSRLReceive(receive, ts);
    }

    private Long getSentAt(final Long msgAddr) {
        Long sentAt = sentTsByMsgAddr.get(msgAddr);
        if (sentAt == null) {
            sentAt = -1L;
        } else {
            sentTsByMsgAddr.remove(msgAddr);
        }
        return sentAt;
    }

    private void getSRLReceive(SRLReceive receive, long ts) {
        FieldValues values = new FieldValues();
        // addProcess(event.getSenderProcess());
        // addProcess(event.getAddresseeProcess());
        values.addIntValue(receive.getReceiverInstance());
        values.addStringValue(receive.getPort().toString());
        values.addIntValue(receive.getPortIndex().intValue());
        values.addIntValue(receive.getMessageAddress().intValue());

        client.eventRead(new RoseReceiveEventInfo(ts, 777, receive
                .getReceiverId(), receive.getSentAt(), receive.getSignal(),
                receive.getStateId(), receive.getTs(),
                EventFactory.ROSE_RECEIVE_EVENT_TYPE_ID, values));

    }

    private void getFunctionEnter(TypedCircularMessage message, long endTime,
            long ts) {
        client.eventRead(new FunctionEnterEventInfo(ts,
                getFunctionName(message), endTime,
                EventFactory.FUNCTION_ENTER_EVENT_TYPE_ID, new FieldValues()));
    }

    private void getFunctionExit(TypedCircularMessage message, long endTime,
            long ts) {
        client.eventRead(new FunctionExitEventInfo(ts,
                getFunctionName(message), endTime,
                EventFactory.FUNCTION_ENTER_EVENT_TYPE_ID, new FieldValues()));
    }

    private String getFunctionName(final TypedCircularMessage message) {
        Object functionPointer = message.getValue(FUNCTION_FIELD);
        TypedLinearMessage functionMessage = functionMap.get(functionPointer);
        String functionName = null;
        if (functionMessage == null) {
            if (functionPointer instanceof Pointer) {
                Pointer ptr = (Pointer) functionPointer;
                functionName = Long.toHexString(ptr.getPointer());
            } else {
                throw new IllegalStateException(
                        "function field is not a pointer!");
            }
        } else {
            functionName = (String) functionMessage.getValue(FN_NAME);
        }
        return functionName;
    }

    private long findReceiveTime(final TypedCircularMessage message,
            final List<TypedCircularMessage> copy, final int begin) {
        Long my = (Long) getValue(message, SRLEventReader.FN_MSG_ADDR);
        if (my == null) {
            my = (Long) getValue(message, SRLEventReader.SIGNAL_INSTANCE_ID);
        }
        final int size = copy.size();
        for (int i = begin; i < size; i++) {
            final TypedCircularMessage other = copy.get(i);
            if (other.getStruct().getId() != Message.ROSE_RECEIVE.srlTypeId()
                    && other.getStruct().getId() != Message.UML_RECEIVE
                            .srlTypeId()) {
                continue;
            }
            Long their = (Long) getValue(other, SRLEventReader.FN_MSG_ADDR);
            if (their == null) {
                their = (Long) getValue(other,
                        SRLEventReader.SIGNAL_INSTANCE_ID);
            }
            if (their == null) {
                continue;
            }
            if (!their.equals(my)) {
                continue;
            }
            return ((CircularMessage) other.getMessage()).getTs();
        }
        return -1;
    }

    private UmlStateMachine getStatemachineByName(final String statemachineName) {
        for (final UmlStateMachine machine : stateMachineMap.values()) {
            if (!statemachineName.equals(machine.getName())) {
                continue;
            }
            return machine;
        }
        final UmlStateMachine unknown = new UmlStateMachine(statemachineName);
        unknown.setId(Integer.MAX_VALUE - SRLEventReader.unknownIdCnt);
        SRLEventReader.unknownIdCnt++;
        stateMachineMap.put(statemachineName, unknown);
        return unknown;
    }

    private void reset() {
        lastInterruptPrio = -777;
        lastTaskName = SRLEventReader.UNKNOWN_TASK;
        lastTaskId = 777;
        Task unknown = new Task(lastTaskName);
        unknown.setPriority(lastInterruptPrio);
        taskMap.put(Long.valueOf(lastTaskId), unknown);
        ticksPerNano = -1;
    }

    private long resolveTimeStamp(final long ts) {
        if (ticksPerNano == -1) {
            return ts;
        }
        final double ns = ts / ticksPerNano;
        return (long) ns;
    }

    private UmlState getStateByName(final String stateName,
            final UmlStateMachine machine) {
        if (stateName == null) {
            return null;
        }
        for (final UmlState state : machine.getStates()) {
            if (stateName.equals(state.getName())) {
                return state;
            }
        }

        final UmlState state = new UmlState(stateName, machine, 0);
        state.setId(Integer.MAX_VALUE - SRLEventReader.unknownIdCnt);
        SRLEventReader.unknownIdCnt++;
        machine.add(state);
        return state;
    }

    private Object getValue(final TypedCircularMessage message, final int i) {
        final Field f = message.getStruct().getFields().get(i);
        final String name = f.getName();
        return getValue(message, name);
    }

    private Object getValue(final TypedCircularMessage message,
            final String name) {
        final Object value = message.getValue(name);
        if (value == null) {
            Logger.err("WARNING: No such value: " + name);
            return null;
        }
        if (value instanceof IPointer) {
            final IPointer p = (IPointer) value;

            if (p.getValue() == null) {
                if (!name.equals(SRLEventReader.FN_RECEIVER_ACTOR_INSTANCE)
                        && !name.equals(SRLEventReader.FN_SENDER_ACTOR_INSTANCE)) {
                    Logger.err("Note: value of '"
                            + name
                            + "' on '"
                            + message.getStruct().getName()
                            + "' is an unresolved pointer value (NULL). Will use pointer ("
                            + p.getPointer() + ")");
                }
                if (name.equals(SRLEventReader.FN_STATE)
                        && p.getPointer() == 0L) {
                    return null;
                }
                return p.getPointer();
            }
            return p.getValue();
        }

        if (value.getClass().isArray()) {
            final Object[] arr = (Object[]) value;
            return Arrays.asList(arr);
        }
        return value;
    }

    private void sort(final List<TypedCircularMessage> cmsgs) {
        Collections.sort(cmsgs, new Comparator<TypedCircularMessage>() {

            public int compare(final TypedCircularMessage o1,
                    final TypedCircularMessage o2) {
                final long ts1 = ((CircularMessage) o1.getMessage()).getTs();
                final long ts2 = ((CircularMessage) o2.getMessage()).getTs();
                return Long.valueOf(ts1).compareTo(ts2);
            }
        });
    }

    class Task {
        private String name;

        private boolean hasExecutedDuringLog;

        private int priority;

        public Task(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(final int priority) {
            this.priority = priority;
        }

        public boolean hasExecutedDuringLog() {
            return hasExecutedDuringLog;
        }

        public void setHasExecutedDuringLog(final boolean hasExecutedDuringLog) {
            this.hasExecutedDuringLog = hasExecutedDuringLog;
        }

    }
}

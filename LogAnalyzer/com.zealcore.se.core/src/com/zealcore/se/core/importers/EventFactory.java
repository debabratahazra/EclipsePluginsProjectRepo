package com.zealcore.se.core.importers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.format.FieldDescription;
import com.zealcore.se.core.format.FieldDescription.FieldType;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.TypeDescription;

class EventFactory {

    private static final List<TypeDescription> TYPE_DESCRIPTIONS = new ArrayList<TypeDescription>();

    public static final int ROSE_SEND_EVENT_TYPE_ID = 1;

    public static final int ROSE_RECEIVE_EVENT_TYPE_ID = 2;

    public static final int TASK_SWITCH_EVENT_TYPE_ID = 3;

    public static final int TASK_COMPLETE_EVENT_TYPE_ID = 4;

    public static final int TASK_RELEASE_EVENT_TYPE_ID = 5;

    public static final int FUNCTION_ENTER_EVENT_TYPE_ID = 6;

    public static final int FUNCTION_EXIT_EVENT_TYPE_ID = 7;

    public static final int TASK_EVENT_TYPE_ID = 8;

    public static final int STATE_MACHINE_EVENT_TYPE_ID = 9;

    public static final int STATE_EVENT_TYPE_ID = 10;

    private final Map<Integer, GenericArtifactInfo> artifacts = new HashMap<Integer, GenericArtifactInfo>();

    static {
        TYPE_DESCRIPTIONS.add(new RoseSendEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new RoseReceiveEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new TaskSwitchEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new TaskCompleteEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new TaskReleaseEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new FunctionEnterEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new FunctionExitEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new TaskArtifactTypeDescription());
        TYPE_DESCRIPTIONS.add(new StateMachineArtifactTypeDescription());
        TYPE_DESCRIPTIONS.add(new StateArtifactTypeDescription());
    }

    public void init() {}

    public static List<TypeDescription> getTypeDescriptions() {
        return TYPE_DESCRIPTIONS;
    }

    public Collection<GenericArtifactInfo> getArtifacts() {
        return artifacts.values();
    }

    private static class RoseSendEventTypeDescription extends TypeDescription {
        static final int TYPE_ID = ROSE_SEND_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        static {
            FIELDS.add(new FieldDescription("SenderInstance", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Port", FieldType.STRING));
            FIELDS.add(new FieldDescription("PortIndex", FieldType.UINT32));
            FIELDS.add(new FieldDescription("MessageAddress", FieldType.INT32));
        }

        RoseSendEventTypeDescription() {
            super(TYPE_ID, "Send", FIELDS);
        }
    }

    private static class RoseReceiveEventTypeDescription extends
            TypeDescription {
        static final int TYPE_ID = ROSE_RECEIVE_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        static {
            FIELDS.add(new FieldDescription("ReceiveInstance", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Port", FieldType.STRING));
            FIELDS.add(new FieldDescription("PortIndex", FieldType.UINT32));
            FIELDS.add(new FieldDescription("MessageAddress", FieldType.INT32));
        }

        RoseReceiveEventTypeDescription() {
            super(TYPE_ID, "Receive", FIELDS);
        }
    }

    private static class TaskSwitchEventTypeDescription extends TypeDescription {
        static final int TYPE_ID = TASK_SWITCH_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        TaskSwitchEventTypeDescription() {
            super(TYPE_ID, "TaskSwitch", FIELDS);
        }
    }

    private static class TaskCompleteEventTypeDescription extends
            TypeDescription {
        static final int TYPE_ID = TASK_COMPLETE_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        TaskCompleteEventTypeDescription() {
            super(TYPE_ID, "TaskCompletion", FIELDS);
        }
    }

    private static class TaskReleaseEventTypeDescription extends
            TypeDescription {
        static final int TYPE_ID = TASK_RELEASE_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        TaskReleaseEventTypeDescription() {
            super(TYPE_ID, "TaskRelease", FIELDS);
        }
    }

    private static class FunctionEnterEventTypeDescription extends
            TypeDescription {
        static final int TYPE_ID = FUNCTION_ENTER_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        FunctionEnterEventTypeDescription() {
            super(TYPE_ID, "FuncationEnter", FIELDS);
        }
    }

    private static class FunctionExitEventTypeDescription extends
            TypeDescription {
        static final int TYPE_ID = FUNCTION_EXIT_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        FunctionExitEventTypeDescription() {
            super(TYPE_ID, "FuncationExit", FIELDS);
        }
    }

    private static class TaskArtifactTypeDescription extends TypeDescription {
        static final int TYPE_ID = TASK_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        /*
         * static { FIELDS.add(new FieldDescription("RemainingTicksCount",
         * FieldType.INT64)); FIELDS.add(new
         * FieldDescription("RemainingTicksMin", FieldType.INT64));
         * FIELDS.add(new FieldDescription("RemainingTicksMax",
         * FieldType.INT64)); FIELDS.add(new
         * FieldDescription("RemainingTicksAvg", FieldType.FLOAT)); }
         */

        TaskArtifactTypeDescription() {
            super(TYPE_ID, "Task", FIELDS);
        }
    }

    private static class StateMachineArtifactTypeDescription extends
            TypeDescription {
        static final int TYPE_ID = STATE_MACHINE_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        StateMachineArtifactTypeDescription() {
            super(TYPE_ID, "StateMachine", FIELDS);
        }

    }

    private static class StateArtifactTypeDescription extends TypeDescription {
        static final int TYPE_ID = STATE_EVENT_TYPE_ID;

        private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

        StateArtifactTypeDescription() {
            super(TYPE_ID, "State", FIELDS);
        }
    }
}

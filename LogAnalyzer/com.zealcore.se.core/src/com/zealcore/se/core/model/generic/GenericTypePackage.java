package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zealcore.se.core.dl.AbstractTypePackage;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;

/**
 * A log message type to the core type-register. These classes must extend types
 * in the core.model package.
 * 
 * @author cafa
 * 
 */
public class GenericTypePackage extends AbstractTypePackage {

    private Map<Short, IProcessSwitch> lastSwitchEventMap;

    private LogFile file;

    private IArtifact currentProcess;

    private GenericSequence sequence;

    private Map<Short, GenericActivity> currentActivityMap;

    private Map<IArtifact, GenericTaskInstance> taskInstances;

    private Map<IArtifact, GenericTaskInstance> completedTaskInstances;

    private Map<String, StatemachineTypeData> lastStateData;

    private GenericTask unknownProcess;

    public static final String EXECUTION_UNIT = "Execution Unit";

    /**
     * Constructor.
     */
    public GenericTypePackage() {}

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.dl.ITypePackage#getTypes()
     */
    public Collection<IType> getTypes() {
        return Collections.unmodifiableCollection(GenericType.getTypes());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.dl.ITypePackage#processLogEvent(com.zealcore.se.
     * core.model.ILogEvent)
     */
    public void processLogEvent(final ILogEvent abstractLogEvent) {
        if (lastStateData == null) {
            lastStateData = new HashMap<String, StatemachineTypeData>();
        }

        file = abstractLogEvent.getLogFile();
        if (sequence == null) {
            sequence = new GenericSequence();
            sequence.setLogFile(file);
            sequence.setName("OSE Sequence");
        }

        if (abstractLogEvent instanceof GenericTaskSwitchEvent) {
            final GenericTaskSwitchEvent e = (GenericTaskSwitchEvent) abstractLogEvent;
            processTaskSwitch(e);
        } else if (abstractLogEvent instanceof GenericReceiveEvent) {
            GenericReceiveEvent e = (GenericReceiveEvent) abstractLogEvent;
            processReceiveEvent(e);
        } else if (abstractLogEvent instanceof GenericSendEvent) {
            GenericSendEvent e = (GenericSendEvent) abstractLogEvent;
            processSendEvent(e);
        } else if (abstractLogEvent instanceof GenericTaskRelease) {
            final GenericTaskRelease e = (GenericTaskRelease) abstractLogEvent;
            processTaskRelease(e);
        } else if (abstractLogEvent instanceof GenericTaskCompletion) {
            final GenericTaskCompletion e = (GenericTaskCompletion) abstractLogEvent;
            processTaskCompletion(e);
        }

    }

    private void processTaskCompletion(final GenericTaskCompletion e) {
        Object object = e.getProperty(EXECUTION_UNIT);
        Short core = 0;
        if (object != null) {
            core = (Short) object;
        }
        IProcessSwitch lastSwitchEvent = lastSwitchEventMap.get(core);
        if (lastSwitchEvent != null) {
            // set completion time for current task instance
            GenericTaskInstance taskInstance = taskInstances.get(e.getTaskId());
            if (taskInstance != null) {
                taskInstance.setCompletionTs(e.getTs());
                taskInstance.setStopEvent(e);
                taskInstance.setExecutionTime((e.getTs() - lastSwitchEvent
                        .getTs())
                        + taskInstance.getExecutionTime());

                completedTaskInstances.put(e.getTaskId(), taskInstance);
                taskInstances.remove(e.getTaskId());
            }
        }
    }

    private void processTaskRelease(final GenericTaskRelease e) {

        GenericTaskInstance taskInstance = completedTaskInstances.get(e
                .getTaskId());
        if (taskInstance != null) {
            long budgetOverrun = taskInstance.getDeadline() - e.getTs();
            if (budgetOverrun > 0) {
                taskInstance.setSchedulingDelay(budgetOverrun);
                taskInstance.setTimeBudget(taskInstance.getTimeBudget()
                        - budgetOverrun);
            }
            sendItem(taskInstance);
            completedTaskInstances.remove(e.getTaskId());
        }
        taskInstance = new GenericTaskInstance();
        taskInstance.setLogFile(e.getLogFile());
        taskInstance.setReleaseTs(e.getTs());
        taskInstance.setOwner(e.getTaskId());
        taskInstance.setStartEvent(e);
        taskInstance.setOwner(e.getTaskId());
        taskInstance.setTimeBudget(e.getTimeBudget());
        taskInstances.put(e.getTaskId(), taskInstance);

    }

    private void processTaskSwitch(final GenericTaskSwitchEvent e) {
        Object object = e.getProperty(EXECUTION_UNIT);
        Short core = 0;
        if (object != null) {
            core = (Short) object;
        }
        currentProcess = e.getResourceUserIn();
        IProcessSwitch lastSwitchEvent = lastSwitchEventMap.get(core);
        GenericActivity currentActivity = currentActivityMap.get(core);
        if (lastSwitchEvent != null) {
            GenericTaskExecution item1;

            /*
             * If a switch-event is missing, i.e. not logged...
             */
            if (lastSwitchEvent.getResourceUserIn() != e.getResourceUserOut()) {
                GenericTaskSwitchEvent fakedProcessSwitchIn = new GenericTaskSwitchEvent(
                        lastSwitchEvent.getTs());
                if (unknownProcess == null) {
                    unknownProcess = new GenericTask("Not Monitored "+core.toString());
                    unknownProcess.setTypeName(e.getResourceUserOut().getType()
                            .getName());
                    unknownProcess.setLogFile(e.getLogFile());
                    sendItem(unknownProcess);
                }
                fakedProcessSwitchIn.setResourceUserIn(unknownProcess);
                fakedProcessSwitchIn.setResourceUserOut(lastSwitchEvent
                        .getResourceUserOut());
                fakedProcessSwitchIn.setLogFile(e.getLogFile());
                GenericTaskSwitchEvent fakedProcessSwitchOut = new GenericTaskSwitchEvent(
                        e.getTs());
                fakedProcessSwitchOut.setResourceUserIn(e.getResourceUserIn());
                fakedProcessSwitchOut.setResourceUserOut(unknownProcess);
                fakedProcessSwitchOut.setLogFile(e.getLogFile());
                item1 = new GenericTaskExecution(fakedProcessSwitchIn,
                        fakedProcessSwitchOut);
                // item1.addProperty(e.getResourceUserOut().getType().getName(),
                // item1.getOwner());

            } else {
                /*
                 * Create task duration
                 */
                item1 = new GenericTaskExecution(lastSwitchEvent, e);

                item1.setOwner(e.getResourceUserOut());
                // item1.addProperty(e.getResourceUserOut().getType().getName(),
                // item1.getOwner());
            }
            item1.addProperty(EXECUTION_UNIT, core);
            if (e.getResourceUserIn() instanceof GenericTask) {
                String typeName = ((GenericTask) e.getResourceUserIn())
                        .getTypeName();
                item1.setTypeName(typeName + "Execution");
            }
            sendItem(item1);

            /*
             * End sequence activity
             */
            currentActivity.setStopEvent(e);
            currentActivity.setOwner((GenericTask) e.getResourceUserOut());
            currentActivity.addProperty(EXECUTION_UNIT, core);
            sendItem(currentActivity);

            /*
             * Create Task instance
             */
            GenericTaskInstance taskInstance = taskInstances.get(e
                    .getResourceUserIn());
            if (taskInstance != null) {
                if (taskInstance.getOwner().equals(currentProcess)) {

                    // set execution start time for current task instance if not
                    // already set
                    if (taskInstance.getExeStartTs() == 0) {
                        taskInstance.setExeStartTs(e.getTs());
                    }
                }
            }

            taskInstance = taskInstances.get(e.getResourceUserOut());
            if (taskInstance != null) {
                taskInstance.setExecutionTime(item1.getDurationTime()
                        + taskInstance.getExecutionTime());
            }
        }

        /*
         * Create sequence activity
         */
        currentActivityMap.put(core, new GenericActivity(e));
        lastSwitchEventMap.put(core, e);
    }

    private void processSendEvent(final GenericSendEvent e) {
        Object object = e.getProperty(EXECUTION_UNIT);
        Short core = 0;
        if (object != null) {
            core = (Short) object;
        }
        GenericActivity currentActivity = currentActivityMap.get(core);
        if (currentActivity != null) {
            currentActivity.addMessage(e);
        }
    }

    private void processReceiveEvent(final GenericReceiveEvent e) {
        final ISequenceMember machine = e.getReceiver();
        if (machine == null) {
            return;
        }
        final String statemachineNameId = machine.getName();
        StatemachineTypeData data = lastStateData.get(statemachineNameId);
        if (data == null) {
            data = new StatemachineTypeData(machine);
            lastStateData.put(statemachineNameId, data);
        }
        data.processReceive(e);
    }

    private void sendItem(final IObject item) {
        item.setLogFile(file);
        notifyListeners(item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Generic Type Package";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.dl.AbstractTypePackage#begin()
     */
    @Override
    public void begin() {
        lastSwitchEventMap = new HashMap<Short, IProcessSwitch>();
        currentActivityMap = new HashMap<Short, GenericActivity>();;
        currentProcess = null;
        file = null;
        sequence = null;
        taskInstances = new HashMap<IArtifact, GenericTaskInstance>();
        completedTaskInstances = new HashMap<IArtifact, GenericTaskInstance>();
        lastStateData = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.dl.AbstractTypePackage#end()
     */
    @Override
    public void end() {
        lastSwitchEventMap.clear();
        currentActivityMap.clear();
        for (GenericTaskInstance taskInstance : completedTaskInstances.values()) {
            sendItem(taskInstance);
        }
        super.end();
    }

    /**
     * Current data holder relevant for one state machine.
     */
    private class StatemachineTypeData {

        private final IState lastState;

        public StatemachineTypeData(final IArtifact machine) {
            lastState = new GenericState(IState.UNKNOWN);
            lastState.setLogFile(file);
            lastState.setParent(machine);
        }

        /**
         * Does nothing.
         * 
         * @param current
         *            the event
         */
        public void processReceive(final GenericReceiveEvent current) {}
    }
}

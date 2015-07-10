package com.zealcore.se.core.format;

import java.util.HashMap;
import java.util.Map;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.generic.GenericLogFile;
import com.zealcore.se.core.model.generic.GenericSequence;
import com.zealcore.se.core.model.generic.GenericState;
import com.zealcore.se.core.model.generic.GenericStateMachine;
import com.zealcore.se.core.model.generic.GenericTask;

public class LogFileInfo {
    private final int id;

    private final GenericLogFile logFile;

    private final GenericSequence sequence;

    private final Map<Integer, TypeDescription> types;

    private final Map<String, IArtifact> artifacts;

    private final Map<Integer, GenericTask> tasks;

    private final Map<Integer, GenericTask> taskExecs;

    private final Map<Integer, GenericStateMachine> stateMachines;

    private final Map<Integer, GenericState> states;

    public LogFileInfo(final int id, final GenericLogFile logFile) {
        this.id = id;
        this.logFile = logFile;
        sequence = new GenericSequence();
        sequence.setLogFile(logFile);
        sequence.setName("Sequence [" + logFile.getFileName() + "]");
        types = new HashMap<Integer, TypeDescription>();
        artifacts = new HashMap<String, IArtifact>();
        tasks = new HashMap<Integer, GenericTask>();
        taskExecs = new HashMap<Integer, GenericTask>();
        stateMachines = new HashMap<Integer, GenericStateMachine>();
        states = new HashMap<Integer, GenericState>();
        artifacts.put("Sequence", sequence);
    }

    public int getId() {
        return id;
    }

    public GenericLogFile getLogFile() {
        return logFile;
    }

    public GenericSequence getSequence() {
        return sequence;
    }

    public Map<Integer, TypeDescription> getTypes() {
        return types;
    }

    public Map<String, IArtifact> getArtifacts() {
        return artifacts;
    }

    public Map<Integer, GenericTask> getTasks() {
        return tasks;
    }

    public Map<Integer, GenericTask> getTaskExecs() {
        return taskExecs;
    }

    public Map<Integer, GenericStateMachine> getStateMachines() {
        return stateMachines;
    }

    public Map<Integer, GenericState> getStates() {
        return states;
    }

    public TypeDescription getType(final int typeId) {
        return types.get(typeId);
    }

    public IArtifact getArtifact(final int artifactId) {
        return artifacts.get(String.valueOf(artifactId));
    }

    public GenericTask getTask(final int taskId) {
        return tasks.get(taskId);
    }

    public GenericStateMachine getStateMachine(final int taskId) {
        return stateMachines.get(taskId);
    }

    public GenericState getState(final int taskId) {
        return states.get(taskId);
    }

    public void addType(final int typeId, final TypeDescription type) {
        types.put(typeId, type);
    }

    public void addArtifact(final String artifactId, final IArtifact artifact) {
        artifacts.put(artifactId, artifact);
    }

    public void addTask(final int taskId, final GenericTask task) {
        tasks.put(taskId, task);
    }

    public void addTaskExec(final int taskId, final double exec) {
        GenericTask task = tasks.get(taskId);
        if (task != null) {
            task.setUtilization(exec);
            taskExecs.put(taskId, task);
        }
    }

    public void addStateMachine(final int taskId, final GenericStateMachine stateMachine) {
        stateMachines.put(taskId, stateMachine);
    }

    public void addState(final int taskId, final GenericState state) {
        states.put(taskId, state);
    }
}

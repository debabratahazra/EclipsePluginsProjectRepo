package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.SEProperty;

/**
 * The GenericTask is an Artifact class keeping track of the different Tasks
 * (processes) that has been referred during the log was created. A boolean flag
 * tells if it has been executing or not during logging.
 * 
 * @author cafa
 * 
 */
public class GenericTask extends AbstractArtifact implements IGenericLogItem,
        ITask, ISequenceMember {

    private static final String UNKNOWN = "Unknown";

    private static final String TYPE_NAME = "Task";

    // Adapter start
    private final GenericAdapter adapter = new GenericAdapter();

    private double util;

    private ISequence parent;

    private int priority = -1;

    private boolean hasExecutedDuringLog;

    private long taskId;

    public GenericTask(final String name) {
        this.adapter.setTypeName(TYPE_NAME);
        this.setName(name);
        initParent();
    }

    public GenericTask() {
        this.adapter.setTypeName(TYPE_NAME);
        initParent();
    }

    public GenericTask(final int taskId, final String name, final int priority) {
        this.setName(name);
        this.adapter.setTypeName(TYPE_NAME);
        this.setTaskId(taskId);
        this.setPriority(priority);
        initParent();
    }

    private void initParent() {
        GenericSequence sequence = new GenericSequence();
        sequence.setName(UNKNOWN);
        sequence.setRoot(true);
        sequence.setTypeName(UNKNOWN + "Sequence");
        sequence.setLogFile(this.getLogFile());
        setParent(sequence);
    }

    @Override
    public void setLogFile(final LogFile logFile) {
        ISequence p = getParent();
        p.setLogFile(logFile);
        super.setLogFile(logFile);
    }

    public void addProperty(final String name, final Object value) {
        this.adapter.addProperty(name, value);
    }

    @Override
    public List<SEProperty> getZPropertyAnnotations() {

        final List<SEProperty> list = super.getZPropertyAnnotations();
        list.addAll(this.adapter.toSEProperties());

        return list;
    }

    public void setTypeName(final String typeName) {
        this.adapter.setTypeName(typeName);
    }

    /**
     * This method is for internal use only {@inheritDoc}
     */
    public Map<String, Object> properties() {
        return this.adapter.properties();
    }

    @Override
    public IType getType() {
        return GenericType.valueOf(this);
    }

    public Object getProperty(final String key) {
        return this.adapter.getProperty(key);
    }

    public String getTypeName() {
        return this.adapter.getTypeName();
    }

    @ZCProperty(name = "Utilization", searchable = true, plottable = true)
    public double getUtilization() {
        return util;
    }

    public void setUtilization(final double util) {
        this.util = util;
    }

    @ZCProperty(name = "Priority", searchable = true)
    public int getPriority() {
        return this.priority;
    }

    // Adapter end

    public ISequence getParent() {
        return this.parent;
    }

    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> abstractArtifacts = super
                .referencedArtifacts();
        abstractArtifacts.add(parent);
        return abstractArtifacts;
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (getParent() == oldArtifact) {
            setParent((ISequence) newArtifact);
            return;
        }
        super.substitute(oldArtifact, newArtifact);
    }

    public void setParent(final ISequence parent) {
        this.parent = parent;
    }

    public void setPriority(final int prio) {
        this.priority = prio;
    }

    public void setTaskId(final long taskId) {
        this.taskId = taskId;
    }

    @ZCProperty(name = "ID", searchable = true)
    public long getTaskId() {
        return this.taskId;
    }

    @Override
    public String getId() {
        return String.valueOf(getTaskId());
    }

    public static GenericTask createUnknown(final LogFile fileObject) {
        final GenericTask task = new GenericTask();
        task.setLogFile(fileObject);
        task.setName(UNKNOWN);
        task.setTaskId(-1);
        return task;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @ZCProperty(name = "Executed during log", searchable = true)
    public boolean hasExecutedDuringLog() {
        return this.hasExecutedDuringLog;
    }

    public void setHasExecutedDuringLog(final boolean hasExecutedDuringLog) {
        this.hasExecutedDuringLog = hasExecutedDuringLog;
    }
}

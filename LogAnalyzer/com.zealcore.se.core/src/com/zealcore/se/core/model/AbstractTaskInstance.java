package com.zealcore.se.core.model;

import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.annotation.ZSearchable;

@ZSearchable(name = "AbstractTaskInstance")
public abstract class AbstractTaskInstance extends AbstractDuration {

    private long releaseTs;

    private long exeStartTs;

    private long completionTs;

    private long timeBudget;

    private long executionTime;

    private long schedulingDelay;
    
    @ZCProperty(name = "Release Time", searchable = true, description = "The time this task is released", ts = true)
    public long getReleaseTs() {
        return releaseTs;
    }

    public void setReleaseTs(final long startTs) {
        this.releaseTs = startTs;
    }

    @ZCProperty(name = "Completion Time", searchable = true, description = "The ending time for this task instance", ts = true)
    public long getCompletionTs() {
        return completionTs;
    }

    public void setCompletionTs(final long endTs) {
        this.completionTs = endTs;
    }

    @ZCProperty(name = "Time Budget", searchable = true, description = "The deadline for this task instance", ts = true)
    public long getTimeBudget() {
        return timeBudget;
    }

    public void setTimeBudget(final long timeBudget) {
        this.timeBudget = timeBudget;
    }

    @ZCProperty(searchable = true, name = "Slack", ts = true, plottable = true)
    public long getSlack() {
        return getDeadline() - getCompletionTs();
    }

    @ZCProperty(searchable = true, name = "Slack Relative", description = "Slack relative timebudget in percent")
    public double getRelativeSlack() {
        if (timeBudget == 0L) {
            return -1.0;
        }
        return ((double) getSlack() / (double) timeBudget) * 100.0;
    }

    @ZCProperty(searchable = true, name = "Deadline", ts = true)
    public long getDeadline() {
        return getReleaseTs() + getTimeBudget();
    }

    @ZCProperty(searchable = true, name = "Release Jitter", ts = true, plottable = true, description = "The time in ns from task is released until it starts to execute")
    public long getRelaseJitter() {
        return getExeStartTs() - getReleaseTs();
    }

    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> r = super.referencedArtifacts();
        r.add(getOwner());
        return r;
    }

    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == getOwner()) {
            setOwner(newArtifact);
        }
        super.substitute(oldArtifact, newArtifact);
    }

    @ZCProperty(name = "Execution Time", plottable = true, searchable = true, description = "The time from this task release to the next task release for this task", ts = true)
    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(final long executionTime) {
        this.executionTime = executionTime;
    }

    @ZCProperty(name = "Execution Start Time", searchable = true, description = "The time in ns when this task starts to execute", ts = true)
    public long getExeStartTs() {
        return exeStartTs;
    }

    public void setExeStartTs(final long exeStartTs) {
        this.exeStartTs = exeStartTs;
    }

    @Override
    @ZCProperty(name = "Task", searchable = true, description = "The name of the task")
    public IArtifact getOwner() {
        return super.getOwner();
    }

    @Override
    @ZCProperty(name = "Response Time", plottable = true, searchable = true, ts = true, description = "The time in ns between the release event and the completion event")
    public long getDurationTime() {
        return super.getDurationTime();
    }
    @ZCProperty(name = "Scheduling Delay", plottable = true, searchable = true, ts = true)
    public long getSchedulingDelay() {
        return schedulingDelay;
    }

    public void setSchedulingDelay(final long budgetOverRun) {
        this.schedulingDelay = budgetOverRun;
    }
}

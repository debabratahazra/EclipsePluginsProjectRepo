package com.zealcore.se.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.zealcore.se.core.annotation.ZCProperty;

public abstract class AbstractTaskRoot extends AbstractArtifact {

    private Collection<ITask> taskExecList;

    private Collection<ITask> taskNonExecList;

    @ZCProperty(name = "Executed - Tasks & Interrupts", searchable = true, description = "Properties of all tasks and interrupts that have executed in log")
    public Collection<ITask> getTaskList() {
        List<ITask> taskListToSort = new ArrayList<ITask>(taskExecList);
        Collections.sort(taskListToSort, new Comparator<ITask>() {
            public int compare(final ITask t1, final ITask t2) {

                return t1.getName().compareToIgnoreCase(t2.getName());
            }
        });
        return taskListToSort;
    }

    public void setTaskList(final Collection<ITask> taskList) {
        this.taskExecList = taskList;
    }

    @Override
    public String getName() {
        return "";
    }

    @ZCProperty(name = "Not executed - Tasks & Interrupts", searchable = true, description = "Properties of all tasks and interrupts that have NOT executed in log")
    public Collection<ITask> getTaskNonExecList() {
        List<ITask> taskListToSort = new ArrayList<ITask>(taskNonExecList);
        Collections.sort(taskListToSort, new Comparator<ITask>() {
            public int compare(final ITask t1, final ITask t2) {

                return t1.getName().compareToIgnoreCase(t2.getName());
            }
        });
        return taskListToSort;
    }

    public void setTaskNonExecList(final Collection<ITask> taskNonExecList) {
        this.taskNonExecList = taskNonExecList;
    }
}

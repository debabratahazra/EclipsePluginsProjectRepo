package com.zealcore.se.core.model;

public interface ITaskDuration extends IDuration {

    /* TODO make this IProcessSwitch  */
    IProcessSwitch getStartEvent();
    ILogEvent getStopEvent();
    
    ITask getOwner();
}

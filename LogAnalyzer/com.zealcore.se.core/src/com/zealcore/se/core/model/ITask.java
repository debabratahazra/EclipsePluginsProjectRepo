package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

public interface ITask extends IArtifact {

    void setUtilization(double util);
    @ZCProperty(name="Utilization", searchable = true)
    double getUtilization();
    
    int getPriority();
    
//    void setHasExecuted(boolean hasExecuted);
//    boolean hasExecuted();
}

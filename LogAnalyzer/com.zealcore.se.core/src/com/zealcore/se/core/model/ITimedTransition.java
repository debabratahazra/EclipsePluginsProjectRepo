package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

public interface ITimedTransition extends IDuration {

    @ZCProperty(name = "StateTransition", searchable = true, description = "The name of the state transition (Usually From.name to To.name)")
    IStateTransition getTransition();

    /**
     * Gets the label of the transition. For example a signal which caused the
     * transition
     * 
     * @return the label of the transition
     */
    String getLabel();
    
    @ZCProperty(name = "Transition Time", plottable = true, searchable = true, description = "The length of this transition")
    long getDurationTime();
}

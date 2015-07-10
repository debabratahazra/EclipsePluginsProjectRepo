package com.zealcore.se.core.dl;

import java.util.Collection;
import java.util.HashSet;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.IStateTransition;

public class StateMachineGraph {

    private final IArtifact abstractStateMachine;

    private final HashSet<IState> abstractStates = new HashSet<IState>();

    private final HashSet<IStateTransition> transitions = new HashSet<IStateTransition>();
    
    public StateMachineGraph(final IArtifact abstractStateMachine) {
        this.abstractStateMachine = abstractStateMachine;
    }

    public IArtifact getStateMachine() {
        return abstractStateMachine;
    }

    public Collection<IState> getStates() {
        return abstractStates;
    }

    public Collection<IStateTransition> getTransitions() {
        return transitions;
    }

    public void addState(final IState state) {
        abstractStates.add(state);
    }

    public void addTransition(final IStateTransition transition) {
        if (transition == null) {
            throw new IllegalArgumentException("transition is null");
        }
        if (transitions == null) {
            throw new IllegalStateException("transitions is null");
        }
        transitions.add(transition);
    }

    public void clear() {
        transitions.clear();
        abstractStates.clear();
    }
}

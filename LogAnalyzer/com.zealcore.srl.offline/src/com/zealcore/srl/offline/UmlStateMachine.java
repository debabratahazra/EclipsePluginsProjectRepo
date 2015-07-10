package com.zealcore.srl.offline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class UmlStateMachine extends UmlEntity {
    private final String name;

    private final HashMap<String, UmlState> stateMap;

    private final List<UmlTransition> transactionList;

    private int nrOfStates;

    private int nrOfTransitions;

    public UmlStateMachine(final String name) {
        super();
        this.name = name;
        stateMap = new HashMap<String, UmlState>();
        transactionList = new ArrayList<UmlTransition>();
    }

    public void add(final UmlState currentState) {
        stateMap.put(currentState.getName(), currentState);
        nrOfStates++;
    }

    public void add(final UmlTransition umlTransition) {
        transactionList.add(umlTransition);
        nrOfTransitions++;
    }

    public Collection<UmlTransition> getTransitions() {
        return transactionList;
    }

    public Collection<UmlState> getStates() {
        final List<UmlState> states = new ArrayList<UmlState>(stateMap.values());

        Collections.sort(states, new Comparator<UmlState>() {

            public int compare(final UmlState o1, final UmlState o2) {
                return Integer.valueOf(o1.getDepth()).compareTo(o2.getDepth());
            }

        });
        return states;
    }

    public String getName() {
        return name;
    }

    public int getNrOfStates() {
        return nrOfStates;
    }

    public int getNrOfTransitions() {
        return nrOfTransitions;
    }

}

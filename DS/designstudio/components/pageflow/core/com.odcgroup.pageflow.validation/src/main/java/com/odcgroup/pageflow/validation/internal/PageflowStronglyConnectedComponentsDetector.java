package com.odcgroup.pageflow.validation.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.Transition;

/**
 * This class shall be used to detect the existence of one or more cycles in a
 * pageflow.
 * <p>
 * 
 * This class identifies all strongly connected components (SCC) in a pageflow.
 * Only Decision States are considered.
 * <p>
 * 
 * The implementation is based on the Tarjan's Algorithm
 * http://en.wikipedia.org/wiki/Tarjan's_strongly_connected_components_algorithm
 * 
 * @author atr
 */
public class PageflowStronglyConnectedComponentsDetector {

	private int index = 0;
	private Stack<State> states;
	private Map<State, Integer> indices = new HashMap<State, Integer>();
	private Map<State, Integer> lowLinks = new HashMap<State, Integer>();
	private Map<State, List<State>> successors = new HashMap<State, List<State>>();

	private List<List<State>> scc; // list all strongly connected components.

	public final List<List<State>> getStronglyConnectedComponents() {
		return scc;
	}

	@SuppressWarnings("unchecked")
	public PageflowStronglyConnectedComponentsDetector(Pageflow pageflow) {

		states = new Stack<State>();
		scc = new ArrayList<List<State>>();

		List<State> candidates = new ArrayList<State>();

		// build the precessors table only for DecisionState
		for (Transition t : (List<Transition>) pageflow.getTransitions()) {
			State fromState = t.getFromState();
			if (fromState instanceof DecisionState) {
				candidates.add(fromState);
				List<State> succList = successors.get(fromState);
				if (succList == null) {
					succList = new ArrayList<State>();
					successors.put(fromState, succList);
				}
				State toState = t.getToState();
				if (toState instanceof DecisionState) {
					candidates.add(toState);
					succList.add(toState);
					succList = successors.get(fromState);
					if (succList == null) {
						// this will avoid a NPE in method strongConnect, stmt:
						// successor.get(v)
						successors.put(fromState, new ArrayList<State>());
					}
				}
			}
		}

		// check for cycle
		for (State state : candidates) {
			if (!indices.containsKey(state)) {
				strongConnect(state);
			}
		}
	}

	private void strongConnect(State v) {

		indices.put(v, index);
		lowLinks.put(v, index);
		index++;
		states.push(v);

		for (State w : successors.get(v)) {
			if (!indices.containsKey(w)) {
				strongConnect(w);
				int min = Math.min(lowLinks.get(v), lowLinks.get(w));
				lowLinks.put(v, min);
			} else if (states.contains(w)) {
				lowLinks.put(v, Math.min(lowLinks.get(v), indices.get(w)));
			}
		}

		if (lowLinks.get(v) == indices.get(v)) {
			List<State> cycle = new ArrayList<State>();
			State w = null;
			do {
				w = states.pop();
				cycle.add(w);
			} while (!w.equals(v));
			if (cycle.size() > 1) {
				// self referenced decision state is not supported, so only
				// SCC with at least 2 Decision States are kept in the list.
				scc.add(cycle);
			}
		}

	}

}

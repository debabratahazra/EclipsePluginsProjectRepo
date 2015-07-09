package com.odcgroup.pageflow.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionMapping;

/**
 * @author atr, 2012
 */
public class PageflowMerger {
	
	private Pageflow pageflow;
	private Map<String, Property> properties = new LinkedHashMap<String, Property>();
	private Set<State> marks = new HashSet<State>();
	
	@SuppressWarnings("unchecked")
	private InitState getStartState(Pageflow pf) {
		InitState s = null;
		for (State state : (List<State>)pf.getStates()) {
			if (state instanceof InitState) {
				s = (InitState)state;
				break;
			}
		}
		return s;
	}
	
	@SuppressWarnings("unchecked")
	private List<EndState> getEndStates(Pageflow pf) {
		List<EndState> endStates = new ArrayList<EndState>();
		for (State state : (List<State>)pf.getStates()) {
			if (state instanceof EndState) {
				endStates.add((EndState)state);
			}
		}
		return endStates;
	}
	
	@SuppressWarnings("unchecked")
	private List<Transition> getOutgoingTransition(Pageflow pf, State v) {
		List<Transition> outTrans = new ArrayList<Transition>();
		for (Transition t : (List<Transition>)pf.getTransitions()) {
			if (t.getFromState().equals(v)) {
				outTrans.add(t);
			}
		}
		return outTrans;
	}
	
	@SuppressWarnings("unchecked")
	private List<Transition> getIncomingTransition(Pageflow pf, State v) {
		List<Transition> inTrans = new ArrayList<Transition>();
		for (Transition t : (List<Transition>)pf.getTransitions()) {
			if (t.getToState().equals(v)) {
				inTrans.add(t);
			}
		}
		return inTrans;
	}
	
	@SuppressWarnings("unchecked")
	private void removeState(State v, Pageflow pf) {
		pf.getStates().remove(v);
		List<Transition> tdel = new ArrayList<Transition>();
		for (Transition t : (List<Transition>)pf.getTransitions()) {
			if (t.getToState().equals(v) || t.getFromState().equals(v)) {
				tdel.add(t);
			}
		}
		pf.getTransitions().removeAll(tdel);
	}

	@SuppressWarnings("unchecked")
	private void mergeStartNode(State p, Pageflow nestingPf, Pageflow nestedPf) {
		State startState = getStartState(nestedPf);
		for (Transition t : getOutgoingTransition(nestedPf, startState)) {
			// merge and reconnect all incoming transitions of p with u
			for (Transition w : getIncomingTransition(nestingPf, p)) {
				// merge actions
				for (Action a : (List<Action>)t.getActions()) {
					w.getActions().add(EcoreUtil.copy(a));
				}
				// reconnect transition
				w.setToState(t.getToState());
			}
		}
		
		removeState(startState, nestedPf);
	}
	
	@SuppressWarnings("unchecked")
	private Transition getMappedTransition(SubPageflowState nestingPf, EndState e) {
		Transition mappedTransition = null;
		for (TransitionMapping mapping : (List<TransitionMapping>)nestingPf.getTransitionMappings()) {
			EndState s = mapping.getEndState();
			if (e.getName().equals(nestingPf.getName()+"."+s.getName())) {
				mappedTransition = mapping.getTransition();
				break;
			}
		}
		return mappedTransition;
	}
	
	@SuppressWarnings("unchecked")
	private void mergeEndNodes(State p, Pageflow nestedPf) {
		for (EndState endState : getEndStates(nestedPf)) {
			Transition m = getMappedTransition((SubPageflowState)p, endState);
			for (Transition t : getIncomingTransition(nestedPf, endState)) {
				// merge actions
				for (Action a : (List<Action>)m.getActions()) {
					t.getActions().add(EcoreUtil.copy(a));
				}
				// reconnect transition
				t.setToState(m.getToState());
			}
			removeState(endState, nestedPf);
		}
	}
	
	/**
	 * Merge pageflow and all its subgraph. 
	 * @param pf the pageflow to be merged
	 * @param prefix the prefix used to rename state
	 */
	@SuppressWarnings("unchecked")
	private void mergeGraph(Pageflow pf, String prefix) {
		InitState s = getStartState(pf);
		Queue<State> qState = new LinkedList<State>();
		qState.add(s);
		marks.add(s);
		
		while (! qState.isEmpty()) {
			
			State v = qState.poll();
			
			for (Transition t : getOutgoingTransition(pf, v)) {
				State o = t.getToState();
				if ( ! marks.contains(o)) {
					marks.add(o);
					qState.add(o);
				}
			}
			
			if (v instanceof SubPageflowState) {
				Pageflow nestedPf = EcoreUtil.copy(((SubPageflowState) v).getSubPageflow());
				for (Property p : (List<Property>)nestedPf.getProperty()) {
					if ( ! this.properties.containsKey(p.getName())) {
						this.properties.put(p.getName(), p);
					}
				}
				mergeGraph(nestedPf, prefix+v.getName()+".");
				mergeStartNode(v, pf, nestedPf);
				v.setName(prefix+v.getName());
				mergeEndNodes(v, nestedPf);
				removeState(v, pf);
				// copy states/transition from nested to nesting pageflows
				pf.getStates().addAll(nestedPf.getStates());
				pf.getTransitions().addAll(nestedPf.getTransitions());
				
			} else {
				// rename regular state
				v.setName(prefix+v.getName());
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateProperties() {
		this.pageflow.getProperty().clear();
		this.pageflow.getProperty().addAll(properties.values());
	}
	
	@SuppressWarnings("unchecked")
	private void sortStates() { 
		// sort by state'name / ascending
		List<State> states = new ArrayList<State>();
		states.addAll((List<State>)pageflow.getStates());
		pageflow.getStates().clear();
		Collections.sort(states, new Comparator<State>() {
			public int compare(State stateLeft, State stateRight) {
				return stateLeft.getName().compareToIgnoreCase(stateRight.getName()); 
			}});
		pageflow.getStates().addAll(states);
	}
	
	private String getLongName(Transition t) {
		StringBuilder name = new StringBuilder();
		name.append(t.getName());
		State state = t.getFromState(); 
		if (state != null) {
			name.append('.');
			name.append(state.getName());
		}
		state = t.getToState(); 
		if (state != null) {
			name.append('.');
			name.append(state.getName());
		}
		return name.toString(); 
	}
	
	@SuppressWarnings("unchecked")
	private void sortTransitions() {
		// sort by transition'name /ascending
		List<Transition> transitions = new ArrayList<Transition>();
		transitions.addAll((List<Transition>)this.pageflow.getTransitions());
		pageflow.getTransitions().clear();
		Collections.sort(transitions, new Comparator<Transition>() {
			public int compare(Transition transLeft, Transition transRight) {
				return getLongName(transLeft).compareToIgnoreCase(getLongName(transRight)); 
			}});
		pageflow.getTransitions().addAll(transitions);
	}

	@SuppressWarnings("unchecked")
	public Pageflow merge() {
		
		// init:
		for (Property p : (List<Property>)this.pageflow.getProperty()) {
			properties.put(p.getName(), p);
		}

		// do the merge
		mergeGraph(this.pageflow, "");
		
		// here the graph is merged, we apply some post-processing
		updateProperties();
		sortStates();
		sortTransitions();
		
		// return the merged pageflow
		return this.pageflow;
	}
	

	public PageflowMerger(Pageflow pageflow) {
		this.pageflow = EcoreUtil.copy(pageflow);
	}
	
}

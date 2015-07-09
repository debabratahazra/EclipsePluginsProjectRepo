package com.odcgroup.iris.rim.generation;

import java.util.HashMap;

import com.temenos.interaction.rimdsl.rim.State;

/**
 * A real comment
 *
 * @author taubert
 *
 */
public class CachedStates {
	final HashMap<String, State> cachedStates = new HashMap<String, State>();
	
	public void addKnownState(String name, State state){
		cachedStates.put(name, state);
	}
	
	public State getKnownState(String name){
		return cachedStates.get(name);
	}
}

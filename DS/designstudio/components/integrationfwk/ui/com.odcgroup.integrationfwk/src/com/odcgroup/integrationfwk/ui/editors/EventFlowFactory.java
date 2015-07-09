package com.odcgroup.integrationfwk.ui.editors;

import java.util.HashMap;
import java.util.Map;

import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.Flow;

/**
 * Factory class which responsible for collecting the Flows and Events as
 * instance of {@link Flow} and {@link Event} based on its respective names, for
 * further usage.
 * 
 * @author sbharathraja
 * 
 */
public class EventFlowFactory {

	/** collection object of Flow */
	private Map<String, Flow> _flowsCollection;

	/** collection object of Event */
	private Map<String, Event> _eventsCollection;

	/**
	 * add the given event instance into collection corresponding to its
	 * respective name.
	 * 
	 * @param eventName
	 *            - name of the event.
	 * @param eventInstance
	 *            - instance of {@link Event}
	 */
	public void addToCollection(String eventName, Event eventInstance) {
		if (_eventsCollection == null) {
			_eventsCollection = new HashMap<String, Event>();
		}
		_eventsCollection.put(eventName, eventInstance);
	}

	/**
	 * add the given flow instance into collection corresponding to its
	 * respective name.
	 * 
	 * @param flowName
	 *            - name of the flow.
	 * @param flowInstance
	 *            - instance of {@link Flow}
	 */
	public void addToCollection(String flowName, Flow flowInstance) {
		if (_flowsCollection == null) {
			_flowsCollection = new HashMap<String, Flow>();
		}
		_flowsCollection.put(flowName, flowInstance);
	}

	/**
	 * get the event instance of respective given name.
	 * 
	 * @param eventName
	 *            - name of event.
	 * @return as instance of {@link Event} if exists, null otherwise.
	 */
	public Event getEventInstance(String eventName) {
		if (_eventsCollection == null || _eventsCollection.isEmpty()) {
			return null;
		}
		return _eventsCollection.get(eventName);
	}

	/**
	 * get the flow instance from collection respective to given name.
	 * 
	 * @param flowName
	 *            - name of the flow
	 * @return instance of {@link Flow} if exists, null otherwise.
	 */
	public Flow getFlowInstance(String flowName) {
		if (_flowsCollection == null || _flowsCollection.isEmpty()) {
			return null;
		}
		return _flowsCollection.get(flowName);
	}

}

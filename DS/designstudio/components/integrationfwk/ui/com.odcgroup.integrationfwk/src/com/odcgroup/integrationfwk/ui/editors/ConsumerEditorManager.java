package com.odcgroup.integrationfwk.ui.editors;

import java.util.HashMap;
import java.util.Map;

import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.Flow;

/**
 * Class which act as a controller between EventsEditor/FlowsEditor and its
 * respective contents. This manager class is only having the responsibility of
 * providing the contents to Events/Flows Editor whenever needed/requested.
 * <p>
 * <b>Note:</b> This class is designed with single-ton pattern.
 * 
 * @author sbharathraja
 * 
 */
public final class ConsumerEditorManager {

	/** instance */
	private static ConsumerEditorManager _instance = new ConsumerEditorManager();

	/**
	 * get the instance of {@link ConsumerEditorManager}
	 * 
	 * @return instance of {@link ConsumerEditorManager}
	 */
	public static ConsumerEditorManager getInstance() {
		return _instance;
	}

	/** collection of {@link EventFlowFactory} */
	private final Map<String, EventFlowFactory> _factoryCollection;

	// block instance creation.
	private ConsumerEditorManager() {
		_factoryCollection = new HashMap<String, EventFlowFactory>();
	}

	/**
	 * add the given event into factory collection of given project name.
	 * 
	 * @param projectName
	 *            - name of the project.
	 * @param event
	 *            - instance of {@link Event}
	 */
	public void addToFactory(String projectName, Event event) {
		EventFlowFactory factory = getFactory(projectName);
		factory.addToCollection(event.getEventName(), event);
		_factoryCollection.put(projectName, factory);
	}

	/**
	 * add the given flow into factory collection of given project name.
	 * 
	 * @param projectName
	 *            - name of the project.
	 * 
	 * @param flow
	 *            - instance of {@link Flow}
	 */
	public void addToFactory(String projectName, Flow flow) {
		EventFlowFactory factory = getFactory(projectName);
		factory.addToCollection(flow.getFlowName(), flow);
		_factoryCollection.put(projectName, factory);
	}

	/**
	 * get the event from factory collection based on given project name and the
	 * event name.
	 * 
	 * @param projectName
	 *            - name of the project.
	 * @param eventName
	 *            - name of the event.
	 * @return instance of {@link Event}
	 */
	public Event getEvent(String projectName, String eventName) {
		if (_factoryCollection.isEmpty()) {
			return null;
		}
		EventFlowFactory factory = _factoryCollection.get(projectName);
		if (factory == null) {
			return null;
		}
		return factory.getEventInstance(eventName);
	}

	/**
	 * get the respective factory of given project name.
	 * 
	 * @param projectName
	 *            - name of the project.
	 * @return instance of {@link EventFlowFactory}
	 */
	private EventFlowFactory getFactory(String projectName) {
		EventFlowFactory factory = _factoryCollection.get(projectName);
		if (factory == null) {
			return new EventFlowFactory();
		}
		return factory;
	}

	/**
	 * get the flow from factory collection based on given project name and the
	 * flow name.
	 * 
	 * @param projectName
	 *            - name of the project.
	 * @param flowName
	 *            - name of the flow.
	 * @return
	 */
	public Flow getFlow(String projectName, String flowName) {
		if (_factoryCollection.isEmpty()) {
			return null;
		}
		EventFlowFactory factory = _factoryCollection.get(projectName);
		if (factory == null) {
			return null;
		}
		return factory.getFlowInstance(flowName);
	}
}

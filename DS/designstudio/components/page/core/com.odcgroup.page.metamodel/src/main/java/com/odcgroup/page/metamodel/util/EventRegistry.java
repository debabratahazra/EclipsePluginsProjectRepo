package com.odcgroup.page.metamodel.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.odcgroup.page.metamodel.EventModel;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetEvent;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * This helper class shall be used to retrieve the set of events supported by a
 * widget type.
 * <p>
 * 
 * This class is used by the MetaModelRegistry
 * 
 * @author Alain Tripod
 * @author Gary Hayes
 */
final class EventRegistry {

	/** The map of Widget Types / EventTypes. */
	private Map<WidgetType, Set<EventType>> widgetEventTypesMap = new HashMap<WidgetType, Set<EventType>>();
	
	/** The Map of EventTypes. */
	private Map<String, EventType> eventTypes = new HashMap<String, EventType>();
	
	/** The Map of PropertyTypes for each event type */
	private Map<String, Map<String, PropertyType>> eventPropertyTypes = new HashMap<String, Map<String,PropertyType>>();

	/**
	 * Initializes this registry.
	 * 
	 * @param metamodel
	 *            The metamodel
	 */
	EventRegistry(MetaModel metamodel) {
		EventModel em = metamodel.getEventModel();
		if (em != null) {
			for (WidgetEvent event : em.getEvents()) {
				for (EventType eventType : event.getEventTypes()) {
					bindEventType(event.getWidgetType(), eventType);
				}
			}
		}
		
		for (EventType et : em.getEventTypes()) {
			String etName = et.getName();
		    eventTypes.put(etName, et);
			Map<String,PropertyType> map = new HashMap<String, PropertyType>();
			eventPropertyTypes.put(etName, map);
			for (PropertyType pt : et.getPropertyTypes()) {
				map.put(pt.getName(), pt);
			}
		}
	}

	/**
	 * Binds the event type to the map.
	 * 
	 * @param wt
	 *            The WidgetType
	 * @param et
	 *            The EventType to bind
	 */
	private final void bindEventType(WidgetType wt, EventType et) {
	    getEventTypesFor(wt).add(et);
	}
    
    /**
     * @param wt an WidgetType instance
     * @return a set of supported EventType given a WidgetType 
     */
    Set<EventType> getEventTypesFor(WidgetType wt) {
        Set<EventType> eventTypes = widgetEventTypesMap.get(wt);
        if (eventTypes == null) {
            eventTypes = new HashSet<EventType>();
            widgetEventTypesMap.put(wt, eventTypes);
        }
        return eventTypes;
    }
    
    /**
     * @param wt the widget
     * @param eventTypeName the name of the event type 
     * @return {@code true} if the given widget type support the event type name,
     */
    boolean hasEventType(WidgetType wt, String eventTypeName) {
    	for (EventType type : getEventTypesFor(wt)) {
    		if (type.getName().equals(eventTypeName)) {
    			return true;
    		}
    	}
    	return false;	
    }
	
	/**
	 * Gets the EventType given its name.
	 * 
	 * @param eventName The name of the EventType
	 * @return EventType The EventType
	 */
	EventType findEventType(String eventName) {
	    return eventTypes.get(eventName);
	}
	
	/**
	 * Gets the propertyTypes supported by the designated event
	 * @param eventName the name of an event
	 * @return the property types map
	 */
	Map<String, PropertyType> findEventPropertyTypes(String eventName) {
		Map<String,PropertyType> map = eventPropertyTypes.get(eventName);
		if (map == null) {
			map = new HashMap<String, PropertyType>();
		}
		return Collections.unmodifiableMap(map);
	}
}
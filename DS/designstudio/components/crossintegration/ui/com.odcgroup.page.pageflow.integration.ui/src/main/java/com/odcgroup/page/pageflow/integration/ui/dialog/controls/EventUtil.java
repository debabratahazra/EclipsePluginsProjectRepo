package com.odcgroup.page.pageflow.integration.ui.dialog.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.EventTypeConstants;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.EventNature;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.pageflow.model.Activator;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * Utility class with utilities to manage events
 * 
 * @author pkk
 * 
 */
public class EventUtil {
	
	/** The value for Call-URI parameter for a simplified event with transitionID */
	public static final String SIMPLIFIEDEVENT_TRANSITION_CALLURI_VALUE = "<pageflow:continuation/>";
	
	/** The parameter name for a simplified event with transitionID */
	public static final String FLOW_ACTION_PARAMETER = "flow-action";
	
	
	/**
	 * @param event
	 * @return
	 */
	public static Event changeAdvancedEventToSimple(Event event) {
		event.setEventName(EventTypeConstants.ON_CLICK_EVENT);
		event.setFunctionName(FunctionTypeConstants.SUBMIT_FUNCTION);
		event.setNature(EventNature.SIMPLIFIED);
		
		event.getParameters().clear();
		addParameters(event);	
		
		event.getProperties().clear();
		addProperties(event);
		
		return event;
	}
	
	/**
	 * DS-3963 - Do not erase values already entered by the user
	 *           when the simplified event is transformed to 
	 *           an advanced event.
	 * @param event
	 * @return
	 */
	public static Event changeSimpleToAdvancedEvent(Event event) {
		event.setNature(EventNature.ADVANCED);
		return event;
	}
	
	/**
	 * add all required parameters to the simplified event
	 * @param event
	 */
	private static void addParameters(Event event) {
		for (ParameterType pt : event.getFunctionType().getParameters()) {
			// Create a new parameter
			Parameter p = ModelFactory.eINSTANCE.createParameter();
			p.setName(pt.getName());
			if (pt.getName().equals(ParameterTypeConstants.METHOD)) {
				List<DataValue> list = pt.getType().getValues();
				for (DataValue dataValue : list) {
					if (dataValue.getValue().equals("post")) {
						p.setValue(dataValue.getValue());
					}
				}
			} else if (pt.getName().equals(ParameterTypeConstants.CALL_URI)) {
				p.setValue(SIMPLIFIEDEVENT_TRANSITION_CALLURI_VALUE);
			}
			event.getParameters().add(p);
		}
		
		Parameter param = ModelFactory.eINSTANCE.createParameter();
		param.setName(FLOW_ACTION_PARAMETER);
		param.setUserDefined(true);
		event.getParameters().add(param);
	}
	
	/**
	 * @param event
	 */
	private static void addProperties(Event event) {
		for (PropertyType pt : event.getEventType().getPropertyTypes()) {
			Property p = ModelFactory.eINSTANCE.createProperty();
			p.setTypeName(pt.getName());
			p.setLibraryName(pt.getLibrary().getName());
			event.getProperties().add(p);
		}
	}
	

	/**
	 * creates a simplified event
	 * 
	 * @return Event
	 */
	public static Event createSimpleEvent() {
		Event event = createEvent(EventTypeConstants.ON_CLICK_EVENT);
		event.setFunctionName(FunctionTypeConstants.SUBMIT_FUNCTION);
		event.setNature(EventNature.SIMPLIFIED);

		// add required parameters
		addParameters(event);
		
		// add properties
		addProperties(event);

// DS-3015
//		ISnippetFactory factory = SnippetUtil.getSnippetFactory();
//		IFilterSet fSet = factory.createFilterSet();
//		event.getSnippets().add(fSet.getSnippet());
		
		return event;
	}
	
	
	/**
	 * creates a new search event
	 * @return event
	 * @deprecated
	 */
	public static Event createSearchEvent() {
		Event event = createSimpleEvent();
		//event.setNature(EventNature.SEARCH);
		ISnippetFactory factory = SnippetUtil.getSnippetFactory();
		IQuery query = factory.createQuery();
		event.getSnippets().add(query.getSnippet());
		return event;
	}

	/**
	 * Creates a default advanced event
	 * 
	 * @param widget
	 * @return event
	 */
	public static Event createAdvancedEvent(Widget widget) {
		Event event = createEvent(getEventTypeNames(widget).get(0));
		event.setNature(EventNature.ADVANCED);
		return event;
	}

	/**
	 * Creates a new event with the specified event name.
	 * 
	 * @param eventName
	 *            The event name to creates
	 * @return Event The created event
	 */
	private static Event createEvent(String eventName) {
		Event event = ModelFactory.eINSTANCE.createEvent();
		event.setEventName(eventName);
		return event;
	}

	/**
	 * creates and returns a sorted EventType names list for the given widget
	 * 
	 * @param widget
	 * @return list
	 */
	public static List<String> getEventTypeNames(Widget widget) {
		// Build a sorted list of event types names
		ArrayList<String> names = new ArrayList<String>();
		Set<EventType> eventTypes = MetaModelRegistry.getEventsFor(widget.getType());
		for (EventType et : eventTypes) {
			names.add(et.getName());
		}
		Collections.sort(names);
		return names;
	}

	/**
	 * @param event
	 * @return datatype
	 */
	public static DataType getTargetDataType(Event event) {
		List<ParameterType> parameters = event.getFunctionType().getParameters();
		for (ParameterType parameterType : parameters) {
			if (parameterType.getName().equals(ParameterTypeConstants.TARGET)) {
				return parameterType.getType();
			}
		}
		return null;
	} 
	
	/**
     * @param functionName
     * @param event
     * @return FunctionType
     */
	public static FunctionType getFunctionType(Event event) {
		String functionName = event.getFunctionName();
		if(StringUtils.isEmpty(functionName)) {
			return null;
		}
        Set<FunctionType> functions = MetaModelRegistry.getFunctionsFor(event.getEventType());
        for (FunctionType ft : functions) {
        	if (ft.getName().equals(functionName)) {
        		return ft;
        	}
        }
        return null;
    }

	/**
	 * @param event
	 * @return boolean
	 */
	public static boolean isSimplifiedEvent(Event event) {
		if (isSimplifiedEventWithPageflow(event) || isSimplifiedEventWithTranstion(event)) {
			IQuery query = SnippetUtil.getSnippetFactory().adaptQuerySnippet(event.getSnippets());
			if (query == null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param event
	 * @return
	 */
	public static boolean isSearchEvent(Event event) {
		if (isSimplifiedEventWithPageflow(event) || isSimplifiedEventWithTranstion(event)) {
			IQuery query = SnippetUtil.getSnippetFactory().adaptQuerySnippet(event.getSnippets());
			if (query != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param event
	 * @return boolean
	 */
	public static boolean isSimplifiedEventWithPageflow(Event event) {
		Parameter p = getPageflowParameter(event);
		if (p == null) {
			return false;
		}
		String uri = p.getValue();
		URI pgUri = URI.createURI(uri);
		if (!StringUtils.isEmpty(uri) 
				&& uri.startsWith(ModelURIConverter.SCHEME) 
				&& pgUri.fileExtension().equals(Activator.MODEL_NAME)) {
			return true;
		}
		return false;
	}

	/**
	 * @param event
	 * @return transitionID
	 */
	public static String getTransitionId(Event event) {
		List<Parameter> parameters = event.getParameters();
		for (Parameter parameter : parameters) {
			if (parameter.getName().equals(FLOW_ACTION_PARAMETER) && parameter.isUserDefined()) {
				return parameter.getValue();
			}
		}
		return "";
	}

	/**
	 * @param event
	 * @return string
	 */
	public static String getPageflowURI(Event event) {
		if (isSimplifiedEventWithPageflow(event)) {
			return getPageflowParameter(event).getValue();
		}
		return "";
	}

	/**
	 * @param event
	 * @return parameter
	 */
	public static Parameter getTransitionParameter(Event event) {
		List<Parameter> parameters = event.getParameters();
		for (Parameter parameter : parameters) {
			if (parameter.getName().equals(FLOW_ACTION_PARAMETER) && parameter.isUserDefined()) {
				return parameter;
			}
		}
		return null;
	}
	
	/**
	 * @param event
	 * @return parameter
	 */
	public static Parameter getPageflowParameter(Event event) {
		return event.findParameter(ParameterTypeConstants.CALL_URI);
	}
	
	/**
	 * @param event
	 * @return parameter
	 */
	public static Parameter getTargetParameter(Event event) {
		return event.findParameter(ParameterTypeConstants.TARGET);
	}
	
	/**
	 * @param event
	 * @return parameter
	 */
	public static Parameter getWidgetGroupRefParameter(Event event) {
		return event.findParameter(ParameterTypeConstants.WIDGET_GROUP_REF);
	}
	
	/**
	 * @param event
	 * @return boolean
	 */
	public static boolean isSimplifiedEventWithTranstion(Event event) {
		List<Parameter> parameters = event.getParameters();
		boolean check = false;
		for (Parameter parameter : parameters) {
			if (parameter.getName().equals(FLOW_ACTION_PARAMETER) && parameter.isUserDefined()) {
				check = true;
			}
		}
		if (check) {
			for (Parameter parameter : parameters) {
				String uri = getCallURIValue(parameter);
				if (!StringUtils.isEmpty(uri) 
						&& uri.equals(SIMPLIFIEDEVENT_TRANSITION_CALLURI_VALUE)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param parameter
	 * @return string
	 */
	public static String getCallURIValue(Parameter parameter) {
		if (!parameter.isUserDefined() 
				&& parameter.getName().equals(ParameterTypeConstants.CALL_URI)) {
			return parameter.getValue();
		}
		return "";
	}
	
	/**
	 * @param event
	 */
	public static void removeUserParameters(Event event) {
		List<Parameter> list = new ArrayList<Parameter>();
		list.addAll(event.getParameters());
		for (Parameter parameter : list) {
			if (parameter.isUserDefined()) {
				event.getParameters().remove(parameter);
			}
		}
	}
	
	/**
	 * @param parameter
	 * @return string
	 */
	public static boolean checkTargetValue(Parameter parameter) {
		DataType dataType = getTargetDataType(parameter.getEvent());
		if (parameter.getName().equals(ParameterTypeConstants.TARGET)) {
			String paramVal = parameter.getValue();
			boolean found = false;
			for(DataValue value :dataType.getValues()) {
				if (value.getValue().equals(paramVal)) {
					found = true;
				}
			}
			if (!found) {
				return true;
			}
		}
		return false;
	}

	/**
	 * change the  event type .event name and function name. 
	 */
	public static Event changeEvenType(Event event,String eventName,String functionName){
		
			event.setEventName(eventName);
			event.setFunctionName(functionName);
			event.setNature(EventNature.SIMPLIFIED);
			addParameters(event);	
			addProperties(event);
			return event;
		}
	
}

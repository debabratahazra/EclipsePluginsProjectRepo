package com.odcgroup.page.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.metamodel.EventTemplate;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.ParameterTemplate;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyTemplate;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.EventNature;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;

/**
 * Creates Widgets given a template.
 * 
 * @author Gary Hayes
 */
public class WidgetFactory {

	/** An EMF lock to synchronize write accesses to widgets */
	//private static Lock widgetLock = new Lock();

	/** The template used to create the Widget. */
	private WidgetTemplate template;

	/**
	 * Creates a new WidgetFactory.
	 */
	public WidgetFactory() {
	}
	
	/**
	 * Creates a Widget.
	 * 
	 * @param type The type of Widget to create
	 * @return Widget The newly created Widget
	 */
	public Widget create(WidgetType type) {
		Widget widget = createEmptyWidget(type);
		List<Property> properties = createProperties(type);
		widget.getProperties().addAll(properties);		

		// initialize the identifier
		widget.setID(UniqueIdGenerator.generateId(widget));

		return widget;
	}

	/**
	 * Creates a Widget given a template.
	 * 
	 * @param template The WidgetTemplate to use to create the Widget
	 * @return Widget The newly created Widget
	 */
	public Widget create(WidgetTemplate template) {
		Assert.isNotNull(template);
		
		this.template = template;
		
		Widget widget = create(template.getType());
		createEvents(widget, template);
		createContents(widget);
		updateValuesFromTemplate(widget);
		
		return widget;
	}
	
	/**
	 * Updates a Widget with the properties which have been defined in the
	 * metamodel. This also updates all the contained Widgets. This is
	 * necessary when we try to reload a previously saved Widget which
	 * has since had properties added to or removed from it.
	 * 
	 * @param widget The Widget to update
	 */
	public void update(Widget widget) {
		if (widget != null) {
			// Call the recursive method
			updateWidget(widget);
		}
	}

	/**
	 * Creates a Widget without any properties.
	 * 
	 * @param type The WidgetType
	 * @return Widget The newly created Widget
	 */
	private Widget createEmptyWidget(WidgetType type) {
		Widget widget = getModelFactory().createWidget();
		widget.setTypeName(type.getName());
		widget.setLibraryName(type.getLibrary().getName());
		return widget;
	}

	/**
	 * Creates the properties defined in the template and initialises their default values.
	 * 
	 * @param type The WidgetType
	 * @return List of Property Objects
	 */
	private List<Property> createProperties(WidgetType type) {
		List<Property> allProperties = new ArrayList<Property>();
		for (PropertyType pt : type.getAllPropertyTypes().values()) {
			Property p = createProperty(pt);
			allProperties.add(p);
		}
		return allProperties;
	}
	
	/**
	 * Creates a Property correctly initialized with the default value.
	 * 
	 * @param propertyType The PropertyType
	 * @return Property The newly created Property
	 */
	private Property createProperty(PropertyType propertyType) {
		Property p = getModelFactory().createProperty();		
		p.setTypeName(propertyType.getName());
		p.setLibraryName(propertyType.getLibrary().getName());
		p.setValue(propertyType.getDefaultValue());
		return p;
	}
	
	/**
	 * Creates the events for the Widget.
	 * 
	 * @param widget The Widget to create the events for
	 * @param wt The WidgetTemplate
	 */
	private void createEvents(Widget widget, WidgetTemplate wt) {
		for (EventTemplate et : wt.getEventTemplates()) {
			String eventName = et.getEventType();
			Event event = getModelFactory().createEvent();
			FunctionType ft = et.getFunctionType();
			event.setEventName(eventName);
			event.setFunctionName(ft.getName());
			event.setNature(EventNature.get(et.getNature()));
			if (!hasEvent(widget, eventName)) {
				widget.getEvents().add(event);				
				createParameters(event, et); // from the template
				// add missing parameters defined in the model.
				for (ParameterType pt : et.getFunctionType().getParameters()) {
					String name = pt.getName();
					if (event.findParameter(name) == null) {
						Parameter p = getModelFactory().createParameter();
						p.setName(name);
						p.setValue(pt.getDefaultValue());
						event.getParameters().add(p);
					}
				}

			}
		}
	}
	
	/**
	 * Does our widget already contains the event.
	 * 
	 * @param widget
	 * 			The widget
	 * @param event
	 * 			The event name
	 * @return boolean return true if our widget already contains the event
	 */
	private boolean hasEvent(Widget widget, String event) {
		List<Event> events = widget.getEvents();
		for (Event ev : events) {
			if (ev.getEventName().equals(event)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Creates the event parameters.
	 * 
	 * @param e The Event
	 * @param et The EventTemplate
	 */
	private void createParameters(Event e, EventTemplate et) {
		for (ParameterTemplate pt : et.getParameterTemplates()) {
			Parameter p = getModelFactory().createParameter();
			String name = pt.getType() == null ? pt.getName() : pt.getType().getName();
			p.setName(name);
			p.setValue(pt.getValue());
			p.setUserDefined(pt.isUserDefined());
			e.getParameters().add(p);
		}
	}

	/**
	 * Updates the default property values of the widget with those contained in the template.
	 * 
	 * @param widget
	 *            The Widget to update
	 */
	private void updateValuesFromTemplate(Widget widget) {
		if (template == null) {
			// Nothing to update
			return;
		}

		// Update the default values
		for (PropertyTemplate templateProperty : template.getPropertyTemplates()) {
			Property widgetProperty = widget.findProperty(templateProperty.getType());
			if (widgetProperty == null) {
				StringBuffer message = new StringBuffer();
				message.append("The property [");
				message.append(templateProperty.getType().getName());
				message.append("] is not defined within the widget [");
				message.append(widget.getLibraryName());
				message.append(":");
				message.append(widget.getTypeName());
				message.append("]");
				System.out.println(message.toString());
			} else {
			  widgetProperty.setValue(templateProperty.getValue());
			  widgetProperty.setReadonly(templateProperty.isReadonly());
			}
		}
	}
	
	/**
	 * Creates the contents of the Widget. These are nested Widgets.
	 * 
	 * @param widget The Widget to create the contents for
	 */
	@SuppressWarnings("unchecked")
	private void createContents(Widget widget) {
		for (Iterator it = template.getContents().iterator(); it.hasNext();) {
			WidgetTemplate wt = (WidgetTemplate) it.next();
			WidgetFactory wf = new WidgetFactory();
			Widget w = wf.create(wt);
			createEvents(w, wt);

			widget.getContents().add(w);
		}		
	}

	/**
	 * Gets the ModelFactory instance.
	 * 
	 * @return ModelFactory
	 */
	private static ModelFactory getModelFactory() {
		return ModelFactory.eINSTANCE;
	}

	private void updateProperties(Map<String, PropertyType> refPropTypes,
			List<Property> propsTobeUpdated) {
		List<Property> propsToBeRemoved = new ArrayList<Property>();
		List<PropertyType> propsToBeAdded = new ArrayList<PropertyType>();
		propsToBeAdded.addAll(refPropTypes.values());

		Set<String> duplicates = new HashSet<String>();

		for (Property p : propsTobeUpdated) {
			PropertyType pt = p.getType();
			if (pt == null) {
				// PropertyType no longer exists
				propsToBeRemoved.add(p);
			} else {
				String name = pt.getName();
				if (refPropTypes.containsKey(name)) {
					propsToBeAdded.remove(pt);
				} else {
					propsToBeRemoved.add(p);
				}
				if (duplicates.contains(name)) {
					propsToBeRemoved.add(p);
				} else {
					duplicates.add(name);
				}
			}
		}

		// Removes all obsolete properties
		if (propsToBeRemoved.size() > 0) {
			propsTobeUpdated.removeAll(propsToBeRemoved);
		}

		// Adds new properties
		for (PropertyType pt : propsToBeAdded) {
			Property newProp = createProperty(pt);
			propsTobeUpdated.add(newProp);
		}
	}
	
	private void updateEventParameters(Map<String, ParameterType> refParamTypes, List<Parameter> paramsTobeUpdated) {
		List<Parameter> paramsToBeRemoved = new ArrayList<Parameter>();
		List<ParameterType> paramsToBeAdded = new ArrayList<ParameterType>();
		paramsToBeAdded.addAll(refParamTypes.values());
		
		Set<String> duplicates = new HashSet<String>();
		
		for (Parameter param : paramsTobeUpdated) {
			ParameterType pt = refParamTypes.get(param.getName());
			if (pt == null && !param.isUserDefined()) {
				// PropertyType no longer exists
				paramsToBeRemoved.add(param);
			} else {
				String name = param.getName();
				if (refParamTypes.containsKey(name)) {
					paramsToBeAdded.remove(pt);
				}
				if (duplicates.contains(name)) {
					paramsToBeRemoved.add(param);
				} else {
					duplicates.add(name);
				}
			}
		}
		
		// Removes all obsolete properties 
		if (paramsToBeRemoved.size() > 0) {
			paramsTobeUpdated.removeAll(paramsToBeRemoved);
		}
		
		// Adds new properties 
		for (ParameterType pt : paramsToBeAdded) {
			Parameter newParam = getModelFactory().createParameter();
			newParam.setName(pt.getName());
			newParam.setValue(pt.getDefaultValue());
			paramsTobeUpdated.add(newParam);
		}

	}
	
	private void update(Event event) {
		String eventName = event.getEventName(); 
		updateProperties(MetaModelRegistry.findEventPropertyTypes(eventName), event.getProperties());
		updateEventParameters(MetaModelRegistry.findFunctionParameterTypes(event.getFunctionName()), event.getParameters());
		for (Snippet snippet: event.getSnippets()) {
			update(snippet);
		}
	}
	
	private void update(Snippet snippet) {
		updateProperties(MetaModelRegistry.findSnippetPropertyTypes(snippet.getTypeName()), snippet.getProperties());
		// Now iterate over the child Widgets
		for (Snippet s : snippet.getContents()) {
			update(s);
		}
	}

	/**
	 * Updates a Widget with the properties which have been defined in the
	 * metamodel. This also updates all the contained Widgets. This is
	 * necessary when we try to reload a previously saved Widget which
	 * has since had properties added to or removed from it.
	 * 
	 * @param widget The Widget to update
	 */
	private boolean updateWidget(Widget widget) {
		
//		try {
//			widgetLock.uiSafeAcquire(false);
//		} catch (InterruptedException ex) {
//			ex.printStackTrace();
//			return false;
//		}
		
		WidgetType wt = widget.getType();
		if(wt==null) {
			return false;  // @see xtext factory
		}
		updateProperties(wt.getAllPropertyTypes(), widget.getProperties());
		
		for (Event event : widget.getEvents()) {
			update(event);
		}
		
		for (Snippet snippet: widget.getSnippets()) {
			update(snippet);
		}

		// Now iterate over the child Widgets
		for (Widget w : widget.getContents()) {
			updateWidget(w);
		}
		
//		widgetLock.release();
		return true;
	}
	

}

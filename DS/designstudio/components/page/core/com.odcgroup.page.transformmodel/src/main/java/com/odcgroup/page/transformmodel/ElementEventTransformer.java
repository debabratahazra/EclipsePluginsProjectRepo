package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.I18nConstants.I18N_ATTRIBUTE_NAME;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_PREFIX;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.EventTypeConstants;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.transformmodel.snippet.SnippetTransformer;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * Transforms events using the new-style (onevent) model to Xml. This creates
 * Xml of the form:
 *
 * <xgui:onevent type="eventName"> <xgui:functionName paramName1="paramValue1"
 * paramName2="paramValue2" /> <xgui:functionName paramName3="paramValue3"
 * paramName4="paramValue4" /> </xgui:onevent>
 *
 * Note that in the metamodel an Event contains a single Function. When we map
 * this to Xml we need to group the functions by their event name.
 *
 * @author atr, pkk
 */
public class ElementEventTransformer {

	/** */
	private boolean tabWidgetEvents = false;
	/** */
	private boolean standalone = false;
	/** */
	private boolean dynamicTabbedPane = false;
	/** */
	private boolean hideEmptyTab = false;
	/** */
	private Widget parent = null;

	/**
	 * Creates a new EventTransformer.
	 */
	public ElementEventTransformer() {
	}

	/**
	 * @param tabWidgetEvents
	 * @param standalone
	 */
	public ElementEventTransformer(boolean tabWidgetEvents, boolean standalone) {
		this.tabWidgetEvents = tabWidgetEvents;
		this.standalone = standalone;
	}

	/**
	 * You have to pass <code>true</true> to generate events for dynamic tabbed pane
	 * @param dynamic
	 */
	public final void setDynamicTabbedPane(boolean dynamic) {
		this.dynamicTabbedPane = dynamic;
	}

	/**
	 * You have to pass <code>true</true> to generate events for dynamic tab
	 * @param hideEmptyTab
	 */
	public final void setHideEmptyTab(boolean hideEmptyTab) {
		this.hideEmptyTab = hideEmptyTab;
	}

	/**
	 * Transforms the Widget containing the events.
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The Widget to transform
	 * @exception CoreException
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		transform(context, widget, new ArrayList<Event>());
	}
	
	
	/**
	 * @param context
	 * @param widget
	 * @param replace
	 * @throws CoreException
	 */
	public void transform(WidgetTransformerContext context, Widget widget, List<Event> replace) throws CoreException {
		Map<String, List<Event>> groupedEvents = groupEvents(widget, replace);
		this.parent = widget;
		for (Map.Entry<String, List<Event>> entry : groupedEvents.entrySet()) {
			transformEvents(context, entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * @param context
	 * @param events
	 * @param widget
	 * @throws CoreException
	 */
	public void transform(WidgetTransformerContext context, List<Event> events, Widget widget) throws CoreException  {
		this.parent = widget;
		for (Event entry: events) {
			List<Event> list = new ArrayList<Event>();
			list.add(entry);
			transformEvents(context, entry.getEventName(), list);
		}
	}

	/**
	 * @param context
	 * @param namespace
	 * @param elementName
	 * @return
	 */
	protected Element createElement(WidgetTransformerContext context, String namespace, String elementName) {
		Document d = context.getDocument();
		Namespace ns = context.getTransformModel().findNamespace(namespace);
		Element e = d.createElementNS(ns.getUri(), elementName);
		e.setPrefix(ns.getPrefix());
		return e;
	}

	/**
	 * @param context
	 * @param widget
	 * @param translation
	 * @param kind
	 */
	protected void transformTranslation(WidgetTransformerContext context, ITranslation translation, ITranslationKind kind) {
		if(!translation.messagesFound(kind)) {
			return;
		}
		String sKey = context.getTranslationKey(translation, kind);
		if (sKey != null) {
			String transElemName = null;
			if (ITranslationKind.NAME.equals(kind)) {
				transElemName = XGuiConstants.XGUI_TEXT_ELEMENT;
			} else if (ITranslationKind.TEXT.equals(kind)) {
				transElemName = XGuiConstants.XGUI_TOOLTIP_ELEMENT;
			}
			if (transElemName != null) {
				Element transElem = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, transElemName);
				context.getParentElement().appendChild(transElem);
				Element i18nElem = createElement(context, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
				transElem.appendChild(i18nElem);
				i18nElem.setTextContent(sKey);
			}
		}
	}


	/**
	 * Transforms a List of events which have the same event name.
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param eventName
	 *            The name of the events
	 * @param events
	 *            The Events to transform
	 * @exception CoreException
	 */
	private void transformEvents(WidgetTransformerContext context, String eventName, List<Event> events)
			throws CoreException {
		Element eventElement = createEventElement(context, eventName);

		// translation key
		if (events.size() >= 1) {
			// Find the first existing translation
			String key = null;
			for (Event event:events) {
		        key = context.getTranslationKey(event, ITranslationKind.NAME);
		        if (key != null) {
		        	break;
		        }
			}
	        if (key != null) {
	              TransformUtils.convertToAttribute(context, eventElement, "confirm", key);
	              Attr a = context.getDocument().createAttribute(I18N_NAMESPACE_PREFIX + ":" + I18N_ATTRIBUTE_NAME);
	              a.setValue("confirm");
	              eventElement.setAttributeNode(a);
	        }
		}

		TransformUtils.appendChild(context, eventElement);

		Element oldParent = context.setParentElement(eventElement);

		transformProperties(context, events);
		transformFunctions(context, events);

		context.setParentElement(oldParent);
	}

	/**
	 * Creates the event Element <xgui:onevent type="change">.
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param eventName
	 *            The name of the Event to transform
	 * @return Element The newly created Element. This needs to be appended to
	 *         the Widget's Element
	 */
	private Element createEventElement(WidgetTransformerContext context, String eventName) {
		TransformModel tm = context.getTransformModel();
		Namespace ns = tm.getDefaultNamespace();

		// Create the <xgui:onevent type="change"> element
		Element eventElement = context.getDocument().createElementNS(ns.getUri(),
				TransformerConstants.ONEVENT_ELEMENT_NAME);
		eventElement.setPrefix(ns.getPrefix());

		// Add the type
		EventTransformation et = tm.getEventTransformations().findEventTransformation(eventName);

		if (!StringUtils.isEmpty(eventName)) {
			Attr a = context.getDocument().createAttribute(TransformerConstants.TYPE_ATTRIBUTE_NAME);
			a.setValue(et.getTypeName());
			eventElement.setAttributeNode(a);
		}

		return eventElement;
	}

	/**
	 * Transforms the fucntions of the Events.
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param events
	 *            The Events to transform
	 */
	private void transformFunctions(WidgetTransformerContext context, List<Event> events) {
		for (Event event : events) {
			transformFunction(context, event);
		}
	}

	/**
	 * Transforms the function (<xgui:functionName>...).
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param event
	 *            The Event to transform
	 * @param functionElement
	 */
	private void transformSnippet(WidgetTransformerContext context, Event event, Element functionElement) {
		if (event.getSnippets().isEmpty()) {
			return;
		}
		SnippetTransformer transformer = new SnippetTransformer();
		for (Snippet snippet : event.getSnippets()) {
			transformer.transform(context, snippet, functionElement, parent, false);
		}
	}

	/**
	 * Transform the properties of the events
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param events
	 *            The list of event
	 * @throws CoreException
	 */
	private void transformProperties(WidgetTransformerContext context, List<Event> events) throws CoreException {
		for (Event event : events) {
			transformEventProperties(context, event);
		}

	}

	/**
	 * Transform the properties of the specified event
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param event
	 *            The event
	 * @throws CoreException
	 */
	private void transformEventProperties(WidgetTransformerContext context, Event event) throws CoreException {
	}

	/**
	 * Transforms the function (<xgui:functionName>...).
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param event
	 *            The Event to transform
	 */
	private void transformFunction(WidgetTransformerContext context, Event event) {
		if (StringUtils.isEmpty(event.getFunctionName())) {
			return;
		}

		Namespace ns = context.getTransformModel().getDefaultNamespace();

		// Create the <xgui:functionName> element
		Element functionElement = context.getDocument().createElementNS(ns.getUri(), event.getFunctionName());
		functionElement.setPrefix(ns.getPrefix());
		//DS-4062:		if (event.getEventName().equals(EventTypeConstants.ON_CLICK_EVENT)
		//DS-4062:				&& event.getFunctionName().equals(FunctionTypeConstants.SUBMIT_FUNCTION) && !standalone) {
		//DS-4062:			functionElement.setAttribute("stabs", "true");
		//DS-4062:		}
		context.getParentElement().appendChild(functionElement);

		transformParameters(context, functionElement, event);
		transformSnippet(context, event, functionElement);
	}

	/**
	 * Transforms the parameters of the function.
	 *
	 * @param context
	 *            The WidgetTransformerContext
	 * @param functionElement
	 *            The DOM element corresponding to the function
	 * @param event
	 *            The Event to transform
	 */
	private void transformParameters(WidgetTransformerContext context, Element functionElement, Event event) {

		FunctionType ft = event.getFunctionType();

		for (Parameter parameter : event.getParameters()) {
			if (!StringUtils.isEmpty(parameter.getValue())) {
				String name = parameter.getName();
				String value = parameter.getValue();
				SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
				if (expander != null) {
					Widget parentWidget = parameter.getEvent().getWidget();
					if (parentWidget == null) {
						parentWidget = parent;
					}
					value = expander.substitute(value, parentWidget);
				}

				if (parameter.isUserDefined()) {
					transformUserParameter(context, functionElement, name, value);
				} else {
					ParameterType pt = ft.findParameterType(name);
					if (pt!=null) {
						if (! value.equals(pt.getDefaultValue())) {
							TransformUtils.convertToAttribute(context, functionElement, name, value);
						}
					}
				}
			}
		}
	}

	/**
	 * @param context
	 * @param functionElement
	 * @param name
	 * @param value
	 */
	private void transformUserParameter(WidgetTransformerContext context, Element functionElement, String name,
			String value) {
		TransformModel tm = context.getTransformModel();
		Namespace ns = tm.getDefaultNamespace();

		// Create the <xgui:param name="f.nature.eq"> element
		Element element = context.getDocument().createElementNS(ns.getUri(), TransformerConstants.EVENT_USER_PARAMETER);
		element.setPrefix(ns.getPrefix());
		functionElement.appendChild(element);

		Attr nameAttrib = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
		nameAttrib.setValue(name);
		element.setAttributeNode(nameAttrib);

		element.setTextContent(value);
	}	
	

	/**
	 * Creates a Map of events grouped according to their name.
	 *
	 * @param widget
	 *            The Widget to create the Event group for
	 * @return Map The map of events
	 */
	private Map<String, List<Event>> groupEvents(Widget widget, List<Event> replace) {
		Map<String, List<Event>> groupedEvents = new LinkedHashMap<String, List<Event>>();
		Event tabEvent = getUserDefinedTabEvent(widget);
		// in case of tab widget, and no user defined event available,
		// include a default tab event
		if (tabWidgetEvents && tabEvent == null && widget.getEvents().size() == 0) {
			List<Event> events = new ArrayList<Event>();			
			if (!standalone) {
				events.add(createContainerEvent()); // tabActive
			}
			Event e = createTabEvent(widget, null); // default event
			events.add(e);

			groupedEvents.put(e.getEventName(), events);
		} 
		
		//DS-4633
		if (tabWidgetEvents && !widget.getEvents().isEmpty()){
			List<Event> events = new ArrayList<Event>();
			events.add(createContainerEvent());
			groupedEvents.put(EventTypeConstants.ON_CLICK_EVENT, events);
		}

		for (Event e : widget.getEvents()) {
			String name = e.getEventName();
			List<Event> events = groupedEvents.get(name);
			if (events == null) {
				events = new ArrayList<Event>();
				groupedEvents.put(name, events);
			}
			if (!replace.isEmpty()) {
				Event rep = getEventByName(name, replace);
				if (rep != null) {
					e = rep;
				}
			}
			events.add(e);
		}

		if (dynamicTabbedPane) {
			List<Event> events = new ArrayList<Event>();
			events.add(createContainerEvent());
			Event e = createTabEvent(widget, tabEvent);
			events.add(e);
			groupedEvents.put(e.getEventName(), events);
		}
		return groupedEvents;
	}
	
	/**
	 * @param udEvent
	 */
	private List<Parameter> getOtherParameters(Event udEvent) {		
		List<String> props = TabbedPaneTransformerUtil.getDynamicTabCustomEventFilterParam();		
		List<Parameter> fparams = new ArrayList<Parameter>();
		for (Parameter param : udEvent.getParameters()) {
			if (!props.contains(param.getName())) {
				fparams.add(param);
			}
		}		
		return fparams;		
	}
	
	/**
	 * @param name
	 * @param events
	 * @return
	 */
	private Event getEventByName(String name, List<Event> events) {
		for (Event event : events) {
			if (name.equals(event.getEventName())) {
				return event;
			}
		}
		return null;
	}

	/**
	 * @param widget
	 * @return event
	 */
	private Event getUserDefinedTabEvent(Widget widget) {
		if (!tabWidgetEvents) {
			return null;
		}
		for (Event event : widget.getEvents()) {
			String name = event.getEventName();
			if (EventTypeConstants.ON_CLICK_EVENT.equals(name)
					&& FunctionTypeConstants.SUBMIT_FUNCTION.equals(event.getFunctionName())) {
				List<Parameter> params = new ArrayList<Parameter>();
				for (Parameter param : event.getParameters()) {
					String paramName = param.getName();
					if (ParameterTypeConstants.CALL_URI.equals(paramName)) {
						params.add(param);
					} else if (ParameterTypeConstants.FLOW_ACTION_PARAMETER.equals(paramName)) {
						params.add(param);
					}
				}
				if (params.size() >= 1) {
					return event;
				}
			}
		}
		return null;
	}

	/**
	 * create a event incase of a tab (container mode)
	 *
	 * @return event
	 */
	private Event createContainerEvent() {
		Event event = ModelFactory.eINSTANCE.createEvent();
		event.setEventName(EventTypeConstants.ON_CLICK_EVENT);
		event.setFunctionName(FunctionTypeConstants.SETTABACTIVE_FUNCTION);
		return event;
	}

	/**
	 * create the default event for tab
	 *
	 * @return Event
	 */
	private Event createTabEvent(Widget tab, Event customEvent) {
		Event event = ModelFactory.eINSTANCE.createEvent();
		event.setEventName(EventTypeConstants.ON_CLICK_EVENT);
		if (standalone) {
			event.setFunctionName(FunctionTypeConstants.SETTABACTIVE_FUNCTION);
		} else {
			event.setFunctionName(FunctionTypeConstants.SUBMIT_FUNCTION);
			
			if(tab.getRootWidget().getTypeName().equals(WidgetTypeConstants.MODULE)&&!standalone){
				//DS-4964 set the only-changed parameter.
				Parameter onlyChanged = ModelFactory.eINSTANCE.createParameter();
				onlyChanged.setName(ParameterTypeConstants.ONLY_CHANGED);
				onlyChanged.setValue("true");
				event.getParameters().add(onlyChanged);
				//DS-4964 set the target parameter.
				Parameter target = ModelFactory.eINSTANCE.createParameter();
				target.setName(ParameterTypeConstants.TARGET);
				target.setValue(ParameterTypeConstants.TARGET_DEFAULT_VALUE);
				event.getParameters().add(target);
				//DS-4964 set the widget-group-ref parameter.
				Parameter groupRef = ModelFactory.eINSTANCE.createParameter();
				groupRef.setName(ParameterTypeConstants.WIDGET_GROUP_REF);
				groupRef.setValue(ParameterTypeConstants.WIDGET_GROUP_REF_FORM_VALUE);
				event.getParameters().add(groupRef);
			}else{
				// the call-uri DS-4964
				Parameter callUri = ModelFactory.eINSTANCE.createParameter();
				callUri.setName(ParameterTypeConstants.CALL_URI);
				callUri.setValue(ParameterTypeConstants.SIMPLIFIEDEVENT_TRANSITION_CALLURI_VALUE);
				event.getParameters().add(callUri);
			}	
			
			Parameter flowAction = ModelFactory.eINSTANCE.createParameter();
			flowAction.setName(ParameterTypeConstants.FLOW_ACTION_PARAMETER);
			flowAction.setValue("reload");
			flowAction.setUserDefined(true);
			event.getParameters().add(flowAction);		
			
			//DS-5926
			Widget tPane = tab.getParent();
			if (loadAsynchronously(tPane)) {
				Parameter sync = ModelFactory.eINSTANCE.createParameter();
				sync.setName(ParameterTypeConstants.ASYNCHRONOUS_PARAMETER);
				sync.setValue("true");
				sync.setUserDefined(true);
				event.getParameters().add(sync);
			}

			if (dynamicTabbedPane) {
				ISnippetFactory factory = SnippetUtil.getSnippetFactory();
				IFilterSet filterset = factory.createFilterSet();
				String dsname = TabbedPaneTransformerUtil.getDatasetName(tab.getParent());
				filterset.setTargetDataset(dsname);
				filterset.setLevel(TabbedPaneTransformerUtil.getFilterLevel(tPane));
				if (hideEmptyTab) {
					IFilter filter = factory.createFilter(filterset);
					filter.setDatasetAttribute(TabbedPaneTransformerUtil.getFilteredAttributeName(tab.getParent()));
					filter.setEditMode("hidden");
					filter.setOperator("EQUAL");
					filter.setValue(TabbedPaneTransformerUtil.getDynamicTabFilterValue(tab));
					filter.setOtherValue("");
					filterset.getSnippet().getContents().add(filter.getSnippet());
				}
				event.getSnippets().add(filterset.getSnippet());				

				//DS-5529 custom events
				if (customEvent != null) {
					Event cevent = EcoreUtil.copy(customEvent);
					// merge parameters
					List<Parameter> parameters = getOtherParameters(cevent);
					for (Parameter parameter : parameters) {
						String paramName = parameter.getName();
						if (ParameterTypeConstants.FLOW_ACTION_PARAMETER.equals(paramName)) {
							mergeParameter(ParameterTypeConstants.FLOW_ACTION_PARAMETER, parameter, event, "reload");
						} else if (ParameterTypeConstants.WIDGET_GROUP_REF.equals(paramName)) {
							mergeParameter(ParameterTypeConstants.WIDGET_GROUP_REF, parameter, 
									event, ParameterTypeConstants.WIDGET_GROUP_REF_FORM_VALUE);
						} else if ("param".equals(paramName) 
								&& parameter.getValue().startsWith("flow-action=")) {
							mergeParamParameter(parameter, parameters, event);							
						} else {
							event.getParameters().add(parameter);
						}
						
					}
					
					// merge filtersets
					List<IFilterSet> filtersets = SnippetUtil.getFilterSets(cevent);
					for (IFilterSet fset : filtersets) {
						if (dsname.equals(fset.getTargetDatasetName()) && fset.getLevel() == 99) {
							List<IFilter> filters = fset.getFilters();
							for (IFilter filter : filters) {
								fset.getSnippet().getContents().add(filter.getSnippet());
							}
						} else {
							event.getSnippets().add(fset.getSnippet());
						}
					}
					
					// merge query
					IQuery query = SnippetUtil.getQuery(cevent);
					if (query != null)
						event.getSnippets().add(query.getSnippet());
				}
			
			}

		}
		return event;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private boolean loadAsynchronously(Widget widget) {
		boolean asyncMode = false;
		if (widget.getTypeName().equals(WidgetTypeConstants.TABBED_PANE)) {
			Property prop = widget.findProperty(PropertyTypeConstants.INCLUDE_LOADING_MODE);
			if (prop != null) {
				asyncMode = "async".equals(prop.getValue());
			}
		}
		return asyncMode;
	}
		
	
	/**
	 * @param parameter
	 * @param parameters
	 * @param e
	 */
	private void mergeParamParameter(Parameter parameter, List<Parameter> parameters, Event e) {
		boolean udflowfound = false;
		for (Parameter param : parameters) {
			if (ParameterTypeConstants.FLOW_ACTION_PARAMETER.equals(param.getName())) {
				udflowfound = true;
				break;
			}
		}
		if (!udflowfound) {
			String flow = "flow-action=";
			if ("param".equals(parameter.getName()) 
					&& parameter.getValue().startsWith(flow) 
					&& !"flow-action=reload".equals(parameter.getValue())) {
				String value = parameter.getValue();
				value = value.substring(flow.length());
				e.findParameter(ParameterTypeConstants.FLOW_ACTION_PARAMETER).setValue(value);
			}
		}
	}
	
	/**
	 * @param parameterName
	 * @param parameter
	 * @param e
	 * @param value
	 */
	private void mergeParameter(String parameterName, Parameter parameter, Event e, String value) {
		if (parameterName.equals(parameter.getName()) 
				&& !StringUtils.isEmpty(parameter.getValue())
				&& !value.equals(parameter.getValue())) {
			e.findParameter(parameterName).setValue(parameter.getValue());
		} 
	}

}
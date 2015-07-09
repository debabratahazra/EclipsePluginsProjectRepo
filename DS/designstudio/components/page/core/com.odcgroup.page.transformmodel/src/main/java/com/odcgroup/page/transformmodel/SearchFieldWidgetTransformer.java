package com.odcgroup.page.transformmodel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IParameterSnippet;
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.transformmodel.snippet.SnippetTransformer;
import com.odcgroup.page.transformmodel.util.DomainObjectUtils;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * @author pkk
 *
 */
public class SearchFieldWidgetTransformer extends BaseWidgetTransformer {
	
	private static final String FLOW_CHANGE = "flow-change";
	private static final String FLOW_ACTION = "flow-action";
	
//	private static final String FLOW_CHANGE_CONTEXT_RELOAD = "flow-change=contextReload";
//	private static final String FLOW_ACTION_SEARCH = "flow-action=search";
	
	private ISearchField searchField;
	private Element searchfield = null;

	/**
	 * @param type
	 */
	public SearchFieldWidgetTransformer(WidgetType type) {
		super(type);
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget)throws CoreException {
		this.searchField = WidgetHelper.getSearchField(widget);
		
		boolean autoComplete = false;
		if (searchField.isAutoCompleteOnly() || searchField.isAutoCompleteAndSearch()) {
			searchfield = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, "autocomplete");	
			autoComplete = true;
		} else if (searchField.isSearchOnly()) {
			searchfield = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, "searchfield");				
		}
		TransformUtils.appendChild(context, searchfield);
		
		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(searchfield);	
		transformTranslations(context, widget);
		transformProperties(context, widget);
		if (autoComplete) {	
			
			// render postValue
			String domAttr = null;
			if (context.isEditableTableTree()) {
				domAttr = context.getEditableDatasetAttribute();
			} else {
				domAttr = searchField.getDomainAttribute();
			}
			transformParameter(context, "postValue", domAttr, searchfield);
			transformParameter(context, "textValue", domAttr, searchfield);
			transformParameter(context, "scope-id", "<scope:get-id/>", searchfield);			
			
			// render parameters from query & filterset snippets of search event
			List<Event> events = widget.getEvents();
			Event search = getSearchEvent(events, widget, true);
			if (search != null) {
				SnippetTransformer transformer = new SnippetTransformer();
				for (Snippet snippet : search.getSnippets()) {
					transformer.transform(context, snippet, searchfield, widget, true);
				}
			}
			//render parameters
			List<IParameterSnippet> params = searchField.getParameters();
			for (IParameterSnippet param : params) {
				transformParameter(context, param.getName(), param.getValue(), searchfield);
			}
			//render onchange event for auto-complete
			if (search != null)
				transformAutoCompleteEvents(context, search, widget);
		}
		if (searchField.isAutoCompleteAndSearch()) {
			Event search = getSearchEvent(widget.getEvents(), widget, true);
			addParameter(search, "Query.alwaysDisplayResult", "true", true);
			List<Event> events = new ArrayList<Event>();
			events.add(search);
			getEventTransformer().transform(context, widget, events);
		}

		if (searchField.isSearchOnly()) {
			List<Event> events = widget.getEvents();
			Event search = getSearchEvent(events, widget, true);
			if (search != null) {
				// if the search event found then create events
				transformSearchOnlyEvents(context, search, widget);
			} else {
				// render specified events
				transformEvents(context, widget);				
			}
		}

		//TransformUtils.convertToAttribute(context, searchfield, "id", widget.getID());
		
		transformChildren(context, widget);

		context.setParentElement(oldParent);
	}
	
	/**
	 * @param search
	 * @return
	 */
	private Event createMatchingEvent(Event search, String eventName) {
		Event ev = makeEvent(eventName, search);
		addParameter(ev, "Query.exactMatching", "false", true);
		addParameter(ev, "only-changed", "true", false);
		return ev;
	}
	
	/**
	 * @param context
	 * @param search
	 * @param widget
	 * @throws CoreException
	 */
	private void transformAutoCompleteEvents(WidgetTransformerContext context, 
			Event search, Widget widget) throws CoreException {
		List<Event> dummies = new ArrayList<Event>();
		// change event
		if (!context.isEditableTableTree()) {
			dummies.add(getChangeEvent(search));
		}
		// select event				
		Event select = makeEvent("OnSelect", search);
		removeParameter(select.getParameters(), "Query.alwaysDisplayResult");
		addParameter(select, "Query.exactMatching", "true", true);
		dummies.add(select);	
		ElementEventTransformer eTransformer = new ElementEventTransformer();
		eTransformer.transform(context, dummies, widget);
	}
	
	/**
	 * @param search
	 * @return
	 */
	private Event getChangeEvent(Event search) {
		Event change = makeEvent("OnChange", search);
		List<Parameter> params = change.getParameters();
		removeParameter(params, "widget-group-ref");
		
		Parameter param = change.findParameter("param");
		if (param != null) {
			change.getParameters().add(makeParameter("param", applyFlowActionOverriddingByFlowChange(param.getValue()), false));
		}
		String groupref = search.findParameter("widget-group-ref").getValue();
		change.getParameters().add(makeParameter("widget-group-ref", groupref+"*", false));
		removeParameter(params, "flow-action");
		removeParameter(params, "call-URI");
		removeParameter(params, "Query.alwaysDisplayResult");
		removeParameter(params, "Query.exactMatching");
		change.getSnippets().clear();
		
		return change;
	}
	
	protected String applyFlowActionOverriddingByFlowChange(String param) {
		return applyParameterOverridding(param, true);
	}
	
	protected String removeFlowChange(String param) {
		return applyParameterOverridding(param, false);
	}
	
	protected String applyParameterOverridding(String param, boolean apply) {
		if (param == null) {
			return null;
		}
		if (!param.contains(FLOW_ACTION)) {
			return param;
		}
		String[] subValues = StringUtils.split(param, ";");
		
		int flowActionIndex = -1;
		int flowChangeIndex = -1;
		String flowChange = null;
		for (int i=0; i<subValues.length; i++) {
			String subValue = subValues[i];
			// i.e. " flow-action = value "
			// i.e. "flow-action = value"
			subValue = subValue.trim();
			if (subValue.startsWith(FLOW_ACTION)) {
				// i.e. "= value" 
				subValue = StringUtils.removeStart(subValue, FLOW_ACTION).trim();
				if (subValue.startsWith("=")) {
					flowActionIndex = i;
				}
			} else if (subValue.startsWith(FLOW_CHANGE)) {
				// i.e. "= value" 
				subValue = StringUtils.removeStart(subValue, FLOW_CHANGE).trim();
				if (subValue.startsWith("=")) {
					flowChangeIndex = i;
					flowChange = StringUtils.removeStart(subValue, "=").trim();
				}
			}
		}
		if (apply) {
			if (flowActionIndex == -1) {
				return param;
			}
			if (flowChangeIndex == -1) {
				flowChange = "reload"; // Default value
			}
			StringBuilder result = new StringBuilder(32);
			for (int i=0; i<subValues.length; i++) {
				if (i == flowActionIndex) {
					result.append(FLOW_ACTION);
					result.append("=");
					result.append(flowChange);
					result.append(";");
				} else if (i == flowChangeIndex) {
					// Do not include flow-change in the result
				} else {
					result.append(subValues[i]);
					result.append(";");
				}
			}
			return StringUtils.removeEnd(result.toString(), ";");
		} else {
			// Remove flow-change
			if (flowChangeIndex == -1) {
				return param;
			}
			StringBuilder result = new StringBuilder(128);
			for (int i=0; i<subValues.length; i++) {
				if (i == flowChangeIndex) {
					// Do not include flow-change in the result
				} else {
					result.append(subValues[i]);
					result.append(";");
				}
			}
			return StringUtils.removeEnd(result.toString(), ";");
		}
	}
	
	
	/**
	 * @param name
	 * @param value
	 * @return
	 */
	private Parameter makeParameter(String name, String value, boolean userDefined) {
		Parameter param = ModelFactory.eINSTANCE.createParameter();
		param.setName(name);
		param.setValue(value);		
		param.setUserDefined(userDefined);
		return param;
	}
	
	/**
	 * @param context
	 * @param search
	 * @param widget
	 */
	private void transformSearchOnlyEvents(WidgetTransformerContext context, 
			Event search, Widget widget) throws CoreException {
		List<Event> dummies = new ArrayList<Event>();	
		// create onEnter event
		dummies.add(createMatchingEvent(search, "OnEnter"));	
		// create onClick event
		Event ev = makeEvent("OnClick", search);
		addParameter(ev, "Query.alwaysDisplayResult", "true", true);
		dummies.add(ev);	
		if (!context.isEditableTableTree()) {
			// create onChange event
			dummies.add(getChangeEvent(search));
		}
		// remove widget-group-ref on search
		removeParameter(search.getParameters(), "widget-group-ref");	
		
		ElementEventTransformer eTransformer = new ElementEventTransformer();
		eTransformer.transform(context, dummies, widget);
	}
	
	/**
	 * @param params
	 */
	private void removeParameter(List<Parameter> params, String paramname) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.addAll(params);
		for (Parameter parameter : parameters) {
			if (parameter.getName().equals(paramname)) {
				params.remove(parameter);
			}
		}
	}
	
	/**
	 * @param eventName
	 * @param search
	 */
	private Event makeEvent(String eventName, Event search) {
		Event event = EcoreUtil.copy(search);
		Widget parent = search.getWidget() != null ? search.getWidget() : search.getParent();
		event.setParent(parent);
		event.setEventName(eventName);
		
		// DS-4664 ignore flow-change=contextReload for
		// Non OnChange event
		if (!eventName.equals("OnChange")) {
			Parameter param = search.findParameter("param");
			if (param != null) {
				event.getParameters().add(makeParameter("param", removeFlowChange(param.getValue()), false));
			}
		}		
		return event;
	}
	
	/**
	 * @param events
	 * @return
	 */
	private Event getSearchEvent(List<Event> events, Widget parent, boolean copy) {
		for (Event event : events) {
			if (event.getEventName().equals("Search"))  {
				Event clone = null;
				if(copy) {
					clone = EcoreUtil.copy(event);
					clone.setParent(parent);
				} else {
					clone = event;
				}
				addParameter(clone, "Query.alwaysDisplayResult", "true", true);
				addParameter(clone, "Query.exactMatching", "false", true);
				if (copy) {
					Parameter param = clone.findParameter("param");
					if (param != null) {
						clone.getParameters().add(makeParameter("param", removeFlowChange(param.getValue()), false));
					}
				}
				return clone;
			}
		}
		return null;
	}
	
	/**
	 * @param event
	 * @param paramName
	 * @param value
	 * @param userDefined
	 */
	private void addParameter(Event event, String paramName, String value, boolean userDefined) {
		if (event == null) {
			return;
		}
		Parameter param = event.findParameter(paramName);//getParameter(event, paramName);
		if (param != null) {
			param.setValue(value);
		} else {
			event.getParameters().add(makeParameter(paramName, value, userDefined));
		}
	}
	
	/**
     * @param context
     * @param name
     * @param value
     */
    protected void transformParameter(WidgetTransformerContext context, String name, String value, Element parent) {
    	if (StringUtils.isEmpty(value)) {
    		return;
    	}
    	TransformModel tm = context.getTransformModel();
        Namespace ns = tm.getDefaultNamespace();

        // Create the <xgui:param name="f.nature.eq"> element
        String param = TransformerConstants.EVENT_USER_PARAMETER;
        Element element = context.getDocument().createElementNS(ns.getUri(), param);
        element.setPrefix(ns.getPrefix());	
         
        Attr nameAttrib = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
        nameAttrib.setValue(name);	
		element.setAttributeNode(nameAttrib);
		
		element.setTextContent(value);

		parent.appendChild(element);
    }
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.transformmodel.BaseWidgetTransformer#transformProperties(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException  {		
		for (Property p: widget.getProperties()) {	
			if (searchField.isSearchOnly()) {
				if (p.getTypeName().equals("auto-delay") 
						|| p.getTypeName().equals("nb-chars")
						|| p.getTypeName().equals("outputDesign")) {
					return;
				}
			} 			
			if (p.getTypeName().equals("auto-delay")) {
				if (p.getIntValue() >0)
					renderAttribute(context, "delay", p.getValue());
			} else if (p.getTypeName().equals("nb-chars")) {
				if (p.getIntValue() >0)
					renderAttribute(context, "keys", p.getValue());
			}
			// Added for DS-6543 -- Begin
			else if (p.getTypeName().equals(TransformerConstants.ACCESS_HISTROY_ITEMS) && p.getBooleanValue()) {
				if (searchField.isAutoCompleteAndSearch() || searchField.isAutoCompleteOnly()) {
					MdfDataset dataset = DomainObjectUtils.getDataset(widget);
					if (dataset != null) {
						renderAttribute(context, TransformerConstants.RECENT, dataset.getBaseClass().getName());
					}
				}
			}else if (p.getTypeName().equals(TransformerConstants.FAVORITE) && p.getBooleanValue()) {
				if (searchField.isAutoCompleteAndSearch() || searchField.isAutoCompleteOnly()) {
					MdfDataset dataset = DomainObjectUtils.getDataset(widget);
					if (dataset != null) {
						renderAttribute(context, TransformerConstants.FAVORITE, dataset.getBaseClass().getName());
					}
				}
			}
			// Added for DS-6543 -- End
			else {
				PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(p);
				pt.transform(context, p);
			}
		}		
	}
	
	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	private void renderAttribute(WidgetTransformerContext context, String key, String value) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return;
		}				
		Attr a = context.getDocument().createAttribute(key);		 
		a.setValue(value);	
		context.getParentElement().setAttributeNode(a);
	}

	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return searchfield;
	}

}

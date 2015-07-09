package com.odcgroup.page.transformmodel.snippet;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Element;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.util.DatasetAttribute;
import com.odcgroup.page.model.util.SearchDomainObjectUtil;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Transformer for filter snippet
 * 
 * @author pkk
 *
 */
public class FilterSnippetTransformer extends BaseSnippetTransformer {
	
	/** */
	private static final String ATTRIBUTE = SnippetTransformerConstants.FILTER_ATTRIBUTE;
	/** */
	private static final String OPERATOR = SnippetTransformerConstants.FILTER_OPERATOR;
	/** */
	private static final String VALUEONE = SnippetTransformerConstants.FILTER_VALUEONE;
	/** */
	private static final String VALUETWO = SnippetTransformerConstants.FILTER_VALUETWO;
	/** */
	private static final String MODE = SnippetTransformerConstants.FILTER_MODE;
	

	/**
	 * @param functionElement
	 */
	public FilterSnippetTransformer(Element functionElement) {
		super(functionElement);
	}
	
	/**
	 * @param context
	 * @param filter
	 */
	public void transform(WidgetTransformerContext context, IFilter filter, Widget eventWidget) {
		if (filter == null) {
			return;
		}
		
		IFilterSet filterSet = filter.getParent();

		String dsName = filterSet.getTargetDatasetName();
		EObject eObj = filterSet.getSnippet().eContainer();
		String[] mappings = null;
		Widget root = null;
		if (eObj instanceof Event) {
			Event event = (Event) eObj;
			Widget widget = event.getWidget()!=null ? event.getWidget() : event.getParent();
			if (widget != null) {
				// widget can be null for temporary created event
				root = widget.getRootWidget();
			}
			IQuery query = SnippetUtil.getQuery((Event) eObj);
			mappings = (query != null) ? query.getMappings() : null;
		} else if (eObj instanceof Widget) {
			root = ((Widget) eObj).getRootWidget();
		}
		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(context.getProject());
		List<DatasetAttribute> attributes = SearchDomainObjectUtil.getDomainAttributes(dsName, mappings, ofsProject);
		List<Snippet> filters = filterSet.getSnippet().getContents();
		int index = filters.indexOf(filter.getSnippet());
		
		String prefix = PARAM_PREFIX+"."+filterSet.getId()+".f"+(index+1)+".";

		// attribute
		String selection = filter.getDatasetAttributeName();
		String attribute = getAttributeName(attributes, selection);		
		transformSnippetParameter(context, prefix+ATTRIBUTE, selection);
		
		// operator
		if (filter.getOperator() != null) {
			String operator = StringEscapeUtils.escapeXml(filter.getOperator());
			transformSnippetParameter(context, prefix+OPERATOR, operator);
		}
		
		// value
		String value = null;
		// check for mapped attributes
		if (isMappedAttribute(attributes, selection)) {
			StringBuilder sb = new StringBuilder();
			sb.append("valueOf(");
			String widgetGroup = getWidgetGroupRef(filter, eventWidget);
			if (!StringUtils.isEmpty(widgetGroup)) {
				sb.append(widgetGroup+".");
			}
			
			String association = getBeanProperty(context, root);
			if (!StringUtils.isEmpty(association)) {
				sb.append(association+".");
			}
			sb.append(attribute);
			sb.append(")");
			value = sb.toString();
		} else {
			if (!StringUtils.isEmpty(filter.getValue())) {
				value = getValue(attributes, filter);
				value = value == null ? "" : value.trim();
				 
				// begin DS-7614 - check if the value is similar to "valueOf(groupRef.*)"
				// if yes transform it to "valueOf(groupRef.beanPrefix.*)"
				String widgetGroup = getWidgetGroupRef(filter, eventWidget);
				String tmp = "valueOf("+widgetGroup+".";
				if (value.startsWith(tmp)) {
					int dotPos = value.indexOf('.');
					String beanPrefix = "";
					List<Widget> list = context.getParentWidgets();
					// traverse the inclusion context in the reverse order
					for (int i = list.size(); --i >= 0;) {
						Widget x = list.get(i);
			            Property p = x.getRootWidget().findProperty(PropertyTypeConstants.BEAN_PROPERTY_PREFIX);
			            if (p != null) {
			            	beanPrefix += p.getValue();
			            	if (StringUtils.isNotBlank(p.getValue())) {
			            		beanPrefix = beanPrefix.trim()+".";
			            		// only 1 level of prefix is supported. 
			            		break;
			            	}
			            }
					}
					value = tmp + beanPrefix + value.substring(dotPos+1); 
				}
				/// end DS-7614

			} 
		}
		if (value == null) {
			value = "";
		}
		transformSnippetParameter(context, prefix+VALUEONE, value);
		
		// value2
		if (!StringUtils.isEmpty(filter.getOtherValue())) {
			transformSnippetParameter(context, prefix+VALUETWO, filter.getOtherValue());
		}
		
		// filter mode
		transformSnippetParameter(context, prefix+MODE, filter.getEditMode().toUpperCase());		
	}
	
	/**
	 * for enumeration types the value for generation is the index of the enumeration-value
	 * and for boolean types 0 for true and 1 for false
	 * 
	 * @param attributes
	 * @param filter 
	 * @return value
	 */
	private String getValue(List<DatasetAttribute> attributes, IFilter filter) {
		MdfEntity type = null;
		for (DatasetAttribute attribute : attributes) {
			if (attribute.getDisplayName().equals(filter.getDatasetAttributeName())) {
				type = attribute.getType();
				break;
			}
		}
		String value = filter.getValue();
		if (type != null) {
			if (type instanceof MdfEnumeration) {
				List<?> enumVals = ((MdfEnumeration) type).getValues();
				// multiple values is possible
				String[] values = value.split(",");
				StringBuilder sb = new StringBuilder();
				int ii = 0;
				for (String string : values) {
					for (Object obj : enumVals) {
						MdfEnumValue val = (MdfEnumValue) obj;
						if(string.equals(val.getName())) {
							sb.append(val.getValue());
							if (ii != values.length -1) {
								sb.append(",");								
							}
						}
					}
					ii++;
				}
				value = sb.toString();
			} else if (type instanceof MdfPrimitive) {
				MdfPrimitive prim = (MdfPrimitive) type;
				if (prim.equals(PrimitivesDomain.BOOLEAN) || prim.equals(PrimitivesDomain.BOOLEAN_OBJ)) {
					if (value.equals("true")) {
						value = "0";
					} else if (value.equals("false")) {
						value = "1";
					}
				}
			}
		} 
		return value;
	}
	
	/**
	 * @param attributes
	 * @param selection
	 * @return name
	 */
	private String getAttributeName(List<DatasetAttribute> attributes, String selection) {
		for (DatasetAttribute attribute : attributes) {
			if (attribute.getDisplayName().equals(selection)) {
				return attribute.getName();
			}
		}
		return "";
	}
	
	/**
	 * @param attributes
	 * @param selection
	 * @return boolean
	 */
	private boolean isMappedAttribute(List<DatasetAttribute> attributes, String selection) {
		for (DatasetAttribute attribute : attributes) {
			if (attribute.getDisplayName().equals(selection)) {
				return attribute.isMapped();
			}
		}
		return false;
	}
	
	
	/**
	 * @param context
	 * @param filterWidget
	 * @return string
	 */
	private String getBeanProperty(WidgetTransformerContext context, Widget filterWidget) {
		Widget parentWidget = getImmediateParent(context.getParentWidgets(), filterWidget);		
		if (parentWidget != null) {
			return parentWidget.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
		} else {
			return null;
		}
		
	}
	
	/**
	 * @param parentWidgets
	 * @param include
	 * @return widget
	 */
	private static Widget getImmediateParent(List<?> parentWidgets, Widget include) {
		for (Object object : parentWidgets) {
			Widget parentWidget = (Widget) object;
			if (parentWidget.getTypeName().equals(WidgetTypeConstants.INCLUDE)) {
				Property property = parentWidget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
				if (property != null) {
					Model model = property.getModel();
					if (model != null && include.equals(model.getWidget())) {
						return parentWidget;
					}
				}
			}
		}		
		return null;
	}
	
	/**
	 * @param filter
	 * @return val
	 */
	private String getWidgetGroupRef(IFilter filter, Widget widget) {
		String  groupref = null;
		if (widget == null) {
			IFilterSet filterSet = filter.getParent();
			EObject eObj = filterSet.getSnippet().eContainer();
			if (eObj instanceof Event) {
				widget = ((Event)eObj).getWidget();
			}
		} 
		if (widget != null) {
			return widget.getPropertyValue(PropertyTypeConstants.WIDGET_GROUP);
		}
		return groupref;
	}

}

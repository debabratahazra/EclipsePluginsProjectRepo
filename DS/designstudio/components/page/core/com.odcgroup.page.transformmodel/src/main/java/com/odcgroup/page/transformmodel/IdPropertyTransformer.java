package com.odcgroup.page.transformmodel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transforms the Id property.
 * 
 * @author atr
 */
public class IdPropertyTransformer extends BasePropertyTransformer {
	
	Set<String> nogeneration = new HashSet<String>();
	
	
	List<String> dependentOnEvent; 
	
	List<String> idGenerationWidget; 
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public IdPropertyTransformer(PropertyType type) {
		super(type);
		dependentOnEvent = new ArrayList<String>();
		dependentOnEvent.add(WidgetTypeConstants.LABEL);
		dependentOnEvent.add(WidgetTypeConstants.ICON);
		
		nogeneration.add(WidgetTypeConstants.BOX);
		nogeneration.add(WidgetTypeConstants.LABEL);
		nogeneration.add(WidgetTypeConstants.LABEL_DOMAIN);
		
		idGenerationWidget = new ArrayList<String>();
		//Editable
		idGenerationWidget.add(WidgetTypeConstants.SEARCH_FIELD);
		idGenerationWidget.add(WidgetTypeConstants.CALDATE_FIELD);
		idGenerationWidget.add(WidgetTypeConstants.PASSWORD_FIELD);
		idGenerationWidget.add(WidgetTypeConstants.TEXTAREA);
		idGenerationWidget.add(WidgetTypeConstants.TEXTFIELD);
		//Modifiable
		idGenerationWidget.add(WidgetTypeConstants.COMBOBOX);
		idGenerationWidget.add(WidgetTypeConstants.CHECKBOX);
		idGenerationWidget.add(WidgetTypeConstants.RADIO_BUTTON);
		//Special
		idGenerationWidget.add(WidgetTypeConstants.HIDDEN_FIELD);
	}
	
	/**
	 * Transforms the Property.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param property
	 *            The Property to transform
	 */
	@SuppressWarnings("unchecked")
	public void transform(WidgetTransformerContext context, Property property) {
		String modulePrefix = null;
		if (context.isEditableTableTree()) {
			// the attribute 'id' is generated elsewhere for 
			// editable widget used within an editable table/tree.
			return;
		}
		
        String value = property.getValue();
        if (StringUtils.isEmpty(value)) {
            Widget widget = property.getWidget();        
        	value = UniqueIdGenerator.generateId(widget, context.getParentWidgets());
        }   
        
        
		if (StringUtils.isBlank(value)) {
			// Nothing to do
			return;
		}
		
		String typeName = property.getWidget().getTypeName();
		if (nogeneration.contains(typeName)) {
			// do not generate id for this widget.
			return;
		}
		
		if (dependentOnEvent.contains(property.getWidget().getTypeName()) && property.getWidget().getEvents().size() == 0 ) {
			return;
		}
		
		String name = property.getTypeName().toLowerCase();
		
		// DS-3982-begin: Moved this code here from BeanPropertyPropertyTransformer 
		Widget widget = property.getWidget();

		String bpp = BeanPropertyUtils.findBeanPropertyPrefix(context, widget);
		String bpi = BeanPropertyUtils.findBeanPropertyPostfix(context, widget);	
		Property pProperty = widget.findProperty(PropertyTypeConstants.ID);
		
		// Changes made for DS-6623 - BEGIN
		if(!StringUtils.isEmpty(bpp) && !bpp.endsWith(".")) {
			bpp = bpp + ".";
		}
		// Changes made for DS-6623 - END
		
		//DS-5275 prefix module prefix name with Id
		if (BeanPropertyUtils.findModuleBeanPropertyPrefix(context, widget)!=null && !BeanPropertyUtils.findModuleBeanPropertyPrefix(context, widget).isEmpty()) {
			//DS-5275
			modulePrefix = BeanPropertyUtils.findModuleBeanPropertyPrefix(context, widget) + ".";
		}
		// DS-4100 - combo - if the value is set to {1}._oid -> it must not be overriden
		if(pProperty!=null && !"{1}._oid".equals(value.trim())) {
			//DS-2421 (default fragments, reset the widget id)
			value = (idGenerationWidget.contains(widget.getTypeName()) && modulePrefix!=null ? modulePrefix : "") +  bpp + pProperty.getValue() + bpi;	
		}
		else if(pProperty!=null && "{1}._oid".equals(value.trim())){
			value = (modulePrefix!=null ? modulePrefix : "")+value;
		}
		
		// DS-3032-begin
		Element elmt = context.getParentElement();
		if (elmt.getLocalName().equals(XGuiConstants.XGUI_COMBO)) {
			Property pName = BeanPropertyUtils.findBeanNameProperty(context, widget);
			String displayProperty = BeanPropertyUtils.findBeanPropertyPrefix(context, widget);
			if (displayProperty != null && displayProperty.endsWith(".")) {
				displayProperty = displayProperty.substring(0, displayProperty.length()-1);
			}
			if(value!=null && pProperty!=null && pName!=null) {
				// substitute placeholders in the id-string by correct values
				MessageFormat format = new MessageFormat(value);
				value = format.format(new String[]{pProperty.getValue(), displayProperty, pName.getValue()});
			}
		}
		// DS-3032-end
		// DS-3982-end		
		// DS-4925
		else {
			String domAttr = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
			StringBuilder sb = new StringBuilder();
			String association = getBeanProperty(context, widget);
			if (idGenerationWidget.contains(widget.getTypeName())) {
				sb.append(value);	
			}
			else if (!StringUtils.isEmpty(domAttr) && !StringUtils.isEmpty(association)) {
				sb.append(association+"."+domAttr);
			} else {
				sb.append(value);					
			}
			value = sb.toString();
		}

		TransformUtils.convertToAttribute(context, context.getParentElement(), name, value);			
	}
	

	/**
	 * @param context
	 * @param filterWidget
	 * @return string
	 */
	protected String getBeanProperty(WidgetTransformerContext context, Widget widget) {
		Widget parentWidget = getImmediateParent(context.getParentWidgets(), widget.getRootWidget());		
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
	
}

package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.TransformerConstants.EXPRESSION_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.DomainObjectUtils;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 *
 * @author pkk
 *
 */
public class SelectedPropertyTransformer extends BasePropertyTransformer {

	/**
	 * @param type
	 */
	public SelectedPropertyTransformer(PropertyType type) {
		super(type);
	}

	private final boolean isCheckboxWithEnumValueDefined(Widget widget) {
		String typeName = widget.getTypeName();
		return WidgetTypeConstants.CHECKBOX.equals(typeName) 
			&& StringUtils.isNotBlank(widget.getPropertyValue(PropertyTypeConstants.ENUM_VALUE));
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	@Override
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
        Widget widget = property.getWidget();
        Property selected = widget.findProperty(PropertyTypeConstants.SELECTED);
        if (selected != null) {
        	Element attribute = createElement(context, XSP_NAMESPACE_URI, TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
        	TransformUtils.appendChild(context, attribute);
        	// Set the parent so that the Attributes are set on the correct element
        	Element oldParent = context.setParentElement(attribute);
        	TransformUtils.convertToAttribute(context, attribute, "name", PropertyTypeConstants.SELECTED);

        	if (!widget.getTypeName().equals(WidgetTypeConstants.RADIO_BUTTON)) {
        		if (isCheckboxWithEnumValueDefined(widget)) {
        			selectedFromExpressionForCheckboxWithEnumValue(context, widget, attribute);
        		} else {
        			selectedFromBeanProperty(context, widget, selected, attribute);
        		}
			} else {
        		if(StringUtils.isBlank(widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE))) {
        			selectedFromBeanProperty(context, widget, selected, attribute);
        		} else {
        			selectedFromExpression(context, widget, attribute);
        		}
        	}
        	context.setParentElement(oldParent);
        }
	}

	/**
	 * @param context
	 * @param widget
	 * @param selected
	 * @param attribute
	 */
	private void selectedFromBeanProperty(WidgetTransformerContext context, Widget widget, Property selected,
			Element attribute) {
		Element nav = createElement(context, TransformerConstants.NAV_NAMESPACE_URI, "is-selected");
		TransformUtils.convertToAttribute(context, nav, "group-id", widget.getPropertyValue("group"));
		TransformUtils.convertToAttribute(context, nav, "element-id", widget.getID());
		if (selected.getValue().equals("true")) {
			TransformUtils.convertToAttribute(context, nav, "default-selected", selected.getValue());
		}
		TransformUtils.appendChild(attribute, nav);
	}

	/**
	 * @param context
	 * @param widget
	 * @param attribute
	 */
	private void selectedFromExpression(WidgetTransformerContext context, Widget widget, Element attribute) {
		Element expressionElement = createElement(context, XSP_NAMESPACE_URI, EXPRESSION_ELEMENT_NAME);
		
		Property nameProperty = BeanPropertyUtils.findBeanNameProperty(context, widget);
		Property beanProperty = widget.findProperty(PropertyTypeConstants.BEAN_PROPERTY);
		String namePropertyValue = "";
		if (nameProperty != null) {
			namePropertyValue = nameProperty.getValue();
		}
		String beanPropertyValue = beanProperty.getValue();
		String domAssoc = TransformUtils.getDomainAssociationIfAny(context, widget);
		if (StringUtils.isNotEmpty(domAssoc)) {
			beanPropertyValue = domAssoc+"."+beanPropertyValue;
		}
		String beanPropertyString = "<bean:get-property bean=\"" + namePropertyValue + "\" property=\"" + beanPropertyValue + "\"/>";
		
		String value = DomainObjectUtils.retrieveEnumValue(widget);
		String expression = "\"" + value + "\".equals(String.valueOf(" + beanPropertyString	+ "))";
		expressionElement.setTextContent(expression);
		TransformUtils.appendChild(attribute, expressionElement);
	}
	
	/**
	 * @param context
	 * @param widget
	 * @param attribute
	 */
	private void selectedFromExpressionForCheckboxWithEnumValue(WidgetTransformerContext context, Widget widget, Element attribute) {
		Element expressionElement = createElement(context, XSP_NAMESPACE_URI, EXPRESSION_ELEMENT_NAME);
		
		Property nameProperty = BeanPropertyUtils.findBeanNameProperty(context, widget);
		Property beanProperty = widget.findProperty(PropertyTypeConstants.BEAN_PROPERTY);
		String namePropertyValue = "";
		if (nameProperty != null) {
			namePropertyValue = nameProperty.getValue();
		}
		String beanPropertyValue = beanProperty.getValue();
		String domAssoc = TransformUtils.getDomainAssociationIfAny(context, widget);
		if (StringUtils.isNotEmpty(domAssoc)) {
			beanPropertyValue = domAssoc+"."+beanPropertyValue;
		}
		StringBuilder expression = new StringBuilder();
		expression.append("<bean:contains bean=\"");
		expression.append(namePropertyValue);
		expression.append("\" property=\"");
		expression.append(beanPropertyValue);
		expression.append("\">");
		expression.append("<bean:param name=\"object\">");
		expression.append(DomainObjectUtils.retrieveEnumValue(widget));
		expression.append("</bean:param>");
		expression.append("</bean:contains>");
		
		expressionElement.setTextContent(expression.toString());
		TransformUtils.appendChild(attribute, expressionElement);
	}
}

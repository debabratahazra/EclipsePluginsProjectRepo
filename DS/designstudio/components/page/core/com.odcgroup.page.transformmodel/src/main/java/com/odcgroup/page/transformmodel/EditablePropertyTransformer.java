package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.mdf.DomainConstants;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.properties.editable.EditableConstants;
import com.odcgroup.page.model.properties.editable.EditableIsBasedOnCondition;
import com.odcgroup.page.transformmodel.impl.DefaultPropertyTransformerImpl;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transformer used to generate Editable attribute value based on condition. 
 * Delegate to DefaultPropertyTransformerImpl for true/false values. 
 * @author yan
 * @since 2.4
 */
public class EditablePropertyTransformer extends BasePropertyTransformer {

	/**
	 * Holds the variable name used with advanced condition
	 */
	public static final String CONDITION_VAL = "conditionVal";
	
	/**
	 * Construct a new EditablePropertyTransformer
	 * @param type
	 */
	public EditablePropertyTransformer(PropertyType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	@Override
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
		if (EditableConstants.EDITABLE_CONDITIONAL_VALUE.equals(property.getValue())) {
			Widget widget = property.getWidget();
			EditableIsBasedOnCondition condition = new EditableIsBasedOnCondition(widget);
            Element xspAttribute = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_ATTRIBUTE_ELEMENT); 
	        xspAttribute.setAttribute(XSPConstants.XSP_NAME, EditableConstants.EDITABLE_PROPERTY_NAME);
	        
	        // Ensure it is the first child
            if (context.getParentElement().hasChildNodes()) {
            	context.getParentElement().insertBefore(xspAttribute, context.getParentElement().getFirstChild());
            } else {
            	context.getParentElement().appendChild(xspAttribute);
            }

            if (!condition.isSimplified()) {
            	// Advanced condition
            	Element xspLogic = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
            	xspLogic.setTextContent(condition.getAdvancedCondition());
            	context.getParentElement().getParentNode().insertBefore(xspLogic, context.getParentElement());
	            
            	Element xspExpr = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_EXPR);
            	xspExpr.setTextContent(CONDITION_VAL);
	            xspAttribute.appendChild(xspExpr);
            } else if (isInTable(widget)) {
            	// Simplified condition in a table
            	Element updItem = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ITEM_ELEMENT);
	            updItem.setPrefix(UdpConstants.UDP_ROOT_ELEMENT);
				String localName = condition.getSimplifiedCondition().getLocalName(); 
				String parts[] = localName.split(DomainConstants.ENTITY_SEPARATOR);
				String columnAttribute = "";
				if (parts.length > 1) {
					columnAttribute = parts[1];
				}
	            updItem.setAttribute(UdpConstants.UDP_COLUMN, columnAttribute);
	            xspAttribute.appendChild(updItem);
            } else {
            	// Simplified condition in a form
            	Element bean = createElement(context, BeanConstants.BEAN_URI, BeanConstants.BEAN_GET_PROPERTY_ELEMENT);
            	Property beanDefine = widget.getRootWidget().findProperty(PropertyTypeConstants.BEAN_NAME);
            	
            	if (beanDefine == null && context.getParentWidgets().size() > 0) {
                	Widget w = (Widget)context.getParentWidgets().get(0);
                	beanDefine = w.getRootWidget().findProperty(PropertyTypeConstants.BEAN_NAME);
            	}
            	
            	bean.setAttribute(BeanConstants.BEAN_BEAN_ATTRIBUTE, beanDefine!=null?beanDefine.getValue():"");
				String localName = condition.getSimplifiedCondition().getLocalName(); 
				String parts[] = localName.split(DomainConstants.ENTITY_SEPARATOR);
				String beanAttribute = "";
				if (parts.length > 1) {
					beanAttribute = parts[1]; // dataset's property
				}
				String domAssoc = TransformUtils.getDomainAssociationIfAny(context, widget);
				if (StringUtils.isNotEmpty(domAssoc)) {
					beanAttribute = domAssoc+"."+beanAttribute;
				}
            	bean.setAttribute(BeanConstants.BEAN_PROPERTY_ATTRIBUTE, beanAttribute);
            	xspAttribute.appendChild(bean);
            }
		} else {
			new DefaultPropertyTransformerImpl().transform(context, property);
		}
	}

	/**
	 * @param widget
	 * @return true if the widget is in a table
	 */
	private boolean isInTable(Widget widget) {
		Widget currentWidget = widget;
		boolean inTable = false;
		while (currentWidget != null) {
			if (WidgetTypeConstants.TABLE_TREE.equals(currentWidget.getTypeName())) {
				inTable = true;
				break;
			}
			currentWidget = currentWidget.getParent();
		}
		return inTable;
	}

}

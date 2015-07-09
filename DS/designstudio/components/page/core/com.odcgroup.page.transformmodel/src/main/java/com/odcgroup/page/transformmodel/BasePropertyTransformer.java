package com.odcgroup.page.transformmodel;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Abstract base class for all concrete PropertyTransformer's.
 * 
 * @author Gary Hayes
 */
abstract public class BasePropertyTransformer implements PropertyTransformer {

	/** The PropertyType. */
	private PropertyType type;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public BasePropertyTransformer(PropertyType type) {
		this.type = type;
	}

	/**
	 * Gets the widget type.
	 * 
	 * @return PropertyType The PropertyType
	 */
	protected PropertyType getPropertyType() {
		return type;
	}
	
	/**
	 * Returns true if the PropertyTransformer is designed to transform the specified PropertyType.
	 * 
	 * @param property The Property to test
	 * @return boolean True if the PropertyTransformer is designed to transform the specified PropertyType
	 */
	public boolean isTransformer(Property property) {
		return getPropertyType().equals(property.getType());
	}
	
	/**
	 * Creates an Attribute of the specified namespace and appends it to the parent Element
	 * defined in the context.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param attributeName The name of the Attribute
	 * @param value The value
	 * @return Attr The newly-created Attribute
	 */	
	protected Attr addAttribute(WidgetTransformerContext context, String attributeName, String value) {
		return addAttribute(context, context.getParentElement(), attributeName, value);
	}	

	/**
	 * Creates an Attribute of the specified namespace and appends it to the specified parent Element.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param parent The parent Element
	 * @param attributeName The name of the Attribute
	 * @param value The value
	 * @return Attr The newly-created Attribute
	 */	
	protected Attr addAttribute(WidgetTransformerContext context, Element parent, String attributeName, String value) {
		Attr a = context.getDocument().createAttribute(attributeName);
		a.setValue(value);
		parent.setAttributeNode(a);
		return a;
	}
	
	/**
	 * Creates an Attribute of the specified namespace and appends it to the specified parent Element.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param parent The parent Element
	 * @param attributeName The name of the Attribute
	 * @param property The Property containing the Attribute's value
	 * @return Attr The newly-created Attribute
	 */	
	protected Attr addAttribute(WidgetTransformerContext context, Element parent, String attributeName, Property property) {
		return addAttribute(context, parent, attributeName, property.getValue());
	}
	
	/**
	 * Creates an Element of the specified namespace and appends it to the parent Element.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param namespace The namespace of the Element to create
	 * @param elementName The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element appendElement(WidgetTransformerContext context, String namespace, String elementName) {
		Element e = createElement(context, namespace, elementName);
		context.getParentElement().appendChild(e);
		return e;
	}	
	
	/**
	 * Creates an Element of the specified namespace and appends it to the specified parent Element.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param parent The parent Element
	 * @param namespace The namespace of the Element to create
	 * @param elementName The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element appendElement(WidgetTransformerContext context, Element parent, String namespace, String elementName) {
		Element element = createElement(context, namespace, elementName);
		TransformUtils.appendChild(parent, element);
		return element;
	}
	
	/**
	 * Creates an Element of the specified namespace and name.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param namespace The namespace of the Element to create
	 * @param elementName The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element createElement(WidgetTransformerContext context, String namespace, String elementName) {
		Document d = context.getDocument();
		Namespace ns = context.getTransformModel().findNamespace(namespace);
		Element e = d.createElementNS(ns.getUri(), elementName);
		e.setPrefix(ns.getPrefix());
		return e;
	}	
	
}
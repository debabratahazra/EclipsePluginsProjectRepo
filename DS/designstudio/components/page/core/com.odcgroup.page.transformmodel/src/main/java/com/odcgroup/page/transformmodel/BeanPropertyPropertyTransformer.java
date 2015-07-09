/**
 * 
 */
package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_BEAN_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_GET_PROPERTY_ELEMENT;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_PROPERTY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_URI;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.DomainObjectUtils;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * This transformer converts theses 2 bean properties
 * 
 * <pre>
 *    bean-name
 *    bean-property
 * &lt;pre&gt;
 * 
 * &lt;pre&gt;
 * 
 * If &quot;bean-define&quot; exist and has a value this fragment is generated
 * 
 *    &lt; xgui:value &gt;
 *       &lt; bean:define name=&quot;...&quot; bean=&quot;...&quot; property=&quot;...&quot; /&gt;
 *    &lt; /gui:value &gt;
 *   
 * If &quot;bean-define&quot; doesn't exist or has no value this fragment is generated
 * 
 *    &lt; xgui:value &gt;
 *       &lt; bean:get-property bean=&quot;...&quot; property=&quot;...&quot; /&gt;
 *    &lt; /gui:value &gt;
 * 
 * &lt;/pre&gt;
 * 
 * Then an update of the bean tags listed in BEAN_ELEMENT_TO_UPDATE is done. The bean
 * and bean property will be updated using {0}, {1}, {2} pattern with respectively   
 * the bean name, the bean property, the bean property used for displaying the value
 * to the user.
 * 
 * @author Alain Tripod
 * @author Gary Hayes
 * 
 */
public class BeanPropertyPropertyTransformer extends BasePropertyTransformer {

	/** default xml element name to store the widget value */
	private static String XGUI_DEFAULT_VALUE_ELEMENT = "value";

	/**
	 * This map bind a widget type name to a xml element name. This element name will contains the value of the widget.
	 */
	private HashMap<String, String> valueElementMap = null;

	// instance initializer
	{
		valueElementMap = new HashMap<String, String>();
		valueElementMap.put(WidgetTypeConstants.TEXTFIELD, XGUI_DEFAULT_VALUE_ELEMENT);
		valueElementMap.put(WidgetTypeConstants.CHECKBOX, XGUI_DEFAULT_VALUE_ELEMENT);
		valueElementMap.put(WidgetTypeConstants.COMBOBOX, XGuiConstants.XGUI_SELECTED_VALUE);
		valueElementMap.put(WidgetTypeConstants.TABLE_COLUMN_COMBOBOX_ITEM, XGuiConstants.XGUI_SELECTED_VALUE);
		valueElementMap.put(WidgetTypeConstants.TABLE_COLUMN_CALENDAR_ITEM, XGUI_DEFAULT_VALUE_ELEMENT);
		valueElementMap.put(WidgetTypeConstants.TABLE_COLUMN_TEXT_ITEM, XGUI_DEFAULT_VALUE_ELEMENT);
		valueElementMap.put(WidgetTypeConstants.TABLE_COLUMN_TEXT_AREA_ITEM, XGUI_DEFAULT_VALUE_ELEMENT);
		valueElementMap.put(WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM, XGUI_DEFAULT_VALUE_ELEMENT);
		// .. complete with specific transformations
	}

	/** Bean xml element to update with the bean:get-property */
	private final static List<String> BEAN_ELEMENT_TO_UPDATE;
	
	static {
		BEAN_ELEMENT_TO_UPDATE = new ArrayList<String>();
		BEAN_ELEMENT_TO_UPDATE.add(BeanConstants.BEAN_IS_NULL_OR_EMPTY);
		BEAN_ELEMENT_TO_UPDATE.add(BeanConstants.BEAN_CONTAINS);
		BEAN_ELEMENT_TO_UPDATE.add(BeanConstants.BEAN_GET_PROPERTY_ELEMENT);
	}

	
	/**
	 * Constructs a new BeanProperty transformer.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public BeanPropertyPropertyTransformer(PropertyType type) {
		super(type);
	}
	
	private boolean isEditableTableColumnItem(Widget widget) {
		return WidgetTypeConstants.TABLE_COLUMN_COMBOBOX_ITEM.equals(widget.getTypeName())
			|| WidgetTypeConstants.TABLE_COLUMN_CALENDAR_ITEM.equals(widget.getTypeName())
			|| WidgetTypeConstants.TABLE_COLUMN_TEXT_ITEM.equals(widget.getTypeName())
			|| WidgetTypeConstants.TABLE_COLUMN_TEXT_AREA_ITEM.equals(widget.getTypeName())
			|| WidgetTypeConstants.TABLE_COLUMN_CHECKBOX_ITEM.equals(widget.getTypeName())
			|| WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName());
	}
	
	/**
	 * @param context
	 * @param widget
	 * @param valueElement
	 * @param nameProperty
	 * @param namePropertyValue
	 * @param beanPropertyValue
	 */
	private void generateBeanGetPropertyElement(WidgetTransformerContext context, Widget widget, 
			Element valueElement, Property nameProperty, Property beanProperty, String namePropertyValue, String beanPropertyValue) {
		if (context.isInXTooltipFragment() && (WidgetTypeConstants.COMBOBOX.equals(widget.getTypeName()))) {
				Element udpItem = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ITEM_ELEMENT);
				valueElement.appendChild(udpItem);
				udpItem.setAttribute(UdpConstants.UDP_ITEM_COLUMN, beanPropertyValue);
		} else {
			Element defElmt = selectFirstChildElement(BEAN_URI, BEAN_GET_PROPERTY_ELEMENT, valueElement);
			if (defElmt == null) {
				Namespace beanNamespace = context.getTransformModel().findNamespace(BEAN_URI);
				defElmt = context.getDocument().createElementNS(beanNamespace.getUri(), BEAN_GET_PROPERTY_ELEMENT);
				defElmt.setPrefix(beanNamespace.getPrefix());
				
				if ( isEditableTableColumnItem(widget) ) {
					addAttribute(context, defElmt, BEAN_BEAN_ATTRIBUTE, namePropertyValue);
					if (WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName())) {
						beanPropertyValue = context.getEditableDatasetAssociation() + "." + context.getEditableDatasetAttribute();
					}
					addAttribute(context, defElmt, BEAN_PROPERTY_ATTRIBUTE, beanPropertyValue);
					valueElement.appendChild(defElmt);
				} else {
					addAttribute(context, defElmt, BEAN_BEAN_ATTRIBUTE, nameProperty);
					
					String bpp = BeanPropertyUtils.findBeanPropertyPrefix(context, widget);
					String bpi = BeanPropertyUtils.findBeanPropertyPostfix(context, widget);
					if (StringUtils.isEmpty(bpp)) {
						String domAssoc = TransformUtils.getDomainAssociationIfAny(context, widget);
						if (StringUtils.isNotEmpty(domAssoc)) {
							bpp = domAssoc+"."+bpp;
						}
					} 
					// Condition added for DS-6623
					else if(!StringUtils.isEmpty(bpp) && !bpp.endsWith(".")) { 
						bpp = bpp + ".";
					}
					
					addAttribute(context, defElmt, BEAN_PROPERTY_ATTRIBUTE, bpp + beanProperty.getValue() + bpi);
					
					valueElement.appendChild(defElmt);
				}
			}
		}
	}

	/**
	 * Transforms the Property.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param property
	 *            The Property to transform
	 */
	public void transform(WidgetTransformerContext context, Property property) {
		Widget widget = property.getWidget();

		Property nameProperty = BeanPropertyUtils.findBeanNameProperty(context, widget);
		if (nameProperty == null) return;
		
		Property beanProperty = widget.findProperty(PropertyTypeConstants.BEAN_PROPERTY);
		if (beanProperty == null) return;
		
		String namePropertyValue = null;
		String beanPropertyValue = null;
		if (context.isEditableTableTree()) {
			String datasetName = context.getEditableDataset(); 
			int pos = datasetName.indexOf(":");
			if (pos != -1) {
				namePropertyValue = datasetName.substring(pos+1);
			}
			beanPropertyValue = context.getEditableDatasetAttribute();
		} else {
			namePropertyValue = nameProperty.getValue();
			beanPropertyValue = beanProperty.getValue();
		}
		
		if (StringUtils.isBlank(namePropertyValue) || StringUtils.isBlank(beanPropertyValue)) {
			return;
		}
			
		Element valueElement = createValueElement(context, widget);			
		
		if (!context.isEditableTableTree() || isEditableTableColumnItem(widget)) {
			
			if (StringUtils.isBlank(widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE)) 
					|| !widget.getTypeName().equals(WidgetTypeConstants.RADIO_BUTTON)) {
				
				if (!isCheckboxWithEnumValueDefined(widget)) {
					generateBeanGetPropertyElement(
							context, 
							widget, 
							valueElement,
							nameProperty,
							beanProperty,
							namePropertyValue,
							beanPropertyValue);
				}
				
			}
		}

		// Update existing beans used (if not already defined)
		String displayProperty = null;
		if (context.isEditableTableTree()) {
			displayProperty = context.getEditableDatasetAssociation();
		} else {
			displayProperty = BeanPropertyUtils.findBeanPropertyPrefix(context, widget);
		}
		if (displayProperty != null && displayProperty.endsWith(".")) {
			displayProperty = displayProperty.substring(0, displayProperty.length()-1);
		}
		updateBeanElement(context.getParentElement(), beanPropertyValue, displayProperty, namePropertyValue);
		
		
		String typeName = widget.getTypeName();
					
		// append udp format in case of caldate field with format property
		if (WidgetTypeConstants.CALDATE_FIELD.equals(typeName) 
				|| WidgetTypeConstants.TABLE_COLUMN_CALENDAR_ITEM.equals(widget.getTypeName())) {
			Property formatProperty = widget.findProperty(PropertyTypeConstants.FORMAT);
			if (formatProperty != null) {
				if (StringUtils.isNotEmpty(formatProperty.getValue())) {
					NodeList nodes = context.getParentElement().getChildNodes();
					if (context.isInXTooltipFragment()) {
						UdpFormatPropertyUtils.appendUnformattedItemToValueNode(context, getValueNode(nodes), formatProperty.getValue());
					} else {
						UdpFormatPropertyUtils.appendFormatToValueNode(context, getValueNode(nodes), formatProperty.getValue());
					}
					Node firstChild = context.getParentElement().getFirstChild().cloneNode(true);
					context.getParentElement().removeChild(context.getParentElement().getFirstChild());
					context.getParentElement().appendChild(firstChild);
				}
			}
		}
		
		// add udp format element around value 
		if (WidgetTypeConstants.TEXTFIELD.equals(typeName) 
				|| WidgetTypeConstants.AUTO_COMPLETE.equals(typeName)
				|| WidgetTypeConstants.SEARCH_FIELD.equals(typeName)
				|| WidgetTypeConstants.TABLE_COLUMN_TEXT_ITEM.equals(typeName)) {
			Property formatProperty = widget.findProperty(PropertyTypeConstants.FORMAT);
			if (formatProperty != null) {
				if (StringUtils.isNotEmpty(formatProperty.getValue())) {
					NodeList nodes = context.getParentElement().getChildNodes();
					UdpFormatPropertyUtils.appendFormatToValueNode(context, getValueNode(nodes), formatProperty.getValue());
				}
			}
		}
		
		// add udp item element
		if(WidgetTypeConstants.CHECKBOX.equals(typeName)) {
			if (context.isInXTooltipFragment()) {
				NodeList nodes = context.getParentElement().getChildNodes();
				UdpFormatPropertyUtils.appendUdpItemElementToSelected(context, getSelectNode(nodes));
			}
		}
		
	}	
	
	/**
	 * @param nodes
	 * @return
	 */
	private Node getValueNode(NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNamespaceURI().equals(XGuiConstants.XGUI_NAMESPACE_URI) 
					&& node.getLocalName().equals(XGuiConstants.XGUI_VALUE)){	
				return node;
			}			
		}
		return null;
	}
	
	/**
	 * @param nodes
	 * @return
	 */
	private Node getSelectNode(NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNamespaceURI().equals(XSPConstants.XSP_NAMESPACE_URI) 
					&& node.getLocalName().equals(XSPConstants.XSP_ATTRIBUTE_ELEMENT)){	
				Node attr = node.getAttributes().getNamedItem(XSPConstants.XSP_NAME);
				if (attr != null && attr.getNodeValue().equals("selected")) {
					return node;
				}
			}			
		}
		return null;
	}
	
	/**
	 * @return true if the widget represents a checkbox
	 */
	private final boolean isCheckbox(Widget widget) {
		String typeName = widget.getTypeName();
		return WidgetTypeConstants.CHECKBOX.equals(typeName) 
			|| WidgetTypeConstants.TABLE_COLUMN_CHECKBOX_ITEM.equals(typeName);
	}
	
	private final boolean isCheckboxWithEnumValueDefined(Widget widget) {
		String typeName = widget.getTypeName();
		return WidgetTypeConstants.CHECKBOX.equals(typeName) 
			&& StringUtils.isNotBlank(widget.getPropertyValue(PropertyTypeConstants.ENUM_VALUE));
	}
	
	/**
	 * @return true if the widget represents a radio button, and its dataset attribute is defined
	 */
	private final boolean isRadioButtonWithDomainAttributeDefined(Widget widget) {
		String typeName = widget.getTypeName();
		return WidgetTypeConstants.RADIO_BUTTON.equals(typeName)
			&& StringUtils.isNotBlank(widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE));
	}

	private Element createValueElement(WidgetTransformerContext context, Widget widget) {
		Element valueElement;
		if (isCheckbox(widget)) {
			valueElement = createCheckboxValueElement(context, widget);			
		} else if (isRadioButtonWithDomainAttributeDefined(widget))	{
			valueElement = createRadioButtonValueElement(context, widget);	
		} else {
			boolean appendlast = WidgetTypeConstants.TABLE_COLUMN_TEXT_ITEM.equals(widget.getTypeName());
			valueElement = createOtherValueElement(context, widget, appendlast);
		}
		return valueElement;
	}

	/**
	 * @param context
	 * @param widget
	 * @return
	 */
	private Element createOtherValueElement(WidgetTransformerContext context, Widget widget, boolean appendLast) {
		Element valElmt;
		String prefix = widget.getLibraryName();
		Namespace widgetNS = context.getTransformModel().findNamespace(prefix);
		String valueElement = getValueElement(widget);
		valElmt = selectFirstChildElement(XGuiConstants.XGUI_NAMESPACE_URI, valueElement, context.getParentElement());
		if (valElmt == null) {
			valElmt = createElement(context, widgetNS.getUri(), valueElement);

			valElmt.setPrefix(widgetNS.getPrefix());
			Element parent = context.getParentElement();
			Node firstChild = parent.getFirstChild();
			if (firstChild != null && !appendLast) {
				context.getParentElement().insertBefore(valElmt, firstChild);
			} else {
				context.getParentElement().appendChild(valElmt);
			}
		}
		return valElmt;
	}
	
	// DS-4763
	private Element createCheckboxValueElement(WidgetTransformerContext context, Widget widget) {
		
		Element valueElement = null;
		
		Property enumValue = widget.findProperty(PropertyTypeConstants.ENUM_VALUE);
		if (enumValue != null && StringUtils.isNotBlank(enumValue.getValue())) {
			/**
			 * Checkbox associated with an enumeration
			 */
			String prefix = widget.getLibraryName();
			Namespace widgetNamspace = context.getTransformModel().findNamespace(prefix);
			String valueElementName = getValueElement(widget);
			valueElement = selectFirstChildElement(XGuiConstants.XGUI_NAMESPACE_URI, valueElementName, context.getParentElement());
			if (valueElement == null) {
				valueElement = createElement(context, widgetNamspace.getUri(), valueElementName);
				valueElement.setPrefix(widgetNamspace.getPrefix());
				String value =  DomainObjectUtils.retrieveEnumValue(widget);
				valueElement.setTextContent(value);
			}
			TransformUtils.appendChild(context, valueElement);
		} else {
			/**
			 * Checkbox is NOT associated with an enumeration
			 */
			// Create the element <xsp:attribute>
			Namespace ns = context.getTransformModel().findNamespace(XSP_NAMESPACE_URI);
			valueElement = context.getDocument().createElementNS(ns.getUri(), TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
			valueElement.setPrefix(ns.getPrefix());
			TransformUtils.appendChild(context, valueElement);	
			
			// Create the attribute name="selected"
			Attr a = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
			a.setValue("selected");	
			valueElement.setAttributeNode(a);
			return valueElement;
		}
		return valueElement;
	}

	/**
	 * @param context
	 * @param widget
	 * @return
	 */
	private Element createRadioButtonValueElement(WidgetTransformerContext context, Widget widget) {
		String prefix = widget.getLibraryName();
		Namespace widgetNamspace = context.getTransformModel().findNamespace(prefix);
		String valueElementName = getValueElement(widget);
		Element valueElement = selectFirstChildElement(XGuiConstants.XGUI_NAMESPACE_URI, valueElementName, context.getParentElement());
		
		if (valueElement == null) {
			valueElement = createElement(context, widgetNamspace.getUri(), valueElementName);
			valueElement.setPrefix(widgetNamspace.getPrefix());
			String value =  DomainObjectUtils.retrieveEnumValue(widget);
			valueElement.setTextContent(value);
		}
		
		TransformUtils.appendChild(context, valueElement);
		return valueElement;
	}

	/**
	 * @param context
	 * @return
	 */
	private Element createCheckboxValueElement(WidgetTransformerContext context) {
		Element valElmt;
		// Create the element <xsp:attribute>
		Namespace ns = context.getTransformModel().findNamespace(XSP_NAMESPACE_URI);
		valElmt = context.getDocument().createElementNS(ns.getUri(), TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
		valElmt.setPrefix(ns.getPrefix());
		TransformUtils.appendChild(context, valElmt);	
		
		// Create the attribute name="selected"
		Attr a = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
		a.setValue("selected");	
		valElmt.setAttributeNode(a);
		return valElmt;
	}

	/**
	 * Return the first child of the parentElement if it is an Element and if it matches
	 * the uri and the local name passed in parameter 
	 * @param uri uri searched
	 * @param elementName local name searched
	 * @param parentElement parent element
	 * @return the first child of the parentElement if it is an Element and if it matches
	 * the uri and the local name passed in parameter
	 */
	private Element selectFirstChildElement(String uri, String elementName,
			Element parentElement) {
		Element valElmt = null;
		Node firstChild = parentElement.getFirstChild();
		
		if (firstChild != null && firstChild instanceof Element &&
				uri.equals(firstChild.getNamespaceURI()) &&
				elementName.equals(firstChild.getLocalName())) {
			valElmt = (Element)parentElement.getFirstChild();
		}
		return valElmt;
	}

	/**
	 * Update recursively the bean element(s) of the parentNode
	 * @param parentNode the parent node
	 * @param beanName bean name to replace with
	 * @param beanProperty bean property to replace with
	 * @param beanDisplayProperty bean property display name
	 */
	private void updateBeanElement(Node parentNode, String beanName, String beanProperty, String beanDisplayProperty) {
		NodeList nodeList = parentNode.getChildNodes();
		String[] values = {beanName, beanProperty, beanDisplayProperty};
		for (int i=0; i<nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			
			if (BEAN_URI.equals(currentNode.getNamespaceURI()) ||
					BEAN_ELEMENT_TO_UPDATE.contains(currentNode.getNodeName())) {
				updateBeanAttribute(currentNode.getAttributes().getNamedItem(BEAN_BEAN_ATTRIBUTE), values);
				updateBeanAttribute(currentNode.getAttributes().getNamedItem(BEAN_PROPERTY_ATTRIBUTE), values);
			}
			updateBeanElement(currentNode, beanName, beanProperty, beanDisplayProperty);
		}
	}

	/**
	 * Update the bean attribute with the new value. If the attribute is "", replace
	 * the value. If the attribute contains {0}, {1}, ..., replace it with the value. 
	 * Otherwise live it as it is.
	 * @param beanAttribute bean attribute
	 * @param values attribute values
	 * 
	 */
	private void updateBeanAttribute(Node beanAttribute, String[] values) {
		if (beanAttribute != null) {
			String beanAttributeValue = beanAttribute.getNodeValue();
			if (beanAttributeValue != null) {
				MessageFormat format = new MessageFormat(beanAttributeValue);
				beanAttribute.setNodeValue(format.format(values));
			}
		}
	}

	/**
	 * Returns the xml value element to use based on the given widget.
	 * 
	 * @param widget
	 *            The widget.
	 * @return String The name of the element to contain the value
	 * 
	 */
	protected String getValueElement(Widget widget) {
		String name = valueElementMap.get(widget.getTypeName());
		if (name == null) {
			name = XGUI_DEFAULT_VALUE_ELEMENT;
		}
		return name;
	}
	
}
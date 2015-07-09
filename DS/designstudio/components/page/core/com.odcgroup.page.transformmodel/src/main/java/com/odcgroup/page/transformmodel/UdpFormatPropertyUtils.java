/**
 * 
 */
package com.odcgroup.page.transformmodel;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author pkk
 *
 */
public class UdpFormatPropertyUtils {
	
	
	/**
	 * @param context
	 * @param child
	 * @param format
	 */
	public static void appendFormatToValueNode(WidgetTransformerContext context, Node child, String format) {
		if (child != null 
				&& child.getNamespaceURI().equals(XGuiConstants.XGUI_NAMESPACE_URI) 
				&& child.getLocalName().equals(XGuiConstants.XGUI_VALUE)){	
			Node valueChild = child.getFirstChild();
			if (valueChild != null && valueChild.getLocalName().equals(BeanConstants.BEAN_GET_PROPERTY_ELEMENT)) {
				child.insertBefore(getUdpFormatElement(context, format), valueChild);
				Node cNode = child.removeChild(child.getLastChild());
				child.getFirstChild().appendChild(cNode);
			}
		} 
	}
	
	/**
	 * @param context
	 * @param valueNode
	 * @param column
	 */
	public static void appendUnformattedItemToValueNode(WidgetTransformerContext context, Node valueNode, String format) {
		if (valueNode != null 
				&& valueNode.getNamespaceURI().equals(XGuiConstants.XGUI_NAMESPACE_URI) 
				&& valueNode.getLocalName().equals(XGuiConstants.XGUI_VALUE)){	
			Node bean = valueNode.getFirstChild();
			if (bean != null && bean.getLocalName().equals(BeanConstants.BEAN_GET_PROPERTY_ELEMENT)) {
				valueNode.insertBefore(getUdpFormatElement(context, format), bean);
				valueNode.removeChild(valueNode.getLastChild());
				Element unformat = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_UNFORMATTED_ITEM);
				addAttribute(context, unformat, "column", bean.getAttributes().getNamedItem("property").getNodeValue());
				valueNode.getFirstChild().appendChild(unformat);
			}
		}
	}
	
	/**
	 * @param context
	 * @param selectNode
	 */
	public static void appendUdpItemElementToSelected(WidgetTransformerContext context, Node selectNode) {
		if (selectNode != null 
				&& selectNode.getNamespaceURI().equals(XSPConstants.XSP_NAMESPACE_URI)
				&& selectNode.getLocalName().equals(XSPConstants.XSP_ATTRIBUTE_ELEMENT)) {
			Node bean = selectNode.getFirstChild();
			if (bean != null && bean.getLocalName().equals(BeanConstants.BEAN_GET_PROPERTY_ELEMENT)) {
				Element item = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ITEM_ELEMENT);
				addAttribute(context, item, UdpConstants.UDP_ITEM_COLUMN, bean.getAttributes().getNamedItem("property").getNodeValue());
				selectNode.insertBefore(item, bean);
				selectNode.removeChild(selectNode.getLastChild());
			}
		}
	}
	
	/**
	 * @param context
	 * @param format
	 * @return Element
	 */
	private static Element getUdpFormatElement(WidgetTransformerContext context, String format) {
		Element udpFormat = createElement(context,
				UdpConstants.UDP_NAMESPACE_URI,
				UdpConstants.UDP_FORMAT + "-"
						+ getFormatPattern(format));
		
		if (getFormatOption(format)!= null) 
			addAttribute(context, udpFormat, "option", getFormatOption(format));
		
		return udpFormat;
	}
	
	/**
	 * @param context
	 * @param namespace
	 * @param elementName
	 * @return Element
	 */
	private static Element createElement(WidgetTransformerContext context, String namespace, String elementName) {
		Namespace ns = context.getTransformModel().findNamespace(namespace);
		Element e = context.getDocument().createElementNS(ns.getUri(), elementName);
		e.setPrefix(ns.getPrefix());
		return e;
	}	
	
	/**
	 * @param context
	 * @param parent
	 * @param attributeName
	 * @param value
	 * @return Attr
	 */
	private static Attr addAttribute(WidgetTransformerContext context, Element parent, String attributeName, String value) {
		Attr a = context.getDocument().createAttribute(attributeName);
		a.setValue(value);
		parent.setAttributeNode(a);
		return a;
	}
	
	/**
	 * @param format
	 * @return String
	 */
	public static String getFormatPattern(String format) {
		int index = format.indexOf(".");
		if (index>0) {
			return format.substring(0, index);
		} 
		return format;
	}
	
	/**
	 * @param format
	 * @return String
	 */
	public static String getFormatOption(String format) {
		int index = format.indexOf(".");
		if (index>0) {
			return format.substring(index+1, format.length());
		} 
		return null;		
	}
}

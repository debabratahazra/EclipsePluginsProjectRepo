package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * Transformer specific to the format attribute for xgui widgets.
 * This transformer looks for the format attribute on the widget and
 * add a udp-format element to the underlying value node.
 * 
 * @author pkk
 *
 */
public class UdpFormatPropertyTransformer extends BasePropertyTransformer {

	/**
	 * @param type
	 */
	public UdpFormatPropertyTransformer(PropertyType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform
	 * (com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	public void transform(WidgetTransformerContext context, Property property) {
		Widget widget = property.getWidget();
		Property formatProperty = widget.findProperty(PropertyTypeConstants.FORMAT);
		if (formatProperty == null) {
			return;
		}
		String format = formatProperty.getValue();
		if (StringUtils.isNotEmpty(format)) {
			if (widget.getLibrary().getName().equals(WidgetLibraryConstants.XGUI)) {
				String widgetName = widget.getTypeName();
				NodeList nodes = context.getParentElement().getChildNodes();
				if (WidgetTypeConstants.CALDATE_FIELD.equals(widgetName) || "TableColumnCalendar".equals(widgetName)) {
					appendXspAttributeNode(context, property, context.getParentElement(), format, "TableColumnCalendar".equals(widgetName));
					UdpFormatPropertyUtils.appendFormatToValueNode(context, nodes.item(1), format);
				}
			}
		}
	}
	
	/**
	 * @param context
	 * @param parent
	 * @param format 
	 */
	private void appendXspAttributeNode(WidgetTransformerContext context, Property property, Element parent, String format, boolean last) {
		if (!context.isInXTooltipFragment()) {
			Element attrElement = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_ATTRIBUTE_ELEMENT);
			addAttribute(context, attrElement, XSPConstants.XSP_NAME, "date-format");
			attrElement.appendChild(getUdpPatternElement(context, format));
			if (last) {
				parent.appendChild(attrElement);
			} else {
				parent.insertBefore(attrElement, parent.getFirstChild());
			}
		}
	}	
	
	/**
	 * @param context
	 * @param format
	 * @return Element
	 */
	private Element getUdpPatternElement(WidgetTransformerContext context, String format) {
		Element udpFormat = createElement(context,
				UdpConstants.UDP_NAMESPACE_URI,
				UdpFormatPropertyUtils.getFormatPattern(format)+"-"+ UdpConstants.UDP_PATTERN);		
		if (UdpFormatPropertyUtils.getFormatOption(format)!= null) 
			addAttribute(context, udpFormat, "option", UdpFormatPropertyUtils.getFormatOption(format));
		
		return udpFormat;
	}
}

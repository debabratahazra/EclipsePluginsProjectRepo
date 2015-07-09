package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ITEM_COLUMN;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ITEM_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_LABEL_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TEXT_ELEMENT;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * This class represent the WidgetTransformer for the widget udp:item.
 *  
 * @author Alexandre Jaquet
 *
 */
public class UdpItemWidgetTransformer extends BaseWidgetTransformer {
	
	/**
	 * @param w a widget
	 * @return the value of the property {@code PropertyTypeConstants.COLUMN_NAME}
	 */
	protected String getPropertyColumnName(Widget w) {
		String column = "";
		Property prop = w.findProperty(PropertyTypeConstants.COLUMN_NAME);
		if (prop != null && prop.getValue() != null) {
			column = prop.getValue();
		}
		return column;
	}
	
	/**
	 * Creates a new ConditionalWidgetTransformer.
	 * 
	 * @param type the widget type
	 */
	public UdpItemWidgetTransformer(WidgetType type) {
		super(type);	
	}
	
	/**
	 * Transforms the UDP Item Widget and all its children.
	 *
	 * <pre>
	 * 
	 * This transformer emits xml code similar to theses samples.
	 * 
	 * &lt;xgui:label&gt;
	 *     &lt;xgui:text&gt;
	 *        &lt;i18n:text&gt;&lt;udp:item/&gt;&lt;/i18n:text&gt;
	 *     &lt;/xgui:text&gt;
	 * &lt;/xgui:label&gt;
	 * 
	 * &lt;xgui:label&gt;
	 *     &lt;xgui:text&gt;
	 *        &lt;i18n:text&gt;attr.enum&lt;udp:item/&gt;.text&lt;/i18n:text&gt;
	 *     &lt;/xgui:text&gt;
	 * &lt;/xgui:label&gt;
     *
	 * &lt;xgui:label&gt;
	 *     &lt;xgui:text&gt;
	 *        &lt;i18n:text&gt;attr.enum&lt;udp:item column="..."/&gt;.text&lt;/i18n:text&gt;
	 *     &lt;/xgui:text&gt;
	 * &lt;/xgui:label&gt;
	 * </pre>
	 * 
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {

		// create UDP/Item Node
		Element udpItem = createElement(context, UDP_NAMESPACE_URI, UDP_ITEM_ELEMENT);
		String column = getPropertyColumnName(widget);
		if (!StringUtils.isEmpty(column)) {
			addAttribute(context, udpItem, UDP_ITEM_COLUMN, column);
		}
		
		Element oldParent = null; 
		
		boolean isMdfStringEnum = false;
		MdfEnumeration mdfEnum = null;
        MdfModelElement mdfe = getMdfModelElement(context, widget);
        if (mdfe != null) {
	        MdfEntity entity = getMdfType(mdfe);
			if (entity != null && entity instanceof MdfEnumeration) {
				mdfEnum = (MdfEnumeration)entity;
				isMdfStringEnum = mdfEnum.getType().getName().equalsIgnoreCase("string"); 
				
				if (isMdfStringEnum) {
					Element xspLogic = appendElement(context,XSP_NAMESPACE_URI,TransformerConstants.LOGIC_ELEMENT_NAME);
					xspLogic.setTextContent("if ((<udp:item-type/>.equals(\"text\")) &amp;&amp; <udp:item/>.equals(\"\")) {");
					Element myLabel = appendElement(context,XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
					Element myText = appendElement(context, myLabel, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
					Element i18n = appendElement(context, myText, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
					i18n.setTextContent("cbi.enumeration.value.undefined.text");
					xspLogic = appendElement(context,XSP_NAMESPACE_URI,TransformerConstants.LOGIC_ELEMENT_NAME);
					xspLogic.setTextContent("} else {");
				}
	
				Element label = appendElement(context,XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
				Element text = appendElement(context, label, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
				Element i18n = appendElement(context, text, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
				String enumName = mdfEnum.getQualifiedName().getQualifiedName().toLowerCase().replace(':', '.');
				String xmlStr = TransformUtils.transformDomNodeToXML(udpItem);
				i18n.setTextContent(enumName+"."+xmlStr+".text");
				
				if (isMdfStringEnum) {
					Element xspLogic = appendElement(context,XSP_NAMESPACE_URI,TransformerConstants.LOGIC_ELEMENT_NAME);
					xspLogic.setTextContent("}");
				}
				oldParent = context.setParentElement(label);

			} else {
				Element label = appendElement(context,XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
				Element text = appendElement(context, label, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
				text.appendChild(udpItem);
				oldParent = context.setParentElement(label);
			}
			
        } else {
    		Element label = appendElement(context,XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
    		Element text = appendElement(context, label, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
			text.appendChild(udpItem);
			oldParent = context.setParentElement(label);
        }

        transformProperties(context, widget);
        transformEvents(context, widget);
		context.setParentElement(oldParent);
	}
	
	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached.
	 * Note that this does not return all the XML that this transformer will generate. It is essentially
	 * used to help in the content-assist and auto-completion facilities.
	 *  
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return createElement(context, UDP_NAMESPACE_URI, UDP_ITEM_ELEMENT);
	}	
}

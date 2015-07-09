package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.I18nConstants.I18N_ATTRIBUTE_NAME;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_PREFIX;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslationKind;
/**
 * Tranformer clsss for the Xtooltip Widget
 */

public class XtooltipWidgetTransformer extends BaseWidgetTransformer {
    
   	
  /**
   * XtooltipWidgetTransformer Constructor.
   * 
   */
	public XtooltipWidgetTransformer(WidgetType type) {
		super(type);
		
	}

        /**
         * transform the xtooltip widget
         */
	public void transform(WidgetTransformerContext context, Widget widget)
		throws CoreException {
	    Element element = createElement(context, XGUI_NAMESPACE_URI, "xtooltip");
	    //context.getParentElement().appendChild(element);
	    TransformUtils.appendChild(context, element);
	    Element oldParent = context.setParentElement(element);
	    
	    Widget wid=null;
	    boolean insideTable = false;
	    if(!context.getParentWidgets().isEmpty()){
		wid = (Widget)context.getParentWidgets().get(0);
	    }
		if (wid !=null && isWidetWithInTable(wid)) {
			insideTable = true;
		}

	    // translation key
	    String key = context.getTranslationKey(widget, ITranslationKind.NAME);
	    if (key != null) {
		TransformUtils.convertToAttribute(context, context.getParentElement(), "caption", key);
		Attr a = context.getDocument().createAttribute(I18N_NAMESPACE_PREFIX + ":" + I18N_ATTRIBUTE_NAME);
		a.setValue("caption");
		context.getParentElement().setAttributeNode(a);
	    }
	    transformProperties(context, widget);
	    context.setInXTooltipFragment(insideTable);
	    transformChildren(context, widget);
	    context.setInXTooltipFragment(false);
	    if(oldParent!=null){
		context.setParentElement(oldParent);
	    }
	    
	    //removed the for, enable ,halign for the label
	    String[] strArr = { PropertyTypeConstants.ENABLED,PropertyTypeConstants.FOR,"halign"}; 
	    NodeList labelList=context.getParentElement().getElementsByTagName("xgui:label");

	    for (int i = 0; i < labelList.getLength(); i++) {
			Node node = labelList.item(i);
			for (String str : Arrays.asList(strArr)) {
			    if (node.getAttributes().getNamedItem(str) != null) {
				node.getAttributes().removeNamedItem(str);
			    }
			}
	    }

		if (insideTable) {
		NodeList list=context.getParentElement().getElementsByTagName("xgui:textfield");
		Node node=null;
		int nodeSize=list.getLength();
		for(int i=0;i<nodeSize;i++){
		    node=list.item(0);
		    Node parent=node.getParentNode();
		    Element labelElement = createElement(context, XGUI_NAMESPACE_URI, "label");
		    Element textElement=createElement(context, XGUI_NAMESPACE_URI, "text");
		    Element itemElement=createElement(context,UdpConstants.UDP_NAMESPACE_URI, "item");
		    String idvalue=node.getAttributes().getNamedItem("id").getNodeValue();
		    itemElement.setAttribute("column", idvalue);
		    textElement.appendChild(itemElement);
		    labelElement.appendChild(textElement);
		    parent.appendChild(labelElement);
		    NamedNodeMap map=node.getAttributes();
		    Node enableNode = map.getNamedItem("enabled");
		    if (enableNode != null	&& enableNode.getNodeValue().equals("false")) {
			Node parentNode=textElement.getParentNode();
			if ( parentNode!= null && parentNode instanceof Element){
			    ((Element) parentNode).setAttribute("enabled", "false");
			}
		    }
		    parent.removeChild(node);
		}
	    }
	}
	
	
	public Element getParentElement(WidgetTransformerContext context,
			Widget widget) {
		
		String elementName = widget.getTypeName();
		
		return createElement(context, XGUI_NAMESPACE_URI, elementName);
	}
	
	
	

	/**
	 * @param xmlString
	 * @return NodeList
	 */
	@SuppressWarnings("unused")
	private NamedNodeMap xmlToDOM(String xmlString) {
		NodeList nodes = null;
		NamedNodeMap map=null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlString)));
			Node root = document.getFirstChild();
			if(root!=null){
			map= root.getAttributes();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * check if the widget is inside the table.
	 * @param widget
	 * @return true if it is inside the table.
	 */
	private boolean isWidetWithInTable(Widget widget){
	    boolean flag =false;
	    Widget temp=widget;
	    while(temp != null && temp.getParent()!=null){
			temp=temp.getParent();
			if(temp!=null && temp.getTypeName().equals(WidgetTypeConstants.TABLE_TREE)){
			    return  true;
			}
	    }
	    return flag; 
	}
}

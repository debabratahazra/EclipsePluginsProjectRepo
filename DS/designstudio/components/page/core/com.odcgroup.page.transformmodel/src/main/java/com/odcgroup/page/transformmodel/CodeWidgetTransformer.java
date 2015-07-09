package com.odcgroup.page.transformmodel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transforms Widgets which contain a Property having the role 'Code'. In this case the value of this Property is
 * converted directly to Xml. The element itself and all the other Properties are ignored.
 * 
 * @author Gary Hayes
 */
public class CodeWidgetTransformer extends BaseWidgetTransformer {

	/**
	 * Constructor
	 * 
	 * @param type
	 * 			The widget type
	 */
	public CodeWidgetTransformer(WidgetType type) {
		super(type);
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		Property property = widget.findProperty(PropertyTypeConstants.CODE);
		if (property == null) {
			// The Widget does not contain any code
			return;
		}

		if (TransformUtils.isDefaultOrEmpty(property)) {
			// Don't append the value if it has not changed otherwise the XSP pages will be huge
			return;
		}

		String xml = property.getValue();	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(false);
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			Logger.error("Unable to transform the String to Xml", pce);
			throw new PageException("Unable to transform the String to Xml", pce);
		}
		Document d = db.newDocument();
		Element e = d.createElement("dummy");
		d.appendChild(e);
		e.setTextContent(xml);
		NodeList nl = e.getChildNodes();
		TransformUtils.appendNodes(context.getParentElement(), nl);
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
		// The code Widget does not itself generate any xsp. This method is essentially
		// called by the CodePropertyEditor to determine the Element to place before and after the contents
		// of the Code Widget. Therefore we should never arrive here.
		return null;
	}	
}
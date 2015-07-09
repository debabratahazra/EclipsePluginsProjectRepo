package com.odcgroup.page.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;

/**
 * Utility methods for Xml.
 * 
 * @author Gary Hayes
 */
public class XmlUtils {

	/**
	 * Gets the text content from the Element.
	 * Note that later versions of Xml Parsers provide
	 * a method getTextContent which looks up the text contained
	 * within this Element.
	 * 
	 * @param element The Element to get the content from
	 * @return String The text content
	 */
	public static String getTextContent(Element element) {
		String result = "";
		if (element == null) {
			return result;
		}
		
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); ++i) {
			Node n = (Node) nl.item(i);
			if (! (n instanceof Text)) {
				continue;
			}
			Text t = (Text) n;
			result += t.getNodeValue();
		}
		
		return result;
	}
	
	/**
	 * Filters the NodeList returning only Nodes which are of the given type.
	 * 
	 * @param <T> The type of Class
	 * @param nodeList The NodeList
	 * @param clazz The class
	 * @return List of Nodes of the specified type
	 */
	public static <T> List<T> filterNodeList(NodeList nodeList, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		if (nodeList == null) {
			return result;
		}
		
		for (int i = 0; i < nodeList.getLength(); ++i) {
			Object obj = nodeList.item(i);
			if (clazz.isInstance(obj)) {
				result.add((T) obj);
			}
		}
		
		return result;
	}
	
	/**
	 * Creates the document used to parse the code.
	 * 
	 * @param inputStream
	 *            The input stream containing the document
	 * @param namespaceAware True if the document should be namespace aware
	 * @return Document The Document containing the XSP page
	 */
	public static Document createDocument(InputStream inputStream, boolean namespaceAware) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(namespaceAware);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(inputStream);
		} catch (Exception saxe) {
			String xml;
			try {
				inputStream.reset(); // !
				// It's OK here to not care about Encoding, so platform's
				// default charset, because the xml.getBytes()) in
				// com.odcgroup.page.transformmodel.util.TransformUtils.appendTextNamespaces(Element,
				// Text, TransformModel) where this came from did that
				xml = IOUtils.toString(inputStream);
			} catch (IOException e) {
				xml = "Couldn't get XML due to " + e.toString();
			}
			Logger.error("Unable to create the DOM document from XML: " + xml, saxe);
			throw new PageException("Unable to create the DOM document from XML: " + xml, saxe);
		}
	}
	
	/**
	 * Creates a new document used to parse the code.

	 * @param namespaceAware True if the document should be namespace aware
	 * @return Document The Document containing the XSP page
	 */
	public static Document createDocument(boolean namespaceAware) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(namespaceAware);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.newDocument();
		} catch (ParserConfigurationException pce) {
			Logger.error("Unable to create the document", pce);
			throw new PageException("Unable to create the document", pce);
		} 
	}	
}

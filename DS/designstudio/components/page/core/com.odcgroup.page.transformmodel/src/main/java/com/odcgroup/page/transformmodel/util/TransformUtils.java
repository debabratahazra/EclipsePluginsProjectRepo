package com.odcgroup.page.transformmodel.util;

import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_HBOX_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_LBOX_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TEXT_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_VBOX_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.SAXException;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformerConstants;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.XGuiConstants;
import com.odcgroup.page.transformmodel.XSPConstants;
import com.odcgroup.page.transformmodel.crossmodel.OfsModelTransformerUtil;
import com.odcgroup.page.transformmodel.impl.WidgetTransformerContextImpl;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacility;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityUtils;
import com.odcgroup.page.util.XalanUtils;
import com.odcgroup.page.util.XmlUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Transforms the Widget tree to Xml.
 *
 * @author Gary Hayes
 */
@SuppressWarnings("deprecation")
public class TransformUtils {

	/** The Xml document being created. */
	private Document document;

	/** The closing dummy element. */
	static final String DUMMY_ROOT_ELEMENT_CLOSE = "</xgui:dummy>";

	/**
	 * This dummy element is used to allow a String to be converted to an XML
	 * document with potentially many roots. It wraps the String passed as a
	 * parameter before it is converteed to XML.
	 */
	static final String DUMMY_ROOT_ELEMENT_OPEN = "<xgui:dummy>";

	/**
	 * @param ofsProject
	 * @param model
	 */
	protected void addUserDefinedNamespaces(IProject project, TransformModel model) {
		NamespaceFacility facility = NamespaceFacilityUtils.getNamespaceFacility(project);
		facility.addUserDefinedNamespaces(model);
	}

	/**
	 * Transforms the Widget to an XML Document.
	 *
	 * @param widget The Widget to transform
	 * @param ofsProject The OFS project being generated
	 * @exception CoreException
	 * @return Document The Document containing the transformed Widget
	 */
	public Document transformWidgetToW3CXmlDoc(IProject project, Widget widget) throws CoreException {
		createDocument();

		Element element = transformWidgetToElement(project, widget, document);
		document.appendChild(element);

		// retrieve the transformation model
		TransformModel model = TransformModelRegistry.getTransformModel();
		addUserDefinedNamespaces(project, model);

		// If we don't do this the namespaces are repeated at each Element
		appendNamespacesToRootElement(model);

		// The root Element of an XSP page has an Attribute language="java"
		appendLanguageAttribute();

		return document;
	}

	/**
	 * Transforms a Widget using the Document to create Elements etc.
	 *
	 * @param project The eclipse project being generated
	 * @param widget The Widget to transform
	 * @param document The Document to use to create Elements etc.
	 * @return Element The transformed Widget
	 * @exception CoreException
	 */
	private Element transformWidgetToElement(IProject project, Widget widget, Document document) throws CoreException {
		this.document = document;

		TransformModel model = TransformModelRegistry.getTransformModel();
		addUserDefinedNamespaces(project, model);

		WidgetTransformerContext context = new WidgetTransformerContextImpl(MetaModelRegistry.getMetaModel(), model,
				document, project);
		WidgetTransformer wt = model.findWidgetTransformer(widget);
		wt.transform(context, widget);
		return context.getRootElement();
	}

	/**
	 * Transforms a Widget using the Document to create Elements etc.
	 *
	 * @param project The eclipse project being generated
	 * @param widget The Widget to transform
	 * @param document  The Document to use to create Elements etc.
	 * @param parentWidgets  The parent Widgets
	 *
	 * @exception CoreException
	 * @return Element The transformed Widget
	 */
	public Element transformWidgetToElement(IProject project, Widget widget, Document document,
			List<Widget> parentWidgets) throws CoreException {
		this.document = document;

		TransformModel model = TransformModelRegistry.getTransformModel();
		addUserDefinedNamespaces(project, model);

		WidgetTransformerContext context = new WidgetTransformerContextImpl(MetaModelRegistry.getMetaModel(), model,
				document, project);
		context.getParentWidgets().addAll(parentWidgets);
		WidgetTransformer wt = model.findWidgetTransformer(widget);
		wt.transform(context, widget);
		return context.getRootElement();
	}

	/**
	 * Creates the Document to be built.
	 */
	private void createDocument() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.newDocument();
		} catch (ParserConfigurationException pce) {
			Logger.error("Unable to create the document", pce);
			throw new PageException("Unable to create the document", pce);
		}
	}

	/**
	 * Each element can belong to a different namespace. Here we append all the
	 * namespaces that we find to the root element. Example: Element toto is in
	 * namespace "http://titi"
	 *
	 * <myRoot xmlns:toto="http://titi" > <titi:toto></titi:toto>
	 *
	 * In this case the prefix "titi" is determined from the Namespace
	 * definition in the transformmodel.
	 */
	private void appendNamespacesToRootElement(TransformModel model) {
		DocumentTraversal traversal = (DocumentTraversal) document;
		Element root = (Element) document.getDocumentElement();
		TreeWalker tw = traversal.createTreeWalker(document, NodeFilter.SHOW_ALL, null, false);
		Node node = tw.nextNode();

		// Append the namespaces for each element
		while (node != null) {
			NamedNodeMap attrs = node.getAttributes();
			appendAttributeNamespaces(root, attrs, model);

			if (node instanceof Text) {
				// Complicated properties contain Xml as their Text Content. For
				// example
				// if I set the property action to post('<jpath:value-of>... the
				// transformer will
				// create an <xsp:attribute>post('<jpath-value-of...
				// For this reason we need to try and parse the text elements
				// into Xml documents.
				Text t = (Text) node;
				appendTextNamespaces(root, t, model);
			}
			
			if (!(node instanceof Text || node instanceof Comment)) {
				Namespace ns = model.findNamespace(node.getNamespaceURI());
				root.setAttribute(TransformerConstants.NAMESPACE_PREFIX + ns.getPrefix(), ns.getUri());
			}
			node = tw.nextNode();
		}
	}

	/**
	 * Appends the namespaces associated with attributes.
	 *
	 * @param root The root Element
	 * @param attrs The Attributes
	 */
	private void appendAttributeNamespaces(Element root, NamedNodeMap attrs, TransformModel model) {
		// Append the namespaces for each Attribute
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); ++i) {
				Attr a = (Attr) attrs.item(i);

				// Attributes which contain ":" in their names also have
				// namespaces
				String name = a.getNodeName();
				if (StringUtils.isEmpty(name)) {
					continue;
				}

				if (!name.contains(":")) {
					continue;
				}

				String prefix = name.substring(0, name.indexOf(':'));

				Namespace ns = model.findNamespaceByPrefix(prefix);
				root.setAttribute(TransformerConstants.NAMESPACE_PREFIX + ns.getPrefix(), ns.getUri());
			}
		}
	}

	/**
	 * Appends the namespaces associated with text content.
	 *
	 * @param root The root Element
	 * @param text The Text
	 */
	private void appendTextNamespaces(Element root, Text text, TransformModel model) {
		// Create a root element. This ensures that we will always parse an Xml
		// document
		String xml = "<dummy>" + text.getNodeValue() + "</dummy>";
		// no need to parse text element with no ':'
		if (xml.indexOf(':') < 0) {
			return;
		}
		int pos = xml.indexOf("&& ");
		if (pos >= 0) {
			xml = xml.replaceAll("&& ", "&amp;&amp; ");
		}
		pos = xml.indexOf("& ");
		if (pos >= 0) {
			xml = xml.replaceAll("& ", "&amp; ");
		}
		ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		final boolean namespaceAware = false;
		Document document = XmlUtils.createDocument(inputStream, namespaceAware);

		Element dummyElement = document.getDocumentElement();

		DocumentTraversal traversal2 = (DocumentTraversal) document;
		TreeWalker tw2 = traversal2.createTreeWalker(dummyElement, NodeFilter.SHOW_ALL, null, false);

		Node node = tw2.nextNode();

		while (node != null) {
			if (node.getNodeName().indexOf(':') >= 0) {
				String prefix = node.getNodeName().substring(0, node.getNodeName().indexOf(':'));
				Namespace ns = model.findNamespaceByPrefix(prefix);
				root.setAttribute(TransformerConstants.NAMESPACE_PREFIX + ns.getPrefix(), ns.getUri());
			}
			node = tw2.nextNode();
		}
	}

	/**
	 * Appends the language="java" attribute to the root element.
	 */
	private void appendLanguageAttribute() {
		Element root = (Element) document.getDocumentElement();
		root.setAttribute(TransformerConstants.LANGUAGE_NAME, TransformerConstants.LANGUAGE_VALUE);
	}

	/**
	 * Gets the element name of the XGui tag given the Box Type.
	 *
	 * @param boxType The Box Type
	 * @return String The element name
	 */
	public static String getBoxElementName(String boxType) {
		String elementName = "";
		if (boxType.equals(TransformerConstants.ABSOLUTE_BOX_TYPE_VALUE)) {
			elementName = XGUI_LBOX_ELEMENT_NAME;
		} else if (boxType.equals(TransformerConstants.HORIZONTAL_BOX_TYPE_VALUE)) {
			elementName = XGUI_HBOX_ELEMENT_NAME;
		} else if (boxType.equals(TransformerConstants.VERTICAL_BOX_TYPE_VALUE)) {
			elementName = XGUI_VBOX_ELEMENT_NAME;
		}
		return elementName;
	}

	/**
	 * Appends all the nodes to the parent Element. All the nodes are first
	 * imported into the parent's document.
	 *
	 * @param parent The parent Element
	 * @param nodeList The nodes to be appended
	 */
	public static void appendNodes(Element parent, NodeList nodeList) {
		if (parent != null) {
			Document d = parent.getOwnerDocument();
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node n = nodeList.item(i);
				final boolean deep = true;
				Node importedNode = d.importNode(n, deep);
				parent.appendChild(importedNode);
			}
		}
	}

	/**
	 * Tests if the property has changed from its default value or is empty.
	 *
	 * @param property The Property to test
	 * @return boolean True if the Property has its default value or is empty
	 */
	public static boolean isDefaultOrEmpty(Property property) {
		if (property.isDefaultValue()) {
			return true;
		}
		if (StringUtils.isEmpty(property.getValue())) {
			return true;
		}
		return false;
	}

	/**
	 * Appends the child Element to the parent Element contained within the
	 * context. If the parent Element is null the child is not appended.
	 *
	 * @param parent The parent element
	 * @param child The Element to append
	 */
	public static void appendChild(Element parent, Element child) {
		if (parent != null) {
			parent.appendChild(child);
		}
	}

	/**
	 * Appends the child Element to the parent Element contained within the
	 * context. If the parent Element is null the child is not appended.
	 *
	 * @param context The WidgetTransformerContext containing the parent Element
	 * @param child The Element to append
	 */
	public static void appendChild(WidgetTransformerContext context, Element child) {
		Element pe = context.getParentElement();
		if (pe != null) {
			pe.appendChild(child);
		}
	}

	/**
	 * Converts the String to an Attribute. If the String is complicated an
	 * <xsp:attribute> tag is created in its place. This is then appended to the
	 * element. For example suppose that the element is called <test>:
	 *
	 * Property name=toto becomes <test name="toto">
	 *
	 * Property name=get('<jpath-value-of>...') becomes
	 *
	 * <test> <xsp:attribute>get('<jpath-value-of>...')</xsp:attribute> </test>
	 *
	 * @param context The WidgetTransformerContext
	 * @param parentElement The Element to append the Attribute to
	 * @param name The name of the Attribute
	 * @param value The value of the Attribute
	 */
	public static void convertToAttribute(WidgetTransformerContext context, Element parentElement, String name,
			String value) {
		
		if ((ParameterTypeConstants.CALL_URI.equals(name)) && "<pageflow:continuation/>".equals(value)) {
			// igmore the generation, DS-4940
			return;
		}
		
		// If the value contains a valid XML we consider that it probably
		// contains XML.
		// In this case we create an <xsp:attribute> element. Although this
		// supposition is not 100% correct it should work correctly at runtime.
		if (value.contains("<") || name.contains(ParameterTypeConstants.CALL_URI)) {
			// Create the element <xsp:attribute
			Namespace ns = context.getTransformModel().findNamespace(XSP_NAMESPACE_URI);
			Element element = context.getDocument().createElementNS(ns.getUri(),
					TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
			element.setPrefix(ns.getPrefix());
			parentElement.appendChild(element);

			// Create the attribute name="toto"
			Attr a = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
			a.setValue(name);
			element.setAttributeNode(a);

			// Append the contents
			final boolean namespaceAware = false;
			Document d = XmlUtils.createDocument(namespaceAware);
			// parse for cross-model URI
			value = OfsModelTransformerUtil.transform(value, context.getProject());
			// Create a dummy root element
			Element e = d.createElement("dummy");
			d.appendChild(e);
			e.setTextContent(value);
			NodeList nl = e.getChildNodes();
			TransformUtils.appendNodes(element, nl);
		} else if (name.equals("target") && value.equals("layer-not-modal")) {
			// DS-2726 - Support modal = false for layer
			Attr a = context.getDocument().createAttribute(name);
			a.setValue("layer");
			parentElement.setAttributeNode(a);

			Namespace ns = context.getTransformModel().findNamespace(XSP_NAMESPACE_URI);
			Element element = context.getDocument().createElementNS(ns.getUri(),
					TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
			element.setPrefix(ns.getPrefix());
			parentElement.appendChild(element);

			// Create the attribute name="modal"
			Attr attribute = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
			attribute.setValue("modal");
			element.setAttributeNode(attribute);
			element.setTextContent("false");
		} else {
			Attr a = context.getDocument().createAttribute(name);
			a.setValue(value);
			parentElement.setAttributeNode(a);
		}
	}

	/**
	 * DS-1854, requires the UDP format are placed in the same line.
	 *
	 * @param value
	 * @return String
	 */
	private static String constructUdpFormatText(String value) {
		String udpFormatPattern = "(<xgui:value>)\\s*(<udp:format.*>)\\s*(<bean:get-property.*/>)\\s*(</udp:format.*>)\\s*(</xgui:value>)";
		value = value.replaceAll(udpFormatPattern, "$1$2$3$4$5");
		String udpPattern = "(<xsp:attribute.*>)\\s*(<udp:.*-pattern.*>)\\s*(</xsp:attribute>)";
		value = value.replaceAll(udpPattern, "$1$2$3");
		return value;
	}

	/**
	 * Ensure the udp:handle are placed in the same line. This is required by
	 * cocoon and doesn't remove the whole formating.
	 *
	 * @param value The value to parse
	 * @return String
	 */
	private static String constructUdpHandleText(String value) {
		value = value.replaceAll("(<udp:handle>)\\s*(.*)\\s*(</udp:handle>)", "$1$2$3");
		return value;
	}

	/**
	 * Ensure the xsp:param are placed in the same line. This is required by
	 * cocoon and doesn't remove the whole formating.
	 *
	 * @param value The value to parse
	 * @return String
	 */
	private static String constructParamText(String value) {
		value = value.replaceAll(
				"(<xgui:param>)\\s*(<xsp:attribute.*>.*</xsp:attribute.*>)\\s*(.*)\\s*(</xgui:param>)", "$1$2$3$4");
		return value;
	}

	/**
	 * Ensure the xsp:attribute and xsp:expr are placed in the same line. This
	 * is required by cocoon and doesn't remove the whole formating.
	 *
	 * @param value The value to parse
	 * @return String
	 */
	private static String constructExpressionText(String value) {
		value = value.replaceAll("(<xsp:attribute.*>)\\s*(<xsp:expr>.*)\\s*(</xsp:expr>)\\s*(</xsp:attribute>)",
				"$1$2$3$4");
		return value;
	}

	/**
	 * Ensure the xsp:attribute and xsp:expr are placed in the same line. This
	 * is required by cocoon and doesn't remove the whole formating.
	 *
	 * @param value The value to parse
	 * @return String
	 */
	private static String constructFormatedText(String value) {
		value = value.replaceAll("(<xsp:attribute.*>)\\s*(.*)\\s*(</xsp:attribute>)", "$1$2$3");
		return value;
	}

	/**
	 * Ensure that the xgui:text tag is on the same line as <i18n:text> tag.
	 * @param value the value to parse
	 * @return String 
	 */
	private static String constructUdpLabelFormattedText(String value) {
		if(value.contains("<xgui:text>")) {
			value = value.replaceAll("(\\s*<xgui:text>)\\s*(.*)\\s*(</xgui:text>\\s*)", "$1$2$3");
		}
		return value;
	}
	
	/**
	 * Ensure that the xgui:tooltip opening tag is on the same line as the text tag.
	 * @param value the value to parse
	 * @return String 
	 */
	private static String constructUdpLabelToolTipText(String value) {
		if(value.contains("<xgui:tooltip>")) {
		 value = value.replaceAll("(\\s*<xgui:tooltip>)\\s*(.*)\\s*(</xgui:tooltip>\\s*)", "$1$2$3");
		}
		return value;
	}
	/**
	 * Gets the XML from the Widget.
	 *
	 * @param project The eclipse project
	 * @param widget The Widget to get the XML for
	 * @return String The XML as a String
	 */
	public static String transformWidgetToXmlString(IProject project, Widget widget) {
		String result = null;

		if (project == null) {
			Logger.error("Unable to transform the document, project was null");
			throw new PageException("Unable to transform the document, project was null");
		}
		if (widget == null) {
			Logger.error("Unable to transform the document, widget was null");
			throw new PageException("Unable to transform the document, project was null");
		}

		try {
			// First transform the Widget to a DOM object
			TransformUtils xmlt = new TransformUtils();
			Document d = xmlt.transformWidgetToW3CXmlDoc(project, widget);

			// Next transform the DOM object to an InputStream
			Transformer t = XalanUtils.createTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			DOMSource source = new DOMSource(d);
			StringWriter writer = new StringWriter();
			StreamResult target = new StreamResult(writer);
			t.transform(source, target);

			// Finally unescape any xml characters
			result = StringEscapeUtils.unescapeXml(writer.toString());
			result = constructFormatedText(result);
			result = constructExpressionText(result);
			result = constructUdpFormatText(result);
			result = constructParamText(result);
			result = constructUdpHandleText(result);
			result = constructUdpLabelFormattedText(result);
			result = constructUdpLabelToolTipText(result);
		} catch (Exception e) {
			String uri = widget.eResource().getURI().toString();
			Logger.error("Unable to create the document: " + uri, e);
			throw new PageException("Unable to transform the document: " + uri, e);
		}

		return result;
	}

	/**
	 * Converts a DOM node to an XML string and returns it.
	 *
	 * @param node the DOM node to be converted
	 * @return the XML string
	 */
	public static String transformDomNodeToXML(Node node) {

		StringWriter result = new StringWriter();
		try {
			OutputFormat format = new OutputFormat();
			format.setOmitXMLDeclaration(true);
			XMLSerializer serializer = new XMLSerializer(result, format);
			switch (node.getNodeType()) {
			case Node.TEXT_NODE:
				result.append(node.getTextContent());
				break;
			case Node.DOCUMENT_NODE:
				serializer.serialize((Document) node);
				break;
			case Node.ELEMENT_NODE:
				serializer.serialize((Element) node);
				break;
			case Node.DOCUMENT_FRAGMENT_NODE:
				serializer.serialize((DocumentFragment) node);
				break;
			}
		} catch (IOException ex) {
			String message = "Unable to transform the DOM node [" + node.getNodeName() + "] to Xml";
			Logger.error(message, ex);
			throw new PageException("message", ex);
		}

		return result.toString();
	}

	/**
	 * Converts the String to XML. If the String is not valid XML a Text node is
	 * returned.
	 *
	 * @param xml The String to convert
	 * @return NodeList The XML
	 */
	public static NodeList transformXmlStringToNodelist(String xml) {
		String fullXml = TransformUtils.DUMMY_ROOT_ELEMENT_OPEN + xml + TransformUtils.DUMMY_ROOT_ELEMENT_CLOSE;
		InputStream inputStream = new ByteArrayInputStream(fullXml.getBytes());

		DocumentBuilder db = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			Logger.error("Unable to transform the String to Xml", pce);
			throw new PageException("Unable to transform the String to Xml", pce);
		}

		try {
			Document d = db.parse(inputStream);
			Element root = d.getDocumentElement();
			return root.getChildNodes();
		} catch (IOException ioe) {
			Logger.error("Unable to transform the String to Xml", ioe);
			throw new PageException("Unable to transform the String to Xml", ioe);
		} catch (SAXException saxe) {
			// In this case the String is not valid XML. Return a Text element
			// in this case
			Document d = db.newDocument();
			Element e = d.createElement("dummy");
			d.appendChild(e);
			e.setTextContent(xml);
			return e.getChildNodes();
		}
	}
	
	
	private static Element createElement(WidgetTransformerContext context, String namespace, String elementName) {
		Document d = context.getDocument();
		Namespace ns = context.getTransformModel().findNamespace(namespace);
		Element e = d.createElementNS(ns.getUri(), elementName);
		e.setPrefix(ns.getPrefix());
		return e;
	}	
	
	private static boolean hasRichTextTranslation(ITranslation translation, ITranslationKind kind) {
		boolean hasRichText = translation.acceptRichText(kind) && translation.messagesFound(kind, true);
		return hasRichText;
	}

	public static void transformTranslation(WidgetTransformerContext context, ITranslation translation, ITranslationKind kind) {
		if(!translation.messagesFound(kind)) {
			return;
		}
		String sKey = context.getTranslationKey(translation, kind);
		if (StringUtils.isNotBlank(sKey)) {
			boolean generateForRichText = false;
			String transElemName = null;
			if (ITranslationKind.NAME.equals(kind)) {
				transElemName = XGuiConstants.XGUI_TEXT_ELEMENT;
				generateForRichText = TransformUtils.hasRichTextTranslation(translation, kind);
			} else if (ITranslationKind.TEXT.equals(kind)) {
				transElemName = XGuiConstants.XGUI_TOOLTIP_ELEMENT;
				generateForRichText = TransformUtils.hasRichTextTranslation(translation, kind);
			}
			if (transElemName != null) {
				Element transElem = TransformUtils.createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, transElemName);
				context.getParentElement().appendChild(transElem);
				if (generateForRichText) {
					// the translation has rich text decorator, so the special attribute encode
					// will force the interpretation of HTML in the generated page.
					transElem.setAttribute("encode","false");
					// add CSS class "richtext" on the parent element.
					Node node = transElem.getParentNode();
					if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
						org.w3c.dom.Element element = (org.w3c.dom.Element) node;
						if ("xgui:label".equals(element.getNodeName())) {
							/* LIMITATION ONLY THE <XGUI:LABEL> IS SUPPORTED.
							 * The CSS class "richtext" is added to allow the activation of 
							 * ordered/unordered list eventually found in the rich text string
							 */
							final String ATTRIBUTE_CLASS = "class";
							String classAttr = element.getAttribute(ATTRIBUTE_CLASS);
							if (StringUtils.isNotBlank(classAttr)) {
								element.setAttribute(ATTRIBUTE_CLASS, classAttr+" richtext");
							} else  {
								element.setAttribute(ATTRIBUTE_CLASS, "richtext");
							}
						}
					}
				}
				Element i18nElem = TransformUtils.createElement(context, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
				transElem.appendChild(i18nElem);
				i18nElem.setTextContent(sKey);
			}
		}
	}
	
	/**
	 * @param context
	 * @param filterWidget
	 * @return string
	 */
	public static String getDomainAssociationIfAny(WidgetTransformerContext context, Widget widget) {
		Widget parentWidget = getImmediateParent(context.getParentWidgets(), widget.getRootWidget());		
		if (parentWidget != null) {
			return parentWidget.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
		} else {
			return null;
		}
		
	}
	
	/**
	 * @param parentWidgets
	 * @param include
	 * @return widget
	 */
	private static Widget getImmediateParent(List<?> parentWidgets, Widget include) {
		for (Object object : parentWidgets) {
			Widget parentWidget = (Widget) object;
			if (parentWidget.getTypeName().equals(WidgetTypeConstants.INCLUDE)) {
				Property property = parentWidget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
				if (property != null) {
					Model model = property.getModel();
					if (model != null && include.equals(model.getWidget())) {
						return parentWidget;
					}
				}
			}
		}		
		return null;
	}
	
	
	/**
	 * strips the given path - the scheme (if found), project-name and the model folder
	 * segments to return the model package
	 * 
	 * @param path
	 * @return string
	 */
	public static String getModelPackage(String path) {
		// Removes the preceeding "/" if it exists
		int index = path.indexOf("/");
		if (index == 0) {
			path = path.substring(index + 1);
		}
		
		// Remove the first directory (fragment, module or page)
		index = path.indexOf("/");
		if (index >= 0) {
			path = path.substring(index + 1);
		}
		return path;
	}
	
	
	/**
	 * @param uri
	 * @return String
	 */
	public static String getModulePath(URI uri) {
		String value = null;
		String mFolder = WidgetTypeConstants.MODULE.toLowerCase();
		value = uri.path();
		if (uri.isPlatformResource()) {
			String path = OfsResourceHelper.getPathFromPlatformURI(uri);
			value = TransformUtils.getModelPackage(path);
		} 	
		if (value.contains(mFolder+"/")) {
			int index = value.indexOf(mFolder)+mFolder.length();
			value = value.substring(index);
		} 
		value = mFolder+value;
		return value;
	}
	
	/**
	 * If display format property is set to Text widget, then insert the UDP
	 * format logic or else if nothing has been set to display format then
	 * insert xgui:text tag.
	 * 
	 * @author manilkapoor
	 * @param context
	 * @param columnName
	 * @param label
	 * @param formatProperty
	 */
	public static void insertFormatCondition(WidgetTransformerContext context, String columnName, Element label,
			Property formatProperty) {
		String formatValue = formatProperty.getValue();
		
		String[] values = formatValue.split("\\.");
		
		String format = "";
		String option = "";
		
		try {
			if (values.length > 0) {
				format = values[0];
				option = values[1];
			}

		} catch (ArrayIndexOutOfBoundsException exception) {
			option = "";
		}
		
		Element formatLogic = appendElement(context, label, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
		formatLogic.setTextContent("if(null != <udp:unformatted-item column=\"" + columnName + "\"/>) {");

		Element formatText = appendElement(context, label, XGUI_NAMESPACE_URI, XGUI_TEXT_ELEMENT);
		
		//If option is present consider option attribute or else don't consider option attribute.
		if(option.length() > 0){
			formatText.setTextContent("<udp:format-" + format + " " +"option=\"" + option + "\">"
					+ "<udp:unformatted-item column=\"" + columnName + "\"/>" + "</udp:format-"
					+ format + ">");
		}else{
			formatText.setTextContent("<udp:format-" + format + ">"
					+ "<udp:unformatted-item column=\"" + columnName + "\"/>" + "</udp:format-"
					+ format + ">");
		}
		
		Element formatlogic2 = appendElement(context, label, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
		formatlogic2.setTextContent("}");
	}
	
	/**
	 * Creates an Element of the specified namespace and appends it to the specified parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param namespace
	 *            The namespace of the Element to create
	 * @param elementName
	 *            The name of the Element
	 * @return Element The newly-created Element
	 */
	private static Element appendElement(WidgetTransformerContext context, Element parent, String namespace,
			String elementName) {
		Element element = createElement(context, namespace, elementName);
		TransformUtils.appendChild(parent, element);
		return element;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static Property getFormatProperty(Widget widget) {
		Property formatProperty = null;
		for (Property property : widget.getProperties()) {
			if (property.getTypeName().equals(PropertyTypeConstants.FORMAT)) {
				formatProperty = property;
				break;
			}
		}
		return formatProperty;
	}
}

package com.odcgroup.page.transformmodel.tests;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityUtils;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * This class provides helper to test xml generation of model
 * from the page designer
 * @author yan
 */
public abstract class AbstractDSPageTransformationUnitTest extends AbstractDSUnitTest {

	/**
	 * Assert.assert the transformation produces the expected xml. Optionally passes a list of xpath expression for which the
	 * test will not be performed between the result and the expected xml
	 * @param modelUri
	 * @param expectedXml
	 * @param skippedXPaths
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 */
	protected void assertTransformation(URI modelUri, String expectedXml, final String... skippedXPaths) throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException {
		assertTransformation("Unexpected difference with the generation of " + modelUri, modelUri, expectedXml, skippedXPaths);
	}

	/**
	 * @param message
	 * @param modelUri
	 * @param expectedXml
	 * @param skippedXPaths
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 */
	protected void assertTransformation(String message, URI modelUri, String expectedXml, final String... skippedXPaths) throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException {
		String generatedXml = createXmlFromUri(modelUri);

		assertXml(message, expectedXml, generatedXml, skippedXPaths);
	}

	public String createXmlFromUri(URI modelUri) throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		// 1. find fragment
		IOfsModelResource resource = getOfsProject().getOfsModelResource(modelUri);

		// 2. get root widget
		Model model = (Model)resource.getEMFModel().iterator().next();
		Widget root = model.getWidget();

		// 3. transform
		String generatedXml = TransformUtils.transformWidgetToXmlString(getProject(), root);
		return generatedXml;
	}

	public String xmlToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String xmlToString(Node node, boolean omitXmlDeclaration) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            if (omitXmlDeclaration) {
            	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            }
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
	 * returns the result of the xpath on string representing the generated XML
	 *
	 *
	 * @param xpath the query to perform
	 * @param generatedXMLString the XML document as a string on which the xpath query should be performed
	 * @return
	 * @throws XpathException
	 * @throws SAXException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	protected String executeXpathOnXML(String xpath, String generatedXMLString) throws XpathException, SAXException, IOException,
			UnsupportedEncodingException {

		Document w3cDocument = createW3CDocument(generatedXMLString);
		return executeXpathOnXML(xpath, w3cDocument);
	}
	
	/**
	 * Extract the first subtree of an xpath expression
	 * @param xpath
	 * @param xmlString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XpathException
	 */
	protected String extractFirstSubtree(String xpath, String xmlString) throws UnsupportedEncodingException, SAXException, IOException, XpathException {
		Document w3cDocument = createW3CDocument(xmlString);

		HashMap<String, String> nampspaces = extracteNamespacesFromXML(w3cDocument);
		SimpleNamespaceContext simpleNamespaceContext = new SimpleNamespaceContext(nampspaces);

		XMLUnit.setXpathNamespaceContext(simpleNamespaceContext);
		XpathEngine xpathEngine = XMLUnit.newXpathEngine();

		NodeList matchingNodes = xpathEngine.getMatchingNodes(xpath, w3cDocument);
		if (matchingNodes.getLength() == 0) {
			return "";
		} else {
			return xmlToString(matchingNodes.item(0), true);
		}
	}

	/**
	 * returns the result of the xpath on W3C document representing the generated XML
	 *
	 * @param xpath the query to perform
	 * @param w3cDocument the document on which the xpath query should be performed
	 * @return
	 * @throws XpathException
	 */
	private String executeXpathOnXML(String xpath, Document w3cDocument) throws XpathException {
		HashMap<String, String> nampspaces = extracteNamespacesFromXML(w3cDocument);
		SimpleNamespaceContext simpleNamespaceContext = new SimpleNamespaceContext(nampspaces);

		XMLUnit.setXpathNamespaceContext(simpleNamespaceContext);
		XpathEngine xpathEngine = XMLUnit.newXpathEngine();

		return xpathEngine.evaluate(xpath, w3cDocument);
	}

	/**
	 * Returns a list of namespaces used in the xml document
	 *
	 * @param w3cDomDocument
	 * @return
	 */
	private HashMap<String, String> extracteNamespacesFromXML(Document w3cDomDocument) {

		org.jdom.input.DOMBuilder jDomBuilder = new DOMBuilder();
		org.jdom.Document jDomDocument = jDomBuilder.build(w3cDomDocument);

		Namespace namespace = jDomDocument.getRootElement().getNamespace();

		HashMap<String, String> nampspaces = new HashMap<String, String>();
		nampspaces.put(namespace.getPrefix(), namespace.getURI());

		List<?> additionalNamespaces = jDomDocument.getRootElement().getAdditionalNamespaces();
		for (Iterator<?> iterator = additionalNamespaces.iterator(); iterator.hasNext();) {
			Namespace additionalNamespace = (Namespace) iterator.next();
			nampspaces.put(additionalNamespace.getPrefix(), additionalNamespace.getURI());
		}

		return nampspaces;
	}

	/**
	 * Creates a W3C Document from a generated XML string in utf-8 format
	 *
	 * @param generatedXMLString
	 * @throws UnsupportedEncodingException
	 * @throws SAXException
	 * @throws IOException
	 * @return
	 */
	private Document createW3CDocument(String generatedXMLString) throws UnsupportedEncodingException, SAXException, IOException {

			ByteArrayInputStream xmlAsStream = new ByteArrayInputStream(generatedXMLString.getBytes("utf-8"));
			InputSource xmlAsInputSource = new InputSource(xmlAsStream);
			DocumentBuilder documentBuilder = XMLUnit.newControlParser();
			Document document = documentBuilder.parse(xmlAsInputSource);
			return document;
	}

	@Override
	protected void assertXml(String message, String expectedXml,
			String generatedXml, String... skippedXPaths) throws SAXException,
			IOException {
		try {
			super.assertXml(message, expectedXml, generatedXml, skippedXPaths);
		} finally {
			NamespaceFacilityUtils.stopNamespaceFacility();
			CorporateDesignUtils.uninstallCorporateDesign();
		}
	}
	/**
	 * returns the URI for the given resource path
	 * 
	 * @param path
	 * @return
	 */
	protected URI getResourceURI(String path) {
		if (getOfsProject() != null) {
			IOfsModelResource mres = getModelResource(getOfsProject(), path);
			assertNotNull(mres);
			return mres.getURI();
		} else {
			return URI.createPlatformResourceURI(path, true);
		}
	}
	
}

package com.odcgroup.workbench.core.repository;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.workbench.core.OfsCore;

public class PomConverter {

// This doesn't appear to be used (anymore) ?	
//	public InputStream createInputStream(File file) throws IOException {
//		boolean isModelProject = false;
//		
//		IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toURI());
//		if(files.length>0) {
//			IProject project = files[0].getProject();
//			isModelProject = OfsCore.isOfsProject(project);
//		}
//		try {
//			return transformPom(new FileInputStream(file), isModelProject);
//		} catch (ParserConfigurationException e) {
//			OfsCore.getDefault().logError("Cannot instantiate DOM builder", e);
//		} catch (SAXException e) {
//			// we ignore any pom that we cannot parse
//		} catch (IOException e) {
//			OfsCore.getDefault().logError("Cannot parse pom.xml", e);
//		}
//		// in case of error return file unchanged
//		return new FileInputStream(file);
//	}

	static public String transformPom(String s, boolean isModelProject) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(false);
		domFactory.setNamespaceAware(true);

		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		InputSource source = new InputSource(s);
		Document document = domBuilder.parse(source);
		Element rootElement = document.getDocumentElement();

		if(isModelProject) addCoreModelsDependency(document, rootElement); // add the ds-core-models artifact as a dependency
		return toString(document);
	}

	static private void extractParentInformation(Element rootElement) {
		Element parentElement = getFirstElement(rootElement, "parent");
		if (parentElement != null) {
			Element groupIdElement = getFirstElement(parentElement, "groupId");
			if (getFirstElement(rootElement, "groupId") == null && groupIdElement != null) {
				rootElement.appendChild(groupIdElement.cloneNode(true));
			}
			Element versionElement = getFirstElement(parentElement, "version");
			if (getFirstElement(rootElement, "version") == null && versionElement != null) {
				rootElement.appendChild(versionElement.cloneNode(true));
			}
		}
	}

	static private void addCoreModelsDependency(Document document, Element rootElement) {
		Element dependenciesElement = getFirstElement(rootElement, "dependencies");
		if (dependenciesElement == null) {
			dependenciesElement = document.createElement("dependencies");
			rootElement.appendChild(dependenciesElement);
		}
		Element dependencyElement = document.createElement("dependency");

		Element groupIdElement = document.createElement("groupId");
		Element artifactIdElement = document.createElement("artifactId");
		Element versionElement = document.createElement("version");

		groupIdElement.setTextContent("com.odcgroup.ds");
		artifactIdElement.setTextContent("rule-core-models");
		versionElement.setTextContent(OfsCore.getVersionNumber());

		dependencyElement.appendChild(groupIdElement);
		dependencyElement.appendChild(artifactIdElement);
		dependencyElement.appendChild(versionElement);

		dependenciesElement.appendChild(dependencyElement);
	}

	static private void removeNode(Node rootElement, String nodeName) {
		Node node = null;
		while ((node = getFirstElement(rootElement, nodeName)) != null) {
			rootElement.removeChild(node);
		}
	}

	static private Element getFirstElement(Node rootNode, String elementName) {
		NodeList childNodes = rootNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			if (childNode instanceof Element && childNode.getNodeName().equals(elementName))
				return (Element) childNode;
		}
		return null;
	}
	
	private static void removeTypeFromDependencies(Element rootElement) {
		 Element dependencies = getFirstElement(rootElement, "dependencies");
		if(dependencies!=null) {
			NodeList children = dependencies.getChildNodes();
			for(int i=0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if(child instanceof Element) removeNode(child, "type");
			}
		}
	}

	protected static String toString(Document document) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StringWriter sw = new StringWriter();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			transformer.transform(new DOMSource(document), new StreamResult(sw));
			return sw.toString();
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
		return null;
	}

//	protected static InputStream toInputStream(Document document) {
//		try {
//			Transformer transformer = TransformerFactory.newInstance().newTransformer();
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			transformer.transform(new DOMSource(document), new StreamResult(os));
//			InputStream is = new ByteArrayInputStream(os.toByteArray());
//			return is;
//		} catch (TransformerConfigurationException e) {
//		} catch (TransformerException e) {
//		}
//		return null;
//	}

}
package com.odcgroup.workbench.core.repository.maven;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.odcgroup.workbench.core.OfsCore;

public class MavenDependencyHelper {

	/**
	 * Parse the pom xml file
	 * @param pomFile
	 * @return the jdom document of the pom
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document parsePom(IFile pomFile) throws CoreException {
		SAXBuilder builder = new SAXBuilder();
		Document pomDocument;
		try {
			pomDocument = builder.build(pomFile.getLocation().toFile());
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Cannot read the pom (" + pomFile.getFullPath().toPortableString() + ")", e);
			throw new CoreException(status);
		}
		return pomDocument;
	}

	/**
	 * Add a dependency to an existing pom.xml (with at least a project root node).
	 * No check is made to determine if such dependency already exists.
	 * @param pomDocument jdom document that represents the pom.xml
	 * @param groupId 
	 * @param artifactId
	 * @param version
	 */
	public static void addDependency(Document pomDocument, String groupId, String artifactId, String version) {
		if (groupId == null || artifactId == null) {
			throw new IllegalArgumentException("A new dependency needs at least a groupId and an artifact");
		}
		
		Element dependencies = pomDocument.getRootElement().getChild("dependencies", pomDocument.getRootElement().getNamespace());
		if (dependencies == null) {
			dependencies = new Element("dependencies", pomDocument.getRootElement().getNamespace());
			pomDocument.getRootElement().addContent(dependencies);
		}
		
		// Create the dependency elements
		Element dependency = new Element("dependency", dependencies.getNamespace());
		
		Element groupIdElement = new Element("groupId", dependency.getNamespace());
		groupIdElement.setText(groupId);
		dependency.addContent(groupIdElement);
		
		Element artifactIdElement = new Element("artifactId", dependency.getNamespace());
		artifactIdElement.setText(artifactId);
		dependency.addContent(artifactIdElement);
		
		if (version != null) {
			Element versionElement = new Element("version", dependency.getNamespace());
			versionElement.setText(version);
			dependency.addContent(versionElement);
		}
		
		dependencies.addContent(dependency);
	}
	
	/**
	 * Add a dependency to an existing pom.xml (with at least a project root node).
	 * No check is made to determine if such dependency already exists.
	 * @param pomDocument jdom document that represents the pom.xml
	 * @param groupId 
	 * @param artifactId
	 * @param version
	 */
	public static boolean hasDeclaredDependency(Document pomDocument, String groupId, String artifactId, String version) {
		if (groupId == null || artifactId == null) {
			throw new IllegalArgumentException("A new dependency needs at least a groupId and an artifact");
		}
		
		Namespace namespace = pomDocument.getRootElement().getNamespace();
		
		Element dependencies = pomDocument.getRootElement().getChild("dependencies", namespace);
		if (dependencies == null) {
			return false;
		}
		
		// Read the dependencies elements
		List dependenciesChildren = dependencies.getChildren("dependency", namespace);
		boolean found = false;
		for (Element dependency : (List<Element>)dependenciesChildren) {
			String groupIdFound = dependency.getChild("groupId", namespace).getText();
			String artifactIdFound = dependency.getChild("artifactId", namespace).getText();
			String versionFound = dependency.getChild("version", namespace).getText();
			
			if (version == null) {
				if (groupId.equals(groupIdFound) && 
					artifactId.equals(artifactIdFound)) {
					found = true;
					break;
				}
			} else {
				if (groupId.equals(groupIdFound) && 
					artifactId.equals(artifactIdFound) &&
					version.equals(versionFound)) {
					found = true;
					break;
				}
			}
		}
		return found;
	}
	

	/**
	 * Update an existing pom file with a jdom document file
	 * @param pomFile
	 * @param pomDocument
	 * @throws IOException
	 * @throws CoreException
	 */
	public static void updatePom(IFile pomFile, Document pomDocument)
			throws CoreException {
		InputStream is = null;
		try {
			StringWriter pomWriter = new StringWriter();
			new XMLOutputter(Format.getPrettyFormat()).output(pomDocument, pomWriter);
			is = IOUtils.toInputStream(pomWriter.toString(), pomFile.getCharset());
			pomFile.setContents(is, IFile.FORCE, null);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Unable to update the pom (" + pomFile.getFullPath().toPortableString() + ")", e);
			throw new CoreException(status);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

}

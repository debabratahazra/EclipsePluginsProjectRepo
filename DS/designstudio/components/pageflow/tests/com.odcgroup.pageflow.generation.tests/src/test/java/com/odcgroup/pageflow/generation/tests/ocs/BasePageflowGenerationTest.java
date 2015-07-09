package com.odcgroup.pageflow.generation.tests.ocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.odcgroup.otf.utils.tests.XMLAssert;
import com.odcgroup.pageflow.generation.PageflowGenerationCore;
import com.odcgroup.pageflow.generation.PageflowMerger;
import com.odcgroup.pageflow.model.Activator;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.testframework.headless.TestFrameworkHeadlessCore;

/**
 * @author phanikumark
 *
 */
public abstract class BasePageflowGenerationTest extends AbstractDSUnitTest{	


	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
	private static final String schemaSource = "wui-workflow.xsd";
	private static final String OCSSCHEMA_DIR = "resources/schema/pageflow";
	
	/**
	 * @param ofsProject
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	protected void copyPageflowResourcesToModelProject(final IOfsProject ofsProject, String pageflowdir, String targetFolder) throws URISyntaxException, IOException {		
		URL url = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(pageflowdir), null);
        final File pfdir = new File(FileLocator.toFileURL(url).toURI());
        Assert.assertTrue(pfdir.exists() && pfdir.isDirectory());
        final FilenameFilter ff = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".pageflow");
			}
		};		
		
		final IFolder folder = ofsProject.getProject().getFolder(targetFolder);
		class MyWorkspaceJob extends WorkspaceJob {
			public MyWorkspaceJob() {
				super("Copy Model Resource Job");
			}
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				for (File sourceResourceFile : pfdir.listFiles(ff)) {			
					mkdirs(folder);
					IFile targetFile = folder.getFile(sourceResourceFile.getName());
		            try {
						targetFile.create(new FileInputStream(sourceResourceFile), true, null);
					} catch (Exception e) {
						throw new RuntimeException("Unable to add pageflow models to " + ofsProject + " models project", e);
					}
				}
				return Status.OK_STATUS;
			}
		};
		MyWorkspaceJob job = new MyWorkspaceJob();
		job.setRule(ofsProject.getProject());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * @param ofsProject
	 * @param outputfolder
	 * @return
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 */
	@SuppressWarnings("deprecation")
	protected boolean runPageflowGeneration(IOfsProject ofsProject, String outputfolder) 
			throws IOException, InvalidMetamodelVersionException {
		
		WorkflowRunner wfRunner = new WorkflowRunner();

		Map<String, String> properties = new HashMap<String, String>();
		Map<String, Object> slotContents = new HashMap<String, Object>();
		IProject project = ofsProject.getProject();
		boolean success = false;

		String[] EXTENSIONS = { Activator.MODEL_NAME };
		Collection<IOfsModelResource> pfresources = ofsProject.getModelResourceSet()
				.getAllOfsModelResources(EXTENSIONS, IOfsProject.SCOPE_ALL);

		for (IOfsModelResource modelResource : pfresources) {
			Pageflow pageflow =  (modelResource.getEMFModel().get(0) instanceof Pageflow)?
					(Pageflow) modelResource.getEMFModel().get(0) :
					(Pageflow) modelResource.getEMFModel().get(1);

			pageflow = new PageflowMerger(pageflow).merge();
					
			properties.put("projectName", "test");
			properties.put("systemUser", System.getProperty("user.name"));
			properties.put("packageName", "testpackage");
			properties.put("pageflowModelSlot", "pageflowModelSlot");
			properties.put("outlet", project.getFolder(outputfolder).getLocation().toString());
			slotContents.put("pageflowModelSlot",
					Collections.singleton(pageflow));
			properties.put("pageflowFileName", pageflow.getFileName());
			success = wfRunner.run(
					"com/odcgroup/pageflow/generation/ocs/workflow.oaw",
					new NullProgressMonitor(), properties, slotContents);
		}
		return success;
	}
	
	/**
	 * Helper method to get a JAXP DocumentBuilderFactory
	 * 
	 * @return DocumentBuilder
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	protected DocumentBuilderFactory getDocumentBuilderFactory() 
			throws URISyntaxException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);

		try {

			final URL url = FileLocator.find(PageflowGenerationCore
					.getDefault().getBundle(), new Path(OCSSCHEMA_DIR + "/"
					+ schemaSource), null);
			final File schema = new File(FileLocator.toFileURL(url).toURI());

			factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			factory.setAttribute(JAXP_SCHEMA_SOURCE, schema);

		} catch (IllegalArgumentException x) {
			// Happens if the parser does not support JAXP 1.2
			x.printStackTrace();
		}
		return factory;
	}
	


	/**
	 * Checks for the "equality" of the two documents
	 * 
	 * @param src1
	 *            InputSource
	 * @param src2
	 *            InputSource
	 */
	protected void assertEquals(InputSource src1, InputSource src2) {
		if (src1 != src2) {
			try {
				DocumentBuilder builder = getDocumentBuilderFactory()
						.newDocumentBuilder();
				Document doc1 = builder.parse(src1);
				Document doc2 = builder.parse(src2);
				checkDocuments(doc1, doc2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	


	/**
	 * This function uses XMLAssert utility to check the equality of Document
	 * objects.
	 * 
	 * @param doc1
	 * @param doc2
	 * @param element
	 * @param key
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void checkXML(Document doc1, Document doc2, String element,
			String key) {
		XMLAssert xmlAssert = new XMLAssert();

		NodeList templateNodes = doc1.getElementsByTagName(element);
		NodeList testNodes = doc2.getElementsByTagName(element);
		HashMap<String, List> nodesMap = BasePageflowGenerationTest
				.obtainNodesByKey(testNodes, key);

		boolean result = false;
		for (int i = 0; i < templateNodes.getLength(); i++) {
			Node templateNode = templateNodes.item(i);
			NamedNodeMap nmp = templateNode.getAttributes();
			List<Node> testNodeList = nodesMap.get(nmp.getNamedItem(key)
					.getNodeValue());
			if (null == testNodeList) {
				Assert.fail("Document does not contain the element " + element
						+ " with key " + nmp.getNamedItem(key).getNodeValue());
			} else {
				for (Node testNode : testNodeList) {
					if (testNodeList.size() > 1) {
						result = BasePageflowGenerationTest.assertNodesEqual(
								templateNode, testNode);
						if (result == true){
							if (templateNode.getNodeName().equals("wf:process") && testNode.getNodeName().equals("wf:process")) {
								boolean result1 = BasePageflowGenerationTest.assertNodesEqual(
										templateNode.getParentNode(), testNode.getParentNode());
								if (result1 && templateNode.getParentNode().getChildNodes().getLength()!=testNode.getParentNode().getChildNodes().getLength()) {
									xmlAssert.assertEquals(templateNode,
											testNode);
								}
							}else {
								xmlAssert.assertEquals(templateNode,
										testNode);
							}
						}
						
					} else {
						xmlAssert.assertEquals(templateNode, testNode);
					}
				}
			}
		}
	}
	


	/**
	 * Obtain nodes by their key
	 * 
	 * @param nodesList
	 * @param key
	 * @return HashMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static HashMap<String, List> obtainNodesByKey(NodeList nodesList,
			String key) {

		HashMap<String, List> hMap = new HashMap<String, List>();
		for (int i = 0; i < nodesList.getLength(); i++) {
			Node node = nodesList.item(i);
			Node namedItem = node.getAttributes().getNamedItem(key);
			if ( namedItem!=null) {
				String mapKey = namedItem.getNodeValue();
				List<Node> nodeArrayList = new ArrayList<Node>();
				if (!hMap.containsKey(mapKey)) {
					nodeArrayList.add(node);
					hMap.put(mapKey, nodeArrayList);
				} else {
					List<Node> listNodes = hMap.get(mapKey);
					listNodes.add(node);
					hMap.put(mapKey, listNodes);
				}
			}
		}
		return hMap;
	}

	/**
	 * Checks if the nodes are equal
	 * 
	 * @param n1
	 * @param n2
	 * @return boolean
	 */
	private static boolean assertNodesEqual(Node n1, Node n2) {
		boolean result = false;
		if (n1 != null && n2 != null) {
			if (n1.getLocalName().equals(n2.getLocalName())) {
				if (n1.getAttributes().getLength() == n2.getAttributes()
						.getLength()) {
					result = assertAttributesEqual(n1.getAttributes(),
							n2.getAttributes());
				}
			}
		}
		return result;
	}

	/**
	 * Checks if the attributes of an element are the same
	 * 
	 * @param attr1
	 * @param attr2
	 * @return boolean
	 */
	private static boolean assertAttributesEqual(NamedNodeMap attr1,
			NamedNodeMap attr2) {
		String[] attrs = new String[attr1.getLength()];
		String[] attrs1 = new String[attr2.getLength()];
		for (int i = 0; i < attr1.getLength(); i++) {
			attrs[i] = attr1.item(i).getNodeValue();
			attrs1[i] = attr2.item(i).getNodeValue();
		}
		return java.util.Arrays.equals(attrs, attrs1);
	}
	

	/**
	 * @param doc1
	 * @param doc2
	 */
	protected abstract void checkDocuments(Document doc1, Document doc2);
	
	


}

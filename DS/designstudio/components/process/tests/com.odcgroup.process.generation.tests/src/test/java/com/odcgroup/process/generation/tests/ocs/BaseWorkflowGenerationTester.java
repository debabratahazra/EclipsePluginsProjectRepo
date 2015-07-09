/**
 * 
 */
package com.odcgroup.process.generation.tests.ocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.odcgroup.otf.utils.tests.XMLAssert;
import com.odcgroup.process.generation.ProcessGenerationCore;
import com.odcgroup.process.model.Process;

/**
 * @author nba
 * @author pkk
 *
 */
public abstract class BaseWorkflowGenerationTester  {
	
	static IProject project;
    static IPath outputPath;
    
    String[] cases = {"general", "gateways", "chaining", "service"};

    @Before
	public void setUp() throws CoreException {
        project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
        if (project.exists()) {
            project.delete(true, null);
        }
    	project.create(null);
        project.open(null);
        outputPath = project.getFullPath().append(OCSOUTPUT_DIR);
        project.getFolder(OCSOUTPUT_DIR).create(true, true, null);
    }

    @After
	public void tearDown() throws CoreException {
        project.delete(true, null);
    }
    
    /**
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
	public void testProcessGeneration()throws IOException, URISyntaxException {
    	for (String folder : cases) {
    		checkProcessGeneration(folder);
		}
    }
    
    /**
     * @throws IOException
     * @throws URISyntaxException
     */
    @SuppressWarnings("deprecation")
	protected void checkProcessGeneration(String folder) throws IOException, URISyntaxException {
        // need to create an instance in order to have the notation package
        // registered outside Eclipse
        final ResourceSet resources = new ResourceSetImpl();

        WorkflowRunner wfRunner = new WorkflowRunner();

        Map<String, String> properties = new HashMap<String, String>();
        Map<String, Object> slotContents = new HashMap<String, Object>();

        final URL url = FileLocator.find(ProcessGenerationCore.getDefault().getBundle(), new Path(OCSWORKFLOW_DIR+"/"+folder), null);
        File dir = new File(FileLocator.toFileURL(url).toURI());

        Assert.assertTrue(dir.exists() && dir.isDirectory());

        FilenameFilter ff = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".workflow");
            }
        };

        for (File file : dir.listFiles(ff)) {
            URI uri = URI.createFileURI(file.getAbsolutePath().toString());
            Resource res = resources.getResource(uri, false);
            if (res == null) {
                res = resources.createResource(uri);
            }
            res.load(Collections.EMPTY_MAP);
            Process process = null;
            for (EObject obj : res.getContents()) {
                if (obj instanceof Process) process = (Process) obj;
            }
            String fileName = process.getFilename();
			if (fileName == null || fileName.length()==0) {
				fileName= project.getName() + "-" + process.getName();
			} else {
				fileName = fileName+ "-" + process.getName();
			}
            properties.put("projectName", "test");
            properties.put("packageName", "test");
            properties.put("workflowFilePrefix", fileName);
            properties.put("modelSlot", "modelSlot");
            properties.put("outlet", project.getFolder(OCSOUTPUT_DIR).getLocation().toString());
            slotContents.put("modelSlot", Collections.singleton(process));
            
            boolean success = wfRunner.run(
                    "com/odcgroup/process/generation/ocs/workflow.oaw",
                    new NullProgressMonitor(), properties, slotContents);
            
            Assert.assertTrue(success);
        }

        // now validate the generated files       
        checkWorkflows();
    } 
    
    /**
     * @throws URISyntaxException
     * @throws IOException
     */
    public void checkWorkflows() throws URISyntaxException, IOException {
        final File dir = project.getFolder(OCSOUTPUT_DIR).getLocation().toFile();

        Assert.assertTrue(dir.exists() && dir.isDirectory());

        FilenameFilter ff = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };

        if (dir.listFiles(ff).length == 0) {
            Assert.fail("Could not find generated workflow files to check equivalence!!!!!!!!!");
        }

        for (File file : dir.listFiles(ff)) {
            final File template = getCorrespondingTemplateFile(file.getName());
            if (template == null) {
                Assert.fail("Could not find template workflow files to check equivalence!!!!!!!!!" + file.toString());
            } else {
            	assertEquals(new InputSource(new FileInputStream(template)), new InputSource(new FileInputStream(file)));
            }
        }
    }
    
    /**
     * @param fileName
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private File getCorrespondingTemplateFile(String fileName) throws URISyntaxException, IOException {
    	
        final URL url = FileLocator.find(ProcessGenerationCore.getDefault().getBundle(), new Path(OCSTEMPLATE_DIR), null);
        File dir = new File(FileLocator.toFileURL(url).toURI());
        Assert.assertTrue(dir.exists() && dir.isDirectory());
        FilenameFilter ff = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };
        if (dir.listFiles(ff).length == 0) {
            Assert.fail("Could not find template workflow files to check equivalence!!!!!!!!!");
        }
        for (File file : dir.listFiles(ff)) {
        	if (file.getName().equals(fileName)){
        		return file;
        	}
        }
        return null;
    }

    /**
     * Checks for the "equality" of the two documents
     * 
     * @param src1 InputSource
     * @param src2 InputSource
     */
    private void assertEquals(InputSource src1, InputSource src2) {
        if (src1 != src2) {
            try {
                DocumentBuilder builder = getDocumentBuilderFactory().newDocumentBuilder();
                builder.setErrorHandler(new WorkflowGeneratorErrorHandler());
                Document doc1 = builder.parse(src1);

                builder = getDocumentBuilderFactory().newDocumentBuilder();
                builder.setErrorHandler(new WorkflowGeneratorErrorHandler());
                Document doc2 = builder.parse(src2);
                
                //
                checkDocuments(doc1, doc2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This function uses XMLAssert.assert utility to check the equality of Document
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
        HashMap<String, List> nodesMap = BaseWorkflowGenerationTester.obtainNodesByKey(testNodes, key);

        boolean result = false;
        for (int i = 0; i < templateNodes.getLength(); i++) {
            Node templateNode = templateNodes.item(i);
            NamedNodeMap nmp = templateNode.getAttributes();
            List<Node> testNodeList = nodesMap.get(nmp.getNamedItem(key).getNodeValue());
            if (null == testNodeList) {
                Assert.fail("Document does not contain the element " + element
                        + " with key " + nmp.getNamedItem(key).getNodeValue());
            } else {
                for (Node testNode : testNodeList) {
                    if (testNodeList.size() > 1) {
                        result = BaseWorkflowGenerationTester.assertNodesEqual(
                                templateNode, testNode);
                        if (result == true)
                            xmlAssert.assertEquals(templateNode, testNode);
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
            String mapKey = node.getAttributes().getNamedItem(key).getNodeValue();
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
                if (n1.getAttributes().getLength() == n2.getAttributes().getLength()) {
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
     * Helper method to get a JAXP DocumentBuilderFactory
     * 
     * @return DocumentBuilder
     * @throws IOException
     * @throws URISyntaxException
     */
    private DocumentBuilderFactory getDocumentBuilderFactory()
            throws URISyntaxException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);

        try {
            final URL url = FileLocator.find(
                    ProcessGenerationCore.getDefault().getBundle(), new Path(OCSSCHEMA_DIR
                            + "/" + schemaSource), null);
            final File schema = new File(FileLocator.toFileURL(url).toURI());
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
            factory.setAttribute(JAXP_SCHEMA_SOURCE, schema.getAbsoluteFile());
        } catch (IllegalArgumentException x) {
            // Happens if the parser does not support JAXP 1.2
            x.printStackTrace();
		}
        return factory;
    }
    
    
    /**
     * ErrorHandler Implementation
     *
     */
    private class WorkflowGeneratorErrorHandler implements ErrorHandler {

        public void error(SAXParseException exception) throws SAXException {
            System.out.println("ERROR " + exception.getLocalizedMessage());

        }

        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("ERROR " + exception.getLocalizedMessage());

        }

        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("WARNING " + exception.getLocalizedMessage());

        }

    }
    
    /*_______________________________________ abstract method */
    
    protected abstract void checkDocuments(Document doc1, Document doc2);

	/**
	 * static variables
	 */
	private static final String OCSOUTPUT_DIR = "output";

	private static final String OCSSCHEMA_DIR = "resources/schema/workflow";
	
    private static final String OCSWORKFLOW_DIR = "resources/models/workflow";

	private static final String OCSTEMPLATE_DIR = "resources/templates/workflow";

	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	
	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	private static final String schemaSource = "workflow.xsd";
}

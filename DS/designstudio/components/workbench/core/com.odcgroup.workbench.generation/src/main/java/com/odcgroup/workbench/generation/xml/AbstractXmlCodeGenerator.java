package com.odcgroup.workbench.generation.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.dsl.xml.XtextToNameURISwapper;
import com.odcgroup.workbench.dsl.xml.XtextToNameURISwapperSimpleImpl;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;

/**
 * Generic Generator which generates that "smart" XML we use in T24 (with the name:/ URIs etc.).
 *  
 * @author Michael Vorburger - Original author of the Xtext DSL EMF to XML stuff
 * @author Sreekanth - Some Extensions
 */
public abstract class AbstractXmlCodeGenerator implements CodeGenerator, CodeGenerator2 {

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractXmlCodeGenerator.class);
	private XtextToNameURISwapper nameURISwapper = new XtextToNameURISwapperSimpleImpl();
	private boolean postGenTransformRequired = false;
	
	@Override
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources, IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new IllegalStateException("This should never be called anymore - it's a CodeGenerator2 now!");
	}
	
	/**
	 * Transforms the Xtext DSL EMF model resources into XML resources.
	 *  
	 * @param resource original DSL model resource (non-XML)
	 * @param xmlURI URI of the XML File to write
	 * @return XML model resource, not yet saved
	 */
	public Resource generateXMLResourceWithoutYetSavingIt(Resource resource, URI xmlURI, ModelLoader loader) throws Exception {
		// Get the new EObject with the references replaced correctly
		EObject root = getEObjectFromResource(resource);
		
		root = EcoreUtil.copy(root);
		transform(root,resource);
		root = nameURISwapper.cloneAndReplaceAllReferencesByNameURIProxies(root ,resource );
		
		// Create the new EObject as XML Resource
		GenericXMLResourceFactoryImpl resourceFactory = new GenericXMLResourceFactoryImpl();
		Resource xmlResource = resourceFactory.createResource(xmlURI);
		xmlResource.getContents().add(root);

		return xmlResource;
	}

	protected EObject getEObjectFromResource(Resource resource) {
		EObject root = resource.getContents().get(0);
		return root;
	}
	
	/**
	 * This methods is a "hook" to allow subclasses to do some "processing" on
	 * the model, before it is written out in XML. The default implementation
	 * here does nothing.
	 * 
	 * @param root Root Object in the model going which is about to be written
	 *             out. This is already a clone, it's safe to modify it.
	 *            resource Resource of the model 
	 */
	protected void transform(EObject root ,Resource resource) throws Exception {
	}

	/**
	 * NOTE: RepositoryFileContentHandler in basic-rtc-ui has direct accesses to this...
	 * TODO make changes to use {@link #generateXML(Resource, ModelLoader, IFileSystemAccess)}
	 */
	public void generateXML(Resource resource, ModelLoader loader) throws Exception {
		
		Resource xmlResource = generateXMLResource(resource, loader);
		if (xmlResource != null) {
			try {
				xmlResource.save(getSaveOptions());
			} finally {
				xmlResource.unload();
			}
		}
	}

	/**
	 * This save option helps in xml save for default values.
	 *
	 * @return
	 */
	private Map<String, Boolean> getSaveOptions() {
		Map<String,Boolean> options = new HashMap<String,Boolean>();
		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, true);
		return options;
	}

	protected void generateXML(Resource resource, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		Resource xmlResource = generateXMLResource(resource, loader);
		saveResource(fsa, xmlResource);
	}

	protected void saveResource(IFileSystemAccess fsa, Resource xmlResource) throws IOException,
			UnsupportedEncodingException, ParserConfigurationException, SAXException, TransformerException {
		if (xmlResource != null) {
			try {
				String xmlFileName = xmlResource.getURI().lastSegment();
				if(!(xmlFileName.toLowerCase().endsWith(".xml"))){
					xmlFileName = xmlFileName + ".xml";
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				xmlResource.save(baos, getSaveOptions());
				String xml = baos.toString(CharEncoding.UTF_8);
				if(isPostGenTransformRequired()){
					xml = transform(xml);
				}
				if(fsa instanceof SimplerEclipseResourceFileSystemAccess2){
					((SimplerEclipseResourceFileSystemAccess2)fsa).setForceGeneration(true);
				}
				fsa.generateFile(xmlFileName, xml);
			} finally {
				xmlResource.unload();
			}
		}
	}

	private Document convertStringToDocument(String xml) throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return db.parse(IOUtils.toInputStream(xml, "UTF-8"));
	}

	private String convertDocumentToString(Document doc) throws UnsupportedEncodingException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		ByteArrayOutputStream memoryOutputStream = new ByteArrayOutputStream(1024);
		StreamResult result = new StreamResult(new OutputStreamWriter(memoryOutputStream, "UTF-8"));
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		return memoryOutputStream.toString();
	}

	/**
	 * This method performs transformation required on generated xml
	 * artifact.
	 *
	 * @param xml - the xml generated by generator
	 *
	 */
	private String transform(String xml) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		Document doc = convertStringToDocument(xml);
		transform(doc);
		return convertDocumentToString(doc);
	}

	/**
	 * This method is a "hook" to allow subclasses to do some "processing" on
	 * the xml generated, before it is written out in File. The default implementation
	 * here does nothing.
	 * To enable this set the postGenTransformRequired to true
	 *
	 * @param doc- document for xml processing
	 */
	protected void transform(Document doc) {
	}

	// Caller must unload() this Resource
	private Resource generateXMLResource(Resource resource, ModelLoader loader) throws Exception {
		URI uri = resource.getURI().appendFileExtension("xml");
		URI xmlURI = URI.createURI(getActualURI(uri));
		return generateXMLResourceWithoutYetSavingIt(resource, xmlURI, loader);
	}

	protected String getActualURI(URI uri) {
		String platformUri = uri.toString();
		String[] segments = uri.segments();
		String srcdir = getModelsExtension();
		srcdir = (segments[2].equals(srcdir)) ? srcdir : "";
		String projectPath = platformUri.split("(?i)-models/" + srcdir)[0];
		if(uri.toString().equals(projectPath)){
			return uri.toString(); // JUnit test case
		}
		return projectPath.concat("-models-gen/src/xml-t24i/" + uri.lastSegment());
	}
	
	abstract protected String getModelsExtension();
	
	@Override
	public void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		if (getModelsExtension().equals(input.fileExtension()))
			generateXML(loader.getResource(input), loader, fsa);
	}

	/**
	 * @return the transformDocumentRquired
	 */
	public boolean isPostGenTransformRequired() {
		return postGenTransformRequired;
	}

	/**
	 * @param transformDocumentRquired the transformDocumentRquired to set
	 */
	public void setPostGenTransformRequired(boolean transformDocumentRquired) {
		this.postGenTransformRequired = transformDocumentRquired;
	}
	
}

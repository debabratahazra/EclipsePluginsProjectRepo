package com.odcgroup.cdm.generation.services;

import static com.odcgroup.cdm.generation.util.CdmConstants.CDM_ANNOTATION_NAME;
import static com.odcgroup.cdm.generation.util.CdmConstants.CDM_NAMESPACE;
import static com.odcgroup.cdm.generation.util.CdmConstants.CDM_XML_ANNOTATION_NAME;

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.odcgroup.cdm.generation.CdmOawGeneratorHelper;
import com.odcgroup.cdm.generation.util.MdfUtils;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * Writes the services (classes), that inherit from the class GenericService of
 * the CDM Domain Model, to the cdmServices.xml file.
 * 
 * @author Frederic Le Maitre
 */
public class ServicesWriter {

    /**
     * The name of the abstract class, in the CDM domain model, that represents
     * the CDM generic service.
     */
    private static final String ABSTRACT_CDM_SERVICE_CLASS_NAME = "CoreService";

    /** Folder where the service file is generated */
    private static final String FOLDER = "META-INF/config";
    
    /** The name of the file to which the services are written. */
    private static final String FILE_NAME = FOLDER + "/cdmServices.xml";

    /** The domain model. */
    private final MdfDomain domain;

    /** The output file path. */
    private String outputFilePath;

    /**
     * Creates a new ServicesWriter.
     * 
     * @param domain the Domain Model.
     * @param outputFilePath te output file path.
     */
    public ServicesWriter(MdfDomain domain, String outputFilePath) {
	this.domain = domain;
	this.outputFilePath = outputFilePath;
    }

    /**
     * Writes the cdmServices.xml file.
     */
    @SuppressWarnings("unchecked")
    public void write() {
	try {
	    Document document = createDocument();

	    Element root = document.createElement("cdmServices:cdmServices");
	    document.appendChild(root);

	    root.setAttribute("xmlns:cdmServices", "http://www.odcgroup.com/cdmServices");
	    root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    root.setAttribute("xsi:schemaLocation", "http://www.odcgroup.com/cdmServices cdmServices.xsd");

	    Element services = document.createElement("cdmServices:services");
	    root.appendChild(services);

	    for (Iterator iterator = domain.getEntities().iterator(); iterator.hasNext();) {
		MdfEntity entity = (MdfEntity) iterator.next();

		if (entity instanceof MdfClass && isCDMService((MdfClass) entity)) {
		    writeService((MdfClass) entity, document, services);
		}
	    }

	    saveDocument(document);
	} catch (Exception e) {
	    System.err.println("Unable to write the CDM Services XML file.");
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    /**
     * Creates a new XML document.
     * 
     * @return the document used to save the services.
     * @throws SAXException if an error occurs.
     * @throws ParserConfigurationException if an error occurs.
     */
    private Document createDocument() throws SAXException, ParserConfigurationException {
	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	return documentBuilder.newDocument();
    }

    /**
     * Creates a new XML transformer. <br>
     * <br>
     * This method corrects a bug in the Java API (up until 1.5.06) in which the
     * indentation does not work correctly.
     * 
     * @return Transformer the transformer.
     */
    private Transformer createTransformer() {
	try {
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();

	    // Correct the bug in the Java API. This is corrected by the JDK1.6.
	    transformerFactory.setAttribute("indent-number", new Integer(4));

	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");

	    // Correct the bug in the Java API. This is corrected by the JDK1.6.
	    transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    return transformer;
	} catch (TransformerConfigurationException tce) {
	    throw new RuntimeException("Unable to create the XML transformer", tce);
	}
    }

    /**
     * Checks if the class in parameter is a "CDM service" class.
     * 
     * @param clazz a class from the domain model.
     * @return <code>true</code> if the class in parameter is a "CDM service"
     *         class, else <code>false</code>.
     */
    private boolean isCDMService(MdfClass clazz) {
	if (!clazz.isAbstract()) {
	    MdfClass baseClass = clazz.getBaseClass();

	    while (baseClass != null) {
		if (ABSTRACT_CDM_SERVICE_CLASS_NAME.equals(baseClass.getName())) {
		    return true;
		}
		baseClass = baseClass.getBaseClass();
	    }
	}

	return false;
    }

    /**
     * Saves the XML document to file.
     * 
     * @param document The DOM document
     * @throws TransformerException if an error occurs.
     */
    private void saveDocument(Document document) throws TransformerException {
	Transformer transformer = createTransformer();
	Source source = new DOMSource(document);
	new File(outputFilePath + "/" + FOLDER).mkdirs();
	File file = new File(outputFilePath + "/" + FILE_NAME);
	Result result = new StreamResult(file);
	transformer.transform(source, result);
    }

    /**
     * Writes a "CDM service" from CDM Domain Model to the XML file.
     * 
     * @param service a "CDM service" from CDM Domain Model.
     * @param document The DOM document
     * @param parent the parent element of the service element in the XML file.
     */
    @SuppressWarnings("unchecked")
    private void writeService(MdfClass service, Document document, Element parent) {
	Element element = document.createElement("cdmServices:service");
	parent.appendChild(element);

	element.setAttribute("name", service.getName());

	for (Iterator iterator = service.getProperties().iterator(); iterator.hasNext();) {
	    MdfProperty property = (MdfProperty) iterator.next();

	    Element propertyElement = document.createElement("cdmServices:property");
	    element.appendChild(propertyElement);

	    propertyElement.setAttribute("name", property.getName());
	    propertyElement.setAttribute("javatype", CdmOawGeneratorHelper.getJavaType(property));

	    if (property.getType() instanceof MdfEnumeration) {
		MdfEnumeration enumeration = (MdfEnumeration) property.getType();
		String enumerationPath = MdfUtils.getAnnotationPropertyValue(enumeration, CDM_NAMESPACE,
			CDM_ANNOTATION_NAME, CDM_XML_ANNOTATION_NAME);
		if (enumerationPath != null && enumerationPath.trim().equals("")) {
			propertyElement.setAttribute("enumeration", "CdmConfig/General/" + enumeration.getName());			
		} else {
			propertyElement.setAttribute("enumeration", "CdmConfig/" + enumerationPath.replace('.', '/'));			
		}
	    }
	}

    }

}

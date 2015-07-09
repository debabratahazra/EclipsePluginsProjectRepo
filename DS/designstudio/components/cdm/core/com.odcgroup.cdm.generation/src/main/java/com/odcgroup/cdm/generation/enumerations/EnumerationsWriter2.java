package com.odcgroup.cdm.generation.enumerations;

import static com.odcgroup.cdm.generation.util.CdmConstants.CDM_ANNOTATION_NAME;
import static com.odcgroup.cdm.generation.util.CdmConstants.CDM_KEY_ANNOTATION_NAME;
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

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.odcgroup.cdm.generation.util.MdfUtils;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;


/**
 * Writes the enumerations to the cdm-enumeration.xml file.
 * <pre>
 *  &lt;cdmEnum:text-enumeration name="CdmConfig/ParticipantProfile/Marketing/TypeOfClient"&gt;
 *       &lt;cdmEnum:entry key="Elderly Person" value="EP"/&gt;
 *       &lt;cdmEnum:entry key="Business Owner" value="BO"/&gt;
 *       &lt;cdmEnum:entry key="Inheritor" value="I"/&gt;
 *       &lt;cdmEnum:entry key="High Income Earning" value="HIEI"/&gt;
 *  &lt;/cdmEnum:text-enumeration&gt;
 * </pre>
 * 
 * @author Gary Hayes
 */
public class EnumerationsWriter2 {
	
	/** Folder where the enumeration file is generated */
	private static final String FOLDER = "META-INF/config";
	
	/** The name of the file to which the permitted values are written. */
	private static final String FILE_NAME = FOLDER + "/cdm-enumeration.xml";

	/** The domain model. */
	private final MdfDomain domain;
	
	/** The output file path. */
	private String outputFilePath;

	/** The Xml document being written. */
	private Document document;

	/**
	 * Creates a new CustomFieldEnumerationsWriter.
	 * 
	 * @param domain
	 *            The Domain Model
	 * @param outputFilePath The output file path
	 */
	public EnumerationsWriter2(MdfDomain domain, String outputFilePath) {
		this.domain = domain;
		this.outputFilePath = outputFilePath;
	}

	/**
	 * Writes the file.
	 */
	@SuppressWarnings("rawtypes")
	public void write() {
		try {
			document = createDocument();
			Element root = document.createElement("cdmEnum:cdm");
			document.appendChild(root);
			
			root.setAttribute("xsi:schemaLocation", "http://www.odcgroup.com/cdmEnum cdmEnum.xsd");
			root.setAttribute("xmlns:cdmEnum", "http://www.odcgroup.com/cdmEnum");
			root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			
			
			for (Iterator it = domain.getEntities().iterator(); it.hasNext();) {
				MdfEntity e = (MdfEntity) it.next();
				if (e instanceof MdfEnumeration) {
					MdfEnumeration mdfe = (MdfEnumeration) e;
					if (hasEnumAnnotations(mdfe)) {
						writeAnnotatedEnumeration(mdfe, root);
					} else {
						writeStandardEnumeration(mdfe, root);
					}
				}
			}
			
			save();
			
		} catch (Exception e) {
			System.err.println("Unable to write the custom field enumerations");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns true if the Enumeration is an existing (custom one) which was previously
	 * defined outside of Design Studio. In this case it has special annotations which 
	 * are used to create the custom fields file.
	 * 
	 * @param enumeration The Enumeration to test
	 * @return boolean True if the Enumeration is an existing (custom one) which was previously
	 * 		defined outside of Design Studio
	 */
	private boolean hasEnumAnnotations(MdfEnumeration enumeration) {
		String name = MdfUtils.getAnnotationPropertyValue(enumeration, CDM_NAMESPACE, CDM_ANNOTATION_NAME, CDM_XML_ANNOTATION_NAME);
		return StringUtils.isNotEmpty(name);
	}
	
	/**
	 * Writes the standard enumeration to the file.
	 * 
	 * @param enumeration
	 *            The MdfEnumeration to write
	 * @param root The root Xml Element
	 * @throws SAXException
	 *             Thrown if an error occurs
	 * @throws ParserConfigurationException,
	 *             IOException Thrown if an error occurs
	 */
	@SuppressWarnings("rawtypes")
	private void writeStandardEnumeration(MdfEnumeration enumeration, Element root) throws SAXException, ParserConfigurationException {	
		Element e = document.createElement("cdmEnum:text-enumeration");
		String name = "CdmConfig/General/" + enumeration.getName();
		
		e.setAttribute("name", name);
		root.appendChild(e);
		
		// if null value is allowed, create an empty enumvalue
		if (enumeration.isAcceptNullValue()){
			writeNullEnumerationValue(e);
		}
		
		// Now that we have created the element write the enumerations
		for (Iterator it = enumeration.getValues().iterator();it.hasNext(); ) {
			MdfEnumValue v = (MdfEnumValue) it.next();
			writeStandardEnumerationValue(e, v);
		}
	}
	
	/**
	 * DS-706
	 * Writes an empty enumeration value.
	 * 
	 * @param parent
	 */
	private void writeNullEnumerationValue(Element parent){
		Element e = document.createElement("cdmEnum:entry");
		parent.appendChild(e);
				
		e.setAttribute("value", "");
		e.setAttribute("key", "");
	}
	
	/**
	 * Writes a standard enumeration value.
	 * 
	 * @param parent The parent Xml Element to write the value to
	 * @param v The MdfEnumValue
	 */
	private void writeStandardEnumerationValue(Element parent, MdfEnumValue v) {
		Element e = document.createElement("cdmEnum:entry");
		parent.appendChild(e);
				
		e.setAttribute("value", v.getValue());
		e.setAttribute("key", v.getName());
	}	

	/**
	 * Writes the enumeration to the file. This Enumeration has special annotations determining
	 * the name of the file and other features...
	 * 
	 * @param enumeration
	 *            The MdfEnumeration to write
	 * @param root The root Xml Element
	 * @throws SAXException
	 *             Thrown if an error occurs
	 * @throws ParserConfigurationException,
	 *             IOException Thrown if an error occurs
	 */
	@SuppressWarnings("rawtypes")
	private void writeAnnotatedEnumeration(MdfEnumeration enumeration, Element root) throws SAXException, ParserConfigurationException {		
		String elementName = MdfUtils.getAnnotationPropertyValue(enumeration, CDM_NAMESPACE, CDM_ANNOTATION_NAME, CDM_XML_ANNOTATION_NAME);
			
		Element e = document.createElement("cdmEnum:text-enumeration");
		
		String s = elementName.replace('.', '/');
		String name = "CdmConfig/" + s;
		
		e.setAttribute("name", name);
		root.appendChild(e);
		
		// DS-706 if null value is allowed, create an empty enumvalue
		if (enumeration.isAcceptNullValue()){
			writeNullEnumerationValue(e);
		}
		
		// Now that we have created the element write the enumerations
		for (Iterator it = enumeration.getValues().iterator();it.hasNext(); ) {
			MdfEnumValue v = (MdfEnumValue) it.next();
			writeAnnotatedEnumerationValue(e, v);
		}
	}
	
	/**
	 * Writes an enumeration value. This Enumeration has special annotations determining
	 * the name of the file and other features...
	 * 
	 * @param parent The parent Xml Element to write the value to
	 * @param v The MdfEnumValue
	 */
	private void writeAnnotatedEnumerationValue(Element parent, MdfEnumValue v) {
		Element e = document.createElement("cdmEnum:entry");
		parent.appendChild(e);

		String value = v.getValue();
		e.setAttribute("value", value);
		String key = MdfUtils.getAnnotationPropertyValue(v, CDM_NAMESPACE, CDM_ANNOTATION_NAME, CDM_KEY_ANNOTATION_NAME);
		if (key != null && key.trim().length() >0 ){
			e.setAttribute("key", key);
		} else {
			e.setAttribute("key", v.getName());
		}
	}
		
	/**
	 * Saves the document to file.
     *
	 * @throws TransformerException
	 *             Thrown if an error occurs
	 */
	private void save() throws TransformerException {
		Transformer t = createTransformer();
		Source source = new DOMSource(document);
		new File(outputFilePath + "/" + FOLDER).mkdirs();
		File f = new File(outputFilePath + "/" + FILE_NAME);
		Result result = new StreamResult(f);
		t.transform(source, result);
	}

	/**
	 * Creates a new Xml document.
	 * 
	 * @return Document The Document used to save the custom field data in
	 * @throws SAXException
	 *             Thrown if an error occurs
	 * @throws ParserConfigurationException,
	 *             IOException Thrown if an error occurs
	 */
	private Document createDocument() throws SAXException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.newDocument();
	}
	
	/**
	 * Creates a new Transformer. This method corrects a bug in the Java API (up until 1.5.06) in which the indentation
	 * does not work correctly.
	 * 
	 * @return Transformer The Transformer
	 */
	private Transformer createTransformer() {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();

			// Correct the bug in the Java API. This is corrected by the JDK1.6
			tf.setAttribute("indent-number", new Integer(4));

			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty(OutputKeys.METHOD, "xml");

			// Correct the bug in the Java API. This is corrected by the JDK1.6
			t.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			return t;
		} catch (TransformerConfigurationException tce) {
			throw new RuntimeException("Unable to create the Transformer", tce);
		}
	}	
}
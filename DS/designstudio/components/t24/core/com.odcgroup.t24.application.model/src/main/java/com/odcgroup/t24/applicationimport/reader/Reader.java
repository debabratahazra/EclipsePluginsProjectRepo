package com.odcgroup.t24.applicationimport.reader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.odcgroup.t24.applicationimport.ApplicationsImportProblemsException;
import com.odcgroup.t24.applicationimport.schema.DomainServiceResponse;
import com.odcgroup.t24.applicationimport.schema.MetaT24;
import com.odcgroup.t24.applicationimport.schema.Module;
import com.odcgroup.t24.helptext.schema.T24Help;

/**
 * Reads T24 Application XML via JAXB.
 * 
 * @author Michael Vorburger
 */
public class Reader {

	// Schema and JAXBContext are "thread safe"
	
	private static final String appsPkg = MetaT24.class.getPackage().getName();
	private static final String helptextPkg = T24Help.class.getPackage().getName();
	
	private final JAXBContext jaxbContext;
	private final Unmarshaller applicationUnmarshaller;
	private final Unmarshaller helptextUnmarshaller;

	public Reader() throws JAXBException, SAXException {
		jaxbContext = JAXBContext.newInstance(appsPkg + ":" + helptextPkg);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		URL applicationSchemaURL = Reader.class.getResource("/meta-t24.xsd");
		Schema applicationSchema = schemaFactory.newSchema(applicationSchemaURL);
        applicationUnmarshaller = jaxbContext.createUnmarshaller();
		applicationUnmarshaller.setSchema(applicationSchema);
		
		URL helptextSchemaURL = Reader.class.getResource("/helptext.xsd");
		Schema helptextSchema = schemaFactory.newSchema(helptextSchemaURL);
		helptextUnmarshaller = jaxbContext.createUnmarshaller();
		helptextUnmarshaller.setSchema(helptextSchema);
	}
	
	public List<Module> unmarshalApplications(String xml) throws SAXException, JAXBException, UnsupportedEncodingException {
		MetaT24 meta = MetaT24.class.cast(applicationUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
		DomainServiceResponse response = (DomainServiceResponse) meta.getDomainServiceResponse().get(0);
		return response.getModules();
	}

	public List<Module> unmarshalApplications(URL xml) throws SAXException, JAXBException {
		MetaT24 meta = MetaT24.class.cast(applicationUnmarshaller.unmarshal(xml));
		DomainServiceResponse response = (DomainServiceResponse) meta.getDomainServiceResponse().get(0);
		return response.getModules();
	}

	public Iterable<T24Help> unmarshalHelptext(Collection<File> xmls, ApplicationsImportProblemsException problems) throws ApplicationsImportProblemsException {
		Collection<T24Help> helpTexts = new LinkedList<T24Help>();
		for (File file : xmls) {
			try {
				helptextUnmarshaller.setEventHandler(new IgnoringValidationEventHandler());
				helpTexts.add(T24Help.class.cast(helptextUnmarshaller.unmarshal(file)));
			} catch (JAXBException e) {
				problems.addProblem(file.toString(), e);
			} catch (Exception  e) {
				problems.addProblem(file.toString(), e);
			} catch (Throwable e) {
				problems.addProblem(file.toString(), e);
			}
		}
		return helpTexts;
	}

}

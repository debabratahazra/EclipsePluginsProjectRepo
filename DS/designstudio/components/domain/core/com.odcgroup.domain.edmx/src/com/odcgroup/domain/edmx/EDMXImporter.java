package com.odcgroup.domain.edmx;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.consumer.behaviors.OClientBehaviors;
import org.odata4j.core.ImmutableList;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.edm.EdmSchema;
import org.odata4j.format.xml.EdmxFormatParser;
import org.odata4j.stax2.XMLEventReader2;
import org.odata4j.stax2.util.StaxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.domain.edmx.internal.EDMX2DomainMapper;
import com.odcgroup.mdf.ecore.MdfDomainImpl;

/**
 * Imports M$ EDMX (OData) into Design Studio.
 * 
 * @author Initial skeleton by Michael Vorburger, for Stéphane Mellinet
 */
public class EDMXImporter implements IEDMXContainerSelector{
	private static Logger LOGGER = LoggerFactory.getLogger(EDMXImporter.class);
	
	private IContainer container;	
	private String modelName = "";
	private EDMXImportReport importReport;
	
	/**
	 * 
	 */
	public EDMXImporter() {
		this.importReport = new EDMXImportReport();
	}
	
	public Collection<MdfDomainImpl> fromInputStreamImportEDMX(InputStream input) throws EDMXImportException {
		Collection<MdfDomainImpl> mdfDomain = null;
		try {
			EdmDataServices edmDataServices;
			edmDataServices = readFromStream(input);
			ImmutableList<EdmSchema> schemas = edmDataServices.getSchemas();
			mdfDomain = new EDMX2DomainMapper(importReport).map(schemas);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new EDMXImportException("Error Importing "+getModelType()+"s, Caused by: "+e.getMessage(), e);
		}

		return mdfDomain;

	}

	/**
	 * @param edmxURL
	 * @param user
	 * @param password
	 * @return
	 * @throws EDMXImportException
	 */
	public Collection<MdfDomainImpl> fromURLImportEDMX(URL edmxURL, String user, String password) throws EDMXImportException {
		Collection<MdfDomainImpl> mdfDomains = null;
		try {
			ODataConsumer consumer;
			if (user == null || user.isEmpty()){
			consumer = ODataConsumers.newBuilder(edmxURL.toString()).build();
			} else {
				consumer = ODataConsumers.newBuilder(edmxURL.toString()).
						setClientBehaviors(OClientBehaviors.basicAuth(user,
								password)).build();

			}
			EdmDataServices service = consumer.getMetadata();
			ImmutableList<EdmSchema> schemas = service.getSchemas();
	        mdfDomains = new EDMX2DomainMapper(importReport).map(schemas);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new EDMXImportException("Error Importing "+getModelType()+"s, Caused by: "+e.getMessage(), e);
		}

		return mdfDomains;
	}

	/**
	 * Read and parse from a stream the EDMX file.
	 * @param stream
	 * @return the service metadata of a Data Service
	 */
	public EdmDataServices readFromStream(InputStream stream) {
		InputStreamReader streamReader = null;
		try {
			streamReader = new InputStreamReader(stream);
			EdmxFormatParser parser = new EdmxFormatParser();
			XMLEventReader2 reader = StaxUtil.newXMLEventReader(streamReader);
			EdmDataServices metadata = parser.parseMetadata(reader);
			return metadata;
		} finally {
			if (streamReader != null) {
				try {
					streamReader.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					return null;
				}
			}
		}
	}	

	
	public EDMXImportReport getImportReport() {
		return importReport;
	}

	
	public final IEDMXContainerSelector getContainerSelector() {
		return this;
	}

	@Override
	public String getModelName() {
		return this.modelName;
	}

	@Override
	public final void setContainer(IContainer container) {
		this.container = container;
	}

	@Override
	public final IContainer getContainer() {
		return this.container;
	}

	@Override
	public boolean isContainerSet() {
		return container != null;
	}

	@Override
	public int level() {
		return 1;
	}

	@Override
	public int minSelectedLevel() {
		return 2;
	}

	@Override
	public String getModelType() {
		return "domain";
	}

	@Override
	public void setModelName(String modelName) {
		this.modelName = modelName;	
	}
	
}

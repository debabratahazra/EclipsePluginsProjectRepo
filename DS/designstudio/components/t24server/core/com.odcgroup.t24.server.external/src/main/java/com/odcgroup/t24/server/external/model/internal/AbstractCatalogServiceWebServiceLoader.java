package com.odcgroup.t24.server.external.model.internal;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

import javax.xml.namespace.QName;

import catalogservicews.CatalogServiceWS;
import catalogservicews.CatalogServiceWSPortType;

import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.temenos.soa.services.data.xsd.ObjectFactory;
import com.temenos.soa.services.data.xsd.T24UserDetails;

public abstract class AbstractCatalogServiceWebServiceLoader implements IExternalLoader {

	private final String URL_PATTERN = "http://{0}:{1}/axis2/services/CatalogServiceWS?wsdl";
	private final QName QNAME = new QName("http://CatalogServiceWS", "CatalogServiceWS");
	protected static final String RESPONSE_SEPARATOR = "#";

	protected Properties properties;
	
	// Component service and common
	protected CatalogServiceWSPortType catalogPort;
	protected T24UserDetails userDetails;
	
	public AbstractCatalogServiceWebServiceLoader(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void open() throws T24ServerException {
		String host = ServerPropertiesHelper.getHost(properties);
		String port = ServerPropertiesHelper.getWebServicePort(properties);
		String user = ServerPropertiesHelper.getUsername(properties);
		String password = ServerPropertiesHelper.getPassword(properties);
				
		try {
			// Instantiate the catalog web service client
			String url = MessageFormat.format(URL_PATTERN, host, port);
			
			CatalogServiceWS catalogService = new CatalogServiceWS(new URL(url), QNAME);
			catalogPort = catalogService.getCatalogServiceWSHttpSoap11Endpoint();
		
			// Create an instance of soa common and create a user details object for accessing T24
			ObjectFactory objCommon = new ObjectFactory();
		
			userDetails = objCommon.createT24UserDetails();
			userDetails.setUser(objCommon.createT24UserDetailsUser(user));
			userDetails.setPassword(objCommon.createT24UserDetailsPassword(password));
			
		} catch (Exception e) {
			throw new T24ServerException("Failed to instantiate web service, see exception for more details. Error Message: " + e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		// Nothing to do with web services
	}
	
	public void checkConnection() throws T24ServerException {
		if (catalogPort == null) {
			throw new T24ServerException("Catalog client is not instantiated, Aborting Request!");
		}
	}

}

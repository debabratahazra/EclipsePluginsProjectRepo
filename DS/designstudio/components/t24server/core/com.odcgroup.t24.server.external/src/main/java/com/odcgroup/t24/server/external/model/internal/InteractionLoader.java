package com.odcgroup.t24.server.external.model.internal;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.t24.introspector.catalog.CatalogResourceDetails;
import com.temenos.t24.introspector.catalog.CatalogServiceConnector;
import com.temenos.t24.introspector.catalog.CatalogServiceConnectorFactory;
import com.temenos.t24.introspector.catalog.T24ServerConfiguration;
import com.temenos.t24.introspector.exceptions.InvalidFieldsException;
import com.temenos.t24.introspector.exceptions.RIMGenerationException;
import com.temenos.t24.introspector.exceptions.ServiceConfigurationException;
import com.temenos.t24.introspector.exceptions.ServiceRequestException;
import com.temenos.t24.introspector.interaction.IrisModelData;
import com.temenos.t24.introspector.odata.OdataFeedData;

/** 
 * InteractionLoader for TAFC is able to load RIM resources from a T24 server.
 */
public class InteractionLoader extends ExternalLoader implements IInteractionLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(InteractionLoader.class);

	private String modelName;

	private T24ServerConfiguration t24ServerConfiguration;

	private T24UserDetails userDetails;

	private IrisModelData irisModelData;

	private OdataFeedData odataFeed;

	private CatalogServiceConnector connector;

	public InteractionLoader(Properties properties) {
		super(properties);
	}

	@Override
	public void open() throws T24ServerException {

		String host = ServerPropertiesHelper.getHost(properties);
		String port = ServerPropertiesHelper.getAgentPort(properties);
		String user = ServerPropertiesHelper.getUsername(properties);
		String pswd = ServerPropertiesHelper.getPassword(properties);
		String ofsSource = ServerPropertiesHelper.getOfsId(properties);

		irisModelData = new IrisModelData(modelName);
		odataFeed = new OdataFeedData(modelName);

		userDetails = new T24UserDetails();

		userDetails.setUser(user);
		userDetails.setPassword(pswd);

		t24ServerConfiguration = new T24ServerConfiguration();
		t24ServerConfiguration.setHosts(host);
		t24ServerConfiguration.setOfsSource(ofsSource);
		t24ServerConfiguration.setPorts(port);

		try {
			connector = CatalogServiceConnectorFactory.getCatalogServiceConnector(userDetails, t24ServerConfiguration);

		} catch (ServiceConfigurationException ex) {
			throw new T24ServerException(ex);
		}
	}
	
	@Override
	public void close() {
	}
	
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		// should never call this method
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		
		String resourceName = detail.getName();
		resourceName = resourceName.replaceAll("_", ".");

		try {
			CatalogResourceDetails resourceDetails = connector.getResourceInformation(resourceName);
			odataFeed.addResourceInformation(resourceName, resourceDetails);
			irisModelData.addResourceInformation(resourceName, resourceDetails);
		} catch (ServiceRequestException ex) {
			logger.error(ex.getMessage(), ex);
			throw new T24ServerException(ex);
		} catch (InvalidFieldsException ex) {
			logger.error(ex.getMessage(), ex);
			throw new T24ServerException(ex);
		}
		return null;

	}

	@Override
	public final void setModelName(String name) {
		this.modelName = name;
	}

	@Override
	public InputStream getRIM() throws T24ServerException {
		String value = ServerPropertiesHelper.getStrictOData(properties);
		boolean strictOdata = Boolean.valueOf(value);
		try {
			return irisModelData.getRIM(strictOdata);
		} catch (RIMGenerationException ex) {
			logger.error(ex.getMessage(), ex);
			throw new T24ServerException(ex);
		}
	}
	
	@Override
	public Map<String,String> getRIMsMap() throws T24ServerException {
		String value = ServerPropertiesHelper.getStrictOData(properties);
		boolean strictOdata = Boolean.valueOf(value);
		try {
			return irisModelData.getRIMsMap(strictOdata);
		} catch (RIMGenerationException ex) {
			logger.error(ex.getMessage(), ex);
			throw new T24ServerException(ex);
		}
	}

}

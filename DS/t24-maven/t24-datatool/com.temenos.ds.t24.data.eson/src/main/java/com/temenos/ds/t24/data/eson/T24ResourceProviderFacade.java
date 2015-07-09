package com.temenos.ds.t24.data.eson;

import java.io.IOException;

import com.temenos.ds.t24.data.rps.RemoteServiceException;
import com.temenos.ds.t24.data.rps.ResourceProviderService;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.connection.T24JAgentConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.T24WSConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionParametersFactory;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionProperties;
import com.temenos.ds.t24.data.rps.impl.ResourceProviderServiceWrapper;
import com.temenos.ds.t24.data.rps.impl.ResourceProviderWebService;
import com.temenos.ds.t24.data.rps.impl.factory.JAgentSpringJencksResourceProviderServiceProviderAPIFactory;
import com.temenos.ds.t24.data.rps.impl.factory.ResourceProviderServiceProviderAPIFactory;
import com.temenos.services.resourceprovider.ResourceProviderServiceProviderAPI;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * @author yandenmatten
 */
public class T24ResourceProviderFacade {
	
	private T24ConnectionProperties t24props;
	private ResourceProviderServiceProviderAPIFactory dsServiceProviderAPIFactory;
	private ResourceProviderService dsService;
	private T24InteractionContext currentInteractionCtx;
	
	/* Configuration */
	private boolean autoOverride = true;
	
	public void connectTAFC(String connectionPropertiesFile) throws RemoteServiceException {
		t24props = T24ConnectionProperties.fromClasspath(connectionPropertiesFile);
		T24JAgentConnectionParameters p = (T24JAgentConnectionParameters) T24ConnectionParametersFactory.from(t24props);
		dsServiceProviderAPIFactory = new JAgentSpringJencksResourceProviderServiceProviderAPIFactory(p);
		ResourceProviderServiceProviderAPI cfService = dsServiceProviderAPIFactory.getComponentFrameworkResourceProviderService();
		dsService = new ResourceProviderServiceWrapper(cfService);
	}
	
	public void connectTAFJ(String connectionPropertiesFile) throws RemoteServiceException {
		t24props = T24ConnectionProperties.fromClasspath("/tafj-integrationtest-server.properties");
		T24WSConnectionParameters p = (T24WSConnectionParameters) T24ConnectionParametersFactory.from(t24props);
		dsService = new ResourceProviderWebService(p);
	}
	
	public GetResourceResponse readRecord(String table, String id) throws RemoteServiceException {
		currentInteractionCtx = new T24InteractionContext();
		return dsService.getResource(getT24UserDetails(), currentInteractionCtx, table, id, "");
	}

	private T24UserDetails getT24UserDetails() {
		return new T24UserDetails(t24props.t24username, t24props.t24password, t24props.branch);
	}
	
	// TODO include the id in the ESON
	public void writeRecord(GetResourceResponse resourceResponse, String id) throws RemoteServiceException {
		if (currentInteractionCtx == null) {
			currentInteractionCtx = new T24InteractionContext();
		}
		dsService.inputResource(getT24UserDetails(), currentInteractionCtx, resourceResponse.resourceName, id, "", resourceResponse, autoOverride);
	}

	public void authorizeRecord(String table, String id) throws RemoteServiceException {
		if (currentInteractionCtx == null) {
			currentInteractionCtx = new T24InteractionContext();
		}
		dsService.authorizeResource(getT24UserDetails(), currentInteractionCtx, table, id);
	}
	
	public void authorizeRecordSafely(String table, String id) throws RemoteServiceException {
		if (currentInteractionCtx == null) {
			currentInteractionCtx = new T24InteractionContext();
		}
		// Read the record before authorizing it
		dsService.getResource(getT24UserDetails(), currentInteractionCtx, table, id, "");
		dsService.authorizeResource(getT24UserDetails(), currentInteractionCtx, table, id);
	}
	
	public void resetContext() {
		currentInteractionCtx = null;
	}
	
	public void close() throws IOException {
		if (dsServiceProviderAPIFactory != null) {
			dsServiceProviderAPIFactory.close();
		}
	}

	// Configuration
	public boolean isAutoOverride() {
		return autoOverride;
	}
	public void setAutoOverride(boolean autoOverride) {
		this.autoOverride = autoOverride;
	}

}

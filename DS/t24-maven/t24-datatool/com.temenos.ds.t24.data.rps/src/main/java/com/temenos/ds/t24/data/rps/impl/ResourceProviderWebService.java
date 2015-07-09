package com.temenos.ds.t24.data.rps.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.BindingProvider;

import resourceproviderservicews.ResourceProviderServiceWS;
import resourceproviderservicews.ResourceProviderServiceWSPortType;

import com.temenos.ds.t24.data.rps.RemoteServiceException;
import com.temenos.ds.t24.data.rps.connection.T24WSConnectionParameters;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.services.resourceprovider.data.xsd.Identifier;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * DS ResourceProviderService API impl based on CF WS
 * (because CF WS gen. impl. do not implement same API as non-WS CF ResourceProviderServiceProviderAPI).
 * Incl. nicer types (e.g. hides internal JAXB types, and properly throws Exception).
 *
 * @author Michael Vorburger
 */
public class ResourceProviderWebService extends AbstractJAXBResourceProviderService {
	// NOTE: If you make changes to this class, consider if other ResourceProviderService implementations need likewise adaption

	private final ResourceProviderServiceWSPortType delegate;

	public ResourceProviderWebService(ResourceProviderServiceWSPortType delegate) {
		super();
		this.delegate = delegate;
	}

	public ResourceProviderWebService(T24WSConnectionParameters wsParams) throws RemoteServiceException {
		// TODO This with the WSDL isn't actually needed, when using ENDPOINT_ADDRESS_PROPERTY, it seems - try (once test is green..)
		ResourceProviderServiceWS ws;
		try {
			ws = new ResourceProviderServiceWS(new URL(wsParams.wsURL+"?wsdl"));
					//new URL("http://localhost:9089/axis2/services/ResourceProviderServiceWS?wsdl"),
					//new QName("http://ResourceProviderServiceWS", "ResourceProviderServiceWS"));
		} catch (MalformedURLException e) {
			throw new RemoteServiceException("Unable to created ResourceProviderServiceWS", e);
		}
		ResourceProviderServiceWSPortType servicePort = ws.getResourceProviderServiceWSHttpEndpoint();
		BindingProvider bp = (BindingProvider)servicePort;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsParams.wsURL);
        this.delegate = servicePort;
	}

	@Override
	protected com.temenos.services.resourceprovider.data.response.xsd.GetResourceResponse _getResource(
			com.temenos.soa.services.data.xsd.T24UserDetails wsUserDetails,
			com.temenos.services.resourceprovider.data.xsd.T24InteractionContext wsInteractionContext,
			Identifier wsResourceName, Identifier wsID, Identifier wsResourceState) throws RemoteServiceException {

		try {
			return delegate.getResource(wsUserDetails, wsInteractionContext, wsResourceName, wsID, wsResourceState);
		} catch (Exception e) {
			throw new RemoteServiceException("Failed to invoke remote ResourceProviderService", e);
		}
	}

	@Override
	public void inputResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext,
			String resourceName, String id, String resourceState, GetResourceResponse resourceResponse,
			boolean autoOverride)
			throws RemoteServiceException {
		throw new RuntimeException("Not implmented yet.");
	}

	@Override
	public void authorizeResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext, String resourceName, String id) throws RemoteServiceException {
		throw new RuntimeException("Not implmented yet.");
	}

}

package com.temenos.ds.t24.data.rps.impl;

import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.temenos.ds.t24.data.rps.RemoteServiceException;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionProperties;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.services.resourceprovider.data.xsd.Identifier;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * DS ResourceProviderService API impl for tests.
 * It just returns a response read from an XML file.
 *
 * @author Michael Vorburger
 */
public class MockResourceProviderService extends AbstractJAXBResourceProviderService {

	protected JAXBContext jaxbContext; // Keep this as field init. in constructor, to be more performant
	private resourceproviderservicews.GetResourceResponse jaxbGetResourceResponse;

	public MockResourceProviderService() throws JAXBException {
		super();
		// CAREFUL here, there are *N different classes named GetResourceResponse, it's important to import the correct one..
		this.jaxbContext = JAXBContext.newInstance(resourceproviderservicews.GetResourceResponse.class);
	}

	public void mockGetResourceResponse(String classpathResource) throws IOException, JAXBException {
		URL url = getURL(classpathResource);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		//  getResourceResponse.getReturn().getValue().getResourceName().getValue().getValue().getValue()
		jaxbGetResourceResponse = (resourceproviderservicews.GetResourceResponse) unmarshaller.unmarshal(url);
	}

	@Override
	protected com.temenos.services.resourceprovider.data.response.xsd.GetResourceResponse _getResource(
			com.temenos.soa.services.data.xsd.T24UserDetails jaxbUserDetails,
			com.temenos.services.resourceprovider.data.xsd.T24InteractionContext jaxbInteractionContext,
			Identifier jaxbResourceName, Identifier jaxbID, Identifier jaxbResourceState) throws RemoteServiceException {

		return jaxbGetResourceResponse.getReturn().getValue();
	}

	protected URL getURL(String classpathResource) throws IOException {
		URL url = T24ConnectionProperties.class.getResource(classpathResource);
		if (url == null)
			throw new IOException("Classpath resource not found: " + classpathResource);
		return url;
	}

	@Override
	public void inputResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext,
			String resourceName, String id, String resourceState, GetResourceResponse resourceResponse,
			boolean autoOverride)
			throws RemoteServiceException {
		throw new IllegalStateException("inputResource not supported for this mock object");
	}

	@Override
	public void authorizeResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext, String resourceName, String id) throws RemoteServiceException {
		throw new IllegalStateException("authorizeResource not supported for this mock object");
	}

}

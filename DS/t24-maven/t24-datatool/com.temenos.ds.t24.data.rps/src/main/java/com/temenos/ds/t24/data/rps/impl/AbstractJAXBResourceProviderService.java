package com.temenos.ds.t24.data.rps.impl;

import com.temenos.ds.t24.data.rps.RemoteServiceException;
import com.temenos.ds.t24.data.rps.ResourceProviderService;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.services.resourceprovider.data.xsd.Identifier;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.soa.services.data.xsd.Response;

/**
 * DS ResourceProviderService API impl based on JAXB.
 *
 * @author Michael Vorburger
 */
public abstract class AbstractJAXBResourceProviderService implements ResourceProviderService {
	// NOTE: If you make changes to this class, consider if other ResourceProviderService implementations need likewise adaption

	// JAXB ObjectFactory (like JAXBContext) *are* thread safe, so this is OK
	// @see https://java.net/projects/jaxb/lists/users/archive/2004-06/message/21
	protected static final com.temenos.soa.services.data.xsd.ObjectFactory soaFactory = new com.temenos.soa.services.data.xsd.ObjectFactory();
	protected static final com.temenos.services.resourceprovider.data.xsd.ObjectFactory rpFactory = new com.temenos.services.resourceprovider.data.xsd.ObjectFactory();

	protected abstract com.temenos.services.resourceprovider.data.response.xsd.GetResourceResponse _getResource(
			com.temenos.soa.services.data.xsd.T24UserDetails jaxbUserDetails,
			com.temenos.services.resourceprovider.data.xsd.T24InteractionContext jaxbInteractionContext,
			Identifier jaxbResourceName, Identifier jaxbID, Identifier jaxbResourceState) throws RemoteServiceException;

	@Override
	public final GetResourceResponse getResource(T24UserDetails userDetails, T24InteractionContext interactionCtx, String resourceName, String id, String resourceState) throws RemoteServiceException {
		com.temenos.soa.services.data.xsd.T24UserDetails jaxbUserDetails = soaFactory.createT24UserDetails();
		jaxbUserDetails.setUser(soaFactory.createT24UserDetailsUser(userDetails.getUser()));
		jaxbUserDetails.setPassword(soaFactory.createT24UserDetailsUser(userDetails.getPassword()));
		jaxbUserDetails.setCoCode(soaFactory.createT24UserDetailsUser(userDetails.getCoCode()));

		com.temenos.services.resourceprovider.data.xsd.T24InteractionContext jaxbInteractionContext = new com.temenos.services.resourceprovider.data.xsd.T24InteractionContext();
		// TODO wsInteractionContext.set ...

		Identifier jaxbResourceName = identifier(resourceName);
		Identifier jaxbID = identifier(id);
		Identifier jaxbResourceState = identifier(resourceState);
		jaxbResourceState.setValue(rpFactory.createIdentifierValue(resourceState));

		com.temenos.services.resourceprovider.data.response.xsd.GetResourceResponse jaxbGetResourceResponse;
		jaxbGetResourceResponse = _getResource(jaxbUserDetails, jaxbInteractionContext, jaxbResourceName, jaxbID, jaxbResourceState);

		com.temenos.soa.services.data.xsd.ResponseDetails jaxbResponseDetails = jaxbGetResourceResponse.getResponseDetails().getValue();
		String returnCode = jaxbResponseDetails.getReturnCode().getValue();
		if (!"SUCCESS".equals(returnCode)) {
			Response firstResponse = jaxbResponseDetails.getResponses().get(0);
			String errorMessage = firstResponse.getResponseText().getValue();
			// TODO add jaxbResponseDetails to text (not just toString) as detail to Exception, and log it..
			throw new RemoteServiceException(jaxbResponseDetails);
		}

		// map jaxbGetResourceResponse to getResourceResponse
		GetResourceResponse getResourceResponse = new ResourceProviderService.GetResourceResponse(jaxbGetResourceResponse.getResourceName().getValue().getValue().getValue());
		for (com.temenos.services.resourceprovider.data.xsd.Field jaxbField : jaxbGetResourceResponse.getFields()) {
			GetResourceResponse.Field dsField = new GetResourceResponse.Field(
					jaxbField.getName().getValue(),
					jaxbField.getValue().getValue(),
					jaxbField.getMv().getValue(),
					"TODO: mvGroupName not implemented yet", // TODO: implement mvGroupName extraction
					jaxbField.getSv().getValue(),
					"TODO: svGroupName not implemented yet"); // TODO: implement svGroupName extraction
			getResourceResponse.fields.add(dsField);
		}
		// TODO Complete mapping: jaxbGetResourceResponse.getResourceFields()
		return getResourceResponse;
	}

	protected Identifier identifier(String value) {
		Identifier identifier = rpFactory.createIdentifier();
		identifier.setValue(rpFactory.createIdentifierValue(value));
		return identifier;
	}
}

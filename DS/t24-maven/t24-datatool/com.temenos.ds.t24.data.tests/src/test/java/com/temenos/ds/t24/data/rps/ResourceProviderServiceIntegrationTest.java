package com.temenos.ds.t24.data.rps;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import resourceproviderservicews.ResourceProviderServiceWS;
import resourceproviderservicews.ResourceProviderServiceWSPortType;

import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JConnection;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse.Field;
import com.temenos.ds.t24.data.rps.connection.T24JAgentConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.T24WSConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionParametersFactory;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionProperties;
import com.temenos.ds.t24.data.rps.impl.ResourceProviderServiceWrapper;
import com.temenos.ds.t24.data.rps.impl.ResourceProviderWebService;
import com.temenos.ds.t24.data.rps.impl.factory.JAgentSpringJencksResourceProviderServiceProviderAPIFactory;
import com.temenos.ds.t24.data.rps.impl.factory.ResourceProviderServiceProviderAPIFactory;
import com.temenos.services.resourceprovider.ResourceProviderServiceImpl;
import com.temenos.services.resourceprovider.ResourceProviderServiceProviderAPI;
import com.temenos.services.resourceprovider.data.Identifier;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.services.resourceprovider.tafc.ResourceProviderServiceProviderImplTAFC;
import com.temenos.services.resourceprovider.tafj.ResourceProviderServiceProviderImplTAFJ;
import com.temenos.soa.services.RuntimeProperties;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.soa.services.data.UserDetails;
import com.temenos.soa.services.data.xsd.ObjectFactory;
import com.temenos.soa.services.data.xsd.Response;
import com.temenos.soa.services.tafj.TAFJDefaultServiceHandlerImpl;
import com.temenos.soa.services.tafj.TAFJServiceCallBack;
import com.temenos.soa.services.tafj.TAFJServiceHandler;
import com.temenos.soa.services.tafj.TAFJServiceInitialisationHandler;

public class ResourceProviderServiceIntegrationTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ResourceProviderServiceIntegrationTest.class);

	@Test
	public void testTAFC_getResource_SpringJenksEtc() throws Exception {
		T24ConnectionProperties t24props = T24ConnectionProperties.fromClasspath("/tafc-integrationtest-server.properties");
		T24JAgentConnectionParameters p = (T24JAgentConnectionParameters) T24ConnectionParametersFactory.from(t24props);
		ResourceProviderServiceProviderAPIFactory f = new JAgentSpringJencksResourceProviderServiceProviderAPIFactory(p);
		ResourceProviderServiceProviderAPI cfService = f.getComponentFrameworkResourceProviderService();
		ResourceProviderService dsService = new ResourceProviderServiceWrapper(cfService);

		checkGetResource(t24props, dsService);

		f.close();
	}

	@Test
	@Ignore // TODO http://rd.oams.com/browse/DS-8384 
	// TODO DS-8830 RemoteServiceException: Failed to load user sms : null
	public void testTAFjWS_getResource() throws Exception {
		T24ConnectionProperties t24props = T24ConnectionProperties.fromClasspath("/tafj-integrationtest-server.properties");
		T24WSConnectionParameters p = (T24WSConnectionParameters) T24ConnectionParametersFactory.from(t24props);
		ResourceProviderService dsService = new ResourceProviderWebService(p);
		checkGetResource(t24props, dsService);
	}

	private void checkGetResource(T24ConnectionProperties t24props, ResourceProviderService dsService) throws RemoteServiceException {
		T24UserDetails asUser = new T24UserDetails(t24props.t24username, t24props.t24password, t24props.branch);
		T24InteractionContext interactionCtx = new T24InteractionContext();
		GetResourceResponse resource = dsService.getResource(asUser, interactionCtx, IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID, "");

		Field firstField = resource.fields.get(0);
		Assert.assertEquals("MNEMONIC", firstField.name);
		assertEquals("BKAMEXGVA", firstField.value);
	}

	@Test
	@Ignore // TODO http://rd.oams.com/browse/DS-8387
	public void testTAFC_getResource_DefaultJConnectionFactory() throws Exception {
		DefaultJConnectionFactory jConnectionFactory = new DefaultJConnectionFactory();
		jConnectionFactory.setHost("vmcmtdst24.oams.com");
		jConnectionFactory.setPort(21001);
		jConnectionFactory.setActionTimeout(30);
		jConnectionFactory.enableAllowInput();
		// TODO jConnectionFactory.enableCompression()
		// TODO jConnectionFactory.setCharSet("UTF-8");

		Properties properties = new Properties();
		properties.put("env.OFS_SOURCE", "IRISPA");
// How to set connection properties? This doesn't work either :(
//		jConnectionFactory.getConnectionProperties().setProperty("env.OFS_SOURCE", "IRISPA");
		// TODO HOW TO specify company code, "GB0010001" ?
		
		JConnection jConnection = jConnectionFactory.getConnection(IntegrationTestData.T24_USER, IntegrationTestData.T24_PASSWORD, properties /*, IntegrationTestData.T24_BRANCH */);
		jConnection.call("JF.INITIALISE.CONNECTION", null);
/*
		TAFCServiceHandler serviceHandler = new TAFCDefaultServiceHandlerImpl();
		serviceHandler.initialise(jConnection);
		ResourceProviderServiceProviderAPI service = new ResourceProviderServiceProviderImplTAFC(serviceHandler);
		testTAFC_getResource(service);
		serviceHandler.finalise(jConnection);
*/
		// TODO How to pass in the OFS_SOURCE "IRISPA" so that this won't fail with "EB-FAIL.TO.LOAD.USER.SMS" ??
		ResourceProviderServiceProviderAPI service = new ResourceProviderServiceProviderImplTAFC(jConnectionFactory);
		testTAFC_getResource(service);
	}

	private void testTAFC_getResource(ResourceProviderServiceProviderAPI service) throws Exception {
		// set the user credentials
		UserDetails userDetails = new T24UserDetails(IntegrationTestData.T24_USER, IntegrationTestData.T24_PASSWORD, IntegrationTestData.T24_BRANCH);
		T24UserContextCallBackImpl userContextCallBack = new T24UserContextCallBackImpl(userDetails);
		service.setUserContextCallBack(userContextCallBack);

		// call getResource()
		T24InteractionContext inT24InteractionContext = new T24InteractionContext();
		Identifier inResourceName = new Identifier(IntegrationTestData.CUSTOMER_TABLE);
		Identifier inID = new Identifier(IntegrationTestData.CUSTOMER_ID);
		Identifier outFields = new Identifier();
		List<com.temenos.services.resourceprovider.data.Field> outResourceFields = new ArrayList<com.temenos.services.resourceprovider.data.Field>();
		List<com.temenos.services.resourceprovider.data.FieldDefinitions> outFieldDefinitions = new ArrayList<com.temenos.services.resourceprovider.data.FieldDefinitions>();
		ResponseDetails outResponseDetails = new ResponseDetails();
		service.getResource(inT24InteractionContext, inResourceName, inID, outFields, outResourceFields, outFieldDefinitions, outResponseDetails);
		String returnCode = outResponseDetails.getReturnCode();
		if (!"SUCCESS".equals(returnCode)) {
			String errorMessage = outResponseDetails.getResponses()[0].getResponseText();
			throw new RemoteServiceException(errorMessage);
		}
		LOGGER.debug(outResourceFields.toString());
	}

	@Test
	@Ignore // TODO http://rd.oams.com/browse/DS-8384
	public void testTAFjWS_getResource_manual() throws Exception {
		// TODO move this code into the ResourceProviderServiceWSDelegate (or its Factory)

		// TODO This with the WSDL isn't actually needed, when using ENDPOINT_ADDRESS_PROPERTY, it seems - try (once test is green..)
		ResourceProviderServiceWS ws = new ResourceProviderServiceWS(
				new URL("http://localhost:9089/axis2/services/ResourceProviderServiceWS?wsdl"),
				new QName("http://ResourceProviderServiceWS", "ResourceProviderServiceWS"));
		ResourceProviderServiceWSPortType servicePort = ws.getResourceProviderServiceWSHttpEndpoint();

		BindingProvider bp = (BindingProvider)servicePort;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:9089/axis2/services/ResourceProviderServiceWS");

		ObjectFactory soaFactory = new com.temenos.soa.services.data.xsd.ObjectFactory();
		com.temenos.services.resourceprovider.data.xsd.ObjectFactory rpFactory = new com.temenos.services.resourceprovider.data.xsd.ObjectFactory();

		com.temenos.soa.services.data.xsd.T24UserDetails userDetails = soaFactory.createT24UserDetails();
		userDetails.setUser(soaFactory.createT24UserDetailsUser("INPUTT"));
		userDetails.setPassword(soaFactory.createT24UserDetailsPassword(IntegrationTestData.T24_PASSWORD));
		// userDetails.setCoCode(soaFactory.createT24UserDetailsCoCode("GB0010001"));

		com.temenos.services.resourceprovider.data.xsd.T24InteractionContext t24InteractionContext = new com.temenos.services.resourceprovider.data.xsd.T24InteractionContext();
		com.temenos.services.resourceprovider.data.xsd.Identifier resourceName = rpFactory.createIdentifier();
		resourceName.setValue(rpFactory.createIdentifierValue(IntegrationTestData.CUSTOMER_TABLE));
		com.temenos.services.resourceprovider.data.xsd.Identifier id = rpFactory.createIdentifier();
		id.setValue(rpFactory.createIdentifierValue(IntegrationTestData.CUSTOMER_ID));
		com.temenos.services.resourceprovider.data.xsd.Identifier resourceState = rpFactory.createIdentifier();

		com.temenos.services.resourceprovider.data.response.xsd.GetResourceResponse response = servicePort.getResource(userDetails, t24InteractionContext, resourceName, id, resourceState);

		com.temenos.soa.services.data.xsd.ResponseDetails responseDetails = response.getResponseDetails().getValue();
		String returnCode = responseDetails.getReturnCode().getValue();
		if (!"SUCCESS".equals(returnCode)) {
			Response firstResponse = responseDetails.getResponses().get(0);
			String errorMessage = firstResponse.getResponseText().getValue();
			throw new RemoteServiceException(errorMessage);
		}
		assertEquals("SUCCESS", returnCode);
		com.temenos.services.resourceprovider.data.xsd.FieldDefinitions firstField = response.getResourceFields().get(0);
		assertEquals("MNEMONIC", firstField.getName().getValue());
		assertEquals("BKAMEXGVA", firstField.getFieldValue().get(0));
	}

	// TODO @Test
	public void testTAFjInProcOneWay() {
		RuntimeProperties properties = new RuntimeProperties("OFS_SOURCE", "GCS");
		// Here we are using the default Service Handler
		ResourceProviderServiceImpl service = new ResourceProviderServiceImpl(properties);

		// Below line will set the T24 User Context within Service object and
		// this will be used before every jBC call to load/switch
		// the user context using JF.VALIDATE.SIGN.ON
		service.setUserContextCallBack(new T24UserContextCallBackImpl(new T24UserDetails("INPUTT", "123456", "")));

		T24InteractionContext inT24InteractionContext = new T24InteractionContext();
		Identifier inResourceName = new Identifier("CUSTOMER");
		Identifier inID = new Identifier("GB0010001");
		Identifier outFields = new Identifier();
		List<com.temenos.services.resourceprovider.data.Field> outResourceFields = new ArrayList<com.temenos.services.resourceprovider.data.Field>();
		List<com.temenos.services.resourceprovider.data.FieldDefinitions> outFieldDefinitions = new ArrayList<com.temenos.services.resourceprovider.data.FieldDefinitions>();
		ResponseDetails outResponseDetails = new ResponseDetails();
		service.getResource(inT24InteractionContext, inResourceName, inID, outFields, outResourceFields, outFieldDefinitions, outResponseDetails);
	}

	// TODO @Test
	public void testTAFjInProcAnotherWay() {
		TAFJServiceCallBack tafjServiceCallBack = new TAFJServiceInitialisationHandler();
		// TAFJRuntime tafjRuntime; // TODO needs TAFJClient*.jar - do we want this in DS??

		RuntimeProperties runtimeProperties = new RuntimeProperties("OFS_SOURCE", "IRISPA");
		runtimeProperties.setAppServerMode(false);
		runtimeProperties.setSuperTransaction(false);

		// tafjServiceCallBack.initialise(tafjRuntime, runtimeProperties);
		TAFJServiceHandler tafjServiceHandler = new TAFJDefaultServiceHandlerImpl(tafjServiceCallBack);
		ResourceProviderServiceProviderImplTAFJ serviceTAFJ = new ResourceProviderServiceProviderImplTAFJ(tafjServiceHandler);

		T24InteractionContext inT24InteractionContext = new T24InteractionContext();
		Identifier inResourceName = new Identifier("CUSTOMER");
		Identifier inID = new Identifier("GB0010001");
		Identifier outFields = new Identifier();
		List<com.temenos.services.resourceprovider.data.Field> outResourceFields = new ArrayList<com.temenos.services.resourceprovider.data.Field>();
		List<com.temenos.services.resourceprovider.data.FieldDefinitions> outFieldDefinitions = new ArrayList<com.temenos.services.resourceprovider.data.FieldDefinitions>();
		ResponseDetails outResponseDetails = new ResponseDetails();
		serviceTAFJ.getResource(inT24InteractionContext, inResourceName, inID, outFields, outResourceFields, outFieldDefinitions, outResponseDetails);
	}

}

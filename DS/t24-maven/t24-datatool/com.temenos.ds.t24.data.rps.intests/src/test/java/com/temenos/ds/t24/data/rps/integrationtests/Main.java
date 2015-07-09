package com.temenos.ds.t24.data.rps.integrationtests;

import java.util.ArrayList;
import java.util.List;

import com.temenos.ds.t24.h2.DBSingleton;
import com.temenos.services.resourceprovider.ResourceProviderServiceImpl;
import com.temenos.services.resourceprovider.data.Field;
import com.temenos.services.resourceprovider.data.Identifier;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.soa.services.RuntimeProperties;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.soa.services.data.UserDetails;
import com.temenos.soa.services.tafj.TAFJDefaultServiceHandlerImpl;
import com.temenos.soa.services.tafj.TAFJServiceHandler;
import com.temenos.tafj.api.client.TAFJRuntime;
import com.temenos.tafj.api.client.impl.TAFJRuntimeFactory;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Don't hard-code this.. DB must be available as a Maven dependency
		DBSingleton.startIfNotAlreadyRunning("/home/mvorburger/dev/DS/BrowserReplacement/base_mb_t24brpdev_34_tafj/h2/");

		// TODO retry this, without the -D in the launch config
		//System.setProperty("tafj.home", "/home/mvorburger/dev/DS/BrowserReplacement/base_mb_t24brpdev_34_tafj/TAFJ/");
		TAFJRuntime runtime = TAFJRuntimeFactory.getTAFJRuntime();
		runtime.init();

//		jVar jv1 = jVarFactory.get("Thierry");
//		jVar jv2 = jVarFactory.get();
//		runtime.callJBC("HELLO", jv1, jv2);
//		System.out.println(jv2.toExternalString());

		RuntimeProperties runtimeProperties = new RuntimeProperties("OFS_SOURCE", "IRISPA");
		runtimeProperties.setAppServerMode(false);
		runtimeProperties.setSuperTransaction(false);
		ResourceProviderServiceImpl r = new ResourceProviderServiceImpl(runtimeProperties);

		TAFJServiceHandler serviceHandler = new TAFJDefaultServiceHandlerImpl();
		serviceHandler.operationInitialise(runtime, runtimeProperties);
		r.setServiceHandler(serviceHandler);

		T24InteractionContext inT24InteractionContext = new T24InteractionContext();
		Identifier inResourceName = new Identifier("CUSTOMER");
		Identifier inID = new Identifier("100432");
		Identifier outFields = new Identifier();
		List<com.temenos.services.resourceprovider.data.Field> outResourceFields = new ArrayList<com.temenos.services.resourceprovider.data.Field>();
		List<com.temenos.services.resourceprovider.data.FieldDefinitions> outFieldDefinitions = new ArrayList<com.temenos.services.resourceprovider.data.FieldDefinitions>();
		ResponseDetails outResponseDetails = new ResponseDetails();

		UserDetails userDetails = new T24UserDetails("SSOUSER1", "654321", "GB0010001");
		T24UserContextCallBackImpl userContextCallBack = new T24UserContextCallBackImpl(userDetails);
		r.setUserContextCallBack(userContextCallBack);

		System.out.println("go");
		r.getResource(inT24InteractionContext, inResourceName, inID, outFields, outResourceFields, outFieldDefinitions, outResponseDetails);
		System.out.println("done");

		System.out.println("retCode: " + outResponseDetails.getReturnCode());
		Field firstField = outResourceFields.get(0);
		System.out.println("name: " + firstField.getName());  // MNEMONIC
		System.out.println("value: " + firstField.getValue()); // "BKAMEXGVA"

/*
		TAFJServiceCallBack tafjServiceCallBack = new TAFJServiceInitialisationHandler();
		// TAFJRuntime tafjRuntime; // TODO needs TAFJClient*.jar - do we want
		// this in DS??

		RuntimeProperties runtimeProperties = new RuntimeProperties("OFS_SOURCE", "IRISPA");
		runtimeProperties.setAppServerMode(false);
		runtimeProperties.setSuperTransaction(false);

		// tafjServiceCallBack.initialise(tafjRuntime, runtimeProperties);
		TAFJServiceHandler tafjServiceHandler = new TAFJDefaultServiceHandlerImpl(tafjServiceCallBack);
		ResourceProviderServiceProviderImplTAFJ serviceTAFJ = new ResourceProviderServiceProviderImplTAFJ(
				tafjServiceHandler);

		T24InteractionContext inT24InteractionContext = new T24InteractionContext();
		Identifier inResourceName = new Identifier("CUSTOMER");
		Identifier inID = new Identifier("GB0010001");
		Identifier outFields = new Identifier();
		List<com.temenos.services.resourceprovider.data.Field> outResourceFields = new ArrayList<com.temenos.services.resourceprovider.data.Field>();
		List<com.temenos.services.resourceprovider.data.FieldDefinitions> outFieldDefinitions = new ArrayList<com.temenos.services.resourceprovider.data.FieldDefinitions>();
		ResponseDetails outResponseDetails = new ResponseDetails();
		serviceTAFJ.getResource(inT24InteractionContext, inResourceName, inID, outFields, outResourceFields, outFieldDefinitions, outResponseDetails);
*/
	}

}

package com.odcgroup.integrationfwk.ui.services.connectivity;

import integrationflowservicews.IntegrationFlowServiceWS;
import integrationflowservicews.IntegrationFlowServiceWSPortType;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.decorators.FilePropertyPage;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.FileUtil;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.odcgroup.integrationfwk.ui.utils.Utils;
import com.temenos.services.integrationflow.data.response.xsd.CreateFlowResponse;
import com.temenos.services.integrationflow.data.response.xsd.UpdateDataLibraryResponse;
import com.temenos.services.integrationflow.data.xsd.ComponentServiceData;
import com.temenos.services.integrationflow.data.xsd.ContractData;
import com.temenos.services.integrationflow.data.xsd.ExitPoint;
import com.temenos.services.integrationflow.data.xsd.IntegrationFlowBase;
import com.temenos.services.integrationflow.data.xsd.SchemaDocument;
import com.temenos.soa.services.data.xsd.ObjectFactory;
import com.temenos.soa.services.data.xsd.ResponseDetails;
import com.temenos.soa.services.data.xsd.T24UserDetails;

public class FlowServices {

    private IntegrationFlowServiceWSPortType servicePort;
    private T24UserDetails userDetails;
    /**
     * holds the name of the flow which has been successfully published into T24
     */
    private final List<String> publishedFlows;

    private TafjCreateFlowServiceDataBuilder createFlowServiceDataBuilder;

    public FlowServices(String url, String password, String userName) {
	initService(url);
	initUserDetails(userName, password);
	publishedFlows = new ArrayList<String>();
    }

    public FlowServices(String url, String password, String userName,
	    TafjCreateFlowServiceDataBuilder createFlowServiceDataBuilder) {
	initService(url);
	initUserDetails(userName, password);
	publishedFlows = new ArrayList<String>();
	this.createFlowServiceDataBuilder = createFlowServiceDataBuilder;
    }

    /**
     * Helps to create the data library into T24.
     */
    public void createDataLibrary() {
	if (servicePort == null || userDetails == null) {
	    return;
	}
	UpdateDataLibraryResponse response = servicePort
		.updateDataLibrary(userDetails);
	ResponseDetails responseDetails = response.getResponseDetails()
		.getValue();
	if (responseDetails.getReturnCode().getValue()
		.equalsIgnoreCase("SUCCESS")) {
	    LogConsole.getSoaConsole().logMessage(
		    "Data library created : Successfully");
	} else {
	    String failureReason = responseDetails.getResponses().get(0)
		    .getResponseText().getValue();
	    LogConsole.getSoaConsole().logMessage(
		    "Creating Data library : Failed" + "\n"
			    + "Failure Reason : " + failureReason);
	}

    }

    public void createFlow(EventFlow eventFlow, StringBuffer publishLog,
	    FilePropertyPage filePropertyPage, String projectName,
	    TWSConsumerProject project) throws TWSConsumerPluginException {
	if (createFlowServiceDataBuilder == null) {
	    createFlowServiceDataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		    eventFlow);
	}
	ExitPoint exitPoint = createFlowServiceDataBuilder
		.createExitPointData();
	IntegrationFlowBase flowBase = createFlowServiceDataBuilder
		.createIntegrationFlowBaseData();
	List<ContractData> contractDataList = createFlowServiceDataBuilder
		.createContractData();
	List<ComponentServiceData> componentServiceDataList = createFlowServiceDataBuilder
		.createComponetServiceData();
	CreateFlowResponse response = servicePort
		.createFlow(userDetails, exitPoint, flowBase, contractDataList,
			componentServiceDataList);
	getStatus(response, publishLog, filePropertyPage, eventFlow, project);
    }

    public void deleteAllFlows(String projectName) {
	servicePort.deleteAllFlows(userDetails, projectName);
    }

    private void getStatus(CreateFlowResponse response,
	    StringBuffer publishLog, FilePropertyPage filePropertyPage,
	    EventFlow t24Event, TWSConsumerProject project) {
	ResponseDetails details = response.getResponseDetails().getValue();
	if (details.getReturnCode().getValue().equalsIgnoreCase("FAILURE")) {
	    LogConsole.getSoaConsole().logMessage("Status : FAILURE");
	    LogConsole.getSoaConsole().logMessage(
		    "Reason : "
			    + details.getResponses().get(0).getResponseText()
				    .getValue());
	    publishLog.append("Status : FAILURE \n");
	    publishLog.append("Reason : "
		    + details.getResponses().get(0).getResponseText()
			    .getValue() + "\n");
	    filePropertyPage.performPublish(true, t24Event.getEvent()
		    .getEventName());
	}

	else {

	    LogConsole.getSoaConsole().logMessage(
		    "Status : Successfully Published");
	    publishLog.append("Status : Successfully Published \n");
	    filePropertyPage.performPublish(false, t24Event.getEvent()
		    .getEventName());
	    String flowName = t24Event.getFlow().getFlowName();
	    String schemaName = response.getIntegrationFlowSchema().getValue()
		    .getSchemaName().getValue();
	    String schemaContent = response.getIntegrationFlowSchema()
		    .getValue().getSchema().getValue();
	    if (schemaContent == null || schemaContent.length() == 0) {
		if (!publishedFlows.contains(Utils.getFlowName(flowName))) {
		    // exceptional scenario
		    LogConsole.getSoaConsole().logMessage(
			    "Schema returned from T24 for the flow " + flowName
				    + "is empty!");
		}
		// most possibly the incoming flow has been published into T24
		// already. The T24 itself doesn't return the schema content if
		// we are trying to publish a same flow continuously!!!
		return;
	    }
	    // write the schema to schemas folder

	    String folderPath = project.getPathString() + File.separator
		    + StringConstants.SCHEMA_FOLDER_NAME + File.separator
		    + Utils.getFlowName(flowName);
	    FileUtil.createFolder(folderPath);
	    FileUtil.doFileWrite(schemaContent, schemaName, folderPath);
	    Map<String, String> schema = new HashMap<String, String>();
	    List<String> importedschemaNames = response
		    .getIntegrationFlowSchema().getValue()
		    .getImportedSchemaNames();
	    List<String> importedschemaDocs = response
		    .getIntegrationFlowSchema().getValue()
		    .getImportedSchemaDocuments();
	    for (int namesCount = 0; namesCount < importedschemaNames.size(); namesCount++) {
		schema.put(importedschemaNames.get(namesCount),
			importedschemaDocs.get(namesCount));

	    }

	    // Publish Additional Schemas
	    // Batch
	    SchemaDocument batchSchema = response.getIntegrationFlowSchema()
		    .getValue().getBatchSchema().getValue();
	    schema.put(batchSchema.getSchemaName().getValue(), batchSchema
		    .getSchemaContent().getValue());
	    // Multi
	    SchemaDocument multiSchema = response.getIntegrationFlowSchema()
		    .getValue().getMultiSchema().getValue();
	    schema.put(multiSchema.getSchemaName().getValue(), multiSchema
		    .getSchemaContent().getValue());
	    // Batch Multi
	    SchemaDocument batchMultiSchema = response
		    .getIntegrationFlowSchema().getValue()
		    .getBatchMultiSchema().getValue();
	    schema.put(batchMultiSchema.getSchemaName().getValue(),
		    batchMultiSchema.getSchemaContent().getValue());

	    Set<String> keySet = schema.keySet();

	    for (String schemaNames : keySet) {
		String schemaDoc = schema.get(schemaNames);
		FileUtil.doFileWrite(schemaDoc, schemaNames, folderPath);
	    }

	    // update the published flows list
	    publishedFlows.add(flowName);
	}
    }

    private void initService(String url) {
	URL wsdlUrl = null;
	try {
	    wsdlUrl = new URL(url);
	} catch (MalformedURLException e) {
	    TWSConsumerLog.logError("Flow Service url is not well formed", e);
	}
	IntegrationFlowServiceWS flowService = new IntegrationFlowServiceWS(
		wsdlUrl, new QName("http://IntegrationFlowServiceWS",
			"IntegrationFlowServiceWS"));
	servicePort = flowService
		.getIntegrationFlowServiceWSHttpSoap11Endpoint();
    }

    private void initUserDetails(String userName, String password) {
	ObjectFactory factory = new ObjectFactory();
	userDetails = new com.temenos.soa.services.data.xsd.T24UserDetails();
	userDetails.setUser(factory.createT24UserDetailsUser(userName));
	userDetails.setPassword(factory.createT24UserDetailsPassword(password));
    }

    /**
     * Helps to set/update the flow service data builder associated with this
     * instance.
     *
     * @param createFlowServiceDataBuilder
     */
    public void setFlowServiceDataBuilder(
	    TafjCreateFlowServiceDataBuilder createFlowServiceDataBuilder) {
	this.createFlowServiceDataBuilder = createFlowServiceDataBuilder;
    }
}

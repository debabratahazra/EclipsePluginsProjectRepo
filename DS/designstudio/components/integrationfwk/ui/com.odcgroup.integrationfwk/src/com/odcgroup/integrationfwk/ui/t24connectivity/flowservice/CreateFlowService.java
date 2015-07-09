package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.decorators.FilePropertyPage;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.utils.FileUtil;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * This class creates new Integration flow in T24. having T24Event model class,
 * JSubroutineParameters is populated and given as argument to createFlow
 * subroutine.
 * 
 * @author eswaranmuthu
 * 
 */
public class CreateFlowService extends InputFlowService {

	/**
	 * instance of {@link JSubroutineParameters}
	 */
	private JSubroutineParameters jSubroutineResponse;
	/**
	 * flag which tell us whether the particular event successfully published or
	 * not
	 */
	private boolean hasSuccessfullyPublished;

	/**
	 * holds the name of the flow which has been successfully published into T24
	 */
	private final List<String> publishedFlows;

	private final int ADDITIONAL_SCHEMA_LOCATION = 7;

	/**
	 * Creates the instance of create flow service with the given details.
	 * 
	 * @param configEntity
	 * @param projectName
	 * @param createFlowServiceDataBuilder
	 */
	public CreateFlowService(ConfigEntity configEntity, String projectName,
			TafcCreateFlowServiceDataBuilder createFlowServiceDataBuilder) {
		super(configEntity, projectName, createFlowServiceDataBuilder);
		publishedFlows = new ArrayList<String>();
	}

	public void createFlow(EventFlow t24Event, StringBuffer publishLog,
			FilePropertyPage filePropertyPage, String projectName) {
		eventFlow = t24Event;
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		if (jSubroutineRequest == null || jSubroutineRequest.isEmpty()) {
			errorDecorator(publishLog, filePropertyPage);
			hasSuccessfullyPublished = false;
		} else {
			jSubroutineResponse = getT24Response(jSubroutineRequest,
					CREATE_FLOW_SERVICE, projectName, false);

			getStatus(jSubroutineResponse, publishLog, filePropertyPage);
		}
	}

	/**
	 * get the T24 Response schema and write it in respective project folder.
	 * 
	 * @param project
	 * @param flowName
	 *            - name of the flow which initiate the T24 process.
	 */
	public void createSchema(TWSConsumerProject project, String flowName) {
		if (jSubroutineResponse == null || !hasSuccessfullyPublished) {
			return;
		}
		String schemaContent = jSubroutineResponse.get(4).get(1, 1);
		String schemaName = jSubroutineResponse.get(4).get(2);

		if (schemaContent == null || schemaContent.length() == 0) {
			if (!publishedFlows.contains(Utils.getFlowName(flowName))) {
				// exceptional scenario
				LogConsole.getSoaConsole().logMessage(
						"Schema returned from T24 for the flow " + flowName
								+ " is empty!");
			}
			// most possibly the incoming flow has been published into T24
			// already. The T24 itself doesn't return the schema content if we
			// are trying to publish a same flow continuously!!!
			return;
		}
		String folderPath = project.getPathString() + File.separator
				+ StringConstants.SCHEMA_FOLDER_NAME + File.separator
				+ Utils.getFlowName(flowName);
		FileUtil.createFolder(folderPath);
		FileUtil.doFileWrite(schemaContent, schemaName, folderPath);
		Map<String, String> schema = new HashMap<String, String>();

		JDynArray publishedSchemas = jSubroutineResponse.get(4);
		for (int importedSchemaCount = 1; importedSchemaCount <= publishedSchemas
				.getNumberOfValues(5); importedSchemaCount++) {
			schema.put(publishedSchemas.get(5, importedSchemaCount),
					publishedSchemas.get(6, importedSchemaCount));
		}

		// Additional Schema for Batch, Multi and Batch Multi so on...
		// MV<1> = Name, Mv<2> = Schema
		for (int i = ADDITIONAL_SCHEMA_LOCATION; i <= publishedSchemas
				.getNumberOfAttributes(); i++) {
			schema.put(publishedSchemas.get(i, 1)
					+ StringConstants.SCHEMA_EXTENSION,
					publishedSchemas.get(i, 2));
		}

		Set<String> keySet = schema.keySet();
		for (String schemaNames : keySet) {
			String schemaDoc = schema.get(schemaNames);
			FileUtil.doFileWrite(schemaDoc, schemaNames, folderPath);
		}

	}

	private void errorDecorator(StringBuffer publishLog,
			FilePropertyPage filePropertyPage) {
		LogConsole.getSoaConsole().logMessage("Status : FAILURE");
		LogConsole
				.getSoaConsole()
				.logMessage(
						"Reason : "
								+ "Data insufficient, enter valid version and attach it to existing flow");
		publishLog.append("Status : FAILURE \n");
		publishLog
				.append("Reason : "
						+ "Data insufficient, enter valid version and attach it to existing flow"
						+ "\n");
		filePropertyPage.performPublish(true, eventFlow.getEvent()
				.getEventName());

	}

	private void getStatus(JSubroutineParameters jSubroutineResponse,
			StringBuffer publishLog, FilePropertyPage filePropertyPage) {
		if (jSubroutineResponse == null) {
			errorDecorator(publishLog, filePropertyPage);
			hasSuccessfullyPublished = false;
		} else if (jSubroutineResponse.get(5).get(1, 1)
				.equalsIgnoreCase("FAILURE")) {
			LogConsole.getSoaConsole().logMessage("Status : FAILURE");
			LogConsole.getSoaConsole().logMessage(
					"Reason : " + jSubroutineResponse.get(5).get(3));
			publishLog.append("Status : FAILURE \n");
			publishLog.append("Reason : " + jSubroutineResponse.get(5).get(3)
					+ "\n");
			filePropertyPage.performPublish(true, eventFlow.getEvent()
					.getEventName());
			hasSuccessfullyPublished = false;
		} else {
			LogConsole.getSoaConsole().logMessage(
					"Status : Successfully Published");
			publishLog.append("Status : Successfully Published \n");
			filePropertyPage.performPublish(false, eventFlow.getEvent()
					.getEventName());
			hasSuccessfullyPublished = true;
			// update the published flows list
			publishedFlows.add(eventFlow.getFlow().getFlowName());
		}
	}

	/**
	 * Update the given flow service data builder to this instance.
	 * 
	 * @param createFlowServiceDataBuilder
	 */
	public void updateFlowServiceDataBuilder(
			TafcCreateFlowServiceDataBuilder createFlowServiceDataBuilder) {
		setFlowServiceDataBuilder(createFlowServiceDataBuilder);
	}
}

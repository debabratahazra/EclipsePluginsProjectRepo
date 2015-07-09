package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import java.util.ArrayList;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.model.Application;
import com.odcgroup.integrationfwk.ui.model.ApplicationVersionField;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Fields;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.Overrides;
import com.odcgroup.integrationfwk.ui.model.Version;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationFlowService;

/**
 * reads given integration flow from T24 and populates T24Event model class.
 * 
 * @author eswaranmuthu
 * 
 */
public class ReadFlowService extends IntegrationFlowService {

	private final String flowName;
	// TODO:FORESWAREN fix commented lines
	// private IntegrationFlow integrationFlowName;
	private ExitPointType exitPointType;
	// private EnrichmentSummary enrichmentSummary;
	private Fields fields;
	private Overrides overrides;

	public ReadFlowService(ConfigEntity configEntity, String flowName,
			String projectName) {
		super(configEntity, projectName);
		this.flowName = flowName;
	}

	/**
	 * constructs jSubroutineRequest, which is passed as an argument to
	 * jConnection. SUBROUTINE T24IntegrationFlowServiceImpl.readFlow(flowName,
	 * integrationFlowBase, contractData, componentServiceData,
	 * integrationFlowSchema, exitPoint, responseDetails) only flowName is IN
	 * argument, all other six arguments are OUT arguments.
	 * 
	 * @return
	 */
	@Override
	public JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(flowName));
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	/**
	 * constructs T24Event from JSubroutineResponse from T24.
	 * 
	 * @return constructed T24Event.
	 */
	public EventFlow getT24Event(String projectName) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, READ_FLOW_SERVICE, projectName, false);
		eventFlow = new EventFlow();
		Event event = new Event();
		Flow flow = new Flow();
		if (jSubroutineResponse != null) {
			populateT24Event(jSubroutineResponse);
			event.setExitPointType(exitPointType);
			flow.setFields(fields);
			event.setOverrides(overrides);
			// flow.setIntegrationFlow(integrationFlowName);
			eventFlow.setEvent(event);
			eventFlow.setFlow(flow);
			return eventFlow;
		}
		return new EventFlow();
	}

	/**
	 * method constructs List of Application from JSubroutineParameters
	 * jSubroutineResponse argument(<flowName>,<integrationFlowBase>,
	 * <contractData>, <componentServiceData>, <integrationFlowSchema>,
	 * <exitPoint>, <responseDetails>)
	 * 
	 * @param jSubroutineResponse
	 * @return
	 */
	public void populateT24Event(JSubroutineParameters jSubroutineResponse) {
		setFlowName(jSubroutineResponse.get(0));
		setExitPointType(jSubroutineResponse.get(1));
		setContractData(jSubroutineResponse.get(2));
		setServiceData(jSubroutineResponse.get(3));
		setSchema(jSubroutineResponse.get(4));
		setExitPoint(jSubroutineResponse.get(5));
		setSuccessIndicator(jSubroutineResponse.get(6));
	}

	/**
	 * data in jdynArray will of this format <1><1,1> fieldName <1,2> field type
	 * <1,3> display name <2><2,1> field name <2,2> field type <2,3> display
	 * name. this method gets the data from jdynArray and constructs
	 * enrichmentSummary.
	 * 
	 * @param jdynArray
	 *            3rd argument from T24 response.
	 */
	private void setContractData(JDynArray jdynArray) {
		// enrichmentSummary = new EnrichmentSummary();
		fields = new Fields();
		Field field = new ApplicationVersionField();
		List<Field> listFields = new ArrayList<Field>();
		int size = jdynArray.getNumberOfAttributes();
		for (int count = 0; count < size; count++) {
			String fieldName = jdynArray.get(count + 1, 1);
			jdynArray.get(count + 1, 2);
			String displayName = jdynArray.get(count + 1, 3);
			if (displayName.startsWith("!")) {
				field.setFieldName(fieldName);
				field.setDisplayName(displayName);
			} else {
				field.setFieldName(fieldName);
				field.setDisplayName(displayName);
			}
			listFields.add(field);
		}
		fields.setInputFields(listFields);
	}

	/**
	 * jdynArray contains two element 1) exit point and 2) overrides
	 * 
	 * @param jdynArray
	 *            6th argument from T24 response.
	 */
	private void setExitPoint(JDynArray jdynArray) {
		exitPointType.setExitPoint(jdynArray.get(1, 1));
		overrides = new Overrides();
		List<String> overrideList = new ArrayList<String>();
		int numberofValues = jdynArray.getNumberOfValues(2);
		for (int count = 0; count < numberofValues; count++) {
			overrideList.add(jdynArray.get(2, 1 + count));
		}
		overrides.setOverrides(overrideList);
	}

	/**
	 * the three elements of jdynArray are 1) flow name 2) version , Application
	 * or service 3) name of contract or service.
	 * 
	 * @param jdynArray
	 *            2nd argument from T24 response.
	 */
	private void setExitPointType(JDynArray jdynArray) {
		if (jdynArray.get(2, 1).equalsIgnoreCase("version")) {
			exitPointType = new Version();
		} else {
			exitPointType = new Application();
		}
		exitPointType.setContractName(jdynArray.get(3, 1));
	}

	private void setFlowName(JDynArray jdynArray) {
		// integrationFlowName = new IntegrationFlow();
		// integrationFlowName.setIntegrationFlow(jdynArray.get(1, 1));
	}

	private void setSchema(JDynArray jdynArray) {
		// TODO implement this method.
	}

	private void setServiceData(JDynArray jdynArray) {
	}

	private void setSuccessIndicator(JDynArray jdynArray) {
		System.out.println(jdynArray.get(1));

	}
}

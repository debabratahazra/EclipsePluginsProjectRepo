package com.odcgroup.integrationfwk.t24connectivity.flowservice.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.jbase.jremote.JDynArray;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.model.Application;
import com.odcgroup.integrationfwk.ui.model.ApplicationVersionField;
import com.odcgroup.integrationfwk.ui.model.ComponentService;
import com.odcgroup.integrationfwk.ui.model.ComponentServiceField;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Fields;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.FlowAttribute;
import com.odcgroup.integrationfwk.ui.model.FlowAttributes;
import com.odcgroup.integrationfwk.ui.model.Overrides;
import com.odcgroup.integrationfwk.ui.model.SourceType;
import com.odcgroup.integrationfwk.ui.model.TSAService;
import com.odcgroup.integrationfwk.ui.model.Version;
import com.odcgroup.integrationfwk.ui.t24connectivity.flowservice.TafcCreateFlowServiceDataBuilder;
import com.odcgroup.integrationfwk.ui.t24connectivity.flowservice.TafcCreateFlowServiceDataBuilderImpl;

/**
 * Responsible for running unit tests on
 * {@link TafcCreateFlowServiceDataBuilderImpl}
 * 
 * @author sbharathraja
 * 
 */
public class TafcCreateFlowServiceDataBuilderImplTest {

	/**
	 * 
	 * @return mocked event flow instance which is in type of application exit
	 *         point.
	 */
	private EventFlow mockApplicationTypeFlow() {

		// build a dummy application type of exit point
		ExitPointType exitPointType = new Application();
		exitPointType.setContractName("FUNDS.TRANSFER");
		exitPointType.setExitPoint("INPUT.ROUTINE-IFToolingTest");
		// build a dummy overrides
		Overrides overrides = new Overrides();
		overrides.addOverride("override1");
		overrides.addOverride("override2");
		overrides.addOverride("override3");

		// build a dummy event
		Event event = new Event();
		event.setEventName("FUNDS.TRANSFER");
		event.setExitPointType(exitPointType);
		event.setFlowName("IFToolingTest-Flow1");
		event.setOverrides(overrides);

		// build a dummy flow
		Flow flow = new Flow();
		flow.setBaseEvent("Event1");
		flow.setExitPointType(SourceType.APPLICATION);
		flow.setFlowName("IFToolingTest-Flow1");
		flow.setFields(mockApplicationVersionFields());
		// build a dummy event flow
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);

		return eventFlow;
	}

	/**
	 * 
	 * @return mocked event flow instance which is in type of application exit
	 *         point.
	 */
	private EventFlow mockApplicationTypeFlowWithAttributes() {

		// build a dummy application type of exit point
		ExitPointType exitPointType = new Application();
		exitPointType.setContractName("FUNDS.TRANSFER");
		exitPointType.setExitPoint("INPUT.ROUTINE-IFToolingTest");
		// build a dummy overrides
		Overrides overrides = new Overrides();
		overrides.addOverride("override1");
		overrides.addOverride("override2");
		overrides.addOverride("override3");

		// build a dummy event
		Event event = new Event();
		event.setEventName("FUNDS.TRANSFER");
		event.setExitPointType(exitPointType);
		event.setFlowName("IFToolingTest-Flow1");
		event.setOverrides(overrides);

		// build a dummy flow
		Flow flow = new Flow();
		flow.setBaseEvent("Event1");
		flow.setExitPointType(SourceType.APPLICATION);
		flow.setFlowName("IFToolingTest-Flow1");
		flow.setFields(mockApplicationVersionFields());
		FlowAttributes flowAttribs = new FlowAttributes();
		flowAttribs.addFlowAttribute(FlowAttribute.INCLUDE_BEFORE_IMAGE);
		flowAttribs.addFlowAttribute(FlowAttribute.PROCESS_ONLY_UPDATES);
		flow.setAttributes(flowAttribs);
		// build a dummy event flow
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);

		return eventFlow;
	}

	/**
	 * 
	 * @return some dummy T24 Application/Version fields.
	 */
	private Fields mockApplicationVersionFields() {
		Fields fields = new Fields();

		Field field = new ApplicationVersionField();
		field.setDisplayName("field1");
		field.setFieldName("FIELD.1");
		field.setFieldType("string");
		fields.addField(field);

		field = new ApplicationVersionField();
		field.setDisplayName("field2");
		field.setFieldName("FIELD.2");
		field.setFieldType("decimal");
		fields.addField(field);

		field = new ApplicationVersionField();
		field.setDisplayName("field3");
		field.setFieldName("FIELD.3");
		field.setFieldType("string");
		fields.addField(field);

		return fields;
	}

	/**
	 * 
	 * @return some dummy T24 Component Service fields.
	 */
	private Fields mockComponentServiceFields() {

		Fields fields = new Fields();

		Field field = new ComponentServiceField();
		field.setDisplayName("field1");
		field.setFieldName("FIELD.1");
		field.setFieldType("string");
		fields.addField(field);

		field = new ComponentServiceField();
		field.setDisplayName("field2");
		field.setFieldName("FIELD.2");
		field.setFieldType("decimal");
		fields.addField(field);

		field = new ComponentServiceField();
		field.setDisplayName("field3");
		field.setFieldName("FIELD.3");
		field.setFieldType("string");
		fields.addField(field);

		return fields;

	}

	/**
	 * 
	 * @return mocked event flow instance which is in type of component service
	 *         exit point.
	 */
	private EventFlow mockComponentServiceTypeFlow() {
		// build a dummy application type of exit point
		ExitPointType exitPointType = new ComponentService();
		exitPointType.setContractName("CUSTOMERService");
		exitPointType.setExitPoint("GETSMSDETAILS-IFToolingTest");
		// build a dummy overrides
		Overrides overrides = new Overrides();
		overrides.addOverride("override1");
		overrides.addOverride("override2");
		overrides.addOverride("override3");

		// build a dummy event
		Event event = new Event();
		event.setEventName("Event1");
		event.setExitPointType(exitPointType);
		event.setFlowName("IFToolingTest-Flow1");
		event.setOverrides(overrides);

		// build a dummy flow
		Flow flow = new Flow();
		flow.setBaseEvent("CALC.INT");
		flow.setExitPointType(SourceType.COMPONENT_SERVICE);
		flow.setFlowName("IFToolingTest-Flow1");
		flow.setFields(mockComponentServiceFields());
		// build a dummy event flow
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);

		return eventFlow;
	}

	/**
	 * 
	 * @return mocked event flow instance which is in type of tsa service exit
	 *         point.
	 */
	private EventFlow mockTsaServiceTypeFlow() {
		// build a dummy application type of exit point
		ExitPointType exitPointType = new TSAService();
		exitPointType.setContractName("CALC.INT");
		exitPointType.setExitPoint("-IFToolingTest");
		// build a dummy overrides
		Overrides overrides = new Overrides();
		overrides.addOverride("override1");
		overrides.addOverride("override2");
		overrides.addOverride("override3");

		// build a dummy event
		Event event = new Event();
		event.setEventName("Event1");
		event.setExitPointType(exitPointType);
		event.setFlowName("IFToolingTest-Flow1");
		event.setOverrides(overrides);

		// build a dummy flow
		Flow flow = new Flow();
		flow.setBaseEvent("CALC.INT");
		flow.setExitPointType(SourceType.TSA_SERVICE);
		flow.setFlowName("IFToolingTest-Flow1");
		flow.setFields(mockApplicationVersionFields());
		// build a dummy event flow
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);

		return eventFlow;
	}

	/**
	 * 
	 * @return mocked event flow instance which is in type of tsa service exit
	 *         point.
	 */
	private EventFlow mockTsaServiceTypeFlowWithAttributes() {
		// build a dummy application type of exit point
		ExitPointType exitPointType = new TSAService();
		exitPointType.setContractName("CALC.INT");
		exitPointType.setExitPoint("-IFToolingTest");
		// build a dummy overrides
		Overrides overrides = new Overrides();
		overrides.addOverride("override1");
		overrides.addOverride("override2");
		overrides.addOverride("override3");

		// build a dummy event
		Event event = new Event();
		event.setEventName("Event1");
		event.setExitPointType(exitPointType);
		event.setFlowName("IFToolingTest-Flow1");
		event.setOverrides(overrides);

		// build a dummy flow
		Flow flow = new Flow();
		flow.setBaseEvent("CALC.INT");
		flow.setExitPointType(SourceType.TSA_SERVICE);
		flow.setFlowName("IFToolingTest-Flow1");
		flow.setFields(mockApplicationVersionFields());
		FlowAttributes flowAttribs = new FlowAttributes();
		flowAttribs.addFlowAttribute(FlowAttribute.INCLUDE_BEFORE_IMAGE);
		flowAttribs.addFlowAttribute(FlowAttribute.PROCESS_ONLY_UPDATES);
		flow.setAttributes(flowAttribs);
		// build a dummy event flow
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);

		return eventFlow;
	}

	/**
	 * 
	 * @return mocked event flow instance which is in type of version exit
	 *         point.
	 */
	private EventFlow mockVersionTypeFlow() {
		// build a dummy application type of exit point
		ExitPointType exitPointType = new Version();
		exitPointType.setContractName("FUNDS.TRANSFER,NEW");
		exitPointType.setExitPoint("AUTH.ROUTINE-IFToolingTest");
		// build a dummy overrides
		Overrides overrides = new Overrides();
		overrides.addOverride("override1");
		overrides.addOverride("override2");
		overrides.addOverride("override3");

		// build a dummy event
		Event event = new Event();
		event.setEventName("Event1");
		event.setExitPointType(exitPointType);
		event.setFlowName("IFToolingTest-Flow1");
		event.setOverrides(overrides);

		// build a dummy flow
		Flow flow = new Flow();
		flow.setBaseEvent("FUNDS.TRANSFER,NEW");
		flow.setExitPointType(SourceType.VERSION);
		flow.setFlowName("IFToolingTest-Flow1");
		flow.setFields(mockApplicationVersionFields());
		// build a dummy event flow
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);

		return eventFlow;
	}

	/**
	 * 
	 * @return mocked event flow instance which is in type of version exit
	 *         point.
	 */
	private EventFlow mockVersionTypeFlowWithAttributes() {
		// build a dummy application type of exit point
		ExitPointType exitPointType = new Version();
		exitPointType.setContractName("FUNDS.TRANSFER,NEW");
		exitPointType.setExitPoint("AUTH.ROUTINE-IFToolingTest");
		// build a dummy overrides
		Overrides overrides = new Overrides();
		overrides.addOverride("override1");
		overrides.addOverride("override2");
		overrides.addOverride("override3");

		// build a dummy event
		Event event = new Event();
		event.setEventName("Event1");
		event.setExitPointType(exitPointType);
		event.setFlowName("IFToolingTest-Flow1");
		event.setOverrides(overrides);

		// build a dummy flow
		Flow flow = new Flow();
		flow.setBaseEvent("FUNDS.TRANSFER,NEW");
		flow.setExitPointType(SourceType.VERSION);
		flow.setFlowName("IFToolingTest-Flow1");
		flow.setFields(mockApplicationVersionFields());
		FlowAttributes flowAttribs = new FlowAttributes();
		flowAttribs.addFlowAttribute(FlowAttribute.INCLUDE_BEFORE_IMAGE);
		flowAttribs.addFlowAttribute(FlowAttribute.PROCESS_ONLY_UPDATES);
		flow.setAttributes(flowAttribs);
		// build a dummy event flow
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);

		return eventFlow;
	}

	@Test
	public void testCreateComponetServiceData() throws Exception {
		// test for application type.
		TafcCreateFlowServiceDataBuilder dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockApplicationTypeFlow());
		JDynArray tafcComponentServiceData = dataBuilder
				.createComponetServiceData();
		assertNotNull(tafcComponentServiceData);
		assertTrue(tafcComponentServiceData.getNumberOfAttributes() == 0);
		assertTrue(tafcComponentServiceData.toString().equals(""));

		// test for version type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockVersionTypeFlow());
		tafcComponentServiceData = dataBuilder.createComponetServiceData();
		assertNotNull(tafcComponentServiceData);
		assertTrue(tafcComponentServiceData.getNumberOfAttributes() == 0);
		assertTrue(tafcComponentServiceData.toString().equals(""));

		// test for component service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockComponentServiceTypeFlow());
		tafcComponentServiceData = dataBuilder.createComponetServiceData();
		assertNotNull(tafcComponentServiceData);
		assertTrue(tafcComponentServiceData.getNumberOfAttributes() == 0);
		assertTrue(tafcComponentServiceData.toString().equals(""));

		// test for tsa service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockTsaServiceTypeFlow());
		tafcComponentServiceData = dataBuilder.createComponetServiceData();
		assertNotNull(tafcComponentServiceData);
		assertTrue(tafcComponentServiceData.getNumberOfAttributes() == 0);
		assertTrue(tafcComponentServiceData.toString().equals(""));
	}

	@Test
	public void testCreateContractData() throws Exception {
		// test for application type
		TafcCreateFlowServiceDataBuilder dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockApplicationTypeFlow());
		JDynArray tafcContractData = dataBuilder.createContractData();
		assertNotNull(tafcContractData);
		assertTrue(tafcContractData.getNumberOfAttributes() == 3);

		assertTrue(tafcContractData.get(1, 1).equals("field1"));
		assertTrue(tafcContractData.get(1, 2).equals("string"));
		assertTrue(tafcContractData.get(1, 3).equals("FIELD.1"));

		assertTrue(tafcContractData.get(2, 1).equals("field2"));
		assertTrue(tafcContractData.get(2, 2).equals("decimal"));
		assertTrue(tafcContractData.get(2, 3).equals("FIELD.2"));

		assertTrue(tafcContractData.get(3, 1).equals("field3"));
		assertTrue(tafcContractData.get(3, 2).equals("string"));
		assertTrue(tafcContractData.get(3, 3).equals("FIELD.3"));

		// test for version type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockVersionTypeFlow());
		tafcContractData = dataBuilder.createContractData();
		assertNotNull(tafcContractData);
		assertTrue(tafcContractData.getNumberOfAttributes() == 3);

		assertTrue(tafcContractData.get(1, 1).equals("field1"));
		assertTrue(tafcContractData.get(1, 2).equals("string"));
		assertTrue(tafcContractData.get(1, 3).equals("FIELD.1"));

		assertTrue(tafcContractData.get(2, 1).equals("field2"));
		assertTrue(tafcContractData.get(2, 2).equals("decimal"));
		assertTrue(tafcContractData.get(2, 3).equals("FIELD.2"));

		assertTrue(tafcContractData.get(3, 1).equals("field3"));
		assertTrue(tafcContractData.get(3, 2).equals("string"));
		assertTrue(tafcContractData.get(3, 3).equals("FIELD.3"));

		// test for component service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockComponentServiceTypeFlow());
		tafcContractData = dataBuilder.createContractData();
		assertNotNull(tafcContractData);
		assertTrue(tafcContractData.getNumberOfAttributes() == 3);

		assertTrue(tafcContractData.get(1, 1).equals("field1"));
		assertTrue(tafcContractData.get(1, 2).equals("string"));
		assertTrue(tafcContractData.get(1, 3).equals("FIELD.1"));

		assertTrue(tafcContractData.get(2, 1).equals("field2"));
		assertTrue(tafcContractData.get(2, 2).equals("decimal"));
		assertTrue(tafcContractData.get(2, 3).equals("FIELD.2"));

		assertTrue(tafcContractData.get(3, 1).equals("field3"));
		assertTrue(tafcContractData.get(3, 2).equals("string"));
		assertTrue(tafcContractData.get(3, 3).equals("FIELD.3"));

		// test for tsa service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockTsaServiceTypeFlow());
		tafcContractData = dataBuilder.createContractData();
		assertNotNull(tafcContractData);
		assertTrue(tafcContractData.getNumberOfAttributes() == 3);

		assertTrue(tafcContractData.get(1, 1).equals("field1"));
		assertTrue(tafcContractData.get(1, 2).equals("string"));
		assertTrue(tafcContractData.get(1, 3).equals("FIELD.1"));

		assertTrue(tafcContractData.get(2, 1).equals("field2"));
		assertTrue(tafcContractData.get(2, 2).equals("decimal"));
		assertTrue(tafcContractData.get(2, 3).equals("FIELD.2"));

		assertTrue(tafcContractData.get(3, 1).equals("field3"));
		assertTrue(tafcContractData.get(3, 2).equals("string"));
		assertTrue(tafcContractData.get(3, 3).equals("FIELD.3"));

	}

	@Test
	public void testCreateExitPointData() throws Exception {
		// test for application type
		TafcCreateFlowServiceDataBuilder dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockApplicationTypeFlow());
		JDynArray tafcExitPointData = dataBuilder.createExitPointData();
		assertNotNull(tafcExitPointData);
		// contract name will be not available for application type; It will be
		// available only for TSA Service type at 3rd position.
		assertTrue(tafcExitPointData.getNumberOfAttributes() == 2);

		assertTrue(tafcExitPointData.get(1).equals(
				"INPUT.ROUTINE-IFToolingTest"));
		// 3 overrides should be available
		assertTrue(tafcExitPointData.getNumberOfValues(2) == 3);
		assertTrue(tafcExitPointData.get(2, 1).equals("override1"));
		assertTrue(tafcExitPointData.get(2, 2).equals("override2"));
		assertTrue(tafcExitPointData.get(2, 3).equals("override3"));

		// test for version type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockVersionTypeFlow());
		tafcExitPointData = dataBuilder.createExitPointData();
		assertNotNull(tafcExitPointData);
		// contract name will be not available for application type; It will be
		// available only for TSA Service type at 3rd position.
		assertTrue(tafcExitPointData.getNumberOfAttributes() == 2);

		assertTrue(tafcExitPointData.get(1)
				.equals("AUTH.ROUTINE-IFToolingTest"));
		// 3 overrides should be available
		assertTrue(tafcExitPointData.getNumberOfValues(2) == 3);
		assertTrue(tafcExitPointData.get(2, 1).equals("override1"));
		assertTrue(tafcExitPointData.get(2, 2).equals("override2"));
		assertTrue(tafcExitPointData.get(2, 3).equals("override3"));

		// test for component service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockComponentServiceTypeFlow());
		tafcExitPointData = dataBuilder.createExitPointData();
		assertNotNull(tafcExitPointData);
		// contract name will be not available for application type; It will be
		// available only for TSA Service type at 3rd position.
		assertTrue(tafcExitPointData.getNumberOfAttributes() == 2);

		assertTrue(tafcExitPointData.get(1).equals(
				"SERVICE.OPERATION-IFToolingTest"));
		// 3 overrides should be available
		assertTrue(tafcExitPointData.getNumberOfValues(2) == 3);
		assertTrue(tafcExitPointData.get(2, 1).equals("override1"));
		assertTrue(tafcExitPointData.get(2, 2).equals("override2"));
		assertTrue(tafcExitPointData.get(2, 3).equals("override3"));

		// test for TSA service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockTsaServiceTypeFlow());
		tafcExitPointData = dataBuilder.createExitPointData();
		assertNotNull(tafcExitPointData);
		// contract name is available only for TSA Service type at 3rd position.
		assertTrue(tafcExitPointData.getNumberOfAttributes() == 3);

		assertTrue(tafcExitPointData.get(1).equals("JOB.PROCESS-IFToolingTest"));
		// 3 overrides should be available
		assertTrue(tafcExitPointData.getNumberOfValues(2) == 3);
		assertTrue(tafcExitPointData.get(2, 1).equals("override1"));
		assertTrue(tafcExitPointData.get(2, 2).equals("override2"));
		assertTrue(tafcExitPointData.get(2, 3).equals("override3"));
		// contract name should be in 3rd position for tsa service type
		assertTrue(tafcExitPointData.get(3).equals("CALC.INT"));

	}

	@Test
	public void testCreateIntegrationFlowBaseData() throws Exception {
		// test for application type
		TafcCreateFlowServiceDataBuilder dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockApplicationTypeFlow());
		JDynArray tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals("APPLICATION"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals("FUNDS.TRANSFER"));
		assertTrue(tafcIntegrationFlowBaseData.get(4).equals(""));

		// test for version type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockVersionTypeFlow());
		tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals("VERSION"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals(
				"FUNDS.TRANSFER,NEW"));
		assertTrue(tafcIntegrationFlowBaseData.get(4).equals(""));

		// test for component service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockComponentServiceTypeFlow());
		tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals(
				"COMPONENT.SERVICE"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals(
				"CUSTOMERService.GETSMSDETAILS"));
		assertTrue(tafcIntegrationFlowBaseData.get(4).equals(""));

		// test for tsa service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockTsaServiceTypeFlow());
		tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals("TSA.SERVICE"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals("CALC.INT"));
		assertTrue(tafcIntegrationFlowBaseData.get(4).equals(""));
	}

	@Test
	public void testCreateIntegrationFlowBaseDataWithAttributes()
			throws Exception {
		// test for application type
		TafcCreateFlowServiceDataBuilder dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockApplicationTypeFlowWithAttributes());
		JDynArray tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals("APPLICATION"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals("FUNDS.TRANSFER"));
		assertTrue(tafcIntegrationFlowBaseData.get(4, 1).equals(
				FlowAttribute.PROCESS_ONLY_UPDATES.toString()));
		assertTrue(tafcIntegrationFlowBaseData.get(4, 2).equals(
				FlowAttribute.INCLUDE_BEFORE_IMAGE.toString()));

		// test for version type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockVersionTypeFlowWithAttributes());
		tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals("VERSION"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals(
				"FUNDS.TRANSFER,NEW"));
		assertTrue(tafcIntegrationFlowBaseData.get(4, 1).equals(
				FlowAttribute.PROCESS_ONLY_UPDATES.toString()));
		assertTrue(tafcIntegrationFlowBaseData.get(4, 2).equals(
				FlowAttribute.INCLUDE_BEFORE_IMAGE.toString()));

		// test for component service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockComponentServiceTypeFlow());
		tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals(
				"COMPONENT.SERVICE"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals(
				"CUSTOMERService.GETSMSDETAILS"));
		assertTrue(tafcIntegrationFlowBaseData.get(4).equals(""));

		// test for tsa service type
		dataBuilder = new TafcCreateFlowServiceDataBuilderImpl(
				mockTsaServiceTypeFlowWithAttributes());
		tafcIntegrationFlowBaseData = dataBuilder
				.createIntegrationFlowBaseData();
		assertNotNull(tafcIntegrationFlowBaseData);
		assertTrue(tafcIntegrationFlowBaseData.getNumberOfAttributes() == 4);
		assertTrue(tafcIntegrationFlowBaseData.get(1).equals(
				"IFToolingTest-Flow1"));
		assertTrue(tafcIntegrationFlowBaseData.get(2).equals("TSA.SERVICE"));
		assertTrue(tafcIntegrationFlowBaseData.get(3).equals("CALC.INT"));
		assertTrue(tafcIntegrationFlowBaseData.get(4, 1).equals(
				FlowAttribute.PROCESS_ONLY_UPDATES.toString()));
		assertTrue(tafcIntegrationFlowBaseData.get(4, 2).equals(
				FlowAttribute.INCLUDE_BEFORE_IMAGE.toString()));
	}

	@Test
	public void testTafcCreateFlowServiceDataBuilder() throws Exception {
		try {
			new TafcCreateFlowServiceDataBuilderImpl(null);
			fail("TWSConsumerPlugin exception should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}
	}

}

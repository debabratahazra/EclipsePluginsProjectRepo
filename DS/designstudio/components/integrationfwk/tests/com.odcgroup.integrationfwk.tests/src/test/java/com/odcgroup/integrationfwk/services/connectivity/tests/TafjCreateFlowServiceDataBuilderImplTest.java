package com.odcgroup.integrationfwk.services.connectivity.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

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
import com.odcgroup.integrationfwk.ui.services.connectivity.TafjCreateFlowServiceDataBuilder;
import com.odcgroup.integrationfwk.ui.services.connectivity.TafjCreateFlowServiceDataBuilderImpl;
import com.temenos.services.integrationflow.data.xsd.ComponentServiceData;
import com.temenos.services.integrationflow.data.xsd.ContractData;
import com.temenos.services.integrationflow.data.xsd.ExitPoint;
import com.temenos.services.integrationflow.data.xsd.IntegrationFlowBase;

/**
 * Responsible for running unit tests on
 * {@link TafjCreateFlowServiceDataBuilderImpl}
 * 
 * @author sbharathraja
 * 
 */
public class TafjCreateFlowServiceDataBuilderImplTest {

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
	exitPointType.setContractName("CUSTOMERSERVICE");
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
	// test for application type
	TafjCreateFlowServiceDataBuilder dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockApplicationTypeFlow());
	List<ComponentServiceData> componentServiceData = dataBuilder
		.createComponetServiceData();
	assertNotNull(componentServiceData);
	assertTrue(componentServiceData.isEmpty());
	// nothing to check more here as of now.

	// test for version type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockVersionTypeFlow());
	componentServiceData = dataBuilder.createComponetServiceData();
	assertNotNull(componentServiceData);
	assertTrue(componentServiceData.isEmpty());
	// nothing to check more here as of now.

	// test for component service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockComponentServiceTypeFlow());
	componentServiceData = dataBuilder.createComponetServiceData();
	assertNotNull(componentServiceData);
	assertTrue(componentServiceData.isEmpty());
	// nothing to check more here as of now.

	// test for tsa service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockTsaServiceTypeFlow());
	componentServiceData = dataBuilder.createComponetServiceData();
	assertNotNull(componentServiceData);
	assertTrue(componentServiceData.isEmpty());
	// nothing to check more here as of now.
    }

    @Test
    public void testCreateContractData() throws Exception {
	// test for application type
	TafjCreateFlowServiceDataBuilder dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockApplicationTypeFlow());
	List<ContractData> contractDataResult = dataBuilder
		.createContractData();
	assertNotNull(contractDataResult);
	// 3 data should be available
	assertTrue(contractDataResult.size() == 3);
	// checking the first data
	ContractData tafjContractData = contractDataResult.get(0);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field1"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.1"));
	// checking the second data
	tafjContractData = contractDataResult.get(1);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field2"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("decimal"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.2"));
	// checking the third data
	tafjContractData = contractDataResult.get(2);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field3"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.3"));

	// test for version type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockVersionTypeFlow());
	contractDataResult = dataBuilder.createContractData();
	assertNotNull(contractDataResult);
	// 3 data should be available
	assertTrue(contractDataResult.size() == 3);
	// checking the first data
	tafjContractData = contractDataResult.get(0);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field1"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.1"));
	// checking the second data
	tafjContractData = contractDataResult.get(1);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field2"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("decimal"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.2"));
	// checking the third data
	tafjContractData = contractDataResult.get(2);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field3"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.3"));

	// test for component service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockComponentServiceTypeFlow());
	contractDataResult = dataBuilder.createContractData();
	assertNotNull(contractDataResult);
	// 3 data should be available
	assertTrue(contractDataResult.size() == 3);
	// checking the first data
	tafjContractData = contractDataResult.get(0);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field1"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.1"));
	// checking the second data
	tafjContractData = contractDataResult.get(1);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field2"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("decimal"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.2"));
	// checking the third data
	tafjContractData = contractDataResult.get(2);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field3"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.3"));

	// test for tsa service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockTsaServiceTypeFlow());
	contractDataResult = dataBuilder.createContractData();
	assertNotNull(contractDataResult);
	// 3 data should be available
	assertTrue(contractDataResult.size() == 3);
	// checking the first data
	tafjContractData = contractDataResult.get(0);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field1"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.1"));
	// checking the second data
	tafjContractData = contractDataResult.get(1);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field2"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("decimal"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.2"));
	// checking the third data
	tafjContractData = contractDataResult.get(2);
	assertTrue(tafjContractData.getFieldName().getValue().equals("field3"));
	assertTrue(tafjContractData.getFieldType().getValue().equals("string"));
	assertTrue(tafjContractData.getFieldDefinition().getValue()
		.equals("FIELD.3"));
    }

    @Test
    public void testCreateExitPointData() throws Exception {
	// test for application type
	TafjCreateFlowServiceDataBuilder dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockApplicationTypeFlow());
	ExitPoint tafjExitPointData = dataBuilder.createExitPointData();
	assertNotNull(tafjExitPointData);
	assertTrue(tafjExitPointData.getName().getValue()
		.equals("INPUT.ROUTINE-IFToolingTest"));
	assertTrue(tafjExitPointData.getSourceName().getValue().equals(""));
	List<String> overrides = tafjExitPointData.getOverrideCodes();
	assertNotNull(overrides);
	assertTrue(overrides.size() == 3);
	assertTrue(overrides.get(0).equals("override1"));
	assertTrue(overrides.get(1).equals("override2"));
	assertTrue(overrides.get(2).equals("override3"));

	// test for version type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockVersionTypeFlow());
	tafjExitPointData = dataBuilder.createExitPointData();
	assertNotNull(tafjExitPointData);
	assertTrue(tafjExitPointData.getName().getValue()
		.equals("AUTH.ROUTINE-IFToolingTest"));
	assertTrue(tafjExitPointData.getSourceName().getValue().equals(""));
	overrides = tafjExitPointData.getOverrideCodes();
	assertNotNull(overrides);
	assertTrue(overrides.size() == 3);
	assertTrue(overrides.get(0).equals("override1"));
	assertTrue(overrides.get(1).equals("override2"));
	assertTrue(overrides.get(2).equals("override3"));

	// test for component service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockComponentServiceTypeFlow());
	tafjExitPointData = dataBuilder.createExitPointData();
	assertNotNull(tafjExitPointData);
	assertTrue(tafjExitPointData.getName().getValue()
		.equals("SERVICE.OPERATION-IFToolingTest"));
	assertTrue(tafjExitPointData.getSourceName().getValue().equals(""));
	overrides = tafjExitPointData.getOverrideCodes();
	assertNotNull(overrides);
	assertTrue(overrides.size() == 3);
	assertTrue(overrides.get(0).equals("override1"));
	assertTrue(overrides.get(1).equals("override2"));
	assertTrue(overrides.get(2).equals("override3"));

	// test for tsa service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockTsaServiceTypeFlow());
	tafjExitPointData = dataBuilder.createExitPointData();
	assertNotNull(tafjExitPointData);
	assertTrue(tafjExitPointData.getName().getValue()
		.equals("JOB.PROCESS-IFToolingTest"));
	assertTrue(tafjExitPointData.getSourceName().getValue()
		.equals("CALC.INT"));
	overrides = tafjExitPointData.getOverrideCodes();
	assertNotNull(overrides);
	assertTrue(overrides.size() == 3);
	assertTrue(overrides.get(0).equals("override1"));
	assertTrue(overrides.get(1).equals("override2"));
	assertTrue(overrides.get(2).equals("override3"));
    }

    @Test
    public void testCreateIntegrationFlowBaseData() throws Exception {
	// test for application type
	TafjCreateFlowServiceDataBuilder dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockApplicationTypeFlow());
	IntegrationFlowBase tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("FUNDS.TRANSFER"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("APPLICATION"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 0);

	// test for version type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockVersionTypeFlow());
	tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("FUNDS.TRANSFER,NEW"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("VERSION"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 0);

	// test for component service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockComponentServiceTypeFlow());
	tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("CUSTOMERSERVICE.GETSMSDETAILS"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("COMPONENT.SERVICE"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 0);

	// test for tsa service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockTsaServiceTypeFlow());
	tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("CALC.INT"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("TSA.SERVICE"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 0);
    }

    @Test
    public void testCreateIntegrationFlowBaseDataWithAttributes()
	    throws Exception {
	// test for application type
	TafjCreateFlowServiceDataBuilder dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockApplicationTypeFlowWithAttributes());
	IntegrationFlowBase tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("FUNDS.TRANSFER"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("APPLICATION"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 2);
	assertTrue(tafjIntegrationFlowBaseData.getAttributes().get(0)
		.equals(FlowAttribute.INCLUDE_BEFORE_IMAGE.toString()));
	assertTrue(tafjIntegrationFlowBaseData.getAttributes().get(1)
		.equals(FlowAttribute.PROCESS_ONLY_UPDATES.toString()));

	// test for version type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockVersionTypeFlowWithAttributes());
	tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("FUNDS.TRANSFER,NEW"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("VERSION"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 2);
	assertTrue(tafjIntegrationFlowBaseData.getAttributes().get(0)
		.equals(FlowAttribute.INCLUDE_BEFORE_IMAGE.toString()));
	assertTrue(tafjIntegrationFlowBaseData.getAttributes().get(1)
		.equals(FlowAttribute.PROCESS_ONLY_UPDATES.toString()));

	// test for component service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockComponentServiceTypeFlow());
	tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("CUSTOMERSERVICE.GETSMSDETAILS"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("COMPONENT.SERVICE"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 0);

	// test for tsa service type
	dataBuilder = new TafjCreateFlowServiceDataBuilderImpl(
		mockTsaServiceTypeFlowWithAttributes());
	tafjIntegrationFlowBaseData = dataBuilder
		.createIntegrationFlowBaseData();
	assertNotNull(tafjIntegrationFlowBaseData);
	assertTrue(tafjIntegrationFlowBaseData.getFlowName().getValue()
		.equals("IFToolingTest-Flow1"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceName().getValue()
		.equals("CALC.INT"));
	assertTrue(tafjIntegrationFlowBaseData.getSourceType().getValue()
		.equals("TSA.SERVICE"));
	assertEquals(tafjIntegrationFlowBaseData.getAttributes().size(), 2);
	assertTrue(tafjIntegrationFlowBaseData.getAttributes().get(0)
		.equals(FlowAttribute.INCLUDE_BEFORE_IMAGE.toString()));
	assertTrue(tafjIntegrationFlowBaseData.getAttributes().get(1)
		.equals(FlowAttribute.PROCESS_ONLY_UPDATES.toString()));
    }

    @Test
    public void testTafjCreateFlowServiceDataBuilder() {
	try {
	    new TafjCreateFlowServiceDataBuilderImpl(null);
	    fail("TWSConsumerPlugin Exception should be thrown.");
	} catch (Exception e) {
	    assertTrue(e instanceof TWSConsumerPluginException);
	}
    }

}

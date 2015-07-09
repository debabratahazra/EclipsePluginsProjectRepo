package com.odcgroup.integrationfwk.ui.common;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.common.CreateFlowServiceDataBuilder;
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
import com.odcgroup.integrationfwk.ui.model.Overrides;
import com.odcgroup.integrationfwk.ui.model.SourceType;
import com.odcgroup.integrationfwk.ui.model.TSAService;
import com.odcgroup.integrationfwk.ui.model.Version;

/**
 * Responsible for running unit tests on {@link CreateFlowServiceDataBuilder}
 * 
 * @author sbharathraja
 * 
 */
public class CreateFlowServiceDataBuilderTest {

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
		exitPointType.setContractName("FUNDS.TRANSFER");
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

	@Test
	public void testAbstractCreateFlowServiceDataBuilder() {
		try {
			new CreateFlowServiceDataBuilder(null);
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}
	}

	@Test
	public void testGetContractName() throws Exception {
		// test for some null cases
		try {
			// event is null
			EventFlow dummyEventFlow = new EventFlow();
			dummyEventFlow.setEvent(null);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getContractName();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// exit point type is null
			EventFlow dummyEventFlow = new EventFlow();
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(null);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getContractName();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is null
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn(null);
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getContractName();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is empty
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn("");
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getContractName();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is empty
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn("	");
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getContractName();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		// test for application type
		CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
				mockApplicationTypeFlow());
		assertTrue(dataBuilder.getContractName().equals("FUNDS.TRANSFER"));

		// test for version type
		dataBuilder = new CreateFlowServiceDataBuilder(mockVersionTypeFlow());
		assertTrue(dataBuilder.getContractName().equals("FUNDS.TRANSFER,NEW"));

		// test for component service type
		dataBuilder = new CreateFlowServiceDataBuilder(
				mockComponentServiceTypeFlow());
		assertTrue(dataBuilder.getContractName().equals(
				"CUSTOMERService.GETSMSDETAILS"));

		// test for tsa service type
		dataBuilder = new CreateFlowServiceDataBuilder(mockTsaServiceTypeFlow());
		assertTrue(dataBuilder.getContractName().equals("CALC.INT"));

		// a test case for component service type where service name ends with
		// word service more than one time.
		ExitPointType exitPointType = new ComponentService();
		exitPointType.setContractName("CUSTOMERServiceService");
		exitPointType.setExitPoint("GETSMSDETAILS");
		Event event = new Event();
		event.setExitPointType(exitPointType);
		EventFlow eventFlow = new EventFlow();
		eventFlow.setEvent(event);

		dataBuilder = new CreateFlowServiceDataBuilder(eventFlow);
		assertTrue(dataBuilder.getContractName().equals(
				"CUSTOMERServiceService.GETSMSDETAILS"));
	}

	@Test
	public void testGetExitPoint() throws Exception {
		// test for some null cases
		try {
			// event is null
			EventFlow dummyEventFlow = new EventFlow();
			dummyEventFlow.setEvent(null);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPoint();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// exit point type is null
			EventFlow dummyEventFlow = new EventFlow();
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(null);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPoint();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is null
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn(null);
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPoint();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is empty
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn("");
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPoint();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is empty
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn("	");
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPoint();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		// test for application type
		CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
				mockApplicationTypeFlow());
		assertTrue(dataBuilder.getExitPoint().equals(
				"INPUT.ROUTINE-IFToolingTest"));

		// test for version type
		dataBuilder = new CreateFlowServiceDataBuilder(mockVersionTypeFlow());
		assertTrue(dataBuilder.getExitPoint().equals(
				"AUTH.ROUTINE-IFToolingTest"));

		// test for component service type
		dataBuilder = new CreateFlowServiceDataBuilder(
				mockComponentServiceTypeFlow());
		assertTrue(dataBuilder.getExitPoint().equals(
				"SERVICE.OPERATION-IFToolingTest"));

		// test for tsa service type
		dataBuilder = new CreateFlowServiceDataBuilder(mockTsaServiceTypeFlow());
		assertTrue(dataBuilder.getExitPoint().equals(
				"JOB.PROCESS-IFToolingTest"));
	}

	@Test
	public void testGetExitPointType() throws Exception {
		// test for some null cases
		try {
			// event is null
			EventFlow dummyEventFlow = new EventFlow();
			dummyEventFlow.setEvent(null);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPointType();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// exit point type is null
			EventFlow dummyEventFlow = new EventFlow();
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(null);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPointType();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is null
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn(null);
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPointType();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is empty
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn("");
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPointType();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		try {
			// source type is empty
			EventFlow dummyEventFlow = new EventFlow();
			ExitPointType dummyExitPointType = mock(ExitPointType.class);
			when(dummyExitPointType.getSourceType()).thenReturn("	");
			Event dummyEvent = new Event();
			dummyEvent.setExitPointType(dummyExitPointType);
			dummyEventFlow.setEvent(dummyEvent);
			CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
					dummyEventFlow);
			dataBuilder.getExitPointType();
			fail("TWSConsumerPluginException should be thrown.");
		} catch (Exception e) {
			assertTrue(e instanceof TWSConsumerPluginException);
		}

		// test for application type
		CreateFlowServiceDataBuilder dataBuilder = new CreateFlowServiceDataBuilder(
				mockApplicationTypeFlow());
		assertTrue(dataBuilder.getExitPointType().equals("APPLICATION"));

		// test for version type
		dataBuilder = new CreateFlowServiceDataBuilder(mockVersionTypeFlow());
		assertTrue(dataBuilder.getExitPointType().equals("VERSION"));

		// test for component service type
		dataBuilder = new CreateFlowServiceDataBuilder(
				mockComponentServiceTypeFlow());
		assertTrue(dataBuilder.getExitPointType().equals("COMPONENT.SERVICE"));

		// test for tsa service type
		dataBuilder = new CreateFlowServiceDataBuilder(mockTsaServiceTypeFlow());
		assertTrue(dataBuilder.getExitPointType().equals("TSA.SERVICE"));
	}

}

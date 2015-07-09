package com.odcgroup.integrationfwk.cache.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.odcgroup.integrationfwk.ui.common.CommonStrings;
import com.odcgroup.integrationfwk.ui.model.Application;
import com.odcgroup.integrationfwk.ui.model.ApplicationVersionField;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Fields;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.FlowAttribute;
import com.odcgroup.integrationfwk.ui.model.FlowAttributes;
import com.odcgroup.integrationfwk.ui.model.Overrides;
import com.odcgroup.integrationfwk.ui.utils.CacheManager;

public class CacheManagerTest {

	private CacheManager cacheManager;

	private void assertForEvent(String eventXml) {
		assertTrue(eventXml.contains(CommonStrings.EVENT_EventName));
		assertTrue(eventXml.contains(CommonStrings.EVENT_ContractName));
		assertTrue(eventXml.contains(CommonStrings.EVENT_ExitPoint));
		assertTrue(eventXml.contains(CommonStrings.EVENT_FlowName));
		assertTrue(eventXml.contains(CommonStrings.EVENT_Override1));
		assertTrue(eventXml.contains(CommonStrings.EVENT_Override2));
		assertTrue(eventXml.contains(CommonStrings.EVENT_Override3));

	}

	private void assertForFlow(String flowXml) {
		assertTrue(flowXml.contains(CommonStrings.FLOW_EmptyAttribs));
		assertTrue(flowXml.contains(CommonStrings.FLOW_BaseEvent));
		assertTrue(flowXml.contains(CommonStrings.FLOW_IsComponentService));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_DisplayName));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldName));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldType));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_selected));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_DisplayName1));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldName1));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldType1));
		assertTrue(flowXml.contains(CommonStrings.FLOW_FlowName));
	}

	@Test
	public void EventToXmlTest() {
		Event event = new Event();
		event.setEventName("EventName");
		event.setFlowName("FlowName");
		Overrides overrides = new Overrides();
		List<String> listOfOverrides = new ArrayList<String>();
		listOfOverrides.add("overrides1");
		listOfOverrides.add("overrides2");
		listOfOverrides.add("overrides3");
		overrides.setOverrides(listOfOverrides);
		event.setOverrides(overrides);
		ExitPointType exitPointType = new Application();
		exitPointType.setContractName("contaratName");
		exitPointType.setExitPoint("ExitPoint");
		event.setExitPointType(exitPointType);
		String eventXml = cacheManager.eventToXML(event);
		assertForEvent(eventXml);
	}

	@Test
	public void flowToXMLTest() {
		Flow flow = new Flow();
		flow.setFlowName("FlowName1");
		flow.setBaseEvent("BaseEvent");
		Fields fields = new Fields();
		List<Field> inputFields = new ArrayList<Field>();
		Field field = new ApplicationVersionField();
		Field field1 = new ApplicationVersionField();

		field.setDisplayName("DispalyName");
		field.setFieldName("FieldName");
		field.setFieldType("fieldType");

		field1.setDisplayName("DispalyName1");
		field1.setFieldName("FieldName1");
		field1.setFieldType("fieldType1");

		inputFields.add(field);
		inputFields.add(field1);

		fields.setInputFields(inputFields);
		flow.setFields(fields);
		String flowXml = CacheManager.flowToXML(flow);
		assertForFlow(flowXml);
	}

	@Test
	public void flowToXMLWithAttributesTest() {
		Flow flow = new Flow();
		flow.setFlowName("FlowName1");
		flow.setBaseEvent("BaseEvent");
		Fields fields = new Fields();
		List<Field> inputFields = new ArrayList<Field>();
		Field field = new ApplicationVersionField();
		Field field1 = new ApplicationVersionField();

		field.setDisplayName("DispalyName");
		field.setFieldName("FieldName");
		field.setFieldType("fieldType");

		field1.setDisplayName("DispalyName1");
		field1.setFieldName("FieldName1");
		field1.setFieldType("fieldType1");

		inputFields.add(field);
		inputFields.add(field1);

		fields.setInputFields(inputFields);
		flow.setFields(fields);

		FlowAttributes attribs = new FlowAttributes();
		attribs.addFlowAttribute(FlowAttribute.INCLUDE_BEFORE_IMAGE);
		attribs.addFlowAttribute(FlowAttribute.PROCESS_ONLY_UPDATES);
		attribs.addFlowAttribute(FlowAttribute.INCLUDE_BEFORE_IMAGE);
		flow.setAttributes(attribs);

		String flowXml = CacheManager.flowToXML(flow);

		assertFalse(flowXml.contains(CommonStrings.FLOW_EmptyAttribs));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Attribute_InclB4Image));
		assertTrue(flowXml
				.contains(CommonStrings.FLOW_Attribute_PrcssOnlyUpdates));
		assertTrue(flowXml.contains(CommonStrings.FLOW_BaseEvent));
		assertTrue(flowXml.contains(CommonStrings.FLOW_IsComponentService));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_DisplayName));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldName));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldType));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_selected));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_DisplayName1));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldName1));
		assertTrue(flowXml.contains(CommonStrings.FLOW_Field_FieldType1));
		assertTrue(flowXml.contains(CommonStrings.FLOW_FlowName));
	}

	@Before
	public void setup() {
		cacheManager = new CacheManager();
	}
}

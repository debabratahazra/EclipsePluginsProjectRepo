package com.odcgroup.t24.enquiry.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class EMUtilsTest {
	
	
	
	@Test
	public void testGetPropertyByName() {
		List<EMProperty> properties = createProperties();
		
		// Check it handles lookups of simple fields
		EMProperty property = EMUtils.getPropertyByName(properties, "MetricType");
		assertEquals("{MetricType, METRIC.TYPE}", property.toString());
		
		// Check it handles lookups of multivalue groups
		property = EMUtils.getPropertyByName(properties, "ThresholdTypeMvGroup");
		assertEquals("{ThresholdTypeMvGroup}", property.toString());
		
		// Check it handles lookups of multivalue fields
		property = EMUtils.getPropertyByName(properties, "Threshold");
		assertEquals("{Threshold, THRESHOLD, TERM_VALUE_TYPE=INTEGER_NUMBER}", property.toString());

		// Check it handles lookups of subvalue properties
		property = EMUtils.getPropertyByName(properties, "Subscriber");
		assertEquals("{Subscriber, SUBSCRIBER}", property.toString());
	}
	

	@Test
	public void testGetPropertyByT24Name() {
		List<EMProperty> properties = createProperties();
		
		// Check it handles lookups of simple fields
		EMProperty property = EMUtils.getPropertyByT24Name(properties, "METRIC.TYPE");
		assertEquals("{MetricType, METRIC.TYPE}", property.toString());
		
		// Check it handles lookups of multivalue fields
		property = EMUtils.getPropertyByT24Name(properties, "THRESHOLD");
		assertEquals("{Threshold, THRESHOLD, TERM_VALUE_TYPE=INTEGER_NUMBER}", property.toString());

		// Check it handles lookups of subvalue properties
		property = EMUtils.getPropertyByT24Name(properties, "SUBSCRIBER");
		assertEquals("{Subscriber, SUBSCRIBER}", property.toString());
	}
	
	
	
	/**
	 * Test that empty strings are handled correctly by testUpperInitialCharacter and don't throw exception
	 */
	@Test
	public void testUpperInitialCharacter() {
		String source = "";
		String actual = EMUtils.upperInitialCharacter(source);
		assertEquals("", actual);
	}
	
	

	/**
	 * Set up a list of properties such as an entity might have, that contains some fields, and a multivalue group with a subvalue.
		<Property Name="Area" t24Name="AREA" />
		<Property Name="MetricType" t24Name="METRIC.TYPE" />
		<Property Name="ThresholdTypeMvGroup">
			<Property Name="ThresholdType" t24Name="THRESHOLD.TYPE" />
			<Property Name="Threshold" t24Name="THRESHOLD" >
				<Term Name="TERM_VALUE_TYPE">INTEGER_NUMBER</Term>
			</Property>
			<Property Name="SubscriberSvGroup">
				<Property Name="Subscriber" t24Name="SUBSCRIBER" />
			</Property>
		</Property>
	 * @return
	 */
	private List<EMProperty> createProperties() {
		List<EMProperty> properties = new ArrayList<EMProperty>();

		EMProperty Area = new EMProperty("Area");
		Area.setT24Name("AREA");
		properties.add(Area);
		
		EMProperty MetricType = new EMProperty("MetricType");
		MetricType.setT24Name("METRIC.TYPE");
		properties.add(MetricType);
		
		EMProperty ThresholdTypeMvGroup = new EMProperty("ThresholdTypeMvGroup");
		properties.add(ThresholdTypeMvGroup);

		EMProperty ThresholdType = new EMProperty("ThresholdType");
		ThresholdType.setT24Name("THRESHOLD.TYPE");
		ThresholdTypeMvGroup.addSubProperty(ThresholdType);
		
		EMProperty Threshold = new EMProperty("Threshold");
		Threshold.setT24Name("THRESHOLD");
		Threshold.addVocabularyTerm(new EMTerm(EMTermType.TYPE_TERM, "INTEGER_NUMBER"));
		ThresholdTypeMvGroup.addSubProperty(Threshold);
		
		EMProperty SubscriberSvGroup = new EMProperty("SubscriberSvGroup");
		ThresholdTypeMvGroup.addSubProperty(SubscriberSvGroup);
		
		EMProperty Subscriber = new EMProperty("Subscriber");
		Subscriber.setT24Name("SUBSCRIBER");
		SubscriberSvGroup.addSubProperty(Subscriber);
		
		return properties;
	}
	
	@Test
	public void testT24FieldNameToCamelCaseWithNumber() {
		String t24Field = "AA.ARRANGEMENT.ACTIVITY,TCIB.RETAIL.DEPOSIT.3M";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("AaArrangementActivity_TcibRetailDeposit3m", result);
	}

	@Test
	public void testT24FieldNameToCamelCaseSuccessUpperDot() 
	{
		String t24Field = "ACCOUNT.BAL";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("AccountBal", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseSuccessUpperHyphen() 
	{
		String t24Field = "ACCOUNT-BAL";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("AccountBal", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseSuccessLowerDot() 
	{
		String t24Field = "account.bal";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("AccountBal", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseSuccessLowerHypphen() 
	{
		String t24Field = "account-bal";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("AccountBal", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseSuccessUpperDotHyphen() 
	{
		String t24Field = "EB.CHANNEL-LIST";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("EbChannelList", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseSuccessVersion() 
	{
		String t24Field = "EB.CHANNEL-LIST,TEST";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("EbChannelList_Test", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseSuccessComplex() 
	{
		String t24Field = "EB.CHANNEL-LIST,TEST-MY,VERSION.END";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("EbChannelList_TestMy_VersionEnd", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseEmptyString() 
	{
		String t24Field = "";
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("", result);
	}
	
	@Test
	public void testT24FieldNameToCamelCaseNull() 
	{
		String t24Field = null;
		String result = EMUtils.camelCaseName(t24Field);
		
		assertEquals("", result);
	}

	@Test
	public void testT24FieldNameWithDoubleUnderscore()
	{
	     
	    String t24Field = "H_A_1__2";
        String result = EMUtils.camelCaseName(t24Field);
        
        assertEquals("HA12", result);
	}

	@Test
	public void testEMPropertyListEqual() {
		List<EMProperty> emPropertyList1 = createProperties();
		List<EMProperty> emPropertyList2 = createProperties();
		Iterator<EMProperty> iter1 = emPropertyList1.iterator();
		Iterator<EMProperty> iter2 = emPropertyList2.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			assertTrue(iter1.next().equals(iter2.next()));
		}
	}
	
	@Test
	public void testEMPropertyEqual1() {
		EMProperty Area1 = new EMProperty("Area");
		Area1.setT24Name("AREA");
		EMProperty Area2 = new EMProperty("Area");
		Area2.setT24Name("AREA");
		assertTrue(Area1.equals(Area2));	
	}
	
	@Test
	public void testEMPropertyEqual2() {
		EMProperty Area1 = new EMProperty("Area");
		Area1.setT24Name("AREA");
		EMProperty Area2 = new EMProperty("Area");
		Area2.setT24Name("AREA1");
		assertFalse(Area1.equals(Area2));	
	}
	
	@Test
	public void testEMPropertyEqual3() {
		EMProperty Area1 = new EMProperty("Area");
		Area1.setT24Name("AREA");
		Iterator<EMProperty> iter1 = createProperties().iterator();
		while (iter1.hasNext()) {
			Area1.addSubProperty(iter1.next());
		}
		
		EMProperty Area2 = new EMProperty("Area");
		Area2.setT24Name("AREA");
		Iterator<EMProperty> iter2 = createProperties().iterator();
		while (iter2.hasNext()) {
			Area2.addSubProperty(iter2.next());
		}

		assertTrue(Area1.equals(Area2));	
	}
	
	@Test
	public void testEMPropertyEqual4() {
		EMProperty Area1 = new EMProperty("Area");
		Area1.setT24Name("AREA");
		Iterator<EMProperty> iter1 = createProperties().iterator();
		while (iter1.hasNext()) {
			Area1.addSubProperty(iter1.next());
		}
		
		EMProperty Area2 = new EMProperty("Area");
		Area2.setT24Name("AREA");
		Iterator<EMProperty> iter2 = createProperties().iterator();
		while (iter2.hasNext()) {
			Area2.addSubProperty(iter2.next());
		}
		
		Iterator<EMProperty> iter3 = createProperties().iterator();
		while (iter3.hasNext()) {
			Area2.addSubProperty(iter3.next());
		}
		assertFalse(Area1.equals(Area2));	
	}
}

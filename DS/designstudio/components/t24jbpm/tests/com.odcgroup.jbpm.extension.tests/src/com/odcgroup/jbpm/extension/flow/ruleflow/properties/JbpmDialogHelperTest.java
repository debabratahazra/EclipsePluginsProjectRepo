package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.junit.Test;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Test class for JbpmDialogHelper
 *
 * @author gasampong
 *
 */
public class JbpmDialogHelperTest  extends AbstractDSUnitTest{
	
	@Test
	public void testDurationValidation(){
		assertFalse(JbpmDialogHelper.isDurationValid("0"));
		assertFalse(JbpmDialogHelper.isDurationValid("00"));	
		assertFalse(JbpmDialogHelper.isDurationValid("00:00"));
		assertFalse(JbpmDialogHelper.isDurationValid("00:00:00"));	
		assertFalse(JbpmDialogHelper.isDurationValid("00:00:00:aa"));	
		assertFalse(JbpmDialogHelper.isDurationValid("00:00:00:00:00"));	
		assertTrue(JbpmDialogHelper.isDurationValid("00:00:00:00"));	
		assertTrue(JbpmDialogHelper.isDurationValid("01:02:03:04"));	
		assertTrue(JbpmDialogHelper.isDurationValid("1:02:03:04"));		
		assertTrue(JbpmDialogHelper.isDurationValid("1:2:03:04"));			
		assertTrue(JbpmDialogHelper.isDurationValid("01:19:03:04"));
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:03:04"));	
		assertFalse(JbpmDialogHelper.isDurationValid("01:24:03:04"));	
		assertFalse(JbpmDialogHelper.isDurationValid("01:230:03:04"));			
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:09:04"));		
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:19:04"));
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:29:04"));	
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:39:04"));	
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:49:04"));	
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:59:04"));	
		assertFalse(JbpmDialogHelper.isDurationValid("01:23:69:04"));
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:09:19"));	
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:9:19"));			
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:09:29"));
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:09:39"));
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:09:49"));	
		assertTrue(JbpmDialogHelper.isDurationValid("01:23:09:59"));
		assertTrue(JbpmDialogHelper.isDurationValid("99:23:09:9"));		
		assertFalse(JbpmDialogHelper.isDurationValid("01:23:09:69"));		
		assertFalse(JbpmDialogHelper.isDurationValid("01:23:234:04"));		
		assertFalse(JbpmDialogHelper.isDurationValid("01:23:09:123"));			
	}
	
	@Test
	public void testSpecialCharactersValidation(){
		assertTrue(JbpmDialogHelper.containsInValidCharacter("^"));
		assertTrue(JbpmDialogHelper.containsInValidCharacter("a^"));
		assertTrue(JbpmDialogHelper.containsInValidCharacter("a^a"));
		assertTrue(JbpmDialogHelper.containsInValidCharacter("="));
		assertTrue(JbpmDialogHelper.containsInValidCharacter("123="));
		assertTrue(JbpmDialogHelper.containsInValidCharacter("test=hello"));
		assertTrue(JbpmDialogHelper.containsInValidCharacter("test1=1^test2=2"));		
		assertFalse(JbpmDialogHelper.containsInValidCharacter("hello"));
		assertFalse(JbpmDialogHelper.containsInValidCharacter("hello world"));		
	}
	
	@Test
	public void testGetTaskAssignedGroupId(){
		HumanTaskNodeWrapper wrapper = new HumanTaskNodeWrapper();		
		wrapper.setPropertyValue("GroupId","CUSTOMER.SERVICE.AGENT");		
		assertEquals("CUSTOMER.SERVICE.AGENT",
				JbpmDialogHelper.getTaskAssignedGroupId(wrapper));
	}
	
	private void parsingTest(String value,String expectedT24Target,String expectedEnqVars,
			String[] exptectedValuesArray,Map<String, String> expectedFieldmappings,
			Map<String, String> expectedEnqVarsFieldmappings){
		
		//check initial parse
		String temp[] = JbpmDialogHelper.parseValue(value);	
		String actualT24Target = temp[0];
		String actualEnqVars = temp[1];				
		assertEquals(expectedT24Target,actualT24Target);
		assertEquals(expectedEnqVars,actualEnqVars);
		
		//check further parsing of t24 target string and field mappings	
		String[] actualValuesArray = expectedT24Target.split(JbpmDialogHelper.delims);	
		assertEquals(exptectedValuesArray.length,actualValuesArray.length);	
		
		//Doing this because assertEquals comparing Object[] is deprecated
		compareStringArrayElements(exptectedValuesArray,actualValuesArray);
		
		Map<String, String> actualfieldmappings = 
				JbpmDialogHelper.getFieldMappings(exptectedValuesArray);
		assertEquals(expectedFieldmappings, actualfieldmappings);		
		
		//Check further parsing of enq vars mapping
		Map<String, String> actualEnqVarsFieldMappings = 
				JbpmDialogHelper.getEnqVarsFieldMappings(expectedEnqVars);		
		assertEquals(expectedEnqVarsFieldmappings,actualEnqVarsFieldMappings);
	}
	
	private void compareStringArrayElements(String[] arrayActual,String[] arrayExpected){
		for(int i = 0; i < arrayActual.length; i++) {
			assertEquals(arrayActual[i],arrayExpected[i]);
		}
	}
	
	@Test
	public void testEmptyFieldMappings(){
		String value = "CR.CONTACT.LOG,TASK I F3";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3";
		String expectedEnqVars = "";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3"};		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				new LinkedHashMap<String,String>(),new LinkedHashMap<String,String>());
	}
	
	@Test
	public void testEmptyFieldMappingsWithSingleEnqVars(){		
		String value = "CR.CONTACT.LOG,TASK I F3 ENQ_VARS={CUSTOMER=John}";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3";
		String expectedEnqVars = "CUSTOMER=John";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3"};
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				new LinkedHashMap<String,String>(),expectedEnqVarsFieldmappings);				
	}	
	
	@Test
	public void testEmptyFieldMappingsWithMultipleEnqVars(){		
		String value = "CR.CONTACT.LOG,TASK I F3 ENQ_VARS={CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs}";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3";
		String expectedEnqVars = "CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3"};	
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.NUMBER", "#{accountNumber}");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				new LinkedHashMap<String,String>(),expectedEnqVarsFieldmappings);				
	}	
	
	@Test
	public void testSingleFieldMappings(){		
		String value = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId}";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId}";
		String expectedEnqVars = "";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3","CONTACT.CLIENT=#{customerId}"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");					
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,new LinkedHashMap<String,String>());				
	}
	
	@Test
	public void testSingleFieldMappingsWithSingleEnqVars(){		
		String value = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ENQ_VARS={CUSTOMER=John}";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId}";
		String expectedEnqVars = "CUSTOMER=John";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3","CONTACT.CLIENT=#{customerId}"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");			
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");			
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,expectedEnqVarsFieldmappings);				
	}	
	
	@Test
	public void testSingleFieldMappingsWithMultipleEnqVars(){		
		String value = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ENQ_VARS={CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs}";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId}";
		String expectedEnqVars = "CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3","CONTACT.CLIENT=#{customerId}"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");			
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.NUMBER", "#{accountNumber}");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,expectedEnqVarsFieldmappings);				
	}		
	
	@Test
	public void testMultipleFieldMappings(){
		String value = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs";
		String expectedEnqVars = "";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3","CONTACT.CLIENT=#{customerId}","ACCOUNT.OFFICER=Joe","Bloggs"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");	
		expectedFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");
						
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,new LinkedHashMap<String,String>());				
	}
	
	@Test
	public void testMultipleFieldMappingsWithSingleEnqVars(){
		String value = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs ENQ_VARS={CUSTOMER=John}";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs";
		String expectedEnqVars = "CUSTOMER=John";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3","CONTACT.CLIENT=#{customerId}","ACCOUNT.OFFICER=Joe","Bloggs"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");	
		expectedFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");			
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,expectedEnqVarsFieldmappings);				
	}
	
	@Test
	public void testMultipleFieldMappingsWithMultipleEnqVars(){
		String value = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs ENQ_VARS={CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs}";
		String expectedT24Target = "CR.CONTACT.LOG,TASK I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs";
		String expectedEnqVars = "CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,TASK","I","F3","CONTACT.CLIENT=#{customerId}","ACCOUNT.OFFICER=Joe","Bloggs"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");	
		expectedFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.NUMBER", "#{accountNumber}");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");		
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,expectedEnqVarsFieldmappings);				
	}		
	
	@Test
	public void testCommaVersion(){
		String value = "CR.CONTACT.LOG, I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs ENQ_VARS={CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs}";
		String expectedT24Target = "CR.CONTACT.LOG, I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs";
		String expectedEnqVars = "CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,","I","F3","CONTACT.CLIENT=#{customerId}","ACCOUNT.OFFICER=Joe","Bloggs"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");	
		expectedFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.NUMBER", "#{accountNumber}");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,expectedEnqVarsFieldmappings);				
	}	
	
	@Test
	/**
	 * By default the ENQ VARS map should be at the end of the comment string, the parsing logic
	 * has been written to handle the scenario whereby the comment string is manually changed
	 * and a field mapping is placed after the ENQ VARS map
	 */
	public void testEnqVarsInBetweenFieldMappings(){
		String value = "CR.CONTACT.LOG, I F3 CONTACT.CLIENT=#{customerId} ENQ_VARS={CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs} ACCOUNT.OFFICER=Joe Bloggs";
		String expectedT24Target = "CR.CONTACT.LOG, I F3 CONTACT.CLIENT=#{customerId} ACCOUNT.OFFICER=Joe Bloggs";
		String expectedEnqVars = "CUSTOMER=John^ACCOUNT.NUMBER=#{accountNumber}^ACCOUNT.OFFICER=Joe Bloggs";
		String[] exptectedValuesArray = {"CR.CONTACT.LOG,","I","F3","CONTACT.CLIENT=#{customerId}","ACCOUNT.OFFICER=Joe","Bloggs"};		
		
		Map<String, String> expectedFieldmappings = new LinkedHashMap<String,String>();
		expectedFieldmappings.put("CONTACT.CLIENT", "#{customerId}");	
		expectedFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		LinkedHashMap<String,String> expectedEnqVarsFieldmappings 
			= new LinkedHashMap<String,String>();
		expectedEnqVarsFieldmappings.put("CUSTOMER", "John");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.NUMBER", "#{accountNumber}");	
		expectedEnqVarsFieldmappings.put("ACCOUNT.OFFICER", "Joe Bloggs");			
		
		parsingTest(value,expectedT24Target,expectedEnqVars,exptectedValuesArray,
				expectedFieldmappings,expectedEnqVarsFieldmappings);				
	}	
}
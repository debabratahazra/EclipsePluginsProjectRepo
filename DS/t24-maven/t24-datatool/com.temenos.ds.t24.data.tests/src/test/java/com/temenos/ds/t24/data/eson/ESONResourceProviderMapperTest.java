package com.temenos.ds.t24.data.eson;

import java.util.HashSet;
import java.util.Set;

import javax.jms.IllegalStateException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse.Field;

/**
 * @author yandenmatten
 */
public class ESONResourceProviderMapperTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ESONResourceProviderMapperTest.class);
	
	@Test
	public void testToEsonToGetResourceResponseInvertible_SimpleValues() throws IllegalStateException {
		GetResourceResponse resource = new GetResourceResponse("CUSTOMER");
		resource.fields.add(new Field("MNEMONIC", "BKAMEXGVA"));
		resource.fields.add(new Field("REL.CUSTOMER", ""));
		
		ESONDataModel eson = new ESONResourceProviderMapper().toESON(resource);
		
		Assert.assertEquals(eson.toString(), 
				new ESONParser(
						"import t24.applications.*\n" +
						"CUSTOMER {\n" +
						"MNEMONIC: \"BKAMEXGVA\"\n" +
						"REL.CUSTOMER: \"\"\n" +
						"}").parse().toString());
		
		GetResourceResponse resource2 = new ESONResourceProviderMapper().toGetResourceResponse(eson);
		Assert.assertEquals(resource.resourceName, resource2.resourceName);
		Assert.assertEquals(resource.fields.size(), resource2.fields.size());
		assertSameResource(resource, resource2);
	}

	/*
	 * Based on
	 * FieldDefinitions
	 * ================
	 * Name                |mvGroupName    |svGroupName
	 * -------------------------------------------------
	 * CR.PROFILE.TYPE     |CR.PROFILE.TYPE|           
	 * 
	 * Name                |Value                         |mv |sv 
	 * -----------------------------------------------------------
	 * CR.PROFILE.TYPE     |HOME.OWNERS                   |1  |1  
	 * CR.PROFILE.TYPE     |LOYALTY                       |2  |1  
	 * CR.PROFILE          |APARTMENT.OWNER               |1  |1  
	 * CR.PROFILE          |BLUE                          |2  |1  
	 */
	@Test
	public void testToEsonToGetResourceResponseInvertible_MultiValue() throws IllegalStateException {
		GetResourceResponse resource = new GetResourceResponse("CUSTOMER");
		resource.fields.add(new Field("MNEMONIC", "BKAMEXGVA"));
		resource.fields.add(new Field("CR.PROFILE.TYPE", "HOME.OWNERS",     1, "CR.PROFILE.TYPE"));
		resource.fields.add(new Field("CR.PROFILE.TYPE", "LOYALTY",         2, "CR.PROFILE.TYPE"));
		resource.fields.add(new Field("CR.PROFILE",      "APARTMENT.OWNER", 1, "CR.PROFILE.TYPE"));
		resource.fields.add(new Field("CR.PROFILE",      "BLUE",            2, "CR.PROFILE.TYPE"));
		
		ESONDataModel eson = new ESONResourceProviderMapper().toESON(resource);
		LOGGER.debug(eson.toString());
		
		Assert.assertEquals(new ESONParser(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {" + 
					"MNEMONIC: \"BKAMEXGVA\"" +
					"CR.PROFILE.TYPE: [" +
						"CUSTOMER__CR_PROFILE_TYPE {" +
							"CR.PROFILE.TYPE: \"HOME.OWNERS\" " +
							"CR.PROFILE: \"APARTMENT.OWNER\" " +
						"}" +
						"CUSTOMER__CR_PROFILE_TYPE {" +
							"CR.PROFILE.TYPE: \"LOYALTY\" " +
							"CR.PROFILE: \"BLUE\" " +
						"}" +
					"]" +
				"}").parse().toString(),
				eson.toString());
		
		GetResourceResponse resource2 = new ESONResourceProviderMapper().toGetResourceResponse(eson);
		assertSameResource(resource, resource2);
	}

	/*
	 * Based on
	 * FieldDefinitions
	 * ================
	 * Name                |mvGroupName    |svGroupName
	 * -------------------------------------------------
	 * ADDRESS             |ADDRESS1       |ADDRESS2
	 * 
	 * Name                |Value                         |mv |sv 
	 * -----------------------------------------------------------
	 * ADDRESS             |MY ADDRESS                    |1  |1
	 */
	@Test
	public void testToEsonToGetResourceResponseInvertible_Subvalues() throws IllegalStateException {
		GetResourceResponse resource = new GetResourceResponse("CUSTOMER");
		resource.fields.add(new Field("ADDRESS", "MY ADDRESS", 1, "ADDRESS1", 1, "ADDRESS2"));

		ESONDataModel eson = new ESONResourceProviderMapper().toESON(resource);
		LOGGER.debug(eson.toString());
		
		Assert.assertEquals(new ESONParser(
				"import t24.applications.*\n" +
				"CUSTOMER {\n" +
						"	ADDRESS1: [\n" +
						"		CUSTOMER__ADDRESS1 {\n" +
						"			ADDRESS2: [\n" +
						"				CUSTOMER__ADDRESS1__ADDRESS2 {\n" +
						"					ADDRESS: \"MY ADDRESS\"\n" +
						"				}\n" +
						"			]\n" +
						"		}\n" +
						"	]\n" +
						"}\n").parse().toString(),
				eson.toString());
	}
	
	private void assertSameResource(GetResourceResponse expectedResource, GetResourceResponse actualResource) {
		Assert.assertEquals(expectedResource.resourceName, actualResource.resourceName);
		Assert.assertEquals(expectedResource.fields.size(), actualResource.fields.size());
		Set<Field> expectedFields = new HashSet<Field>();
		expectedFields.addAll(expectedResource.fields);
		for (Field actualField : actualResource.fields) {
			Field expectedFieldFound = null;
			for (Field expectedField: expectedFields) {
				if (StringUtils.equals(actualField.name, expectedField.name) &&
						StringUtils.equals(actualField.value, expectedField.value) &&
						actualField.mv == expectedField.mv &&
						StringUtils.equals(actualField.mvGroupName, expectedField.mvGroupName) &&
						actualField.sv == expectedField.sv &&
						StringUtils.equals(actualField.svGroupName, expectedField.svGroupName)) {
					expectedFieldFound = expectedField;
					break;
				}
			}
			Assert.assertNotNull("The field name:"+actualField.name+", value:"+actualField.value+", mv:"+actualField.mv+", mvGroupName:"+actualField.mvGroupName+", sv:"+actualField.sv+", svGroupName:"+actualField.svGroupName, expectedFieldFound);
			expectedFields.remove(expectedFieldFound);
		}
	}
}

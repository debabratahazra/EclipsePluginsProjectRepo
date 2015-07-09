package com.temenos.ds.t24.data.eson.tests;

import org.junit.Test;

import com.temenos.ds.t24.data.eson.ESONResourceProviderMapper;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse.Field;

/**
 * @author yandenmatten
 */
public class ESONDataModelFactoryForT24Test {
	
	@Test
	public void testFieldWithoutMvSvGroup() {
		GetResourceResponse customer = new GetResourceResponse("CUSTOMER");
		customer.fields.add(new Field("MNEMONIC", "COOPERA"));
		
		AssertESON.assertEqualsNormilizedESON(
				"import t24.applications.* " + 
				"CUSTOMER {" +
				"MNEMONIC: \"COOPERA\"" +
				"}", 
				new ESONResourceProviderMapper().toESON(customer));
	}

	@Test
	public void testFieldWithMvGroupWithoutSvGroup() {
		GetResourceResponse customer = new GetResourceResponse("CUSTOMER");
		customer.fields.add(new Field("CR.PROFILE", "APARTMENT.OWNER", 1, "CR.PROFILE", 1, ""));
		customer.fields.add(new Field("CR.PROFILE", "BLUE", 2, "CR.PROFILE", 1, ""));
		
		AssertESON.assertEqualsNormilizedESON(
				"import t24.applications.* " + 
				"CUSTOMER {" +
					"CR.PROFILE: [ " +
						"CUSTOMER__CR_PROFILE {" +
							"CR.PROFILE: \"APARTMENT.OWNER\"" +
						"}" +
						"CUSTOMER__CR_PROFILE {" +
							"CR.PROFILE: \"BLUE\"" +
						"}" +
					"]" +
				"}", 
				new ESONResourceProviderMapper().toESON(customer));
	}

	@Test
	public void testFieldWithMvGroupWithoutSvGroup_SeveralFields() {
		GetResourceResponse customer = new GetResourceResponse("CUSTOMER");
		customer.fields.add(new Field("CR.PROFILE", "APARTMENT.OWNER", 1, "CR.PROFILE", 1, ""));
		customer.fields.add(new Field("CR.PROFILE", "BLUE", 2, "CR.PROFILE", 1, ""));
		customer.fields.add(new Field("CR.PROFILE.TYPE", "HOME.OWNERS", 1, "CR.PROFILE", 1, ""));
		customer.fields.add(new Field("CR.PROFILE.TYPE", "LOYALTY", 2, "CR.PROFILE", 1, ""));
		
		AssertESON.assertEqualsNormilizedESON(
				"import t24.applications.* " + 
				"CUSTOMER {" +
					"CR.PROFILE: [ " +
						"CUSTOMER__CR_PROFILE {" +
							"CR.PROFILE: \"APARTMENT.OWNER\" " +
							"CR.PROFILE.TYPE: \"HOME.OWNERS\" " +
						"}" +
						"CUSTOMER__CR_PROFILE {" +
							"CR.PROFILE: \"BLUE\" " +
							"CR.PROFILE.TYPE: \"LOYALTY\" " +
						"}" +
					"]" +
				"}", 
				new ESONResourceProviderMapper().toESON(customer));
	}

	@Test(expected=IllegalStateException.class)
	public void testFieldWithMvGroupUnordered() {
		GetResourceResponse customer = new GetResourceResponse("CUSTOMER");
		customer.fields.add(new Field("CR.PROFILE", "BLUE", 2, "CR.PROFILE", 1, ""));
		customer.fields.add(new Field("CR.PROFILE", "APARTMENT.OWNER", 1, "CR.PROFILE", 1, ""));
		
		new ESONResourceProviderMapper().toESON(customer);
	}

	@Test
	public void testFieldWithMvGroupAndSvGroup() {
		GetResourceResponse customer = new GetResourceResponse("CUSTOMER");
		customer.fields.add(new Field("ADDRESS", "My address", 1, "ADDRESS", 1, "ADDRESS"));
		
		AssertESON.assertEqualsNormilizedESON(
				"import t24.applications.* " + 
				"CUSTOMER {" +
					"ADDRESS: [ CUSTOMER__ADDRESS { " + 
						"ADDRESS: [ CUSTOMER__ADDRESS__ADDRESS { " +
							"ADDRESS: \"My address\" " + 
						"} ]" +
					"} ]" +
				"}", 
				new ESONResourceProviderMapper().toESON(customer));
	}

}

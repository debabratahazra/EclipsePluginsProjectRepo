package com.temenos.ds.t24.data.eson.tests;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.eson.ESONDataModel.Feature;
import com.temenos.ds.t24.data.eson.ESONDataModel.MultiValue;
import com.temenos.ds.t24.data.eson.ESONDataModel.NewObject;
import com.temenos.ds.t24.data.eson.ESONDataModelFactory;

/**
 * @author yandenmatten
 */
public class ESONDataModelFactoryTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ESONDataModelFactoryTest.class);

	@Test
	public void testAddImport() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");
		Assert.assertEquals(
				"CUSTOMER {\n" +
				"}\n", 
				factory.getEsonDataModel().toString());
		
		factory.addImportedNamespace("t24.applications.*");
		
		Assert.assertEquals(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {\n" +
				"}\n", 
				factory.getEsonDataModel().toString());
		
		factory.addImportedNamespace("t24.applications.extension.*");
		Assert.assertEquals(
				"import t24.applications.*\n" +
				"import t24.applications.extension.*\n" +
				"\n" +
				"CUSTOMER {\n" +
				"}\n", 
				factory.getEsonDataModel().toString());
		
	}
	
	@Test
	public void testAddFeatureStringValue() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");

		factory.addRootFeature("MNEMONIC", "BKAMEXGVA");
		factory.addRootFeature("SHORT.NAME", "American Express Geneva");
		Assert.assertEquals(
				"CUSTOMER {\n" +
				"	MNEMONIC: \"BKAMEXGVA\"\n" + 
				"	SHORT.NAME: \"American Express Geneva\"\n" +	
				"}\n", 
				factory.getEsonDataModel().toString());
	}
	
	@Test
	public void testAddFeatureMultiValue() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");

		Feature feature = factory.addRootFeature("NAME.1");
		MultiValue multiValue = factory.addMultiValue(feature);
		NewObject newObject = factory.addNewObject(multiValue, "CUSTOMMER__NAME_1");
		Feature feature2 = factory.addFeature(newObject, "NAME_1");
		factory.addStringAttribute(feature2, "American Express Geneva");
		Feature feature3 = factory.addFeature(newObject, "NAME_2");
		factory.addStringAttribute(feature3, "American Express Geneva bis");
		
		Assert.assertEquals(
			"CUSTOMER {\n" +
			"	NAME.1: [\n" +
			"		CUSTOMMER__NAME_1 {\n" +
			"			NAME_1: \"American Express Geneva\"\n" +
			"			NAME_2: \"American Express Geneva bis\"\n" +
			"		}\n" +
			"	]\n" +
			"}\n", 
			factory.getEsonDataModel().toString());
	}
	
	@Test
	public void testAddFeatureMultiValue2() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");

		{
			Feature feature = factory.addRootFeature("NAME.1");
			MultiValue multiValue = factory.addMultiValue(feature);
			{
				NewObject newObject = factory.addNewObject(multiValue, "CUSTOMMER__NAME_1");
				Feature feature2 = factory.addFeature(newObject, "NAME_1");
				factory.addStringAttribute(feature2, "American Express Geneva");
			}
			
			{
				NewObject newObject = factory.addNewObject(multiValue, "CUSTOMMER__NAME_1");
				Feature feature2 = factory.addFeature(newObject, "NAME_1");
				factory.addStringAttribute(feature2, "American Express Geneva");
			}
		}
		
		{
			Feature feature = factory.addRootFeature("NAME.2");
			MultiValue multiValue = factory.addMultiValue(feature);
			{
				NewObject newObject = factory.addNewObject(multiValue, "CUSTOMMER__NAME_1");
				Feature feature2 = factory.addFeature(newObject, "NAME_1");
				factory.addStringAttribute(feature2, "American Express Geneva");
			}
			
			{
				NewObject newObject = factory.addNewObject(multiValue, "CUSTOMMER__NAME_1");
				Feature feature2 = factory.addFeature(newObject, "NAME_1");
				factory.addStringAttribute(feature2, "American Express Geneva");
			}
		}
		
		Assert.assertEquals(
			"CUSTOMER {\n" +
			"	NAME.1: [\n" +
			"		CUSTOMMER__NAME_1 {\n" +
			"			NAME_1: \"American Express Geneva\"\n" +
			"		}\n" +
			"		CUSTOMMER__NAME_1 {\n" +
			"			NAME_1: \"American Express Geneva\"\n" +
			"		}\n" +
			"	]\n" +
			"	NAME.2: [\n" +
			"		CUSTOMMER__NAME_1 {\n" +
			"			NAME_1: \"American Express Geneva\"\n" +
			"		}\n" +
			"		CUSTOMMER__NAME_1 {\n" +
			"			NAME_1: \"American Express Geneva\"\n" +
			"		}\n" +
			"	]\n" +
			"}\n", factory.getEsonDataModel().toString());
	}
	
	@Test
	public void testAddFeatureMultiValue3() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");

		Feature feature = factory.addRootFeature("FAVOURITE_FRUITS");
		MultiValue multiValue = factory.addMultiValue(feature);

		factory.addStringAttribute(multiValue, "APPLE");
		factory.addStringAttribute(multiValue, "PEAR");
		factory.addStringAttribute(multiValue, "PEACH");
		
		Assert.assertEquals(
			"CUSTOMER {\n" +
			"	FAVOURITE_FRUITS: [\"APPLE\" \"PEAR\" \"PEACH\"]\n" +
			"}\n", factory.getEsonDataModel().toString());
	}

	@Test(expected=RuntimeException.class)
	public void testConvertSingleValueToMultiValueMissorderedUnsupported() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");

		factory.addRootFeature("FAVOURITE_FRUITS", "PEAR", 2);
	}
	
	@Test
	public void testConvertSingleValueToMultiValueNotAtRootLevel() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");

		Feature rootFeature = factory.addRootFeature("favourie");
		NewObject fruits = factory.createNewObject("fruits");
		rootFeature.value = fruits;
		
		factory.addFeature(fruits, "PEAR");
		factory.addFeature(fruits, "APPLE");
		factory.addFeature(fruits, "PEACH");
		
		LOGGER.debug(factory.getEsonDataModel().toString());

//		Value newFeatureValue = factory.findExistingFeature("FAVOURITE_FRUITS").value;
//		Assert.assertFalse("Shouldn't be anymore a StringAttribute", newFeatureValue instanceof StringAttribute);
//		Assert.assertTrue("Should be a MultiValue by now", newFeatureValue instanceof MultiValue);
//		MultiValue newFeatureMultiValue = (MultiValue)newFeatureValue;
//		Assert.assertEquals("Wrong first value", "APPLE", ((StringAttribute)newFeatureMultiValue.values.get(0)).value);
//		Assert.assertEquals("Wrong second value", "PEAR", ((StringAttribute)newFeatureMultiValue.values.get(1)).value);
//		Assert.assertEquals("Wrong 3rd value", "PEACH", ((StringAttribute)newFeatureMultiValue.values.get(2)).value);
//		
//		Assert.assertEquals(
//			"CUSTOMER {\n" +
//			"	FAVOURITE_FRUITS: [\"APPLE\" \"PEAR\" \"PEACH\"]\n" +
//			"}\n", factory.getEsonDataModel().toString());
//
//		
//		ESONDataModelFactory factory = new ESONDataModelFactory();
//		factory.setRootEClass("CUSTOMER");
//		Feature feature = factory.addRootFeature("TODO");
//
//		// TODO
//		
//		// No multi value created up to this point
////		factory.addStringValue(feature, "FAVOURITE_FRUITS", "PEAR", 2);
////		factory.addStringValue(feature, "FAVOURITE_FRUITS", "PEACH", 3);
//		
//		Assert.assertEquals(
//			"CUSTOMER {\n" +
//			"	FAVOURITE_FRUITS: [\"APPLE\" \"PEAR\" \"PEACH\"]\n" +
//			"}\n", factory.getEsonDataModel().toString());
	}

	@Test
	public void testAddSubValues() {
		ESONDataModelFactory factory = new ESONDataModelFactory();
		factory.setRootEClass("CUSTOMER");

		Feature feature = factory.addRootFeature("ADDRESS1");
		MultiValue multiValue = factory.addMultiValue(feature);
		NewObject newObject = factory.addNewObject(multiValue, "CUSTOMER__ADDRESS");
		Feature feature2 = factory.addFeature(newObject, "ADDRESS2");
		MultiValue multiValue2 = factory.addMultiValue(feature2);
		NewObject newObject2 = factory.addNewObject(multiValue2, "CUSTOMER__ADDRESS__ADDRESS");
		Feature feature3 = factory.addFeature(newObject2, "ADDRESS3");
		factory.addStringAttribute(feature3, "MY ADDRESS");
		
		Assert.assertEquals(
			"CUSTOMER {\n" +
			"	ADDRESS1: [\n" +
			"		CUSTOMER__ADDRESS {\n" +
			"			ADDRESS2: [\n" +
			"				CUSTOMER__ADDRESS__ADDRESS {\n" +
			"					ADDRESS3: \"MY ADDRESS\"\n" +
			"				}\n" +
			"			]\n" +
			"		}\n" +
			"	]\n" +
			"}\n", factory.getEsonDataModel().toString());
	}
}

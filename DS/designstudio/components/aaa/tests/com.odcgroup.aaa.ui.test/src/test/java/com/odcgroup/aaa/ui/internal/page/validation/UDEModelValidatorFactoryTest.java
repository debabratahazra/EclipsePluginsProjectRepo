package com.odcgroup.aaa.ui.internal.page.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.ecore.ECoreModelFactory;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfReverseAssociationImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

public class UDEModelValidatorFactoryTest {
	
	private MdfDomain testDomain;
	
	@Before
	public void setUp() {
		testDomain = MdfFactory.eINSTANCE.createMdfDomain();
	}
	
	/**
	 * Test that all sql name are retrived by getSqlName. Adding a new supported to type in this method will
	 * lead to upate to this test method. 
	 */
	@Test
	public void testGetSqlName() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("SomeClass");
		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		MdfEnumValueImpl enumValue = createMdfEnumValue(mdfEnum, "SomeEnumName", "123");
		MdfAttributeImpl attribute = addMdfAttribute(mdfClass, "attribute", mdfEnum);
		MdfAssociationImpl association = addMdfAssociation(mdfClass, "association", mdfClass, MdfConstants.MULTIPLICITY_MANY);
		MdfReverseAssociationImpl reverse = addMdfReverse(mdfClass, association);
		MdfDataset mdfDataset = createDataset("SomeDataset", mdfClass);
		MdfDatasetProperty mappedDatasetProperty = addDatasetMappedProperty(mdfDataset, "someDatasetProperty", "attribute");
		MdfDatasetProperty derivedDatasetProperty = addDatasetDerivedProperty(mdfDataset, "someDatasetProperty", PrimitivesDomain.BOOLEAN);
		MdfBusinessTypeImpl businessType = createMdfBusinessType("SomeBT", PrimitivesDomain.BOOLEAN);
		
		// When
		UDEModelValidatorFactory udeModelValidatorFactory = new UDEModelValidatorFactory() {
			@Override
			protected void addIfRelevant(List<String> list, String item) {
				list.add("dummy");
			}
		};
		List<String> sqlNameMdfClass = udeModelValidatorFactory.getSqlName(mdfClass);
		List<String> sqlNameMdfEnum = udeModelValidatorFactory.getSqlName(mdfEnum);
		List<String> sqlNameMdfEnumValue = udeModelValidatorFactory.getSqlName(enumValue);
		List<String> sqlNameAttribute = udeModelValidatorFactory.getSqlName(attribute);
		List<String> sqlNameAssociation = udeModelValidatorFactory.getSqlName(association);
		List<String> sqlNameReverse = udeModelValidatorFactory.getSqlName(reverse);
		List<String> sqlNameMdfDataset = udeModelValidatorFactory.getSqlName(mdfDataset);
		List<String> sqlNameMdfMappedDatasetProperty = udeModelValidatorFactory.getSqlName(mappedDatasetProperty);
		List<String> sqlNameMdfDerivedDatasetProperty = udeModelValidatorFactory.getSqlName(derivedDatasetProperty);
		List<String> sqlNameBusinessType = udeModelValidatorFactory.getSqlName(businessType);
		
		// Then
		Assert.assertEquals(1, sqlNameMdfClass.size());
		Assert.assertEquals(2, sqlNameMdfEnum.size());
		Assert.assertEquals(0, sqlNameMdfEnumValue.size());
		Assert.assertEquals(1, sqlNameAttribute.size());
		Assert.assertEquals(3, sqlNameAssociation.size());
		Assert.assertEquals(0, sqlNameReverse.size());
		Assert.assertEquals(0, sqlNameMdfDataset.size());
		Assert.assertEquals(0, sqlNameMdfMappedDatasetProperty.size());
		Assert.assertEquals(0, sqlNameMdfDerivedDatasetProperty.size());
		Assert.assertEquals(0, sqlNameBusinessType.size());
	}

	@Test
	public void testValidateSQLNameConstraints_maxLength() {
		// Given
		MdfClass original = createMdfClass("SomeClass");
		MdfClass workingCopy = createMdfClass("SomeClass");
		
		// When
		String tooLong = StringUtils.leftPad("", UDEModelValidatorFactory.MAX_LENGTH_SQL_NAME+1, 'a');
		AAAAspectDS.setTripleAEntitySQLName(workingCopy, tooLong);
		IStatus status = new UDEModelValidatorFactory().validateSQLNameConstraints(original, workingCopy);
		
		// Then
		Assert.assertFalse("Shouldn't be valid", status.isOK());
		
		// When
		String lengthOk = StringUtils.leftPad("", UDEModelValidatorFactory.MAX_LENGTH_SQL_NAME, 'a');
		AAAAspectDS.setTripleAEntitySQLName(workingCopy, lengthOk);
		status = new UDEModelValidatorFactory().validateSQLNameConstraints(original, workingCopy);
		
		// Then
		Assert.assertTrue("Should be valid", status.isOK());
	}
	
	@Test
	public void testValidateSQLNameConstraints_lowercaseChar() {
		// Given
		MdfClass original = createMdfClass("SomeClass");
		MdfClass workingCopy = createMdfClass("SomeClass");
		
		// Failures expected
		for (String failingString: new String[] {
				"Abc", "abc$", "?", "\u0092"}) {
			// When
			AAAAspectDS.setTripleAEntitySQLName(workingCopy, failingString);
			IStatus status = new UDEModelValidatorFactory().validateSQLNameConstraints(original, workingCopy);
			
			// Then
			Assert.assertFalse("Shouldn't be valid", status.isOK());
		}
		
		// Successes expected
		for (String okString: new String[] {
				"abc", "123", "ab12", "12ab", "_", "_12", "12_", "ab_12"
		}) {
			// When
			AAAAspectDS.setTripleAEntitySQLName(workingCopy, okString);
			IStatus status = new UDEModelValidatorFactory().validateSQLNameConstraints(original, workingCopy);
			
			// Then
			Assert.assertTrue("Should be valid", status.isOK());
		}
	}
	
	@Test
	public void testValidateSQLNameConstraints_sqlReservedKeywords() {
		// Given
		MdfClass original = createMdfClass("SomeClass");
		MdfClass workingCopy = createMdfClass("SomeClass");
		
		// Failures expected
		for (String failingString: new String[] {
				"select", "delete", "from", "to", "update"}) {
			// When
			AAAAspectDS.setTripleAEntitySQLName(workingCopy, failingString);
			IStatus status = new UDEModelValidatorFactory().validateSQLNameConstraints(original, workingCopy);
			
			// Then
			Assert.assertFalse("Shouldn't be valid", status.isOK());
		}
		
		// Successes expected
		for (String okString: new String[] {
				"toto", "portfolio", "test", "client"
		}) {
			// When
			AAAAspectDS.setTripleAEntitySQLName(workingCopy, okString);
			IStatus status = new UDEModelValidatorFactory().validateSQLNameConstraints(original, workingCopy);
			
			// Then
			Assert.assertTrue("Should be valid", status.isOK());
		}
	}
	
	@Test
	public void testValidateUseOnlyBusinessType() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("TestClass");
		MdfAttributeImpl primAttribute = addMdfAttribute(mdfClass, "primAttribute", PrimitivesDomain.INTEGER);
		MdfBusinessTypeImpl businessType = createMdfBusinessType("someBT", PrimitivesDomain.INTEGER);
		MdfAttributeImpl btypeAttribute = addMdfAttribute(mdfClass, "btypeAttribute", businessType);
		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		MdfAttributeImpl enumAttribute = addMdfAttribute(mdfClass, "enumAttribute", mdfEnum);
		
		// When
		UDEModelValidatorFactory factory = new UDEModelValidatorFactory();
		IStatus statusPrim = factory.validateUseOnlyBusinessType(primAttribute, primAttribute);
		IStatus statusBType = factory.validateUseOnlyBusinessType(btypeAttribute, btypeAttribute);
		IStatus statusEnum = factory.validateUseOnlyBusinessType(enumAttribute, enumAttribute);
		
		// Then
		Assert.assertFalse("integer type for an attribute should be invalid", statusPrim.isOK());
		Assert.assertTrue("business type for an attribute should be valid", statusBType.isOK());
		Assert.assertTrue("enum type for an attribute should be valid", statusEnum.isOK());
	}

	@Test
	public void testValidateSuffixUsage() {
		// Given
		MdfEnumerationImpl boolEnum = createMdfEnum(PrimitivesDomain.BOOLEAN);
		MdfEnumerationImpl intEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		
		MdfClassImpl mdfClass = createMdfClass("SomeClass");
		MdfAttributeImpl boolEnumAttribute = addMdfAttribute(mdfClass, "boolEnumAttribute", boolEnum);
		AAAAspectDS.setTripleAAttributeSQLName(boolEnumAttribute, "invalid");
		MdfAttributeImpl intEnumAttribute = addMdfAttribute(mdfClass, "intEnumAttribute", intEnum);
		AAAAspectDS.setTripleAAttributeSQLName(intEnumAttribute, "invalid");
		
		MdfBusinessType btypeNumber = createMdfBusinessType("BType", PrimitivesDomain.INTEGER);
		AAAAspectDS.setTripleABusinessType(btypeNumber, "number_t");
		MdfAttributeImpl btIntAttribute = addMdfAttribute(mdfClass, "btIntAttribute", btypeNumber);
		AAAAspectDS.setTripleAAttributeSQLName(btIntAttribute, "invalid");
		
		MdfBusinessType btypeStr = createMdfBusinessType("BType", PrimitivesDomain.STRING);
		MdfAttributeImpl btStrAttribute = addMdfAttribute(mdfClass, "btStrAttribute", btypeStr);
		AAAAspectDS.setTripleAAttributeSQLName(btStrAttribute, "invalid");
		
		MdfAssociationImpl associationRef = addMdfAssociation(mdfClass, "associationRef", mdfClass, MdfConstants.MULTIPLICITY_MANY);
		associationRef.setContainment(MdfConstants.CONTAINMENT_BYREFERENCE);
		AAAAspectDS.setTripleAAttributeSQLName(associationRef, "invalid");
		
		// When
		IStatus boolEnumAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(boolEnumAttribute, boolEnumAttribute);
		IStatus intEnumAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(intEnumAttribute, intEnumAttribute);
		IStatus btIntAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(btIntAttribute, btIntAttribute);
		//IStatus btStrAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(btStrAttribute, btStrAttribute);
		IStatus associationRefStatus = new UDEModelValidatorFactory().validateSuffixUsage(associationRef, associationRef);
		
		// Then
		Assert.assertFalse("Shouldn't be valid", boolEnumAttributeStatus.isOK());
		Assert.assertFalse("Shouldn't be valid", intEnumAttributeStatus.isOK());
		Assert.assertFalse("Shouldn't be valid", btIntAttributeStatus.isOK());
		//Assert.assertFalse("Shouldn't be valid", btStrAttributeStatus.isOK());
		Assert.assertFalse("Shouldn't be valid", associationRefStatus.isOK());
		
		// Given
		AAAAspectDS.setTripleAAttributeSQLName(boolEnumAttribute, "test_f");
		AAAAspectDS.setTripleAAttributeSQLName(intEnumAttribute, "test_e");
		AAAAspectDS.setTripleAAttributeSQLName(btIntAttribute, "test_n");
		AAAAspectDS.setTripleAAttributeSQLName(btStrAttribute, "test_c");
		AAAAspectDS.setTripleAAttributeSQLName(associationRef, "assoc_id");

		MdfAssociationImpl associationValue = addMdfAssociation(mdfClass, "associationValue", mdfClass, MdfConstants.MULTIPLICITY_MANY);
		associationValue.setContainment(MdfConstants.CONTAINMENT_BYVALUE);
		AAAAspectDS.setTripleAAttributeSQLName(associationValue, "ok");

		// When
		boolEnumAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(boolEnumAttribute, boolEnumAttribute);
		intEnumAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(intEnumAttribute, intEnumAttribute);
		btIntAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(btIntAttribute, btIntAttribute);
		//btStrAttributeStatus = new UDEModelValidatorFactory().validateSuffixUsage(btStrAttribute, btStrAttribute);
		associationRefStatus = new UDEModelValidatorFactory().validateSuffixUsage(associationRef, associationRef);
		IStatus associationValueStatus = new UDEModelValidatorFactory().validateSuffixUsage(associationValue, associationValue);
		
		// Then
		Assert.assertTrue("Should be valid", boolEnumAttributeStatus.isOK());
		Assert.assertTrue("Should be valid", intEnumAttributeStatus.isOK());
		Assert.assertTrue("Should be valid", btIntAttributeStatus.isOK());
		//Assert.assertTrue("Should be valid", btStrAttributeStatus.isOK());
		Assert.assertTrue("Should be valid", associationRefStatus.isOK());
		Assert.assertTrue("Should be valid", associationValueStatus.isOK());
	}

	@Test
	public void testvalidatePrimaryKey() {
		// Given
		MdfBusinessType btypeNumber = createMdfBusinessType("BType", PrimitivesDomain.INTEGER);

		MdfClassImpl mdfClass = createMdfClass("SomeClass");
		MdfAttributeImpl pkInvalid = addMdfAttribute(mdfClass, "pk", btypeNumber);
		pkInvalid.setPrimaryKey(true);
		AAAAspectDS.setTripleAAttributeSQLName(pkInvalid, "invalid");

		MdfClassImpl mdfClass2 = createMdfClass("SomeClass2");
		MdfAttributeImpl pkValid = addMdfAttribute(mdfClass2, "pk", btypeNumber);
		pkValid.setPrimaryKey(true);
		AAAAspectDS.setTripleAAttributeSQLName(pkValid, "id");

		MdfClassImpl mdfClass3 = createMdfClass("SomeClass2");
		MdfAttributeImpl notPkValid = addMdfAttribute(mdfClass3, "notPk", btypeNumber);
		notPkValid.setPrimaryKey(false);
		AAAAspectDS.setTripleAAttributeSQLName(notPkValid, "notPk");

		// When
		IStatus status1 = new UDEModelValidatorFactory().validatePrimaryKey(pkInvalid, pkInvalid);
		IStatus status2 = new UDEModelValidatorFactory().validatePrimaryKey(pkValid, pkValid);
		IStatus status3 = new UDEModelValidatorFactory().validatePrimaryKey(notPkValid, notPkValid);
		
		// Then
		Assert.assertFalse("Should be invalid", status1.isOK());
		Assert.assertTrue("Should be valid", status2.isOK());
		Assert.assertTrue("Should be valid", status3.isOK());
	}

	@Test
	public void testValidateRequired() {
		// Given
		MdfBusinessType btypeNumber = createMdfBusinessType("BType", PrimitivesDomain.INTEGER);
		MdfEnumerationImpl intEnum = createMdfEnum(PrimitivesDomain.INTEGER);

		MdfClassImpl mdfClass = createMdfClass("SomeClass");
		MdfAttributeImpl pkInvalid = addMdfAttribute(mdfClass, "pk", btypeNumber);
		pkInvalid.setPrimaryKey(true);
		pkInvalid.setRequired(false);
		MdfAttributeImpl enumInvalid = addMdfAttribute(mdfClass, "enumAttrib", intEnum);
		enumInvalid.setRequired(false);

		MdfClassImpl mdfClass2 = createMdfClass("SomeClass2");
		MdfAttributeImpl pkValid = addMdfAttribute(mdfClass2, "pk", btypeNumber);
		pkValid.setPrimaryKey(true);
		pkValid.setRequired(true);
		MdfAttributeImpl enumValid = addMdfAttribute(mdfClass, "enumAttrib", intEnum);
		enumValid.setRequired(true);
		MdfAttributeImpl attributeValid = addMdfAttribute(mdfClass, "attribute", btypeNumber);
		attributeValid.setRequired(false);

		// When
		IStatus statusPkInvalid = new UDEModelValidatorFactory().validateRequired(pkInvalid, pkInvalid);
		IStatus statusPkValid = new UDEModelValidatorFactory().validateRequired(pkValid, pkValid);
		IStatus statusEnumInvalid = new UDEModelValidatorFactory().validateRequired(enumInvalid, enumInvalid);
		IStatus statusEnumValid = new UDEModelValidatorFactory().validateRequired(enumValid, enumValid);
		IStatus statusAttributeValid = new UDEModelValidatorFactory().validateRequired(attributeValid, attributeValid);
		
		// Then
		Assert.assertFalse("Should be invalid", statusPkInvalid.isOK());
		Assert.assertTrue("Should be valid", statusPkValid.isOK());
		Assert.assertFalse("Should be invalid", statusEnumInvalid.isOK());
		Assert.assertTrue("Should be valid", statusEnumValid.isOK());
		Assert.assertTrue("Should be valid", statusAttributeValid.isOK());
	}

	@Test
	public void testValidateMandatory() {
		// Given
		MdfClassImpl mdfClassInvalid1 = createMdfClass("SomeClass1");
		MdfClassImpl mdfClassInvalid2 = createMdfClass("SomeClass2");
		AAAAspectDS.setTripleAEntitySQLName(mdfClassInvalid2, "someValue");
		MdfClassImpl mdfClassInvalid3 = createMdfClass("SomeClass3");
		AAAAspectDS.setTripleAEntityName(mdfClassInvalid3, "someValue");
		MdfClassImpl mdfClassValid = createMdfClass("SomeClass4");
		AAAAspectDS.setTripleAEntitySQLName(mdfClassValid, "someValue");
		AAAAspectDS.setTripleAEntityName(mdfClassValid, "someValue");
		
		MdfAttributeImpl attributeInvalid1 = addMdfAttribute(mdfClassValid, "attributeInvalid1", PrimitivesDomain.INTEGER);
		MdfAttributeImpl attributeInvalid2 = addMdfAttribute(mdfClassValid, "attributeInvalid2", PrimitivesDomain.INTEGER);
		AAAAspectDS.setTripleAAttributeSQLName(attributeInvalid2, "someValue");
		MdfAttributeImpl attributeInvalid3 = addMdfAttribute(mdfClassValid, "attributeInvalid3", PrimitivesDomain.INTEGER);
		AAAAspectDS.setTripleAAttributeName(attributeInvalid3, "someValue");
		MdfAttributeImpl attributeValid = addMdfAttribute(mdfClassValid, "attributeValid", PrimitivesDomain.INTEGER);
		AAAAspectDS.setTripleAAttributeSQLName(attributeValid, "someValue");
		AAAAspectDS.setTripleAAttributeName(attributeValid, "someValue");
		
		MdfAssociationImpl associationInvalid1 = addMdfAssociation(mdfClassValid, "associationInvalid1", mdfClassValid, MdfConstants.MULTIPLICITY_ONE);
		MdfAssociationImpl associationInvalid2 = addMdfAssociation(mdfClassValid, "associationInvalid2", mdfClassValid, MdfConstants.MULTIPLICITY_ONE);
		AAAAspectDS.setTripleAAttributeSQLName(associationInvalid2, "someValue"); 
		MdfAssociationImpl associationInvalid3 = addMdfAssociation(mdfClassValid, "associationInvalid3", mdfClassValid, MdfConstants.MULTIPLICITY_ONE);
		AAAAspectDS.setTripleAAttributeName(associationInvalid3, "someValue");
		MdfAssociationImpl associationValid = addMdfAssociation(mdfClassValid, "associationValid", mdfClassValid, MdfConstants.MULTIPLICITY_ONE);
		AAAAspectDS.setTripleAAttributeSQLName(associationValid, "someValue"); 
		AAAAspectDS.setTripleAAttributeName(associationValid, "someValue");
		
		MdfEnumerationImpl enumInvalid1 = createMdfEnum(PrimitivesDomain.BOOLEAN);
		MdfEnumerationImpl enumInvalid2 = createMdfEnum(PrimitivesDomain.BOOLEAN);
		AAAAspectDS.setTripleAEntitySQLName(enumInvalid2, "someValue");
		MdfEnumerationImpl enumInvalid3 = createMdfEnum(PrimitivesDomain.BOOLEAN);
		AAAAspectDS.setTripleAAttributeSQLName(enumInvalid3, "someValue");
		MdfEnumerationImpl enumValid = createMdfEnum(PrimitivesDomain.BOOLEAN);
		AAAAspectDS.setTripleAEntitySQLName(enumValid, "someValue");
		AAAAspectDS.setTripleAAttributeSQLName(enumValid, "someValue");
		
		MdfEnumValueImpl enumValueInvalid1 = createMdfEnumValue(enumValid, "name", "true");
		MdfEnumValueImpl enumValueInvalid2 = createMdfEnumValue(enumValid, "name", "true");
		AAAAspectDS.setTripleAPermittedValueName(enumValueInvalid2, "someValue");
		MdfEnumValueImpl enumValueInvalid3 = createMdfEnumValue(enumValid, "name", "true");
		AAAAspectDS.setTripleAPermittedValueRank(enumValueInvalid3, "someValue");
		MdfEnumValueImpl enumValueValid = createMdfEnumValue(enumValid, "name", "true");
		AAAAspectDS.setTripleAPermittedValueName(enumValueValid, "someValue");
		AAAAspectDS.setTripleAPermittedValueRank(enumValueValid, "someValue");
		
		// When
		IStatus statusClassInvalid1 = new UDEModelValidatorFactory().validateMandatory(mdfClassInvalid1, mdfClassInvalid1);
		IStatus statusClassInvalid2 = new UDEModelValidatorFactory().validateMandatory(mdfClassInvalid1, mdfClassInvalid2);
		IStatus statusClassInvalid3 = new UDEModelValidatorFactory().validateMandatory(mdfClassInvalid1, mdfClassInvalid3);
		IStatus statusClassValid = new UDEModelValidatorFactory().validateMandatory(mdfClassValid, mdfClassValid);
		
		IStatus statusAttributeInvalid1 = new UDEModelValidatorFactory().validateMandatory(attributeInvalid1, attributeInvalid1);
		IStatus statusAttributeInvalid2 = new UDEModelValidatorFactory().validateMandatory(attributeInvalid2, attributeInvalid2);
		IStatus statusAttributeInvalid3 = new UDEModelValidatorFactory().validateMandatory(attributeInvalid3, attributeInvalid3);
		IStatus statusAttributeValid = new UDEModelValidatorFactory().validateMandatory(attributeValid, attributeValid);
		
		IStatus statusAssociationInvalid1 = new UDEModelValidatorFactory().validateMandatory(associationInvalid1, associationInvalid1);
		IStatus statusAssociationInvalid2 = new UDEModelValidatorFactory().validateMandatory(associationInvalid2, associationInvalid2);
		IStatus statusAssociationInvalid3 = new UDEModelValidatorFactory().validateMandatory(associationInvalid3, associationInvalid3);
		IStatus statusAssociationValid = new UDEModelValidatorFactory().validateMandatory(associationValid, associationValid);
		
		IStatus statusEnumInvalid1 = new UDEModelValidatorFactory().validateMandatory(enumInvalid1, enumInvalid1);
		IStatus statusEnumInvalid2 = new UDEModelValidatorFactory().validateMandatory(enumInvalid2, enumInvalid2);
		IStatus statusEnumInvalid3 = new UDEModelValidatorFactory().validateMandatory(enumInvalid3, enumInvalid3);
		IStatus statusEnumValid = new UDEModelValidatorFactory().validateMandatory(enumValid, enumValid);
		
		IStatus statusEnumValueInvalid1 = new UDEModelValidatorFactory().validateMandatory(enumValueInvalid1, enumValueInvalid1);
		IStatus statusEnumValueInvalid2 = new UDEModelValidatorFactory().validateMandatory(enumValueInvalid2, enumValueInvalid2);
		IStatus statusEnumValueInvalid3 = new UDEModelValidatorFactory().validateMandatory(enumValueInvalid3, enumValueInvalid3);
		IStatus statusEnumValueValid = new UDEModelValidatorFactory().validateMandatory(enumValueValid, enumValueValid);
		
		// Then
		Assert.assertFalse("Should be invalid", statusClassInvalid1.isOK());
		Assert.assertFalse("Should be invalid", statusClassInvalid2.isOK());
		Assert.assertFalse("Should be invalid", statusClassInvalid3.isOK());
		Assert.assertTrue("Should be valid", statusClassValid.isOK());
		
		Assert.assertFalse("Should be invalid", statusAttributeInvalid1.isOK());
		Assert.assertFalse("Should be invalid", statusAttributeInvalid2.isOK());
		Assert.assertFalse("Should be invalid", statusAttributeInvalid3.isOK());
		Assert.assertTrue("Should be valid", statusAttributeValid.isOK());
		
		Assert.assertFalse("Should be invalid", statusAssociationInvalid1.isOK());
		Assert.assertFalse("Should be invalid", statusAssociationInvalid2.isOK());
		Assert.assertFalse("Should be invalid", statusAssociationInvalid3.isOK());
		Assert.assertTrue("Should be valid", statusAssociationValid.isOK());
		
		Assert.assertFalse("Should be invalid", statusEnumInvalid1.isOK());
		Assert.assertFalse("Should be invalid", statusEnumInvalid2.isOK());
		Assert.assertFalse("Should be invalid", statusEnumInvalid3.isOK());
		Assert.assertTrue("Should be valid", statusEnumValid.isOK());
		
		Assert.assertFalse("Should be invalid", statusEnumValueInvalid1.isOK());
		Assert.assertFalse("Should be invalid", statusEnumValueInvalid2.isOK());
		Assert.assertFalse("Should be invalid", statusEnumValueInvalid3.isOK());
		Assert.assertTrue("Should be valid", statusEnumValueValid.isOK());
	}

	@Test
	public void testValidatePermittedValueRank() {
		// Given
		MdfEnumerationImpl enumValid = createMdfEnum(PrimitivesDomain.BOOLEAN);
		MdfEnumValueImpl enumValueInvalid = createMdfEnumValue(enumValid, "name", "false");
		AAAAspectDS.setTripleAPermittedValueRank(enumValueInvalid, "invalid");
		MdfEnumValueImpl enumValueValid = createMdfEnumValue(enumValid, "name", "true");
		AAAAspectDS.setTripleAPermittedValueRank(enumValueValid, "123");
		
		// When
		IStatus statusInvalid = new UDEModelValidatorFactory().validatePermittedValueRank(enumValueInvalid, enumValueInvalid);
		IStatus statusValid = new UDEModelValidatorFactory().validatePermittedValueRank(enumValueValid, enumValueValid);
		
		// Then
		Assert.assertFalse("Should be invalid", statusInvalid.isOK());
		Assert.assertTrue("Should be valid", statusValid.isOK());
	}
	
	@Test
	public void testValidateBaseTypeForEnumeration() {
		// Given
		MdfEnumerationImpl enumValid1 = createMdfEnum(PrimitivesDomain.BOOLEAN);
		MdfEnumerationImpl enumInvalid2 = createMdfEnum(PrimitivesDomain.BOOLEAN_OBJ);
		MdfEnumerationImpl enumValid3 = createMdfEnum(PrimitivesDomain.INTEGER);
		MdfEnumerationImpl enumInvalid4 = createMdfEnum(PrimitivesDomain.INTEGER_OBJ);
		MdfEnumerationImpl enumInvalid = createMdfEnum(PrimitivesDomain.STRING);
		
		// When
		IStatus statusValid1 = new UDEModelValidatorFactory().validateBaseTypeForEnumeration(enumValid1, enumValid1);
		IStatus statusInvalid2 = new UDEModelValidatorFactory().validateBaseTypeForEnumeration(enumInvalid2, enumInvalid2);
		IStatus statusValid3 = new UDEModelValidatorFactory().validateBaseTypeForEnumeration(enumValid3, enumValid3);
		IStatus statusInvalid4 = new UDEModelValidatorFactory().validateBaseTypeForEnumeration(enumInvalid4, enumInvalid4);
		IStatus statusInvalid = new UDEModelValidatorFactory().validateBaseTypeForEnumeration(enumInvalid, enumInvalid);
		
		// Then
		Assert.assertTrue("Should be valid", statusValid1.isOK());
		Assert.assertFalse("Shouldn't be valid", statusInvalid2.isOK());
		Assert.assertTrue("Should be valid", statusValid3.isOK());
		Assert.assertFalse("Should be valid", statusInvalid4.isOK());
		Assert.assertFalse("Should be invalid", statusInvalid.isOK());
	}

	@Test
	public void testValidateDefaultValue() {
		// Given
		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		MdfClassImpl mdfClass = createMdfClass("SomeClass");
		createMdfEnumValue(mdfEnum, "name", "123");
		MdfAttributeImpl attributeValid = addMdfAttribute(mdfClass, "attributeValid", mdfEnum);
		attributeValid.setDefault("name");
		MdfAttributeImpl attributeInvalid = addMdfAttribute(mdfClass, "attributeInvalid", mdfEnum);
		
		// When
		IStatus statusValid = new UDEModelValidatorFactory().validateDefaultValue(attributeValid, attributeValid);
		IStatus statusInvalid = new UDEModelValidatorFactory().validateDefaultValue(attributeInvalid, attributeInvalid);
		
		// Then
		Assert.assertTrue("Should be valid", statusValid.isOK());
		Assert.assertFalse("Should be invalid", statusInvalid.isOK());
	}
	
	@Test
	public void testValidateNotSupportedAssociation() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("SomeClass");
		MdfAssociationImpl associationValid1 = addMdfAssociation(mdfClass, "associationValid", mdfClass, MdfConstants.MULTIPLICITY_ONE);
		associationValid1.setContainment(MdfConstants.CONTAINMENT_BYREFERENCE);
		MdfAssociationImpl associationInvalid1 = addMdfAssociation(mdfClass, "associationInvalid1", mdfClass, MdfConstants.MULTIPLICITY_MANY);
		associationInvalid1.setContainment(MdfConstants.CONTAINMENT_BYVALUE);
		MdfAssociationImpl associationInvalid2 = addMdfAssociation(mdfClass, "associationInvalid2", mdfClass, MdfConstants.MULTIPLICITY_MANY);
		associationInvalid2.setContainment(MdfConstants.CONTAINMENT_BYREFERENCE);
		MdfAssociationImpl associationInvalid3 = addMdfAssociation(mdfClass, "associationInvalid3", mdfClass, MdfConstants.MULTIPLICITY_ONE);
		associationInvalid3.setContainment(MdfConstants.CONTAINMENT_BYVALUE);
		MdfReverseAssociationImpl reverseInvalid = addMdfReverse(mdfClass, associationInvalid1);
		
		// When
		IStatus statusValid1 = new UDEModelValidatorFactory().validateNotSupportedAssociation(associationValid1, associationValid1);
		IStatus statusInvalid1 = new UDEModelValidatorFactory().validateNotSupportedAssociation(associationInvalid1, associationInvalid1);
		IStatus statusInvalid2 = new UDEModelValidatorFactory().validateNotSupportedAssociation(associationInvalid2, associationInvalid2);
		IStatus statusInvalid3 = new UDEModelValidatorFactory().validateNotSupportedAssociation(associationInvalid3, associationInvalid3);
		IStatus statusInvalid4 = new UDEModelValidatorFactory().validateNotSupportedAssociation(reverseInvalid, reverseInvalid);
		
		// Then
		Assert.assertTrue("Should be valid", statusValid1.isOK());
		Assert.assertFalse("Should be valid", statusInvalid1.isOK());
		Assert.assertFalse("Should be invalid", statusInvalid2.isOK());
		Assert.assertFalse("Should be invalid", statusInvalid3.isOK());
		Assert.assertFalse("Should be invalid", statusInvalid4.isOK());
	}
	
	@Test
	public void testValidateNoInheritance() {
		// Given
		MdfClassImpl mdfClass1 = createMdfClass("SomeClass1");
		mdfClass1.setAbstract(true);
		MdfClassImpl mdfClass2 = createMdfClass("SomeClass2");
		mdfClass2.setBaseClass(mdfClass1);
		MdfClassImpl mdfClassOk = createMdfClass("ClassOk");
		
		// When
		IStatus statusInvalid1 = new UDEModelValidatorFactory().validateNoInheritance(mdfClass1, mdfClass1);
		IStatus statusInvalid2 = new UDEModelValidatorFactory().validateNoInheritance(mdfClass2, mdfClass2);
		IStatus statusValid1 = new UDEModelValidatorFactory().validateNoInheritance(mdfClassOk, mdfClassOk);
		
		// Then
		Assert.assertFalse("Should be invalid", statusInvalid1.isOK());
		Assert.assertFalse("Should be invalid", statusInvalid2.isOK());
		Assert.assertTrue("Should be valid", statusValid1.isOK());
	}
	
	@Test
	public void testValidateOnlyClassAndEnumAllowed() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("ClassName");
		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.BOOLEAN);
		MdfDatasetImpl dataset = createDataset("DatasetName", mdfClass);
		MdfBusinessTypeImpl businessType = createMdfBusinessType("BT", PrimitivesDomain.BOOLEAN);
		
		// When
		IStatus statusValid1 = new UDEModelValidatorFactory().validateOnlyClassAndEnumAllowed(mdfClass, mdfClass);
		IStatus statusValid2 = new UDEModelValidatorFactory().validateOnlyClassAndEnumAllowed(mdfEnum, mdfEnum);
		IStatus statusInvalid1 = new UDEModelValidatorFactory().validateOnlyClassAndEnumAllowed(dataset, dataset);
		IStatus statusInvalid2 = new UDEModelValidatorFactory().validateOnlyClassAndEnumAllowed(businessType, businessType);
		
		// Then
		Assert.assertTrue("Should be valid", statusValid1.isOK());
		Assert.assertTrue("Should be valid", statusValid2.isOK());
		Assert.assertFalse("Shouldn't be valid", statusInvalid1.isOK());
		Assert.assertFalse("Shouldn't be valid", statusInvalid2.isOK());
	}
	
	@Test
	public void testValidatePrimaryKeyMandatory() {
		// Given
		MdfBusinessType btypeNumber = createMdfBusinessType("BType", PrimitivesDomain.INTEGER);
		MdfClassImpl mdfClassValid = createMdfClass("ValidClass");
		MdfAttributeImpl mdfAttribute1 = addMdfAttribute(mdfClassValid, "id", btypeNumber);
		mdfAttribute1.setPrimaryKey(true);
		MdfClassImpl mdfClassInvalid = createMdfClass("InvalidClass");
		MdfAttributeImpl mdfAttribute2 = addMdfAttribute(mdfClassValid, "id", btypeNumber);
		mdfAttribute2.setPrimaryKey(false);
		
		// When
		IStatus statusValid = new UDEModelValidatorFactory().validatePrimaryKeyMandatory(mdfClassValid, mdfClassValid);
		IStatus statusInvalid = new UDEModelValidatorFactory().validatePrimaryKeyMandatory(mdfClassInvalid, mdfClassInvalid);
		
		// Then
		Assert.assertTrue("Should be valid", statusValid.isOK());
		Assert.assertFalse("Shouldn't be valid", statusInvalid.isOK());
	}
	
	@Test
	public void testValidateNoNullValueForEnum() {
		// Given
		MdfEnumerationImpl mdfEnumValid = createMdfEnum(PrimitivesDomain.INTEGER);
		mdfEnumValid.setAcceptNullValue(false);
		MdfEnumerationImpl mdfEnumInvalid = createMdfEnum(PrimitivesDomain.INTEGER);
		mdfEnumInvalid.setAcceptNullValue(true);
		
		// When
		IStatus statusValid = new UDEModelValidatorFactory().validateNoNullValueForEnum(mdfEnumValid, mdfEnumValid);
		IStatus statusInvalid = new UDEModelValidatorFactory().validateNoNullValueForEnum(mdfEnumInvalid, mdfEnumInvalid);
		
		// Then
		Assert.assertTrue("Should be valid", statusValid.isOK());
		Assert.assertFalse("Shouldn't be valid", statusInvalid.isOK());
	}
	
	@Test
	public void testValidateAttributeSQLUniqueWithinClass() {
		// Given
		MdfBusinessType btypeNumber = createMdfBusinessType("BType", PrimitivesDomain.INTEGER);
		MdfClassImpl mdfClassValid = createMdfClass("ValidClass");
		MdfAttributeImpl attribute1a = addMdfAttribute(mdfClassValid, "attr1", btypeNumber);
		AAAAspectDS.setTripleAAttributeSQLName(attribute1a, "sqlName1");
		MdfAttributeImpl attribute2a = addMdfAttribute(mdfClassValid, "attr2", btypeNumber);
		AAAAspectDS.setTripleAAttributeSQLName(attribute2a, "sqlName2");
		MdfAttributeImpl attribute3a = addMdfAttribute(mdfClassValid, "attr3", btypeNumber);
		AAAAspectDS.setTripleAAttributeSQLName(attribute3a, "sqlName3");
		
		MdfClassImpl mdfClassInvalid = createMdfClass("InvalidClass");
		MdfAttributeImpl attribute1b = addMdfAttribute(mdfClassInvalid, "attr1", btypeNumber);
		AAAAspectDS.setTripleAAttributeSQLName(attribute1b, "sqlName1");
		MdfAttributeImpl attribute2b = addMdfAttribute(mdfClassInvalid, "attr2", btypeNumber);
		AAAAspectDS.setTripleAAttributeSQLName(attribute2b, "sqlName2");
		MdfAttributeImpl attribute3b = addMdfAttribute(mdfClassInvalid, "attr3", btypeNumber);
		AAAAspectDS.setTripleAAttributeSQLName(attribute3b, "sqlName2");
		
		// When
		IStatus statusValid = new UDEModelValidatorFactory().validateAttributeSQLUniqueWithinClass(attribute2a, attribute2a);
		IStatus statusInvalid = new UDEModelValidatorFactory().validateAttributeSQLUniqueWithinClass(attribute2b, attribute2b);
		
		// Then
		Assert.assertTrue("Should be valid", statusValid.isOK());
		Assert.assertFalse("Shouldn't be valid", statusInvalid.isOK());
	}
	

	private MdfEnumerationImpl createMdfEnum(MdfPrimitive mdfPrimitive) {
		MdfEnumerationImpl mdfEnum = (MdfEnumerationImpl)ECoreModelFactory.INSTANCE.createMdfEnumeration(testDomain);
		mdfEnum.setType(getECorePrimitive(mdfPrimitive));
		return mdfEnum;
	}

	@SuppressWarnings("unchecked")
	private MdfEnumValueImpl createMdfEnumValue(MdfEnumerationImpl mdfEnum,
			String name, String value) {
		MdfEnumValueImpl mdfEnumValue = (MdfEnumValueImpl)ECoreModelFactory.INSTANCE.createMdfEnumValue(mdfEnum);
		mdfEnumValue.setName(name);
		mdfEnumValue.setValue(value);
		mdfEnum.getValues().add(mdfEnumValue);
		return mdfEnumValue;
	}

	private MdfClassImpl createMdfClass(String name) {
		MdfClassImpl mdfClass = (MdfClassImpl)ECoreModelFactory.INSTANCE.createMdfClass(testDomain);
		mdfClass.setName(name);
		return mdfClass;
	}
	
	@SuppressWarnings("unchecked")
	private MdfAttributeImpl addMdfAttribute(MdfClassImpl mdfClass, String name, MdfPrimitive type) {
		MdfAttributeImpl mdfAttribute = (MdfAttributeImpl)ECoreModelFactory.INSTANCE.createMdfAttribute(mdfClass);
		mdfAttribute.setName(name);
		mdfAttribute.setType(type);
		mdfClass.getProperties().add(mdfAttribute);
		return mdfAttribute;
	}
	
	private MdfBusinessTypeImpl createMdfBusinessType(String name, MdfPrimitive mdfPrimitive) {
		MdfBusinessTypeImpl businessType = (MdfBusinessTypeImpl)ECoreModelFactory.INSTANCE.createMdfBusinessType(testDomain);
		businessType.setName(name);
		businessType.setType(getECorePrimitive(mdfPrimitive));
		return businessType;
	}

	@SuppressWarnings("unchecked")
	private MdfAssociationImpl addMdfAssociation(MdfClassImpl mdfClass,
			String name, MdfClassImpl mdfClass2, int multiplicity) {
		MdfAssociationImpl association = (MdfAssociationImpl)ECoreModelFactory.INSTANCE.createMdfAssociation(mdfClass);
		association.setType(mdfClass2);
		association.setMultiplicity(multiplicity);
		mdfClass.getProperties().add(association);
		return association;
	}

	private MdfReverseAssociationImpl addMdfReverse(MdfClassImpl mdfClass,
			MdfAssociationImpl association) {
		MdfReverseAssociationImpl reverse = (MdfReverseAssociationImpl)ECoreModelFactory.INSTANCE.createMdfReverseAssociation(association);
		association.setReverse(reverse);
		return reverse;
	}

	private MdfDatasetImpl createDataset(String string, MdfClassImpl mdfClass) {
		MdfDatasetImpl dataset = (MdfDatasetImpl)ECoreModelFactory.INSTANCE.createMdfDataset(testDomain);
		dataset.setBaseClass(mdfClass);
		return dataset;
	}

	private MdfDatasetMappedPropertyImpl addDatasetMappedProperty(MdfDataset mdfDataset,
			String name, String path) {
		MdfDatasetMappedPropertyImpl mappedProperty = (MdfDatasetMappedPropertyImpl)ECoreModelFactory.INSTANCE.createMdfDatasetMappedProperty(mdfDataset);
		mappedProperty.setPath(path);
		return mappedProperty;
	}

	private MdfDatasetDerivedPropertyImpl addDatasetDerivedProperty(MdfDataset mdfDataset,
			String name, MdfEntity type) {
		MdfDatasetDerivedPropertyImpl derivedProperty = (MdfDatasetDerivedPropertyImpl)ECoreModelFactory.INSTANCE.createMdfDatasetDerivedProperty(mdfDataset);
		derivedProperty.setType(type);
		return derivedProperty;
	}
	
	private MdfPrimitive getECorePrimitive(MdfPrimitive p) {
		return PrimitivesDomain.DOMAIN.getPrimitive(p.getName());
	}

}

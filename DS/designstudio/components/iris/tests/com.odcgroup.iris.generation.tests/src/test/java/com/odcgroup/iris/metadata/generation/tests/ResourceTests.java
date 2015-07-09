package com.odcgroup.iris.metadata.generation.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.t24.enquiry.util.EMTermType;

public class ResourceTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests that the TERM_LIST_TYPE = true is added to multivalue groups and subvalue groups but not normal fields.
	 */
	@Test
	public void testTermListType() {

		// Setup
		Resource resource = new Resource(RESOURCE_TYPE.unknown, "CUSTOMER", "");
		resource.addField("NORMAL.FIELD", "", "", "", "", "", false, false, "", false, false, "");
		resource.addField("MV.FIELD", "", "", "", "MV.FIELD", "", false, false, "", false, false, "");
		resource.addField("SV.FIELD", "", "", "", "MV.FIELD", "SV.FIELD",  false, false, "", false, false, "");
		
		// Exercise
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity emEntity = resource.toEMEntity(fieldTypeChecker);
		
		// Examine
		// Check that the MV.FIELD and SV.FIELD properties have the TERM_LIST_TYPE term
		EMProperty normalField = emEntity.getProperties().get(0);
		assertEquals("NormalField", normalField.getName());
		assertEquals(false, normalField.getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
		
		EMProperty mvGroup = emEntity.getProperties().get(1);
		assertEquals("MvFieldMvGroup", mvGroup.getName());
		assertEquals(true, mvGroup.getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
		
		EMProperty svGroup = mvGroup.getSubProperties().get(1);
		assertEquals("SvFieldSvGroup", svGroup.getName());
		assertEquals(true, svGroup.getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
	}
	
	@Test
	public void testTermLangTypeForNonGroupField() {
	
		Resource resource = new Resource(RESOURCE_TYPE.unknown, "CUSTOMER", "");
		resource.addField("NORMAL.FIELD", "", "", "", "", "", false, false, "", false, false, "");
		resource.addField("SHORT.NAME", "", "", "", "SHORT.NAME", "", true, false, "", false, true, "");
		resource.addField("ADDRESS", "ADDRESS", "", "", "ADDRESS", "ADDRESS", false, false, "", false, true, "");
		
        // Exercise
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity emEntity = resource.toEMEntity(fieldTypeChecker);

		EMProperty singleLangProperty = emEntity.getProperties().get(1);
		assertEquals("ShortNameMvGroup", singleLangProperty.getName());
		assertEquals("LanguageCode",singleLangProperty.getSubProperties().get(0).getName());		
		assertEquals(0,singleLangProperty.getSubProperties().get(0).getVocabularyTerms().size());		
		assertEquals("ShortName",singleLangProperty.getSubProperties().get(1).getName());
		assertEquals(2, singleLangProperty.getVocabularyTerms().size());		
		assertEquals(true, singleLangProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
		assertEquals(true, singleLangProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.TERM_LANG_TYPE, "true")));		
		
		EMProperty addressMvProperty = emEntity.getProperties().get(2);
		assertEquals("AddressMvGroup", addressMvProperty.getName());
		assertEquals(1,addressMvProperty.getVocabularyTerms().size());
		assertEquals(true, addressMvProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
		EMProperty addressSvProperty = addressMvProperty.getSubProperties().get(0);
		assertEquals(true, addressSvProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.TERM_LANG_TYPE, "true")));
		assertEquals("AddressSvGroup",addressSvProperty.getName());
		assertEquals(2, addressSvProperty.getVocabularyTerms().size());
		EMProperty languageCodeProperty = addressSvProperty.getSubProperties().get(0);
		assertEquals("LanguageCode",languageCodeProperty.getName());
		assertEquals(0, languageCodeProperty.getVocabularyTerms().size());
		EMProperty addressProperty = addressSvProperty.getSubProperties().get(1);
		assertEquals("Address",addressProperty.getName());
		assertEquals(0, addressProperty.getVocabularyTerms().size());		
	}
	
	@Test
	public void testTermLangTypeForGroupField() {
	
		Resource resource = new Resource(RESOURCE_TYPE.unknown, "CUSTOMER", "");
		resource.addField("GROUP.FIRST.FIELD", "", "", "", "", "", false, false, "", false, false, "");
		resource.addField("SOME.NAME", "", "", "", "GROUP.FIRST.FIELD", "SOME.NAME", true, false, "", false, true, "");
		
        // Exercise
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity emEntity = resource.toEMEntity(fieldTypeChecker);

		EMProperty groupFirstFieldProperty = emEntity.getProperties().get(1);
		assertEquals("GroupFirstFieldMvGroup", groupFirstFieldProperty.getName());
		EMProperty someNameProperty = groupFirstFieldProperty.getSubProperties().get(0);
		assertEquals("SomeNameSvGroup", someNameProperty.getName());
		assertEquals(2,someNameProperty.getSubProperties().size());
		assertEquals("LanguageCode",someNameProperty.getSubProperties().get(0).getName());		
		assertEquals(0,someNameProperty.getSubProperties().get(0).getVocabularyTerms().size());		
		assertEquals("SomeName",someNameProperty.getSubProperties().get(1).getName());
		assertEquals(2, someNameProperty.getVocabularyTerms().size());		
		assertEquals(true, groupFirstFieldProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
		assertEquals(true, someNameProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.TERM_LANG_TYPE, "true")));		
	}
	
	@Test
	public void testTermLangTypeForGroupFirstField() {
	
		Resource resource = new Resource(RESOURCE_TYPE.unknown, "CUSTOMER", "");
		resource.addField("SOME.NAME", "", "", "", "SOME.NAME", "SOME.NAME", true, false, "", false, true, "");
		resource.addField("GROUP.SECOND.FIELD", "", "", "", "SOME.NAME", "", false, false, "", false, false, "");		
		
        // Exercise
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity emEntity = resource.toEMEntity(fieldTypeChecker);

		EMProperty someNameMvProperty = emEntity.getProperties().get(0);
		EMProperty someNameProperty = someNameMvProperty.getSubProperties().get(0);
		assertEquals("SomeNameSvGroup", someNameProperty.getName());
		assertEquals(2,someNameProperty.getSubProperties().size());
		assertEquals("LanguageCode",someNameProperty.getSubProperties().get(0).getName());		
		assertEquals(0,someNameProperty.getSubProperties().get(0).getVocabularyTerms().size());		
		assertEquals("SomeName",someNameProperty.getSubProperties().get(1).getName());
		assertEquals(2, someNameProperty.getVocabularyTerms().size());		
		assertEquals(true, someNameMvProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
		assertEquals(true, someNameProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.TERM_LANG_TYPE, "true")));	

		EMProperty groupSecondFieldProperty = emEntity.getProperties().get(0).getSubProperties().get(1);
		assertEquals("GroupSecondField", groupSecondFieldProperty.getName());
	}

	@Test
	public void testTermSemanticType() {
		Resource resource = new Resource(RESOURCE_TYPE.unknown, "CUSTOMER", "");
		resource.addField("SOME.NAME", "", "", "", "", "", true, false, "", false, true, "BT");
		
		FieldTypeChecker checker = new FieldTypeChecker();
		EMEntity emEntity = resource.toEMEntity(checker);
		
		EMProperty someNameProperty = emEntity.getProperties().get(0);
		assertEquals(true, someNameProperty.getVocabularyTerms().contains(new EMTerm(EMTermType.BUSINESS_TYPE_TERM, "BT")));
	}
}

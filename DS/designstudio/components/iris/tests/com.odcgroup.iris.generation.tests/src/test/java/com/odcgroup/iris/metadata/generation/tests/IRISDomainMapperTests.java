package com.odcgroup.iris.metadata.generation.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.IRISDomainMapper;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.t24.enquiry.util.EMTermType;


/**
 * Class to test IRISDomainMapper.
 * Most of the code for IRISDomainMapper this is currently (still) in the IRISMetadataMapper class and should be refactored.
 *
 * @author agoulding
 *
 */
public class IRISDomainMapperTests {

	private TestHelper testHelper = new TestHelper();
	private IRISDomainMapper domainMapper = new IRISDomainMapper();

	/**
	 * Tests that AA property classes are tagged with the TERM_LIST_TYPE vocabulary item
	 */
	@Test
	public void testAAListType() {
		
		// Create the arrangement
		MdfClass mdfClass = testHelper.getAaAsClass();
		FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		
		// Exercise
		EMEntity entity = domainMapper.getEntity(mdfClass, fieldTypeChecker, null);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(3, properties.size());
		assertEquals(true, properties.get(2).getVocabularyTerms().contains(new EMTerm(EMTermType.LIST_TERM, "true")));
	}

	
    /**
     * Test that field type information for a property is generated when the config shows that it's safe to do so for that field.
     */
    @Test
    public void testSimpleFieldType() {
        // Create the arrangement
        MdfClass mdfClass = testHelper.getBasicAppAsClass();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        fieldTypeChecker.setText("ACCOUNT/FROM.DATE");

        // Exercise
        EMEntity entity = domainMapper.getEntity(mdfClass, fieldTypeChecker, null);
        
        // Examine output
        List<EMProperty> properties = entity.getProperties();
        assertEquals("DATE", properties.get(2).getSubProperties().get(2).getVocabularyTerms().get(0).getValue());
    }
    

    


}

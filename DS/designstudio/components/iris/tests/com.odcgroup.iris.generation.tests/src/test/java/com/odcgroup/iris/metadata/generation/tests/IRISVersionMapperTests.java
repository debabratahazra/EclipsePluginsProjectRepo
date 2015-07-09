package com.odcgroup.iris.metadata.generation.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.IRISVersionMapper;
import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLFactory;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;

public class IRISVersionMapperTests {

	private VersionDSLFactory versionFactory = new VersionDSLFactoryImpl();
	private IRISVersionMapper versionMapper = new IRISVersionMapper();
	private TestHelper testHelper = new TestHelper();

	/**
	 * Test that a the mapper generates the correct entity for a simple version
	 * The version has just one field - non mv
	 * The result should have 2 fields - the primary key and the field.
	 */
	@Test
	public void testSimpleCase() {
		// Create the version
		Version version = createVersion("TEC.ITEMS,INPUT");
		version.getFields().add(createField("AREA", 1, 25));

		// Exercise
		Application application = testHelper.getTecItemsAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(2, properties.size());
		assertEquals("{TecProfileId, TEC.PROFILE.ID, TERM_ID_FIELD=true}", properties.get(0).toString());
		assertEquals("{Area, AREA}", properties.get(1).toString());
	}
	

	
    /**
     * Test that a the mapper generates the field type information for a property when the config shows that it's safe to do so.
     * The version has just one field - non mv
     * The result should have 2 fields - the primary key and the field.
     * The primary key field should have type information
     */
    @Test
    public void testFieldType() {
        // Create the version
        Version version = createVersion("ACCOUNT,TEST");
        version.getFields().add(createField("FROM.DATE-1", 1, 25));

        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        fieldTypeChecker.setText("ACCOUNT/FROM.DATE");
        EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
        
        // Examine output
        List<EMProperty> properties = entity.getProperties();
        assertEquals("DATE", properties.get(1).getSubProperties().get(2).getVocabularyTerms().get(0).getValue());
    }
    

    
	/**
	 * If a version contains a field that's part of a mv group then the resulting EMEntity should have a property with subproperties, named after the first
	 * field in each mv group, whether or not the first field of the group is in the version.
	 */
	@Test
	public void testMvFieldsHandled() {
		// Create the version
		Version version = createVersion("TEC.ITEMS,INPUT");
		version.getFields().add(createField("THRESHOLD", 1, 25)); // Part of mv group beginning with THRESHOLD.TYPE.

		// Exercise
		Application application = testHelper.getTecItemsAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(2, properties.size());
		EMProperty thresholdTypeMvGroup = properties.get(1);
		assertEquals("{ThresholdTypeMvGroup, TERM_LIST_TYPE=true}", thresholdTypeMvGroup.toString());
		assertEquals("{"+GeneratorConstants.VALUEPOSITION_PROP_NAME+"}", thresholdTypeMvGroup.getSubProperties().get(0).toString());
		assertEquals("{"+GeneratorConstants.SUBVALUEPOSITION_PROP_NAME+"}", thresholdTypeMvGroup.getSubProperties().get(1).toString());
		assertEquals("{Threshold, THRESHOLD}", thresholdTypeMvGroup.getSubProperties().get(2).toString());
	}
	
	/**
	 * Version fields beginning with '*' should be ignored, they are presentation metadata, not field data.
	 * Also test that fields that aren't part of the underlying application are ignored.
	 * However, we must still add fields where column = zero.
	 * Seems like during introspection, when the underlying version has COLUMN = '', it gets converted to '0' in the DSL.
	 */
	@Test
	public void testNonFieldsIgnored() {
		// Create the version
		Version version = createVersion("TEC.ITEMS,INPUT");
		version.getFields().add(createField("*", null, 25));  // A label field, should be ignored
		version.getFields().add(createField("AREA", 0, 25));  // A field with column = zero, should be displayed
		version.getFields().add(createField("METRIC.TYPE", 1, 25));  // A field with proper column, should be displayed
		version.getFields().add(createField("*", 1, 25));  // Another label field, should be ignored
		version.getFields().add(createField("SHOULD.BE.IGNORED", 1, 25));  // A field that's not on the underlying application, should be ignored

		// Exercise
		Application application = testHelper.getTecItemsAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(3, properties.size());
		assertEquals("TecProfileId", properties.get(0).getName());
		assertEquals("Area", properties.get(1).getName());
		assertEquals("MetricType", properties.get(2).getName());
	}
	
	/**
	 * When versions want to display the first value (only) from a mv group, eg to show only the English label from a language field
	 * then they add '-1' to the field name.
	 * eg TEC.ITEMS application has a field called 'DESCRIPTION'
	 * The version TEC.ITEMS,INPUT has a field called 'DESCRIPTION-1'.
	 * We need to map this onto the entire 'DESCRIPTION' field in the application and return the whole field.
	 * If a version is displaying two values from the field, then the field should only be added once.
	 * Similarly, versions can specify -1.1, to request the first subvalue of the first multivalue.
	 */
	@Test
	public void testSpecificValueRequestsHandled() {
		// Create the version
		Version version = createVersion("TEC.ITEMS,INPUT");
		version.getFields().add(createField("DESCRIPTION-1", 1, 25));
		version.getFields().add(createField("DESCRIPTION-2", 1, 25));  // Only one description should be added
		version.getFields().add(createField("SUBSCRIBER-1.2", 1, 25));  // 2nd subvalue requested

		// Exercise
		Application application = testHelper.getTecItemsAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(3, properties.size());
		assertEquals("TecProfileId", properties.get(0).getName());
		assertEquals(GeneratorConstants.VALUEPOSITION_PROP_NAME, properties.get(1).getSubProperties().get(0).getName());
		assertEquals(GeneratorConstants.SUBVALUEPOSITION_PROP_NAME, properties.get(1).getSubProperties().get(1).getName());
		assertEquals("Description", properties.get(1).getSubProperties().get(2).getName());
		assertEquals(GeneratorConstants.VALUEPOSITION_PROP_NAME, properties.get(2).getSubProperties().get(2).getSubProperties().get(0).getName());
		assertEquals(GeneratorConstants.SUBVALUEPOSITION_PROP_NAME, properties.get(2).getSubProperties().get(2).getSubProperties().get(1).getName());
		assertEquals("Subscriber", properties.get(2).getSubProperties().get(2).getSubProperties().get(2).getName());
	}
	

	/**
	 * If a version has an associated version, then the fields from the associated version should be included, creating a
	 * resource that has the union of all fields in the associated versions.
	 */
	@Test
	public void testAssociatedVersions() {
		// Create the main version
		Version version = createVersion("TEC.ITEMS,INPUT");
		version.getFields().add(createField("AREA", 1, 25));
		
		// Create the associated version and add it to the main version
		Version versionMt = createVersion("TEC.ITEMS,MT");
		versionMt.getFields().add(createField("METRIC.TYPE", 1, 25));
		version.getAssociatedVersions().add(versionMt);

		// Exercise
		Application application = testHelper.getTecItemsAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(3, properties.size());
		assertEquals("TecProfileId", properties.get(0).getName());
		assertEquals("Area", properties.get(1).getName());
		assertEquals("MetricType", properties.get(2).getName());
	}
	
	/**
	 * In a T24 VERSION record, fields can be specified in the AUTOM.FIELD.NO field, and these are distinct from the fields defined in the FIELD.NO field.
	 * However in the DS model, these are all treated as the same object - a versionDSL.Field.
	 * They can be told apart - AUTOM.FIELD.NO fields don't have any Presentation data, so column and maxLength are both null.
	 * This test checks that fields created without presentation data are skipped.
	 */
	@Test
	public void testDefaultingFieldsIgnored() {
		// Create the main version
		Version version = createVersion("TEC.ITEMS,INPUT");
		version.getFields().add(createField("AREA", 1, 25));
		version.getFields().add(createField("SUBSCRIBER", null, 0));   // Default fields have null column and maxLength

		// Exercise
		Application application = testHelper.getTecItemsAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(2, properties.size());
		assertEquals("TecProfileId", properties.get(0).getName());
		assertEquals("Area", properties.get(1).getName());
	}
	
	/**
	 * Some T24 versions do not have any fields, they just want to indicate that a version has zero authorisers or similar.
	 * In these cases, the default behaviour should be that all fields from the underlying application are displayed.
	 */
	@Test
	public void testVersionWithNoFields() {
		// Create the main version
		Version version = createVersion("TEC.ITEMS,INPUT");
		version.setNumberOfAuthorisers(0);

		// Exercise
		Application application = testHelper.getTecItemsAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
		EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Examine output
		List<EMProperty> properties = entity.getProperties();
		assertEquals(5, properties.size());
		assertEquals("TecProfileId", properties.get(0).getName());
		assertEquals("DescriptionMvGroup", properties.get(1).getName());
		assertEquals("Area", properties.get(2).getName());
		assertEquals("MetricType", properties.get(3).getName());
		assertEquals("ThresholdTypeMvGroup", properties.get(4).getName());
	}
	
	

	
	/**
	 * Creates a Version
	 * @param t24Name
	 * @return
	 */
	private Version createVersion(String t24Name) {
		Version version = versionFactory.createVersion();
		version.setT24Name(t24Name);
		return version;
	}

	/**
	 * Creates a version field
	 * @param t24name
	 * @param column
	 * @param maxLength
	 * @return
	 */
	private Field createField(String t24name, Integer column, int maxLength) {
		Field field = versionFactory.createField();
		field.setName(t24name.replace(".", "_"));
		field.setColumn(column);
		field.setMaxLength(maxLength);
		return field;
	}


}

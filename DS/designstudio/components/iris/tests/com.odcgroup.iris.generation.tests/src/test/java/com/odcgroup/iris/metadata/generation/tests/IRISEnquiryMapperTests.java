package com.odcgroup.iris.metadata.generation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.iris.generator.AppField;
import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.IRISEnquiryMapper;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.generator.ResourceField;
import com.odcgroup.t24.enquiry.enquiry.AbsConversion;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.BreakKind;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.ConvertConversion;
import com.odcgroup.t24.enquiry.enquiry.CriteriaOperator;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryType;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.FromFieldType;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.ProcessingMode;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperation;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;
import com.odcgroup.t24.enquiry.enquiry.TotalOperation;
import com.odcgroup.t24.enquiry.enquiry.ValueConversion;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.t24.enquiry.util.EMTermType;

public class IRISEnquiryMapperTests {

    private EnquiryFactory enquiryFactory = new EnquiryFactoryImpl();
    private IRISEnquiryMapper enquiryMapper = new IRISEnquiryMapper();
    private TestHelper testHelper = new TestHelper();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetResultFields() {
        Field field;
        Enquiry enquiry = enquiryFactory.createEnquiry();
        List<Field> expectedFields = new ArrayList<Field>();

        // Make a field with a column number, should be output
        field = createEnqField("A", null, null, 1, null); // Autoboxing
        enquiry.getFields().add(field);
        expectedFields.add(field);
        
        // Make a field with no position or display break at all, should not be output
        field = createEnqField("B", null, null, null, null);
        enquiry.getFields().add(field);
        
        // Make a field with a column number, but DISPLAY.BREAK = NONE, should not be output
        field = createEnqField("C", null, null, 1, BreakKind.NONE);
        enquiry.getFields().add(field);
        
        // Make a field with a page throw, should not be output or cause NullPointerException
        field = enquiryFactory.createField();
        field.setName("D");
        FieldPosition fieldPosition = enquiryFactory.createFieldPosition();
        fieldPosition.setPageThrow(Boolean.TRUE);
        field.setPosition(fieldPosition);
        enquiry.getFields().add(field);
        
        // Make a field with no column number, but used in ENQUIRY.NAME, should be output
        field = createEnqField("DRILL1", null, null, null, null);
        enquiry.getFields().add(field);
        DrillDown drilldown = enquiryFactory.createDrillDown();
        FromFieldType fromFieldType = enquiryFactory.createFromFieldType();
        fromFieldType.setValue("DRILL1");
        drilldown.setType(fromFieldType);
        enquiry.getDrillDowns().add(drilldown);
        expectedFields.add(field);
        
        // Make a field with no column number, but used in SEL.CRIT, should be output
        field = createEnqField("DRILL2", null, null, null, null);
        enquiry.getFields().add(field);
        drilldown = enquiryFactory.createDrillDown();
        EnquiryType enquiryType = enquiryFactory.createEnquiryType();
        enquiryType.setValue("ACCT.BAL.TODAY");
        drilldown.setType(enquiryType);
        SelectionCriteria selectionCriteria = enquiryFactory.createSelectionCriteria();
        selectionCriteria.setField("SOME.FIELD");
        selectionCriteria.setOperand(CriteriaOperator.EQUALS);
        selectionCriteria.getValues().add("DRILL2");
        drilldown.getCriteria().add(selectionCriteria);
        enquiry.getDrillDowns().add(drilldown);
        expectedFields.add(field);
        
        // Exercise
        List<Field> resultFields = enquiryMapper.getResultFields(enquiry);
        
        // Examine output
        assertEquals(expectedFields, resultFields);
    }
    
    
    
    /**
     * DS-7498 IRIS metadata cartridge should generate metadata for fields used in break conditions even if they are not output or used in drilldowns
     * ie when a field is used in a BREAK CHANGE operation, then it should be output
     * Unit test
     */
    @Test
    public void testBreakChangeFieldsShouldBeResultFields() {
        Field field;
        Enquiry enquiry = enquiryFactory.createEnquiry();
        List<Field> expectedFields = new ArrayList<Field>();

        // Make a field with a column number, should be output
        field = createEnqField("A", null, null, 1, null);
        enquiry.getFields().add(field);
        expectedFields.add(field);
        
        // Make a field with no position or display break at all, should not be output
        field = createEnqField("B", null, null, null, null);
        enquiry.getFields().add(field);
        expectedFields.add(field);  // Even though not displayed in T24Browser, it should be output as it's used in BC operation
        
        // Make a field that uses a non displayed field in a break change operation, the non displayed field should be returned.
        BreakOnChangeOperation breakOnChangeOperation = enquiryFactory.createBreakOnChangeOperation();
        breakOnChangeOperation.setField("B");
        field = createEnqField("C", breakOnChangeOperation, null, null, null);
        enquiry.getFields().add(field);
        
        // Exercise
        List<Field> resultFields = enquiryMapper.getResultFields(enquiry);
        
        // Examine output
        assertEquals(expectedFields, resultFields);
    }
    
    /**
     * Bugfix test. Check that a field with breakCondition = BreakKind.NONE that nonetheless has a column is added to the result fields.
     */
    @Test
    public void testGetResultFieldsCustomDisplayBreak() {
        Field field;
        Enquiry enquiry = enquiryFactory.createEnquiry();
        List<Field> expectedFields = new ArrayList<Field>();

        // Make a field with a column number, but DISPLAY.BREAK = NONE, should be output because the breakCondition has a field
        field = createEnqField("CustomBreak", null, null, 1, BreakKind.NONE);
        field.getBreakCondition().setField("breakConditionField");
        enquiry.getFields().add(field);
        expectedFields.add(field);
        
        // Exercise
        List<Field> resultFields = enquiryMapper.getResultFields(enquiry);
        
        // Examine output
        assertEquals(expectedFields, resultFields);
    }
    
    /**
     * Unit test
     * Various tests for the EnquiryMapper.getBaseAppField()
     */
    @Test
    public void testGetBaseAppField() {
        // Define variables
        String actual;
        Enquiry enquiry;
        Field field;
        ConstantOperation constantOperation;
        ApplicationFieldNameOperation applicationFieldNameOperation;
        
        // Field with a literal operation, should return null for the base application field
        enquiry = enquiryFactory.createEnquiry();
        constantOperation = enquiryFactory.createConstantOperation();
        field = createEnqField("A", constantOperation, null, null, null);
        enquiry.getFields().add(field);
        actual = enquiryMapper.getBaseAppField(enquiry, null, "A", false, new ArrayList<String>());
        assertEquals(null, actual);
        
        // Field with a field name in the operation, should return the field name
        enquiry = enquiryFactory.createEnquiry();
        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("CURRENCY");
        field = createEnqField("A", applicationFieldNameOperation, null, null, null);
        enquiry.getFields().add(field);
        actual = enquiryMapper.getBaseAppField(enquiry, null, "A", false, new ArrayList<String>());
        assertEquals("CURRENCY", actual);
        
        // Field with field name in the operation, but has a conversion so should return null because it has probably been modified
        enquiry = enquiryFactory.createEnquiry();
        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("CURRENCY");
        ConvertConversion convertConversion = enquiryFactory.createConvertConversion();
        field = createEnqField("A", applicationFieldNameOperation, convertConversion, 1, null);
        enquiry.getFields().add(field);
        actual = enquiryMapper.getBaseAppField(enquiry, null, "A", false, new ArrayList<String>());
        assertEquals(null, actual);
        
        // Field that does not exist, should return null
        enquiry = enquiryFactory.createEnquiry();
        field = createEnqField("B", null, null, 1, null);
        enquiry.getFields().add(field);
        actual = enquiryMapper.getBaseAppField(enquiry, null, "A", false, new ArrayList<String>());
        assertEquals(null, actual);
        
        // Field with SelectionOperation should return the selection field
        enquiry = enquiryFactory.createEnquiry();
        SelectionOperation selectionOperation = enquiryFactory.createSelectionOperation();
        selectionOperation.setField("CUSTOMER");
        field = createEnqField("A", selectionOperation, null, 1, null);
        enquiry.getFields().add(field);
        actual = enquiryMapper.getBaseAppField(enquiry, null, "A", false, new ArrayList<String>());
        assertEquals("CUSTOMER", actual);
        
        // Recursive lookup of field should return the base field of the original variable
        enquiry = enquiryFactory.createEnquiry();
        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("CURRENCY");
        field = createEnqField("A", applicationFieldNameOperation, null, null, null);  // This is the root field
        enquiry.getFields().add(field);
        FieldExtractOperation fieldExtractOperation = enquiryFactory.createFieldExtractOperation();
        fieldExtractOperation.setField("A");
        field = createEnqField("B", fieldExtractOperation, null, null, null);
        enquiry.getFields().add(field);
        actual = enquiryMapper.getBaseAppField(enquiry, null, "B", false, new ArrayList<String>());
        assertEquals("CURRENCY", actual);
        
        // Lookup of a field defined using the field number should return the field name
        enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        FieldNumberOperation fieldNumberOperation = enquiryFactory.createFieldNumberOperation();
        fieldNumberOperation.setNumber(2);
        field = createEnqField("A", fieldNumberOperation, null, 1, null);
        enquiry.getFields().add(field);
        Application application = testHelper.getBasicAppAsApplication();
        actual = enquiryMapper.getBaseAppField(enquiry, application, "A", false, new ArrayList<String>());
        assertEquals("FROM.DATE", actual);
    }
    
    
    /**
     * Unit test (DS-7165)
     * Handle cyclic field definitions without StackOverflowError, eg where field1 refers to field2 which refers back to field1.
     * Or more simply where a field refers to itself.
     * getBaseAppField should return null for cyclic field definitions as they do not refer to any underlying application field.
     */
    @Test
    public void testGetBaseAppFieldHandlesCyclicFieldDefinitions() {
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        FieldExtractOperation fieldExtractOperation = enquiryFactory.createFieldExtractOperation();
        fieldExtractOperation.setField("CYCLIC");
        Field field = createEnqField("CYCLIC", fieldExtractOperation, null, 1, null);
        enquiry.getFields().add(field);
        String actual = enquiryMapper.getBaseAppField(enquiry, null, "CYCLIC", false, new ArrayList<String>());
        assertEquals(null, actual);
    }
    
    
    
    /**
     * Unit test
     * Test that only the right fixed selections are added to the resource 
     */
    @Test
    public void testAddFieldsFromFixedSelections() {
        // Setup the enquiry
        Enquiry enquiry = enquiryFactory.createEnquiry();
        enquiry.setCustomSelection(enquiryFactory.createSelectionExpression());
        FixedSelection fixedSelection;
        
        // Add a fixed selection with !CURRENT. - should be added
        fixedSelection = createEnqFixedSelection("AMOUNT", SelectionOperator.BETWEEN, "1000", "!CURRENT.MAX.AMOUNT");
        enquiry.getFixedSelections().add(fixedSelection);
        
        // Add a fixed selection with CURRENT. - should NOT be added as doesn't have '!'
        fixedSelection = createEnqFixedSelection("TYPE", SelectionOperator.EQUALS, "CURRENT.ACCOUNT");
        enquiry.getFixedSelections().add(fixedSelection);

        // Add a fixed selection with !EXT. - should NOT be added.
        // !EXT variables are internal variables should not be visible outside T24 for security reasons.
        fixedSelection = createEnqFixedSelection("CUSTOMER", SelectionOperator.EQUALS, "!EXT.CUSTOMER");
        enquiry.getFixedSelections().add(fixedSelection);
        
        // Exercise
        Application application = new Application(null);
        Resource resource = new Resource(RESOURCE_TYPE.enquiry, "SOME.ENQUIRY", "");
        enquiryMapper.addFieldsFromFixedSelections(resource, application, enquiry);
        
        // Examine output
        // Only one resourceField should exist
        assertEquals(1, resource.getFields().size());
        // Check it's properties. It should be called CURRENT.MAX.AMOUNT, be selection only, and mandatory
        ResourceField resourceField = resource.getFieldByT24Name("CURRENT.MAX.AMOUNT");
        assertEquals("{t24name=CURRENT.MAX.AMOUNT, selectionField=CURRENT.MAX.AMOUNT, mandatory=true, selectionOnly=true}", resourceField.toString());
        
        // TODO: test we get exception when current variable has same name as selection field
        // TODO: test we get exception when current variable has same name as result field
    }
    
    /**
     * Unit test
     * Test that custom selections are added to the resource with the right properties 
     */
    @Test
    public void testAddPropertiesFromUnderlyingApplication() {
        // Setup the enquiry
        Enquiry enquiry = createEnquiry("SOME.ENQUIRY", "SOME.APPLICATION");
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        Selection selection;
        enquiry.setCustomSelection(selectionExpression);
        
        // Create a selection field that's mandatory, but no operands
        selection = enquiryFactory.createSelection();
        selection.setField("@ID");
        selection.setMandatory(true);
        selectionExpression.getSelection().add(selection);
        
        // Create a complementary selection field: not mandatory, has operands
        selection = enquiryFactory.createSelection();
        selection.setField("ACCOUNT");
        selection.getOperands().add(SelectionOperator.EQUALS);
        selectionExpression.getSelection().add(selection);

        // Create an application to match our nofile enquiry
        List<AppField> appFields = new ArrayList<AppField>();
        AppField appField = new AppField();
        appField.setName("@ID");
        appField.setT24name("@ID");
        appFields.add(appField);
        appField = new AppField();
        appField.setName("ACCOUNT");
        appField.setT24name("ACCOUNT");
        appFields.add(appField);
        Application application = new Application("SOME.APPLICATION", "SomeModule", "SomeComponent", appFields);

        // Exercise
        Resource resource = new Resource(RESOURCE_TYPE.enquiry, "SOME.ENQUIRY", "");
        enquiryMapper.addFieldsFromUnderlyingApplication(resource, application, enquiry);
        
        // Examine output
        // Two fields should be created
        List<ResourceField> resourceFields = resource.getFields();
        assertEquals(2, resourceFields.size());
        // The first should be mandatory, the second should not. Neither should have selectionOnly
        assertEquals("{t24name=@ID, selectionField=@ID, primaryKey=true, mandatory=true}", resourceFields.get(0).toString());
        assertEquals("{t24name=ACCOUNT, selectionField=ACCOUNT}", resourceFields.get(1).toString());
    }
    
    @Test
    public void testAddPropertiesFromFields() {
        // Setup the enquiry
        Enquiry enquiry = enquiryFactory.createEnquiry();
        Field field;
        
        // Create a normal result field with (different) fieldname in operation, should be added and selectable
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("CURRENCY");
        field = createEnqField("A", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);
        
        // Create a literal type result field, should be added but not selectable
        ConstantOperation constantOperation = enquiryFactory.createConstantOperation();
        constantOperation.setValue("Some literal");
        field = createEnqField("B", constantOperation, null, 2, null);
        enquiry.getFields().add(field);
        
        // Exercise
        Application application = new Application(null);
        Resource resource = new Resource(RESOURCE_TYPE.enquiry, "SOME.ENQUIRY", "");
        enquiryMapper.addFieldsFromFields(resource, application, enquiry);
        
        // Examine output
        List<ResourceField> resourceFields = resource.getFields();
        assertEquals(2, resourceFields.size());
        // The first field should have selectionField, the second field should not
        assertEquals("{t24name=A, selectionField=CURRENCY}", resourceFields.get(0).toString());
        assertEquals("{t24name=B}", resourceFields.get(1).toString());
    }
    
    
    /**
     * In the generator we will assume that any field resulting from a TotalOperation is a float type number.
     * It could actually be totalling integers, in which case the result should really be integer, but we aren't being so sophisticated at first.
     */
    @Test
    public void testTotalFieldsAreMarkedAsNumeric() {
        // Setup the enquiry
        Enquiry enquiry = enquiryFactory.createEnquiry();
        Field field;
        
        // Create a result field with a TotalOperation, should result in a numeric resourceField
        TotalOperation totalOperation = enquiryFactory.createTotalOperation();
        totalOperation.setField("Blah"); // Doesn't really matter what we're totalling, we'll assume result is numeric.
        field = createEnqField("A", totalOperation, null, 1, null);
        enquiry.getFields().add(field);
        
        // Exercise
        Application application = new Application(null);
        Resource resource = new Resource(RESOURCE_TYPE.enquiry, "SOME.ENQUIRY", "");
        enquiryMapper.addFieldsFromFields(resource, application, enquiry);
        
        // Examine output
        List<ResourceField> resourceFields = resource.getFields();
        assertEquals("NUMBER", resourceFields.get(0).getValueType());
    }
    
    
    /**
     * Unit test (DS-7491)
     * If processingMode = 'Multi' then the field should be put in a multivalue group, even if it it is a calculated field or based on a calculated field.
     */
    @Test
    public void testProcessingModeMultiGeneratesMvGroups() {
        // Setup the enquiry
        Enquiry enquiry = enquiryFactory.createEnquiry();
        Field field;
        
        // Create a constant field, has no underlying application field
        ConstantOperation constantOperation = enquiryFactory.createConstantOperation();
        constantOperation.setValue("Constant Value");
        field = createEnqField("CONSTANT", constantOperation, null, null, null);
        enquiry.getFields().add(field);
        
        // Create a field based on the constant, but has processingMode = M
        FieldExtractOperation fieldExtractOperation = enquiryFactory.createFieldExtractOperation();
        fieldExtractOperation.setField("CONSTANT");
        field = createEnqField("MVCONST", fieldExtractOperation, null, 1, null);
        field.setSingleMulti(ProcessingMode.MULTI);
        enquiry.getFields().add(field);
        
        // Exercise
        Application application = new Application(null);
        Resource resource = new Resource(RESOURCE_TYPE.enquiry, "SOME.ENQUIRY", "");
        enquiryMapper.addFieldsFromFields(resource, application, enquiry);
        
        // Examine output
        // The mvconst field must be in a mvGroup = MVCONST
        List<ResourceField> resourceFields = resource.getFields();
        assertEquals("MVCONST", resourceFields.get(0).getMvGroup());
    }
    
    
    @Test
    public void testSetPrimaryKey() {
        
        /* 
         * Test an enquiry that has a primary key
         * Add a result field that is marked as the primary key - this should be left as is and no new fields added
         */
        Enquiry enquiry = null;
        Resource resource = new Resource(RESOURCE_TYPE.enquiry, "SOME.ENQUIRY", "");
        resource.addField("A", "A", "", "", "", "", true, false, "", false, false, "");
        enquiryMapper.addPrimaryKey(resource, enquiry);
        List<ResourceField> resourceFields = resource.getFields();
        assertEquals(1, resourceFields.size());
        assertEquals("{t24name=A, selectionField=A, primaryKey=true}", resourceFields.get(0).toString());

        /*
         * Test an enquiry that has no primary keys
         * Add a result field not marked as the primary key
         * We need to check a new field gets created called PrimaryKey
         */
        enquiry = createEnquiry("SOME.ENQUIRY", "SOME.APPLICATION"); // Anything that isn't NOFILE.
        resource = new Resource(RESOURCE_TYPE.enquiry, "SOME.ENQUIRY", "");
        resource.addField("A", "A", "", "", "", "", false, false, "", false, false, "");
        enquiryMapper.addPrimaryKey(resource, enquiry);
        resourceFields = resource.getFields();
        assertEquals(2, resourceFields.size());
        assertEquals("{t24name=A, selectionField=A}", resourceFields.get(0).toString());
        assertEquals("{t24name=PrimaryKey, selectionField=@ID, primaryKey=true}", resourceFields.get(1).toString());
    }
    
    
    
    
    /**
     * Integration test (for DS-7161)
     * For When a field has a CONVERSION it is unlikely for the multiplicity of the field to change, 
     * except when the field has a VALUE type conversion (ValueConversion.
     * Hence the cartridge should use the multiplicity of the underlying field, (though not the data type, because that can change sometimes)
     */
    @Test
    public void testFieldWithConversionInheritsMultiplicityOfUnderlyingApplicationField() {
        // Set up an enquiry that has multiple primary keys
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        
        // Output the primary key - just so it doesn't try generating new ones
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("ACCOUNT.ID");
        Field field = createEnqField("ACCOUNT.ID", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Output the LOCKED.AMOUNT, but with an AbsConversion.
        // The resulting field in the generated output should be part of the FROM.DATE multivalue group, even though it has a conversion
        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("LOCKED.AMOUNT");
        AbsConversion absConversion = enquiryFactory.createAbsConversion();
        field = createEnqField("ABS.CONVERSION", applicationFieldNameOperation, absConversion, 2, null);
        enquiry.getFields().add(field);

        // Create a field that uses ValueConversion but is not output.
        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("LOCKED.AMOUNT");
        ValueConversion valueConversion = enquiryFactory.createValueConversion();
        valueConversion.setValue(1);
        field = createEnqField("VALUE.CONVERSION", applicationFieldNameOperation, valueConversion, null, null);
        enquiry.getFields().add(field);

        // Create a field that is based on VALUE.CONVERSION field but has no CONVERSION.
        // It should not be part of the FROM.DATE multivalue group
        FieldExtractOperation fieldExtractOperation = enquiryFactory.createFieldExtractOperation();
        fieldExtractOperation.setField("VALUE.CONVERSION");
        field = createEnqField("BASED.ON.VALUE.CONVERSION", fieldExtractOperation, null, 3, null);
        enquiry.getFields().add(field);
        
        // Set up the base application to indicate that ACCOUNT.ID is the primary key
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();

        // Exercise
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);

        // Examine output.
        // 1) Field ABS.CONVERSION must be inside group FromDateMvGroup even though it has a conversion
        EMProperty fromDateProperty = EMEntityHelper.getProperty(entity, "FromDateMvGroup");
        EMProperty absConversionProperty = EMEntityHelper.getProperty(fromDateProperty, "AbsConversion");
        assertEquals("AbsConversion", absConversionProperty.getName());
        // 2) Field BASED.ON.CONVERSION is single value property because it is based on a field with a ValueConversion
        EMProperty basedOnValueConversionProperty = EMEntityHelper.getProperty(entity, "BasedOnValueConversion");
        assertEquals("BasedOnValueConversion", basedOnValueConversionProperty.getName());
    }
    
    
    /**
     * Integration test (for DS-7217)
     * An enquiry had field '@ID' defined in custom selection and 'ID' defined in the fields.
     * This resulted in two fields called 'Id' in the resource.
     * Since both fields have the same 'Iris' name, the value 'Id' should be used for equality checks instead of the t24Name.
     */
    @Test
    public void testIrisNameUsedForEqualityInResourceField() {
        // Set up an enquiry that has multiple primary keys
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        
        // Create a custom selection field of '@ID'
        Selection selection = enquiryFactory.createSelection();
        selection.setField("@ID");
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        selectionExpression.getSelection().add(selection);
        enquiry.setCustomSelection(selectionExpression);
        
        // Create a field called 'ID'
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("@ID");
        Field field = createEnqField("ID", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Set up the base application to indicate that ACCOUNT.ID is the primary key
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();

        // Exercise
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);

        // Examine output. We expect just 1 instance of 'Id'
        String propertyNames = "";
        for (EMProperty emProperty : entity.getProperties()) {
        	propertyNames += emProperty.getName() + ", ";
        }
        assertEquals("Id, AccountId, ShortTitle, FromDateMvGroup, ", propertyNames);
    }
        
    
    /**
     * Integration test
     * Check that when a field is exposed as a selection field and result field it is selectable and displayable, and only added once.
     * As of DS-8380, when a result field has same name as a selection field but a different value, we don't mark it displayOnly because
     * some composite screens and drilldowns expect to be able to filter on such fields. We just make sure the data type is String.
     * Also check that the primary key is generated and selectable with @ID.
     */
    @Test
    public void testSelectionFieldMergesWithResultField() {
        // Setup the enquiry
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        enquiry.setCustomSelection(selectionExpression);

        // Add FROM.DATE as a selection and unmodified result field, so field is selectable and displayable, and has data type
        Selection selection = enquiryFactory.createSelection();
        selection.setField("FROM.DATE");
        selectionExpression.getSelection().add(selection);
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("FROM.DATE");
        Field field = createEnqField("FROM.DATE", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Add LOCKED.AMOUNT as selection field and modified(!) result field, the entity property should be selectable and displayable, but have data type String
        selection = enquiryFactory.createSelection();
        selection.setField("LOCKED.AMOUNT");
        selectionExpression.getSelection().add(selection);
        ConstantOperation constantOperation = enquiryFactory.createConstantOperation();
        constantOperation.setValue("A literal");
        field = createEnqField("LOCKED.AMOUNT", constantOperation, null, 2, null);
        enquiry.getFields().add(field);
        
        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        fieldTypeChecker.setText("ACCOUNT/FROM.DATE\r\nACCOUNT/LOCKED.AMOUNT");  // Add both fields to typeSafe list so that type info exists for both (but shouldn't apply to LOCKED.AMOUNT)
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
		assertEquals("{FromDate, FROM.DATE, TERM_VALUE_TYPE=DATE}", EMEntityHelper.getProperty(entity, "FromDate").toString());
		assertEquals("{LockedAmount, LOCKED.AMOUNT}", EMEntityHelper.getProperty(entity, "LockedAmount").toString());
		assertEquals("{PrimaryKey, PrimaryKey, selectionField: @ID, TERM_ID_FIELD=true}", EMEntityHelper.getProperty(entity, "PrimaryKey").toString());
    }
    
    
    /**
     * Integration test
     * Check that when an enquiry has multiple fields that are the primary key, only one primary key is added.
     * Also that no PrimaryKey field is generated.
     */
    @Test
    public void testMultiplePrimaryKeys() {
        // Set up an enquiry that has multiple primary keys
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        // Add a selection field that is marked as the primary key
        Selection selection = enquiryFactory.createSelection();
        selection.setField("ACCOUNT.ID");
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        selectionExpression.getSelection().add(selection);
        enquiry.setCustomSelection(selectionExpression);
        // Add a result field that is not a primary key
        ConstantOperation constantOperation = enquiryFactory.createConstantOperation();
        constantOperation.setValue("AccountNo");
        Field field = createEnqField("LABEL1", constantOperation, null, 1, null);
        enquiry.getFields().add(field);
        // Add a result field that is marked as the primary key
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("ACCOUNT.ID");
        field = createEnqField("ACCOUNT.NO", applicationFieldNameOperation, null, 2, null);
        enquiry.getFields().add(field);

        // Set up the base application to indicate that ACCOUNT.ID is the primary key
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();

        // Exercise
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);

        // Examine output
        // AccountNo should be the primary key, should also have the selectionField property
        assertEquals("{AccountNo, ACCOUNT.NO, selectionField: ACCOUNT.ID, TERM_ID_FIELD=true}", EMEntityHelper.getProperty(entity, "AccountNo").toString());
        // AccountId should not be marked as the primary key
        assertEquals("{AccountId, ACCOUNT.ID}", EMEntityHelper.getProperty(entity, "AccountId").toString());
        // Label field should not be marked as the primary key
        assertEquals("{Label1, LABEL1, selectable: false, TERM_RESTRICTION=displayOnly}", EMEntityHelper.getProperty(entity, "Label1").toString());
        // PrimaryKey should not be present
        assertNull(EMEntityHelper.getProperty(entity, "PrimaryKey"));
    }
    
    
    /**
     * Integration test
     * Check that type information is generated only for fields listed in the typeSafeFields.txt
     */
    @Test
    public void testDataTypeChecker() {
        
        // Set up an enquiry that has fields of different types
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("ACCOUNT.ID");
        Field field = createEnqField("ACCOUNT.NO", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);
        
        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("SHORT.TITLE");
        field = createEnqField("SHORT.TITLE", applicationFieldNameOperation, null, 2, null);
        enquiry.getFields().add(field);

        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("FROM.DATE");
        field = createEnqField("FROM.DATE", applicationFieldNameOperation, null, 3, null);
        enquiry.getFields().add(field);

        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("LOCKED.AMOUNT");
        field = createEnqField("LOCKED.AMOUNT", applicationFieldNameOperation, null, 4, null);
        enquiry.getFields().add(field);

        // Set up the base application to indicate that ACCOUNT.ID is the primary key
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        fieldTypeChecker.setText("ACCOUNT/FROM.DATE");

        // Check that type information gets generated when flag is true
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        // Check that DATE does have a data type because it is in the list
        assertEquals("DATE", entity.getProperties().get(2).getSubProperties().get(0).getVocabularyTerms().get(0).getValue());
        // Check that the LOCKED.AMOUNT does not have a data type because it is not in the list
        assertEquals(0, entity.getProperties().get(2).getSubProperties().get(1).getVocabularyTerms().size());
    }
    
    /**
     * Integration test
     * Check that a nofile enquiry has all the special properties we expect:
     *    a) SelectionOnly fields
     *    b) PrimaryKey added and marked as selectable: false
     */
    @Test
    public void testNofileEnquiry() {
        // Set up an enquiry that has multiple primary keys
        // Result should be that only the selection field is the primary key
        Enquiry enquiry = createEnquiry("SOME.NOFILE", "NOFILE.ACCOUNT");
        // Add a selection field
        Selection selection = enquiryFactory.createSelection();
        selection.setField("ACC.ID");
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        selectionExpression.getSelection().add(selection);
        enquiry.setCustomSelection(selectionExpression);
        // Add a result field
        ConstantOperation constantOperation = enquiryFactory.createConstantOperation();
        constantOperation.setValue("AccountNo");
        Field field = createEnqField("LABEL1", constantOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Create an application to match our nofile enquiry
        List<AppField> appFields = new ArrayList<AppField>();
        AppField appField = new AppField();
        appField.setName("ACC_ID");
        appField.setT24name("ACC.ID");
        appFields.add(appField);
        Application application = new Application("NOFILE.ACCOUNT", "SomeModule", "SomeComponent", appFields);

        // Exercise
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        List<EMProperty> actualProperties = entity.getProperties();
        assertEquals(3, actualProperties.size());
        assertEquals("{AccId, ACC.ID, selectionOnly: true, TERM_RESTRICTION=filterOnly}", EMEntityHelper.getProperty(entity, "AccId").toString());
        assertEquals("{Label1, LABEL1, selectable: false, TERM_RESTRICTION=displayOnly}", EMEntityHelper.getProperty(entity, "Label1").toString());
        assertEquals("{PrimaryKey, PrimaryKey, selectable: false, TERM_ID_FIELD=true, TERM_RESTRICTION=displayOnly}", EMEntityHelper.getProperty(entity, "PrimaryKey").toString());
    }



    /**
     * Integration test
     * DS-8701: Check that when an enquiry uses different case field name to underlying application field name, that the enquiry field name takes priority
     */
    @Test
    public void testFieldNameCaseTakesPriorityOverUnderlyingApplicationFieldNameCase() {
        // Set up an enquiry that has multiple primary keys
        // Result should be that only the selection field is the primary key
        Enquiry enquiry = createEnquiry("MONTH.LIST", "CALENDAR");
        // Add a result field with same name but different case as the underlying SS record
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("0");
        Field field = createEnqField("Month", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Create an application to match our nofile enquiry
        List<AppField> appFields = new ArrayList<AppField>();
        AppField appField;
        appField = new AppField();			appField.setName("ID");        appField.setT24name("ID");        appFields.add(appField);
        appField = new AppField();			appField.setName("YEAR");      appField.setT24name("YEAR");      appFields.add(appField);
        appField = new AppField();          appField.setName("MONTH");     appField.setT24name("MONTH");     appFields.add(appField);
        appField = new AppField();          appField.setName("DAY");       appField.setT24name("DAY");       appFields.add(appField);
        Application application = new Application("CALENDAR", "SomeModule", "SomeComponent", appFields);

        // Exercise
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);

        // Examine output
        assertEquals("Month", EMEntityHelper.getProperty(entity, "Month").getT24Name());
    }
    
    
    /**
     * Integration test
     * DS-8701: Check that a NOFILE only adds customSelection fields, not underlying application fields
     */
    @Test
    public void testNofileEnquiryDoesntAddUnderlyingApplicationFields() {
        // Set up an enquiry that has multiple primary keys
        // Result should be that only the selection field is the primary key
        Enquiry enquiry = createEnquiry("MONTH.LIST", "NOFILE.CALENDAR");
        // Add a result field with same name but different case as the underlying SS record
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("0");
        Field field = createEnqField("FIELD1", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Create an application to match our nofile enquiry
        List<AppField> appFields = new ArrayList<AppField>();
        AppField appField;
        appField = new AppField();			appField.setName("ID");        appField.setT24name("ID");        appFields.add(appField);
        appField = new AppField();			appField.setName("YEAR");      appField.setT24name("YEAR");      appFields.add(appField);
        appField = new AppField();          appField.setName("MONTH");     appField.setT24name("MONTH");     appFields.add(appField);
        appField = new AppField();          appField.setName("DAY");       appField.setT24name("DAY");       appFields.add(appField);
        Application application = new Application("NOFILE.CALENDAR", "SomeModule", "SomeComponent", appFields);

        // Exercise
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);

        // Examine output
        // There should only be the one field - Month, because we should not automatically add underlying application fields for NOFILEs
        assertEquals(2, entity.getProperties().size());  // PrimaryKey and Month
        assertEquals("FIELD1", EMEntityHelper.getProperty(entity, "Field1").getT24Name());
        assertEquals("PrimaryKey", EMEntityHelper.getProperty(entity, "PrimaryKey").getName());
    }




    /**
     * Integration test
     * Check that an enquiry with @ID in the operation is marked as the primary key
     */
    @Test
    public void testAtIdTreatedAsPrimaryKey() {
        // Set up an enquiry that has multiple primary keys
        // Result should be that only the selection field is the primary key
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("@ID");
        Field field = createEnqField("ACCOUNT.NO", applicationFieldNameOperation, null, 2, null);
        enquiry.getFields().add(field);

        // Set up the base application to indicate that ACCOUNT.ID is the primary key
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();

        // Exercise
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        // AccountNo should be marked as the primary key
        assertEquals("true", EMEntityHelper.getTermValue(entity, "AccountNo", EMTermType.ID_TERM));
        // PrimaryKey field should not be added
        assertNull(EMEntityHelper.getProperty(entity, "PrimaryKey"));
    }
    
    /**
     * Integration test
     * Check that data types are added to the entity correctly
     */
    @Ignore  // TODO: stop ignoring this test when we resume data type support (eg Resource.toEMEntity())
    @Test
    public void testDataTypeHandled() {
        // Set up an enquiry that has a field related to an application field with a date data type
        // Result should be that the entity has a property with TERM_VALUE_TYPE=DATE
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("FROM.DATE");
        Field field = createEnqField("FROM.DATE", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Set up the base application to indicate that ACCOUNT.ID is the primary key
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();

        // Exercise
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        List<EMProperty> actualProperties = entity.getProperties();
        assertEquals(2, actualProperties.size());
        EMProperty fromDate = actualProperties.get(0).getSubProperties().get(0);
        assertEquals(new EMTerm(EMTermType.TYPE_TERM, "DATE"), fromDate.getVocabularyTerms().get(0));
    }
    
    
    /**
     * Integration test
     * Check that multivalue types are handled correctly
     * Set up an enquiry that has a field related to an application field
     * The application field is part of a multivalue group, but the field is not the first in the group.
     * Result should be that the entity has a property with the name of the first field in the group, and a sub property with the name of the field.
     */
    @Test
    public void testMultivaluesHandled() {
        // Set up the enquiry
        // It has field LOCK.AMT
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("LOCKED.AMOUNT");
        Field field = createEnqField("LOCK.AMT", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);

        // Add a custom selection so we don't generate all underlying application fields
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        enquiry.setCustomSelection(selectionExpression);
        Selection selection = enquiryFactory.createSelection();
        selection.setField("ACCOUNT.ID");
        selectionExpression.getSelection().add(selection);
        
        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        EMProperty fromDateMvGroup = EMEntityHelper.getProperty(entity, "FromDateMvGroup");
        assertEquals("{FromDateMvGroup, TERM_LIST_TYPE=true}", fromDateMvGroup.toString());
        assertEquals("{LockAmt, LOCK.AMT, selectionField: LOCKED.AMOUNT}", EMEntityHelper.getProperty(fromDateMvGroup, "LockAmt").toString());
    }
    
    
    /**
     * Integration test
     * Check that multivalue types are handled correctly - parent properties not duplicated
     * Set up an enquiry that has two fields in an mvGroup
     * Result should be that only one property for the mvGroup is created, containing both items
     */
    @Test
    public void testMultivaluesNotDuplicated() {
        // Set up the enquiry
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        // Add a first field from the FROM.DATE group
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("FROM.DATE");
        Field field = createEnqField("FROM.DATE", applicationFieldNameOperation, null, 1, null);
        enquiry.getFields().add(field);
        // Add a second field from the FROM.DATE group
        applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("LOCKED.AMOUNT");
        field = createEnqField("LOCK.AMT", applicationFieldNameOperation, null, 2, null);
        enquiry.getFields().add(field);

        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output - there should only be one FromDateMvGroup containing both FromDate and LockAmt
        List<EMProperty> actualProperties = entity.getProperties();
        EMProperty fromDateMvGroup = null;
        for (EMProperty emProperty : actualProperties) {
        	if (emProperty.getName().equals("FromDateMvGroup")) {
        		assertNull(fromDateMvGroup);   // If there are two groups - this check will fail
        		fromDateMvGroup = emProperty;
        	}
        }
        assertEquals("{FromDateMvGroup, TERM_LIST_TYPE=true}", fromDateMvGroup.toString());
        assertEquals("{FromDate, FROM.DATE}", EMEntityHelper.getProperty(fromDateMvGroup, "FromDate").toString());
        assertEquals("{LockAmt, LOCK.AMT, selectionField: LOCKED.AMOUNT}", EMEntityHelper.getProperty(fromDateMvGroup, "LockAmt").toString());
    }
    
    
    
    /**
     * Integration test
     * 1) All fields from the underlying application should be added as 'filterOnly' fields with selectionOnly=true, except...
     * 2) If a field is listed in custom selections, then it should not have the 'selectionOnly' property (ie it will be output by T24)
     */
    @Test
    public void testAllUnderlyingApplicationFieldsAreAdded() {
        // Set up the enquiry
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");

        // Add a displayOnly field
        ConstantOperation constantOperation = enquiryFactory.createConstantOperation();
        constantOperation.setValue("Account Number: ");
        Field field = createEnqField("ACCOUNT.LABEL", constantOperation, null, 10, null);
        enquiry.getFields().add(field);
        
        // Add a 'both' field
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("ACCOUNT.ID");
        field = createEnqField("ACCOUNT.ID", applicationFieldNameOperation, null, 20, null);
        enquiry.getFields().add(field);
        
        // Add a custom selection - this field should not have 'selectionOnly=true'
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        enquiry.setCustomSelection(selectionExpression);
        Selection selection = enquiryFactory.createSelection();
        selection.setField("SHORT.TITLE");
        selectionExpression.getSelection().add(selection);

        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        // 1) ShortTitle should be present with no TERM_RESTRICTION as T24 will add it to the output since it's a custom selection
        // 2) AccountLabel should have TERM_RESTRICTION=displayOnly as it's a generated output field
        // 3) FromDate should be present with TERM_RESTRICTION=filterOnly as it's an added underlying application field, won't be output
        assertEquals("{ShortTitle, SHORT.TITLE}", EMEntityHelper.getProperty(entity, "ShortTitle").toString());
        assertEquals("{AccountLabel, ACCOUNT.LABEL, selectable: false, TERM_RESTRICTION=displayOnly}", EMEntityHelper.getProperty(entity, "AccountLabel").toString());
        assertEquals("{FromDate, FROM.DATE, selectionOnly: true, TERM_RESTRICTION=filterOnly}", EMEntityHelper.getProperty(entity, "FromDate").toString());
    }
    
    
    
    /**
     * Integration test
     * Check that the TERM_RESTRICTION = displayOnly is added to displayOnly fields
     * displayOnly fields are values for which there is not an exact match in the underlying record.
     * eg literal values, fields with a CONVERSION or NOFILE fields are displayOnly 
     */
    @Test
    public void testDisplayOnlyTerm() {
        // Set up the enquiry
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");

        // Add a displayOnly field
        ConstantOperation constantOperation = enquiryFactory.createConstantOperation();
        constantOperation.setValue("Account Number: ");
        Field field = createEnqField("ACCOUNT.LABEL", constantOperation, null, 10, null);
        enquiry.getFields().add(field);
        
        // Add a 'both' field
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("ACCOUNT.ID");
        field = createEnqField("ACCOUNT.ID", applicationFieldNameOperation, null, 20, null);
        enquiry.getFields().add(field);
        
        // Add a custom selection so we don't generate all underlying application fields
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        enquiry.setCustomSelection(selectionExpression);
        Selection selection = enquiryFactory.createSelection();
        selection.setField("ACCOUNT.ID");
        selectionExpression.getSelection().add(selection);
        
        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        // We should have a displayOnly field called AccountLabel
        assertEquals("displayOnly", EMEntityHelper.getTermValue(entity, "AccountLabel", "TERM_RESTRICTION"));
    }
    
    
    
    /**
     * Integration test
     * Check that the TERM_RESTRICTION = filterOnly is added to filterOnly fields
     * filterOnly fields are values which can only be used in $filter and are not returned by the enquiry
     * eg NOFILE enquiry custom selection fields cannot be displayed unless there is a definition for them in the enquiry fields.
     * another eg: when enquiry has no custom selection, all fields from the underlying application should be added as filterOnly fields.
     */
    @Test
    public void testFilterOnlyTerm() {
        // Set up the enquiry
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");

        // Add the primary key (we need at least one display field)
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("ACCOUNT.ID");
        Field field = createEnqField("ACCOUNT.ID", applicationFieldNameOperation, null, 20, null);
        enquiry.getFields().add(field);
        
        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        // We should have a filterOnly field called SHORT.TITLE
        assertEquals("filterOnly", EMEntityHelper.getTermValue(entity, "ShortTitle", "TERM_RESTRICTION"));
    }
    
    
    /**
     * Integration test
     * When an enquiry 
     * 		1) has no custom selection criteria
     * 		2) is displaying the Id field with a different name to ID.F
     * Then the generated metadata should contain the ID.F as a selectionOnly field and should not be marked as the primary key.
     * The primary key part is important because the ID.F field won't be returned when running the enquiry, the displayed field will be.
     */
    @Test
    public void testAddedIdfNotMarkedAsPrimaryKey() {
        // Set up the enquiry
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");

        // Add the primary key with different field name
        ApplicationFieldNameOperation applicationFieldNameOperation = enquiryFactory.createApplicationFieldNameOperation();
        applicationFieldNameOperation.setField("ACCOUNT.ID");
        Field field = createEnqField("ACC.ID", applicationFieldNameOperation, null, 10, null);
        enquiry.getFields().add(field);
        
        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        // The ACCOUNT.ID field should be present but should not be the primary key
        assertEquals(false, EMEntityHelper.getProperty(entity, "AccountId").hasVocabularyTerm(EMTermType.ID_TERM));
        // The ACC.ID field should be the primary key
        assertEquals(true, EMEntityHelper.getProperty(entity, "AccId").hasVocabularyTerm(EMTermType.ID_TERM));
    }
    
    
 
    
    
    /**
     * Integration test
     * Check that multivalue types are handled correctly when a mv field is in a custom selection
     */
    @Test
    public void testMultivaluesInCustomSelectionHandled() {
        Enquiry enquiry = createEnquiry("ACCOUNT-LIST", "ACCOUNT");
        SelectionExpression selectionExpression = enquiryFactory.createSelectionExpression();
        enquiry.setCustomSelection(selectionExpression);
        Selection selection = enquiryFactory.createSelection();
        selection.setField("LOCKED.AMOUNT");
        selectionExpression.getSelection().add(selection);
        
        // Exercise
        Application application = testHelper.getBasicAppAsApplication();
        FieldTypeChecker fieldTypeChecker = new FieldTypeChecker();
        EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
        
        // Examine output
        EMProperty fromDate = EMEntityHelper.getProperty(entity, "FromDateMvGroup");
        assertEquals("{FromDateMvGroup, TERM_LIST_TYPE=true}", fromDate.toString());
        assertEquals("{LockedAmount, LOCKED.AMOUNT}", EMEntityHelper.getProperty(entity, "LockedAmount").toString());
    }
    

    
    private Field createEnqField(String name, Operation operation, Conversion conversion, Integer column, BreakKind breakKind) {
        Field field = enquiryFactory.createField();
        field.setName(name);
        
        if (operation != null) {
            field.setOperation(operation);
        }
        
        if (conversion != null) {
            field.getConversion().add(conversion);
        }
        
        if (column != null) {
            FieldPosition fieldPosition = enquiryFactory.createFieldPosition();
            fieldPosition.setColumn(column.intValue());
            field.setPosition(fieldPosition);
        }
        
        if (breakKind != null) {
            BreakCondition breakCondition = enquiryFactory.createBreakCondition();
            breakCondition.setBreak(breakKind);
            field.setBreakCondition(breakCondition);
        }
        
        return field;
    }
    
    
    private FixedSelection createEnqFixedSelection(String fieldname, SelectionOperator operator, String ... values) {
        FixedSelection fixedSelection = enquiryFactory.createFixedSelection();
        fixedSelection.setField(fieldname);
        fixedSelection.setOperand(operator);
        for (String value : values) {
            fixedSelection.getValues().add(value);
        }
        return fixedSelection;
    }


    /** Convenience method for creating an enquiry and setting it's name and fileName in one line
     * @param name
     * @param filename
     * @return
     */
    private Enquiry createEnquiry(String name, String filename) {
        Enquiry enquiry = enquiryFactory.createEnquiry();
        enquiry.setName(name);
        enquiry.setFileName("name:/EB_Something:" + filename + "#");
        return enquiry;
    }


}

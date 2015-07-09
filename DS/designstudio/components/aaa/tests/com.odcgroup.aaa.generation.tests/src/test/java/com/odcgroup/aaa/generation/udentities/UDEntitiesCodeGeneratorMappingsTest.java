package com.odcgroup.aaa.generation.udentities;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.aaa.core.UDEHelper;
import com.odcgroup.aaa.generation.gateway.line.CMDType;
import com.odcgroup.aaa.generation.gateway.line.DAT;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.ecore.ECoreModelFactory;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfReverseAssociationImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;

public class UDEntitiesCodeGeneratorMappingsTest {
	
	private static final String TEST_DOMAIN = "TestDomain";
	private UDEntitiesCodeGenerator generator;
	private MdfDomainImpl testDomain;
	
	@Before
	public void setUp() {
		generator = new UDEntitiesCodeGenerator();
		testDomain = (MdfDomainImpl)ECoreModelFactory.INSTANCE.createMdfDomain();
		testDomain.setName(TEST_DOMAIN);
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void testGenerate_xd_entity() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("UDEClass");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass, "SomeEntitySQLName");
		AAAAspectDS.setTripleAEntityName(mdfClass, "SomeEntityName");
		
		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_entity(mdfClass, cmds);
		
		// Then
		Assert.assertNotNull("Should have populated xd_entity", cmds.xd_entity);
		Assert.assertEquals("Wrong entity name generated", "xd_entity", cmds.xd_entity.getEntityName());
		Assert.assertEquals("Wrong update type generated", CMDType.INSUPDDEF, cmds.xd_entity.getType());

		Assert.assertEquals("Wrong column name", "sqlname_c", cmds.xd_entity.getATT().getColumnNames().get(0));
		Assert.assertEquals("Wrong column name", "name", cmds.xd_entity.getATT().getColumnNames().get(1));
		
		Assert.assertEquals("Wrong number of DAT returned", 1, cmds.xd_entity.getATT().getDATs().size());
		Assert.assertEquals("Wrong sqlname_c returned", "SomeEntitySQLName", cmds.xd_entity.getATT().getDATs().get(0).getData().get(0).toString());
		Assert.assertEquals("Wrong name returned", "SomeEntityName", cmds.xd_entity.getATT().getDATs().get(0).getData().get(1).toString());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGenerate_xd_entity_security() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("UDEClass");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass, "SomeEntitySQLName");
		AAAAspectDS.setTripleAEntityName(mdfClass, "SomeEntityName");
		AAAAspectDS.setTripleAEntitySecured(mdfClass, true);		
		testDomain.getClasses().add(mdfClass);
		
		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_security(testDomain, cmds);
		
		// Then
		Assert.assertNotNull("Should have populated xd_entity_security", cmds.xd_entity_security);
		Assert.assertNotNull("Should have populated xd_attribute_security", cmds.xd_attribute_security);
		Assert.assertNotNull("Should have populated xd_attribute_security_rights", cmds.xd_attribute_security_rights);
		
		Assert.assertEquals("Wrong number of DAT returned", 1, cmds.xd_entity_security.getATT().getDATs().size());
		Assert.assertEquals("Wrong number of DAT returned", 1, cmds.xd_attribute_security.getATT().getDATs().size());
		Assert.assertEquals("Wrong number of DAT returned", 2, cmds.xd_attribute_security_rights.getATT().getDATs().size());
	}

	@Test
	public void testGenerate_xd_attribute_datatype() {
		// Given
		MdfClassImpl mdfClass1 = createMdfClass("UDEClass1");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass1, "Class1_SomeEntitySQLName");
		AAAAspectDS.setTripleAEntityName(mdfClass1, "SomeEntityName1");

		MdfClassImpl mdfClass2 = createMdfClass("UDEClass2");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass2, "Class2_SomeEntitySQLName");
		AAAAspectDS.setTripleAEntityName(mdfClass2, "SomeEntityName2");

		MdfAssociationImpl association = addMdfAssociation(mdfClass1, "assoc", mdfClass2, MdfConstants.MULTIPLICITY_ONE);
		AAAAspectDS.setTripleAAttributeDataType(association, "dict_t");
		
		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_attribute(association,   cmds);
		
		// Then
		Assert.assertNotNull("Should have populated xd_attribute", cmds.xd_attribute);
		Assert.assertEquals("Wrong entity name generated", "xd_attribute", cmds.xd_attribute.getEntityName());
		Assert.assertEquals("Wrong update type generated", CMDType.INSUPDDEF, cmds.xd_attribute.getType());

		DAT association4dat = cmds.xd_attribute.getATT().getDATs().get(0);
		Assert.assertEquals("Wrong datatype generated", "dict_t", association4dat.getData().get(3).toString());
		
	}
	
	@Test
	public void testGenerate_xd_attribute() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("UDEClass");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass, "Class_SomeEntitySQLName");

		MdfEnumerationImpl btEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		MdfEnumValueImpl btEnumValue = createMdfEnumValue(btEnum, "btName", "btValue");
		MdfBusinessTypeImpl businessType = createMdfBusinessType("SomeBT", btEnum);
		AAAAspectDS.setTripleABusinessType(businessType, "SomeBType");
		
		MdfAttributeImpl attribute1 = addMdfAttribute(mdfClass, "attrib1", businessType);
		AAAAspectDS.setTripleAAttributeSQLName(attribute1, "Attribute1_SomeAttrSQLName");
		AAAAspectDS.setTripleAAttributeName(attribute1, "Attribute1_SomeAttrName");
		attribute1.setDefault(btEnumValue.getName());

		MdfEnumerationImpl intEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		MdfEnumValueImpl intEnumValue = createMdfEnumValue(intEnum, "intName", "123");
		MdfAttributeImpl attribute2 = addMdfAttribute(mdfClass, "attrib2", intEnum);
		AAAAspectDS.setTripleAAttributeSQLName(attribute2, "Attribute2_SomeAttrSQLName");
		AAAAspectDS.setTripleAAttributeName(attribute2, "Attribute2_SomeAttrName");
		attribute2.setPrimaryKey(true);
		attribute2.setRequired(true);
		attribute2.setBusinessKey(true);
		attribute2.setDefault(intEnumValue.getName());

		MdfEnumerationImpl booleanEnum = createMdfEnum(PrimitivesDomain.BOOLEAN_OBJ);
		MdfEnumValueImpl yesEnumValue = createMdfEnumValue(booleanEnum, "yes", "true");
		createMdfEnumValue(booleanEnum, "no", "false");
		MdfAttributeImpl attribute3 = addMdfAttribute(mdfClass, "attrib3", booleanEnum);
		AAAAspectDS.setTripleAAttributeSQLName(attribute3, "Attribute3_SomeAttrSQLName");
		AAAAspectDS.setTripleAAttributeName(attribute3, "Attribute3_SomeAttrName");
		attribute3.setDefault(yesEnumValue.getName());
		
		MdfClassImpl mdfClass2 = createMdfClass("UDEClass2");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass2, "Class2_SomeEntitySQLName");
		MdfAssociationImpl association4 = addMdfAssociation(mdfClass, "assoc4", mdfClass2, MdfConstants.MULTIPLICITY_MANY);
		AAAAspectDS.setTripleAAttributeSQLName(association4, "Association4_SomeAttrSQLName");
		AAAAspectDS.setTripleAAttributeName(association4, "Association4_SomeAttrName");

		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_attribute(attribute1,   cmds);
		generator.generate_xd_attribute(attribute2,   cmds);
		generator.generate_xd_attribute(attribute3,   cmds);
		generator.generate_xd_attribute(association4, cmds);
		
		// Then
		Assert.assertNotNull("Should have populated xd_attribute", cmds.xd_attribute);
		Assert.assertEquals("Wrong entity name generated", "xd_attribute", cmds.xd_attribute.getEntityName());
		Assert.assertEquals("Wrong update type generated", CMDType.INSUPDDEF, cmds.xd_attribute.getType());

		Assert.assertEquals("Wrong number of columns", 12, cmds.xd_attribute.getATT().getColumnNames().size());
		
		Assert.assertEquals("Wrong column name", "sqlname_c",                  cmds.xd_attribute.getATT().getColumnNames().get(0));
		Assert.assertEquals("Wrong column name", "xd_entity_id.sqlname_c",     cmds.xd_attribute.getATT().getColumnNames().get(1));
		Assert.assertEquals("Wrong column name", "name",                       cmds.xd_attribute.getATT().getColumnNames().get(2));
		Assert.assertEquals("Wrong column name", "datatype_dict_id.sqlname_c", cmds.xd_attribute.getATT().getColumnNames().get(3));
		Assert.assertEquals("Wrong column name", "ref_xd_entity_id.sqlname_c", cmds.xd_attribute.getATT().getColumnNames().get(4));
		Assert.assertEquals("Wrong column name", "primary_f",                  cmds.xd_attribute.getATT().getColumnNames().get(5));
		Assert.assertEquals("Wrong column name", "mandatory_f",                cmds.xd_attribute.getATT().getColumnNames().get(6));
		Assert.assertEquals("Wrong column name", "db_mandatory_f",             cmds.xd_attribute.getATT().getColumnNames().get(7));
		Assert.assertEquals("Wrong column name", "default_c",                  cmds.xd_attribute.getATT().getColumnNames().get(8));
		Assert.assertEquals("Wrong column name", "perm_val_f",                 cmds.xd_attribute.getATT().getColumnNames().get(9));
		Assert.assertEquals("Wrong column name", "business_key_f",             cmds.xd_attribute.getATT().getColumnNames().get(10));
		Assert.assertEquals("Wrong column name", "logical_f",                  cmds.xd_attribute.getATT().getColumnNames().get(11));

		Assert.assertEquals("Wrong number of DAT generated", 4, cmds.xd_attribute.getATT().getDATs().size());
		
		DAT attribute1dat = cmds.xd_attribute.getATT().getDATs().get(0);
		Assert.assertEquals("Wrong sqlname_c",                  "Attribute1_SomeAttrSQLName",  attribute1dat.getData().get(0).toString());
		Assert.assertEquals("Wrong xd_entity_id.sqlname_c",     "Class_SomeEntitySQLName",     attribute1dat.getData().get(1).toString());
		Assert.assertEquals("Wrong name",                       "Attribute1_SomeAttrName",     attribute1dat.getData().get(2).toString());
		Assert.assertEquals("Wrong datatype_dict_id.sqlname_c", "SomeBType",                   attribute1dat.getData().get(3).toString());
		Assert.assertEquals("Wrong ref_xd_entity_id.sqlname_c", "NULL",                        attribute1dat.getData().get(4).toString());
		Assert.assertEquals("Wrong primary_f",                  "0",                           attribute1dat.getData().get(5).toString());
		Assert.assertEquals("Wrong mandatory_f",                "0",                           attribute1dat.getData().get(6).toString());
		Assert.assertEquals("Wrong db_mandatory_f",             "0",                           attribute1dat.getData().get(7).toString());
		Assert.assertEquals("Wrong default_c",                  btEnumValue.getValue(),        attribute1dat.getData().get(8).toString());
		Assert.assertEquals("Wrong perm_val_f",                 "0",                           attribute1dat.getData().get(9).toString());
		Assert.assertEquals("Wrong business_key_f",             "0",                           attribute1dat.getData().get(10).toString());
		Assert.assertEquals("Wrong logical_f",                  "0",                           attribute1dat.getData().get(11).toString());
		
		DAT attribute2dat = cmds.xd_attribute.getATT().getDATs().get(1);
		Assert.assertEquals("Wrong sqlname_c",                  "Attribute2_SomeAttrSQLName",  attribute2dat.getData().get(0).toString());
		Assert.assertEquals("Wrong xd_entity_id.sqlname_c",     "Class_SomeEntitySQLName",     attribute2dat.getData().get(1).toString());
		Assert.assertEquals("Wrong name",                       "Attribute2_SomeAttrName",     attribute2dat.getData().get(2).toString());
		Assert.assertEquals("Wrong datatype_dict_id.sqlname_c", "enum_t",                      attribute2dat.getData().get(3).toString());
		Assert.assertEquals("Wrong ref_xd_entity_id.sqlname_c", "NULL",                        attribute2dat.getData().get(4).toString());
		Assert.assertEquals("Wrong primary_f",                  "1",                           attribute2dat.getData().get(5).toString());
		Assert.assertEquals("Wrong mandatory_f",                "1",                           attribute2dat.getData().get(6).toString());
		Assert.assertEquals("Wrong db_mandatory_f",             "1",                           attribute2dat.getData().get(7).toString());
		Assert.assertEquals("Wrong default_c",                  intEnumValue.getValue(),       attribute2dat.getData().get(8).toString());
		Assert.assertEquals("Wrong perm_val_f",                 "1",                           attribute2dat.getData().get(9).toString());
		Assert.assertEquals("Wrong business_key_f",             "1",                           attribute2dat.getData().get(10).toString());
		Assert.assertEquals("Wrong logical_f",                  "0",                           attribute2dat.getData().get(11).toString());
		
		DAT attribute3dat = cmds.xd_attribute.getATT().getDATs().get(2);
		Assert.assertEquals("Wrong sqlname_c",                  "Attribute3_SomeAttrSQLName",  attribute3dat.getData().get(0).toString());
		Assert.assertEquals("Wrong xd_entity_id.sqlname_c",     "Class_SomeEntitySQLName",     attribute3dat.getData().get(1).toString());
		Assert.assertEquals("Wrong name",                       "Attribute3_SomeAttrName",     attribute3dat.getData().get(2).toString());
		Assert.assertEquals("Wrong datatype_dict_id.sqlname_c", "flag_t",                      attribute3dat.getData().get(3).toString());
		Assert.assertEquals("Wrong ref_xd_entity_id.sqlname_c", "NULL",                        attribute3dat.getData().get(4).toString());
		Assert.assertEquals("Wrong primary_f",                  "0",                           attribute3dat.getData().get(5).toString());
		Assert.assertEquals("Wrong mandatory_f",                "0",                           attribute3dat.getData().get(6).toString());
		Assert.assertEquals("Wrong db_mandatory_f",             "0",                           attribute3dat.getData().get(7).toString());
		Assert.assertEquals("Wrong default_c",                  "1",                           attribute3dat.getData().get(8).toString());
		Assert.assertEquals("Wrong perm_val_f",                 "1",                           attribute3dat.getData().get(9).toString());
		Assert.assertEquals("Wrong business_key_f",             "0",                           attribute3dat.getData().get(10).toString());
		Assert.assertEquals("Wrong logical_f",                  "0",                           attribute3dat.getData().get(11).toString());

		DAT association4dat = cmds.xd_attribute.getATT().getDATs().get(3);
		Assert.assertEquals("Wrong sqlname_c",                  "Association4_SomeAttrSQLName", association4dat.getData().get(0).toString());
		Assert.assertEquals("Wrong xd_entity_id.sqlname_c",     "Class_SomeEntitySQLName",      association4dat.getData().get(1).toString());
		Assert.assertEquals("Wrong name",                       "Association4_SomeAttrName",    association4dat.getData().get(2).toString());
		Assert.assertEquals("Wrong datatype_dict_id.sqlname_c", "id_t",                         association4dat.getData().get(3).toString());
		Assert.assertEquals("Wrong ref_xd_entity_id.sqlname_c", "Class2_SomeEntitySQLName",     association4dat.getData().get(4).toString());
		Assert.assertEquals("Wrong primary_f",                  "0",                            association4dat.getData().get(5).toString());
		Assert.assertEquals("Wrong mandatory_f",                "0",                            association4dat.getData().get(6).toString());
		Assert.assertEquals("Wrong db_mandatory_f",             "0",                            association4dat.getData().get(7).toString());
		Assert.assertEquals("Wrong default_c",                  "NULL",                         association4dat.getData().get(8).toString());
		Assert.assertEquals("Wrong perm_val_f",                 "0",                            association4dat.getData().get(9).toString());
		Assert.assertEquals("Wrong business_key_f",             "0",                            association4dat.getData().get(10).toString());
		Assert.assertEquals("Wrong logical_f",                  "1",                            association4dat.getData().get(11).toString());
	}
	
	@Test
	public void testGenerate_xd_perm_value() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		// Given
		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.INTEGER_OBJ);
		AAAAspectDS.setTripleAAttributeSQLName(mdfEnum, "[TestDomain:UDEClass#attrib]");
		AAAAspectDS.setTripleAEntitySQLName(mdfEnum, "[TestDomain:UDEClass#attrib]");
		MdfEnumValueImpl mdfEnumValue = createMdfEnumValue(mdfEnum, "EnumValueName", "123");
		AAAAspectDS.setTripleAPermittedValueRank(mdfEnumValue, "1");
		AAAAspectDS.setTripleAPermittedValueName(mdfEnumValue, "enum_value_name");

		final MdfClassImpl mdfClass = createMdfClass("UDEClass");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass, "Class_SomeEntitySQLName");
		final MdfAttributeImpl attribute = addMdfAttribute(mdfClass, "attrib", mdfEnum);
		AAAAspectDS.setTripleAAttributeSQLName(attribute, "Attribute_SomeAttrSQLName");

		overrideUDEHelper(new UDEHelper() {
			@Override
			public MdfClass findMdfClass(MdfModelElement model,
					String path) {
				if ("TestDomain:UDEClass#attrib".equals(path)) {
					return mdfClass;
				}
				return null;
			}

			@Override
			public MdfProperty findMdfProperty(MdfModelElement model,
					String path) {
				if ("TestDomain:UDEClass#attrib".equals(path)) {
					return attribute;
				}
				return null;
			}
		});

		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_perm_value(mdfEnumValue, cmds);
		
		// Then
		Assert.assertNotNull("Should have populated xd_perm_value", cmds.xd_perm_value);
		Assert.assertEquals("Wrong entity name generated", "xd_perm_value", cmds.xd_perm_value.getEntityName());
		Assert.assertEquals("Wrong update type generated", CMDType.INSUPDDEF,  cmds.xd_perm_value.getType());

		Assert.assertEquals("Wrong number of columns", 5, cmds.xd_perm_value.getATT().getColumnNames().size());
		Assert.assertEquals("Wrong column name", "xd_attrib_id.sqlname_c",              cmds.xd_perm_value.getATT().getColumnNames().get(0));
		Assert.assertEquals("Wrong column name", "xd_attrib_id.xd_entity_id.sqlname_c", cmds.xd_perm_value.getATT().getColumnNames().get(1));
		Assert.assertEquals("Wrong column name", "perm_val_nat_e",                      cmds.xd_perm_value.getATT().getColumnNames().get(2));
		Assert.assertEquals("Wrong column name", "name",                                cmds.xd_perm_value.getATT().getColumnNames().get(3));
		Assert.assertEquals("Wrong column name", "rank_n",                              cmds.xd_perm_value.getATT().getColumnNames().get(4));
		
		Assert.assertEquals("Wrong number of DAT generated", 1, cmds.xd_perm_value.getATT().getDATs().size());
		DAT dat = cmds.xd_perm_value.getATT().getDATs().get(0);

		Assert.assertEquals("Wrong xd_attrib_id.sqlname_c",              "Attribute_SomeAttrSQLName",   dat.getData().get(0).toString());
		Assert.assertEquals("Wrong xd_attrib_id.xd_entity_id.sqlname_c", "Class_SomeEntitySQLName", dat.getData().get(1).toString());
		Assert.assertEquals("Wrong perm_val_nat_e",                      "123",               dat.getData().get(2).toString());
		Assert.assertEquals("Wrong name",                                "enum_value_name",   dat.getData().get(3).toString());
		Assert.assertEquals("Wrong rank_n",                              "1",                 dat.getData().get(4).toString());
	}

	@Test
	public void testGenerate_xd_attribute_parent() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		// Given
		final MdfClassImpl mdfClass = createMdfClass("UDEClass");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass, "Class_SomeEntitySQLName");

		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		AAAAspectDS.setTripleAAttributeSQLName(mdfEnum, "[TestDomain:UDEClass#attrib]");
		AAAAspectDS.setTripleAEntitySQLName(mdfEnum, "[TestDomain:UDEClass#attrib]");
		
		final MdfAttributeImpl attribute = addMdfAttribute(mdfClass, "attrib", mdfEnum);
		AAAAspectDS.setTripleAAttributeSQLName(attribute, "Attribute_SomeAttrSQLName");
		
		final MdfAttributeImpl attribute2 = addMdfAttribute(mdfClass, "attrib2", mdfEnum);
		AAAAspectDS.setTripleAAttributeSQLName(attribute2, "Attribute2_SomeAttrSQLName");

		// Fake AAAEntities domain with type definition
		MdfDomainImpl aaaDomain = (MdfDomainImpl)ECoreModelFactory.INSTANCE.createMdfDomain();
		aaaDomain.setName("AAAEntites");
		final MdfClassImpl accProfileCompoClass = (MdfClassImpl)ECoreModelFactory.INSTANCE.createMdfClass(aaaDomain);
		accProfileCompoClass.setName("AccProfileCompo");
		MdfClassImpl type = (MdfClassImpl)ECoreModelFactory.INSTANCE.createMdfClass(aaaDomain);
		type.setName("Type");
		final MdfAssociationImpl parentTypeAttributeAssoc = addMdfAssociation(accProfileCompoClass, "instrSubtype", type, MdfConstants.MULTIPLICITY_ONE);
		AAAAspectDS.setTripleAAttributeSQLName(parentTypeAttributeAssoc, "accProfileCompoClass_instrSubtype_attribSqlName");
		AAAAspectDS.setTripleAEntitySQLName(accProfileCompoClass, "accProfileCompoClass_entitySqlName");
		
		MdfAssociationImpl association = addMdfAssociation(mdfClass, "assoc", type, MdfConstants.MULTIPLICITY_MANY);
		AAAAspectDS.setTripleAAttributeSQLName(association, "assoc_AttributeSQLName");
		AAAAspectDS.setTripleAParentTypeAttr(association, "[AAAEntities:AccProfileCompo#instrSubtype]");
		AAAAspectDS.setTripleAParentTypeEntity(association, "[AAAEntities:AccProfileCompo#instrSubtype]");

		overrideUDEHelper(new UDEHelper() {
			@Override
			public MdfClass findMdfClass(MdfModelElement model,
					String path) {
				if ("AAAEntities:AccProfileCompo#instrSubtype".equals(path)) {
					return accProfileCompoClass;
				} else if ("TestDomain:UDEClass#attrib".equals(path)) {
					return mdfClass;
				}
				return null;
			}

			@Override
			public MdfProperty findMdfProperty(MdfModelElement model,
					String path) {
				if ("AAAEntities:AccProfileCompo#instrSubtype".equals(path)) {
					return parentTypeAttributeAssoc;
				} else if ("TestDomain:UDEClass#attrib".equals(path)) {
					return attribute;
				}
				return null;
			}
		});

		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_attribute_parent(attribute, mdfEnum, cmds);
		generator.generate_xd_attribute_parent(attribute2, mdfEnum, cmds);
		generator.generate_xd_attribute_parent(association, cmds);
		
		// Then
		Assert.assertNotNull("Should have populated xd_attribute_parent",  cmds.xd_attribute_parent);
		Assert.assertEquals("Wrong entity name generated", "xd_attribute", cmds.xd_attribute_parent.getEntityName());
		Assert.assertEquals("Wrong update type generated", CMDType.UPDATE, cmds.xd_attribute_parent.getType());

		Assert.assertEquals("Wrong number of columns", 4, cmds.xd_attribute_parent.getATT().getColumnNames().size());
		Assert.assertEquals("Wrong column name", "sqlname_c",                                  cmds.xd_attribute_parent.getATT().getColumnNames().get(0));
		Assert.assertEquals("Wrong column name", "xd_entity_id.sqlname_c",                     cmds.xd_attribute_parent.getATT().getColumnNames().get(1));
		Assert.assertEquals("Wrong column name", "parent_xd_attrib_id.sqlname_c",              cmds.xd_attribute_parent.getATT().getColumnNames().get(2));
		Assert.assertEquals("Wrong column name", "parent_xd_attrib_id.xd_entity_id.sqlname_c", cmds.xd_attribute_parent.getATT().getColumnNames().get(3));
		
		Assert.assertEquals("Wrong number of DAT generated", 2, cmds.xd_attribute_parent.getATT().getDATs().size());

		DAT dat1 = cmds.xd_attribute_parent.getATT().getDATs().get(0);
		Assert.assertEquals("Wrong sqlname_c",                                  "Attribute2_SomeAttrSQLName", dat1.getData().get(0).toString());
		Assert.assertEquals("Wrong xd_entity_id.sqlname_c",                     "Class_SomeEntitySQLName",    dat1.getData().get(1).toString());
		Assert.assertEquals("Wrong parent_xd_attrib_id.sqlname_c",              "Attribute_SomeAttrSQLName",  dat1.getData().get(2).toString());
		Assert.assertEquals("Wrong parent_xd_attrib_id.xd_entity_id.sqlname_c", "Class_SomeEntitySQLName",    dat1.getData().get(3).toString());

		DAT dat2 = cmds.xd_attribute_parent.getATT().getDATs().get(1);
		Assert.assertEquals("Wrong sqlname_c",                                  "assoc_AttributeSQLName",                          dat2.getData().get(0).toString());
		Assert.assertEquals("Wrong xd_entity_id.sqlname_c",                     "Class_SomeEntitySQLName",                         dat2.getData().get(1).toString());
		Assert.assertEquals("Wrong parent_xd_attrib_id.sqlname_c",              "accProfileCompoClass_instrSubtype_attribSqlName", dat2.getData().get(2).toString());
		Assert.assertEquals("Wrong parent_xd_attrib_id.xd_entity_id.sqlname_c", "accProfileCompoClass_entitySqlName",              dat2.getData().get(3).toString());
	}
	
	@Test
	public void testGenerate_xd_attribute_parent_match() {
		// Given
		final MdfClassImpl mdfClass = createMdfClass("UDEClass");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass, "Enum_SomeEntitySQLName");

		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		AAAAspectDS.setTripleAAttributeSQLName(mdfEnum, "[TestDomain:UDEClass#attrib]");
		AAAAspectDS.setTripleAEntitySQLName(mdfEnum, "[TestDomain:UDEClass#attrib]");
		
		final MdfAttributeImpl attribute = addMdfAttribute(mdfClass, "attrib", mdfEnum);
		AAAAspectDS.setTripleAAttributeSQLName(attribute, "Enum_SomeAttributeSQLName");

		overrideUDEHelper(new UDEHelper() {
			@Override
			public MdfClass findMdfClass(MdfModelElement model,
					String path) {
				if ("TestDomain:UDEClass#attrib".equals(path)) {
					return mdfClass;
				}
				return null;
			}

			@Override
			public MdfProperty findMdfProperty(MdfModelElement model,
					String path) {
				if ("TestDomain:UDEClass#attrib".equals(path)) {
					return attribute;
				}
				return null;
			}
		});

		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_attribute_parent(attribute, mdfEnum, cmds);

		// Then
		Assert.assertNull("Shouldn't have populated xd_attribute_parent", cmds.xd_attribute_parent);
	}

	private void overrideUDEHelper(UDEHelper udeHelper) {
		try {
			Field helper = UDEHelper.class.getDeclaredField("UDE_HELPER");
			helper.setAccessible(true);
			helper.set(null, udeHelper);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGenerate_xd_attribute_parent_no_match() {
		// Given
		MdfClassImpl mdfClass = createMdfClass("UDEClass");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass, "Enum_SomeEntitySQLName");

		MdfEnumerationImpl mdfEnum = createMdfEnum(PrimitivesDomain.INTEGER);
		AAAAspectDS.setTripleAAttributeSQLName(mdfEnum, "Enum_SomeAttributeSQLName");
		AAAAspectDS.setTripleAEntitySQLName(mdfEnum, "Enum_SomeEntitySQLName");
		
		MdfAttributeImpl attribute = addMdfAttribute(mdfClass, "attrib", mdfEnum);
		AAAAspectDS.setTripleAAttributeSQLName(attribute, "something that doesnt match");

		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_attribute_parent(attribute, mdfEnum, cmds);

		// Then
		Assert.assertNotNull("Should have populated xd_attribute_parent", cmds.xd_attribute_parent);
		Assert.assertNotNull("Should have created the ATT", cmds.xd_attribute_parent.getATT());
		Assert.assertEquals("Should have created one DAT", 1, cmds.xd_attribute_parent.getATT().getDATs().size());
	}

	@Test
	public void testGenerate_xd_entity_parent_entity() {
		// Given
		MdfClassImpl mdfClass1 = createMdfClass("UDEClass1");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass1, "Class1_SomeEntitySQLName");

		MdfClassImpl mdfClass2 = createMdfClass("UDEClass2");
		AAAAspectDS.setTripleAEntitySQLName(mdfClass2, "Class2_SomeEntitySQLName");

		MdfAssociationImpl association = addMdfAssociation(mdfClass1, "assoc", mdfClass2, MdfConstants.MULTIPLICITY_ONE);
		MdfReverseAssociationImpl reverse = addMdfReverse(mdfClass1, association);
		reverse.setMultiplicity(MdfConstants.MULTIPLICITY_MANY);
		AAAAspectDS.setTripleAAttributeSQLName(association, "Association_SomeAttrSQLName");

		// When
		CMDsByTypeList cmds = new CMDsByTypeList();
		generator.generate_xd_entity_parent_entity(reverse, cmds);
		
		// Then
		Assert.assertNotNull("Should have populated xd_entity_parent_entity", cmds.xd_entity_parent_entity);
		Assert.assertEquals("Wrong entity name generated", "xd_entity", cmds.xd_entity_parent_entity.getEntityName());
		Assert.assertEquals("Wrong update type generated", CMDType.UPDATE, cmds.xd_entity_parent_entity.getType());

		Assert.assertEquals("Wrong number of columns", 3, cmds.xd_entity_parent_entity.getATT().getColumnNames().size());
		Assert.assertEquals("Wrong column name", "sqlname_c",                                     cmds.xd_entity_parent_entity.getATT().getColumnNames().get(0));
		Assert.assertEquals("Wrong column name", "parent_xd_attrib_id.sqlname_c",              cmds.xd_entity_parent_entity.getATT().getColumnNames().get(1));
		Assert.assertEquals("Wrong column name", "parent_xd_attrib_id.xd_entity_id.sqlname_c", cmds.xd_entity_parent_entity.getATT().getColumnNames().get(2));
		
		Assert.assertEquals("Wrong number of DAT generated", 1, cmds.xd_entity_parent_entity.getATT().getDATs().size());

		DAT dat1 = cmds.xd_entity_parent_entity.getATT().getDATs().get(0);
		Assert.assertEquals("Wrong sqlname_c",                                     "Class1_SomeEntitySQLName", dat1.getData().get(0).toString());
		Assert.assertEquals("Wrong parent_xd_attrib_id.sqlname_c",                 "Association_SomeAttrSQLName", dat1.getData().get(1).toString());
		Assert.assertEquals("Wrong parent_xd_attrib_id.xd_entity_id.sqlname_c",    "Class1_SomeEntitySQLName", dat1.getData().get(2).toString());
	}

	private MdfEnumerationImpl createMdfEnum(MdfPrimitive mdfPrimitive) {
		MdfEnumerationImpl mdfEnum = (MdfEnumerationImpl)ECoreModelFactory.INSTANCE.createMdfEnumeration(testDomain);
		mdfEnum.setType(mdfPrimitive);
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
		businessType.setType(mdfPrimitive);
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

}

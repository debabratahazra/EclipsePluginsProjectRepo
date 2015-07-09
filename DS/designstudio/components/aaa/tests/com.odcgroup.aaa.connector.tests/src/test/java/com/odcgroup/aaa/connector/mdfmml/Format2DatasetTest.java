package com.odcgroup.aaa.connector.mdfmml;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictDataType;
import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.DictFunctionEntity;
import com.odcgroup.aaa.connector.domainmodel.FormatElementEntity;
import com.odcgroup.aaa.connector.domainmodel.FormatEntity;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * Test cases for Format2Dataset.
 *
 * @see Format2Dataset
 * @author Michael Vorburger
 */
public class Format2DatasetTest extends AbstractMetaDictTst {

	private static final Integer EXPECTED_SORTING_RANK_VALUE = new Integer("23");
	private static final Integer EXPECTED_RANK_VALUE = new Integer("42");
    private final MdfDomainImpl formatsDomain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();

    private static Format2Dataset format2Dataset;
	private static final String SQL_NAME = "Format_ELEMENT_1_SQL_name";

    /*
     * NOTE: In an ideal world, this test should may be no need a T'A DB, because it only tests manually created (not
     * read from DB) Format objects and verifies their conversion. Because a MetaDict is needed for that though, I'm
     * read that (a MetaDict, no formats) from DB. An alternative would have been to keep a aaa.mdf lying around as test
     * resource.
     */

    public Format2DatasetTest() throws IOException, SQLException {
        super();

        formatsDomain.setName("Format2DatasetTest");
        formatsDomain.setNamespace("http://Format2DatasetTest");
    }

    @Before
	public void setUp() throws Exception {
        super.setUp();
        if (format2Dataset == null) {
            format2Dataset = new Format2Dataset(domains.entitiesDomain, 
            									domains.enumerationsDomain, 
            									domains.businessTypesDomain);
        }
    }

    /**
     * Test transformation of a simple T'A Format to a MDF Class.
     */
    @SuppressWarnings("unchecked")
    @Test
	public void testFormatToClass() throws Exception {
        FormatEntity format = newTestFormatEntity();
        DictEntity entity = getSomeMetaDictClass();
        format.setEntity(entity);
        
        FormatElementEntity element = newFormatElement(format);
        element.setName("formatElement1Name");
        element.setSQLName(SQL_NAME);

        DictDataType datatype = new DictDataType();
        datatype.setName("amount_t").setSqlname("amount_t").setEquivType("numeric(16,2)");
        element.setDatatype(datatype);

        // DS-2181 - begin
        FormatElementEntity seqnoElement = newFormatElement(format, 57);
        seqnoElement.setName("Identifier");
        seqnoElement.setSQLName("seqno");
        DictDataType seqnoDatatype = new DictDataType();
        seqnoDatatype.setName("id_t").setSqlname("id_t").setEquivType("numeric(9,0)");
        seqnoElement.setDatatype(seqnoDatatype);
        // DS-2181 - end

        // Set Format stuff for new tests below...
        DictFunctionEntity function = new DictFunctionEntity();
        function.setName("SomeFunction");
        function.setProcName("SomeProcName");
        format.setFunction(function);
        format.setFilter("someFilter");

        MdfClass klass = format2Dataset.convertFormatToClass(formatsDomain, format, domains.businessTypesDomain);

        Assert.assertEquals("Format imported as class has unexpected name", "CodeOfFormat", klass.getName());
        Assert.assertTrue("Format imported as class has no primary key", klass.hasPrimaryKey(true));
        assertStringNotEmpty("Format imported as class has no documentation", klass.getDocumentation());
        Assert.assertEquals("SQL Name Annotation bug", "codeOfFormat", SQLAspect.getSqlName(klass));

        List<MdfProperty> properties = klass.getProperties();
        Assert.assertEquals("not exactly 2 properties (1 test format element and 1 auto-gen. 'seqno')", 2, properties.size());
        checkMdfProperty(klass, "seqno", domains.businessTypesDomain.getBusinessType("Id"), "seqno");

        // checkMdfProperty(klass, "formatElement1SqlName", PrimitivesDomain.DOUBLE_OBJ, "Format_ELEMENT_1_SQL_name");
        checkMdfProperty(klass, "Format_ELEMENT_1_SQL_name", domains.businessTypesDomain.getBusinessType("Amount"), "Format_ELEMENT_1_SQL_name");
        
        Assert.assertNotNull(AAAAspect.getTripleAFormatFunction(klass));
        Assert.assertNotNull(AAAAspect.getTripleAColumnFilter(klass));
    }

    /**
     * DS-3161 Testing the ability to retrieve rank and sorting rank of format elements as annotation in models
     * @throws Exception
     */
    @Test
	public void testDS3161_RankAndSortingRankAnnotationPropertiesAreSet() throws Exception {

    	FormatEntity format = newTestFormatEntity();
    	DictEntity entity = getSomeMetaDictClass();
    	format.setEntity(entity);

    	FormatElementEntity element = newFormatElement(format);
    	element.setName("formatElement1Name");
		element.setSQLName(SQL_NAME);

    	Integer rankValue = new Integer(EXPECTED_RANK_VALUE);
    	element.setRank(rankValue);

    	Integer sortingRankValue = new Integer(EXPECTED_SORTING_RANK_VALUE);
    	element.setSortingRank(sortingRankValue);

    	DictDataType datatype = new DictDataType();
    	datatype.setName("amount_t").setSqlname("amount_t").setEquivType("numeric(16,2)");
    	element.setDatatype(datatype);

    	MdfClass mdfClass = format2Dataset.convertFormatToClass(formatsDomain, format, domains.businessTypesDomain);

    	Integer actualRankValue = AAAAspect.getTripleAFormatElementRank(mdfClass.getProperty(SQL_NAME));
		Integer actualSortingRankValue = AAAAspect.getTripleAFormatElementSortingRank(mdfClass.getProperty(SQL_NAME));

    	Assert.assertNotNull("Annotation Rank was null", actualRankValue);
    	Assert.assertEquals("Annotation Rank value is incorrect", EXPECTED_RANK_VALUE, actualRankValue);

    	Assert.assertNotNull("Annotation Sorting Rank was null", actualSortingRankValue);
    	Assert.assertEquals("Annotation Rank value is incorrect", EXPECTED_SORTING_RANK_VALUE, actualSortingRankValue);
    }

    /**
     * Test transformation of a T'A Format which already has an Element with sqlName 'seqno' to a MDF Class.
     */
    @SuppressWarnings("unchecked")
    @Test
	public void testFormatElementNamedIdToClass() throws Exception {
        FormatEntity format = newTestFormatEntity();
        DictEntity entity = getSomeMetaDictClass();
        format.setEntity(entity);

        FormatElementEntity element = newFormatElement(format);
        element.setName("Identifier");
        element.setSQLName("seqno");

        DictDataType datatype = new DictDataType();
        datatype.setName("int_t").setSqlname("int_t").setEquivType("numeric(8,0)");
        element.setDatatype(datatype);

        MdfClass klass = format2Dataset.convertFormatToClass(formatsDomain, format, domains.businessTypesDomain);
        List<MdfProperty> properties = klass.getProperties();
        Assert.assertEquals("Not exactly 1 properties as expected (1 non-auto-gen. 'id')", 1, properties.size());

        checkMdfProperty(klass, "seqno", domains.businessTypesDomain.getBusinessType("Int"), "seqno");
    }

    /**
     * Test transformation of a T'A Format with an element which has a Dict Attribute pointing to an association to a MDF Class.
     */
    @SuppressWarnings("unchecked")
    @Test
	public void testFormatElementWithAttributeOfAssociationToClass() throws Exception {
        MdfClass metaDictEntity = getFirstClass(domains.entitiesDomain); // just any one
        MdfAssociation assoc = getFirstSingleAssociation(metaDictEntity);

        DictAttribute attribute = aaaMetaDict.getDictAttribute(AAAAspect.getTripleAEntitySQLName(assoc.getParentClass()), AAAAspect.getTripleAAttributeSQLName(assoc));

        FormatEntity format = newTestFormatEntity();
        DictEntity entity = new DictEntity(505); // whoa! Ugly hard-coding..
        entity.setSQLName(AAAAspect.getTripleAEntitySQLName(metaDictEntity));
		format.setEntity(entity);

        FormatElementEntity element = newFormatElement(format);
        element.setName("SOMETHING");
        element.setSQLName("something_id");
        element.setAttribute(attribute);

        MdfClass klass = format2Dataset.convertFormatToClass(formatsDomain, format, domains.businessTypesDomain);
        List<MdfProperty> properties = klass.getProperties();

        Assert.assertEquals("not have exactly 1 properties (1 test format element", 1, properties.size());
        MdfProperty property = checkMdfProperty(klass, "something_id", assoc.getType(), "something_id");
        // MdfProperty property = checkMdfProperty(klass, "somethingId", assoc.getType(), "something_id");
        MdfAssociation formatAssociation = (MdfAssociation)property;
        Assert.assertEquals(assoc.getType(), formatAssociation.getType());
        Assert.assertEquals(MdfConstants.CONTAINMENT_BYREFERENCE, formatAssociation.getContainment());
        Assert.assertEquals(MdfConstants.MULTIPLICITY_ONE, formatAssociation.getMultiplicity());
    }

    /**
     * Test transformation of a T'A Format with an element which has a Dict Attribute of type PermValue (Enum) to a MDF Class.
     */
    @SuppressWarnings("unchecked")
    @Test
	public void testFormatElementWithAttributeOfEnumToClass() throws Exception {
        MdfClass metaDictEntity = getFirstClass(domains.entitiesDomain); // just any one
        MdfAttribute att = getFirstEnumedAttribute(metaDictEntity);

        DictAttribute attribute = aaaMetaDict.getDictAttribute(AAAAspect.getTripleAEntitySQLName(att.getParentClass()), AAAAspect.getTripleAAttributeSQLName(att));
        DictEntity entity = attribute.getDictEntity();

        FormatEntity format = newTestFormatEntity();
        format.setEntity(entity);

        FormatElementEntity element = newFormatElement(format);
        element.setName("SOMETHING");
        element.setSQLName("something_e");
        element.setAttribute(attribute);

        MdfClass klass = format2Dataset.convertFormatToClass(formatsDomain, format, domains.businessTypesDomain);
        List<MdfProperty> properties = klass.getProperties();


        Assert.assertEquals("not have exactly 1 properties (1 test format element)", 1, properties.size());
        // MdfProperty property = checkMdfProperty(klass, "somethingE", att.getType(), "something_e");
        MdfProperty property = checkMdfProperty(klass, "something_e", att.getType(), "something_e");
        MdfAttribute formatAtt = (MdfAttribute)property;
        Assert.assertEquals(att.getType(), formatAtt.getType());
    }

    /**
     * Test transformation of a T'A Format which has two format elements that have the same SQLName in uppercase/lowercase.
     * This is not hypothetical, but has actually been seen in the wild.
     * The transformer has to ignore one of them.
     */
    @Test(expected=TA2MMLException.class)
	public void testSameNamedFormatElements() throws Exception {
        FormatEntity format = newTestFormatEntity();
        DictEntity entity = getSomeMetaDictClass();
        format.setEntity(entity);

        FormatElementEntity element = newFormatElement(format);
        element.setName("Quantity");
        element.setSQLName("QUANTITY");

        DictDataType datatype = new DictDataType();
        datatype.setName("int_t").setSqlname("int_t").setEquivType("numeric(8,0)");
        element.setDatatype(datatype);

        element = newFormatElement(format);
        element.setName("Quantity");
        element.setSQLName("quantity");
        element.setDatatype(datatype);

        format2Dataset.convertFormatToClass(formatsDomain, format, domains.businessTypesDomain);
    }
    
    @Test
	public void testDS4805MultilingualAnnotation() throws TA2MMLException {
        FormatEntity format = newTestFormatEntity();
        DictEntity entity = getSomeMetaDictClass();
        format.setEntity(entity);

        DictDataType datatype = new DictDataType();
        datatype.setName("int_t").setSqlname("int_t").setEquivType("numeric(8,0)");

        FormatElementEntity formatElement_noMultilingual = newFormatElement(format);
        formatElement_noMultilingual.setName("noMultilingual");
        formatElement_noMultilingual.setSQLName("noMultilingual_f");
        formatElement_noMultilingual.setDatatype(datatype);
        
        FormatElementEntity formatElement_multilingual0 = newFormatElement(format);
        formatElement_multilingual0.setName("multilingual0");
        formatElement_multilingual0.setSQLName("multilingual0_f");
        formatElement_multilingual0.setTslMultilingual(0);
        formatElement_multilingual0.setDatatype(datatype);
        
        FormatElementEntity formatElement_multilingual1 = newFormatElement(format);
        formatElement_multilingual1.setName("multilingual1");
        formatElement_multilingual1.setSQLName("multilingual1_f");
        formatElement_multilingual1.setTslMultilingual(1);
        formatElement_multilingual1.setDatatype(datatype);
        
        MdfClass formatClass = format2Dataset.convertFormatToClass(formatsDomain, format, domains.businessTypesDomain);
        
        MdfProperty noMultilingualProperty = formatClass.getProperty("noMultilingual_f");
        Assert.assertNotNull("The property sould be created", noMultilingualProperty);
        Assert.assertFalse("Shouldn't be multilingual", AAAAspect.getTripleAAttrMultiLanguage(noMultilingualProperty));
        
        MdfProperty multilingual0Property = formatClass.getProperty("multilingual0_f");
        Assert.assertNotNull("The property sould be created", multilingual0Property);
        Assert.assertFalse("Shouldn't be multilingual", AAAAspect.getTripleAAttrMultiLanguage(multilingual0Property));

        MdfProperty multilingual1Property = formatClass.getProperty("multilingual1_f");
        Assert.assertNotNull("The property sould be created", multilingual1Property);
        Assert.assertTrue("Should be multilingual", AAAAspect.getTripleAAttrMultiLanguage(multilingual1Property));
    }

    private FormatEntity newTestFormatEntity() {
        FormatEntity format = new FormatEntity();
        format.setCode("codeOfFormat");
        format.setName("nameOfFormat");
        format.setID(1234);
        return format;
    }

    private DictEntity getSomeMetaDictClass() {
    	DictEntity dictEntity = new DictEntity(813);
        dictEntity.setSQLName("acc_period");
        return dictEntity;
    }

    private FormatElementEntity newFormatElement(FormatEntity format, long id) {
        FormatElementEntity element = new FormatElementEntity();
        format.getFormatElements().add(element);
        element.setFormat(format);
        element.setID(id);
        return element;
    }

    private FormatElementEntity newFormatElement(FormatEntity format) {
    	return newFormatElement(format, 56);
    }

    /**
     * Return the first Class in the domain which has at least one association.
     */
    @SuppressWarnings("unchecked")
    private MdfClass getFirstClass(MdfDomain domain) {
        List<MdfEntity> entities = domain.getEntities();
        for (MdfEntity mdfEntity : entities) {
            if (mdfEntity instanceof MdfClass) {
                MdfClass klass = (MdfClass) mdfEntity;
                if (getFirstSingleAssociation(klass) != null
                 && getFirstEnumedAttribute(klass) != null) {
                    return klass;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private MdfAssociation getFirstSingleAssociation(MdfClass klass) {
        List<MdfProperty> props = klass.getProperties();
        for (MdfProperty prop : props) {
            if (prop instanceof MdfAssociation) {
                MdfAssociation assoc = (MdfAssociation) prop;
                if (assoc.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
                    return assoc;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private MdfAttribute getFirstEnumedAttribute(MdfClass klass) {
        List<MdfProperty> props = klass.getProperties();
        for (MdfProperty prop : props) {
            if (prop instanceof MdfAttribute) {
                MdfAttribute att = (MdfAttribute) prop;
                if (att.getType() instanceof MdfEnumeration) {
                    return att;
                }
            }
        }
        return null;
    }

    private MdfProperty checkMdfProperty(MdfClass klass, String propertyName, MdfEntity expectedType, String expectedSQLName) {
    	MdfProperty prop = klass.getProperty(propertyName);
        Assert.assertNotNull("Format has no property named '" + propertyName + "', only: " + klass.getProperties(), prop);
        Assert.assertEquals(MdfConstants.MULTIPLICITY_ONE, prop.getMultiplicity());
        assertStringNotEmpty("Format property has no documentation", prop.getDocumentation());
        Assert.assertEquals("Format property type unexpected", expectedType, prop.getType());
        Assert.assertEquals("Format property has wrong SQL Name Annotation", expectedSQLName, SQLAspect.getSqlName(prop));
        return prop;
    }

    private static void assertStringNotEmpty(String message, String string) {
        Assert.assertNotNull(message, string);
        Assert.assertTrue(message, !string.isEmpty());
    }

    @Test
	public void testTAVersion() {
    	Assert.assertNotNull(aaaMetaDict.getTaVersionInfo());
    }
}

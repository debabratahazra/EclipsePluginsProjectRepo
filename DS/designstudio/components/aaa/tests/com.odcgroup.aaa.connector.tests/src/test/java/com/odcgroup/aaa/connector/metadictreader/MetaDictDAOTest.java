package com.odcgroup.aaa.connector.metadictreader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictDataType;
import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.DictPermValue;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictDAO;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictWithLabelsDAO;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictWithLabelsRepository;
import com.odcgroup.aaa.connector.internal.nls.Language;
import com.odcgroup.aaa.connector.internal.nls.Translateable;
import com.odcgroup.aaa.connector.nls.LabelsDAOTest;
import com.odcgroup.aaa.connector.tests.JUnit4TstJPA;
import com.odcgroup.otf.jpa.test.openjpa.RememberingAllStatementJDBCListenerHelper;
import com.odcgroup.otf.jpa.utils.BootstrapJPA;
import com.odcgroup.otf.jpa.utils.CreateDB;

/**
 * Test for MetaDictDAO.
 *
 * @author Michael Vorburger (MVO)
 */
public class MetaDictDAOTest extends JUnit4TstJPA {

    /**
     * This is the (maximuum) number of SQL statements that the tests expects the DAO to issue to read the entire Meta Dictionary.
     * MAX_NUMBER_OF_SQL_STATEMENTS_FOR_METADICT_READING
     */
    private static final int NSQL = 8;

    public MetaDictDAOTest() throws IOException, SQLException {
        super();
        connectionProperties = BootstrapJPA.getPropertiesFromClasspathResource("ta-db.properties");
        CreateDB.addNormalLoggingProperties(connectionProperties);
        connectionProperties.setProperty("openjpa.DetachState", "loaded(AccessUnloaded=false)");
        persistenceUnitName = "aaa";
    }


    @Test
	public void testValidateMetaDictThrowsErrorWhenInvalidWhendatabaseIsUnavailable() throws Exception {
    	MetaDictDAO metaDictDAO = new MetaDictDAO(Mockito.mock(EntityManager.class));

    	try {

    		metaDictDAO.validateConnection();
    		Assert.fail("Invalid database details should cause the database to not be validated");
    	}
    	catch (Exception e) {
    		Assert.assertTrue(true);
    	}
	}

    @Test
	public void testValidateMetaDictDoesntThrowErrorWhenValidWhendatabaseIsUnavailable() throws Exception {
    	MetaDictDAO metaDictDAO = new MetaDictDAO(em);

    	try {
    		metaDictDAO.validateConnection();
    		Assert.assertTrue(true);
    	}
    	catch (Exception e) {
    		Assert.fail("Valid database details should cause the database to be valid");
    	}
	}

    //@org.junit.Test
    @Test
	public void testGetMetaDictWithLabels() {
        MetaDictWithLabelsDAO dao = new MetaDictWithLabelsDAO(em);
        MetaDictWithLabelsRepository repo = dao.getMetaDictWithLabels();

        // Assert.asserts validating the exact SQL used..
        // loadDictEntitiesAndAttributes()
        Assert.assertEquals("SELECT t0.dict_id, t0.business_key_f, t0.calculated_e, t0.custom_f, t2.dict_id, t2.equiv_type_c, t2.name, t2.sqlname_c, t0.db_mandatory_f, t0.default_c, t0.default_display_len_n, t0.entity_dict_id, t0.disp_rank_n, t0.edit_e, t0.entity_attribute, t0.enum_attribute, t0.enum_value, t0.fk_presentation_e, t0.logical_f, t0.mandatory_f, t0.max_db_len_n, t0.multi_language_f, t0.name, t0.object_attribute, t0.parent_attribute_dict_id, t0.perm_auth_f, t0.perm_val_f, t0.primary_f, t0.quick_search_mask, t0.ref_entity_attribute_dict_id, t0.ref_entity_dict_id, t0.search_mask, t0.short_index_n, t0.sqlname_c, t0.subtype_mask, t0.tasc_view_e, t0.widget_e, t0.xd_status_e FROM dict_attribute_vw t0 INNER JOIN dict_entity_vw t1 ON t0.entity_dict_id = t1.dict_id INNER JOIN dict_datatype_vw t2 ON t0.datatype_dict_id = t2.dict_id WHERE (t1.nature_e <> ? AND (t1.xd_status_e = ? OR t1.xd_status_e = ?) AND t1.xd_status_e IS NOT NULL AND (t0.xd_status_e = ? OR t0.xd_status_e = ?) AND t0.xd_status_e IS NOT NULL)", RememberingAllStatementJDBCListenerHelper.getSQL(em, 7));
        Assert.assertEquals("SELECT t0.dict_id, t0.logical_f, t0.name, t0.nature_e, t0.sqlname_c, t0.xd_status_e FROM dict_entity_vw t0 WHERE (t0.nature_e <> ? AND (t0.xd_status_e = ? OR t0.xd_status_e = ?) AND t0.xd_status_e IS NOT NULL)", RememberingAllStatementJDBCListenerHelper.getSQL(em, 6));
        // loadDictPermValues()
        Assert.assertEquals("SELECT t0.dict_id, t0.attribute_dict_id, t0.name, t0.perm_val_nat_e, t0.rank_n, t0.xd_status_e FROM dict_perm_value_vw t0 INNER JOIN dict_attribute_vw t1 ON t0.attribute_dict_id = t1.dict_id INNER JOIN dict_entity_vw t2 ON t1.entity_dict_id = t2.dict_id WHERE (t2.nature_e <> ? AND (t2.xd_status_e = ? OR t2.xd_status_e = ?) AND t2.xd_status_e IS NOT NULL AND (t0.xd_status_e = ? OR t0.xd_status_e = ?) AND t0.xd_status_e IS NOT NULL) ORDER BY t0.attribute_dict_id ASC, t0.rank_n ASC", RememberingAllStatementJDBCListenerHelper.getSQL(em, 5));
        // getAllLabels()
        Assert.assertEquals("SELECT t0.dict_id, t0.code, t0.date_format_c, t0.decim_separator_c, t0.denom, t0.name, t0.sqlname_c, t0.thous_separator_c FROM dict_language_vw t0", RememberingAllStatementJDBCListenerHelper.getSQL(em, 4));
        Assert.assertEquals("SELECT t0.entity_dict_id, t0.language_dict_id, t0.object_dict_id, t0.name FROM dict_label_vw t0 WHERE t0.entity_dict_id = ?", RememberingAllStatementJDBCListenerHelper.getSQL(em, 3));
        Assert.assertEquals("SELECT t0.entity_dict_id, t0.language_dict_id, t0.object_dict_id, t0.name FROM dict_label_vw t0 WHERE t0.entity_dict_id = ?", RememberingAllStatementJDBCListenerHelper.getSQL(em, 2));
        Assert.assertEquals("SELECT t0.entity_dict_id, t0.language_dict_id, t0.object_dict_id, t0.name FROM dict_label_vw t0 WHERE t0.entity_dict_id = ?", RememberingAllStatementJDBCListenerHelper.getSQL(em, 1));
        // getTAVersion()
        Assert.assertEquals("select value_n from appl_param_vw where param_name='AAA_VERSION'", RememberingAllStatementJDBCListenerHelper.getSQL(em, 0));
        Assert.assertEquals("Although MetaDictDAO.getMetaDict() was OK, there are too many total SQL statements", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));

        Set<Language> langs = repo.getLanguages();
        Assert.assertNotNull(langs);
        Assert.assertTrue(langs.size() > 3);

        boolean atLeastOneEntityHasLabel = false;
        Collection<DictEntity> entities = repo.getEntities();
        {
            Assert.assertNotNull(entities);
            Assert.assertTrue(entities.size() > 200);

            boolean atLeastOneAttributeHasAReferencedDictEntity = false;
            boolean atLeastOneAttributeHasAParentDictEntity = false;
            for (DictEntity dictEntity : entities) {
                Assert.assertNotNull(dictEntity.getName());
                if (checkIfHasTranslation(langs, dictEntity)) {
                    atLeastOneEntityHasLabel = true;
                }

                boolean atLeastOneAttributeOfThisEntityHasLabel = false;
                List<DictAttribute> attributes = dictEntity.getSortedAttributes();
                Assert.assertNotNull(attributes);
                Assert.assertTrue(attributes.size() > 0);
                for (DictAttribute dictAttribute : attributes) {
                    Assert.assertNotNull(dictAttribute.getSQLName());
                    Assert.assertNotNull(dictAttribute.getDictEntity());
                    Assert.assertEquals(dictEntity, dictAttribute.getDictEntity());
                    Assert.assertNotNull(dictAttribute.getDatatype());
                    Assert.assertNotNull(dictAttribute.getDatatype().getEquivType());
                    if (checkIfHasTranslation(langs, dictAttribute)) {
                        atLeastOneAttributeOfThisEntityHasLabel = true;
                    }

                    // Is there a good test criteria for attributes which need to have a ref_entity_dict_id?
                    if (dictAttribute.getReferencedDictEntity() != null) {
                        atLeastOneAttributeHasAReferencedDictEntity = true;
                    }

                    // Is there a good test criteria for attributes which need to have a ref_entity_dict_id?
                    if (dictAttribute.getParentAttribute() != null) {
                        atLeastOneAttributeHasAParentDictEntity = true;
                    }
                }
                Assert.assertTrue("No Attribute of this entity has a label: " + dictEntity.getSQLName(), atLeastOneAttributeOfThisEntityHasLabel);
            }
            Assert.assertTrue(atLeastOneAttributeHasAParentDictEntity);
            Assert.assertTrue(atLeastOneAttributeHasAReferencedDictEntity);
            Assert.assertTrue("No Entity has a label", atLeastOneEntityHasLabel);
        }

        // Now testing DictPermValue stuff...
        {
            boolean atLeastOnePermValueHasLabel = false;
            Collection<List<DictPermValue>> p = repo.getPermValues().values();
            Assert.assertNotNull(p);
            Assert.assertTrue(p.size() > 0);
            for (List<DictPermValue> list : p) {
                Assert.assertTrue(list.size() > 0);
                for (DictPermValue dictPermValue : list) {
                    DictAttribute dictAttribute = dictPermValue.getAttribute();
                    Assert.assertNotNull(dictAttribute);

                    DictDataType dataType = dictAttribute.getDatatype();
                    Assert.assertNotNull(dataType);
                    Assert.assertTrue(dataType + " is not enum_t or flag_t or enummask_t but is " + dataType.getName(), dataType.getName().equals("enum_t") || dataType.getName().equals("flag_t") || dataType.getName().equals("enummask_t"));

                    DictEntity dictEntity = dictAttribute.getDictEntity();
                    Assert.assertNotNull(dictEntity);

                    List<DictPermValue> listOfPermValues = dictAttribute.getPermValues();
                    Assert.assertNotNull(listOfPermValues);
                    Assert.assertTrue(listOfPermValues.size() > 0);

                    // Now test if the DictPermValue is correctly wired to the
                    // DictAttribute from the DictEntities list.. This may seem
                    // obvious, but I (MVO) got this wrong in the first DTO, and
                    // it lead to an initially very confusing error in the MetaDict2MML.
                    // If the following test fails again, think before just deactivating... ;)
                    //
                    Assert.assertTrue("Something is 'wired' wrong - the DictEntity of the PermValue is NOT in the list of DictEntities (by object identity)",
                            entities.contains(dictEntity));

                    if (checkIfHasTranslation(langs, dictPermValue)) {
                        atLeastOnePermValueHasLabel = true;
                    }
                }
            }
            Assert.assertTrue("No PermValue has a label", atLeastOnePermValueHasLabel);
        }

        Assert.assertEquals("Although MetaDictDAO.getMetaDict() was OK, there seems to have been (unexpected!) lazy loading occuring, because there are too many total SQL statements at the end", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
    }

    private boolean checkIfHasTranslation(Set<Language> langs, Translateable t) {
        boolean hasAtLeastOneTranslation = false;
        for (Language translatedLanguage : langs) {
            if (t.hasTranslatedName(translatedLanguage)) {
                LabelsDAOTest.assertStringNotEmpty(t.getTranslatedName(translatedLanguage));
                hasAtLeastOneTranslation = true;
            }
        }
        return hasAtLeastOneTranslation;
    }
}

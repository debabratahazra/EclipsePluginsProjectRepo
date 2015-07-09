package com.odcgroup.aaa.connector.nls;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.aaa.connector.domainmodel.subs.label.DictAttributeLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictEntityLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictPermValueLabelEntity;
import com.odcgroup.aaa.connector.internal.nls.LabelsDAO;
import com.odcgroup.aaa.connector.internal.nls.LabelsRepository;
import com.odcgroup.aaa.connector.internal.nls.Language;
import com.odcgroup.aaa.connector.tests.JUnit4TstJPA;
import com.odcgroup.otf.jpa.test.openjpa.RememberingAllStatementJDBCListenerHelper;
import com.odcgroup.otf.jpa.utils.BootstrapJPA;
import com.odcgroup.otf.jpa.utils.CreateDB;

/**
 * Test for LabelsDAO.
 * 
 * @author Michael Vorburger (MVO)
 */
public class LabelsDAOTest extends JUnit4TstJPA {
	
    private static final String SQL_DICTLABEL = "SELECT t0.entity_dict_id, t0.language_dict_id, t0.object_dict_id, t0.name FROM dict_label_vw t0 WHERE t0.entity_dict_id = ?";
    private static final int NSQL = 4;
    
	public LabelsDAOTest() throws IOException, SQLException {
		super();
		connectionProperties = BootstrapJPA.getPropertiesFromClasspathResource("ta-db.properties");
		CreateDB.addNormalLoggingProperties(connectionProperties);
		persistenceUnitName = "aaa";
	}

	@Test
	public void testLabels() {
		Assert.assertEquals(0, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
		
		LabelsDAO dao = new LabelsDAO(em);
		LabelsRepository labels = dao.getAllLabels();
        Assert.assertNotNull(labels);

		// Assert.asserts validating the exact SQL used..
		Assert.assertEquals("MetaDictDAO getMetaDict() initially issued more (or less? good! ;) SQL statements than expected", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
        Assert.assertEquals("SELECT t0.dict_id, t0.code, t0.date_format_c, t0.decim_separator_c, t0.denom, t0.name, t0.sqlname_c, t0.thous_separator_c FROM dict_language_vw t0", RememberingAllStatementJDBCListenerHelper.getSQL(em, 3));
        Assert.assertEquals(SQL_DICTLABEL, RememberingAllStatementJDBCListenerHelper.getSQL(em, 2));
        Assert.assertEquals(SQL_DICTLABEL, RememberingAllStatementJDBCListenerHelper.getSQL(em, 1));
        Assert.assertEquals(SQL_DICTLABEL, RememberingAllStatementJDBCListenerHelper.getSQL(em, 0));

		{
    		Set<Language> langs = labels.getLanguages();
            Assert.assertNotNull(langs);
            Assert.assertTrue(langs.size() > 10);
            for (Language language : langs) {
                assertStringNotEmpty(language.getCode());
                assertStringNotEmpty(language.getName());
                System.out.println(language.getCode() + " : " + language.getName());
            }
		}

	    {
            Set<DictPermValueLabelEntity> enumLabels = labels.getPermValLabels();
            Assert.assertNotNull(enumLabels);
            Assert.assertTrue(enumLabels.size() > 100);
            for (DictPermValueLabelEntity permValueLabel : enumLabels) {
                Assert.assertNotNull(permValueLabel.getLanguage());
                assertStringNotEmpty(permValueLabel.getName());
            }
        }
        
        {
            Set<DictEntityLabelEntity> entityLabels = labels.getEntityLabels();
            Assert.assertNotNull(entityLabels);
            Assert.assertTrue(entityLabels.size() > 100);
            for (DictEntityLabelEntity permValueLabel : entityLabels) {
                Assert.assertNotNull(permValueLabel.getLanguage());
                assertStringNotEmpty(permValueLabel.getName());
            }
        }

        {
            Set<DictAttributeLabelEntity> attributeLabels = labels.getAttributeLabels();
            Assert.assertNotNull(attributeLabels);
            Assert.assertTrue(attributeLabels.size() > 100);
            for (DictAttributeLabelEntity permValueLabel : attributeLabels) {
                Assert.assertNotNull(permValueLabel.getLanguage());
                assertStringNotEmpty(permValueLabel.getName());
            }
        }

        Assert.assertEquals("Although LabelsDAO appeared OK, there seems to have been (unexpected!) lazy loading occuring, because there are too many total SQL statements at the end", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
	}
	
	
	public static void assertStringNotEmpty(String message, String string) {
	    Assert.assertNotNull(message, string);
	    Assert.assertTrue(message, !string.isEmpty());
	}
    public static void assertStringNotEmpty(String string)  {
        Assert.assertNotNull(string);
        Assert.assertTrue(!string.isEmpty());
    }
}

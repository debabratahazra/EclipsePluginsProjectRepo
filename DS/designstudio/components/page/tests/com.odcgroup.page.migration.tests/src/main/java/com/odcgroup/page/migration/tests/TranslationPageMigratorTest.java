package com.odcgroup.page.migration.tests;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.translation.migration.PageTranslationMigrator;
import com.odcgroup.translation.core.migration.ITranslationModelMigrator;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class TranslationPageMigratorTest extends AbstractDSUnitTest {
	
	private static Logger logger = LoggerFactory.getLogger(TranslationPageMigratorTest.class);

	private ITranslationModelMigrator migrator;
	
	@Before
	public void setUp() {
		createModelsProject();
		migrator = new PageTranslationMigrator();
	}
	
	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
		migrator = null;
	}		

	@Test
	public void testAcceptTranslationKey() {

		migrator.startMigration(logger, getOfsProject());
		
		boolean accept = false;
		
		accept = migrator.isKeyAccepted(null);
		Assert.assertFalse("Null translation key must not be accepted", accept);
		
		accept = migrator.isKeyAccepted("");
		Assert.assertFalse("Empty translation key must not be accepted", accept);

		accept = migrator.isKeyAccepted("  ");
		Assert.assertFalse("Blank translation key must not be accepted", accept);
		
		accept = migrator.isKeyAccepted("1234.message");
		Assert.assertFalse("Visual Rule translation key must not be accepted", accept);

		accept = migrator.isKeyAccepted("a.b.c");
		Assert.assertFalse("Non Numerical translation key must not be accepted", accept);

		accept = migrator.isKeyAccepted("module_123.text");
		Assert.assertTrue("translation key starting with 'module_' must be accepted", accept);

		accept = migrator.isKeyAccepted("1234");
		Assert.assertTrue("Numerical translation key must be accepted", accept);

		accept = migrator.isKeyAccepted("1234.text");
		Assert.assertTrue("Numerical translation key must be accepted", accept);
	}
	
	@Test
	public void testProcessTranslation() {
		
		migrator.startMigration(logger, getOfsProject());
		
		TranslationVO tvo = null;
		boolean processed = false;
		
		// Try to process translation key without the suffix
		tvo = new TranslationVO("123", "en", "test-123");
		processed = migrator.process(tvo);
		if (!processed) {
			Assert.assertTrue("The error status must be defined", StringUtils.isNotBlank(tvo.errorStatus));
		}
		Assert.assertFalse("Translation Key without the suffix must be processed", processed);

		// Try to process valid translation key but cannot find the widget
		tvo = new TranslationVO("123.text", "en", "test");
		processed = migrator.process(tvo);
		if (!processed) {
			Assert.assertTrue("The error status must be defined", StringUtils.isNotBlank(tvo.errorStatus));
		}
		Assert.assertFalse("Translation Key with no widget bound to it, must not be processed", processed);
		
	}

}

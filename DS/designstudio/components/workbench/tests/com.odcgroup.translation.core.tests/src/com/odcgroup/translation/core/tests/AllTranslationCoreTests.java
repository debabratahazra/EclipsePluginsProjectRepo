package com.odcgroup.translation.core.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.translation.core.internal.migration.MessageRepositoryMigrationTest;
import com.odcgroup.translation.core.internal.migration.SaveMigrationErrorToExternalFileTest;
import com.odcgroup.translation.core.tests.richtext.TranslationRichTextUtilsTest;

@RunWith(Suite.class)
@SuiteClasses( {
	TranslationManagerTest.class,
	TranslationChangeSupportTest.class,
	TranslationProviderTest.class,
	TranslationPreferencesTest.class,
	TranslationTest.class,
	TranslationRichTextUtilsTest.class,
	MessageRepositoryMigrationTest.class,
	SaveMigrationErrorToExternalFileTest.class,
} )
public class AllTranslationCoreTests {

}

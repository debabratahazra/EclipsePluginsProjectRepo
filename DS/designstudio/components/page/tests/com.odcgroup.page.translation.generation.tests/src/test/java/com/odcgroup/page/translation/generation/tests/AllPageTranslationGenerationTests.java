package com.odcgroup.page.translation.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author atr
 */
@RunWith(Suite.class)
@SuiteClasses( {
	EventTranslationKeyProviderTest.class,
	EventTranslationKeyTest.class,
	WidgetTranslationKeyTest.class,
	TranslationXLSGenerationTest.class,
	DomainWidgetTranslationKeyTest.class,
	PageTranslationXLSGeneratorTest.class,
} )
public class AllPageTranslationGenerationTests {
}

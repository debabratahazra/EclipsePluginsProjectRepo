package com.odcgroup.mdf.ecore.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.mdf.ecore.MdfAnnotationTests;
import com.odcgroup.mdf.ecore.MdfModelElementImplTest;
import com.odcgroup.mdf.ecore.util.DomainRepositoryTest;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtilTest;
import com.odcgroup.mdf.model.translation.MdfInheritableTranslationProviderTest;
import com.odcgroup.mdf.model.translation.MdfInheritableTranslationTest;
import com.odcgroup.mdf.model.translation.MdfTranslationProviderTest;
import com.odcgroup.mdf.model.translation.MdfTranslationTest;

/**
 * @author yan
 */
@RunWith(Suite.class)
@SuiteClasses( {
	MdfTranslationTest.class,
	MdfTranslationProviderTest.class,
	MdfInheritableTranslationTest.class,
	MdfInheritableTranslationProviderTest.class,
	DomainRepositoryTest.class,
	MdfModelElementImplTest.class ,
	MdfAnnotationTests.class, 
	MdfNameURIUtilTest.class
})
public class AllMDFEcoreTests {

}

package com.odcgroup.domain.dsl.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.domain.derived.DomainJvmModelInferrerTest;
import com.odcgroup.domain.resource.DomainDSLResourceTest;
import com.odcgroup.domain.resource.DomainFormatterTest;
import com.odcgroup.domain.resource.DomainSerializeTest;
import com.odcgroup.domain.resource.DomainValueConverterServiceLoadingTest;
import com.odcgroup.domain.resource.DomainValueConverterServiceSavingTest;
import com.odcgroup.domain.resource.MdfNameQualifiedNameUtilTest;
import com.odcgroup.domain.resource.SyntaxTest;
import com.odcgroup.domain.scoping.tests.LegacyMdfNameFactoryUtilTest;
import com.odcgroup.domain.validation.tests.AllDomainDslValidationTests;


@RunWith(Suite.class)
@SuiteClasses( {DomainDSLResourceTest.class,
	DomainValueConverterServiceLoadingTest.class,
	DomainValueConverterServiceSavingTest.class,
	SyntaxTest.class,
	DomainSerializeTest.class,
	DomainFormatterTest.class, 
	MdfNameQualifiedNameUtilTest.class,
	LegacyMdfNameFactoryUtilTest.class,
	DomainJvmModelInferrerTest.class,
	AllDomainDslValidationTests.class} )
public class AllDomainDSLTests {

}

package com.odcgroup.page.model.dsl.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.model.dsl.internal.InjectorUniquenessTest;
import com.odcgroup.page.model.dsl.internal.PageDSLResourceTest;
import com.odcgroup.page.model.dsl.internal.PageValueConverterServiceTest;

@RunWith(Suite.class)
@SuiteClasses( {
	InjectorUniquenessTest.class,
	PageValueConverterServiceTest.class,
	PageDSLResourceTest.class,
} )
public class AllPageModelDSLTests {

}

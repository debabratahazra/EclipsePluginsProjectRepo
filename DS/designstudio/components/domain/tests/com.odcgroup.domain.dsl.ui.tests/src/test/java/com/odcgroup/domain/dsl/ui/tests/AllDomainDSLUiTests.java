package com.odcgroup.domain.dsl.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.domain.resource.DomainDSLOutlineTest;
import com.odcgroup.domain.resource.DomainEscapeSplCharTest;


@RunWith(Suite.class)
@SuiteClasses( {DomainDSLOutlineTest.class,
	DomainEscapeSplCharTest.class} )
public class AllDomainDSLUiTests {

}

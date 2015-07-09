package com.odcgroup.t24.localref.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.t24.localref.generation.tests.LocalRefTests;
import com.odcgroup.t24.localref.generation.tests.SubClassDomainTests;

@RunWith(Suite.class)
@SuiteClasses( {
	   SubClassDomainTests.class,
	   LocalRefTests.class
	})
public class AllLocalRefTests {

}

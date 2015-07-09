package com.odcgroup.t24.version.model.translation.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.CaseConvention;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class VersionModelTest extends AbstractDSUnitTest{
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}
	
	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS5672VersionFieldEnumValueCaseConversion(){
		CaseConvention convention = VersionUtils.getCaseConvention("propercase");
		assertNotNull(convention);
		assertSame(CaseConvention.PROPERCASE, convention);
	}
}

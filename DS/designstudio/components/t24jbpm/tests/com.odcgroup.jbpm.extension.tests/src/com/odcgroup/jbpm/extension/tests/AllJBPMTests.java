package com.odcgroup.jbpm.extension.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.jbpm.extension.flow.ruleflow.properties.IFModelUtilTest;
import com.odcgroup.jbpm.extension.flow.ruleflow.properties.JbpmDialogHelperTest;
import com.odcgroup.jbpm.extension.flow.ruleflow.properties.VersionFieldMappingTest;
import com.odcgroup.jbpm.extension.flow.ruleflow.properties.VersionModelUtilTest;

/**
 * Test Suite for all the JPBM tests.
 * 
 * @author vramya
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({
	VersionFieldMappingTest.class,
	VersionModelUtilTest.class,
	IFModelUtilTest.class,
	JbpmDialogHelperTest.class})
public class AllJBPMTests {

}

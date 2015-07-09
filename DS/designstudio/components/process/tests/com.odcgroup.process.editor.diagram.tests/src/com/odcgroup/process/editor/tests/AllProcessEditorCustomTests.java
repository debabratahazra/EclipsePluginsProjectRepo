package com.odcgroup.process.editor.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author pkk
 */
@RunWith(Suite.class)
@SuiteClasses( {
//	commented due to DS-6274 JUNIT failures in build - Fix Required on dependency
AllProcessEditorTechnicalValidationTests.class
} )
public class AllProcessEditorCustomTests {

}

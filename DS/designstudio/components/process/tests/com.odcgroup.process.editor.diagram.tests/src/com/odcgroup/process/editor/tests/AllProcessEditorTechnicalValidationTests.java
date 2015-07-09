package com.odcgroup.process.editor.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.process.editor.validation.tests.EventTechValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.GatewayTechValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.PoolTechValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.ProcessTechValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.TaskTechValidationEditorTests;

/**
 * @author pkk
 */

@RunWith(Suite.class)
@SuiteClasses( {
	EventTechValidationEditorTests.class,
	GatewayTechValidationEditorTests.class,
	PoolTechValidationEditorTests.class,
	ProcessTechValidationEditorTests.class,
	TaskTechValidationEditorTests.class
} )
public class AllProcessEditorTechnicalValidationTests {
	
}

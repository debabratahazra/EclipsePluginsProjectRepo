package com.odcgroup.process.editor.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.process.editor.validation.tests.EventSketchValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.GatewaySketchValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.PoolSketchValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.ProcessSketchValidationEditorTests;
import com.odcgroup.process.editor.validation.tests.TaskSketchValidationEditorTests;
@RunWith(Suite.class)
@SuiteClasses( {
	EventSketchValidationEditorTests.class,
	GatewaySketchValidationEditorTests.class,
	PoolSketchValidationEditorTests.class,
	ProcessSketchValidationEditorTests.class,
	TaskSketchValidationEditorTests.class
} )
public class AllProcessEditorSketchingValidationTests {
	
}

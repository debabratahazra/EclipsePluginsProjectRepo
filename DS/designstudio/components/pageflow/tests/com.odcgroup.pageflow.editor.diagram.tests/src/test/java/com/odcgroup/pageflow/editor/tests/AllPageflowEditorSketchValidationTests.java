package com.odcgroup.pageflow.editor.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.pageflow.editor.validation.tests.PageflowDecisionStateSketchValidationTest;
import com.odcgroup.pageflow.editor.validation.tests.PageflowEndStateSketchValidationTest;
import com.odcgroup.pageflow.editor.validation.tests.PageflowSubPageflowSketchTest;
import com.odcgroup.pageflow.editor.validation.tests.PageflowViewStateSketchValidationTest;

@RunWith(Suite.class)
@SuiteClasses( { 
	PageflowEndStateSketchValidationTest.class,
	PageflowDecisionStateSketchValidationTest.class,
	PageflowSubPageflowSketchTest.class,
	PageflowViewStateSketchValidationTest.class,
} )
public class AllPageflowEditorSketchValidationTests {

}

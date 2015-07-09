package com.odcgroup.pageflow.editor.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.pageflow.editor.validation.tests.PageflowDecisionStateValidationTest;
import com.odcgroup.pageflow.editor.validation.tests.PageflowEndStateValidationTest;
import com.odcgroup.pageflow.editor.validation.tests.PageflowSubPageflowTest;
import com.odcgroup.pageflow.editor.validation.tests.PageflowTransitionValidationtest;
import com.odcgroup.pageflow.editor.validation.tests.PageflowValidationEditorTests;
import com.odcgroup.pageflow.editor.validation.tests.PageflowViewStateValidationTest;

@RunWith(Suite.class)
@SuiteClasses( {
	PageflowEndStateValidationTest.class,
	PageflowValidationEditorTests.class,
	PageflowDecisionStateValidationTest.class,
	PageflowViewStateValidationTest.class,
	PageflowTransitionValidationtest.class,
	PageflowSubPageflowTest.class, 
} )
public class AllPageflowEditorTechnicalValidationTests {

}

package com.odcgroup.service.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.service.ui.editor.tests.TextEditorTest;
/**
 * Tests for the Service UIModel plugin.
 * 
 * @author SCN
 */

@RunWith(Suite.class)
@SuiteClasses( {
	TextEditorTest.class
} )
public class AllServiceUITests {

}

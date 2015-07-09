package com.odcgroup.mdf.editor.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.mdf.editor.ui.actions.DomainEditorActionTest;
import com.odcgroup.mdf.editor.ui.actions.SynchronizeDatasetTest;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfDatasetPageTest;
import com.odcgroup.mdf.editor.ui.providers.decorators.ValidationDecoratorTest;

/**
 * @author yan
 */
@RunWith(Suite.class)
@SuiteClasses( {
	SynchronizeDatasetTest.class,
	DomainEditorActionTest.class,
	MdfDatasetPageTest.class,
	ValidationDecoratorTest.class,
} )
public class AllMdfEditorTests {

}

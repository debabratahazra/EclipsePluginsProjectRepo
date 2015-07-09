package com.odcgroup.workbench.integration.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.workbench.integration.tests.fragment.FragmentDesignerTest;
import com.odcgroup.workbench.integration.tests.generation.CartridgesTest;
import com.odcgroup.workbench.integration.tests.generation.PartialGenerationTest;
import com.odcgroup.workbench.integration.tests.help.ContextSensitiveHelpTest;
import com.odcgroup.workbench.integration.tests.jira.AllJiraTests;
import com.odcgroup.workbench.integration.tests.module.ModuleDesignerTest;
import com.odcgroup.workbench.integration.tests.ocs.extract.OcsSupportTest;


@RunWith(Suite.class)
@SuiteClasses( {
//	SVNMenuTest.class,
//	SWTBotDomainDSLUITest.class,
	SmokeTest.class, 
//	FragmentDesignerTest.class, 
// requires template feature	PageEventDialogTest.class, 
//	CartridgesTest.class, 
//	GenerationWithUnsavedResources.class,
//	AllJiraTests.class,
//	ModuleDesignerTest.class,
	//DomainDesignerTest.class,
	//TranslationEditorTest.class,
	//TranslationPreferencesTest.class,
	
//	due to missing action “Import Translations from Excel” 	
//	TranslationImportWizardPageTest.class,
	
//	FileSystemChangeRefreshEditorTests.class,
//	ContextSensitiveHelpTest.class,
// requires template feature	CustoPackagerTemplateTest.class,
	
	
//	PartialGenerationTest.class,
	//RichTextTest.class,
	
//	MimeTimeOnNewModelsTest.class,
	//MenuItemPageflowTest.class,

	// DS-4965: This test does not work (anymore?) on Bamboo for whatever reason.
	// I have tried many things, it works well locally, but it somehow cannot find
	// the nodes and menus when being run on Bamboo.
	// As I do see any other option, I am deactivating this test.
//  DataTypeRuleModelSyncTests.class,

	// DS-3832: This test should stay the last one to be executed as it
	// extracts a new TAP repository and thus overwrites the initially
	// prepared standard projects.

//	OcsSupportTest.class
	
})

public class AllIntegrationTests {
}

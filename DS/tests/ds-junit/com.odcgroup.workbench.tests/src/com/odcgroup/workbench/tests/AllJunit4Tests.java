package com.odcgroup.workbench.tests;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.aaa.core.tests.AllAaaCoreTests;
import com.odcgroup.aaa.generation.tests.AllAaaGenerationTests;
import com.odcgroup.aaa.ui.tests.AllAAAUITests;
import com.odcgroup.basic.tests.AllBasicUITest;
import com.odcgroup.cdm.generation.tests.AllCdmGenerationTests;
import com.odcgroup.component.dsl.tests.AllComponentDSLTests;
import com.odcgroup.domain.dsl.tests.AllDomainDSLTests;
import com.odcgroup.domain.dsl.ui.tests.AllDomainDSLUiTests;
import com.odcgroup.domain.edmx.tests.AllDomainEDMXTests;
//import com.odcgroup.edge.t24.generation.tests.AllEdgeGenerationTest;
//import com.odcgroup.edge.t24ui.tests.AllT24UIModelTests;
import com.odcgroup.iris.generation.tests.AllIRISGenerationTests;
import com.odcgroup.iris.rim.generation.tests.AllRimGeneratorTest;
import com.odcgroup.mdf.ecore.tests.AllMDFEcoreTests;
import com.odcgroup.mdf.editor.tests.AllMdfEditorTests;
import com.odcgroup.mdf.entity.generator.AllMdfEntityGeneratorTests;
import com.odcgroup.mdf.generation.tests.AllMDFGenerationTests;
import com.odcgroup.mdf.validation.tests.AllMdfValidationTests;
import com.odcgroup.menu.model.tests.AllMenuModelTests;
import com.odcgroup.ocs.packager.tests.AllOCSPackagerTests;
import com.odcgroup.ocs.packager.ui.tests.AllOCSPackagerUITests;
import com.odcgroup.ocs.server.embedded.tests.AllOcsServerEmbeddedTests;
import com.odcgroup.ocs.server.embedded.ui.tests.AllOcsServerEmbeddedUITests;
import com.odcgroup.ocs.server.external.tests.AllOcsServerExternalTests;
import com.odcgroup.ocs.server.external.ui.tests.AllOcsServerExternalUITests;
import com.odcgroup.ocs.server.tests.AllOcsServerTests;
import com.odcgroup.ocs.server.ui.tests.AllServerUITests;
import com.odcgroup.ocs.support.tests.AllOcsSupportTests;
import com.odcgroup.ocs.support.ui.tests.AllOcsSupportUITests;
import com.odcgroup.page.generation.tests.AllPageGenerationTests;
import com.odcgroup.page.metamodel.tests.AllPageMetaModelTests;
import com.odcgroup.page.migration.tests.AllTranslationPageMigrationTests;
import com.odcgroup.page.model.dsl.tests.AllPageModelDSLTests;
import com.odcgroup.page.model.tests.AllPageModelTests;
import com.odcgroup.page.pageflow.integration.tests.AllPageflowIntegrationTests;
import com.odcgroup.page.transformmodel.tests.AllPageTransformModelTests;
import com.odcgroup.page.translation.generation.tests.AllPageTranslationGenerationTests;
import com.odcgroup.page.ui.tests.AllPageUITests;
import com.odcgroup.page.uimodel.tests.AllPageUIModelTests;
import com.odcgroup.page.validation.tests.AllPageValidationTests;
import com.odcgroup.page.validation.ui.tests.AllPageValidationUITests;
import com.odcgroup.process.generation.tests.AllProcessGenerationTests;
import com.odcgroup.process.migration.tests.AllTranslationProcessMigrationTests;
import com.odcgroup.process.model.tests.AllProcessModelTests;
import com.odcgroup.t24.application.importer.tests.AllApplicationImportTests;
import com.odcgroup.t24.enquiry.importer.tests.AllT24EnquiryImporterTests;
import com.odcgroup.t24.enquiry.tests.AllEnquiryTests;
import com.odcgroup.t24.mdf.generation.tests.AllT24MdfGenerationTests;
import com.odcgroup.t24.version.importer.tests.AllT24VersionImporterTests;
import com.odcgroup.t24.version.tests.AllVersionTests;
import com.odcgroup.dataframework.generation.tests.AllDataFrameworkGenerationTest;
import com.odcgroup.translation.core.tests.AllTranslationCoreTests;
import com.odcgroup.translation.generation.tests.AllTranslationGenerationTests;
import com.odcgroup.translation.generation.ui.tests.AllTapTranslationTests;
import com.odcgroup.translation.migration.tests.AllTranslationMigrationTests;
import com.odcgroup.translation.ui.tests.AllTranslationUITests;
import com.odcgroup.visualrules.depmgr.tests.AllVRDepMgrTests;
import com.odcgroup.visualrules.generation.tests.AllVRGenerationTests;
import com.odcgroup.visualrules.integration.tests.AllVRIntegrationTests;
import com.odcgroup.visualrules.integration.ui.tests.AllVRIntegrationUITests;
import com.odcgroup.workbench.dsl.tests.AllWorkbenchDslTests;
import com.odcgroup.workbench.generation.ui.tests.AllGenerationUITests;
import com.odcgroup.workbench.migration.tests.AllMigrationTests;
import com.odcgroup.workbench.ui.tests.AllWorkbenchUITest;
import com.odcgroup.workbench.tap.validation.tests.AllWorkbenchValidationTests;
import com.odcgroup.t24.localref.tests.AllLocalRefTests;
import com.odcgroup.jbpm.extension.tests.AllJBPMTests;
import com.odcgroup.t24.menu.tests.AllT24MenuModelTests;
import com.odcgroup.integrationfwk.cache.tests.AllIntegrationframeworkTests;
import com.odcgroup.iris.rim.generation.tests.AllRimGeneratorTest;
import com.odcgroup.workbench.core.tests.AllCoreTests;
import com.odcgroup.t24.server.external.ui.tests.AllT24ExternalUITests;
@RunWith(Suite.class)
@SuiteClasses( {
	AllWorkbenchDslTests.class,
	AllTranslationUITests.class,
	AllPageUITests.class,
	AllOcsSupportUITests.class,
	AllOcsServerExternalUITests.class,
	AllOcsServerEmbeddedUITests.class,
	AllOcsServerTests.class,
	AllPageTransformModelTests.class,
	AllTranslationGenerationTests.class,	
	AllComponentDSLTests.class,
	AllCoreTests.class,
	AllGenerationUITests.class,
	AllMDFEcoreTests.class,
	AllMDFGenerationTests.class,
	AllDomainDSLTests.class,
	AllDomainDSLUiTests.class,
	AllDomainEDMXTests.class,
//	AllPageflowGenerationTests.class,
//	AllPageflowImporterTests.class,
//	AllPageflowValidationTests.class,
//	AllPageflowEditorCustomTests.class, 
	AllTranslationPageMigrationTests.class,
	AllPageModelTests.class,
	AllAaaCoreTests.class,
	AllAaaGenerationTests.class,
	AllAAAUITests.class,
	AllCdmGenerationTests.class,
	AllMdfEditorTests.class,
	AllMdfEntityGeneratorTests.class,
	AllMdfValidationTests.class,
	AllMenuModelTests.class,
	AllOCSPackagerTests.class,
	AllOCSPackagerUITests.class,
	AllOcsServerEmbeddedTests.class,	
	AllOcsServerExternalTests.class,
	AllServerUITests.class,
	AllOcsSupportTests.class,
	AllPageModelDSLTests.class,
  AllPageMetaModelTests.class,
  AllPageUIModelTests.class,
  AllPageTranslationGenerationTests.class,
	AllPageGenerationTests.class,
	AllPageValidationTests.class,
	AllPageValidationUITests.class,
	AllProcessGenerationTests.class,
//	AllProcessImporterTests.class,	
	AllProcessModelTests.class,
	AllVRDepMgrTests.class,
	AllVRIntegrationTests.class,
	AllVRIntegrationUITests.class,
	AllVRGenerationTests.class,
	AllTranslationMigrationTests.class,
	AllTranslationProcessMigrationTests.class,
	AllTranslationCoreTests.class,	
	AllTapTranslationTests.class,
	AllMigrationTests.class,
	AllPageflowIntegrationTests.class,	
	AllWorkbenchValidationTests.class,
	AllVersionTests.class,
	AllEnquiryTests.class,
	AllApplicationImportTests.class,
	AllT24VersionImporterTests.class,
	AllT24EnquiryImporterTests.class,
	//commented till velocity template resolves
//    AllT24ServiceGenerationTests.class,
    AllT24MdfGenerationTests.class,
	AllIRISGenerationTests.class,
	AllWorkbenchUITest.class,
//	AllEdgeGenerationTest.class,
//	AllT24UIModelTests.class,
	AllT24MenuModelTests.class,
//	AllProcessEditorCustomTests.class
        AllLocalRefTests.class,
        AllDataFrameworkGenerationTest.class,
        AllRimGeneratorTest.class,
    AllJBPMTests.class,
	AllT24ExternalUITests.class,
    AllIntegrationframeworkTests.class,
    AllBasicUITest.class
	})
	
public class AllJunit4Tests {
	
	// DS-8421 Do *NOT* do anything like a @BeforeClass calling headless Activator with *StandaloneSetup!!

}

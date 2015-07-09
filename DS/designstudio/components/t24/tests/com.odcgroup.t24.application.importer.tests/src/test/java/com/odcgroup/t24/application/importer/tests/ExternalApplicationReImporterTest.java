package com.odcgroup.t24.application.importer.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.applicationimport.ApplicationsImport;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;

public class ExternalApplicationReImporterTest extends T24ImporterTest {
	
	private IProject project;
	private IProject serverProject;
	private IPath importPath = new Path("/domain/applications/AA");
	private String[] sampleDomainFiles = new String[] { "/domain/applications/AA/AA_Account.domain",
			"/domain/applications/AA/AA_Accounting.domain" };	
	private	ApplicationImporter importer;
	private ApplicationsImport appImporters = new ApplicationsImport();
	
	private final String AA_ARR_ACCOUNT_Doc = "The ACCOUNT Property Class is used by all products which are account based.  This property class primarily controls the description of the account.  Each T24 product defined and processed in AA can have a single ACCOUNT Property defined.\n"
			+ "\n"
			+ "Although account names are typically account specific, generic titles can be defaulted from the product and can be replaced or given additional detail at the arrangement level.";
	
	private final String AA_ARR_ACCOUNTING_Doc = "The ACCOUNTING component is used by all products which require accounting. Products serviced through AA use activity-based accounting. This component allows for the definition of the allocation rules to use for each action.\n"
			+ "\n"
			+ "Each Property can have multiple Actions which require accounting. For each of these actions the user must specify the allocation rule (defined in AC.ALLOCATION.RULE) which will be used each time the action is run.\n"
			+ "\n"
			+ "The allocation rule may be specified at two levels - either at Property Class level or more specifically at property level. If a property level definition is not defined Property class level definition would be used if available. But either of the definition is required otherwise a validation error is raised at proofing level.";

	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("t-server");
		project = createModelsProject().getProject();

		mkdirs(project.getFolder(importPath));
		copyResourceInModelsProject(sampleDomainFiles);
		waitForAutoBuild();
		Injector injector = DomainActivator.getInstance().getInjector(DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
		importer = new ApplicationImporter(injector);
	}

	@After
	public void tearDown() throws Exception {
		if (project != null) {
			project.delete(true, null);
		}
		if (serverProject != null) {
			serverProject.delete(true, null);
		}
	}
	
	protected void importModels(IExternalServer server) throws Exception {
		importer.setServer(server);
		importer.setContainer(project.getFolder(importPath));
		List<ApplicationDetail> details = new ArrayList<ApplicationDetail>();
		details.add(new ApplicationDetail("AA.ARR.ACCOUNT", "AA.Account", "AA"));
		details.add(new ApplicationDetail("AA.ARR.ACCOUNTING", "AA.Accounting", "AA"));
		importer.getSelectedModels().addAll(details);
		importer.importModels(new NullProgressMonitor());
		waitForAutoBuild();
	}

	@Test
	public void testReImportApplicationWithHelpText() throws Exception {
		createServerConfig(serverProject, false);
		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		importModels(server);
		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
				fail("Application import failed." + report.getErrors());
		}
		
		Map<String, String> docs = getDocumentation("AA_Account", "AA_ARR_ACCOUNT");
		assertEquals("All documentations are not updated properly.", 62, docs.entrySet().size());
		String mdfClassDoc = docs.get("AA_Account:AA_ARR_ACCOUNT");
		assertEquals("Documentation is not matched.", mdfClassDoc, AA_ARR_ACCOUNT_Doc);
		
		docs = getDocumentation("AA_Accounting", "AA_ARR_ACCOUNTING");
		assertEquals("All documentations are not updated properly.", 134, docs.entrySet().size());
		mdfClassDoc = docs.get("AA_Accounting:AA_ARR_ACCOUNTING");
		assertEquals("Documentation is not matched.", mdfClassDoc, AA_ARR_ACCOUNTING_Doc);
	}

	private Map<String, String> getDocumentation(String mdfDomainName, String mdfClassName) throws Exception {
		MdfDomainImpl domain = getMdfDomain(mdfDomainName);
		if(domain == null) {
			throw new FileNotFoundException(mdfDomainName + " : Domain file not found in models project");
		}
		MdfClassImpl mdfClass = (MdfClassImpl) domain.getClass(mdfClassName);
		if(mdfClass == null) {
			throw new ClassNotFoundException(mdfClassName + " : MdfClass not found in domain");
		}
		Map<String, String> docs = appImporters.getMdfClassDocumentations(mdfClass);
		assertFalse("Documentation are not updated properly after re-import of the same application.", docs.isEmpty());
		return docs;
	}
	
	private MdfDomainImpl getMdfDomain(String mdfDomainName) {
		MdfDomainImpl domain = (MdfDomainImpl) DomainRepositoryUtil.getMdfDomain(
				QualifiedName.create(mdfDomainName), project);
		return domain;
	}
}

package com.odcgroup.t24.application.importer.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Injector;
import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class ExternalApplicationImporterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;

	private IPath importPath = new Path("/domain/application");

	//DS:7395 - ignoring domain due to different case MO_ModelBank, MO_Modelbank.
	private String[] invalidDomainNames = new String[] { "MO_Modelbank", "IF.EXIT.POINTS", "ACCT.ENT.STMT.STD",
			"PD.CAPTURE", "AM.BENCHMARK", "MM.MONEY.MARKET", "SL.ROLLOVER", "DX.CONTRACT.MASTER" };	
	private	ApplicationImporter importer;

	@Before
	public void setUp() throws Exception {
		deleteModelsProject("project");
		serverProject = createServerProject("t-server");
		project = createModelsProject().getProject();
		mkdirs(project.getFolder(importPath));
		
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
	
	protected void importModels(IExternalServer server, int nbModels) throws Exception {
		// complete integration test
		importer.setServer(server);
		importer.setContainer(project.getFolder(importPath));
		
		List<ApplicationDetail> details = new ArrayList<ApplicationDetail>();
		try {
			details = importer.getFilteredModels();
		} catch (T24ServerException e) {
			fail("Cannot establish connection with the server " + e.getMessage());
		}
		if (nbModels < 0) nbModels = details.size();
		final int MAX = Math.min(nbModels, details.size());
		for (int kx=0; kx < MAX; kx++ ) {
			ApplicationDetail application = details.get(kx);
			List<String> invalidList = Arrays.asList(invalidDomainNames);
			if(valid(invalidList,application)){
				importer.getSelectedModels().add(application);
			}
		}
		importer.importModels(new NullProgressMonitor());		
	}

	@Ignore @Test
	public void testImportApplicationsFromT24Server() throws Exception {
		
		createServerConfig(serverProject, false);

		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		importModels(server, 500);
		
		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if ( !(StringUtils.contains(message, "not a valid value for")
					| StringUtils.contains(message, "BUG in T24 XML"))) {
				fail(message);
			}
		}
	}

	private boolean valid(List<String> invalidList, ApplicationDetail application) {
		if( invalidList.contains(application.getComponent())){
			return false;
		}
		else if(invalidList.contains(application.getName())){
			return false;
		}
		return true;
	}
	
	@Test 
	public void testImportApplicationsFromT24WebServer() throws Exception {
		
		createServerConfig(serverProject, true);

		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		importModels(server, 500);
		
		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if(!StringUtils.contains(message, "not a valid value for")){
				fail(message);
			}	
		}
	}

	 @Test
	public void testApplicationImportDescription() throws Exception {
		createServerConfig(serverProject, false);
		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		//set the server
		importer.setServer(server);
		importer.setContainer(project.getFolder(importPath));
		//load the application from server
		List<ApplicationDetail> details = new ArrayList<ApplicationDetail>();
		try {
			details = importer.getFilteredModels();
		} catch (T24ServerException e) {
			fail("Cannot establish connection with the server " + e.getMessage());
		}
		assertTrue(!details.isEmpty());
		importer.getSelectedModels().add(details.get(0));
		importer.getSelectedModels().add(details.get(1));
		
		assertNotNull(details.get(0).getApplicaitonDescription());
		assertTrue(details.get(0).getApplicaitonDescription().equals("DX Item Status"));
		
		//import 2 domains 
		importer.importModels(new NullProgressMonitor());
		waitForAutoBuild();
		
		QualifiedName qName = QualifiedName.create("CON_Consolidation");
	    MdfDomain domain = DomainRepositoryUtil.getMdfDomain(qName, project);
	    assertNotNull(domain);
	    String applicationName = details.get(1).getName();
	    applicationName = applicationName.replace('.', '_');
	    MdfClass klass = domain.getClass(applicationName);
	    assertNotNull(klass);
	    String description = T24Aspect.getDescription(klass, project);
	    assertNotNull(description);
	    assertTrue(description.equals("Consolidate Profit Loss"));
	}
	
	
}

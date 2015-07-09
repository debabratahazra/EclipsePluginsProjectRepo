package com.odcgroup.t24.localref.application.importer.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.domain.DomainUiInjectorProvider;
import com.odcgroup.t24.application.localref.importer.LocalRefImporter;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.LocalRefDetail;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.workbench.core.IOfsProject;

@RunWith(XtextRunner.class)
@InjectWith(DomainUiInjectorProvider.class)
public class ExternalLocalrefImporterTest extends  T24ImporterTest {
private IProject project;
	
	private IOfsProject iofsProject;
	
	private IProject serverProject;

	private IPath importPath = new Path("/domain/");
	@Inject
	private	LocalRefImporter localRefImporter ;

	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("t-server");
		iofsProject = createModelsProject();
		project = iofsProject.getProject();
		mkdirs(project.getFolder(importPath));
		
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
		localRefImporter.setServer(server);
		localRefImporter.setContainer(project.getFolder(importPath));
		
		List<LocalRefDetail> details = localRefImporter.getFilteredModels();
		if (nbModels < 0) nbModels = details.size();
		final int MAX = Math.min(nbModels, details.size());
		for (int kx=0; kx < MAX; kx++ ) {
			localRefImporter.getSelectedModels().add(details.get(kx));
		}
		localRefImporter.importModels(new NullProgressMonitor());
	}

	
	@Test
	public void testImportApplicationsFromT24Server() throws Exception {
		
		createServerConfig(serverProject, false);

		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		importModels(server, -1);
		
		IImportModelReport report = localRefImporter.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if(!StringUtils.contains(message, "not a valid value for")){
				fail(message);
			}
		}
	}
	
	@Test 
	public void testImportApplicationsFromT24WebServer() throws Exception {
		
		createServerConfig(serverProject, true);

		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		importModels(server, 500);
		
		IImportModelReport report = localRefImporter.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if(!StringUtils.contains(message, "not a valid value for")){
				fail(message);
			}	
		}
	}
}

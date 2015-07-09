package com.odcgroup.t24.application.importer.tests;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Tests Application Import of 2589 XML local files in the resources/applications folder. 
 * 
 * This one (contrary to the ApplicationIntrospectionTest) doesn't actually
 * assert anything functional on the MDF Domains created from the Import - it just
 * makes sure the import report contains no errors.
 */
public class LocalApplicationImporterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;

	private IPath importPath = new Path("/domain");

	private	ApplicationImporter importer;

	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("Server");
		project = createNamedModelsProject("project").getProject();
		OfsCore.getOfsProjectManager().getOfsProject(project);
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
		
		List<ApplicationDetail> details = importer.getFilteredModels();
		if (nbModels < 0) nbModels = details.size();
		final int MAX = Math.min(nbModels, details.size());
		for (int kx=0; kx < MAX; kx++ ) {
			importer.getSelectedModels().add(details.get(kx));
		}
		importer.importModels(new NullProgressMonitor());
	}

	@Test
	public void testImportApplicationsFromLocalServer() throws Exception {
		URL url =  Platform.getBundle("com.odcgroup.t24.application.importer.tests").getEntry("resources/applications");
		File xmlFolder = new File(FileLocator.toFileURL(url).toURI());
		LocalFileServer server = new LocalFileServer("S-ID","S-NAME", serverProject, xmlFolder);

		importModels(server, 500 /* -1 for ALL*/);
		
		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			Assert.fail(message);
		}
	}

}

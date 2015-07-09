package com.odcgroup.t24.version.importer.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.version.VersionDSLUiInjectorProvider;
import com.odcgroup.t24.version.importer.internal.VersionImporter;

@RunWith(XtextRunner.class)
@InjectWith(VersionDSLUiInjectorProvider.class)
public class VersionImporterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;

	private IPath importPath = new Path("/version/version");

	@Inject
	private	VersionImporter importer;


	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("Server");
		project = createProject("project");
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
		// complete integration test
		importer.setServer(server);
		importer.setContainer(project.getFolder(importPath));
		
		List<VersionDetail> details = importer.getFilteredModels();
		if (nbModels < 0) nbModels = details.size();
		final int MAX = Math.min(nbModels, details.size());
		for (int kx=0; kx < MAX; kx++ ) {
			importer.getSelectedModels().add(details.get(kx));
		}
		importer.importModels(new NullProgressMonitor());
	}

	
	@Test
	public void testImportVersionFromLocalServer() throws Exception {
		
		URL url =  Platform.getBundle("com.odcgroup.t24.version.importer.tests").getEntry("resources/versions");
		File xmlFolder = new File(FileLocator.toFileURL(url).toURI());
		LocalFileServer server = new LocalFileServer("S-ID","S-NAME", serverProject, xmlFolder);

		importModels(server, -1);
		
		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			fail(message);
		}
	}
}

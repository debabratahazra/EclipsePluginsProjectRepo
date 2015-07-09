package com.odcgroup.t24.version.importer.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.version.VersionDSLUiInjectorProvider;
import com.odcgroup.t24.version.importer.internal.VersionImporter;

@RunWith(XtextRunner.class)
@InjectWith(VersionDSLUiInjectorProvider.class)
public class VersionFilterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;
	
	private static final int TOTAL_VERSION = 500;
	private static final int FILTER_MODULE_VERSION = 68;
	private static final int FILTER_COMPONENT_VERSION = 8;

	@Inject
	private	VersionImporter importer;


	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("Server");
		project = createProject("project");
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
	
	@Test
	public void testImportVersionFromLocalServer() throws Exception {

		URL url = Platform.getBundle("com.odcgroup.t24.version.importer.tests").getEntry("resources/versions");
		File xmlFolder = new File(FileLocator.toFileURL(url).toURI());
		LocalFileServer server = new LocalFileServer("S-ID", "S-NAME", serverProject, xmlFolder);

		importer.setServer(server);

		List<VersionDetail> details = importer.getFilteredModels();
		assertEquals("All Version files are not loaded.", TOTAL_VERSION, details.size());

		importer.getFilter().setModule("?B");
		details = importer.getFilteredModels();
		assertEquals("Filter of Module is not OK.", FILTER_MODULE_VERSION, details.size());

		importer.getFilter().setComponent("A?_*");
		details = importer.getFilteredModels();
		assertEquals("Filter of Component is not OK.", FILTER_COMPONENT_VERSION, details.size());
	}

}

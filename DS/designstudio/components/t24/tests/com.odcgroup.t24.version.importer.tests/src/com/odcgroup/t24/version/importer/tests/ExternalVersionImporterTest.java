package com.odcgroup.t24.version.importer.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
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

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.version.VersionDSLUiInjectorProvider;
import com.odcgroup.t24.version.importer.internal.VersionImporter;

@RunWith(XtextRunner.class)
@InjectWith(VersionDSLUiInjectorProvider.class)
public class ExternalVersionImporterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;

	private IPath importPath = new Path("/version/version");

	// DS:6968 - the version xml contains invalid value hence removed from
	// importing.
	private String[] invalidVersionNames = new String[] {};

	@Inject
	private VersionImporter importer;

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

		List<VersionDetail> details = new ArrayList<VersionDetail>();
		try {
			details = importer.getFilteredModels();
		} catch (T24ServerException e) {
			fail("Cannot establish connection with the server " + e.getMessage());
		}
		if (nbModels < 0)
			nbModels = details.size();
		final int MAX = Math.min(nbModels, details.size());
		for (int kx = 0; kx < MAX; kx++) {
			VersionDetail version = details.get(kx);
			List<String> invalidList = Arrays.asList(invalidVersionNames);
			if (valid(invalidList, version)) {
				importer.getSelectedModels().add(version);
			}
		}
		importer.importModels(new NullProgressMonitor());
	}

	/**
	 * @param invalidList
	 * @param version
	 * @return
	 */
	private boolean valid(List<String> invalidList, VersionDetail version) {
		if (invalidList.contains(version.getApplication() + ":" + version.getName())) {
			return false;
		}
		return true;
	}

	@Test
	public void testImportVersionsFromT24Server() throws Exception {

		createServerConfig(serverProject, false);

		ExternalT24Server server = new ExternalT24Server("S-ID", "S-NAME", serverProject);
		importModels(server, 500);

		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if (!StringUtils.contains(message, "is not legal")) {
				fail(message);
			}
		}
	}

	@Test
	public void testImportVersionsFromT24WebServer() throws Exception {

		createServerConfig(serverProject, true);

		ExternalT24Server server = new ExternalT24Server("S-ID", "S-NAME", serverProject);
		importModels(server, 500);

		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if (!StringUtils.contains(message, "ID may not be empty")) {
				fail(message);
			}
		}
	}

	@Test
	public void testImporterVersionWithSingleFieldName() throws Exception {
		createServerConfig(serverProject, true);
		ExternalT24Server server = new ExternalT24Server("S-ID", "S-NAME", serverProject);
		importer.setServer(server);
		importer.setContainer(project.getFolder(importPath));
		VersionDetail model = new VersionDetail("INPUT", "EB_Utility", "EB", "DFE.GROUP.PARAMETER");
		importer.getSelectedModels().add(model);
		importer.importModels(new NullProgressMonitor());
		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			fail(report.getErrors());
		}
		verifyImportedDSLFile();
	}

	private void verifyImportedDSLFile() throws Exception {
		final String VERSION_FILE = "DFE_GROUP_PARAMETER,INPUT.version";
		IFile genFile = project.getFile(importPath.toPortableString() + "/" + VERSION_FILE);
		if (!genFile.exists()) {
			fail(VERSION_FILE + " : This version file not imported successfully.");
		}
		File genVersionFile = genFile.getRawLocation().makeAbsolute().toFile();
		String genText = FileUtils.readFileToString(genVersionFile);
		genText = genText.replaceAll("\r", "");
		String expText = Resources.toString(Resources.getResource(getClass(), VERSION_FILE), Charsets.UTF_8);
		genText = genText.replaceAll("\t", "");
		expText = expText.replaceAll("\r", "");
		expText = expText.replaceAll("\t", "");
		assertEquals(genText, expText);
	}
}

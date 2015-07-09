package com.odcgroup.t24.iris.importer.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaCore;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.iris.importer.IRISModelImporter;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.InteractionDetail;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * JUnit test case for
 * DS-6372 (Dev *.rim Interaction import for T24)
 * @author hdebabrata
 *
 */
 public class RIMImporterTest extends AbstractDSUnitTest {
	
	private static IProject iProject;
	IOfsProject iofsProject = null;
	private IProject serverProject;
	private IPath importPath = new Path("rim/rim/");
	private static final String MODEL_NAME = "MyModel";
	private static final String DOMAIN_MODEL = "domain/ds6372/AA_ClassicProducts.domain";
	private static final String ENQUIRY_MODEL = "enquiry/ds6372/AA.PROPERTY-LIST.enquiry";
	private static final String VERSION_MODEL = "version/ds6372/AA_ACTIVITY_CLASS,AA_AUDIT.version";
	private final NullProgressMonitor nullProgressMonitor = new NullProgressMonitor();
	
	private IRISModelImporter importer = new IRISModelImporter();
	
	public RIMImporterTest() {
		super();
	}
	
	@Before
	public void setup() throws Exception {
		
		iofsProject = createModelsProject();
		Assert.assertNotNull("Null iofs project", iofsProject);
		Assert.assertTrue("rim folder should exists", getRimFolder().exists());
		iProject = iofsProject.getProject();
		serverProject = createProject("rim-server");
		Assert.assertTrue("Server project not created successfully.", serverProject.exists());
		
		copyResourceInModelsProject(DOMAIN_MODEL);
		copyResourceInModelsProject(ENQUIRY_MODEL);
		copyResourceInModelsProject(VERSION_MODEL);
	}
	
	@After
	public void tearDown() throws Exception {

		deleteModelsProjects();
		if (serverProject != null) {
			serverProject.delete(true, null);
		}
	}

	@Ignore @Test
	public void testTAFCImportRIMFromT24Server() throws Exception {

		// Test RIM importer functionality
		createTAFCServerConfig(serverProject);

		// do the test
		testImportRIMFromT24Server();
		
	}

	@Ignore @Test
	public void testTAFCImportedApplnEnqVerIsNotEmpty() throws Exception {

		// Test RIM importer functionality
		createTAFCServerConfig(serverProject);

		// do the test
		testImportedApplnEnqVerIsNotEmpty();		
	}

	@Ignore @Test
	public void testWebImportRIMFromT24Server() throws Exception {

		// Test RIM importer functionality
		createWebServerConfig(serverProject);
		
		// do the test
		testImportRIMFromT24Server();

	}


	@Ignore @Test
	public void testWebImportedApplnEnqVerIsNotEmpty() throws Exception {

		// Test RIM importer functionality
		createWebServerConfig(serverProject);
		
		// do the test
		testImportedApplnEnqVerIsNotEmpty();		
	}

	/** test used with TAFC/TAFJ */
	private void testImportedApplnEnqVerIsNotEmpty() throws Exception {

		ExternalT24Server server = new ExternalT24Server("RIM-ID", "RIM-NAME",
				serverProject);
		importModels(server, -1);

		//check selected Application is not empty 
		for (ApplicationDetail aDetail : importer.getApplicationSelector().getSelectedModels()) {
			Assert.assertTrue(StringUtils.isNotEmpty(aDetail.getProduct()));
			Assert.assertTrue(StringUtils.isNotEmpty(aDetail.getName()));
			Assert.assertTrue(StringUtils.isNotEmpty(aDetail.getComponent()));
		}

		//check selected Enquiry is not empty
		for (EnquiryDetail eDetail : importer.getEnquirySelector().getSelectedModels()) {
			Assert.assertTrue(StringUtils.isNotEmpty(eDetail.getName()));
			Assert.assertTrue(StringUtils.isNotEmpty(eDetail.getComponent()));
			Assert.assertTrue(StringUtils.isNotEmpty(eDetail.getModule()));
		}

		//check selected Version is not empty
		for (VersionDetail vDetail : importer.getVersionSelector().getSelectedModels()) {
			Assert.assertTrue(StringUtils.isNotEmpty(vDetail.getApplication()));
			Assert.assertTrue(StringUtils.isNotEmpty(vDetail.getName()));
			Assert.assertTrue(StringUtils.isNotEmpty(vDetail.getComponent()));
			Assert.assertTrue(StringUtils.isNotEmpty(vDetail.getModule()));
		}

		Assert.assertTrue("Test complete", true);
		
	}

	/** test used with TAFC/TAFJ */
	private void testImportRIMFromT24Server() throws Exception {

		ExternalT24Server server = new ExternalT24Server("RIM-ID", "RIM-NAME",
				serverProject);
		importModels(server, -1);

		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage();
			if (!StringUtils.contains(message, "Models with errors: 0")) {
				Assert.fail(message+ "\n"+ report.getErrors());
			}
		}

		// Refresh Workspace
		IProject curProj = ResourcesPlugin.getWorkspace().getRoot().getProject(iProject.getName());
		curProj.refreshLocal(IResource.DEPTH_INFINITE, null);
		iofsProject.refresh();
		
		// Verify the generated RIM file with sample RIM file.
		IFolder rimFolder = curProj.getFolder(importPath);
		Assert.assertTrue("RIM source folder not found.", rimFolder.exists());
		IResource[] iResources = rimFolder.members();
		Assert.assertNotNull("Resource is NULL", iResources);

		for (IResource iResource : iResources) {
			IFile targetFile = iofsProject.getProject().getFile(importPath + iResource.getName());
			Assert.assertTrue("RIM File not exist.", targetFile.exists());
			String rimFileContent = getFileContent(targetFile.getContents());
			
			// Verify *.domain parameter
			Assert.assertTrue(verifyContainsIgnoreCase(rimFileContent, "resource AaArrAzAccountings"));
			Assert.assertTrue(verifyContainsIgnoreCase(rimFileContent, "resource aaarrazaccounting"));
			
			// verify *.enquiry parameter
			Assert.assertTrue(verifyContainsIgnoreCase(rimFileContent, "resource AaPropertyLists"));
			Assert.assertTrue(verifyContainsIgnoreCase(rimFileContent, "resource aapropertylist"));
			
			// Verify *.version parameter
			Assert.assertTrue(verifyContainsIgnoreCase(rimFileContent, "resource AaActivityClass_AaAudits"));
			Assert.assertTrue(verifyContainsIgnoreCase(rimFileContent, "resource aaactivityclass_aaaudit"));
			
		}
		Assert.assertTrue("Test complete", true);
	}
	
	/**
	 * Match string from RIM file content.
	 * @param rimFileContent
	 * @param verifyText
	 * @return
	 */
	private boolean verifyContainsIgnoreCase(String rimFileContent, String verifyText) {

		if (verifyText.equals(""))
			return true;
		if (rimFileContent == null || verifyText == null || rimFileContent.equals(""))
			return false;

		Pattern p = Pattern.compile(verifyText, Pattern.CASE_INSENSITIVE + Pattern.LITERAL);
		Matcher m = p.matcher(rimFileContent);
		return m.find();
	}

	/**
	 * Get the value from InputStream
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private String getFileContent(InputStream inputStream) throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer);
		inputStream.close();
		writer.close();
		return writer.toString();
	}
	
	/**
	 * RIM Import
	 * @param server
	 * @param nbModels
	 * @throws Exception
	 */
	private void importModels(ExternalT24Server server, int nbModels) throws Exception {
		try {
			importer.setServer(server);
		} catch (Throwable e) {
			Assert.fail(e.getMessage());
		}
		importer.setContainer(iProject.getFolder(importPath));
		importer.setModelName(MODEL_NAME);

		List<String> entityNames = new ArrayList<String>();
		collectEntityNames(importer.getApplicationSelector().getFilteredModels(), entityNames);
		collectEntityNames(importer.getEnquirySelector().getFilteredModels(), entityNames);
		collectEntityNames(importer.getVersionSelector().getFilteredModels(), entityNames);
		
		Set<InteractionDetail> entities = new HashSet<InteractionDetail>();
		
		for (String name : entityNames) {
			InteractionDetail detail = new InteractionDetail(name);
			entities.add(detail);
		}
		
		importer.setEntities(entities);
		importer.importModels(new NullProgressMonitor());
	}

	/**
	 * Add entities of Application/Enquiry
	 * @param entities
	 * @param names
	 */
	private <T extends IExternalObject> void collectEntityNames(List<T> entities, List<String> names) {
		for (IExternalObject entity : entities) {
			names.add(entity.getName());
		}
	}

	/**
	 * Create RIM related Server configuration for TAFC.
	 * @param serverProject
	 * @throws CoreException
	 */
	private void createServerConfig(IProject serverProject, StringBuilder configuration)
			throws CoreException {
		
		mkdirs(serverProject.getFolder(new Path("/config")));
		IFile file = serverProject
				.getFile(ExternalT24Server.SERVER_PROPERTIES_PATH);

		InputStream source = new ByteArrayInputStream(configuration.toString().getBytes());
		file.create(source, true, nullProgressMonitor);
	}

	/**
	 * Create RIM related Server configuration for TAFC.
	 * @param serverProject
	 * @throws CoreException
	 */
	private void createTAFCServerConfig(IProject serverProject)
			throws CoreException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("host=vmcmtdst24.oams.com\n");
		sb.append("username=SSOUSER1\n");
		sb.append("password=123456\n");
		sb.append("t24username=SSOUSER1\n");
		sb.append("t24password=123456\n");
		sb.append("agent.port=21001\n");
		sb.append("ofsid = GCS\n");
		sb.append("strictOdata = false");
		createServerConfig(serverProject, sb);

	}
	
	/**
	 * Create RIM related Server configuration for TAFJ.
	 * @param serverProject
	 * @throws CoreException
	 */
	private void createWebServerConfig(IProject serverProject)
			throws CoreException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("host=vmcmtdst24.oams.com\n");
		sb.append("username=INPUTT\n");
		sb.append("password=123456\n");
		sb.append("ws.port=9089\n");
		sb.append("strictOdata=false");

		createServerConfig(serverProject, sb);
	}

	/**
	 * Create Sample DS project/ Server Configuration project.
	 * @param projectName
	 * @param additionalNatures
	 * @return
	 * @throws CoreException
	 */
	private IProject createProject(String projectName, String... additionalNatures) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();

		String[] newNatures = new String[natures.length + additionalNatures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		System.arraycopy(additionalNatures, 0, newNatures, natures.length, additionalNatures.length);
		newNatures[newNatures.length - 1] = JavaCore.NATURE_ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);

		return project;
	}

}

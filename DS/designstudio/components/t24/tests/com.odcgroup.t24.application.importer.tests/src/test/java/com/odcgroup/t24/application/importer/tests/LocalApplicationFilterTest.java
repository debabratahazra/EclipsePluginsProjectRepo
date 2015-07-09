package com.odcgroup.t24.application.importer.tests;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Tests Application Filter of 2589 XML local files in the resources/applications folder. 
 * @author hdebabrata
 */
public class LocalApplicationFilterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;
	
	private static final int TOTAL_APPLICATIONS = 2589;
	private static final int FILTER_COMPONENT_APPLICATIONS = 9;
	private static final int FILTER_PRODUCT_APPLICATIONS = 5;

	private	ApplicationImporter importer;

	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("Server");
		project = createProject("project", OfsCore.NATURE_ID);
		OfsCore.getOfsProjectManager().getOfsProject(project);
		
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
	
	@Test
	public void testFilterApplicationsFromLocalServer() throws Exception {
		URL url =  Platform.getBundle("com.odcgroup.t24.application.importer.tests").getEntry("resources/applications");
		File xmlFolder = new File(FileLocator.toFileURL(url).toURI());
		LocalFileServer server = new LocalFileServer("S-ID","S-NAME", serverProject, xmlFolder);

		importer.setServer(server);
		
		List<ApplicationDetail> details = importer.getFilteredModels();
		Assert.assertEquals("Not loaded all applications from local.", TOTAL_APPLICATIONS, details.size());
		
		importer.getFilter().setComponent("*Account");
		details = importer.getFilteredModels();
		Assert.assertEquals("Not filtered properly in Component", FILTER_COMPONENT_APPLICATIONS, details.size());
		
		importer.getFilter().setProduct("?A");
		details = importer.getFilteredModels();
		Assert.assertEquals("Not filtered properly in Product", FILTER_PRODUCT_APPLICATIONS, details.size());
	}
}

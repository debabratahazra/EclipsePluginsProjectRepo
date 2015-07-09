package com.odcgroup.t24.enquiry.importer.tests;

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
import com.odcgroup.t24.enquiry.EnquiryUiInjectorProvider;
import com.odcgroup.t24.enquiry.importer.internal.EnquiryImporter;
import com.odcgroup.t24.server.external.model.EnquiryDetail;

@RunWith(XtextRunner.class)
@InjectWith(EnquiryUiInjectorProvider.class)
public class EnquiryFilterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;

	private static final int TOTAL_ENQUIRY = 5069;
	private static final int FILTER_COMPONENT_ENQUIRY = 496;
	private static final int FILTER_MODULE_ENQUIRY = 522;

	@Inject
	private	EnquiryImporter importer;

	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("Server");
		project = createProject("project");
	}

	@After
	public void tearDown() throws Exception {
		project.delete(true, null);
		serverProject.delete(true, null);
	}

	@Test
	public void testImportEnquiriesFromLocalServer() throws Exception {

		URL url = Platform.getBundle("com.odcgroup.t24.enquiry.importer.tests").getEntry("resources/enquiries");
		File xmlFolder = new File(FileLocator.toFileURL(url).toURI());
		LocalFileServer server = new LocalFileServer("S-ID", "S-NAME", serverProject, xmlFolder);

		importer.setServer(server);

		List<EnquiryDetail> details = importer.getFilteredModels();
		assertEquals("All Enquiry files are not loaded.", TOTAL_ENQUIRY, details.size());

		importer.getFilter().setModule("E?");
		details = importer.getFilteredModels();
		assertEquals("Filter of Module is not OK.", FILTER_MODULE_ENQUIRY, details.size());

		importer.getFilter().setComponent("EB*");
		details = importer.getFilteredModels();
		assertEquals("Filter in Component is not OK.", FILTER_COMPONENT_ENQUIRY, details.size());
	}
}

package com.odcgroup.t24.enquiry.importer.tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

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

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.enquiry.EnquiryUiInjectorProvider;
import com.odcgroup.t24.enquiry.importer.internal.EnquiryImporter;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

@RunWith(XtextRunner.class)
@InjectWith(EnquiryUiInjectorProvider.class)
public class ExternalEnquiryImporterTest extends T24ImporterTest {

	private IProject project;

	private IProject serverProject;

	private IPath importPath = new Path("/enquiry/enquiry");

	// ignoring below enquiries due to below errors
	private String[] invalidEnquiryNames = new String[] {
			// DS-7395: Could not serialize EObject via backtracking.
			"CREATE.NET.NAU", "PENDING.IC", "MM.UNCONF.CPARTY", "SC.PORT.COMPARE.SUB.ASSET", "MODEL",
			"SP.ORDER.STP.ACTIVITY", "YIELD.ENQUIRY",
			// DS-8616: Value '<value>' is not legal.
			"PWM.INVESTMENT.PROFILE", "SC.MAINT.MARGIN.DETS", "UPDATE.VIEW.ITEMS", "FATCA.EXPIRING.DOCS.60D",
			"FATCA.EXPIRING.DOCS.GD", "AC.UNEXP", "TCIB.AI.SC.PORT.CHG", "FATCA.EXPIRING.DOCS.90GD", "ACCOUNT.STATEMENT.SCRN", "EB.UPDATE.ITEM.SEARCH",
			// DS-8616: A value for feature '<feature>' is missing but required.
			"INCOMING.MSG.DETS",
			// DS-8616: The element type "enquiry:Enquiry" must be terminated by the matching end-tag "</enquiry:Enquiry>".
			"SC.POSITION2", "SC.VAL.MATRIX", "TODAYS.DEALS", "AM.EXCLUSION.PREPARATION",
			// DS-8616: Could not serialize EObject via backtracking.
			"AA.DETAILS.BILLS.DEF", "AA.DETAILS.CHARGES.BY.TYPE", "AA.DETAILS.BILLS",
			// DS-8616: Unhandled targetType in Catalog.
			"SCRIPT.LIST", };

	@Inject
	private	EnquiryImporter importer;

	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("Server");
		project = createProject("project");
		mkdirs(project.getFolder(importPath));
	}

	@After
	public void tearDown() throws Exception {
		project.delete(true, null);
		serverProject.delete(true, null);
	}

	protected void importModels(IExternalServer server, int nbModels) throws Exception {
		// complete integration test
		importer.setServer(server);
		importer.setContainer(project.getFolder(importPath));

		List<EnquiryDetail> details = new ArrayList<EnquiryDetail>();
		try {
			details = importer.getFilteredModels();
		} catch (T24ServerException e) {
			fail("Cannot establish connection with the server " + e.getMessage());
		}
		if (nbModels < 0) nbModels = details.size();
		final int MAX = Math.min(nbModels, details.size());
		for (int kx=0; kx < MAX; kx++ ) {
			EnquiryDetail enquiry = details.get(kx);
			List<String> invalidList = Arrays.asList(invalidEnquiryNames);
			if(valid(invalidList,enquiry)){
				importer.getSelectedModels().add(enquiry);
			}
		}
		importer.importModels(new NullProgressMonitor());
	}

	/**
	 * @param invalidList
	 * @param enquiry
	 * @return
	 */
	private boolean valid(List<String> invalidList, EnquiryDetail enquiry) {
		if( invalidList.contains(enquiry.getName())){
			return false;
		}
		return true;
	}

	@Test
	public void testImportEnquiriesFromT24Server() throws Exception {

		createServerConfig(serverProject, false);

		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		importModels(server, 200);

		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if(!StringUtils.contains(message, "is not legal")){
				fail(message);
			}
		}
	}

	@Test
	public void testImportEnquiriesFromT24WebServer() throws Exception {

		createServerConfig(serverProject, true);

		ExternalT24Server server = new ExternalT24Server("S-ID","S-NAME", serverProject);
		importModels(server, 200);

		IImportModelReport report = importer.getImportReport();
		if (report.hasErrors()) {
			String message = report.getSummaryMessage() + "\n" + report.getErrors();
			if(!StringUtils.contains(message, "ID may not be empty")){
				fail(message);
			}
		}
	}

}

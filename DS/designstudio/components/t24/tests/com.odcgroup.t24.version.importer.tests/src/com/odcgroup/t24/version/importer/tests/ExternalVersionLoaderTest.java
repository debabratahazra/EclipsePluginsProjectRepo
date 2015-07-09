package com.odcgroup.t24.version.importer.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class ExternalVersionLoaderTest extends T24ImporterTest {

	private IProject serverProject;
	private ExternalT24Server t24server;

	@Before
	public void setUp() throws Exception {
		serverProject = createServerProject("Server");
		createServerConfig(serverProject, false);
		t24server = new ExternalT24Server("S-ID","S-NAME", serverProject);
	}

	@After
	public void tearDown() throws Exception {
		serverProject.delete(true, null);
	}
	
	@Test
	public void testVersionLoaderProvider() throws T24ServerException {
		IExternalLoader loader = t24server.getExternalLoader(VersionDetail.class);
		assertTrue("VersionLoader is not defined", loader != null);
	}

	@Test
	public void testConnection() throws T24ServerException {
		IExternalLoader loader = t24server.getExternalLoader(VersionDetail.class);
		try {
			loader.open();
		} catch (T24ServerException ex) {
			fail("Cannot establish connection with the server " + ex.getMessage());
		} finally {
			loader.close();
		}
	}

	@Test
	public void testGetObjectDetails() throws T24ServerException {
		IExternalLoader loader = t24server.getExternalLoader(VersionDetail.class);
		try {
			try {
				loader.open();
			} catch (T24ServerException ex) {
				fail("Cannot establish connection with the server " + ex.getMessage());
			}
			List<VersionDetail> details = loader.getObjectDetails();
			assertTrue("Collection cannot be null", details != null);
			assertTrue("No Enquiries defined on the T24 server", !details.isEmpty());
		} finally {
			loader.close();
		}

	}

	@Test
	public void testGetObjectAsXML() throws T24ServerException {
		IExternalLoader loader = t24server.getExternalLoader(VersionDetail.class);
		try {
			try {
				loader.open();
			} catch (T24ServerException ex) {
				fail("Cannot establish connection with the server " + ex.getMessage());
			}
			List<VersionDetail> details = loader.getObjectDetails();
			if (!details.isEmpty()) {
				String xml = loader.getObjectAsXML(details.get(0));
				assertTrue("Enquiry XML stream should not be null/empty", StringUtils.isNotBlank(xml));
			}
		} finally {
			loader.close();
		}
	}


}

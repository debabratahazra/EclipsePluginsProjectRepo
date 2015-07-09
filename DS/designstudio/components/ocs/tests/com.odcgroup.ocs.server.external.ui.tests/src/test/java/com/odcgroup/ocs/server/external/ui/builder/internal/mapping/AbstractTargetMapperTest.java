package com.odcgroup.ocs.server.external.ui.builder.internal.mapping;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.model.internal.ExternalServer;
import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

public class AbstractTargetMapperTest  {

	private IExternalServer classicalServer;
	private AbstractTargetMapper mapper;

	@Before
	public void setUp() {
		classicalServer = new ExternalServer("someId", "someName");
		mapper = new AbstractTargetMapper(classicalServer) {
			public void configure(Map<String, String> builderConfig) {
				super.configure(builderConfig);
			}
			public File getTarget(String source) throws CoreException {
				return null;
			}
			public boolean needsPrepareDeploymentDestinations()
					throws CoreException {
				return false;
			}
			public IStatus prepareDeploymentDestinations() throws CoreException {
				return null;
			}
			@Override
			public IStatus undeployDestinations() throws CoreException {
				return null;
			}
		};
	}

	/**
	 * Validate that if the server installation is lost, the deployment
	 * fails instead of deploying to C:\
	 * @throws CoreException
	 * @throws IOException
	 */
	@Test
	public void testDs4322DeployServerWorksInUnpredictableWays() throws CoreException, IOException {
		fakeCorrectInstallationDir();

		// Shouldn't throw CoreException
		mapper.configure(Collections.<String,String> emptyMap());
		mapper.getDestination();

		boolean coreExceptionThrown = false;
		OCSPluginActivator.getDefault().getProjectPreferences().put(OCSRuntimePreference.INSTALL_DIR, "");
		try {
			mapper.getDestination();
		} catch (CoreException e) {
			coreExceptionThrown = true;
		}
		Assert.assertTrue("When the preferences are lost, should throw a CoreException", coreExceptionThrown);
	}

	/**
	 * @throws IOException
	 */
	private void fakeCorrectInstallationDir() throws IOException {
		ProjectPreferences prefs = OCSPluginActivator.getDefault().getProjectPreferences();
		File tmpFile = File.createTempFile("ds4322", "unitTest");
		tmpFile.deleteOnExit();

		prefs.put(OCSRuntimePreference.INSTALL_DIR, tmpFile.getAbsolutePath());
	}
	
	@Test
	public void testDS4625manualWuiBlockNotRenamedWhenDeployingProjectInWeblogicServer_callNonConfiguredMapper() {
		try {
			mapper.getDestination();
			Assert.fail();
		} catch (CoreException e) {
			// OK
		}
	}

	@Test
	public void testDS4625manualWuiBlockNotRenamedWhenDeployingProjectInWeblogicServer_callProperlyConfiguredMapper() throws CoreException, IOException {
		fakeCorrectInstallationDir();
		mapper.configure(Collections.<String,String> emptyMap());
		mapper.getDestination();
	}

}

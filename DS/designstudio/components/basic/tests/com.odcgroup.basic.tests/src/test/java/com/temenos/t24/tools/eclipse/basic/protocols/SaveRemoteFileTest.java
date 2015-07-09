package com.temenos.t24.tools.eclipse.basic.protocols;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.workbench.core.IOfsProject;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.PopupKiller;

/**
 * Test class for saving files into remote sites using RemoteOperationsManager.
 *
 * @author vramya
 *
 */
public class SaveRemoteFileTest extends AbstractConfigTest {
	private IProject iProject;
	private IOfsProject iofsProject = null;
	private IProject serverProject;
	private RemoteOperationsManager opsMgr;
	private RemoteSitesManager sitesManager;
	private static final String SOURCE_FILE = "testfiles/FUNDS.TRANSFER.FIELDS.b";

	@Before
	public void setUp() throws Exception {
		iofsProject = createModelsProject();
		iProject = iofsProject.getProject();
		opsMgr = RemoteOperationsManager.getInstance();
		sitesManager = RemoteSitesManager.getInstance();
		serverProject = createServerProject("test-server");
		copyFromClasspathToModelsProject(SOURCE_FILE);
	}

	@BeforeClass
	public static void setupPopupKillerThread() {
		new PopupKiller().start();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
		if (serverProject != null) {
			serverProject.delete(true, null);
		}
	}

	@Ignore @Test
	public void testSaveFile() throws Exception {
		createServerConfig(serverProject, false);
		getTAFCExternalServer().setState(IDSServerStates.STATE_ACTIVE);
		sitesManager.loadAllRemoteSitesForce();

		IFile file = iProject.getFile("/" + SOURCE_FILE);
		boolean isSaved = opsMgr.saveFile(sitesManager.getDefaultSite(), "/glodev1/bnk/bnk.run/RTC.BP",
				"FUNDS.TRANSFER.FIELDS", file);
		assertTrue(isSaved);

	}
}

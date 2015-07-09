package com.odcgroup.t24.server.external.ui.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.prefs.Preferences;

import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.nature.T24ExternalServerNature;

public class T24ServerStateTest extends AbstractServerTest {

	private IProject serverProject;
	
	@Before
	public void setUp() throws CoreException {
		serverProject = createJavaProject("someServer", T24ExternalServerNature.NATURE_ID).getProject();

		mkdirs(serverProject.getFolder(new Path("/config")));
		
		IFile file = serverProject.getFile(ExternalT24Server.SERVER_PROPERTIES_PATH);
		String contents = "Whatever";
		InputStream source = new ByteArrayInputStream(contents.getBytes());
		file.create(source, true, null);
	}
	
	@After
	public void tearDown() throws CoreException {
		serverProject.delete(true, null);
	}
	
	@Test
	public void testDS5922ServerStateOnDSRestart() throws T24ServerException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		// Given
		ExternalT24Server server = new ExternalT24Server("someServerId", "someServerName", serverProject);
		server.setState(Integer.valueOf("7"));
		Assert.assertEquals(server.getState(), 7);
		// When
		try {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbench workbench = PlatformUI.getWorkbench();
					final IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
					 
					workbench.addWorkbenchListener( new IWorkbenchListener()
					{
					    public boolean preShutdown( IWorkbench workbench, boolean forced )
					    {                            
					        activePage.closeEditors( activePage.getEditorReferences(), true);
					        activePage.closeAllPerspectives(true, true);
					        activePage.close();
					        return true;
					    }
					 
					    public void postShutdown( IWorkbench workbench )
					    {
					 
					    }
					});
					try {
						workbench.restart();
					} catch (Exception e) {
						// IGNORE service gets unregistered
					}
				}
			});
		} catch (Exception e) {
			// TODO swt disposed
		}
		// Then
		Preferences t24ExternalServerState = ConfigurationScope.INSTANCE.getNode("com.odcgroup.t24.server.external");
	    Preferences t24ExtServPref = t24ExternalServerState.node("t24ExtServ");
	    Assert.assertEquals(Integer.valueOf(t24ExtServPref.get("state","7")), new Integer(7));
	}
}

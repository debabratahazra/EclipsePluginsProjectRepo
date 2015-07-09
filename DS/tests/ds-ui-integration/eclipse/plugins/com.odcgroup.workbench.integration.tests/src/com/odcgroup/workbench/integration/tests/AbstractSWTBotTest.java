package com.odcgroup.workbench.integration.tests;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT_GEN;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

abstract public class AbstractSWTBotTest {

	protected static final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	protected static final MenuPageObject menu = new MenuPageObject();
	protected static final OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	@BeforeClass
	public static void createProject() throws Exception {		
		// create DS project
		menu.createNewModelProject(DS_PROJECT);
	}

	@AfterClass
	public static void deleteProjects() throws CoreException {
		for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			project.delete(true, null);
		}
	}

	public AbstractSWTBotTest() {
		super();
	}

}
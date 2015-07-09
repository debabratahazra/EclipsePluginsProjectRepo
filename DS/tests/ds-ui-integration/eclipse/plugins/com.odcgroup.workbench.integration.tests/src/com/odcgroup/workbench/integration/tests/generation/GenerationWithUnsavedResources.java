package com.odcgroup.workbench.integration.tests.generation;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.ProjectUtils;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.ModuleEditorPageObject;

@RunWith(SWTBotJunit4ClassRunner.class)
public class GenerationWithUnsavedResources extends AbstractSWTBotTest {
	
	private String projectName = null;
	private long oldTimeout;
	
	private static final String MODULE = "Module1";
	
	@Before
	public void setUp() throws Exception {
		oldTimeout = SWTBotPreferences.TIMEOUT;
		// we reduce the timeout as the code generation won't go through because of the dialog
		SWTBotPreferences.TIMEOUT = 1000L;
	}
	
	@After
	public void tearDown() throws Exception {
		if(projectName != null) {
			ProjectUtils.deleteModelProject(projectName);
		}
		SWTBotPreferences.TIMEOUT = oldTimeout;
	}
	
	@Test
	public void codeGenerationWithUnsavedResourcesWillBringUpDialog() throws Exception {
		projectName = "unsaved-resources-models";
		createProject(projectName);
		ModuleEditorPageObject moduleEditor = on.createModule(projectName, MODULE);
		createLabel(moduleEditor);
		
		on.runCodeGeneration(projectName);
		SWTBotShell shell = bot.shell("Save resources and generate code");
        shell.activate();
        shell.bot().button("OK").click();
        waitUntilDone(shell, "Running Code Generation", 10000);
        moduleEditor.close();
	}

	/**
	 * @param shell
	 */
	private void waitUntilDone(SWTBotShell shell, String title, long timeout) {
		try {
			bot.shell(title);
			bot.waitUntil(shellCloses(shell), timeout);	
        }
	    catch (WidgetNotFoundException e) {
			// ignore - code gen is sometimes too quick for the Running Code Generation popup to appear
		}
	}
	
	private void createProject(String projectName) {
		MenuPageObject menu = new MenuPageObject();
		menu.createNewModelProject(projectName);
	}

	private void createLabel(ModuleEditorPageObject moduleEditor) {
		moduleEditor.addHBox(30, 30);
		moduleEditor.addLabel(35, 35);
	}
}

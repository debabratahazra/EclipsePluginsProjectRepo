package com.odcgroup.workbench.integration.tests.module;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_MODULE;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

@RunWith(SWTBotJunit4ClassRunner.class)
public class ModuleDesignerTest {

	private static SWTGefBot bot = new SWTGefBot();
	
	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	private static SWTBotGefEditor editor;

	@BeforeClass
	public static void setUp() throws Exception {
		menu.createNewModelProject(DS_PROJECT);
		IPath path = new Path(DS_PROJECT+"/Modules");
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		assertNotNull("Package was not created correctly!", pkgTreeItem);
		pkgTreeItem.contextMenu("New Module...").click();
		SWTBotShell shell = bot.shell("New Module");
		shell.activate();
		bot.text("MyModule").setText(TEST_MODULE);
		bot.button("Finish").click();
		editor = bot.gefEditor(TEST_MODULE +".module");
	}
	
	@AfterClass
	public static void tearDown() throws CoreException {
		editor.save();
		editor.close();
		on.selectNode(new Path(DS_PROJECT)).collapse();
		AbstractSWTBotTest.deleteProjects();
	}
	
	@Test
	public void createModule() throws Exception {
		editor.activateTool("Horizontal Box");
		editor.click(30, 30);
		editor.activateTool("Label");
		editor.click(35, 35);

//		bot.viewByTitle("Properties").setFocus();
//		SWTBotListElement.get(bot, 2).select();
//		bot.table().click(0, 1);
//		bot.button().click();
//		bot.shell("Translation").activate();
//		bot.textWithLabel("Translation").setText("User Name");
//		bot.button().click();
		
		editor.activateTool("Text Field");
		editor.click(100, 35);
		editor.activateTool("Button");
		editor.click(400, 35);
		
//		bot.viewByTitle("Properties").setFocus();
//		SWTBotListElement.get(bot, 3).select();
//		bot.table().click(0, 1);
//		bot.button().click();
//		bot.shell("Translation").activate();
//		bot.textWithLabel("Translation").setText("Login");
//		bot.button().click();
	}

}

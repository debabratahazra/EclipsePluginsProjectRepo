package com.odcgroup.workbench.integration.tests;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_MODULE;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

public class RichTextTest {

	private static SWTGefBot bot = new SWTGefBot();
	
	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	private static SWTBotGefEditor editor;
	private static SWTBotView propertiesView;
	
	private static String DS_RT_PROJECT = "testrichtext-models";

	@BeforeClass
	public static void setUp() throws Exception {
		menu.createNewModelProject(DS_RT_PROJECT);
		IPath path = new Path(DS_RT_PROJECT + "/Modules");
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		assertNotNull("Package was not created correctly!", pkgTreeItem);
		pkgTreeItem.contextMenu("New Module...").click();
		SWTBotShell shell = bot.shell("New Module");
		shell.activate();
		bot.text("MyModule").setText(TEST_MODULE);
		bot.button("Finish").click();
		editor = bot.gefEditor(TEST_MODULE +".module");
		propertiesView = bot.viewByTitle("Properties");
	}
	
	@AfterClass
	public static void tearDown() throws CoreException {
		editor.save();
		editor.close();
		on.selectNode(new Path(DS_RT_PROJECT)).collapse();
		AbstractSWTBotTest.deleteProjects();
	}
	
	@Test
	public void testRichTextEditor() throws Exception {
		editor.activateTool("Horizontal Box");
		editor.click(30, 30);
		editor.activateTool("Label");
		editor.click(35, 35);
		propertiesView.show();
		propertiesView.setFocus();
		SWTBotTestUtil.selectTabbedPropertyView(propertiesView.bot(), "Translation");
		bot.button("Edit Translation").click();
		SWTBotShell shell = bot.shell("Translation");
		shell.activate();
		shell.bot().styledText().setText("This is Rich Text");
		shell.bot().styledText().selectLine(0);
		shell.bot().button("OK").click();
	}
}


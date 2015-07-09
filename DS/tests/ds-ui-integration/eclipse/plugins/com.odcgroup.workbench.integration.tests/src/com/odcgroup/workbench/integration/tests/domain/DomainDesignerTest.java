package com.odcgroup.workbench.integration.tests.domain;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_DOMAIN;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;


@RunWith(SWTBotJunit4ClassRunner.class)
public class DomainDesignerTest {

	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	private static SWTBotEditor editor;

	@BeforeClass
	public static void setUp() throws Exception {
		menu.createNewModelProject(DS_PROJECT);
		IPath path = new Path(DS_PROJECT+"/Domains");
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		pkgTreeItem.contextMenu("New Domain...").click();
		SWTBotShell shell = bot.shell("New Domain");
		shell.activate();
		bot.text("MyName.domain").setText(TEST_DOMAIN + ".domain");
		bot.button("Finish").click();
		editor = bot.editorByTitle(TEST_DOMAIN + ".domain");
	}

	@Test
	public void createClass() throws Exception {
		SWTBotTree editorTree = editor.bot().tree();
		SWTBotTreeItem rootItem = editorTree.getTreeItem("NewDomain (http://www.odcgroup.com/new-domain)");
		rootItem.select();
//		try {
//			rootItem.contextMenu("New").contextMenu("Class").click();
//		} catch(WidgetNotFoundException e) {
//			rootItem.contextMenu("New").contextMenu("Class").click();
//		}
//		editorTree.getTreeItem("NewClass").contextMenu("New").contextMenu("Attribute").click();
//		assertEquals(3, editorTree.getAllItems().length);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		menu.close();
		on.selectNode(new Path(DS_PROJECT)).collapse();
		AbstractSWTBotTest.deleteProjects();
	}
}

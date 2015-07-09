package com.odcgroup.workbench.integration.tests.designer.menu;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_PAGEFLOW;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

/**
 * @author ramapriyamn
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class MenuItemPageflowTest {

	private static SWTGefBot bot = new SWTGefBot();
	protected static final MenuPageObject menu = new MenuPageObject();
	protected static final OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
	private static SWTBotEditor editor;

	@BeforeClass
	public static void setUp() throws Exception {
		menu.createNewModelProject(DS_PROJECT);
		createPageflow();
		createMenu();
		editor = bot.editorByTitle("Menu.menu");
	}
		
	private static void createMenu() throws Exception {
		IPath path = new Path(DS_PROJECT+"/Menus");
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		assertNotNull("Package was not created correctly!", pkgTreeItem);
		pkgTreeItem.contextMenu("New Menu...").click();
	}
	
	private static void createPageflow() throws Exception {
		IPath pageflowPath = new Path(DS_PROJECT+"/Pageflows");
		SWTBotTreeItem pkgTreeItem = on.createPackage(pageflowPath, STD_PACKAGE);
		pkgTreeItem.contextMenu("New Pageflow...").click();
		SWTBotShell shell = bot.shell("New Pageflow");
		shell.activate();
		bot.text("MyName").setText(TEST_PAGEFLOW);
		bot.text("").setText("testpfl Description");
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
		menu.close();
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		on.selectNode(new Path(DS_PROJECT)).collapse();
		if (editor != null) {
			editor.close();
		}
		AbstractSWTBotTest.deleteProjects();
	}

	@Test
	public void checkBrowseTextFieldModifiable() {

		//click on menu
		SWTBotTree editorTree = editor.bot().tree();
		SWTBotTreeItem rootItem = editorTree.getTreeItem("RootMenu");
		rootItem.select();
		// select the properties view
		bot.viewByTitle("Properties").setFocus();
		bot.button("Browse...").click();

		// activate the event dialog
		SWTBotShell shell = bot.shell("Model object selection");
		shell.activate();

		// Search for the pageflow in search text field
		shell.bot().text(0).setText("t");
		
		SWTBotTable widgetListEle = shell.bot().tableWithLabel("Resource:");
		widgetListEle.setFocus();
		widgetListEle.select(0);
		bot.button("OK").click();
		// verify that Browse has pageflow configured
		SWTBotText browseTxt = bot.text("resource:///testpkg/Testpfl.pageflow");
		assertEquals("Wrong text selected", "resource:///testpkg/Testpfl.pageflow", browseTxt.getText());
		browseTxt.setFocus();

		//Check whether the text field is enabled 
		assertTrue("Text Field is not enabled",browseTxt.isEnabled());
	}

}
package com.odcgroup.workbench.integration.tests.jira;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static junit.framework.Assert.assertNotNull;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.ContextMenuHelper;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

@RunWith(SWTBotJunit4ClassRunner.class)
public class FileSystemChangeRefreshEditorTests {

	private static SWTGefBot bot = new SWTGefBot();
	
	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
	private static final String PROJ_NAME = "ds4119-models";

	private SWTBotGefEditor editor;
	
	@BeforeClass
	public static void createProject() {
		menu.createNewModelProject(PROJ_NAME);
	}
	
	@AfterClass
	public static void deleteProjects() throws CoreException {
		AbstractSWTBotTest.deleteProjects();
	}
	
	@Test
	public void pageDesigner_DS4119() throws Exception  {
		IPath path = new Path(PROJ_NAME+"/Modules");
		on.selectNode(path);
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		pkgTreeItem.contextMenu("New Module...").click();
		SWTBotShell shell = bot.shell("New Module");
		shell.activate();
		String MOD_NAME = "Test4119";
		bot.text("MyModule").setText(MOD_NAME);
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
		SWTBotEditor botEdit = bot.editorByTitle(MOD_NAME +".module");
		botEdit.setFocus();
		botEdit.close();
		
		IPath modpath = new Path(PROJ_NAME+"/Modules/" + STD_PACKAGE + "/" + MOD_NAME);
		on.selectNode(modpath).doubleClick();
		editor = bot.gefEditor(MOD_NAME +".module");
		
		editor.activateTool("Horizontal Box");
		editor.click(30, 30);
		editor.activateTool("Label");
		editor.click(35, 35);		
		editor.save();
		
		SWTBotTreeItem item = on.selectNode(new Path(PROJ_NAME+"/Modules/"+STD_PACKAGE+"/"+MOD_NAME));
		assertNotNull("Modules node does not exist!", item);
		ContextMenuHelper.clickContextMenu(item, "Replace With", "Local History...");
		SWTBotShell repdialog = bot.shell("Replace from Local History");
		repdialog.activate();
		bot.button("Replace").click();
		
		SWTBotShell msgdialog = bot.shell("Resource Changed");
		msgdialog.activate();
		bot.button(0).click();	
		
		editor.close();
		
	}
	
	@Test
	public void domainDesigner_DS4119() throws Exception  {
		IPath path = new Path(PROJ_NAME+"/Domains");
		on.selectNode(path);
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		pkgTreeItem.contextMenu("New Domain...").click();
		SWTBotShell shell = bot.shell("New Domain");
		shell.activate();
		String DOMAIN_NAME = "Test4119";
		bot.text("MyName.domain").setText(DOMAIN_NAME + ".domain");
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
		SWTBotTreeItem treeItem = on.selectNode(path.append(STD_PACKAGE).append(DOMAIN_NAME));
		assertNotNull(treeItem);
		SWTBotEditor editor = bot.editorByTitle(DOMAIN_NAME+".domain");
		SWTBotTree editorTree = editor.bot().tree();
		SWTBotTreeItem rootItem = editorTree.getTreeItem("NewDomain (http://www.odcgroup.com/new-domain)");
		rootItem.select();
		ContextMenuHelper.clickContextMenu(rootItem, "New","Class");
		editor.save();
		
		SWTBotTreeItem item = on.selectNode(new Path(PROJ_NAME+"/Domains/"+STD_PACKAGE));
		SWTBotTreeItem domitem = item.getItems()[0].select();
		ContextMenuHelper.clickContextMenu(domitem, "Replace With", "Local History...");
		SWTBotShell repdialog = bot.shell("Replace from Local History");
		repdialog.activate();
		bot.button("Replace").click();
		
		SWTBotShell msgdialog = bot.shell("Resource Changed");
		msgdialog.activate();
		bot.button(0).click();			
		editor.close();		
	}

}

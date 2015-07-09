package com.odcgroup.workbench.integration.tests;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static junit.framework.Assert.assertNotNull;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.odcgroup.workbench.integration.tests.util.pageobjects.ContextMenuHelper;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

public class SWTBotDomainDSLUITest {

	private static SWTGefBot bot = new SWTGefBot();
	
	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
	private static final String PROJ_NAME = "ds3665-models";

	@BeforeClass
	public static void createProject() {
		menu.createNewModelProject(PROJ_NAME);
	}
	
	@AfterClass
	public static void deleteProjects() throws CoreException {
		AbstractSWTBotTest.deleteProjects();
	}
	
	@Test
	public void domainDesigner_DS3665() throws Exception  {
		IPath path = new Path(PROJ_NAME+"/Domains");
		on.selectNode(path);
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		pkgTreeItem.contextMenu("New Domain...").click();
		SWTBotShell shell = bot.shell("New Domain");
		shell.activate();
		String DOMAIN_NAME = "Test3665";
		bot.text("MyName.domain").setText(DOMAIN_NAME + ".domain");
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
		SWTBotTreeItem treeItem = on.selectNode(path.append(STD_PACKAGE).append(DOMAIN_NAME));
		assertNotNull(treeItem);
		SWTBotEditor editor = bot.editorByTitle(DOMAIN_NAME+".domain");
		SWTBotTree editorTree = editor.bot().tree();
		SWTBotTreeItem rootItem = editorTree.getTreeItem("NewDomain (http://www.odcgroup.com/new-domain)");
		rootItem.select();
		ContextMenuHelper.clickContextMenu(rootItem, "New","Business Type");
		rootItem.select();
		ContextMenuHelper.clickContextMenu(rootItem, "New","Enumeration");
		rootItem.select();
		ContextMenuHelper.clickContextMenu(rootItem, "New","Class");
		rootItem.select();
		ContextMenuHelper.clickContextMenu(rootItem, "New","Dataset");
		
		editor.save();
		UIThreadRunnable.asyncExec(new VoidResult() {
			@Override
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					page.showView("org.eclipse.ui.navigator.ProjectExplorer");
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
		
		editor.close();
		
		SWTBotView view = bot.viewByTitle("Project Explorer");
		assertNotNull("Project Explorer view should not be null" , view);
		view.show();
		view.setFocus();
		SWTBotTree tree = null;
		
		tree = view.bot().tree();
		SWTBotTreeItem projectItem = tree.getTreeItem("ds3665-models");
		projectItem.select();
		try {
		ContextMenuHelper.clickContextMenu(projectItem , "Configure", "Add Xtext Nature");
		}catch (Exception e) {
			// ignore
		} 
		
		SWTBotTreeItem item = tree.expandNode("ds3665-models").expandNode("domain").expandNode("testpkg").expandNode("Test3665.domain");
        item.select();
        ContextMenuHelper.clickContextMenu(item, "Open With", "Other...");
        
        SWTBotShell editorShell = bot.activeShell();
        assertNotNull("Shell should not be null " + editorShell);
        editorShell.activate();
        
        SWTBotTable table = editorShell.bot().table();
        assertNotNull("Table should not be null ", table);
        table.setFocus();
        SWTBotTableItem tableItem = table.getTableItem("Domain DSL Editor");
        tableItem.select();
        assertNotNull("Table Item should not be null ", table);
        
        bot.button("OK").click();

        SWTBotView outlineView = bot.viewByTitle("Outline");
        outlineView.setFocus();
        SWTBotTree outlineTree = outlineView.bot().tree();
        assertNotNull("Outline Tree should not be null " + outlineTree);
        outlineTree.setFocus();
        SWTBotTree dslTree = outlineTree.select(0);
        
        assertNotNull("Root is not Null", dslTree.select(0));
        assertEquals("NewDomain (http://www.odcgroup.com/new-domain)", dslTree.select(0).getAllItems()[0].getText());
        dslTree.select(0).getAllItems()[0].select();
        SWTBotTreeItem dslItem = dslTree.select(0).getAllItems()[0].expand();
        dslItem.select();
        assertEquals("Length of tree" , 4, dslItem.getItems().length);
        assertEquals("Class not null", "NewClass", dslItem.getItems()[0].getText());
        assertEquals("Dataset not null", "NewDataset", dslItem.getItems()[1].getText());
        assertEquals("BusinessType not null", "NewBusinessType", dslItem.getItems()[2].getText());
        assertEquals("Enumeration not null", "NewEnumeration", dslItem.getItems()[3].getText());
	}
}

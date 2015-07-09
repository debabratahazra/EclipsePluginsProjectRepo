package com.odcgroup.workbench.integration.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

public class SVNMenuTest {
private static SWTGefBot bot = new SWTGefBot();
	
	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	private static String DS_TEAM_PROJECT = "testteam-models";
	
	@BeforeClass
	public static void setUp() throws Exception {
		menu.createNewModelProject(DS_TEAM_PROJECT);
	}
	
	@AfterClass
	public static void tearDown() throws CoreException {
		AbstractSWTBotTest.deleteProjects();
	}
	
	@Test
	public void checkTeamMenu1() throws Exception {
		IPath path = new Path(DS_TEAM_PROJECT);
		SWTBotTreeItem rootTreeItem = on.selectNode(path);
		assertNotNull("Team Menu should be shown", rootTreeItem.contextMenu("Team"));
		SWTBotMenu menuItem = rootTreeItem.contextMenu("Team");
		assertNotNull("Apply Patch Menu should be present", menuItem.menu("Apply Patch..."));
		assertNotNull("Show Local History Menu should be present",menuItem.menu("Show Local History"));
		assertNotNull("Share Projects Menu should be present", menuItem.menu("Share Projects..."));
		
		menuItem.menu("Apply Patch...").click();
		SWTBotShell shell = bot.shell("Apply Patch");
		shell.activate();
		bot.button("Cancel").click();
		shell.close();
		
		menuItem.menu("Share Projects...").click();
		shell = bot.shell("Share Project Wizard");
		shell.activate();
		bot.button("Cancel").click();
		shell.close();
	}
	
	@Test
	public void checkTeamMenu2() throws Exception {
		IPath path = new Path(DS_TEAM_PROJECT+"/Domains");
		SWTBotTreeItem rootTreeItem = on.selectNode(path);
		assertNotNull("Team Menu should be shown", rootTreeItem.contextMenu("Team"));
		SWTBotMenu menuItem = rootTreeItem.contextMenu("Team");
		assertNotNull("Apply Patch Menu should be present", menuItem.menu("Apply Patch..."));
		assertNotNull("Show Local History Menu should be present",menuItem.menu("Show Local History"));
		assertNotNull("Share Projects Menu should be present", menuItem.menu("Share Projects..."));
		
		assertFalse(menuItem.menu("Share Projects...").isActive());
		
		menuItem.menu("Apply Patch...").click();
		SWTBotShell shell = bot.shell("Apply Patch");
		shell.activate();
		bot.button("Cancel").click();
		shell.close();
	}
}

package com.odcgroup.workbench.integration.tests;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.CONTEXT_MENUITEM_GENERATECODE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.ODYSSEY_CAT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_DOMAIN;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_FRAGMENT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_MODULE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_PAGE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_PAGEFLOW;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_WORKFLOW;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner; 
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Ignore;

import com.odcgroup.workbench.integration.tests.util.pageobjects.ProjectTreeItem;

/**
 * These smoke tests test the basic functionality of Design Studio in order to see if anything is fundamentally broken.
 * This includes project and model creation, checking for menus and preferences etc.
 *
 * @author Kai Kreuzer
 *
 */

@RunWith(SWTBotJunit4ClassRunner.class)
public class SmokeTest extends AbstractSWTBotTest {

	@BeforeClass
	public static void preparation() throws Exception {
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 1000L;
			bot.viewByTitle("Welcome").close();
		} catch(WidgetNotFoundException e) {
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}

		try {
			// depending if Eclipse has been started with the right product definition
			// the name of the Odyssey perspective might be different
			menu.openPerspective(ODYSSEY_CAT + " (default)");
			//menu.openPerspective(ODYSSEY_CAT);
		} catch(IllegalArgumentException e) {
			bot.button("Cancel").click();
		} catch(WidgetNotFoundException e) {
			bot.button("Cancel").click();
		};
	}
	
	private static final int NB_RETRIES = 5;
	private static final long DELAY_BETWEEN_RETRIES = 2000;

	@Test
	public void projectComplete() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				// check for DS project
				assertNotNull("Domains node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Domains")));
				assertNotNull("Fragments node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Fragments")));
				assertNotNull("Modules node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Modules")));
				assertNotNull("Pageflows node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Pageflows")));
				assertNotNull("Pages node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Pages")));
				assertNotNull("Workflows node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Workflows")));
				assertNotNull("Translations node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Translations")));
				assertNotNull("Menus node does not exist!", on.selectNode(new Path(DS_PROJECT+"/Menus")));
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void createModule() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				IPath path = new Path(DS_PROJECT+"/Modules");
				SWTBotTreeItem rootTreeItem = on.selectNode(path);
				assertNotNull("Modules node does not exist!", rootTreeItem);
				SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
				assertNotNull("Package was not created correctly!", pkgTreeItem);
				pkgTreeItem.contextMenu("New Module...").click();
				SWTBotShell shell = bot.shell("New Module");
				shell.activate();
				bot.text("MyModule").setText(TEST_MODULE);
				bot.button("Finish").click();
				SWTBotEditor editor = bot.editorByTitle("TestModule.module");
				editor.setFocus();
				menu.close();
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}
	
	@Test @Ignore
	public void createMenu() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				IPath path = new Path(DS_PROJECT+"/Menus");
				SWTBotTreeItem rootTreeItem = on.selectNode(path);
				assertNotNull("Menus node does not exist!", rootTreeItem);
				SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
				assertNotNull("Package was not created correctly!", pkgTreeItem);
				pkgTreeItem.contextMenu("New Menu...").click();		
				SWTBotShell shell = bot.shell("New Menu");
				shell.activate();
				bot.button("Finish").click();
				SWTBotEditor editor = bot.editorByTitle("MyMenu.menu");
				editor.setFocus();
				menu.close();
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void createPageflow() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				IPath pageflowPath = new Path(DS_PROJECT+"/Pageflows");
				SWTBotTreeItem pageflowRootTreeItem = on.selectNode(pageflowPath);
				assertNotNull("Pageflows node does not exist!", pageflowRootTreeItem);
				SWTBotTreeItem pkgTreeItem = on.createPackage(pageflowPath, STD_PACKAGE);
				assertNotNull("Package was not created correctly!", pkgTreeItem);
				pkgTreeItem.contextMenu("New Pageflow...").click();
				SWTBotShell shell = bot.shell("New Pageflow");
				shell.activate();
				bot.text("MyName").setText(TEST_PAGEFLOW);
				bot.text("").setText("testpfl Description");
				bot.button("Finish").click();
				menu.close();
				SWTBotTreeItem pageflowTreeItem = on.selectNode(pageflowPath.append(STD_PACKAGE).append(TEST_PAGEFLOW));
				assertNotNull(pageflowTreeItem);
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void createDomain() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				IPath path = new Path(DS_PROJECT+"/Domains");
				SWTBotTreeItem rootTreeItem = on.selectNode(path);
				assertNotNull("Domains node does not exist!", rootTreeItem);
				SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
				assertNotNull("Package was not created correctly!", pkgTreeItem);
				pkgTreeItem.contextMenu("New Domain...").click();
				SWTBotShell shell = bot.shell("New Domain");
				shell.activate();
				bot.text("MyName.domain").setText(TEST_DOMAIN + ".domain");
				bot.radio(0).click();
				bot.button("Finish").click();
				bot.waitUntil(shellCloses(shell));
				SWTBotTreeItem treeItem = on.selectNode(path.append(STD_PACKAGE).append("testDomain"));
				assertNotNull(treeItem);
				menu.close();
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void createFragment() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				IPath path = new Path(DS_PROJECT+"/Fragments");
				SWTBotTreeItem rootTreeItem = on.selectNode(path);
				assertNotNull("Fragments node does not exist!", rootTreeItem);
				SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
				assertNotNull("Package was not created correctly!", pkgTreeItem);
				pkgTreeItem.contextMenu("New Fragment...").click();
				SWTBotShell shell = bot.shell("New Fragment");
				shell.activate();
				bot.text("MyFragment").setText(TEST_FRAGMENT);
				bot.button("Finish").click();
				menu.close();
				SWTBotTreeItem treeItem = on.selectNode(path.append(STD_PACKAGE).append(TEST_FRAGMENT));
				assertNotNull(treeItem);
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void createPage() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				IPath path = new Path(DS_PROJECT+"/Pages");
				SWTBotTreeItem rootTreeItem = on.selectNode(path);
				assertNotNull("Pages node does not exist!", rootTreeItem);
				SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
				assertNotNull("Package was not created correctly!", pkgTreeItem);
				pkgTreeItem.contextMenu("New Page...").click();
				SWTBotShell shell = bot.shell("New Page");
				shell.activate();
				bot.text("MyPage").setText(TEST_PAGE);
				bot.button("Finish").click();
				menu.close();
				SWTBotTreeItem treeItem = on.selectNode(path.append(STD_PACKAGE).append(TEST_PAGE));
				assertNotNull(treeItem);
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void createWorkflow() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				IPath path = new Path(DS_PROJECT+"/Workflows");
				SWTBotTreeItem rootTreeItem = on.selectNode(path);
				assertNotNull("Workflows node does not exist!", rootTreeItem);
				SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
				assertNotNull("Package was not created correctly!", pkgTreeItem);
				pkgTreeItem.contextMenu("New Workflow...").click();
				SWTBotShell shell = bot.shell("New Workflow");
				shell.activate();
				bot.text("MyName").setText(TEST_WORKFLOW);
				bot.button("Finish").click();
				menu.close();
				SWTBotTreeItem treeItem = on.selectNode(path.append(STD_PACKAGE).append(TEST_WORKFLOW));
				assertNotNull(treeItem);
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test @Ignore
	public void menuEntriesOnProject() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				ProjectTreeItem projectTreeItem = on.getProjectTreeItem(DS_PROJECT);
				
				// check for Generate Documentation
				projectTreeItem.contextMenu("Generate Documentation");
				
				// check for Generate Code
				projectTreeItem.contextMenu(CONTEXT_MENUITEM_GENERATECODE);
				
				// open the properties dialog
				projectTreeItem.contextMenu("Properties").click();
				SWTBotShell shell = bot.shell("Properties for " + DS_PROJECT);
				shell.activate();
				bot.tree().expandNode("Design Studio").expandNode("Code Generation");
				bot.tree().expandNode("Design Studio").expandNode("Documentation Generation");
				bot.tree().expandNode("Design Studio").expandNode("Corporate Design");
				bot.tree().expandNode("Design Studio").expandNode("Namespaces");
				bot.tree().expandNode("Design Studio").expandNode("Translation");
				bot.button("OK").click();
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void menuEntriesOnPackages() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				on.selectNode(new Path(DS_PROJECT).append("Domains").append(STD_PACKAGE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
				// DS-2144 - no more needed :
				// on.selectNode(new Path(DS_PROJECT).append("Fragments").append(STD_PACKAGE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
				on.selectNode(new Path(DS_PROJECT).append("Modules").append(STD_PACKAGE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
				on.selectNode(new Path(DS_PROJECT).append("Pageflows").append(STD_PACKAGE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
				on.selectNode(new Path(DS_PROJECT).append("Pages").append(STD_PACKAGE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
				on.selectNode(new Path(DS_PROJECT).append("Workflows").append(STD_PACKAGE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void testMenuEntriesOnModels() throws Exception {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
//		on.selectNode(new Path(DS_PROJECT).append("Domains").append(STD_PACKAGE).append("testDomain")).contextMenu(CONTEXT_MENUITEM_COMPARE);
				// !!! no codegen for domain model at model level
				// DS-2144 - no more needed :
				// on.selectNode(new Path(DS_PROJECT).append("Fragments").append(STD_PACKAGE).append("TestFragment")).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
//		on.selectNode(new Path(DS_PROJECT).append("Fragments").append(STD_PACKAGE).append("TestFragment")).contextMenu(CONTEXT_MENUITEM_COMPARE);
				on.selectNode(new Path(DS_PROJECT).append("Modules").append(STD_PACKAGE).append(TEST_MODULE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
//		on.selectNode(new Path(DS_PROJECT).append("Modules").append(STD_PACKAGE).append("TestModule")).contextMenu(CONTEXT_MENUITEM_COMPARE);
				on.selectNode(new Path(DS_PROJECT).append("Pageflows").append(STD_PACKAGE).append(TEST_PAGEFLOW)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
//		on.selectNode(new Path(DS_PROJECT).append("Pageflows").append(STD_PACKAGE).append("testpfl")).contextMenu(CONTEXT_MENUITEM_COMPARE);
				on.selectNode(new Path(DS_PROJECT).append("Pages").append(STD_PACKAGE).append(TEST_PAGE)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
//		on.selectNode(new Path(DS_PROJECT).append("Pages").append(STD_PACKAGE).append("TestPage")).contextMenu(CONTEXT_MENUITEM_COMPARE);
				on.selectNode(new Path(DS_PROJECT).append("Workflows").append(STD_PACKAGE).append(TEST_WORKFLOW)).contextMenu(CONTEXT_MENUITEM_GENERATECODE);
//		on.selectNode(new Path(DS_PROJECT).append("Workflows").append(STD_PACKAGE).append("TestWorkflow")).contextMenu(CONTEXT_MENUITEM_COMPARE);
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}

	@Test
	public void preferences() throws Exception  {
		// Note: the test is retried several times if it fails to avoid false alert
		Exception lastException = null;
		for (int i=0; i<NB_RETRIES; i++) {
			try {
				String[] preferenceItems = new String[] {
						"Display Formats",
						"Icons",
						"Pageflow Diagram",
						"Servers",
						"Workflow Diagram"
				};
				bot.menu("Window").menu("Preferences").click();
				SWTBotShell shell = bot.shell("Preferences");
				shell.activate();
				SWTBotTreeItem odysseyNode = bot.tree().expandNode("Design Studio");
				for(String item : preferenceItems) {
					assertNotNull("Window/Preferences/"+item+" not found",odysseyNode.select(item));
				}
				shell.close();
				break;
			} catch (Exception e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			throw lastException;
		}
	}
}

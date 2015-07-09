package com.odcgroup.workbench.integration.tests.translation;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TRANSLATIONS;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableColumn;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

/**
 * 
 * @author pkk
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TranslationEditorTest {

	protected static final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	protected static final MenuPageObject menu = new MenuPageObject();

	private static String EMPTY_TRANSLATIONS = " Filter empty translations ";
	private static String DEPPROJ_TRANSLATIONS = " Filter dependent project translations ";
	
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	private static SWTBotEditor editor;

	@BeforeClass
	public static void setUp() throws Exception {
		menu.createNewModelProject(DS_PROJECT);
		IPath path = new Path(DS_PROJECT+"/" + TRANSLATIONS);
		on.selectNode(path).doubleClick();
		editor = bot.editorByTitle(DS_PROJECT + " Translation Overview");
	}
	
	@Test
	public void domainTranslationTab() throws Exception {
		checkTab("Domain");
	}
	
	@Test
	public void fragmentTranslationTab() throws Exception {
		checkTab("Fragment");
	}
	
	@Test
	public void moduleTranslationTab() throws Exception {
		checkTab("Module");
	}
	
	@Test
	public void pageTranslationTab() throws Exception {
		checkTab("Page");
	}
	
	@Test
	public void fuleTranslationTab() throws Exception {
		checkTab("Rule");
	}
	
	@Test
	public void sorkflowTranslationTab() throws Exception {
		checkTab("Workflow");
	}
	
	@Test
	public void allTranslationTab() throws Exception {
		checkTab("All");
	}
	
	/**
	 * @throws Exception
	 */
	private void checkTab(String tabName) throws Exception {
		SWTBotCTabItem tabItem = editor.bot().cTabItem(tabName);
		tabItem.setFocus();
		
		SWTBotTable table = editor.bot().table();
		SWTBotTableColumn tCol = table.header("Translation of");
		tCol.setFocus();
		tCol = table.header("Kind");
		tCol.setFocus();
		
		SWTBotButton bttn = editor.bot().button("Edit Source Model");
		assertFalse(bttn.isEnabled());
	}
	
	@Test
	public void translationEditorFilterControls() throws Exception {
		SWTBotCheckBox emptyChkBox = editor.bot().checkBox(EMPTY_TRANSLATIONS);
		assertTrue("Filter empty translations is default checked", emptyChkBox.isChecked());
		SWTBotCheckBox depPrjTrans = editor.bot().checkBox(DEPPROJ_TRANSLATIONS);
		assertTrue("Filter dependent project translations is default checked",depPrjTrans.isChecked());
	}

	@AfterClass
	public static void tearDown() throws Exception {
		editor.close();
		on.selectNode(new Path(DS_PROJECT)).collapse();
		AbstractSWTBotTest.deleteProjects();
	}


}

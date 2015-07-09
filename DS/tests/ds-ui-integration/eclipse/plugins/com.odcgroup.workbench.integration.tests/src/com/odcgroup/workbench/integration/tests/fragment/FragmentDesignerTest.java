package com.odcgroup.workbench.integration.tests.fragment;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.TEST_FRAGMENT;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

/**
 * @author amc
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class FragmentDesignerTest extends AbstractSWTBotTest {
	
	private static SWTGefBot bot = new SWTGefBot();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
	
	private static final int SHORTCUT_MODIFIER = SWT.MOD1;
	private static final char SHORTCUT_KEY = 'g';
	private static final String CANNOT_GENERATE_FRAGMENT_POPUP_TEXT = "Cannot generate fragment";
	private static final String PACKAGE = "generateCodeShortcutPackage";
	private static final int FIRST_WIDGET_X = 20;
	private static final int FIRST_WIDGET_Y = 20;
	
	private static String storedKeyboardLayout;
	private static SWTBotGefEditor editor;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		storedKeyboardLayout = SWTBotPreferences.KEYBOARD_LAYOUT;
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		SWTBotPreferences.KEYBOARD_STRATEGY = "org.eclipse.swtbot.swt.finder.keyboard.MockKeyboardStrategy";
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		SWTBotPreferences.KEYBOARD_LAYOUT = storedKeyboardLayout;
		if(editor != null) {
			editor.close();
		}
	}
	
	@Test
	public void generateCodeShortcutInFragmentEditorProducesInfoPopup() throws Exception {
		IPath fragmentsRootPath = new Path(DS_PROJECT+"/Fragments");
		createTestFragment(fragmentsRootPath, TEST_FRAGMENT);
		
		SWTBotTreeItem treeItem = on.selectNode(fragmentsRootPath.append(PACKAGE).append(TEST_FRAGMENT));
		assertNotNull(treeItem);
		
		editor = bot.gefEditor(TEST_FRAGMENT+"."+PageConstants.FRAGMENT_FILE_EXTENSION);
		
		assertPopupAppearsOnCleanEditorWhenShortcutIsTriggered(editor);
		assertPopupAppearsOnDirtyEditorWhenShortcutIsTriggered(editor);
	}

	private void assertPopupAppearsOnDirtyEditorWhenShortcutIsTriggered(SWTBotGefEditor editor) {
		addLabelToEditor(editor);
		assertTrue(editor.isDirty());
		triggerShortcut(editor);
		pressOkOnPopup();
		assertFalse(editor.isDirty());
	}

	private void assertPopupAppearsOnCleanEditorWhenShortcutIsTriggered(SWTBotGefEditor editor) {
		triggerShortcut(editor);
		pressOkOnPopup();
	}

	/**
	 * Relies on SmokeTest.class#testCreateProject to create the project
	 */
	private void createTestFragment(IPath fragmentsRootPath, String fragmentName) {
		SWTBotTreeItem rootTreeItem = on.selectNode(fragmentsRootPath);
		assertNotNull("Fragments node does not exist!", rootTreeItem);
		SWTBotTreeItem pkgTreeItem = on.createPackage(fragmentsRootPath, PACKAGE);
		assertNotNull("Fragment was not created correctly!", pkgTreeItem);
		pkgTreeItem.contextMenu("New Fragment...").click();
		SWTBotShell shell = bot.shell("New Fragment");
		shell.activate();
		bot.text("MyFragment").setText(fragmentName);
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
	}

	private void triggerShortcut(SWTBotGefEditor editor) {
		editor.setFocus();
		editor.toTextEditor().pressShortcut(SHORTCUT_MODIFIER, SHORTCUT_KEY);
	}

	private void addLabelToEditor(SWTBotGefEditor editor) {
		editor.activateTool("Label");
		editor.click(FIRST_WIDGET_X, FIRST_WIDGET_Y);
	}
	
	private void pressOkOnPopup() {
		SWTBotShell dialog = bot.shell(CANNOT_GENERATE_FRAGMENT_POPUP_TEXT);
		dialog.activate();
		assertNotNull(dialog);
		dialog.bot().button("OK").click();
	}

}

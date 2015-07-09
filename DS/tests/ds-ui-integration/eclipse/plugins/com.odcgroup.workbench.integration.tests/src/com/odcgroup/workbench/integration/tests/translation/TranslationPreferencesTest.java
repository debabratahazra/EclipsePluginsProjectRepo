package com.odcgroup.workbench.integration.tests.translation;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotList;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

/**
 * @author atr
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TranslationPreferencesTest extends AbstractSWTBotTest {

	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	/**
	 * @param value the value to be checked
	 * @param names the collection of string
	 * @return <tt>true</tt> if the given <code>value</code> is contained in the
	 *         collection <code>names</name>
	 */
	private boolean contains(String value, String[] names) {
		boolean contains = false;
		for (String name : names) {
			if (value.equals(name)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	@Test
	public void defaultLanguagesPreferences() {

		IPath path = new Path(DS_PROJECT);
		on.selectNode(path).contextMenu("Properties").click();
		SWTBotShell shell = bot.shell("Properties for " + DS_PROJECT);
		shell.activate();
		SWTBotTreeItem translation = shell.bot().tree().expandNode("Design Studio").expandNode("Translation");
		translation.select();

		SWTBotCombo defLanguage = shell.bot().comboBox();
		assertEquals("English (en)", defLanguage.getText());

		SWTBotList list = shell.bot().list();
		String[] additionalLocales = list.getItems();
		assertEquals("Additional Languages", 2, additionalLocales.length);

		Arrays.sort(additionalLocales);
		assertEquals("1st Additional Languages", "French (fr)", additionalLocales[0]);
		assertEquals("2nd Additional Languages", "German (de)", additionalLocales[1]);

		// ensure combo doesn't contains additional languages
		assertFalse("The Default Langagues Combobox must not have French", contains("French (fr)", defLanguage.items()));
		assertFalse("The Default Langagues Combobox must not have German", contains("German (de)", defLanguage.items()));
		
		// ensure the 'English' is not is the list of all additional language
		SWTBotButton addBtn = shell.bot().button("Add...");
		addBtn.setFocus();
		addBtn.click();
		
		SWTBotShell popup = bot.activeShell();
		popup.activate();
		SWTBotList popupList = popup.bot().list();
		// ensure list doesn't contains neither current selected additional languages nor current default language
		assertFalse("The Default Languages Combobox must not have English", contains("English (en)", popupList.getItems()));
		assertFalse("The Default Languages Combobox must not have French", contains("French (fr)", popupList.getItems()));
		assertFalse("The Default Languages Combobox must not have German", contains("German (de)", popupList.getItems()));
		
		
		assertTrue("The Default Languages Combobox must contain French France", contains("French France (fr_FR)", popupList.getItems()));
		assertTrue("The Default Languages Combobox must contain Tamil", contains("Tamil (ta)", popupList.getItems()));
		popup.bot().button("Cancel").click();

		// close the dialog
		shell.bot().button("OK").click();

	}

}

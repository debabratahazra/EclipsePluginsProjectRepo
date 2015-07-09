package com.odcgroup.workbench.integration.tests.translation;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.ODYSSEY_CAT;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author pkk
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TranslationImportWizardPageTest extends TestCase {

	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	/**
	 * DS-4245
	 */
	@Test
	public void translationImportWizardPage() {
		bot.menu("File").menu("Import...").click();
		bot.shell("Import").activate();
		SWTBotTreeItem odysseyNode = bot.tree().expandNode(ODYSSEY_CAT);
		odysseyNode.select("Import Translations from Excel");
		bot.button(1).click();
		List<Combo> list = bot.getFinder().findControls(WidgetMatcherFactory.widgetOfType(Combo.class));
		assertTrue(list.isEmpty());
		bot.activeShell().close();
	}

}

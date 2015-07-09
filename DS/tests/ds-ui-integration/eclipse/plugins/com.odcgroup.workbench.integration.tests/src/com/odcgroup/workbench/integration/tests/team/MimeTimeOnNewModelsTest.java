package com.odcgroup.workbench.integration.tests.team;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class MimeTimeOnNewModelsTest {

	protected SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	@Test
	public void testMimeTypeSettings() {
		bot.menu("Window").menu("Preferences").click();
		SWTBotShell shell = bot.shell("Preferences");
		SWTBotTreeItem node = bot.tree().expandNode("Team", "SVN", "Properties Configuration");
		node.click();
		node.select();
		Assert.assertTrue("some mime type should be listed", bot.table().rowCount() > 0);
		shell.close();
		bot.waitUntil(shellCloses(shell));
	}
}

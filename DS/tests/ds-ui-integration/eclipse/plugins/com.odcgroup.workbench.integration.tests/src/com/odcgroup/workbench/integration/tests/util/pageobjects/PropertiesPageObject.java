package com.odcgroup.workbench.integration.tests.util.pageobjects;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * A page object that represents a property window in Eclipse
 * 
 * @author Kai Kreuzer
 *
 */
public class PropertiesPageObject {

	protected final SWTBot bot;

	public PropertiesPageObject(SWTBot bot) {
		this.bot = bot;
	}
	
	public void selectTreeItem(String treeItem) {
		bot.tree().select(treeItem);
	}

	public SWTBotTreeItem selectTreeItem(IPath path) {
		SWTBotTreeItem item = new TreeObject(bot.tree()).selectTreeItem(path);
		return item;
	}
	
	public void pressCancelButton() {
		bot.button("Cancel").click();
	}

	public void pressOKButton() {
		bot.button("OK").click();
	}
}

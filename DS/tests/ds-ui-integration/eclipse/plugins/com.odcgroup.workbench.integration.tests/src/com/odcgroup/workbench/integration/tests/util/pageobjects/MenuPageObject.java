package com.odcgroup.workbench.integration.tests.util.pageobjects;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;

import com.odcgroup.workbench.ui.internal.wizards.TemplateSelectionPage;

/**
 * A PageObject that represents the main menu
 *
 * @author Kai Kreuzer
 *
 */
public class MenuPageObject {

	protected SWTWorkbenchBot bot = new SWTWorkbenchBot();

	public void createNewModelProject(String name) {
		createNewModelProject(name, false);
	}
	
	public void createNewModelProject(String name, boolean technical) {
		createNewModelProject_openWizard();
	    SWTBotShell shell = bot.activeShell();
	    bot.textWithLabel("Project name:").setText(name);
	    if(technical) bot.radio("Technical").click();
	    bot.button("Finish").click();
	    bot.waitUntil(shellCloses(shell), 60000);
	}

	public void createNewModelProject_openWizard() {
		SWTBotShell activeShell = bot.activeShell();
		activeShell.activate();
		activeShell.setFocus();
	    bot.menu("File").menu("New").menu("Project...").click();
	    SWTBotShell shell = bot.shell("New Project");
		shell.activate();
	    bot.tree().expandNode("Design Studio").select("Design Studio Model Project");
	    bot.button("Next >").click();
	}

	public void checkoutProjectFromSVN(String url) {
		import_();
		new TreeObject(bot.tree()).selectTreeItem(new Path("SVN/Project from SVN")).doubleClick();
		SWTBotShell shell = bot.shell("Checkout from SVN");
		SWTBot wizardBot = shell.bot();
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 5000L;
			wizardBot.radio("Create a new repository location").click();
			wizardBot.button("Next >").click();
		} catch(WidgetNotFoundException e) {
			// no other locations defined yet
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
		wizardBot.comboBoxWithLabel("URL:").setText(url);
		wizardBot.button("Next >").click();
		try {
			SWTBotPreferences.TIMEOUT = 5000L;
			SWTBotShell progressShell = wizardBot.shell("Progress Information");
			bot.waitUntil(shellCloses(progressShell), 5000);
		} catch(WidgetNotFoundException e) {
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
 		wizardBot.button("Browse...").click();
		wizardBot.activeShell().bot().button("OK").click();
		wizardBot.comboBoxWithLabel("URL:").setText(url);
		wizardBot.button("Finish").click();
		try {
			SWTBotPreferences.TIMEOUT = 5000L;
			SWTBotShell progressShell = wizardBot.shell("Progress Information");
			bot.waitUntil(shellCloses(progressShell), 5000);
		} catch(WidgetNotFoundException e) {
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
		shell = bot.shell("Check Out As");
		wizardBot = shell.bot();
		wizardBot.button("Finish").click();
		SWTBotShell progressShell = wizardBot.shell("Check Out");
	    bot.waitUntil(shellCloses(progressShell), 150000);
	}
	
	public void createNewTemplateProject(String templateName, Map<String,String> params) {
		createNewTemplateProject_openWizard();
		SWTBotShell shell = bot.activeShell();
		createNewTemplateProject_selectAndFillParams(templateName, params);
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell), 60000);
	}

	public void createNewTemplateProject_selectAndFillParams(
			String templateName, Map<String, String> params) {
		bot.comboBoxWithLabel("Choose a Template :").setSelection(TemplateSelectionPage.COMBOBOX_ITEM_INDENT + templateName);
		// set the parameters
		for (Map.Entry<String, String> param: params.entrySet()) {
			bot.table().getTableItem(param.getKey()).select();
			SWTBotText textWithId = bot.textWithId("id", "cellText");
			textWithId.setText(param.getValue());
			bot.activeShell().setFocus();
		}
	}
	
	public void createNewTemplateProject(String templateName, String custoname) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("custo-name", "custo");
		createNewTemplateProject(templateName, params);
	}
	
	public void createNewTemplateProject(String templateName) {
		createNewTemplateProject(templateName, Collections.<String,String>emptyMap());
	}
	
	public void createNewTemplateProject_openWizard() {
		bot.activeShell().setFocus();
		bot.menu("File").menu("New").menu("Project...").click();
		bot.tree().expandNode("Design Studio").select("Design Studio Template Projects");
		bot.button("Next >").click();
	}
	
	public void cancelWizard() {
		SWTBotShell shell = bot.activeShell();
	    bot.button("Cancel").click();
	    bot.waitUntil(shellCloses(shell), 60000);
	}

	public void openPerspective(String name) {
		bot.menu("Window").menu("Open Perspective").menu("Other...").click();
		SWTBotShell openPerspectiveShell = bot.shell("Open Perspective");
		openPerspectiveShell.activate();
		bot.table().select(name);
		bot.button("OK").click();	
	}
	
	public void close() {
		SWTBotShell shell = bot.activeShell();
		if(!shell.getText().startsWith("Design")) {
			bot.waitUntil(shellCloses(shell));
		}
		bot.menu("File").menu("Close").click();
	}
	
	public void exit() {
		bot.activeShell().setFocus();
		bot.menu("File").menu("Exit").click();		
	}

	public void save() {
		bot.activeShell().setFocus();
		bot.menu("File").menu("Save").click();		
	}
	
	public void delete() {
		bot.activeShell().setFocus();
		bot.menu("Edit").menu("Delete").click();		
	}
	
	public void import_() {
		bot.activeShell().setFocus();
		bot.menu("File").menu("Import...").click();		
	}
	
	public void importProjects_openWizard() {
		bot.activeShell().setFocus();
	    bot.menu("File").menu("Import Projects...").click();
	}

	public SWTBotMenu getSubMenuItem(final SWTBotMenu parentMenu,
			final String itemText) throws WidgetNotFoundException {

		MenuItem menuItem = UIThreadRunnable
				.syncExec(new WidgetResult<MenuItem>() {
					public MenuItem run() {
						Menu bar = parentMenu.widget.getMenu();
						if (bar != null) {
							for (MenuItem item : bar.getItems()) {
								if (item.getText().equals(itemText)) {
									return item;
								}
							}
						}
						return null;
					}
				});

		if (menuItem == null) {
			throw new WidgetNotFoundException("MenuItem \"" + itemText
					+ "\" not found.");
		} else {
			return new SWTBotMenu(menuItem);
		}
	}
}

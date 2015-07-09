package com.odcgroup.workbench.integration.tests.util.pageobjects;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.workbench.integration.tests.util.widgets.SWTBotListElement;

/**
 * TODO: Document me!
 *
 * @author amc
 *
 */
public class ModuleEditorPageObject extends EditorPageObject {
	
	protected SWTBotGefEditor gefEditor;

	public static final int TRANLATION_ROW_ENGLISH = 0;
	public static final int TRANLATION_ROW_FRENCH = 1;
	public static final int TRANLATION_ROW_GERMAN = 2;
	
	public ModuleEditorPageObject(String moduleName) {
		 super(moduleName+"."+PageConstants.MODULE_FILE_EXTENSION);
		 gefEditor = bot.gefEditor(moduleName+"."+PageConstants.MODULE_FILE_EXTENSION);
	}

	public void addHBox(int x, int y) {
		gefEditor.activateTool("Horizontal Box");
		gefEditor.click(x, y);
	}
	
	public void addLabel(int x, int y) {
		gefEditor.activateTool("Label");
		gefEditor.click(x, y);
	}
	
	public void setTranslationOnSelectedComponentEnglish(String value) {
		setTranslationOnSelectedComponentByRow(value, TRANLATION_ROW_ENGLISH);
	}
	
	public void setTranslationOnSelectedComponentFrench(String value) {
		setTranslationOnSelectedComponentByRow(value, TRANLATION_ROW_FRENCH);
	}
	
	public void setTranslationOnSelectedComponentGerman(String value) {
		setTranslationOnSelectedComponentByRow(value, TRANLATION_ROW_GERMAN);
	}

	private void setTranslationOnSelectedComponentByRow(String value, int row) {
		SWTBotView propertiesView = bot.viewByTitle("Properties");
		propertiesView.setFocus();
		SWTBot viewBot = propertiesView.bot();
		SWTBotListElement.get(viewBot, 2).select();
		viewBot.table().click(row, 1);
		viewBot.button().click();
		SWTBotShell translationShell = viewBot.shell("Translation");
		translationShell.activate();	
		
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 1000L;
			translationShell.bot().styledText().setText(value);
		} catch (WidgetNotFoundException e) {
			// fall back : get the text (not the styledtext)
			translationShell.bot().textWithLabel("Translation").setText(value);
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
		translationShell.bot().button().click();
	}
}

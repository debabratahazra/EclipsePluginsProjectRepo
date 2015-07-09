package com.odcgroup.workbench.integration.tests.util.pageobjects;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;

public class EditorPageObject {

	protected static SWTGefBot bot = new SWTGefBot();
	protected SWTBotEditor editor;

	public EditorPageObject(String fileName) {
	    this.editor = bot.editorByTitle(fileName);
	}

	public void setFocus() {
		editor.setFocus();
	}

	public void save() {
		editor.save();
	}

	public void close() {
		editor.close();
	}
}
